package py.com.global.spm.model.eventcodes;

public enum NotificationManagerEventCodes {
	SUCCESS(0L, "Success"),
	ID_USER_NULL(1L,"Id usuario null"),
	ID_BENEFICIARY_NULL(2L, "Id beneficiario null"),
	PHONE_NUMBER_NULL(3L,"Numero tel null"),
	EMAIL_NULL(4L, "Falta Email"),
	SHORT_NUMBER(5L, "Falta numero corto"),
	MESSAGE_NULL(6L, "Falta mensaje de notificacion"),
	UNKWON_PATTERN(7L, "Plantilla desconocida"),
	EVENT_CODE_NULL(8L, "Falta Evento de notificacion"),
	FAIL_NOTIFICATION(9L, "Notificacion fallida"),
	WITHOUT_DESTINATION(10L, "No existen destinatarios"),
	OTHER_ERROR(11L, "Other error"),
	WS_ERROR_CONNECTION(12L, "Error al conectar con el WS.");
	
	private Long idEventCode;
	private String description;
	
	private NotificationManagerEventCodes(Long idEventCode, String description){
		this.idEventCode=idEventCode;
		this.description=description;
	}
	
	
	public Long getIdEventCode() {
		return idEventCode;
	}
	public void setIdEventCode(Long idEventCode) {
		this.idEventCode = idEventCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String toString() {
		return "[idEventCode=" + idEventCode + ", description=" + description
				+ "]";
	}
	
	

}
