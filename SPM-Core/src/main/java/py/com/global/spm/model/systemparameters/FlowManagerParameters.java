package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum FlowManagerParameters {
	
	MAX_APPROVAL_FAILED(SpmProcesses.FLOW_MANAGER, "maxApprovalFailed", -1),
	MAX_APPROVAL_TIME(SpmProcesses.FLOW_MANAGER, "maxApprovalTime", -1),
	SMS_PIN_CASE_SENSITIVE(SpmProcesses.FLOW_MANAGER, "smsPinCaseSensitive", 0), //0 = no, 1 = yes 
	;
	
	private Long idProcess;
	private String parameterName;
	private long defaultValue;

	private FlowManagerParameters(Long IdProcess, String parameterName,
			int defaultValue) {
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

	public long getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(long defaultValue) {
		this.defaultValue = defaultValue;
	}

}
