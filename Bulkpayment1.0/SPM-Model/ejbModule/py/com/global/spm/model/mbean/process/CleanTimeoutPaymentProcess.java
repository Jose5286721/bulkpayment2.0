package py.com.global.spm.model.mbean.process;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.model.cache.MTSCompanySessionCache;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.TransferProcessManagerLocal;
import py.com.global.spm.model.systemparameters.CleanTimeoutPaymentParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.MTSCompanySession;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.util.SpmUtil;

/**
 * Proceso para limpiar el Hash utilizado por el TransferProcess. Las ordenes de
 * pagos que tienen pagos pendientes se pondran con estado de error y se quitara
 * del hash el OP.
 * 
 * @author R2
 * 
 */
public class CleanTimeoutPaymentProcess extends Thread {

	private static final Logger log = Logger
			.getLogger(CleanTimeoutPaymentProcess.class);

	/**
	 * ThreadParameters
	 */
	private boolean exit;
	private long sleepTime;
	private long reloadParametersTime;
	private long lastTimeParametersLoaded;
	private long lastKeepAliveTime;
	private Date lastUpdate;

	// lino
	private long cachedSessionTimeout;
	private Processcontrol processControl;
	// Cola de envio al AsyncUpdater
	private JMSSender asyncUpdaterQueueSender = null;
	// Recursos del contenedor
	private InitialContext initialContext = null;

	/**
	 * DefaultParametersValues
	 */
	private long defaultSleepValue = 1000L; // 1 segs
	private long defaultReloadParametersTime = 120000L;// 120 segs
	// lino
	private long defaultCachedSessionTimeout = 120000L;// 120 segs
	private final long defaultKeepAliveTime = 120000L;// 120 segs
	private MTSCompanySessionCache cache;

	private TransferProcessManagerLocal transferProcessManager;
	private SystemparameterManagerLocal systemParameterManager;

	public CleanTimeoutPaymentProcess(Processcontrol processControl,
			TransferProcessManagerLocal transferProcessManager,
			SystemparameterManagerLocal systemParameterManager) {
		log.debug("CleanTimeoutPaymentProcess: constructor");
		this.processControl = processControl;
		this.transferProcessManager = transferProcessManager;
		this.systemParameterManager = systemParameterManager;
	}

	public void closeThread() {
		log.debug("Stoping CleanTimeoutPaymentProcess");
		exit = true;

	}

	private void reloadParameters() {
		if ((System.currentTimeMillis() - reloadParametersTime) > lastTimeParametersLoaded) {
			log.debug("Recargando paramatros de sistema");
			this.loadParameters();
		}
	}

	public boolean loadParameters() {
		exit = true;
		this.init();
		if (systemParameterManager != null) {
			// sleep time del proceso
			sleepTime = systemParameterManager
					.getParameter(CleanTimeoutPaymentParameters.SLEEP_TIME,
							defaultSleepValue);
			log.info("SleepTime = " + sleepTime + "millis");
			// reload parameters time
			reloadParametersTime = systemParameterManager.getParameter(
					CleanTimeoutPaymentParameters.RELOAD_PARAMETERS_TIME,
					defaultReloadParametersTime);
			log.info("RefaultReloadParametersTime = " + reloadParametersTime
					+ "millis");
			// cached session timeout
			cachedSessionTimeout = systemParameterManager.getParameter(
					CleanTimeoutPaymentParameters.CACHED_SESSION_TIMEOUT,
					defaultCachedSessionTimeout);
			log.info("CachedSessionTimeout = " + cachedSessionTimeout
					+ "millis");
			// fin carga de parametros
			lastTimeParametersLoaded = System.currentTimeMillis();
			exit = false;
		} else {
			log.error("SystemParameterManager=Null");
		}
		return !exit;
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
	 * Verificar el estado de conexion de las colas de datos
	 */
	private void checkQueues() {
		try {
			// Levantar la cola para AsyncUpdater.
			if (asyncUpdaterQueueSender == null) {
				asyncUpdaterQueueSender = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.ASYNC_UPDATER_QUEUE);
			}
			if (!asyncUpdaterQueueSender.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ asyncUpdaterQueueSender.toString() + ", init="
						+ asyncUpdaterQueueSender.init());
			}
		} catch (Exception e) {
			log.error("Fallo al iniciar la cola.", e);
		}
	}

	/**
	 * Cerrar de forma ordenada
	 */
	private void close() {
		log.info("Cerrando --> proceso=" + SpmProcesses.PAYMENTORDER_CLEANER);
		// Cerrando colas de datos
		try {
			if (asyncUpdaterQueueSender != null) {
				asyncUpdaterQueueSender.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola --> UpdaterQueueSender", e);
		}
	}

	public String listPaymentOrderHash() {
		return this.transferProcessManager.listCachePaymentOrder();
	}

	private void checkExpiredMTSCompanySession() {
		cache = MTSCompanySessionCache.getInstance();
		List<MTSCompanySession> expiredList = cache
				.getExpiredMTSCompanySession(cachedSessionTimeout);
		if (expiredList != null && expiredList.size() > 0) {
			for (MTSCompanySession session : expiredList) {
				log.warn("Removing expired Session!! --> " + session);
				cache.removeSessionFromCache(session.getCompany());
			}
		}
	}

	private void updateProcessControl() {
		long now = System.currentTimeMillis();
		if (now - lastKeepAliveTime > defaultKeepAliveTime) {
			processControl.setChangedateTim(new Timestamp(now));
			asyncUpdaterQueueSender.send(processControl);
			lastKeepAliveTime = now;
		}
	}

	@Override
	public void run() {
		log.debug("Iniciando proceso en background --> proceso="
				+ SpmProcesses.PAYMENTORDER_CLEANER);
		lastKeepAliveTime = System.currentTimeMillis();
		while (!exit) {
			checkQueues();
			this.transferProcessManager.cleanCachePaymentOrder();
			checkExpiredMTSCompanySession();
			SpmUtil.sleep(sleepTime);
			// TODO actualizar dps de 120 seg
			this.updateProcessControl();
			this.reloadParameters();
		}
		log.debug("Saliendo del proceso en background --> proceso="
				+ SpmProcesses.PAYMENTORDER_CLEANER);
		this.close();
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

}
