package py.com.global.spm.model.systemparameters;


import py.com.global.spm.model.utils.SpmProcesses;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum CleanTimeoutPaymentParameters {
	
	SLEEP_TIME(SpmProcesses.PAYMENTORDER_CLEANER, "sleepTime"),
	RELOAD_PARAMETERS_TIME(SpmProcesses.PAYMENTORDER_CLEANER, "reloadParametersTime"),
	// lino
	CACHED_SESSION_TIMEOUT(SpmProcesses.PAYMENTORDER_CLEANER, "cachedSessionTimeout");
	
	
	private Long idProcess;
	private String parameterName;

	private CleanTimeoutPaymentParameters(Long IdProcess, String parameterName) {
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
