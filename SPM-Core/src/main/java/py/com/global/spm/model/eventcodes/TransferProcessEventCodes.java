package py.com.global.spm.model.eventcodes;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum TransferProcessEventCodes {

	SUCCESS(0L, "Success"),
	TIMEOUT(1L, "Timeout");

	private Long idEventCode;
	private String description;

	private TransferProcessEventCodes(Long idEventCode, String description) {
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
	
	public boolean sonIguales(TransferProcessEventCodes other){
		return this.idEventCode == other.getIdEventCode();
	}

}
