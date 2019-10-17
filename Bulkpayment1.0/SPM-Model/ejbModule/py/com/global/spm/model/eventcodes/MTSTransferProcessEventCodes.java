package py.com.global.spm.model.eventcodes;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum MTSTransferProcessEventCodes {

	SUCCESS(0L, "Success"),
	CONNECTION_PROBLEM(-100L, "Connection problem."),
	NO_COINCIDE_CEDULA(-2L, "No coincide cedula del suscriptor"),
	CLIENTE_EXISTE(-3L, "Datos correctos del suscriptor"),
	CLIENTE_NO_EXISTE(-4L,"Cliente no existe en MTS"),
	ERROR_VALIDACION(-5L,"Error al validar suscriptor en MTS");
	

	private Long idEventCode;
	private String description;

	private MTSTransferProcessEventCodes(Long idEventCode, String description) {
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
	
	public boolean sonIguales(MTSTransferProcessEventCodes other){
		return this.idEventCode == other.getIdEventCode();
	}

}
