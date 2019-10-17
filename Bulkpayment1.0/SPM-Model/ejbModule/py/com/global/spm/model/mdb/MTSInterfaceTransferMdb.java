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
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.model.eventcodes.MTSTransferProcessEventCodes;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.MTSTransferInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.MTSInterfaceToTransferProcessMessage;
import py.com.global.spm.model.messages.TransferProcessToMTSInterfaceMessage;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.mts.manager.SubscriberValidationManagerLocal;

/**
 * El TransferProcess se encarga de ejecutar la orden de pago.
 * 
 * @author R2
 * */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.MTS_INTERFACE_QUEUE) })
public class MTSInterfaceTransferMdb implements MessageListener {

	private static final Logger log = Logger
			.getLogger(MTSInterfaceTransferMdb.class);
	private JMSSender queueTransferProcess;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	@EJB
	SystemparameterManagerLocal systemParameterManager;

	@EJB
	MTSTransferInterfaceManagerLocal mtsTransferManager;

	@EJB
	public BeneficiaryManagerLocal beneficiaryMgr;

	@EJB
	private SubscriberValidationManagerLocal subscriberValidatorManager;

	public MTSInterfaceTransferMdb() {

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
						+ SpmQueues.MTS_INTERFACE_QUEUE + ", msg="
						+ e.getMessage());
				msg = null;
			}

			if (msg != null) {
				// Procesar una solicitud del cobro del valorizador
				if (msg instanceof TransferProcessToMTSInterfaceMessage) {
					log.debug("Recibiendo mensaje desde TransferProcess");
					TransferProcessToMTSInterfaceMessage request = (TransferProcessToMTSInterfaceMessage) msg;
					// Procesar solicitud de cobro
					log.debug("Procesando requerimiento --> " + request);
					processRequest(request);
				} else {
					log.debug("Mensaje NULO!!!");
					return;
				}
			}
		}
	}

	private void processRequest(TransferProcessToMTSInterfaceMessage request) {
		// TODO Validar estado logPayment, si es OK meterle
		// FIXME Aqui buscar de la tabla beneficiario la CI (buscar por id
		// benefeciary) y validar contra plataforma
		// Si la validacion es OK

		String idCompanyPaymentForAgent = systemParameterManager
				.getParameterValue(MtsInterfaceParameters.ID_COMPANY_FOR_AGENT_PAYMENT);

		Boolean agentPayment = isAgentPayemnt(request.getPayment()
				.getIdcompanyPk(), idCompanyPaymentForAgent);

		if (!agentPayment) {// Si es para el pago a los Agentes no se valida CI.
			String ci = request.getPayment().getSubscriberDocNumChr();
			String phone = request.getPayment().getPhonenumberChr();
			Integer eventCode = subscriberValidatorManager
					.getSucriberValitation(ci, phone);
			log.info("Validacion de cedula del suscriptor --> [cuenta " + phone
					+ ", ci" + ci + "]");

			// subscriptor existe pero la cedula no coincide.
			if (eventCode == SpmConstants.EVENT_SUBSCRIBER_INVALID_DOC_NUMBER) {
				request.getPayment().setStateChr(PaymentStates.ERROR);
				request.getPayment().setIdeventcodeNum(
						MTSTransferProcessEventCodes.NO_COINCIDE_CEDULA
								.getIdEventCode());
				log.error("Validacion cedula ERROR. Suscriptor --> [cuenta "
						+ phone + " no esta asociada a la cedula " + ci + "]");
				// Aca esta todo OK, hay que pagar
			} else if (eventCode == SpmConstants.EVENT_SUBSCRIBER_EXIST) {
				// request.getPayment().setStateChr(PaymentStates.SATISFACTORIO);
				request.getPayment().setIdeventcodeNum(
				MTSTransferProcessEventCodes.CLIENTE_EXISTE
				.getIdEventCode());
				request.getPayment().setStateChr(PaymentStates.EN_PROCESO);
				log.debug("Validacion cedula OK. Suscriptor --> [cuenta "
						+ phone + ", cedula " + ci + "]");

			} else if (eventCode == SpmConstants.EVENT_SUBSCRIBER_NOT_EXIST) {
				request.getPayment().setStateChr(PaymentStates.ERROR);
				request.getPayment().setIdeventcodeNum(
						MTSTransferProcessEventCodes.CLIENTE_NO_EXISTE
								.getIdEventCode());
				log.error("Validacion cedula ERROR. Suscriptor --> [cuenta "
						+ phone + ", no existe suscriptor en el MTS, cedula "
						+ ci + "]");

			} else {// error
				request.getPayment().setStateChr(PaymentStates.ERROR);
				request.getPayment().setIdeventcodeNum(
						MTSTransferProcessEventCodes.ERROR_VALIDACION
								.getIdEventCode());
				log.info("Validacion cedula ERROR.");
			}

		}
		// FIXME Si la validacion ndoikoi setear como error
		// TODO Crear un parametro de sistema para validar o no CI
		mtsTransferManager.processPayment(request.getPayment());
		// TODO Si no, retornar
		MTSInterfaceToTransferProcessMessage mtp = new MTSInterfaceToTransferProcessMessage();
		mtp.setPayment(request.getPayment());
		if (!this.sendResponseTransferProcess(mtp)) {
			log.error("Fallo al envio de mensaje al TransferProcess-->" + mtp);
		}
	}

	@SuppressWarnings("static-access")
	public Boolean isAgentPayemnt(long idcompanyPk,
			String idCompanyPaymentForAgent) {
		Boolean res = false;
		String idcompany = "";
		idcompany = idcompany.valueOf(idcompanyPk);
		String[] idCompanies = idCompanyPaymentForAgent.split(",");

		for (String id : idCompanies) {
			if (id.equals(idcompany)) {
				res = true;
			}
		}

		return res;
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
			// Levantar la cola para Updater.
			if (queueTransferProcess == null)
				queueTransferProcess = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.TRANSFER_PROCESS_QUEUE);

			if (!queueTransferProcess.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueTransferProcess.toString() + ", init="
						+ queueTransferProcess.init());
		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	private boolean sendResponseTransferProcess(
			MTSInterfaceToTransferProcessMessage tpu) {
		boolean ready = false;
		if (queueTransferProcess != null && queueTransferProcess.isConnected()) {
			log.trace("Mensaje a TransferProcess --> " + tpu);
			if (!(ready = queueTransferProcess.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.TRANSFER_PROCESS_QUEUE);
				try {
					queueTransferProcess.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.TRANSFER_PROCESS_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.TRANSFER_PROCESS_QUEUE);
		}
		return ready;
	}

	@PreDestroy
	public void close() {
		try {

			if (queueTransferProcess != null) {
				queueTransferProcess.close();
			}

		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
