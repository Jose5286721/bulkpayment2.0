package py.com.global.model.systemparameters;


import py.com.global.model.util.SpmProcesses;

/**
 * 
 * @author Lino Chamorro
 *
 */
public enum SMSManagerParameters {
	
	SMSC_ADDRESS(SpmProcesses.SMS_MANAGER, "SMSCAddress"),
	CARRIER(SpmProcesses.SMS_MANAGER, "Carrier"),
	SYSTEM_ID(SpmProcesses.SMS_MANAGER, "SystemID"),
	ADDR_RANGE(SpmProcesses.SMS_MANAGER, "AddrRange"),
	SYSTEM_PASSWORD(SpmProcesses.SMS_MANAGER, "SystemPassword"),
	SYSTEM_TYPE(SpmProcesses.SMS_MANAGER, "SystemType"),
	SERVICE_TYPE(SpmProcesses.SMS_MANAGER, "ServiceType"),
	SOURCE_TON(SpmProcesses.SMS_MANAGER, "SourceTON"),
	SOURCE_NPI(SpmProcesses.SMS_MANAGER, "SourceNPI"),
	SYSTEM_TON(SpmProcesses.SMS_MANAGER, "SystemTON"),
	SYSTEM_NPI(SpmProcesses.SMS_MANAGER, "SystemNPI"),
	SYSTEM_NUMBER(SpmProcesses.SMS_MANAGER, "SystemNumber"),
	CONNECT(SpmProcesses.SMS_MANAGER, "Connect"),
	SMSC_DUMMY(SpmProcesses.SMS_MANAGER, "SMSCDummy"),
	DEST_NUMBER(SpmProcesses.SMS_MANAGER, "DestNumber"),
	SOURCE_NUMBER(SpmProcesses.SMS_MANAGER, "SourceNumber");
	
	private Long idProcess;
	private String parameterName;

	private SMSManagerParameters(Long IdProcess, String parameterName) {
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
	
	public String toString(){
		return "idProcess=" + idProcess + ", parameterName=" + parameterName;
	}


}
