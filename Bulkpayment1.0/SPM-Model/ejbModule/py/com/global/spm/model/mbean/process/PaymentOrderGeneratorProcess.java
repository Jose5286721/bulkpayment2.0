package py.com.global.spm.model.mbean.process;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Beneficiary;
import py.com.global.spm.entities.Company;
import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Draftdetail;
import py.com.global.spm.entities.Logdraftop;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.Paymentorderdetail;
import py.com.global.spm.entities.Processcontrol;
import py.com.global.spm.model.eventcodes.OPManagerEventCodes;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.DraftManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.SequenceManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.OPManagerToFlowManagerMessage;
import py.com.global.spm.model.messages.OPManagerToUpdaterMessage;
import py.com.global.spm.model.systemparameters.OPManagerParameters;
import py.com.global.spm.model.util.CompanyStates;
import py.com.global.spm.model.util.DraftStates;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentorderdetailWrapper;
import py.com.global.spm.model.util.ProcessWindow;
import py.com.global.spm.model.util.SPMXMLUtil;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.util.SpmUtil;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class PaymentOrderGeneratorProcess extends Thread {

	private BeneficiaryManagerLocal beneficiaryManager;
	private CompanyManagerLocal companyManager;
	private DraftManagerLocal draftManager;
	private PaymentOrderManagerLocal paymentOrderManager;
	private SequenceManagerLocal sequenceManager;
	private SystemparameterManagerLocal systemParameterManager;

	// Cola de envio al FlowManager
	private JMSSender flowManagerQueueSender = null;
	// Cola de envio al Updater
	private JMSSender updaterQueueSender = null;
	// Cola de envio al AsyncUpdater
	private JMSSender asyncUpdaterQueueSender = null;

	// Recursos del contenedorkjkjk
	private InitialContext initialContext = null;

	/**
	 * ThreadParameters
	 */
	private boolean exit;
	private long sleepTime;
	private long reloadParametersTime;
	private long lastTimeParametersLoaded;
	private long lastKeepAliveTime;
	private Date lastUpdate;
	private ProcessWindow window1;
	private ProcessWindow window2;
	private long globalAmountByPaymentOrder;
	private long globalAmountByPayment;
	private String filePrefix;
//	private String filePath;
	private String mimeType;
	private Processcontrol processControl;

	/**
	 * DefaultParametersValues
	 */
	private long defaultSleepValue = 1000L; // 1 segs
	private long defaultReloadParametersTime = 120L;// 120 segs
	private final long defaultKeepAliveTime = 120000L;// 120 segs
	private ProcessWindow defaultWindow = new ProcessWindow(0, 23); // de
																	// 0:00:00 a
																	// 23:59:59
																	// hs

	private final Logger log = Logger
			.getLogger(PaymentOrderGeneratorProcess.class);

	public PaymentOrderGeneratorProcess(
			BeneficiaryManagerLocal beneficiaryManager,
			CompanyManagerLocal companyManager, DraftManagerLocal draftManager,
			SequenceManagerLocal sequenceManager,
			PaymentOrderManagerLocal paymentOrderManager,
			Processcontrol processControl,
			SystemparameterManagerLocal systemParameterManager) {
		super();
		this.beneficiaryManager = beneficiaryManager;
		this.companyManager = companyManager;
		this.draftManager = draftManager;
		this.sequenceManager = sequenceManager;
		this.paymentOrderManager = paymentOrderManager;
		this.processControl = processControl;
		this.systemParameterManager = systemParameterManager;
		lastUpdate = new Date();
	}

	public boolean loadParameters() {
		log.info("Loading parameters... --> processId="
				+ SpmProcesses.PO_MANAGER + ", processName=PO Manager");
		exit = true;
		this.init();
		if (systemParameterManager != null) {
			// sleep time del proceso
			sleepTime = systemParameterManager.getParameter(
					OPManagerParameters.SLEEP_TIME, defaultSleepValue);
			log.info("SleepTime = " + sleepTime + "millis");
			// reload parameters time en segundos
			reloadParametersTime = systemParameterManager.getParameter(
					OPManagerParameters.RELOAD_PARAMETERS_TIME,
					defaultReloadParametersTime);
			log.info("RefaultReloadParametersTime = " + reloadParametersTime
					+ "segs");
			reloadParametersTime *= 1000;
			// execution window2
			// window 1
			int from = systemParameterManager.getParameter(
					OPManagerParameters.WINDOW1_FROM, -1);
			int to = systemParameterManager.getParameter(
					OPManagerParameters.WINDOW1_TO, -1);
			window1 = new ProcessWindow(from, to);
			if (!window1.isValid()) {
				window1 = defaultWindow;
				log.info("Using default execution window1 --> " + window1);
			} else {
				log.info("Execution window1 --> " + window1);
			}
			// window 2
			from = systemParameterManager.getParameter(
					OPManagerParameters.WINDOW2_FROM, -1);
			to = systemParameterManager.getParameter(
					OPManagerParameters.WINDOW2_TO, -1);
			window2 = new ProcessWindow(from, to);
			if (!window2.isValid()) {
				log.error("Discarding window2 --> " + window2);
				window2 = null;
			} else {
				log.info("Execution window2 --> " + window2);
			}
			// limite de monto global
			globalAmountByPaymentOrder = systemParameterManager.getParameter(
					OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT_ORDER, -1l);
			if (globalAmountByPaymentOrder < 1) {
				log.error("Invalid GlobalAmountByPaymentOrder --> "
						+ globalAmountByPaymentOrder);
				return false;
			} else {
				log.info("GlobalAmountByPaymentOrder = "
						+ globalAmountByPaymentOrder);
			}
			// limite de monto por pago
			globalAmountByPayment = systemParameterManager.getParameter(
					OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT, -1);
			if (globalAmountByPayment < 1) {
				log.error("Invalid GlobalAmountByPayment --> "
						+ globalAmountByPayment);
				return false;
			} else {
				log.info("GlobalAmountByPayment = " + globalAmountByPayment);
			}
			// filePrefix
			filePrefix = systemParameterManager.getParameter(
					OPManagerParameters.FILE_PREFIX, "spm_");
			log.info("File prefix --> " + filePrefix);
			// filePath
//			filePath = systemParameterManager.getParameter(
//					OPManagerParameters.FILE_PATH, "/opt/spm/xml");
//			log.info("File path --> " + filePath);
			// mimeType
			mimeType = systemParameterManager.getParameter(
					OPManagerParameters.MIME_TYPE, "application/xml");
			log.info("MimeType --> " + mimeType);
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
			// Levantar la cola de salida
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

			if (updaterQueueSender == null) {
				updaterQueueSender = new JMSSender(initialContext,
						JMSSender.JBOSS_CONNECTION_FACTORY,
						SpmQueues.UPDATER_QUEUE);
			}
			if (!updaterQueueSender.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ updaterQueueSender.toString() + ", init="
						+ updaterQueueSender.init());
			}

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
		log.info("Cerrando --> proceso=" + SpmProcesses.PO_MANAGER);
		// Cerrando colas de datos
		try {
			if (flowManagerQueueSender != null) {
				flowManagerQueueSender.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola --> FlowManagerQueueSender", e);
		}
		try {
			if (updaterQueueSender != null) {
				updaterQueueSender.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola --> UpdaterQueueSender", e);
		}
		try {
			if (asyncUpdaterQueueSender != null) {
				asyncUpdaterQueueSender.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola --> asyncUpdaterQueueSender", e);
		}
	}

	private void checkAndProcessDrafts() {
		// Procesar programados
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<Draft> programedList = draftManager.getProgramedPaymentorder(now);
		int totalProgDarft = programedList.size();
		int generated = 0;
		int noGenerated = 0;
		for (Draft draft : programedList) {
			if (this.generatePaymentorder(draft, true)) {
				generated++;
			} else {
				log.warn("Deactivating draft by PO generation error --> "
						+ draft);
				draft.setStateChr(DraftStates.INACTIVE);
				draftManager.updateDraft(draft);
				noGenerated++;
			}
		}
		log.debug("Totales Programados --> draft=" + totalProgDarft
				+ ", generados=" + generated + ", no generados=" + noGenerated);
		// Procesar recurrentes
		Calendar cal = new GregorianCalendar();
		cal.setTime(now);
		int generateDay = cal.get(Calendar.DAY_OF_MONTH);
		List<Draft> recurrentList = draftManager.getRecurrentPaymentorder(
				generateDay, now);
		int totalRecurrentDarft = 0;
		generated = 0;
		noGenerated = 0;
		if (recurrentList != null) {
			totalRecurrentDarft = recurrentList.size();
			for (Draft draft : recurrentList) {
				if (this.generatePaymentorder(draft, false)) {
					generated++;
				} else {
					noGenerated++;
				}
			}
		}
		log.debug("Totales Recurrentes --> draft=" + totalRecurrentDarft
				+ ", generados=" + generated + ", no generados=" + noGenerated);
		lastUpdate = new Date();
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	private OPManagerEventCodes isValidCompany(long idcompany, int paidCount) {
		Company company = companyManager.getCompanyById(idcompany);
		if (company.getStateChr().compareToIgnoreCase(CompanyStates.ACTIVE) != 0) {
			log.warn("Company not active --> " + company);
			return OPManagerEventCodes.INACTIVE_COMPANY;
		}
		// validar cantidad de transacciones total
		if (company.getTrxtotallimitNum() > 0
				&& (company.getTrxtotalcountNum() + paidCount > company
						.getTrxtotallimitNum())) {
			log.warn("Limite de transacciones total superado --> " + company
					+ ", trx=" + paidCount);
			return OPManagerEventCodes.TRX_TOTAL_EXCEEDED;
		}
		// validar cantidad de transacciones diario
		if (company.getTrxdaylimitNum() > 0
				&& (company.getTrxdaycountNum() + paidCount > company
						.getTrxdaylimitNum())) {
			log.warn("Limite de transacciones diario superado --> " + company
					+ ", trx=" + paidCount);
			return OPManagerEventCodes.TRX_DIARY_EXCEEDED;
		}
		// validar cantidad de transacciones mensual
		if (company.getTrxmonthlimitNum() > 0
				&& (company.getTrxmonthcountNum() + paidCount > company
						.getTrxmonthlimitNum())) {
			log.warn("Limite de transacciones mensual superado --> " + company
					+ ", trx=" + paidCount);
			return OPManagerEventCodes.TRX_MONTHLY_EXCEEDED;
		}
		// todo OK
		return OPManagerEventCodes.SUCCESS;
	}

	private boolean generatePaymentorder(Draft draft, boolean programado) {
		try {
			List<Draftdetail> draftdetailList = draftManager
					.getDraftDetail(draft.getIddraftPk());
			log.debug("Generating Paymentorder --> " + draft
					+ ", detail count=" + draftdetailList.size());
			// validar empresa
			OPManagerEventCodes validateCompany = isValidCompany(
					draft.getIdcompanyPk(), draftdetailList.size());

			if (! (OPManagerEventCodes.SUCCESS.getIdEventCode().equals(validateCompany.getIdEventCode()))) {
				this.createLogdraftOP(draft, validateCompany, null);
				log.error("Error generando orden de pago  --> " + draft);
				return false;
			}
			Double totalAmount = 0D;
			for (Draftdetail dd : draftdetailList) {
				totalAmount += dd.getAmountNum();
			}
			// validar limite de monto global de orden de pago
			if (globalAmountByPaymentOrder < totalAmount) {
				log.error("Monto total maximo por orden de pago excedido --> "
						+ draft + ", globalAmountByPaymentOrder="
						+ globalAmountByPaymentOrder + ", monto=" + totalAmount);
				this.createLogdraftOP(
						draft,
						OPManagerEventCodes.GLOBAL_AMOUNT_BY_PAYMENT_ORDER_EXCEEDED,
						null);
				return false;
			}
			// validar monto por pago
			for (Draftdetail dd : draftdetailList) {
				if (globalAmountByPayment < dd.getAmountNum()) {
					log.error("Monto maximo por pago excedido --> " + draft
							+ ", " + dd + ", globalAmountByPayment="
							+ globalAmountByPayment);
					this.createLogdraftOP(
							draft,
							OPManagerEventCodes.GLOBAL_AMOUNT_BY_PAYMENT_EXCEEDED,
							null);
					return false;
				}
			}
			// todo ok, generar orden de pago
			Paymentorder paymentorder = this.createPaymentOrder(draft,
					totalAmount);
			if (paymentorder == null) {
				log.error("Generando orden de pago, " + draft);
				return false;
			}
			List<Paymentorderdetail> paymentorderdetailList = this
					.createPaymentorderDetail(paymentorder, draftdetailList);
//			paymentorder.setMimetypeChr(mimeType);
//			String fileName = SPMXMLUtil.getFileName(filePrefix,
//					paymentorder.getIdpaymentorderPk());
//			paymentorder.setXmlnameChr(fileName);
			List<PaymentorderdetailWrapper> podetailsWrapper = this
					.getPaymentorderdetailWrapperList(paymentorderdetailList);
//			paymentorder.setPofile(SPMXMLUtil.getXMLStream(paymentorder,
//					podetailsWrapper, filePath, fileName));
			try {
				if (!programado) {
					paymentOrderManager.createPaymentorder(paymentorder,
							paymentorderdetailList);
				} else {
					paymentOrderManager.createPaymentorderAndInactivateDraft(
							draft, paymentorder, paymentorderdetailList);
				}
			} catch (Exception e) {
				log.error("Error persistiendo orden de pago --> " + draft, e);
				this.createLogdraftOP(draft,
						OPManagerEventCodes.ERROR_PERSISTING_PAYMENT_ORDER,
						null);
				return false;
			}
			this.createLogdraftOP(draft, OPManagerEventCodes.SUCCESS,
					paymentorder);
			log.info("Paymentorder created! \n"
					+ paymentorder
					+ SpmUtil
							.paymentorderdetailListToString(paymentorderdetailList));
			OPManagerToFlowManagerMessage message = new OPManagerToFlowManagerMessage();
			message.setPaymentorder(paymentorder);
			message.setPodetailList(paymentorderdetailList);
			return flowManagerQueueSender.send(message);
		} catch (Exception e) {
			log.error("Error desconocido generando orden de pago --> " + draft,
					e);
			this.createLogdraftOP(draft,
					OPManagerEventCodes.ERROR_PERSISTING_PAYMENT_ORDER, null);
			return false;
		}
	}

	private void createLogdraftOP(Draft draft, OPManagerEventCodes event,
			Paymentorder paymentorder) {
		// Crear logdraftOP y enviar al updater para que persista
		Logdraftop logdraftop = new Logdraftop();
		logdraftop
				.setCreationdateTim(new Timestamp(System.currentTimeMillis()));
		logdraftop.setIdcompanyPk(draft.getIdcompanyPk());
		logdraftop.setIddraftPk(draft.getIddraftPk());
		logdraftop.setIdeventcodeNum(event.getIdEventCode());
		// if success, set idpaymentorderPk
		if (OPManagerEventCodes.SUCCESS.getIdEventCode().equals(event.getIdEventCode())) {
			logdraftop.setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
		}
		logdraftop.setIdprocessPk(SpmProcesses.PO_MANAGER);
		OPManagerToUpdaterMessage message = new OPManagerToUpdaterMessage();
		message.setLogdraftop(logdraftop);
		updaterQueueSender.send(message);
	}

	private Paymentorder createPaymentOrder(Draft draft, Double totalAmount) {
		try {
			//TODO VERIFICAR QUE SEA LA FUNCIONALIDAD ESPERADA
//			Paymentorder paymentorder = new Paymentorder(
//					sequenceManager.next(SequenceManagerLocal.PAYMENTORDER_SEQ));
			Paymentorder paymentorder = new Paymentorder();

			paymentorder.setIddraftPk(draft.getIddraftPk());
			paymentorder.setCreationdateTim(new Timestamp(System
					.currentTimeMillis()));
			paymentorder.setDescriptionChr(draft.getDescriptionChr());
			paymentorder.setIdcompanyPk(draft.getIdcompanyPk());
			paymentorder.setIdpaymentordertypePk(draft
					.getIdpaymentordertypePk());
			paymentorder.setIdworkflowPk(draft.getIdworkflowPk());
			paymentorder.setStateChr(PaymentOrderStates.EN_PROCESO);
			paymentorder.setUpdatedateTim(new Timestamp(System
					.currentTimeMillis()));
			paymentorder.setNotifybenficiaryNum(draft.getNotifybenficiaryNum());
			paymentorder.setNotifycancelNum(draft.getNotifycancelNum());
			paymentorder.setNotifygenNum(draft.getNotifygenNum());
			paymentorder.setNotifysignerNum(draft.getNotifysignerNum());
			log.debug("Setting amount --> " + totalAmount);
			paymentorder.setAmountNum(totalAmount);
			log.debug("Amount setted --> " + paymentorder.getAmountNum());
			paymentorder.setAmountpaidNum(0D);
			paymentorder.setIdprocessPk(SpmProcesses.PO_MANAGER);
			paymentorder.setIdeventcodeNum(OPManagerEventCodes.SUCCESS
					.getIdEventCode());
			paymentorder.setTotalpaymentNum(0);
			paymentorder.setPaymentsuccessNum(0);
			paymentorder.setPaymentpartsucNum(0);
			paymentorder.setPaymenterrorNum(0);
			return paymentorder;
		} catch (Exception e) {
			log.error("Creando orden de pago --> " + draft, e);
			return null;
		}
	}

	private List<Paymentorderdetail> createPaymentorderDetail(
			Paymentorder paymentorder, List<Draftdetail> draftdetailList) {
		List<Paymentorderdetail> paymentorderdetailList = new ArrayList<Paymentorderdetail>();
		Paymentorderdetail pod = null;
		for (Draftdetail dd : draftdetailList) {
			pod = new Paymentorderdetail(paymentorder.getIdpaymentorderPk(), dd
					.getId().getIdbeneficiaryPk());
			pod.setAmountNum(dd.getAmountNum());
			paymentorderdetailList.add(pod);
		}
		return paymentorderdetailList;
	}

	private List<PaymentorderdetailWrapper> getPaymentorderdetailWrapperList(
			List<Paymentorderdetail> paymentorderdetailList) {
		List<PaymentorderdetailWrapper> list = new ArrayList<PaymentorderdetailWrapper>();
		Beneficiary beneficiary;
		if (paymentorderdetailList != null && paymentorderdetailList.size() > 0) {
			for (Paymentorderdetail pod : paymentorderdetailList) {
				beneficiary = beneficiaryManager.getBeneficiaryById(pod.getId()
						.getIdbeneficiaryPk());
				list.add(new PaymentorderdetailWrapper(beneficiary
						.getPhonenumberChr(),
						String.valueOf(pod.getAmountNum())));
			}
		} else {
			log.error("paymentorderdetailList=NULL!");
		}
		return list;
	}

	private void reloadParameters() {
		if ((System.currentTimeMillis() - reloadParametersTime) > lastTimeParametersLoaded) {
			log.debug("Recargando paramatros de sistema");
			this.loadParameters();
		}
	}

	public void closeThread() {
		log.debug("Stoping PaymentOrderGeneratorProcess");
		exit = true;
	}

	// window1 y window2 se pueden solapar
	private boolean isWindowOpen() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String nowStr = dateFormat.format(new Date());
		try {
			int fromWindow1 = nowStr.compareToIgnoreCase(window1.getFromStr());
			int toWindow1 = nowStr.compareToIgnoreCase(window1.getToStr());
			if (fromWindow1 >= 0 && toWindow1 < 0) {
				return true;
			}
		} catch (Exception e) {
			log.error("Processing window1 --> " + window1.toString(), e);
		}
		if (window2 != null) {
			try {
				int fromWindow2 = nowStr.compareToIgnoreCase(window2
						.getFromStr());
				int toWindow2 = nowStr.compareToIgnoreCase(window2.getToStr());
				if (fromWindow2 >= 0 && toWindow2 < 0) {
					return true;
				}
			} catch (Exception e) {
				log.error("Processing window2 --> " + window2.toString(), e);
			}
		}
		return false;
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
		log.info("Iniciando proceso en background --> proceso="
				+ SpmProcesses.PO_MANAGER);
		lastKeepAliveTime = System.currentTimeMillis();
		while (!exit) {
			checkQueues();
			if (isWindowOpen()) {
				checkAndProcessDrafts();
			} else {
				log.debug("windows close");
			}
			reloadParameters();
			this.updateProcessControl();
			SpmUtil.sleep(sleepTime);
		}
		log.debug("Saliendo del proceso en background --> proceso="
				+ SpmProcesses.PO_MANAGER);
		this.close();
	}

}
