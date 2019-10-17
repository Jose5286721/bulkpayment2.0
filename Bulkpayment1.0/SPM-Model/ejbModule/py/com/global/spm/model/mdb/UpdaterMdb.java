package py.com.global.spm.model.mdb;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.interfaces.DraftManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.UpdaterManagerLocal;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.OPManagerToUpdaterMessage;
import py.com.global.spm.model.messages.ReversionProcessToUpdaterMessage;
import py.com.global.spm.model.messages.TransferProcessToUpdaterMessage;
import py.com.global.spm.model.systemparameters.NotificationParametersEnum;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.util.SpmUtil;
import py.com.global.spm.domain.utils.NotificationEventEnum;

/**
 * Message-Driven Bean implementation class for: UpdaterMdb
 * 
 * @author Lino Chamorro
 * @version 1.1 20/08/13
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.UPDATER_QUEUE) })
public class UpdaterMdb implements MessageListener {

	@EJB
	private UpdaterManagerLocal updaterManager;
	@EJB
	private SystemparameterManagerLocal systemParameterManager;
	@EJB
	private DraftManagerLocal draftManager;

	private JMSSender notificationManagerQueueSender = null;

	private final Logger log = Logger.getLogger(UpdaterMdb.class);

	/**
	 * Default constructor.
	 */
	public UpdaterMdb() {

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

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			if (message != null && !message.getJMSRedelivered()
					&& message instanceof ObjectMessage) {
				Object request = null;
				try {
					request = ((ObjectMessage) message).getObject();
					if (request != null
							&& request instanceof TransferProcessToUpdaterMessage) {
						this.processMessage((TransferProcessToUpdaterMessage) request);
					} else if (request != null
							&& request instanceof ReversionProcessToUpdaterMessage) {
						this.processMessage((ReversionProcessToUpdaterMessage) request);
					} else if (request != null
							&& request instanceof OPManagerToUpdaterMessage) {
						this.processMessage((OPManagerToUpdaterMessage) request);
					} else {
						log.error("Message error --> queue="
								+ SpmQueues.UPDATER_QUEUE
								+ ", message="
								+ (request != null ? request.toString()
										: "NULL"));
					}
				} catch (JMSException e) {
					log.error("Reading message --> queue="
							+ SpmQueues.UPDATER_QUEUE, e);
				}
			}
		} catch (Exception e) {
			log.error("Reading queue --> queue=" + SpmQueues.UPDATER_QUEUE, e);
			return;
		}
	}

	private void processMessage(TransferProcessToUpdaterMessage request) {
		Long init = System.currentTimeMillis();
		Long end = null;
		log.debug("Processing request message --> " + request.toString());
		try {
			Paymentorder paymentorder = request.getPaymentorder();
			// notificaciones
			if (paymentorder.getStateChr().equalsIgnoreCase(
					PaymentOrderStates.PENDIENTE_DE_PAGO)) {
				log.warn("Orden de pago pendiente de pago --> " + paymentorder);
				/*
				 * valida y notifica que no se realizo el pago por saldo
				 * insuficiente
				 */
				this.validateAndNotifyPaidPending(paymentorder);
			} else if (paymentorder.getStateChr().equalsIgnoreCase(
					PaymentOrderStates.NO_PAGADA)) {
				log.warn("Orden de pago no pagada --> " + paymentorder);
				// actualizar contador de transacciones de orden de pago
				this.updatePaymentorderCounter(paymentorder,
						request.getLogpaymentList());
				// valida y notifica no pagado
				this.validateAndNotifyNoPagado(paymentorder);
			} else if (paymentorder.getStateChr().equalsIgnoreCase(
					PaymentOrderStates.PAGADA)
					|| paymentorder.getStateChr().equalsIgnoreCase(
							PaymentOrderStates.PARCIALMENTE_PAGADA)) {
				// actualizar contador de transacciones de empresa
				this.updateCompanyCounters(paymentorder,
						request.getLogpaymentList());
				// actualizar contador de transacciones de orden de pago
				this.updatePaymentorderCounter(paymentorder,
						request.getLogpaymentList());
				// validar y notificar firmantes
				this.validateAndNotifyPayment(paymentorder);
				// validar y notificar a beneficiarios pago o parcialmente
				// pagado
				this.validateAndNotifyPaymentToBeneficiaries(paymentorder,
						request.getLogpaymentList());
			} else {
				log.warn("Notificacion no realizada. Estado no valido --> "
						+ paymentorder.toString());
			}
			// actualizar orden de pago
			this.updatePaymentorder(paymentorder, request.isReintento());
		} catch (Exception e) {
			log.error("Updating --> " + e, e);
		}
		end = System.currentTimeMillis();
		log.trace("processTime=" + SpmUtil.millisToSecondStr(end - init)
				+ "secs, " + request);
	}

	/**
	 * Actualiza los contadores de transacciones (paymentsuccessNum,
	 * paymenterrorNum, paymentpartsucNum) de la orden de pago. El
	 * totalpaymentNum ya setea el transfer process
	 * 
	 * @param paymentorder
	 */
	private void updatePaymentorderCounter(Paymentorder paymentorder,
			List<Logpayment> logpaymentList) {
		int success = paymentorder.getPaymentsuccessNum();
		int parcsuccess = 0;
		int error = 0;
		for (Logpayment logpayment : logpaymentList) {
			if (logpayment.getStateChr().equals(PaymentStates.SATISFACTORIO)) {
				success++;
			} else if (logpayment.getStateChr().equals(
					PaymentStates.PARCIALMENTE_SATISFACTORIO)) {
				parcsuccess++;
			} else if (logpayment.getStateChr().equals(PaymentStates.ERROR)) {
				error++;
			} else {
				log.warn("Guarda que el estado de este logpayment no se tiene en cuenta para actualizar contadores --> "
						+ logpayment);
			}
		}
		paymentorder.setPaymentsuccessNum(success);
		paymentorder.setPaymentpartsucNum(parcsuccess);
		paymentorder.setPaymenterrorNum(error);
	}

	private void updatePaymentorder(Paymentorder paymentorder, boolean isReintento) {
		log.debug("Updating paymentorder --> " + paymentorder);
		paymentorder
				.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
		if (!updaterManager.updatePaymentorder(paymentorder, isReintento)) {
			log.error("Error updaing paymentorder --> " + paymentorder);
		}
	}

	private void updateCompanyCounters(Paymentorder paymentorder,
			List<Logpayment> logpaymentList) {
		int payments = 0;
		int unpaid = 0;
		int total = 0;
		for (Logpayment lp : logpaymentList) {
			if (lp.getStateChr().equalsIgnoreCase(PaymentStates.SATISFACTORIO)) {
				payments++;
			} else {
				unpaid++;
			}
			total++;
		}
		log.debug("Updating company counters --> paid=" + payments
				+ ", unpaid=" + unpaid + ", total=" + total + ", "
				+ paymentorder);
		updaterManager.updateCompany(paymentorder.getIdcompanyPk(), payments,
				paymentorder.getUpdatedateTim());
	}

	private void validateAndNotifyPaymentToBeneficiaries(
			Paymentorder paymentorder, List<Logpayment> logpaymentList) {
		if (paymentorder.getNotifybenficiaryNum()) {
			for (Logpayment logpayment : logpaymentList) {
				if (logpayment.getStateChr().equalsIgnoreCase(
						PaymentStates.SATISFACTORIO)
						|| logpayment.getStateChr().equalsIgnoreCase(
								PaymentStates.PARCIALMENTE_SATISFACTORIO)) {
					// notificar beneficiario
					NotificationRequestMessage notificationRequest = updaterManager
							.getSMSRequest(
									paymentorder,
									logpayment,
									NotificationEventEnum.NOTIFY_PAYMENT_BENEFICIARY,
									logpayment.getIdbeneficiaryPk());
					notificationManagerQueueSender.send(notificationRequest);
				}
			}
		} else {
			log.warn("Notificacion de pago a beneficiarios --> false");
		}
	}

	private void validateAndNotifyPayment(Paymentorder paymentorder) {
		NotificationRequestMessage notificationRequest;
		// notificar creador OP
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		if (draft != null) {
			notificationRequest = updaterManager.getSMSRequest(paymentorder,
					NotificationEventEnum.NOTIFY_PAYMENT_SIGNERS,
					draft.getIduserPk());
			notificationManagerQueueSender.send(notificationRequest);
		} else {
			log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> "
					+ paymentorder);
		}
		if (paymentorder.getNotifysignerNum()) {
			Long[] signers = updaterManager.getSignersWithoutIdDraftCreator(
					paymentorder, draft.getIduserPk());
			if (signers != null) {
				notificationRequest = updaterManager.getSMSRequest(
						paymentorder,
						NotificationEventEnum.NOTIFY_PAYMENT_SIGNERS, signers);
				notificationManagerQueueSender.send(notificationRequest);
			} else {
				log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> "
						+ paymentorder);
			}
		} else {
			log.warn("Notificacion de pago a firmantes --> false");
		}
	}

	private void validateAndNotifyPaidPending(Paymentorder paymentorder) {
		log.debug("Notificar pendiente de pago --> " + paymentorder.toString());
		NotificationRequestMessage notificationRequest;
		// notificar creador OP (draft)
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		if (draft != null) {
			notificationRequest = updaterManager.getSMSRequest(paymentorder,
					NotificationEventEnum.NOTIFY_INSUFFICIENT_MONEY,
					draft.getIduserPk());
			notificationManagerQueueSender.send(notificationRequest);
		} else {
			log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> "
					+ paymentorder);
		}
		// validar y notificar firmantes
		boolean notifyPendingPaid = systemParameterManager
				.getBooleanParameterValue(NotificationParametersEnum.NOTIFY_SIGN_PAID_PENDIND);
		if (notifyPendingPaid) {
			Long[] signers = updaterManager.getSignersWithoutIdDraftCreator(
					paymentorder, draft.getIduserPk());
			if (signers != null) {
				notificationRequest = updaterManager.getSMSRequest(
						paymentorder,
						NotificationEventEnum.NOTIFY_INSUFFICIENT_MONEY,
						signers);
				notificationManagerQueueSender.send(notificationRequest);
			} else {
				log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> "
						+ paymentorder);
			}
		} else {
			log.warn("Notificacion pendiente de pago a firmantes --> false");
		}
	}

	private void validateAndNotifyNoPagado(Paymentorder paymentorder) {
		log.debug("Notificar no pagado --> " + paymentorder.toString());
		NotificationRequestMessage notificationRequest;
		// notificar creador OP
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		if (draft != null) {
			notificationRequest = updaterManager
					.getSMSRequest(paymentorder,
							NotificationEventEnum.NOTIFY_PO_UNPAID,
							draft.getIduserPk());
			notificationManagerQueueSender.send(notificationRequest);
		} else {
			log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> "
					+ paymentorder);
		}
		// validar y notificar firmantes
		boolean notifyUnpaid = systemParameterManager
				.getBooleanParameterValue(NotificationParametersEnum.NOTIFY_SIGN_UNPAID);
		if (notifyUnpaid) {
			Long[] signers = updaterManager.getSignersWithoutIdDraftCreator(
					paymentorder, draft.getIduserPk());
			if (signers != null) {
				notificationRequest = updaterManager.getSMSRequest(
						paymentorder, NotificationEventEnum.NOTIFY_PO_UNPAID,
						signers);
				notificationManagerQueueSender.send(notificationRequest);
			} else {
				log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> "
						+ paymentorder);
			}
		} else {
			log.warn("Notificacion no pagado a firmantes --> false");
		}
	}

	private void processMessage(OPManagerToUpdaterMessage request) {
		updaterManager.insertLogdraftop(request.getLogdraftop());
	}

	private void processMessage(ReversionProcessToUpdaterMessage request) {
		// ByR2:
		Long init = System.currentTimeMillis();
		Long end = null;
		log.debug("Processing request message --> " + request.toString());
		Paymentorder paymentorder = request.getPaymentorder();
		// actualizar estado orden de pago
		this.updatePaymentorder(paymentorder, false);
		// notificaciones
		if (paymentorder.getStateChr().equalsIgnoreCase(
				PaymentOrderStates.NO_REVERTIDA)) {
			log.warn("Orden de pago no revertida --> " + paymentorder);
			// valida y notifica no revertido
			this.validateAndNotifyNoRevertida(paymentorder);
		} else if (paymentorder.getStateChr().equalsIgnoreCase(
				PaymentOrderStates.REVERTIDA)
				|| paymentorder.getStateChr().equalsIgnoreCase(
						PaymentOrderStates.PARCIALMENTE_REVERTIDA)) {
			// validar y notificar a firmantes revertida o parcialmente
			// revertida
			this.validateAndNotifyRevertida(paymentorder);
		} else {
			log.warn("Notificacion no realizada. Estado no valido --> "
					+ paymentorder.toString());
		}
		end = System.currentTimeMillis();
		log.trace("processTime=" + SpmUtil.millisToSecondStr(end - init)
				+ "secs, " + request);

	}

	private void validateAndNotifyRevertida(Paymentorder paymentorder) {
		// ByR2
		log.debug("Notificar orden de pago revertida --> "
				+ paymentorder.toString());
		NotificationRequestMessage notificationRequest;
		// notificar creador OP
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		if (draft != null) {
			notificationRequest = updaterManager.getSMSRequest(paymentorder,
					NotificationEventEnum.NOTIFY_PO_REVERSION,
					draft.getIduserPk());
			notificationManagerQueueSender.send(notificationRequest);
		} else {
			log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> "
					+ paymentorder);
		}
		// validar y notificar firmantes
		boolean notifyUnpaid = systemParameterManager
				.getBooleanParameterValue(NotificationParametersEnum.NOTIFY_SIGN_REVERSION);
		if (notifyUnpaid) {
			Long[] signers = updaterManager.getSignersWithoutIdDraftCreator(
					paymentorder, draft.getIduserPk());
			if (signers != null) {
				notificationRequest = updaterManager.getSMSRequest(
						paymentorder,
						NotificationEventEnum.NOTIFY_PO_REVERSION, signers);
				notificationManagerQueueSender.send(notificationRequest);
			} else {
				log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> "
						+ paymentorder);
			}
		} else {
			log.warn("Notificacion no revertido a firmantes --> false");
		}

	}

	private void validateAndNotifyNoRevertida(Paymentorder paymentorder) {
		// ByR2
		log.debug("Notificar no revertido --> " + paymentorder.toString());
		NotificationRequestMessage notificationRequest;
		// notificar creador OP
		Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
		if (draft != null) {
			notificationRequest = updaterManager.getSMSRequest(paymentorder,
					NotificationEventEnum.NOTIFY_PO_UNREVERSION,
					draft.getIduserPk());
			notificationManagerQueueSender.send(notificationRequest);
		} else {
			log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> "
					+ paymentorder);
		}
		// validar y notificar firmantes
		boolean notifyUnpaid = systemParameterManager
				.getBooleanParameterValue(NotificationParametersEnum.NOTIFY_SIGN_REVERSION);
		if (notifyUnpaid) {
			Long[] signers = updaterManager.getSignersWithoutIdDraftCreator(
					paymentorder, draft.getIduserPk());
			if (signers != null) {
				notificationRequest = updaterManager.getSMSRequest(
						paymentorder,
						NotificationEventEnum.NOTIFY_PO_UNREVERSION, signers);
				notificationManagerQueueSender.send(notificationRequest);
			} else {
				log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> "
						+ paymentorder);
			}
		} else {
			log.warn("Notificacion no revertido a firmantes --> false");
		}
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

}
