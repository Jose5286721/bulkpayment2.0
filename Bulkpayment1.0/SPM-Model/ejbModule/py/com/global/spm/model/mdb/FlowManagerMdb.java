package py.com.global.spm.model.mdb;

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

import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.interfaces.FlowManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.FlowManagerToTransferProcessMessage;
import py.com.global.spm.model.messages.OPManagerToFlowManagerMessage;
import py.com.global.spm.model.messages.SMSMangerToFlowManagerMessage;
import py.com.global.spm.model.messages.SPMGUIToFlowManagerMessage;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpmQueues;

/**
 * Message-Driven Bean implementation class for: FlowManagerMdb
 * 
 * @author Lino Chamorro
 * @version 1.0 11/09/13
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.FLOW_MANAGER_QUEUE) })
public class FlowManagerMdb implements MessageListener {

	@EJB
	private FlowManagerLocal flowManager;
	@EJB
	private PaymentOrderManagerLocal paymentOrderManager;
	@EJB
	private SystemparameterManagerLocal systemParameterManager;

	private JMSSender transferProcessQueueSender = null;

	private final Logger log = Logger.getLogger(FlowManagerMdb.class);

	/**
	 * Default constructor.
	 */
	public FlowManagerMdb() {
	}

	@PostConstruct
	public void initialize() {
		try {
			if (transferProcessQueueSender == null) {
				transferProcessQueueSender = new JMSSender(null,
						"ConnectionFactory", SpmQueues.TRANSFER_PROCESS_QUEUE);
				transferProcessQueueSender.init();
			}
		} catch (Exception e) {
			log.error("Opening connection to queue error --> queue="
					+ SpmQueues.TRANSFER_PROCESS_QUEUE, e);
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
							&& request instanceof OPManagerToFlowManagerMessage) {
						this.processMessage((OPManagerToFlowManagerMessage) request);
					} else if (request != null
							&& request instanceof SPMGUIToFlowManagerMessage) {
						this.processMessage((SPMGUIToFlowManagerMessage) request);
					} else if (request != null
							&& request instanceof SMSMangerToFlowManagerMessage) {
						this.processMessage((SMSMangerToFlowManagerMessage) request);
					} else {
						log.error("Message error --> queue="
								+ SpmQueues.FLOW_MANAGER_QUEUE
								+ ", message="
								+ (request != null ? request.toString()
										: "NULL"));
					}
				} catch (JMSException e) {
					log.error("Reading message --> queue="
							+ SpmQueues.FLOW_MANAGER_QUEUE, e);
				}
			}
		} catch (Exception e) {
			log.error(
					"Reading queue --> queue=" + SpmQueues.FLOW_MANAGER_QUEUE,
					e);
			return;
		}

	}

	private void processMessage(OPManagerToFlowManagerMessage request) {
		// generar flujo de aprobacion de orden de pago
		flowManager.generateApprovalFlow(request.getPaymentorder());
		// notificar generacion de orden de pago al creador del draft
		Long idDraftcreator = flowManager.notifyDraftCreator(
				request.getPaymentorder(),
				FlowManagerEventCodes.PAYMENT_ORDER_GENERATED);
		// si corresponde, notificar generacion de orden de pago a los firmantes
		if (request.getPaymentorder().getNotifygenNum()) {
			flowManager.notifyAllSigners(request.getPaymentorder(),
					FlowManagerEventCodes.PAYMENT_ORDER_GENERATED,
					idDraftcreator);
			log.debug("Payment order generation notificated to signers --> "
					+ request.getPaymentorder());
		}
		// si corresponde, notificar al primer firmante
		if (request.getPaymentorder().getNotifysignerNum()) {
			flowManager.notifyNextSigner(request.getPaymentorder());
		}
	}

	/**
	 * Firma o cancelacion realizada por medio de la interfaz web. La interfaz
	 * realiza las validaciones correspondientes a la firma.
	 * 
	 * @param request
	 */
	private void processMessage(SPMGUIToFlowManagerMessage request) {
		Paymentorder paymentorder = paymentOrderManager.getPaymentorder(request
				.getIdpaymentorder());
		if (request.getEvent() == SPMGUIToFlowManagerMessage.CANCELACION_ORDENDEPAGO) {
			log.debug("Procesando cancelacion de orden de pago --> " + request);
			// notificar cancelacion de orden de pago al creador
			Long idDraftcreator = flowManager.notifyDraftCreator(paymentorder,
					FlowManagerEventCodes.PAYMENT_ORDER_CANCELATION);
			// si corresponde, notificar la cancelacion de orden de pago a
			// firmantes
			if (paymentorder.getNotifycancelNum()) {
				flowManager.notifyAllSigners(paymentorder,
						FlowManagerEventCodes.PAYMENT_ORDER_CANCELATION,
						idDraftcreator);
				log.debug("Payment order cancelation notificated to signers --> "
						+ paymentorder);
			}
		} else if (request.getEvent() == SPMGUIToFlowManagerMessage.FIRMA_ORDENDEPAGO) {
			log.debug("Procesando firma de orden de pago --> " + request);
			// enviar confirmacion a firmante y al creador
			flowManager.notifySigner(paymentorder, request.getIduser(),
					FlowManagerEventCodes.SUCCESS);
			flowManager.notifySignEvent(paymentorder, request.getIduser());
			// si es el ultimo firmante, enviar al Transfer process
			if (request.isLastSigner()) {
				if (!flowManager.updatePaymentorderState(paymentorder,
						PaymentOrderStates.PENDIENTE_DE_PAGO)) {
					log.error("Actualizando estado de orden de pago --> "
							+ request);
				}
				FlowManagerToTransferProcessMessage message = flowManager
						.getFlowManagerToTransferProcessMessage(paymentorder);
				transferProcessQueueSender.send(message);
			} else {
				// si corresponde, notificar al siguiente firmante y
				if (paymentorder.getNotifysignerNum()) {
					flowManager.notifyNextSigner(paymentorder,
							request.getIdApproval());
				}
			}
		} else {
			log.error("evento no valido --> " + request.toString());
		}
	}

	private void processMessage(SMSMangerToFlowManagerMessage request) {
		// validar firma y notificar resultado de aprobacion
		Paymentorder paymentorder = paymentOrderManager.getPaymentorder(request
				.getIdpaymentorder());
		FlowManagerEventCodes validate = flowManager.approveBySMS(paymentorder,
				request);
		if (validate.sonIguales(FlowManagerEventCodes.SUCCESS)) {
			// enviar confirmacion a firmante y al creador
			flowManager.notifySigner(paymentorder, request.getIduser(),
					validate);
			flowManager.notifySignEvent(paymentorder, request.getIduser());
			// si es el ultimo firmante, enviar al Transfer process
			if (flowManager.isLastSigner(paymentorder)) {
				FlowManagerToTransferProcessMessage message = flowManager
						.getFlowManagerToTransferProcessMessage(paymentorder);
				transferProcessQueueSender.send(message);
			} else {
				// si corresponde, notificar al siguiente firmante y
				if (paymentorder.getNotifysignerNum()) {
					flowManager.notifyNextSigner(paymentorder);
				}
			}
		} else {
			// enviar notificacion de error a firmante
			if (!validate.sonIguales(FlowManagerEventCodes.INCORRECT_PIN)) {
				flowManager.notifySigner(paymentorder, request.getIduser(),
						validate);
			} else {
				// notify with attempts lefts
				flowManager.notifySignIncorrectPinError(paymentorder,
						request.getIduser());
			}
		}
	}

	@PreDestroy
	public void closeDtq() {
		try {
			transferProcessQueueSender.close();
		} catch (Exception e) {
			log.error("Closing connection to queue error --> queue="
					+ SpmQueues.TRANSFER_PROCESS_QUEUE, e);
		}
	}

}
