package py.com.global.smsmanager.process;

import ie.omk.smpp.util.GSMConstants;

import java.sql.Timestamp;
import java.util.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import py.com.global.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.model.interfaces.PrefixManagerLocal;
import py.com.global.model.interfaces.SystemparameterManagerLocal;
import py.com.global.model.interfaces.UserManagerLocal;
import py.com.global.model.systemparameters.SMSManagerParameters;
import py.com.global.model.util.*;
import py.com.global.smsmanager.message.DeliverSMWrapper;
import py.com.global.smsmanager.smpp.SmppReceiver;
import py.com.global.smsmanager.util.SpmMessageParser;
import py.com.global.spm.entities.*;
import py.com.global.spm.model.eventcodes.SMSManagerEventCodes;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.SMSMangerToFlowManagerMessage;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SMSMessageReceiver extends Thread implements MessageListener {

	public static final int INACTIVITY = 300; // default 5 min

	// Contexto para lectura de parametros
	private InitialContext initialContext = null;


	private PaymentOrderManagerLocal paymentOrderManager;
	private PrefixManagerLocal prefixManager;
	private SystemparameterManagerLocal systemParameterManager;
	private UserManagerLocal userManager;

	// SMPP Receivers
	private Hashtable<String, SmppReceiver> receivers = null;

	// Atributos principales
	private Long processId = null; // idProcess
	private String processName = null; // Nombre del proceso
	private int inactivity = 0; // Tiempo maximo de inactividad de una conexion
	private String systemNumber = null;
	private Processcontrol processControl = null;
	private long lastKeepAliveTime;
	private final long defaultKeepAliveTime = 120000L;// 120 segs
	private long timeLastConnectParametersLoad = 0; // Timestamp en que se
													// recargo por ultima vez el
													// parametro de
													// conectar si/no

	// Data Queues
	private JMSSender flowManagerQueueSender = null;
	private JMSSender queueAsyncUpdater = null;
	private JMSSender notificationManagerQueueSender = null;
	private JMSReceiver queueMessageReceived = null;

	private boolean running = false; // Indicador de proceso iniciado
	private boolean exit = false;
	private Date lastUpdate;
	protected boolean isSMSCDummy = false;

	private final Logger log = Logger.getLogger(SMSMessageReceiver.class);


	public SMSMessageReceiver(Long processId, String processName,
			PaymentOrderManagerLocal paymentOrderManager,
			PrefixManagerLocal prefixManager, Processcontrol processControl,
			SystemparameterManagerLocal systemParameterManager,
			UserManagerLocal userManager) {
		super();
		this.processId = processId;
		this.processName = processName;
		this.paymentOrderManager = paymentOrderManager;
		this.prefixManager = prefixManager;
		this.processControl = processControl;
		this.systemParameterManager = systemParameterManager;
		this.userManager = userManager;
		log.debug("Constructing... --> processId=" + processId
				+ ", processName=" + processName);
	}

	/**
	 * Leer los parametros del proceso
	 * 
	 * @return false si no se pudieron cargar los parametros. True en caso
	 *         contrario.
	 */
	public boolean loadParameters() {
		log.info("Loading parameters... --> processId=" + processId
				+ ", processName=" + processName);
		exit = true;
		this.init();
		// Leer parametros desde la base de datos
		if (systemParameterManager != null && processName != null) {

			// Cargar el tiempo de inactividad
			inactivity = systemParameterManager.getParameterValue(processId,
					"Inactivity", INACTIVITY);
			inactivity = ((inactivity > 0) ? inactivity : 0);
			log.info("Tiempo maximo de inactividad = " + inactivity + " seg");
			inactivity *= 1000;

			this.loadServersParameters();

			exit = processId == null || receivers.isEmpty();
			if (!exit) {
				log.debug("Parameters successful loaded");
			}
		}
		return !exit;
	}

	private void loadServersParameters() {
		String server;
		String carrier;
		SmppReceiver smppReceiver;

		receivers = new Hashtable<String, SmppReceiver>();
		// Cargar la direccion del SMSC server
		server = systemParameterManager
				.getParameter(SMSManagerParameters.SMSC_ADDRESS);
		// Cargar la operadora
		carrier = systemParameterManager
				.getParameter(SMSManagerParameters.CARRIER);
		if (server == null || server.trim().length() == 0) {
			log.error("Direccion IP y puerto del SMSC Requeridos");
			return;
		} else if (carrier == null || carrier.trim().length() == 0) {
			log.info("Operadora requerido");
			return;
		} else {
			carrier = carrier.trim().toUpperCase();
			log.info("Direccion IP y puerto del SMSC = " + server
					+ ", Operadora = " + carrier);
			// Instanciar sms sender
			smppReceiver = new SmppReceiver(server, 0);

			// Carrier
			smppReceiver.setCarrier(carrier);

			// Cargar el SystemNumber
			smppReceiver.setSystemNumber(systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_NUMBER));
			systemNumber = smppReceiver.getSystemNumber();
			log.info("System Number = " + smppReceiver.getSystemNumber());

			// Cargar el SystemID
			smppReceiver.setSystemID(systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_ID));
			log.info("System ID = " + smppReceiver.getSystemID());

			// Cargar Address Range
			smppReceiver.setAddrRange(systemParameterManager
					.getParameter(SMSManagerParameters.ADDR_RANGE));
			log.info("Address Range = " + smppReceiver.getAddrRange());

			// Cargar el Password
			smppReceiver.setPassword(systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_PASSWORD));
			log.info("System Password = " + smppReceiver.getPassword());

			// Cargar el SystemType
			smppReceiver.setSystemType(systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_TYPE));
			log.info("System Type = " + smppReceiver.getSystemType());

			// Cargar el ServiceType
			// NO SE UTILIZA
			// smppReceiver.setServiceType(systemParameterManager
			// .getParameter(SMSManagerParameters.SERVICE_TYPE));
			// log.info("Service Type = " + smppReceiver.getServiceType());

			// Cargar el TON
			String sourceTONStr = systemParameterManager
					.getParameter(SMSManagerParameters.SOURCE_TON);
			int sourceTON = -1;
			if (sourceTONStr != null) {
				try {
					sourceTON = Integer.parseInt(sourceTONStr);
				} catch (Exception e) {
					log.error("Invaid sourceTON --> " + sourceTONStr, e);
				}
			}
			smppReceiver.setSourceTON(sourceTON > 0 ? sourceTON
					: GSMConstants.GSM_TON_UNKNOWN);
			log.info("Source TON = " + smppReceiver.getSourceTON());

			// Cargar el Source NPI
			String sourceNPIStr = systemParameterManager
					.getParameter(SMSManagerParameters.SOURCE_NPI);
			int sourceNPI = -1;
			if (sourceNPIStr != null) {
				try {
					sourceNPI = Integer.parseInt(sourceTONStr);
				} catch (Exception e) {
					log.error("Invaid sourceNPI --> " + sourceNPIStr, e);
				}
			}
			smppReceiver.setSourceNPI(sourceNPI > 0 ? sourceNPI
					: GSMConstants.GSM_NPI_UNKNOWN);
			log.info("Source NPI = " + smppReceiver.getSourceNPI());

			// Cargar el TON
			String systemTONStr = systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_TON);
			int systemTON = -1;
			if (systemTONStr != null) {
				try {
					systemTON = Integer.parseInt(systemTONStr);
				} catch (Exception e) {
					log.error("Invaid systemTON --> " + systemTONStr, e);
				}
			}
			smppReceiver.setTargetTON(systemTON > 0 ? systemTON
					: GSMConstants.GSM_TON_UNKNOWN);
			log.info("System TON = " + smppReceiver.getTargetTON());

			// Cargar el Target NPI
			String targetNPIStr = systemParameterManager
					.getParameter(SMSManagerParameters.SYSTEM_NPI);
			int targetNPI = -1;
			if (targetNPIStr != null) {
				try {
					targetNPI = Integer.parseInt(targetNPIStr);
				} catch (Exception e) {
					log.error("Invaid targetNPI --> " + targetNPIStr, e);
				}
			}
			smppReceiver.setTargetNPI(targetNPI > 0 ? targetNPI
					: GSMConstants.GSM_NPI_UNKNOWN);
			log.info("System NPI = " + smppReceiver.getTargetNPI());

			// connect = 1 true
			// connect != 1 false
			String connect = systemParameterManager
					.getParameter(SMSManagerParameters.CONNECT);
			int connectNum = -1;
			if (connect != null) {
				try {
					connectNum = Integer.parseInt(connect);
				} catch (Exception e) {
					log.error("Invalid connect value --> " + connect, e);
				}
			}
			smppReceiver.setConnect(connectNum == 1);
			this.timeLastConnectParametersLoad = System.currentTimeMillis();
			log.info("Connect = " + smppReceiver.isConnect());

			String isSMSCDummyStr = systemParameterManager
					.getParameter(SMSManagerParameters.SMSC_DUMMY);
			isSMSCDummy = isSMSCDummyStr.compareToIgnoreCase(SpmConstants.YES) == 0;
			log.info("isSMSCDummy=" + isSMSCDummy);

			smppReceiver.setIndex(0);

			if (smppReceiver.isAttributesEstablished()) {
				if (!receivers.contains(smppReceiver.getCarrier())) {
					receivers.put(smppReceiver.getCarrier(), smppReceiver);
					log.info("SMPPReceiver registered --> "
							+ smppReceiver.toString());
				} else {
					log.error("SMPPReceiver already registered --> "
							+ smppReceiver.toString());
				}
			} else {
				log.error("SMPPReceiver NOT registered, incomplete parameters --> "
						+ smppReceiver.toString());
			}
		}
	}

	private void reloadConnectParameter() {
		SmppReceiver smppReceiver = null;
		Enumeration<SmppReceiver> receiversEnum = receivers.elements();
		// Cargar el tiempo de inactividad
		inactivity = systemParameterManager.getParameterValue(processId,
				"Inactivity", INACTIVITY);
		inactivity = ((inactivity > 0) ? inactivity : 0);
		log.info("Tiempo maximo de inactividad = " + inactivity + " seg");
		inactivity *= 1000;
		while (receiversEnum.hasMoreElements()) {
			smppReceiver = receiversEnum.nextElement();
			String connect = systemParameterManager
					.getParameter(SMSManagerParameters.CONNECT);
			int connectNum = -1;
			if (connect != null) {
				try {
					connectNum = Integer.parseInt(connect);
				} catch (Exception e) {
					log.error("Invalid connect value --> " + connect, e);
					return;
				}
			}
			if ((connectNum == 1) != smppReceiver.isConnect()) {
				log.info("Connecting param changed " + smppReceiver.toString()
						+ " --> oldValue= " + smppReceiver.isConnect()
						+ ", newValue=" + (connectNum == 1));
				smppReceiver.setConnect(connectNum == 1);
			}
			String isSMSCDummyStr = systemParameterManager
					.getParameter(SMSManagerParameters.SMSC_DUMMY);
			if ((isSMSCDummyStr.compareToIgnoreCase(SpmConstants.YES) == 0) != isSMSCDummy) {
				isSMSCDummy = isSMSCDummyStr
						.compareToIgnoreCase(SpmConstants.YES) == 0;
				log.info("isSMSCDummy=" + isSMSCDummy);
			}
			this.timeLastConnectParametersLoad = System.currentTimeMillis();
		}
	}

	/**
	 * Iniciar estructuras
	 * 
	 * @return
	 */
	private void init() {
		// Inicializar el contexto para utilizar los recurso del contenedor
		if (initialContext == null) {
			try {
				log.info("Iniciando el contexto...");
				initialContext = new InitialContext();
			} catch (NamingException e) {
				log.error("Fallo al cargar el contexto del Contenedor", e);
			}
		}
	}

	/**
	 * Indica si el proceso esta en ejecucion en background
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Salir de forma ordenada
	 */
	protected void exit() {
		// Indicar salida ordenada
		exit = true;

		if (running) {
			close();
		} else {
			log.debug("Proceso corriendo en background, close() no ejecutado");
		}
	}

	/**
	 * Cerrar atributos y recursos
	 */
	private void close() {
		// Indicar salida del proceso
		log.debug("Cerrando el proceso y conexiones --> proceso=" + processName);
		exit = true;

		// Cerrar conexion con el SMSC
		SmppReceiver smppReceiver = null;
		Enumeration<SmppReceiver> receiversEnum = receivers.elements();
		while (receiversEnum.hasMoreElements()) {
			smppReceiver = (SmppReceiver) receiversEnum.nextElement();
			if (smppReceiver != null) {
				log.info("Closing connection --> " + smppReceiver.toString());
				smppReceiver.close();
			}
		}

		try {
			if (queueMessageReceived != null) {
				queueMessageReceived.close();
			}
			if (flowManagerQueueSender != null) {
				flowManagerQueueSender.close();
			}
			if (notificationManagerQueueSender != null) {
				notificationManagerQueueSender.close();
			}
			if (queueAsyncUpdater != null) {
				queueAsyncUpdater.close();
			}
		} catch (Exception e) {
			log.error("Error closing queue", e);
		}

	}

	/**
	 * Procesar las solicitudes de envio
	 */
	public void onMessage(Message message) {
		// Leer el mensaje
		try {
			log.debug("Leyendo mensaje...");
			if (message != null && !message.getJMSRedelivered()
					&& message instanceof ObjectMessage) {
				try {
					Object msg = ((ObjectMessage) message).getObject();
					if (msg != null) {
						// Procesar mensaje recibido
						if (msg instanceof DeliverSMWrapper) {
							log.debug("Recibiendo mensaje desde la cola de datos --> cola="
									+ queueMessageReceived);
							this.processPacketReceived((DeliverSMWrapper) msg);
						}
					}
				} catch (JMSException e) {
					log.error("Al leer un mensaje de la cola --> cola="
							+ queueMessageReceived + ", error=" + e.getCause());
				}
			} else {
				log.error("Error leyendo mensaje...");
			}
		} catch (Exception e) {
			log.error("Fallo al procesar mensaje --> error=" + e.getCause());
			e.printStackTrace();
			return;
		}
	}

	private void processPacketReceived(DeliverSMWrapper pak) {
		log.debug("deliver_sm: " + Integer.toString(pak.getSequenceNum())
				+ ": \"" + pak.getMessageText() + "\"");
		if (isSMSCDummy) {
			pak.setSource(systemParameterManager
					.getParameter(SMSManagerParameters.SOURCE_NUMBER));
			pak.setDestination(systemParameterManager
					.getParameter(SMSManagerParameters.DEST_NUMBER));
		}
		SMSMangerToFlowManagerMessage message = this
				.getSMSMangerToFlowManagerMessage(pak);
		if (message.getEvent().sonIguales(SMSManagerEventCodes.SUCCESS)) {
			flowManagerQueueSender.send(message);
		} else {
			NotificationRequestMessage notificationRequest = this
					.getNotificationRequestMessage(pak.getSource(),
							message.getEvent());
			notificationManagerQueueSender.send(notificationRequest);

		}
		Smslogmessage logMessage = this.getSmsLogMessage(message, pak);
		log.debug("Sending --> " + logMessage);
		queueAsyncUpdater.send(logMessage);
	}

	/**
	 * Verificar conexion
	 */
	private void checkConnection() {
		SmppReceiver smppReceiver = null;
		Enumeration<SmppReceiver> receiversEnum = receivers.elements();
		log.debug("Receibers count=" + receivers.size());
		while (receiversEnum.hasMoreElements()) {
			smppReceiver = receiversEnum.nextElement();
			if (smppReceiver != null) {
				log.debug("Verifying receiver --> " + smppReceiver.toString());
				if (!smppReceiver.isConnected() && smppReceiver.isConnect()) {
					// conectar
					try {
						// Iniciar conexion con el SMPP server
						log.info("Connecting.. --> " + smppReceiver.toString());
						synchronized (this) {
							if (!smppReceiver.Connect()) {
								log.error("Binding req error... --> "
										+ smppReceiver.toString());
							}
						}
						// this.close();
					} catch (Exception e) {
						log.error("Error --> " + e.getCause(), e);
					}
				} else if (smppReceiver.isConnected()
						&& smppReceiver.isConnect()) {
					// Verificar tiempo de inactividad
					long currentTime = System.currentTimeMillis();
					if ((inactivity > 0)
							&& ((currentTime - smppReceiver
									.getLastActivityTime()) > inactivity)) {
						log.warn("Cerrando conexion al SMSC por inactividad... --> carrier="
								+ smppReceiver.getCarrier());
						smppReceiver.close();
					}
				} else if (smppReceiver.isConnected()
						&& !smppReceiver.isConnect()) {
					// desconectar
					log.warn("Cerrando conexion al SMSC --> connect = false, "
							+ smppReceiver.toString());
					smppReceiver.close();
				} else {
					// !smppReceiver.isConnected() && !smppReceiver.isConnect()
					// no hacer nada
				}
				smppReceiver.checkQueues();
			}
		}
	}

	public void closeThread() {
		log.info("Stoping SMSMessageReceiver");
		exit = true;
	}

	/**
	 * Verificar el estado de conexion de las colas de datos
	 */
	private void checkQueues() {
		try {
			// Iniciar cola de datos de mensajes recibidos
			if (queueMessageReceived == null) {
				queueMessageReceived = new JMSReceiver(initialContext,
						JMSSender.JBOSS_CONNECTION_FACTORY,
						SpmQueues.SMS_RECEIVED_QUEUE);
				queueMessageReceived.setListener(this);
			}
			if (!queueMessageReceived.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ queueMessageReceived.toString() + ", init="
						+ queueMessageReceived.init());
			}
		} catch (Exception e) {
			log.error("Iniciando cola --> queue" + SpmQueues.SMS_RECEIVED_QUEUE);
		}

		try {
			// Inicializar cola de flow manager
			if (flowManagerQueueSender == null) {
				flowManagerQueueSender = new JMSSender(initialContext,
						JMSSender.JBOSS_CONNECTION_FACTORY,
						SpmQueues.FLOW_MANAGER_QUEUE);
			}
			if (!flowManagerQueueSender.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ flowManagerQueueSender.toString() + ", init="
						+ flowManagerQueueSender.init());
			}
		} catch (Exception e) {
			log.error("Iniciando cola --> queue" + SpmQueues.FLOW_MANAGER_QUEUE);
		}

		try {
			// Iniciar cola de datos de envio de mensajes de requests invalidos
			if (notificationManagerQueueSender == null) {
				notificationManagerQueueSender = new JMSSender(initialContext,
						JMSSender.JBOSS_CONNECTION_FACTORY,
						SpmQueues.NOTIFICATION_QUEUE);
			}
			if (!notificationManagerQueueSender.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ notificationManagerQueueSender.toString() + ", init="
						+ notificationManagerQueueSender.init());
			}
		} catch (Exception e) {
			log.error("Iniciando cola --> queue" + SpmQueues.NOTIFICATION_QUEUE);
		}

		try {
			// Iniciar cola de datos de envio de smslogMessages para el registro
			if (queueAsyncUpdater == null) {
				queueAsyncUpdater = new JMSSender(initialContext,
						JMSSender.JBOSS_CONNECTION_FACTORY,
						SpmQueues.ASYNC_UPDATER_QUEUE);
			}
			if (!queueAsyncUpdater.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ queueAsyncUpdater.toString() + ", init="
						+ queueAsyncUpdater.init());
			}
		} catch (Exception e) {
			log.error("Iniciando cola --> queue"
					+ SpmQueues.ASYNC_UPDATER_QUEUE);
		}
	}


	private void updateProcessControl() {
		long now = System.currentTimeMillis();
		if (now - lastKeepAliveTime > defaultKeepAliveTime) {
			processControl.setChangedateTim(new Timestamp(now));
			queueAsyncUpdater.send(processControl);
			lastKeepAliveTime = now;
		}
	}

	/**
	 * Ejecutar el proceso en background
	 */
	public void run() {

		if (!running) {
			log.debug("Iniciando proceso en background...");
			running = true;
			lastKeepAliveTime = System.currentTimeMillis();
			while (!exit) {
				try {
					lastUpdate = new Date();
					// Verificar conexion al SMPP
					checkConnection();
					// Verificar colas de datos
					checkQueues();
					// Esperar 1 segundo
					synchronized (this) {
						SpmUtil.sleep(1000);
					}
					if (System.currentTimeMillis()
							- timeLastConnectParametersLoad > 120000) {
						this.reloadConnectParameter();
					}
					this.updateProcessControl();
				} catch (Exception e) {
					log.error("Process error --> " + e.getCause(), e);
					exit = true;
				}
			}
			// Cerrando estructuras
			close();
			exit = false;
			running = false;
			log.debug("Finalizando proceso en background...");
		}
	}

	private SMSMangerToFlowManagerMessage getSMSMangerToFlowManagerMessage(
			DeliverSMWrapper pak) {
		SMSMangerToFlowManagerMessage receipt = new SMSMangerToFlowManagerMessage();
		// validar si el sms es para nosotros
		if (systemNumber.compareToIgnoreCase(pak.getDestination()) != 0) {
			log.error("Discarding msg by unknow destination number --> systemNumber="
					+ systemNumber + ", " + pak.toString());
			receipt.setEvent(SMSManagerEventCodes.UNKNOW_DESTINATION);
			return receipt;
		}
		// prefix user
		String msisdnPrefixed = prefixManager.prefixSubscriber(pak.getSource());
		if (msisdnPrefixed == null) {
			log.error("Discarding msg by unknow user prefix --> "
					+ pak.toString());
			receipt.setEvent(SMSManagerEventCodes.UNKNOW_USER_PREFIX);
			return receipt;
		}
		// validar si existe el usuario
		List<User> usersList = userManager.getUsersByMisdn(msisdnPrefixed);
		if (usersList == null || usersList.size() == 0) {
			log.error("Unknow user --> " + pak.toString());
			receipt.setIduser(-1);
			receipt.setEvent(SMSManagerEventCodes.UNKNOW_USER);
			return receipt;
		}
		// message parse
		SMSManagerEventCodes parseResult = SpmMessageParser
				.parsePaymentOrderAndPin(receipt, pak.getMessageText());
		if (!parseResult.sonIguales(SMSManagerEventCodes.SUCCESS)) {
			log.error("Error parsing message --> " + parseResult.toString()
					+ ", message text=[" + pak.getMessageText() + "]");
			receipt.setEvent(parseResult);
			return receipt;
		} else {
			log.debug("Message parsed! --> " + receipt.toString());
		}
		// validar si existe orden de pago
		Paymentorder paymentOrder = paymentOrderManager.getPaymentorder(receipt
				.getIdpaymentorder());
		if (paymentOrder == null) {
			log.error("Paymentorder not found --> " + receipt.toString());
			receipt.setEvent(SMSManagerEventCodes.PAYMENT_ORDER_NOT_FOUND);
			return receipt;
		}
		User user = null;
		for (User uAux : usersList) {
			for(Company c: uAux.getIdcompanyPk()) {
				if (c.getIdcompanyPk() == paymentOrder.getIdcompanyPk()) {
					user = uAux;
					break;
				}
			}
		}
		if (user == null) {
			log.error("Usuario no encontrado para orden de pago --> msisdn="
					+ msisdnPrefixed + ", " + paymentOrder);
			receipt.setIduser(-1);
			receipt.setEvent(SMSManagerEventCodes.UNKNOW_USER);
			return receipt;
		}
		receipt.setIduser(user.getIduserPk());
		//TODO Setear la compañia logueada,  receipt.setIdcompanyPk(user.getIdcompanyPk());
		receipt.setReceiptDate(new Date());

		receipt.setEvent(SMSManagerEventCodes.SUCCESS);
		return receipt;
	}

	private NotificationRequestMessage getNotificationRequestMessage(
			String userPhoneNumber, SMSManagerEventCodes event) {
		NotificationRequestMessage message = new NotificationRequestMessage();
		message.setUserPhoneNumber(userPhoneNumber);
		message.setEvent(SMSManagerEventCodes.parseMessage(event));
		return message;
	}

	private Smslogmessage getSmsLogMessage(
			SMSMangerToFlowManagerMessage message, DeliverSMWrapper pak) {
		Smslogmessage logMessage = new Smslogmessage();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		logMessage.setChangedateTim(now);
		logMessage.setCreationdateTim(now);
		logMessage.setDestinynumberChr(pak.getDestination());
		logMessage.setSourcenumberChr(pak.getSource());
		logMessage.setMessageChr(pak.getMessageText());
		if (message.getIduser() > 0) {
			logMessage.setIduserPk(message.getIduser());
			logMessage.setIdcompanyPk(message.getIdcompanyPk());
		}
		if (message.getEvent().sonIguales(SMSManagerEventCodes.SUCCESS)) {
			logMessage.setStateChr(SpmConstants.SUCCESS);
		} else {
			logMessage.setStateChr(SpmConstants.ERROR);
		}
		logMessage.setTypeChr(SpmConstants.RECEIPT);
		logMessage.setIdexternalmessageNum((long) pak.getSequenceNum());
		logMessage.setIdprocessPk(SpmProcesses.SMS_MANAGER);
		logMessage.setIdeventcodeNum(message.getEvent().getIdEventCode());
		return logMessage;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

}
