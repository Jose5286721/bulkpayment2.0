package py.com.global.spm.model.mts;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum MTSReversionProcessEventCodes {

	SUCCESS(0L, "Success"),
	CONNECTION_PROBLEM(-100L, "Connection problem."),
	INVALID_STATE(-101L, "Invalid state.");

	private Long idEventCode;
	private String description;

	private MTSReversionProcessEventCodes(Long idEventCode, String description) {
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
	
	public boolean sonIguales(MTSReversionProcessEventCodes other){
		return this.idEventCode == other.getIdEventCode();
	}

}
