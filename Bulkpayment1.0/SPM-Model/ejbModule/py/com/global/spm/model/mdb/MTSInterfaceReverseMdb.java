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

import py.com.global.spm.model.interfaces.MTSReversionInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.MTSReversionToReversionProcessMessage;
import py.com.global.spm.model.messages.ReversionProcessToMTSReversionMessage;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.SpmQueues;

/**
 * El MTSInterfaceReverseMDB recibe el mensaje del ReverseProcess y le
 * pasa al manager para procesar la reversion del pago.
 * 
 * @author R2
 * */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.MTS_REVERSION_INTERFACE_QUEUE) })
public class MTSInterfaceReverseMdb implements MessageListener {

	private static final Logger log = Logger
			.getLogger(MTSInterfaceReverseMdb.class);
	private JMSSender queueReversionProcess;
	

	// Recursos del contenedor
	private InitialContext initialContext = null;

	@EJB
	SystemparameterManagerLocal systemParameterManager;
	
	@EJB
	MTSReversionInterfaceManagerLocal mtsReverseManager;

	public MTSInterfaceReverseMdb() {

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
						+ SpmQueues.MTS_REVERSION_INTERFACE_QUEUE + ", msg="
						+ e.getMessage());
				msg = null;
			}

			if (msg != null) {
				// Procesar una solicitud del cobro del valorizador
				if (msg instanceof ReversionProcessToMTSReversionMessage) {
					log.debug("Recibiendo mensaje desde ReversionProcess");
					ReversionProcessToMTSReversionMessage request = (ReversionProcessToMTSReversionMessage) msg;
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

	private void processRequest(ReversionProcessToMTSReversionMessage request) {
		mtsReverseManager.processPayment(request.getPayment());
		MTSReversionToReversionProcessMessage mtp=new MTSReversionToReversionProcessMessage();
		mtp.setPayment(request.getPayment());
		if(!this.sendResponseReversionProcess(mtp)){
			log.error("Fallo al envio de mensaje al ReversionProcess-->"+mtp);
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
			log.error("Error al inicializar MTSInterfaceReverse->" + e.getMessage());
		}
	}

	private void initDtq() {

		try {
			// Levantar la cola para Updater.
			if (queueReversionProcess == null)
				queueReversionProcess = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.REVERSION_PROCESS_QUEUE);

			if (!queueReversionProcess.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueReversionProcess.toString() + ", init="
						+ queueReversionProcess.init());
		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	private boolean sendResponseReversionProcess(MTSReversionToReversionProcessMessage mtp) {
		boolean ready = false;
		if (queueReversionProcess != null && queueReversionProcess.isConnected()) {
			log.trace("Mensaje a ReversionProcess --> " + mtp);
			if (!(ready = queueReversionProcess.send(mtp))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.REVERSION_PROCESS_QUEUE);
				try {
					queueReversionProcess.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.REVERSION_PROCESS_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.REVERSION_PROCESS_QUEUE);
		}
		return ready;
	}

	

	@PreDestroy
	public void close() {
		try {
			
			if (queueReversionProcess != null) {
				queueReversionProcess.close();
			}

		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
