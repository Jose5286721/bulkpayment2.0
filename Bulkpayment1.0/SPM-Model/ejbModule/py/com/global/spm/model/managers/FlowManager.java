package py.com.global.spm.model.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Approval;
import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.User;
import py.com.global.spm.entities.Workflow;
import py.com.global.spm.entities.Workflowdet;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.interfaces.ApprovalManagerLocal;
import py.com.global.spm.model.interfaces.DraftManagerLocal;
import py.com.global.spm.model.interfaces.FlowManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.UserManagerLocal;
import py.com.global.spm.model.interfaces.WorkflowManagerLocal;
import py.com.global.spm.model.messages.FlowManagerToTransferProcessMessage;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.SMSMangerToFlowManagerMessage;
import py.com.global.spm.model.systemparameters.FlowManagerParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.domain.utils.NotificationEventEnum;

/**
 * Session Bean implementation class FlowManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class FlowManager implements FlowManagerLocal {

	@EJB
	private ApprovalManagerLocal approvalManager;
	@EJB
	private DraftManagerLocal draftManager;
	@EJB
	private PaymentOrderManagerLocal paymentOrderManager;
	@EJB
	private SystemparameterManagerLocal systemparameterManager;
	@EJB
	private UserManagerLocal userManager;
	@EJB
	private WorkflowManagerLocal workflowManager;

	private JMSSender notificationManagerQueueSender = null;

	private final Logger log = Logger.getLogger(FlowManager.class);

	/**
	 * Default constructor.
	 */
	public FlowManager() {
	}

	@PostConstruct
	public void initialize() {
		try {
			if (notificationManagerQueueSender == null) {
				notificationManagerQueueSender = new JMSSender(null,
						"ConnectionFactory", SpmQueues.NOTIFICATION_QUEUE);
				notificationManagerQueueSender.init();
			}
		} catch (Exception e) {
			log.error("Opening connection to queue error --> queue="
					+ SpmQueues.NOTIFICATION_QUEUE, e);
		}
	}

	@Override
	public void generateApprovalFlow(Paymentorder paymentorder) {
		Workflow workflow = workflowManager.getWorkflowById(paymentorder
				.getIdworkflowPk());
		if (workflow != null) {
			List<Approval> approvalList = new ArrayList<Approval>();
			Approval approval;
			for (Workflowdet workflowDet : workflow.getWorkflowdets()) {
				approval = new Approval();
				approval.setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
				approval.setCreationdateTim(new Timestamp(System
						.currentTimeMillis()));
				approval.setIdcompanyPk(paymentorder.getIdcompanyPk());
				approval.setIduserPk(workflowDet.getId().getIduserPk());
				approval.setIdworkflowPk(workflow.getIdworkflowPk());
				approval.setSignedNum(false);
				approval.setStepPk(workflowDet.getStepNum());
				approvalList.add(approval);
			}
			if (!approvalManager.insertApproval(approvalList)) {
				log.error("Generando approval flow --> "
						+ paymentorder.toString());
				return;
			}
			log.debug("Approval flow for paymentorder done --> "
					+ paymentorder.toString());
		} else {
			log.fatal("Workflow no encontrado --> " + paymentorder);
		}
	}

	@Override
	public Long notifyDraftCreator(Paymentorder paymentorder,
			FlowManagerEventCodes event) {
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		User draftCreator = userManager.getUserById(draft.getIduserPk());
		Long[] destinatario = { draftCreator.getIduserPk() };
		NotificationRequestMessage notification = this
				.getNotificationRequestMessage(paymentorder, destinatario,
						event);
		notificationManagerQueueSender.send(notification);
		return draftCreator.getIduserPk();
	}

	@Override
	public void notifyAllSigners(Paymentorder paymentorder,
			FlowManagerEventCodes event, Long idDraftcreator) {
		// si el creador es firmantes, controlar que no reciba sms
		// duplicados
		List<Approval> approvalList = approvalManager
				.getApprovalsByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		NotificationRequestMessage notification = this
				.getNotificationRequestMessage(paymentorder,
						this.getSigners(approvalList, idDraftcreator), event);
		notificationManagerQueueSender.send(notification);
	}

	private Long[] getSigners(List<Approval> approvalList, Long idDraftcreator) {
		List<Long> alambre = new ArrayList<Long>();
		Long[] signers = new Long[0];
		for (Approval a : approvalList) {
			// no notificar al creador cuando es firmante para no recibir
			// duplicados
			if (a.getIduserPk() != idDraftcreator.longValue()) {
				alambre.add(a.getIduserPk());
			}
		}
		return alambre.toArray(signers);
	}

	@Override
	public void notifyNextSigner(Paymentorder paymentorder) {
		List<Approval> signers = approvalManager
				.getApprovalsByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		User user;
		NotificationRequestMessage notification;
		for (Approval approval : signers) {
			if (!approval.getSignedNum()) {
				user = userManager.getUserById(approval.getIduserPk());
				Long[] destinatario = { user.getIduserPk() };
				// notificar firmante via SMS
				notification = this.getNotificationRequestMessage(paymentorder,
						destinatario, FlowManagerEventCodes.SIGNER_NOTIFICATE);
				long maxApprovalTime = systemparameterManager
						.getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME);
				if (maxApprovalTime > 0) {
					notification.setTiempoValidezSms((int) maxApprovalTime);
				}
				notificationManagerQueueSender.send(notification);
				// notificar firmante via EMAIL
				notification = this.getNotificationRequestMessage(paymentorder,
						destinatario,
						FlowManagerEventCodes.SIGNER_NOTIFICATE_EMAIL);
				notificationManagerQueueSender.send(notification);
				break;
			}
		}
	}

	@Override
	public void notifySigner(Paymentorder paymentorder, long iduser,
			FlowManagerEventCodes event) {
		Long[] destinatario = { iduser };
		NotificationRequestMessage notification = this
				.getNotificationRequestMessage(paymentorder, destinatario,
						event);
		notificationManagerQueueSender.send(notification);

	}

	@Override
	public void notifySignEvent(Paymentorder paymentorder, long iduser) {
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		User draftCreator = userManager.getUserById(draft.getIduserPk());
		Long[] destinatario = { iduser, draftCreator.getIduserPk() };
		NotificationRequestMessage notification = this
				.getNotificationRequestMessage(paymentorder, destinatario,
						NotificationEventEnum.NOTIFY_SIGNEVENT);
		notificationManagerQueueSender.send(notification);

	}

	@Override
	public FlowManagerEventCodes approveBySMS(Paymentorder paymentorder,
			SMSMangerToFlowManagerMessage request) {
		// validar estado de la orden de pago
		if (!paymentorder.getStateChr().equals(PaymentOrderStates.EN_PROCESO)) {
			log.error("Invalid payment order state --> "
					+ paymentorder.toString());
			return FlowManagerEventCodes.INVALID_PAYMENT_ORDER_STATE;
		}
		// Validar estado usuario
		User user = userManager.getUserById(request.getIduser());
		if (!user.getStateChr().equals(SpmConstants.ACTIVE)) {
			log.error("Estado de usuario no valido --> "
					+ paymentorder.toString() + ", user=" + user.toString());
			return FlowManagerEventCodes.INACTIVE_USER;
		}
		// validar turno de firma
		List<Approval> signers = approvalManager
				.getApprovalsByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		long nextUserToSign = -1;
		Approval lastApproval = null;
		Approval currentApproval = null;
		for (Approval approval : signers) {
			if (!approval.getSignedNum()) {
				currentApproval = approval;
				nextUserToSign = approval.getIduserPk();
				break;
			} else {
				lastApproval = approval;
			}
		}
		if (currentApproval == null) {
			log.error("Current Approval not found --> " + paymentorder);
			return FlowManagerEventCodes.OTHER_ERROR;
		}
		if (nextUserToSign != request.getIduser()) {
			log.error("No corresponde al turno de usuario --> "
					+ paymentorder.toString() + ", user=" + user.toString());
			return FlowManagerEventCodes.NOT_SIGNER_TURN;
		}
		// validar periodo de tiempo de aprobacion por sms
		long maxApprovalTime = systemparameterManager
				.getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME);
		long current = System.currentTimeMillis();
		long lastApproveTime = -1L;
		if (lastApproval == null) {
			lastApproveTime = currentApproval.getCreationdateTim().getTime();
		} else {
			lastApproveTime = lastApproval.getSigndateTim().getTime();
		}
		// si es menor a 0, tiempo ilimitado
		if ((maxApprovalTime > 0)
				&& (current - lastApproveTime > (maxApprovalTime * SpmConstants.HOUR_IN_MILLIS))) {
			log.error("Aprobacion por sms expirado --> "
					+ paymentorder.toString() + ", user=" + user.toString());
			return FlowManagerEventCodes.APPROVAL_TIME_EXPIRED;
		}
		// validar firma (si la firma es incorrecta, incrementar contador)
		// si supera la cantidad de intentos, bloquear el usuario
		String pinSms= toSHA1(request.getPin().getBytes());
		
		long caseSesitive = systemparameterManager
				.getParameterValue(FlowManagerParameters.SMS_PIN_CASE_SENSITIVE);
		boolean pinCorrect = false;
		if (caseSesitive != 1
				&& pinSms.compareToIgnoreCase(user.getSmspinChr()) == 0) {
			pinCorrect = true;
		} else if (pinSms.compareTo(user.getSmspinChr()) == 0) {
			pinCorrect = true;
		}
		if (pinCorrect == false) {
			long maxRetries = systemparameterManager
					.getParameterValue(FlowManagerParameters.MAX_APPROVAL_FAILED);
			user.setAttemptNum(user.getAttemptNum() + 1);
			if (maxRetries == user.getAttemptNum()) {
				user.setStateChr(SpmConstants.INACTIVE);
				if (!userManager.updateUser(user)) {
					log.error("Updating user --> " + user);
				}
				log.error("SMS Pin de usuario no valido, usuario bloqueado!! --> "
						+ paymentorder.toString() + ", user=" + user.toString());
				return FlowManagerEventCodes.INCORRECT_PIN_USER_BLOCKED;
			} else if (maxRetries - 1 == user.getAttemptNum()) {
				if (!userManager.updateUser(user)) {
					log.error("Updating user --> " + user);
				}
				log.error("SMS Pin de usuario no valido --> "
						+ paymentorder.toString() + ", user=" + user.toString());
				return FlowManagerEventCodes.INCORRECT_PIN_LAST_CHANCE;
			} else {
				if (!userManager.updateUser(user)) {
					log.error("Updating user --> " + user);
				}
				log.error("SMS Pin de usuario no valido --> "
						+ paymentorder.toString() + ", user=" + user.toString());
				// bloqueado el usuario
				return FlowManagerEventCodes.INCORRECT_PIN;
			}
		} else if (user.getAttemptNum() > 0) {
			user.setAttemptNum(0L);
			if (!userManager.updateUser(user)) {
				log.error("Updating user --> " + user);
			}
		}
		// Actualizar approval
		currentApproval.setSignedNum(true);
		currentApproval
				.setSigndateTim(new Timestamp(System.currentTimeMillis()));
		approvalManager.updateApproval(currentApproval);
		log.info("Orden de pago, firmada --> " + paymentorder.toString()
				+ ", user=" + user.toString());
		// ultimo firmante aprobo, actualizar orden de pago
		if (currentApproval.getStepPk() == signers.size()) {
			if (!this.updatePaymentorderState(paymentorder,
					PaymentOrderStates.PENDIENTE_DE_PAGO)) {
				log.error("UpdatingPaymentorderState --> user="
						+ user.toString());
			}
		}
		return FlowManagerEventCodes.SUCCESS;
	}

	@Override
	public void notifySignIncorrectPinError(Paymentorder paymentorder,
			long iduser) {
		long maxRetries = systemparameterManager
				.getParameterValue(FlowManagerParameters.MAX_APPROVAL_FAILED);
		log.debug("notifySignIncorrectPinError --> iduser=" + iduser + ", "
				+ paymentorder);
		User user = userManager.getUserById(iduser);
		int attemptsLefts = (int) (maxRetries - user.getAttemptNum());
		Long[] destinatario = { iduser };
		NotificationRequestMessage notification = this
				.getNotificationRequestMessage(paymentorder, destinatario,
						FlowManagerEventCodes.INCORRECT_PIN);
		notification.setCantReintentos(attemptsLefts);
		notificationManagerQueueSender.send(notification);

	}

	public boolean updatePaymentorderState(Paymentorder paymentorder,
			String state) {
		if (paymentorder == null) {
			log.error("UpdatingPaymentorderState --> paymentorder=null");
			return false;
		} else if (state == null) {
			log.error("UpdatingPaymentorderState --> " + paymentorder
					+ ", state=null");
			return false;
		}
		paymentorder.setStateChr(state);
		paymentOrderManager.updatePaymentorder(paymentorder);
		return true;
	}

	private NotificationRequestMessage getNotificationRequestMessage(
			Paymentorder paymentorder, Long[] destinatarios,
			FlowManagerEventCodes event) {
		NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
		notificationRequest.setEvent(FlowManagerEventCodes.parseMessage(event));
		notificationRequest.setOp(paymentorder);
		notificationRequest.setIdDestinatarios(destinatarios);
		return notificationRequest;
	}

	private NotificationRequestMessage getNotificationRequestMessage(
			Paymentorder paymentorder, Long[] destinatarios,
			NotificationEventEnum event) {
		NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
		notificationRequest.setEvent(event);
		notificationRequest.setOp(paymentorder);
		notificationRequest.setIdDestinatarios(destinatarios);
		return notificationRequest;
	}

	@Override
	public boolean isLastSigner(Paymentorder paymentorder) {
		return paymentorder.getStateChr().equalsIgnoreCase(
				PaymentOrderStates.PENDIENTE_DE_PAGO);
	}

	@Override
	public FlowManagerToTransferProcessMessage getFlowManagerToTransferProcessMessage(
			Paymentorder paymentorder) {
		FlowManagerToTransferProcessMessage message = new FlowManagerToTransferProcessMessage();
		message.setPaymentorder(paymentorder);
		return message;
	}

	@PreDestroy
	public void closeDtq() {
		try {
			notificationManagerQueueSender.close();
		} catch (Exception e) {
			log.error("Closing connection to queue error --> queue="
					+ SpmQueues.NOTIFICATION_QUEUE, e);
		}
	}

	@Override
	public void notifyNextSigner(Paymentorder paymentorder, long idlastapproval) {
		List<Approval> signers = approvalManager
				.getApprovalsByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		User user;
		NotificationRequestMessage notification;
		boolean notificate = false;
		for (Approval approval : signers) {
			if (notificate) {
				user = userManager.getUserById(approval.getIduserPk());
				Long[] destinatario = { user.getIduserPk() };
				notification = this.getNotificationRequestMessage(paymentorder,
						destinatario, FlowManagerEventCodes.SIGNER_NOTIFICATE);
				long maxApprovalTime = systemparameterManager
						.getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME);
				if (maxApprovalTime > 0) {
					notification.setTiempoValidezSms((int) maxApprovalTime);
				}
				notificationManagerQueueSender.send(notification);
				break;
			} else {
				if (approval.getIdapprovalPk() == idlastapproval) {
					notificate = true;
				}
			}
		}

	}

	// agregado por harrellaga
	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public static String toSHA1(byte[] convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			//log.error(e);
		}
		return byteArrayToHexString(md.digest(convertme));
	}

}
