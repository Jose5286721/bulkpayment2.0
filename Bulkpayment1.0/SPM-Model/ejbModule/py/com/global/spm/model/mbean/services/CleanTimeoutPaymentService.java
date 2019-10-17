package py.com.global.spm.model.mbean.services;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.Management;
import org.jboss.ejb3.annotation.Service;

import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.entities.ProcesscontrolPK;
import py.com.global.spm.model.interfaces.ProcessControlManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.TransferProcessManagerLocal;
import py.com.global.spm.model.mbean.interfaces.CleanPaymentInterface;
import py.com.global.spm.model.mbean.interfaces.MBeanControl;
import py.com.global.spm.model.mbean.process.CleanTimeoutPaymentProcess;
import py.com.global.spm.model.util.BooleanValueEnum;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmUtil;

/**
 * 
 * @author R2
 * 
 */
//@Service(objectName = "spm:service=CleanTimeoutPayments")
//@DependsOn({
//		"jboss.j2ee:jar=SPM-Model.jar,name=TransferProcessManager,service=EJB3",
//		"jboss.j2ee:jar=SPM-Model.jar,name=ProcessControlManager,service=EJB3" })
//@Management(MBeanControl.class)
@Startup
@Singleton
public class CleanTimeoutPaymentService implements CleanPaymentInterface {

	@EJB//(mappedName = "ProcessControlManager/local", beanInterface = ProcessControlManagerLocal.class)
	private ProcessControlManagerLocal processControlManager;
	@EJB//(mappedName = "TransferProcessManager/local", beanInterface = TransferProcessManagerLocal.class)
	private TransferProcessManagerLocal transferProcessManager;
	@EJB//(mappedName = "SystemparameterManager/local", beanInterface = SystemparameterManagerLocal.class)
	private SystemparameterManagerLocal systemParameterManager;

	private CleanTimeoutPaymentProcess thread = null;
	/**
	 * Monitoring Variables
	 */
	private Date lastUpdate;

	private final Logger log = Logger
			.getLogger(CleanTimeoutPaymentService.class);

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
		log.info("Iniciando --> process=" + SpmProcesses.PAYMENTORDER_CLEANER);
		Processcontrol processControl = this.createProcessControl();
		if (thread != null && thread.isAlive()) {
			log.warn("CleanTimeoutPaymentProcess already started --> process="
					+ SpmProcesses.PAYMENTORDER_CLEANER);
			return;
		} else if (this.thread == null) {
			this.thread = new CleanTimeoutPaymentProcess(processControl,
					transferProcessManager, systemParameterManager);
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
		id.setIdprocessPk(SpmProcesses.PAYMENTORDER_CLEANER);
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
			log.info("Stopping Payment Order Cleaner --> process="
					+ SpmProcesses.PAYMENTORDER_CLEANER);
			thread.closeThread();
			try {
				thread.join(10000);
			} catch (InterruptedException e) {
				log.error("Stopping Payment Order Cleaner --> process="
						+ SpmProcesses.PAYMENTORDER_CLEANER, e);
			}
			thread = null;// Alambre por que el JMX console no le gustaba el
							// estado del hilo.
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
		if (lastUpdate == null) {
			return "null";
		}
		return SpmUtil.timeStampToStr(lastUpdate);
	}

	@Override
	public String listPaymentOrderHash() {
		if (this.thread != null) {
			return this.thread.listPaymentOrderHash();
		} else {
			return "No se instancio el hilo.";
		}
	}

}
