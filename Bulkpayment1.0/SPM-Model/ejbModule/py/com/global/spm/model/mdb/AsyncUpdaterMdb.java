package py.com.global.spm.model.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logmts;
import py.com.global.spm.entities.Logmtsrev;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Logreversion;
import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.entities.Smslogmessage;
import py.com.global.spm.model.interfaces.LogmtsManagerLocal;
import py.com.global.spm.model.interfaces.LogmtsrevManagerLocal;
import py.com.global.spm.model.interfaces.LogpaymentManagerLocal;
import py.com.global.spm.model.interfaces.LogreversionManagerLocal;
import py.com.global.spm.model.interfaces.ProcessControlManagerLocal;
import py.com.global.spm.model.interfaces.SmslogmessageManagerLocal;
import py.com.global.spm.model.util.SpmQueues;

/**
 * Message-Driven Bean implementation class for: AsyncUpdater
 * 
 * @author R2
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.ASYNC_UPDATER_QUEUE) })
public class AsyncUpdaterMdb implements MessageListener {

	@EJB
	private LogmtsManagerLocal logmtsManager;

	@EJB
	private LogmtsrevManagerLocal logmtsrevManager;

	@EJB
	private LogreversionManagerLocal logreversionManager;

	@EJB
	private LogpaymentManagerLocal logpaymentManager;

	@EJB
	private ProcessControlManagerLocal processControlManager;

	@EJB
	private SmslogmessageManagerLocal smslogmessageManager;

	private Logger log = Logger.getLogger(AsyncUpdaterMdb.class);

	/**
	 * Default constructor.
	 */
	public AsyncUpdaterMdb() {

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
					if (request != null && request instanceof Logmts) {
						logmtsManager.updateLogmts((Logmts) request);
					} else if (request != null && request instanceof Logmtsrev) {
						logmtsrevManager.insertLogmtsrev((Logmtsrev) request);
					} else if (request != null && request instanceof Logpayment) {
						if (!logpaymentManager
								.updateLogpayment((Logpayment) request)) {
							log.warn("UPS!!! No se actualizo el logpayment -->"
									+ ((Logpayment) request));
						}
					} else if (request != null
							&& request instanceof Logreversion) {
						logreversionManager
								.updateLogreversion((Logreversion) request);
					} else if (request != null
							&& request instanceof Smslogmessage) {
						smslogmessageManager
								.insertSmslogmessage((Smslogmessage) request);
					} else if (request != null
							&& request instanceof Processcontrol) {
						if (!processControlManager
								.updateProcessControl((Processcontrol) request)) {
							log.warn("Error al actualizar processcontrol"
									+ ((Processcontrol) request));
						}
					} else {
						log.error("Message error --> queue="
								+ SpmQueues.ASYNC_UPDATER_QUEUE
								+ ", message="
								+ (request != null ? request.toString()
										: "NULL"));
					}
				} catch (JMSException e) {
					log.error("Reading message --> queue="
							+ SpmQueues.ASYNC_UPDATER_QUEUE, e);
				}
			}
		} catch (Exception e) {
			log.error("Reading queue --> queue="
					+ SpmQueues.ASYNC_UPDATER_QUEUE, e);
			return;
		}
	}

}
