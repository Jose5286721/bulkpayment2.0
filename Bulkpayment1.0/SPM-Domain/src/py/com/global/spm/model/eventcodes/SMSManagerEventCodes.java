package py.com.global.spm.model.eventcodes;


import py.com.global.spm.domain.utils.NotificationEventEnum;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum SMSManagerEventCodes {

	SUCCESS(0L, "Success"), 
	INVALID_MESSAGE(1L, "Invalid Message"), 
	PAYMENT_ORDER_NOT_FOUND(2L, "Payment order not found"), 
	UNKNOW_USER(3L, "Unknow user"), 
	UNKNOW_DESTINATION(4L, "Unknow destination"), 
	UNKNOW_USER_PREFIX(5L, "Invalid user prefix"), 
	INVALID_ID_PAYMENT_ORDER(6L, "Invalid id payment order");

	private Long idEventCode;
	private String description;

	private SMSManagerEventCodes(Long idEventCode, String description) {
		this.idEventCode = idEventCode;
		this.description = description;
	}

	public Long getIdEventCode() {
		return idEventCode;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return "[idEventCode=" + idEventCode + ", description=" + description
				+ "]";
	}

	public boolean sonIguales(SMSManagerEventCodes other) {
		return this.idEventCode == other.getIdEventCode();
	}

	public static NotificationEventEnum parseMessage(SMSManagerEventCodes event) {
		switch (event) {
		case PAYMENT_ORDER_NOT_FOUND:
			// no se encontro orden de pago especificado en el sms
			return NotificationEventEnum.NOTIFY_PAYMENT_ORDER_NOT_FOUND;
		case UNKNOW_USER:
			// el usuario no se encuentra registrado en el sistema
			return NotificationEventEnum.NOTIFY_UNKNOW_USER;
		case UNKNOW_DESTINATION:
			// el corto no corresponde al sistema
			return NotificationEventEnum.NOTIFY_UNKNOW_DESTINATION;
		case UNKNOW_USER_PREFIX:
			// prefijo desconocido (no es 595)
			return NotificationEventEnum.NOTIFY_UNKNOW_USER_PREFIX;
		case INVALID_ID_PAYMENT_ORDER:
			// el id orden de pago invalido (no es numerico) 
			return NotificationEventEnum.NOTIFY_INVALID_ID_PAYMENT_ORDER;
		default:
			// el sms incorrecto (no tiene formato idpo pin)
			return NotificationEventEnum.NOTIFY_INVALID_MESSAGE;
		}
	}

}
