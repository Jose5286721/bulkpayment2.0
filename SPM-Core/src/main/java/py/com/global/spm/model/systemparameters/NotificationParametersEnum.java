package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

public enum NotificationParametersEnum {
	//Continua, eventos para las notificaciones
	NOTIFY_SIGN_PAID_PENDIND(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignPendingPaid"),
	NOTIFY_SIGN_UNPAID(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignUnpaid"),
	NOTIFY_SIGN_REVERSION(SpmProcesses.SPM_NOTIFICATION_MANAGER, "NotifySignRerversion");
	

	private Long idProcess;
	private String parameterName;

	NotificationParametersEnum(Long idProcess, String parameterName) {
		this.idProcess = idProcess;
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
