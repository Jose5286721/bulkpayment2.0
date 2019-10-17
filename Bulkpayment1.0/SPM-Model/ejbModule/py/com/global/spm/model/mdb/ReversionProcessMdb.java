package py.com.global.spm.model.mdb;

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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Logreversion;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.dto.ReversalDto;
import py.com.global.spm.model.eventcodes.ReversionProcessEventCodes;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.ReversionProcessManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.MTSReversionToReversionProcessMessage;
import py.com.global.spm.model.messages.ReversionProcessToMTSReversionMessage;
import py.com.global.spm.model.messages.ReversionProcessToUpdaterMessage;
import py.com.global.spm.model.messages.SPMGUIToReversionProcessMessage;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;

/**
 * El TransferProcess se encarga de ejecutar la orden de pago.
 * 
 * @author R2
 * */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.REVERSION_PROCESS_QUEUE) })
public class ReversionProcessMdb implements MessageListener {

	private static final Logger log = Logger
			.getLogger(ReversionProcessMdb.class);
	private JMSSender queueUpdaterRequest;
	private JMSSender queueMTSInterfaceRequest;
	private JMSSender queueAsyncUpdater;

	@EJB
	PaymentOrderManagerLocal paymentOrderManager;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	@EJB
	ReversionProcessManagerLocal reversionProcess;

	@EJB
	SystemparameterManagerLocal systemParameterManager;

	public ReversionProcessMdb() {

	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		// Leer el mensaje
		log.debug("Leyendo mensaje...");
		if (message != null && message instanceof ObjectMessage) {
			Object msg = null;
			try {
				msg = ((ObjectMessage) message).getObject();
			} catch (JMSException e) {
				log.error("Al leer un mensaje de la cola --> cola="
						+ SpmQueues.TRANSFER_PROCESS_QUEUE + ", msg="
						+ e.getMessage());
				msg = null;
			}

			if (msg != null) {
				// Procesar una solicitud del cobro del valorizador
				if (msg instanceof SPMGUIToReversionProcessMessage) {
					log.debug("Recibiendo mensaje desde SPM-GUI");
					SPMGUIToReversionProcessMessage request = (SPMGUIToReversionProcessMessage) msg;
					// Procesar solicitud de cobro
					log.debug("Procesando requerimiento --> " + request);
					processRequest(request);
				} else if (msg instanceof MTSReversionToReversionProcessMessage) {
					log.debug("Recibiendo mensaje desde MTSReversion");
					MTSReversionToReversionProcessMessage request = (MTSReversionToReversionProcessMessage) msg;
					// Procesar solicitud de cobro
					log.debug("Procesando requerimiento --> " + request);
					processResponse(request);
				} else {
					log.debug("Mensaje NULO!!!");
					return;
				}
			}
		}

	}

	private void processRequest(SPMGUIToReversionProcessMessage request) {
		Paymentorder op = this.paymentOrderManager.getPaymentorder(request
				.getIdpaymentorderPk());
		this.processReversion(op, request.getIdUserPk());
	}

	/**
	 * Verifica si la orden de pago se encuentra en el hash.
	 * 
	 * @param op
	 *            La orden de pago a validar.
	 * @return true si la orden de pago no es duplicada y false en caso
	 *         contrario.
	 */
	private boolean validateDuplicate(Paymentorder op) {
		// Validar si la orden de pago ya se encuentra en el hash.
		return !this.reversionProcess.existPaymentOrder(op);
	}

	private void processReversion(Paymentorder op, Long idUserPk) {

		if (validateDuplicate(op)) {
			// 1- Obtener los pagos a revertir.
			List<Logpayment> pagosParaRevertir = this.reversionProcess
					.getPaymentsToReversion(op);

			if (pagosParaRevertir != null && pagosParaRevertir.size() > 0) {
				// 2- Cambiar a estado en Proceso la orden de pago.
				op.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
				op.setStateChr(PaymentOrderStates.REVIRTIENDO);
				op.setIdeventcodeNum(0);
				this.paymentOrderManager.updatePaymentorder(op);

				// 3- Enviar cada pago a reveritir al MTSInterface.
				for (Logpayment logpayment : pagosParaRevertir) {
					ReversionProcessToMTSReversionMessage tpmi = new ReversionProcessToMTSReversionMessage();
					tpmi.setPayment(logpayment);
					if (!this.sendRequestMTSInterface(tpmi)) {
						log.error("Fallo al envio de mensaje al MTSInterface-->"
								+ tpmi);
					}
				}
				Logreversion lr = new Logreversion();
				lr.setIdcompanyPk(op.getIdcompanyPk());
				lr.setIdpaymentorderPk(op.getIdpaymentorderPk());
				lr.setIduserPk(idUserPk);
				lr.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
				lr.setStateChr(SpmConstants.SUCCESS);
				lr.setIdeventcodeNum(0);//Exito.
				this.sendAsyncUpdater(lr);
			} else {
				Logreversion lr = new Logreversion();
				lr.setIdcompanyPk(op.getIdcompanyPk());
				lr.setIdpaymentorderPk(op.getIdpaymentorderPk());
				lr.setIduserPk(idUserPk);
				lr.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
				lr.setStateChr(SpmConstants.ERROR);
				lr.setIdeventcodeNum(ReversionProcessEventCodes.WITHOUT_PAYMENTS
						.getIdEventCode());
				this.sendAsyncUpdater(lr);
				log.warn("No hay pagos para revertir de la op con id="
						+ op.getIdpaymentorderPk());
			}

		} else {
			Logreversion lr = new Logreversion();
			lr.setIdcompanyPk(op.getIdcompanyPk());
			lr.setIdpaymentorderPk(op.getIdpaymentorderPk());
			lr.setIduserPk(idUserPk);
			lr.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
			lr.setStateChr(SpmConstants.ERROR);
			lr.setIdeventcodeNum(ReversionProcessEventCodes.DUPLICATE_REVERSION
					.getIdEventCode());
			this.sendAsyncUpdater(lr);
			log.warn("Orden de pago en proceso, solicitud descartada -->" + op);
		}

	}

	private void processResponse(MTSReversionToReversionProcessMessage response) {
		// 1- Buscar en el cache el pago a revertir.
		if (this.reversionProcess.existLogpayment(response.getPayment())) {
			// 2- Actualizar el estado del pago revertido.
			ReversalDto pod = this.reversionProcess
					.updateReversalCache(response.getPayment());
			// 3- Verificar si es el ultimo pago a procesar.
			if (!pod.getPaymentOrder().getStateChr()
					.equalsIgnoreCase(PaymentOrderStates.REVIRTIENDO)) {
				// 3.1- Enviar resultado al proceso Updater.
				ReversionProcessToUpdaterMessage tpu = new ReversionProcessToUpdaterMessage();
				tpu.setPaymentorder(pod.getPaymentOrder());
				if (!this.sendRequestUpdater(tpu)) {
					log.error("Fallo al envio de mensaje al UPDATER-->" + tpu);
				}
				// 3.2- Sacar del cache.
				this.reversionProcess.removeReversalCache(pod);
			}

		} else {
			log.warn("Mensaje descartado -->" + response.toString());
		}

	}

	@PostConstruct
	public void init() {

		try {
			if (initialContext == null) {
				log.debug("Iniciando el contexto...");
				initialContext = new InitialContext();
			}
			// Iniciamos las colas.
			initDtq();

		} catch (NamingException e) {
			log.error("Fallo al cargar el contexto del Contenedor --> error="
					+ e.getMessage());
		} catch (Exception e) {
			log.error("Error al inicializar Charger->" + e.getMessage());
		}
	}

	private void initDtq() {

		try {
			// Levantar la cola para MTS Interface.
			if (queueAsyncUpdater == null)
				queueAsyncUpdater = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.ASYNC_UPDATER_QUEUE);

			if (!queueAsyncUpdater.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueAsyncUpdater.toString() + ", init="
						+ queueAsyncUpdater.init());

			if (queueMTSInterfaceRequest == null)
				queueMTSInterfaceRequest = new JMSSender(initialContext,
						"ConnectionFactory",
						SpmQueues.MTS_REVERSION_INTERFACE_QUEUE);

			if (!queueMTSInterfaceRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueMTSInterfaceRequest.toString() + ", init="
						+ queueMTSInterfaceRequest.init());

			// Levantar la cola para Updater.
			if (queueUpdaterRequest == null)
				queueUpdaterRequest = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.UPDATER_QUEUE);

			if (!queueUpdaterRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueUpdaterRequest.toString() + ", init="
						+ queueUpdaterRequest.init());
		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	private boolean sendRequestUpdater(ReversionProcessToUpdaterMessage tpu) {
		boolean ready = false;
		if (queueUpdaterRequest != null && queueUpdaterRequest.isConnected()) {
			log.trace("Mensaje a Updater desde TransferProcessMdb--> " + tpu);
			if (!(ready = queueUpdaterRequest.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.UPDATER_QUEUE);
				try {
					queueUpdaterRequest.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.UPDATER_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.UPDATER_QUEUE);
		}
		return ready;
	}

	private boolean sendRequestMTSInterface(
			ReversionProcessToMTSReversionMessage tpu) {
		boolean ready = false;
		if (queueMTSInterfaceRequest != null
				&& queueMTSInterfaceRequest.isConnected()) {
			log.trace("Mensaje a MTSInterface --> " + tpu);
			if (!(ready = queueMTSInterfaceRequest.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.MTS_REVERSION_INTERFACE_QUEUE);
				try {
					queueMTSInterfaceRequest.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.MTS_REVERSION_INTERFACE_QUEUE
							+ ",Error=" + e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.MTS_REVERSION_INTERFACE_QUEUE);
		}
		return ready;
	}

	private boolean sendAsyncUpdater(Logreversion tpu) {
		boolean ready = false;
		if (queueAsyncUpdater != null && queueAsyncUpdater.isConnected()) {
			log.trace("Mensaje a AsyncUpdater --> " + tpu);
			if (!(ready = queueAsyncUpdater.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.ASYNC_UPDATER_QUEUE);
				try {
					queueAsyncUpdater.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.ASYNC_UPDATER_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.UPDATER_QUEUE);
		}
		return ready;
	}

	@PreDestroy
	public void close() {
		try {
			if (queueAsyncUpdater != null) {
				queueAsyncUpdater.close();
			}
			if (queueMTSInterfaceRequest != null) {
				queueMTSInterfaceRequest.close();
			}
			if (queueUpdaterRequest != null) {
				queueUpdaterRequest.close();
			}

		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
