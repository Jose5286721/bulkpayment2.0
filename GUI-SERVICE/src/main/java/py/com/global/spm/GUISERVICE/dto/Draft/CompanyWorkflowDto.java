package py.com.global.spm.GUISERVICE.dto.Draft;

/**
 * @author christiandelgado on 18/06/18
 * @project GOP
 */
public class CompanyWorkflowDto {

    private Long idCompany;
    private Long idWorkflow;

    public CompanyWorkflowDto() {
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public Long getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(Long idWorkflow) {
        this.idWorkflow = idWorkflow;
    }
}
