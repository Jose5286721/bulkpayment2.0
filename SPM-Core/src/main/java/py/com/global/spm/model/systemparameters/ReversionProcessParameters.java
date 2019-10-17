package py.com.global.spm.model.systemparameters;
import py.com.global.spm.model.util.SpmProcesses;


/**
 * 
 * @author R2
 * 
 */
public enum ReversionProcessParameters {

	MAX_REVERSION_TIME(SpmProcesses.REVERSION_PROCESS, "maxReversionTime"), //Tiempo maximo en horas para poder realizar la reversion de un orden de pago.
	TIMEOUT(SpmProcesses.REVERSION_PROCESS, "timeout"),
	;
	

	private Long idProcess;
	private String parameterName;
	
	private ReversionProcessParameters(Long IdProcess, String parameterName) {
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
