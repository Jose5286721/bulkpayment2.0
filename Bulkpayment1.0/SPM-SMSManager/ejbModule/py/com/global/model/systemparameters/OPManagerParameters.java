package py.com.global.model.systemparameters;

import py.com.global.model.util.SpmProcesses;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum OPManagerParameters {
	
	SLEEP_TIME(SpmProcesses.PO_MANAGER, "sleepTime"),
	RELOAD_PARAMETERS_TIME(SpmProcesses.PO_MANAGER, "reloadParametersTime"),
	WINDOW1_FROM(SpmProcesses.PO_MANAGER, "window1_from"),
	WINDOW1_TO(SpmProcesses.PO_MANAGER, "window1_to"),
	WINDOW2_FROM(SpmProcesses.PO_MANAGER, "window2_from"),
	WINDOW2_TO(SpmProcesses.PO_MANAGER, "window2_to"),
	GLOBAL_AMOUNT_BY_PAYMENT_ORDER(SpmProcesses.PO_MANAGER, "globalAmountByPaymentOrder"),
	GLOBAL_AMOUNT_BY_PAYMENT(SpmProcesses.PO_MANAGER, "globalAmountByPayment"),
	FILE_PREFIX(SpmProcesses.PO_MANAGER, "filePrefix"),
	FILE_PATH(SpmProcesses.PO_MANAGER, "filePath"),
	MIME_TYPE(SpmProcesses.PO_MANAGER, "mimeType");
	
	private Long idProcess;
	private String parameterName;

	private OPManagerParameters(Long IdProcess, String parameterName) {
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
