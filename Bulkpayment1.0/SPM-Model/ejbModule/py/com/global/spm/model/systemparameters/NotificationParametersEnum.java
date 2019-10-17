package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

public enum NotificationParametersEnum {
	
	//Id de proceso y evento de notificacion para las notificaciones
	NOTIFY_SIGNER_EMAIL(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternSignerEmail"),
	NOTIFY_SIGNER_SMS(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternSignerSms"),
	NOTIFY_PO_CREATED(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPoCreated"),
	NOTIFY_PO_CANCELLED(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPoCanceled"),
	NOTIFY_PAYMENT_BENEFICIARY(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPaymentBeneficiary"),
	NOTIFY_INSUFFICIENT_MONEY(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternInsufficientMoney"),
	NOTIFY_PO_REVERSION(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPoReversion"),
	NOTIFY_PO_SIGNATURE_SUCCESS(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPoSignatureSuccess"),
	NOTIFY_SIGNER_BLOCK(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternSignerBlock"),
	NOTIFY_VALID_TIME(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternValidTime"),
	NOTIFY_OP_GENERATION_FAILED(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternOpGenerationFailed"),
	NOTIFY_INCORRECT_PIN(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternIncorrectPin"),
	NOTIFY_OUTTIME_APPOVAL(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternOuttimeApproval"),
	
	NOTIFY_UNKWOW_PATTERN(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternUnkwon"),
	
	//Agregado lino
	NOTIFY_INVALID_PAYMENT_ORDER_STATE(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternInvalidPoState"),
	NOTIFY_OTHER_ERROR(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternOtherError"),
	NOTIFY_NOT_SIGNER_TURN(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternNotSignerTurn"),
	NOTIFY_INCORRECT_PIN_LAST_CHANCE(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternIncorrectPinLastChance"),
	NOTIFY_INCORRECT_PIN_USER_BLOCKED(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternIncorrectPinUserBlocked"),
	// SMSManager
	NOTIFY_INVALID_MESSAGE(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternInvalidMessage"),
	NOTIFY_UNKNOW_USER(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternUnknowUser"),
	NOTIFY_UNKNOW_DESTINATION(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternUnkowDestination"),
	NOTIFY_UNKNOW_USER_PREFIX(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternUnknowUser"),
	NOTIFY_INVALID_ID_PAYMENT_ORDER(SpmProcesses.SPM_NOTIFICATION_MANAGER,"PatternInvalidIdPaymentOrder"),
	NOTIFY_PAYMENT_ORDER_NOT_FOUND(SpmProcesses.SPM_NOTIFICATION_MANAGER, "PatternPaymentOrderNotFound"),
	
	//Id de proceso y evento de notificacion para el request header y request body del Ws de notificaciones
	EMAIL_PLATAFORM(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.EMAIL_PLATAFORM.getValue()),
	EMAIL_PROCESS(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.EMAIL_PROCESS.getValue()),
	SMS_PLATAFORM(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SMS_PLATAFORM.getValue()),
	SMS_PROCESS(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SMS_PROCESS.getValue()),
	CONSUMER_CODE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.CONSUMER_CODE.getValue()),
	CONSUMER_NAME(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.CONSUMER_NAME.getValue()),
	SERVICE_NAME(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SERVICE_NAME.getValue()),
	SERVICE_CODE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SERVICE_CODE.getValue()),
	
	TRANSPORT_CODE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.TRANSPORT_CODE.getValue()),
	TRANSPORT_NAME(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.TRANSPORT_NAME.getValue()),
	CONVERSATION_ID(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.CONVERSATION_ID.getValue()),
	MESSAGE_ID(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.MESSAGE_ID.getValue()),
	MESSAGE_ID_CORRELATION(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.MESSAGE_ID_CORRELATION.getValue()),
	COUNTRY_ISO_CODE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.COUNTRY_ISO_CODE.getValue()),
	COUNTRY_NAME(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.COUNTRY_NAME.getValue()),
	MESSAGE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.MESSAGE.getValue()),
	FROM(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.FROM.getValue()),
	FROM_VALUE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.FROM_VALUE.getValue()),
	SUBJECT(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SUBJECT.getValue()),
	SUBJECT_VALUE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SUBJECT_VALUE.getValue()),
	SHORT_NUMBER(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SHORT_NUMBER.getValue()),
	SHORT_NUMBER_VALUE(SpmProcesses.SPM_NOTIFICATION_MANAGER, NotificationCenterWsParameters.SHORT_NUMBER_VALUE.getValue()),
	
	//Continua, eventos para las notificaciones
	NOTIFY_SIGN_PAID_PENDIND(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignPendingPaid"),
	NOTIFY_SIGN_UNPAID(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignUnpaid"),
	NOTIFY_SIGN_REVERSION(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignRerversion");
	

	private Long idProcess;
	private String parameterName;

	private NotificationParametersEnum(Long IdProcess, String parameterName) {
		this.idProcess = IdProcess;
		this.parameterName = parameterName;
	}

	public Long getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(Long idProcess) {
		this.idProcess = idProcess;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parametersName) {
		this.parameterName = parametersName;
	}
}
