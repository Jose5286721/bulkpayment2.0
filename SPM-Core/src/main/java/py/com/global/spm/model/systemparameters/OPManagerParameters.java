package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

import java.math.BigDecimal;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum OPManagerParameters {
	
	SLEEP_TIME(SpmProcesses.PO_MANAGER, "sleepTime", "120000"),
	GLOBAL_AMOUNT_BY_PAYMENT_ORDER(SpmProcesses.PO_MANAGER, "globalAmountByPaymentOrder", null),
	GLOBAL_AMOUNT_BY_PAYMENT(SpmProcesses.PO_MANAGER, "globalAmountByPayment", null);
	
	private Long idProcess;
	private String parameterName;
	private String defaultValue;

	private OPManagerParameters(Long IdProcess, String parameterName, String defaultValue) {
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
