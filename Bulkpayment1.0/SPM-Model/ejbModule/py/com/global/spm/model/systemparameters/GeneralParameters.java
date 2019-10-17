package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

public enum GeneralParameters {
	
	UPDATE_TIME(SpmProcesses.SPM_GENERAL, "UpdateTime"); //Tiempo para actualizar el cache.
	
	
	private Long idProcess;
	private String parameterName;

	private GeneralParameters(Long IdProcess, String parameterName) {
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
