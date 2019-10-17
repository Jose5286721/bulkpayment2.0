package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum ProcessPaymentParameters {
	
	MAX_LOGPAYMENT_SIZE(SpmProcesses.LOGPAYMENT_PROCESS, "maxLogPaymetSize");
	// lino

	
	private Long idProcess;
	private String parameterName;

	private ProcessPaymentParameters(Long IdProcess, String parameterName) {
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
