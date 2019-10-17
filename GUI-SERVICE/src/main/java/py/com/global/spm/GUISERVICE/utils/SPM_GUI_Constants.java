package py.com.global.spm.GUISERVICE.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class SPM_GUI_Constants {

	/*
		Cola
	 */

	public static final String IMPORTER_CSV_QUEUE = "csvImporterQueue";
	public static final String PIN_LOGIN_NOTIFICATION_QUEUE = "pinLoginNotificationQueue";
	public static final int MAX_PRIORITY =9;
	/*
		Roles
	 */
	public static final String ROLE_CHANGE_PASSWORD = "ROLE_CHANGE_PASSWORD";
	/*
	 * TRAIDOS DE CONFPARAMETER
	 */
	public static final String PROCESS_CONTROL_PROCESS_MAIN = "processcontrol.process.main";
	public static final String MENU_ROOT_MENU_ID = "menu.root.menu.id";
	public static final String SYSTEM_PARAMETERS_GUI_ID_PROCESSPK = "system.parameters.gui.id.processPk";
	public static final String TEMPLATE_ESP = "template.esp";
	public static final String SHORT_EXAMPLE = "short.example";
	public static final String COMPLETE_EXAMPLE = "complete.example";

	/*
	 * Cadenas Constantes
	 */

	public static final String DEFAULT_NO_FILTERED = "-";
	public static final String ACTIVO = "AC";
	public static final String INACTIVO = "IN";
	/*
	 * SI LOS VALORES DE ARRIBA NO SE OBTIENEN SE DEBEN USAR ESTOS VALORES POR
	 * DEFECTO
	 */
	public static final int PROCESS_CONTROL_DEFAUL_ID = 1000;
	public static final Long DEFAULT_GUI_PROCESS_ID = 1011L;
	public static final Long MENU_ROOT_DEFAULT_ID = 1L;

	/*
	 * OTROS PROCESOS
	 */
	public static final Long PROCESS_TRANSFER = 1004L;
	public static final long PROCESS_SPM_GENERAL = 1000L;
	public static final short PROCESS_PO_MANAGER = 1001;
	public static final short PROCESS_MTSTRANSFERINTERFACE = 1005;
	public static final short PROCESS_MTSREVERSIONINTERFACE = 1006;
	public static final short PROCESS_SMSMANAGER = 1003;
	public static final short PROCESS_REVERSION = 1010;
	public static final  long DRIVER_NOTIFICATIONS = 1013L; //Proceso para la comunicación con el driver de notificaciones
	public static final short PROCESS_NOTIFICATIONMANAGER = 1007;
	/*
	 * TRAIDOS DE LA TABLA SYSTEM_PARAMETERS
	 */
	public static final String SYSTEM_PARAMETERS_AUTH2FACTOR = "auth2Factor";
	public static final String SYSTEM_PARAMETERS_AUTH2FACTOR_MESSAGE = "auth2FactorMessage";
	public static final String SYSTEM_PARAMETERS_CONNECT_TIMEOUT="connectTimeoutMs";
	public static final String SYSTEM_PARAMETERS_READ_TIMEOUT = "readTimeoutMs";
	public static final String SYSTEM_PARAMETERS_MAX_CONNECTIONS = "maxConnections";
	public static final String SYSTEM_PARAMETERS_LDAP_HOST = "ldapHost";
	public static final String SYSTEM_PARAMETERS_LDAP_PORT = "ldapPort";
	public static final String SYSTEM_PARAMETERS_LDAP_DOMAIN = "ldapDomain";
	public static final String SYSTEM_PARAMETERS_LDAP_SEARCH_BASE = "ldapSearchbase";
	public static final String SYSTEM_PARAMETERS_PERMISSION_TREE_MODIFY = "permisionTreeModify";
	public static final String SYSTEM_PARAMETERS_USER_DEFAULT = "userDefault";
	public static final String SYSTEM_PARAMETERS_FAIL_LOGON = "failLogon";
	public static final String SYSTEM_PARAMETERS_USER_PASS_EXPIRED_DAYS = "userExpiredDays";
	public static final String SYSTEM_PARAMETERS_SUPER_COMPANY_ID = "SuperCompanyId";
	public static final String SYSTEM_PARAMETERS_DEFAULT_NOTIFY_SMS = "defaultNotifySms";
	public static final String SYSTEM_PARAMETERS_DEFAULT_NOTIFY_EMAIL = "defaultNotifyEmail";
	public static final String SYSTEM_PARAMETERS_DEFAULT_SMS_SIGN = "defaultSmsSign";
	public static final String SYSTEM_PARAMETERS_TOKEN_ENABLED = "tokenEnabled";
	public static final String SYSTEM_PARAMETERS_PINLOGIN_VALIDATEPERIOD = "pinLoginValidatePeriod";
	public static final String SYSTEM_PARAMETERS_TOKEN_MAXLENGTH = "tokenMaxLength";
	public static final String SYSTEM_PARAMETERS_PINLOGIN_MAXATTEMPT = "pinLoginMaxAttempt";
	public static final String SYSTEM_PARAMETERS_JMS_LISTENER_CONCURRENCY = "jms.listener.concurrency";

	public static final String DEFAULT_TIMEOUT_MS = "30000";
	public static final String DEFAULT_MAX_CONNECTIONS = "33";
	public static final String DEFAULT_AUTH2FACTOR_MESSAGE = "[PIN] es tu codigo de confirmacion. POR TU SEGURIDAD NO LO COMPARTAS CON NADIE.";

	//public static final String SYSTEM_PARAMETERS_DEFAULT_SERVER_IP = "defaultServerIp";
	//public static final String SYSTEM_PARAMETERS_DEFAULT_SERVER_PORT = "defaultServerPort";

	public static final String SYSTEM_PARAMETERS_PAYMENT_VOUCHER_REPORT_FILENAME= "paymentVoucherReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_MTS_REPORT_FILENAME= "logMtsReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_AUDIT_REPORT_FILENAME= "logAuditReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_SESSION_REPORT_FILENAME= "logSessionReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_PAYMENT_REPORT_FILENAME= "logPaymentReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_DRAFTOP_REPORT_FILENAME= "logDraftOpReportFileName";
	public static final String SYSTEM_PARAMETERS_LOG_MESSAGE_REPORT_FILENAME= "logMessageReportFileName";
	public static final String SYSTEM_PARAMETERS_SMS_LOG_MESSAGE_REPORT_FILENAME= "smsLogMessageReportFileName";




	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_EXPIRED_TIME = "userSecurityPassRecoveryExpiredTime";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_SUBJECT = "userNotificationMailTemplateSubject";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_MESSAGE = "userNotificationMailTemplateMessage";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_FROM_EMAIL = "from";

	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_LINK = "<link>";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_EXPIRED_TIME = "<expiredTime>";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_SIGN_WSDL = "userSecuritySignWsdl";
	public static final String SYSTEM_PARAMETERS_USER_SECURITY_SIGN_METHOD = "userSecuritySignMethod";
	public static final String HOME_IMG_DIRECTORY = "images/";
	public static final String SYSTEM_PARAMETERS_IMPORTER_ROLE = "fileImporterRole";
	public static final String SYSTEM_PARAMETERS_IMPORTER_CURRENCY = "fileImporterCurrency";

	public static final String USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TOKEN = "[TOKEN]";
	
	public static final String PAYMENT_ORDER_UPDATE = "paymentOrderUpdate";
	public static final String ID_COMPANY_FOR_AGENT_PAYMENT = "idCompanyForAgentPayment";
	public static final String ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO = "idCompanyForReposicionGuardaSaldo";
	public static final String MAX_FILE_UPLOAD_SIZE="maxFileUploadSize";

	public static final String PASS_EXPIRATION_TIME = "passExpirationTime";
	public static final String PASS_LENGTH = "passLength";
	public static final String PASS_ALPHANUMERIC = "passAlphaNumeric";

	//public static final String SHOW_REVERSION = "showReversion";

	public static final String CONSULTASALDO_VALIDATION_CODE = "consultaSaldoValidationCode";
	public static final String CONSULTASALDO_ENDPOINT = "consultaSaldoEndpoint";
	public static final String CONSULTASALDO_DUMMY = "consultaSaldoDummy";
	public static final String CONSULTASALDO_MONTO = "consultaSaldoMonto";

	public static final int DEFAULT_PASS_RECOVERY_TIME = 12;
	public static final String DEFAULT_SIGN_WSDL = "/SPM-GUI-ejb/GlobalSPMSignedServices?wsdl";

	public static final String[] trueValues = { "Y", "y", "T", "t", "SI", "S",
			"si", "s", "true", "V", "v" };

	public static final String WORKFLOW_DEFAULT_WALLET = "workflowDefaultWallet";
	public static final String WORKFLOW_DEFAULT_WALLET_DESC = "workflowDefaultWalletDesc";

	public static boolean isTrue(String value) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}
		return Arrays.asList(trueValues).contains(value);
	}

	/*
	 * PARA AUDITORIA *
	 */
	public static final long AUDIT_THREAD_SLEEP_TIME = 1000L * 300;// 5 minutos
	public static final String NO_LOGGED_USER = "NO_USER_LOGGED";
	public static final String AUDIT_HTTP_REQUEST_METHDOS = "GET";

	public static final String DEFAULT_YES = "Y";
	public static final String DEFAULT_NO = "N";

	public static final Integer SUCCESS_EVENT_CODE = 0;

	public static final String EXPECTED_RESULTS_PERSISTED = "persisted";
	public static final String EXPECTED_RESULTS_UPDATED = "updated";
	public static final String EXPECTED_RESULTS_REMOVED = "removed";
	public static final String EXPECTED_RESULTS_CHANGED = "changed";
	/**
	 * EVENTOS
	 * */
	public static final String EVENT_USER_CREATED = "user.created";
	public static final String EVENT_USER_UPDATED = "user.updated";
	public static final String EVENT_ROL_UPDATED = "rol.updated";

	public static final String EVENT_SYSTEM_PARAMETERS_CREATED = "systemparameter.created";
	public static final String EVENT_SYSTEM_PARAMETERS_UPDATED = "systemparameter.updated";
	public static final String EVENT_SYSTEM_PARAMETERS_REMOVED = "systemparameter.removed";

	public static final String EVENT_COMPANY_CREATED = "company.created";
	public static final String EVENT_COMPANY_UPDATED = "company.updated";
	public static final String EVENT_COMPANY_REMOVED = "company.removed";

	public static final String EVENT_PAYMENTORDERTYPE_CREATED = "company.created";
	public static final String EVENT_PAYMENTORDERTYPE_UPDATED = "company.updated";
	public static final String EVENT_PAYMENTORDERTYPE_REMOVED = "company.removed";

	public static final String EVENT_BENEFICIARY_CREATED = "company.created";
	public static final String EVENT_BENEFICIARY_UPDATED = "company.updated";
	public static final String EVENT_BENEFICIARY_REMOVED = "company.removed";
	
	//EVENTOS AGREGADOS PARA VALIDAR SUSCRIPTOR CONTRA MTS
	public static final Integer EVENT_SUBSCRIBER_INVALID_DOC_NUMBER = 0;
	public static final Integer EVENT_SUBSCRIBER_EXIST = 1;
	public static final Integer EVENT_SUBSCRIBER_NOT_EXIST = 2;
	public static final Integer EVENT_SUBSCRIBER_VALIDATION_ERROR = 3;

	/**
	 * QUEUES JNDI
	 * */
	public static final String UPDATER_QUEUE = "spmUpdaterQueue";

	public static final String FLOW_MANAGER_QUEUE = "spmFlowManagerQueue"; /*
																			 * Enviar
																			 * aca
																			 * la
																			 * cancelacion
																			 * y
																			 * firma
																			 */

	public static final String MTS_INTERFACE_QUEUE = "jms/spmMtsInterfaceQueue";

	public static final String REVERSION_PROCESS_QUEUE = "jms/spmReversionProcessQueue";

	public static final String NOTIFICATION_QUEUE ="jms/spmNotificationQueue";

	public static final String TRANSFER_PROCESS_QUEUE = "jms/spmTransferProcessQueue";

	public static final String ASYNC_UPDATER_QUEUE = "jms/spmAsyncUpdaterQueue";

	public static final String FILE_RECEIVER_QUEUE = "jms/spmGuiFileReceiverQueue";
	
	public static final String MIME_TYPES_ALLOWED = "mimeTypesAllowed";
	
	public static final String EXTENSIONS_ALLOWED = "extensionsAllowed";

	public static final String MIME_TEXT_PLAIN = "text/csv"; //LINUX
	public static final String MIME_TEXT_PLAIN_EXCEL = "application/vnd.ms-excel"; //MICROSOFT
	public static final String MIME_COMMA_SEPARATED_VALUES = "text/comma-separated-values"; //MAC

	public static final String TIMEENDDAY = " 23:59:00";

	public static final int MAX_RESULT = 50;

	public static final Long ID_MENU_PAYMENTORDER_EDIT = 23L;

	public static final String DEFAULT_LOCALE = "es";

	/*Password por defecto de los usuarios*/
	public static final String DEFAULT_PASSWORD_USER = ".cambiar.";

	public static final String DEFAULT_PASS_EXPIRATION_TIME = "720";

	/*Endpoint por defecto del url del coreService*/
	public static final String DEFAULT_CORE_ENDPOINT = "http://localhost:8081/SPM-CoreServices/core/v1/";

	public static final String DEFAULT_PINLOGIN_VALIDATEPERIOD = "30";
	public static final String DEFAULT_PINLOGIN_MAXATTEMPT = "5";


	/*URL de los servicios del Core*/
	public static final String SYSTEM_PARAMETER_CTX_ENDPOINT_CORE_URL="CoreEndpointUrl";

	public static final String OK_CORE="00";
	public static final String NOT_AVAILABLE="Core No Disponible";
	public static final String RESPONSE_TIMEOUT="maxWaitResponse";

	//Parametro del sistema - expresion regular de la cuenta a registrar en la empresa
	public static final String ACCOUNT_REG_EXPR="accountRegExpr";
	public static final String PHONE_REG_EXPR="phoneNumberRegExpr";

	//Valor por defecto de la expresion regular de la cuenta a registrar en la empresa
	public static final String ACCOUNT_DEF_REG_EXPR = "^[0-9]{4,10}$";
	public static final String PHONE_DEF_REG_EXPR="^[0-9]{4,10}$";
	public static final String DEFAULT_FIXABLE_NUMBER_REG_EXPR = "^9[0-9]{8,8}";
	public static final String DEFAULT_PREFIX_NUMBER_VALIDATION = "0";

	public static final String REG_EXPR_EMAIL = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";


	//Parametro del sistema - monto minimo y maximo al crear/modificar un borrador
	public static final String DRAFT_MIN_AMOUNT_VALUE= "draftMinAmountValue";
	public static final String DRAFT_MAX_AMOUNT_VALUE= "draftMaxAmountValue";

	public static final String DRAFT_DEF_MIN_AMOUNT_VALUE = "1";
	public static final String DRAFT_DEF_MAX_AMOUNT_VALUE = "1000000";

	//CSV Fixable Number
	public static final String PREFIX_NUMBER_VALID_PAR = "prefixNumberValidation";
	public static final String REG_EXPR_FIX_NUMBER_PAR = "fixableNumberRegExpr";

	//Mensajes de evento de logueo
	public static final String MESSAGE_SUCCES_LOGIN = "Logueo exitoso";
	public static final String MESSAGE_FAILED_LOGIN = "Logueo fallido";

	//CSV default max size value
	public static final String CSVFILE_DEF_VALUE = "5242880";

	public static final int MAX_LENGTH_AUDIT_PARAMS = 1500;

	public static final String RECOVERY_URL_PARAM_IP = "[IP]";
	public static final String RECOVERY_URL_PARAM_SCHEME = "[SCHEME]";
	public static final String RECOVERY_URL_PARAM_PORT = "[PORT]";
	public static final String RECOVERY_URL_PARAM_CONTEXT_PATH = "[CONTEXT_PATH]";

	public static final String AUHT2FACTOR_MESSAGE_PARAM_PIN = "[PIN]";

}

