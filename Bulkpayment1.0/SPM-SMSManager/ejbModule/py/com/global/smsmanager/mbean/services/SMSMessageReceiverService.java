package py.com.global.smsmanager.mbean.services;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

import py.com.global.model.interfaces.*;
import py.com.global.model.mbean.interfaces.MBeanControl;
import py.com.global.model.util.SpmConstants;
import py.com.global.model.util.SpmProcesses;
import py.com.global.model.util.SpmUtil;
import py.com.global.smsmanager.process.SMSMessageReceiver;
import py.com.global.smsmanager.util.BooleanValueEnum;
import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.entities.ProcesscontrolPK;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Singleton
@Startup
public class SMSMessageReceiverService implements MBeanControl{


	@EJB
	private PaymentOrderManagerLocal paymentOrderManager;

	@EJB
	private PrefixManagerLocal prefixManager;

	@EJB
	private ProcessControlManagerLocal processControlManager;

	@EJB
	private SystemparameterManagerLocal systemParameterManager;

	@EJB
	private UserManagerLocal userManager;

	private SMSMessageReceiver thread = null;

	/**
	 * Monitoring Variables
	 */
	private Date lastUpdate;

	private final Logger log = Logger
			.getLogger(SMSMessageReceiverService.class);

	@Override
	public void create() throws Exception {
		log.debug("Creating...");
	}

	@Override
	public void destroy() throws Exception {
		log.debug("Destroying...");
	}

	@PostConstruct
	public void start() {
		log.info("Iniciando --> process=" + SpmProcesses.SMS_MANAGER);
		Processcontrol processControl = this.createProcessControl();
		if (thread != null && thread.isAlive()) {
			log.warn("SMS Manager already started --> process="
					+ SpmProcesses.SMS_MANAGER);
			return;
		} else if (this.thread == null) {
			this.thread = new SMSMessageReceiver(SpmProcesses.SMS_MANAGER,
					"SMS Manager", paymentOrderManager, prefixManager,
					processControl, systemParameterManager, userManager);
		}
		if (!this.thread.loadParameters()) {
			log.error("Loading parameters");
			return;
		}
		this.thread.start();
		processControlManager.createProcessControl(processControl);
	}

	private Processcontrol createProcessControl() {
		Processcontrol processControl = new Processcontrol();
		ProcesscontrolPK id = new ProcesscontrolPK();
		id.setIdprocessPk(SpmProcesses.SMS_MANAGER);
		id.setServerPk(SpmUtil.getSiteLocalAddress());
		processControl.setId(id);
		long now = System.currentTimeMillis();
		processControl.setCreationdateTim(new Timestamp(now));
		processControl.setChangedateTim(new Timestamp(now));
		processControl.setDescripcion(SpmConstants.PROCESS_CONTROL_DESC);
		return processControl;
	}

	@PreDestroy
	public void stop() {
		if (thread != null && thread.isAlive()) {
			log.info("Stopping SMS Manager --> process="
					+ SpmProcesses.SMS_MANAGER);
			thread.closeThread();
			lastUpdate = thread.getLastUpdate();
			try {
				thread.join(10000);
			} catch (InterruptedException e) {
				log.error("Stopping SMS Manager --> process="
						+ SpmProcesses.SMS_MANAGER, e);
			}
		} else {
			log.debug("Checking.. Thread already stopped");
		}
	}

	@Override
	public int isRunning() {
		if (thread != null) {
			return thread.isAlive() ? BooleanValueEnum.TRUE.getValue()
					: BooleanValueEnum.FALSE.getValue();
		}
		return BooleanValueEnum.FALSE.getValue();
	}

	@Override
	public String getLastUpdate() {
		if (thread != null && thread.isAlive()) {
			lastUpdate = thread.getLastUpdate();
		}
		return SpmUtil.timeStampToStr(lastUpdate);
	}

}
