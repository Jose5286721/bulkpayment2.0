package py.com.global.spm.GUISERVICE.dto.Workflow;

import py.com.global.spm.GUISERVICE.dto.Company.CompanyDto;
import py.com.global.spm.GUISERVICE.dto.User.UserResponseListDto;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
public class WorkflowResponseDto {
    @Digits(integer = 19, fraction = 0)
    private Long idworkflowPk;
    @Digits(integer = 19, fraction = 0)
    private Long idcompany;
    private CompanyDto company;
    @Size(max = 30, message = "0037")
    private String companyNameChr;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String workflownameChr;
    @Size(max = 100, message = "0037")
    private String descriptionChr;
    @NotNull(message = "0030")
    private String stateChr;
    @Size(max = 20, message = "0037")
    private String walletChr;
    private Boolean isLoggedSuperCompany;
    @NotNull(message = "0030")
    private List<UserResponseListDto>listaFirmantes;

    public List<UserResponseListDto> getListaFirmantes() {
        return listaFirmantes;
    }

    public void setListaFirmantes(List<UserResponseListDto> listaFirmantes) {
        this.listaFirmantes = listaFirmantes;
    }

    public Long getIdworkflowPk() {
        return idworkflowPk;
    }

    public void setIdworkflowPk(Long idworkflowPk) {
        this.idworkflowPk = idworkflowPk;
    }

    public Long getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Long idcompany) {
        this.idcompany = idcompany;
    }

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

    public Boolean getLoggedSuperCompany() {
        return isLoggedSuperCompany;
    }

    public void setLoggedSuperCompany(Boolean loggedSuperCompany) {
        isLoggedSuperCompany = loggedSuperCompany;
    }

    @Override
    public String toString() {
        return "WorkflowResponseDto{" +
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
