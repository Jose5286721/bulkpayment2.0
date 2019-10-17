package py.com.global.spm.model.systemparameters;

public enum NotificationCenterWsParameters {
	
	/*
	 * Parametros necesarios para invocar al Ws de notificaciones
	 * */
	EMAIL_PLATAFORM("emailPlataform"),
	EMAIL_PROCESS("emailProcess"),
	SMS_PLATAFORM("smsPlataform"),
	SMS_PROCESS("smsProcess"),
	CONSUMER_CODE("consumerCode"),
	CONSUMER_NAME("consumerName"),
	SERVICE_NAME("serviceName"),
	SERVICE_CODE("serviceCode"),
	TRANSPORT_NAME("transportName"),
	TRANSPORT_CODE("transportCode"),
	CONVERSATION_ID("conversationId"),//long
	MESSAGE_ID("messageId"),//long
	MESSAGE_ID_CORRELATION("messageIdCorrelation"),//long
	COUNTRY_ISO_CODE("countryIsoCode"),
    COUNTRY_NAME("countryName"),
	MESSAGE("message"),
	FROM("from"),
	FROM_VALUE("fromValue"),
	SUBJECT("subject"),
	SUBJECT_VALUE("subjectValue"),
	SHORT_NUMBER("shortnumber"),
	SHORT_NUMBER_VALUE("shortnumberValue"),
	NC_ENDPOINT("notificationCenterEndPoint"),
	NC_ENDPOINT2("notificationCenterEndPoint2");
	
	private String value;

	private NotificationCenterWsParameters(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
