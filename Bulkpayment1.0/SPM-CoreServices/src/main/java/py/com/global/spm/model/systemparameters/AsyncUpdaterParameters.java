package py.com.global.spm.model.systemparameters;


import py.com.global.spm.model.utils.SpmProcesses;

/**
 * 
 * @author R2
 * 
 */
public enum AsyncUpdaterParameters {

	RETRIES(SpmProcesses.ASYNC_UPDATER, "retries", 3L),
	SLEEP(SpmProcesses.ASYNC_UPDATER, "sleep", 1000L);

	private Long idProcess;
	private String parameterName;
	private Long defaultValue;

	private AsyncUpdaterParameters(Long IdProcess, String parameterName,
			Long defaultValue) {
		this.idProcess = IdProcess;
		this.parameterName = parameterName;
		this.defaultValue = defaultValue;
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

	public Long getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Long defaultValue) {
		this.defaultValue = defaultValue;
	}

}
