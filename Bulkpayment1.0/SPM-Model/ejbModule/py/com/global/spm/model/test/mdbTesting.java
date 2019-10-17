package py.com.global.spm.model.test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Use;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.model.messages.SPMGUIToReversionProcessMessage;
import py.com.global.spm.model.messages.SPMGUIToTransferProcessMessage;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.SpmQueues;

/**
 * Session Bean implementation class mdbTesting
 */
@WebService(targetNamespace = "urn:gspmws.spm.global.com.py", name = "mdbTesting")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = Use.LITERAL)
@Stateless
public class mdbTesting implements mdbTestingLocal {

	private JMSSender queueReversionRequest;
	private JMSSender queueTransferRequest;
	private InitialContext initialContext;
	public static final Logger log = Logger.getLogger(mdbTesting.class);

	/**
	 * Default constructor.
	 */
	public mdbTesting() {
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
			log.error("Error al inicializar mdbTesting->" + e.getMessage());
		}
	}

	@WebMethod
	public boolean sendReversion(Long idPaymentOrder) {
		SPMGUIToReversionProcessMessage srp = new SPMGUIToReversionProcessMessage();
		srp.setIdpaymentorderPk(idPaymentOrder);
		return this.sendReversion(srp);
	}

	@WebMethod
	public boolean sendRetry(Long idPaymentOrder) {
		SPMGUIToTransferProcessMessage srp = new SPMGUIToTransferProcessMessage();
		srp.setIdpaymentorderPk(idPaymentOrder);
		return this.sendRetry(srp);
	}

	private void initDtq() {
		try {
			// Levantar la cola para AsyncUpdater.
			if (queueReversionRequest == null)
				queueReversionRequest = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.REVERSION_PROCESS_QUEUE);

			if (!queueReversionRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueReversionRequest.toString() + ", init="
						+ queueReversionRequest.init());

			// Levantar la cola para AsyncUpdater.
			if (queueTransferRequest == null)
				queueTransferRequest = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.TRANSFER_PROCESS_QUEUE);

			if (!queueTransferRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueTransferRequest.toString() + ", init="
						+ queueTransferRequest.init());

		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	private boolean sendReversion(SPMGUIToReversionProcessMessage logrev) {
		boolean ready = false;
		if (queueReversionRequest != null
				&& queueReversionRequest.isConnected()) {
			log.trace("Mensaje a ReversionProcess --> " + logrev);
			if (!(ready = queueReversionRequest.send(logrev))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.REVERSION_PROCESS_QUEUE);
				try {
					queueReversionRequest.close();
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
	
	private boolean sendRetry(SPMGUIToTransferProcessMessage logrev) {
		boolean ready = false;
		if (queueTransferRequest != null
				&& queueTransferRequest.isConnected()) {
			log.trace("Mensaje a TransferProcess --> " + logrev);
			if (!(ready = queueTransferRequest.send(logrev))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.TRANSFER_PROCESS_QUEUE);
				try {
					queueTransferRequest.close();
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
			if (queueReversionRequest != null) {
				queueReversionRequest.close();
			}
			
			if (queueTransferRequest != null) {
				queueTransferRequest.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
