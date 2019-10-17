package py.com.global.spm.model.util;

/**
 * 
 * @author R2
 * 
 */
public class SpmConstants {

	public static final String FILE_DATE_HOUR_PATTERN = "yyyyMMdd_HHmmss";

	public static final String TIME_STAMP_PATTERN = "dd-MM-yyyy HH:mm:ss,SSS";

	// Valores condicionales
	public static final String YES = "Y";
	public static final String NO = "N";

	/**
	 * Parametro aplicable solo a SystemParameters
	 */

	public static final String ACTIVE = "AC";

	public static final String INACTIVO = "IN";

	public static final String CANCELED = "C";

	public static final String PENDING = "P";

	public static final String ERROR = "ER";

	public static final String SUCCESS = "SU";

	public static final String REVERT = "RE";

	/**
	 * Datasource JNDI name
	 */

	//before: java:spm

	//public static final String DATASOURCE = "java:jboss/datasources/spm-ds";
	public static final String DATASOURCE = "java:jboss/datasources/SPM-GUIDatasource";
	public static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	public static final String DEFAULT_DESTINATION = "jms/spmNotificationQueue";
	public static final String DS = "java:mts-subscriber-ds";

	public static final String SPM_PU = "SPM-Domain-PU";
	public static final String SPM_MTS_PU = "SPM-MTS-PU";

	/**
	 * Eventos de notificacion--> NOTIFICATION_EVENT_CHR
	 */
	public static final String NOTIFY_SIGNER_EMAIL = "SIGNER_EMAIL";
	public static final String NOTIFY_SIGNER_SMS = "SIGNER_SMS";
	public static final String NOTIFY_PAYMENT_BENEFICIARY = "PAYMENT_BENEFICIARY";
	public static final String NOTIFY_PO_CREATED = "PAYMENT_ORDER_CREATED";
	public static final String NOTIFY_PO_CANCELLED = "PAYMENT_ORDER_CANCELLED";
	public static final String NOTIFY_INSUFFICIENT_MONEY = "INSUFFICIENT_MONEY";
	public static final String NOTIFY_PO_REVERSION = "PAYMENT_ORDER_REVERSION";
	public static final String NOTIFY_PO_PARTIAL_REVERSION = "PO_PARTIAL_REVERSION";
	public static final String NOTIFY_PO_UNREVERSION = "PO_UNREVERSION";
	public static final String NOTIFY_PO_UNPAID = "PAYMENT_ORDER_UNPAID";
	public static final String NOTIFY_PO_SIGNATURE_SUCCESS = "PO_SIGNATURE_SUCCESS";
	public static final String NOTIFY_SIGNER_BLOCK = "SIGNER_BLOCK";
	public static final String NOTIFY_VALID_TIME = "VALID_TIME";
	public static final String NOTIFY_OP_GENERATION_FAILED = "PAYMENT_ORDER_GENERATION_FAILED";
	public static final String NOTIFY_INCORRECT_PIN = "INCORRECT_PIN";
	// Evento de notificacion del Pass Recovery
	public static final String NOTIFY_PASS_RECOVERY = "PASS_RECOVERY";

	// Evento de notificacion de creacion de un nuevo usuario
	public static final String NOTIFY_USER_CREATED = "USER_CREATED";

	// Evento de notificacion de creacion de un nuevo usuario
	public static final String SEND_TOKEN = "SEND_TOKEN";

	// public static final String NOTIFY_MESSAGE_WRONG = "MESSAGE_WRONG";

	public static final String NOTIFY_OUTTIME_APPROVAL = "OUTTIME_APPROVAL";
	public static final String NOTIFY_PAYMENT_ORDER_NOT_FOUND = "PAYMENT_ORDER_NOT_FOUND";

	public static final String NOTIFY_INVALID_PAYMENT_ORDER_STATE = "INVALID_PAYMENT_ORDER_STATE";
	public static final String NOTIFY_NOT_SIGNER_TURN = "NOT_SIGNER_TURN";
	public static final String NOTIFY_INCORRECT_PIN_LAST_CHANCE = "INCORRECT_PIN_LAST_CHANCE";
	public static final String NOTIFY_INCORRECT_PIN_USER_BLOCKED = "INCORRECT_PIN_USER_BLOCKED";
	public static final String NOTIFY_INVALID_MESSAGE = "INVALID_MESSAGE";
	public static final String NOTIFY_UNKNOW_USER = "UNKNOW_USER";
	public static final String NOTIFY_UNKNOW_DESTINATION = "UNKNOW_DESTINATION";
	public static final String NOTIFY_UNKNOW_USER_PREFIX = "UNKNOW_USER_PREFIX";
	public static final String NOTIFY_INVALID_ID_PAYMENT_ORDER = "INVALID_ID_PAYMENT_ORDER";
	public static final String NOTIFY_OTHER_ERROR = "OTHER_ERROR";
	public static final String NOTIFY_PAYMENT_SIGNERS = "PAYMENT_SIGNERS";
	public static final String NOTIFY_SIGNEVENT = "SIGNEVENT";

	// Agregado Rodolfo
	public static final String UNKNOWN_PATTERN = "UNKNOWN_PATTERN";
	public static final String FAIL_NOTIFICATION = "FAIL_NOTIFICATION";
	public static final String UNKNOW_EVENT_VALUE = "UNKNOW_EVENT_VALUE";
	public static final String NO_MESSAGE = "NO_MESSAGE";
	public static final String NO_NOTIFIED = "UNNOTIFIED";
	public static final String ERROR_WS_CONNECTION = "ERROR_WS_CONNECTION";

	// Metodos MTS
	public static final String MTS_SALARYPAYMENT = "salaryPayment";
	public static final String MTS_REVERSA = "reversePayment";

	// SMS type

	public static final String SENT = "sent";

	public static final String RECEIPT = "receipt";

	// Tipo de mensaje al MTSInterface
	public static final String MTS_OPERATION_QUERY = "Q";
	public static final String MTS_OPERATION_PAYMENT = "P";
	public static final String MTS_OPERATION_PAYMENT_RETRY = "R";

	public static final long HOUR_IN_MILLIS = 3600 * 1000;
	
	public static final String PROCESS_CONTROL_DESC = "keep alive timestamp";
	
	//EVENTOS AGREGADOS PARA VALIDAR SUSCRIPTOR CONTRA MTS
	public static final Integer EVENT_SUBSCRIBER_INVALID_DOC_NUMBER = -2;
	public static final Integer EVENT_SUBSCRIBER_EXIST = -3;
	public static final Integer EVENT_SUBSCRIBER_NOT_EXIST = -4;
	public static final Integer EVENT_SUBSCRIBER_VALIDATION_ERROR = -5;


	//ERROR_CODES
	public static final String SERVER_ERROR= "INTERNAL_SERVER_ERROR";
	public static final String STATUS_ERROR="01";

	//OK RESPONSE CODE
	public static final String STATUS_OK="00";

	public static final String OK_DSC="Envío Exitoso";

	public static final String ERROR_DSC="Envío Fallido";

	public static final String TIMEOUT="Ocurrió un Timeout";

	public static final String SEND_ERROR_FLOW_MANAGER_DSC="Envío Fallido a Queue: FlowManagerMessage";

	public static final String SEND_ERROR_TRANSFER_DSC="Envío Fallido a Queue: Transfer";

	public static final String EMPTY_ENTITY="Entidad No encontrada";

	public static final String PAYMENT_ORDER_STATE_SIGN_ERROR="Estado inválido al Firmar, debe estar con estado en Proceso";

	public static final String PAYMENT_ORDER_TURN_ERROR="Turno incorrecto del usuario en la firma";

	public static final String PAYMENT_ORDER_STATE_RETRY_ERROR="Estado incorrecto, permitidos: no pagada  o parcialmente pagada";

	public static final String PAYMENT_ORDER_STATE_CANCEL_ERROR="No se puede Cancelar, estado inválido";

	public static final String FIRMA_NO_AUTORIZADA="El usuario no es firmante de la orden de pago";

	//Queue attributes
	public static final String QUEUE_SIZE = "QueueSize";
}
