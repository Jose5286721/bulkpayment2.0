package py.com.global.spm.model.mdb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;


import py.com.global.spm.model.interfaces.NotificationManagerLocal;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.SPMGUIToNotificationManager;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.util.SpmUtil;

import java.util.Arrays;

/**
 * Message-Driven Bean implementation class for: NotificationMdb
 */
//@ActivationConfigProperty(propertyName = "connectionParameters",propertyValue = "httpUpgradeEnabled=true")
//@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
@MessageDriven(name = "spmNotificationQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionParameters",propertyValue = "httpUpgradeEnabled=true"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.NOTIFICATION_QUEUE)})
public class NotificationMdb implements MessageListener {
	/*
	 * Se encarga de recibir el mensaje. Luego invoca al EJB NotificationManager
	 */

	/**
	 * Managers
	 */
	@EJB
	NotificationManagerLocal notificationManager;
	private JMSSender queueSender = null;

	private final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(NotificationMdb.class);

	/**
	 * Default constructor.
	 */
	public NotificationMdb() {
	}

	@PostConstruct
	public void initialize() {
		log.info("Inicializando MDB-->>");
		try {
			if (queueSender == null) {
				//queueSender = new JMSSender(null,null,SpmQueues.NOTIFICATION_QUEUE);
				//queueSender.init();
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
							&& request instanceof NotificationRequestMessage) {
						// TODO: VERIFICAR

						NotificationRequestMessage reqMessage = (NotificationRequestMessage) request;
						log.info("reqMessage-->"+ reqMessage.toString()+" Destinatarios: "+ Arrays.toString(reqMessage.getIdDestinatarios()));
						Long init = System.currentTimeMillis();
						Long end = null;
						log.debug("Processing request message --> "
								+ request.toString());

						notificationManager.processRequest(reqMessage);

						end = System.currentTimeMillis();
						log.trace("processTime="
								+ SpmUtil.millisToSecondStr(end - init)
								+ "secs, " + reqMessage);
					} else if (request != null
							&& request instanceof SPMGUIToNotificationManager) {
						SPMGUIToNotificationManager reqMessage = (SPMGUIToNotificationManager) request;

						Long init = System.currentTimeMillis();
						Long end = null;
						log.debug("Processing request message --> "
								+ request.toString());

						notificationManager.processRequest(reqMessage);

						end = System.currentTimeMillis();
						log.trace("processTime="
								+ SpmUtil.millisToSecondStr(end - init)
								+ "secs, " + reqMessage);
					}

					else {
						log.error("Message error --> queue="
								+ SpmQueues.NOTIFICATION_QUEUE
								+ ", message="
								+ (request != null ? request.toString()
										: "NULL"));
					}
				} catch (JMSException e) {
					log.error("Reading message --> queue="
							+ SpmQueues.NOTIFICATION_QUEUE, e);
				}
			}
		} catch (Exception e) {
			log.error(
					"Reading queue --> queue=" + SpmQueues.NOTIFICATION_QUEUE,
					e);
			return;
		}
	}

	@PreDestroy
	public void closeDtq() {
		try {
			if(queueSender!=null)
			 	queueSender.close();
		} catch (Exception e) {
			log.error("Closing connection to queue error --> queue="
					+ SpmQueues.NOTIFICATION_QUEUE, e);
		}
	}

}
