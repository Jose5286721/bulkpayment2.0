package py.com.global.spm.model.eventcodes;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum OPManagerEventCodes {

	SUCCESS(0L, "Success"),
	INACTIVE_COMPANY(1L, "Empresa no activa"),
	TRX_TOTAL_EXCEEDED(2L, "Limite total de transacciones por empresa excedida"),
	TRX_DIARY_EXCEEDED(3L, "Limite de transacciones diaras excedida"),
	TRX_MONTHLY_EXCEEDED(4L, "Limite de transacciones mensual excedida"),
	GLOBAL_AMOUNT_BY_PAYMENT_ORDER_EXCEEDED(5L, "Monto total maximo por orden de pago excedido"),
	GLOBAL_AMOUNT_BY_PAYMENT_EXCEEDED(6L, "Monto maximo por pago excedido"),
	ERROR_PERSISTING_PAYMENT_ORDER(7L, "Error persistiendo orden de pago en base de datos"),
	ERROR_GENERATE_APPROVAL_FLOW_PO(8L, "Error en la generacion del flujo de aprobacion de orden de pago"),
	ERROR_GENERATE_PAYMENT_ORDER(9L, "Problemas al generar la orden de pago");

	private Long idEventCode;
	private String description;

	private OPManagerEventCodes(Long idEventCode, String description) {
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
}
