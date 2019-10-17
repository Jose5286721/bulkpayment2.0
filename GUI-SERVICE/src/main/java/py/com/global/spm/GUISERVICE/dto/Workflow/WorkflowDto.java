package py.com.global.spm.GUISERVICE.dto.Workflow;

import py.com.global.spm.GUISERVICE.dto.Company.CompanyDto;


public class WorkflowDto {
	private Long idworkflowPk;
	private Long idcompany;
	private CompanyDto company;
	private String companyNameChr;
	private String workflownameChr;
	private String descriptionChr;
	private String stateChr;
	private String walletChr;
	private Boolean isLoggedSuperCompany;


	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public String getCompanyNameChr() {
		return companyNameChr;
	}

	public void setCompanyNameChr(String companyNameChr) {
		this.companyNameChr = companyNameChr;
	}

	public Boolean getLoggedSuperCompany() {
		return isLoggedSuperCompany;
	}

	public void setLoggedSuperCompany(Boolean loggedSuperCompany) {
		isLoggedSuperCompany = loggedSuperCompany;
	}

	public Long getIdcompany() {
		return idcompany;
	}
	public Boolean getIsLoggedSuperCompany() {
		return isLoggedSuperCompany;
	}
	public void setIsLoggedSuperCompany(Boolean isLoggedSuperCompany) {
		this.isLoggedSuperCompany = isLoggedSuperCompany;
	}
	public void setIdcompany(Long idcompany) {
		this.idcompany = idcompany;
	}
	public Long getIdworkflowPk() {
		return idworkflowPk;
	}
	public void setIdworkflowPk(Long idworkflowPk) {
		this.idworkflowPk = idworkflowPk;
	}
	
	public String getWorkflownameChr() {
		return workflownameChr;
	}
	public void setWorkflownameChr(String workflownameChr) {
		this.workflownameChr = workflownameChr;
	}
	public String getDescriptionChr() {
		return descriptionChr;
	}
	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}
	public String getStateChr() {
		return stateChr;
	}
	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}
	public String getWalletChr() {
		return walletChr;
	}

	public void setWalletChr(String walletChr) {
		this.walletChr = walletChr;
	}

	@Override
	public String toString() {
		return "WorkflowDto{" +
				"idworkflowPk=" + idworkflowPk +
				", idcompany=" + idcompany +
				", company=" + company +
				", companyNameChr='" + companyNameChr + '\'' +
				", workflownameChr='" + workflownameChr + '\'' +
				", descriptionChr='" + descriptionChr + '\'' +
				", stateChr=" + stateChr +
				", walletChr='" + walletChr + '\'' +
				", isLoggedSuperCompany=" + isLoggedSuperCompany +
				'}';
	}
}
