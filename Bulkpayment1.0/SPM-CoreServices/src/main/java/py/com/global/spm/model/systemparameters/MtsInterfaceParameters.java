package py.com.global.spm.model.systemparameters;


import py.com.global.spm.model.utils.SpmProcesses;

/**
 * 
 * @author R2
 * 
 */
public enum MtsInterfaceParameters {

	ENDPOINT(SpmProcesses.MTS_TRANSFER_INTERFACE, "mtsEndpoint"),
	LIMITED_AMOUNT(SpmProcesses.MTS_TRANSFER_INTERFACE, "mtsLimitedAmount"),
	CURRENCY(SpmProcesses.MTS_TRANSFER_INTERFACE, "mtsCurrency"),
	COMISION_WALLET(SpmProcesses.MTS_TRANSFER_INTERFACE, "mtsComisionWallet"),
	IS_DUMMY(SpmProcesses.MTS_TRANSFER_INTERFACE, "isDummy"),
	ID_COMPANY_FOR_AGENT_PAYMENT(SpmProcesses.MTS_TRANSFER_INTERFACE, "idCompanyForAgentPayment"),
	TRX_INFO_KEY1(SpmProcesses.MTS_TRANSFER_INTERFACE, "trxInfoKey1"),
	TRX_INFO_KEY2(SpmProcesses.MTS_TRANSFER_INTERFACE, "trxInfoKey2"),
	ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO(SpmProcesses.MTS_TRANSFER_INTERFACE, "idCompanyForReposicionGuardaSaldo");
	

	private Long idProcess;
	private String parameterName;
	
	private MtsInterfaceParameters(Long IdProcess, String parameterName) {
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