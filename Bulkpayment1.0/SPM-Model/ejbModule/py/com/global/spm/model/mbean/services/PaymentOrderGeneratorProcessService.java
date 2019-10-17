package py.com.global.spm.model.mbean.services;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Management;
import org.jboss.ejb3.annotation.Service;
import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.entities.ProcesscontrolPK;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.DraftManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.ProcessControlManagerLocal;
import py.com.global.spm.model.interfaces.SequenceManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.mbean.interfaces.MBeanControl;
import py.com.global.spm.model.mbean.process.PaymentOrderGeneratorProcess;
import py.com.global.spm.model.util.BooleanValueEnum;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmUtil;

/**
 * 
 * @author Lino Chamorro
 * 
 */
//@Service(objectName = "spm:service=PaymentOrderGeneratorProcess")
//@Depends({ "jboss.j2ee:jar=SPM-Model.jar,name=ProcessControlManager,service=EJB3" })
//@Management(MBeanControl.class)
@Startup
@Singleton
public class PaymentOrderGeneratorProcessService implements MBeanControl {

	@EJB//mappedName = "BeneficiaryManager/local", beanInterface = BeneficiaryManagerLocal.class)
	private BeneficiaryManagerLocal beneficiaryManager;
	@EJB//(mappedName = "CompanyManager/local", beanInterface = CompanyManagerLocal.class)
	private CompanyManagerLocal companyManager;
	@EJB//(mappedName = "DraftManager/local", beanInterface = DraftManagerLocal.class)
	private DraftManagerLocal draftManager;
	@EJB//(mappedName = "PaymentOrderManager/local", beanInterface = PaymentOrderManagerLocal.class)
	private PaymentOrderManagerLocal paymentOrderManager;
	@EJB//(mappedName = "ProcessControlManager/local", beanInterface = ProcessControlManagerLocal.class)
	private ProcessControlManagerLocal processControlManager;
	@EJB//(mappedName = "SequenceManager/local", beanInterface = SequenceManagerLocal.class)
	private SequenceManagerLocal sequenceManager;
	@EJB//(mappedName = "SystemparameterManager/local", beanInterface = SystemparameterManagerLocal.class)
	private SystemparameterManagerLocal systemParameterManager;

	private PaymentOrderGeneratorProcess thread = null;
	/**
	 * Monitoring Variables
	 */
	private Date lastUpdate;

	private final Logger log = Logger
			.getLogger(PaymentOrderGeneratorProcessService.class);

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
		log.info("Iniciando --> process=" + SpmProcesses.PO_MANAGER);
		Processcontrol processcontrol = this.createProcessControl();
		if (thread != null && thread.isAlive()) {
			log.warn("OP Manager already started --> process="
					+ SpmProcesses.PO_MANAGER);
			return;
		} else if (this.thread == null) {
			this.thread = new PaymentOrderGeneratorProcess(beneficiaryManager,
					companyManager, draftManager, sequenceManager,
					paymentOrderManager, processcontrol, systemParameterManager);
		}
		if (!this.thread.loadParameters()) {
			log.error("Loading parameters");
			return;
		}
		this.thread.start();
		processControlManager.createProcessControl(processcontrol);
	}

	private Processcontrol createProcessControl() {
		Processcontrol processControl = new Processcontrol();
		ProcesscontrolPK id = new ProcesscontrolPK();
		id.setIdprocessPk(SpmProcesses.PO_MANAGER);
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
			log.info("Stopping OP Manager --> process="
					+ SpmProcesses.PO_MANAGER);
			thread.closeThread();
			lastUpdate = thread.getLastUpdate();
			try {
				thread.join(10000);
			} catch (InterruptedException e) {
				log.error("Stopping OP Manager --> process="
						+ SpmProcesses.PO_MANAGER, e);
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
		if (lastUpdate == null) {
			return "null";
		}
		return SpmUtil.timeStampToStr(lastUpdate);
	}

}
