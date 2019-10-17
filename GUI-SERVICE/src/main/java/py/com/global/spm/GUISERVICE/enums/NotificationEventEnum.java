package py.com.global.spm.GUISERVICE.enums;

public enum NotificationEventEnum {
	
	NOTIFY_SIGNER_EMAIL("PatternSignerEmail"),
	NOTIFY_SIGNER_SMS("PatternSignerSms"),
	NOTIFY_PO_CREATED("PatternPoCreated"),
	NOTIFY_PO_CANCELED("PatternPoCancelled"),
	NOTIFY_PAYMENT_BENEFICIARY("PatternPaymentBeneficiary"), // notificar pago o parcial pago a beneficiario (updater)
	NOTIFY_INSUFFICIENT_MONEY("PatternInsufficientMoney"), // notificar orden de pago pendiente de pago por falta de credito (updater)
	NOTIFY_PO_REVERSION("PatternPoReversion"), // notificar reversion o parcial reversion de orden de pago (updater)
	NOTIFY_PO_PARTIAL_REVERSION("PatternPoPartialReversion"),// notificar orden de pago parcialmente revertida.
	NOTIFY_PO_UNPAID("PatternPoUnpaid"), // notificar orden de pago no pagada (updater)
	NOTIFY_PO_UNREVERSION("PatternPoUnreversion"), // notificar orden de pago no revertida (updater) -->ByR2.
	NOTIFY_PO_SIGNATURE_SUCCESS("PatternPoSignatureSuccess"),
	NOTIFY_SIGNER_BLOCK("PatternSignerBlock"),
	NOTIFY_VALID_TIME("PatternValidTime"),
	NOTIFY_OP_GENERATION_FAILED("PatternOpGenerationFailed"),
	// Modificado lino
	NOTIFY_INCORRECT_PIN("PatternIncorrectPin"),//Codigo PIN incorrecto
	NOTIFY_OUTTIME_APPROVAL("PatternOuttimeApproval"),//OJO --> El tiempo de aprobacion ha expirado.
	// agregado lino
	NOTIFY_INVALID_PAYMENT_ORDER_STATE("PatternInvalidPoState"),// La Orden de Pago NO esta Activa.
	NOTIFY_OTHER_ERROR("PatternOtherError"),//Se ha producido un error.
	NOTIFY_NOT_SIGNER_TURN("PatternNotSignerTurn"),//OJO No es momento de su aprobacion
	NOTIFY_INCORRECT_PIN_LAST_CHANCE("PatternIncorrectPinLastChance"),//
	NOTIFY_INCORRECT_PIN_USER_BLOCKED("PatternIncorrectPinUserBlocked"),// Ha sido bloqueado. Contactese con el Administrador del Sistema
	// SMSManager
	NOTIFY_INVALID_MESSAGE("PatternInvalidMessage"),//Mensaje incorrecto
	NOTIFY_PAYMENT_ORDER_NOT_FOUND("PatternPaymentOrderNotFound"),//Orden de Pago no encontrada.
	NOTIFY_UNKNOW_USER("PatternUnknowUser"),//Usuario incorrecto.
	NOTIFY_UNKNOW_DESTINATION("PatternUnkowDestination"),//Destino desconocido
	NOTIFY_UNKNOW_USER_PREFIX("PatternUnknowPrefix"),//Prefijo desconocido
	NOTIFY_INVALID_ID_PAYMENT_ORDER("PatternInvalidIdPaymentOrder"),
	NOTIFY_PASS_RECOVERY("PatterPassRecovery"),
	NOTIFY_PAYMENT_SIGNERS("PatternPaymentSigners"),
	//
	NOTIFY_USER_CREATED("PatternUserCreated"),
	SEND_TOKEN("PatternSendToken"),
	// Notificar al creador de la OP de la aprobacion de un firmante
	NOTIFY_SIGNEVENT("PatternSignEvent");
	//Continua, eventos para las notificaciones
	//NOTIFY_SIGN_PAID_PENDIND("NotifySignPendingPaid"),
	//NOTIFY_SIGN_UNPAID("NotifySignUnpaid"),
	//NOTIFY_SIGN_REVERSION("NotifySignRerversion");
	
	private String value;

	private NotificationEventEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
