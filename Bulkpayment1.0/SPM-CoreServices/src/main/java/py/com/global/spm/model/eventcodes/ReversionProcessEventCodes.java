package py.com.global.spm.model.eventcodes;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum ReversionProcessEventCodes {

	SUCCESS(0L, "Success"),
	TIMEOUT(1L, "Timeout"),
	WITHOUT_PAYMENTS(2L, "Without payment to revert"),
	DUPLICATE_REVERSION(3L, "Duplicate request reversion."),
	ERROR_UNDETERMINED(4L, "Error undetermined.");

	private Long idEventCode;
	private String description;

	private ReversionProcessEventCodes(Long idEventCode, String description) {
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
	
	public boolean sonIguales(ReversionProcessEventCodes other){
		return this.idEventCode == other.getIdEventCode();
	}

}
