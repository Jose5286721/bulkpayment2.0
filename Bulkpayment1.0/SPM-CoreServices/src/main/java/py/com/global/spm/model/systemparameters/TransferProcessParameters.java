package py.com.global.spm.model.systemparameters;
import py.com.global.spm.model.utils.SpmProcesses;


/**
 * 
 * @author R2
 * 
 */
public enum TransferProcessParameters {

	VALIDATION_AMOUNT(SpmProcesses.TRANSFER_PROCESS, "validationAmount"), //"Y" para si y "N" para no.
	TIMEOUT(SpmProcesses.TRANSFER_PROCESS, "timeOut"), //Tiempo maximo 	que una orden de pago pasa en el hash.
	DELAY(SpmProcesses.TRANSFER_PROCESS, "delay"),
	TRX_RUN(SpmProcesses.TRANSFER_PROCESS, "trxRun"),
	DELAY_RUN(SpmProcesses.TRANSFER_PROCESS, "delayRun"),
	WAIT_RESPONSE(SpmProcesses.TRANSFER_PROCESS, "waitResponse"),
	MAX_WAIT_RESPONSE(SpmProcesses.TRANSFER_PROCESS, "maxWaitResponse");
	

	private Long idProcess;
	private String parameterName;
	
	private TransferProcessParameters(Long IdProcess, String parameterName) {
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
