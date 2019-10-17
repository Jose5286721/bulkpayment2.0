package py.com.global.spm.model.util;


/**
 * Contiene todos los comodines para reemplazar en el mensaje de notificacion.
 * @author R2
 *
 */
public enum SpecialWordsDescripcion {
	// Comment Comverse
	
	PO_NUMBER("[PONUMBER]"),
	AMOUNT("[AMOUNT]"),
	AMOUNT_PAID("[AMOUNTPAID]"),
	VALID_TIME("[VALIDTIME]"),
	OP_TYPE("[OPTYPE]"),
	CANCELL_REASON("[CANCELLREASON]"),
	COMPANY_NAME("[COMPANYNAME]"),
	PAYMENT_NUMBER("[PAYMENTNUMBER]"),//TICKET PARA LOS BENEFICIARIOS
	ATTEMPTS_NUMBER("[ATTEMPTSNUMBER]"),//NRO INTENTOS RESTANTES
	PO_STATE("[POSTATE]"),//Estado de la orden de pago
	TOTAL_SUCCESSFUL("[TOTALSUCCESSFUL]"),//Total de OP pagadas correctamente
	PARTIAL_PAYMENT_AMOUNT("[PARTIALPAYMENTAMOUNT]"),//Total de OP pagadas parcialmente
	PAYMENT_ERROR("[PAYMENTERROR]"),//Cantidad de pagos con errores
	PAYMENT_TOTAL("[PAYMENTTOTAL]"),//Cantidad total de pagos --> CORRECTOS + PARCIALES + ERRORES
	USERNAME("[USERNAME]"),
	PIN("[PIN]"),
	PASSWORD("[PASSWORD]"),
	TOKEN("[TOKEN]");
	
	private String value;

	private SpecialWordsDescripcion(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
