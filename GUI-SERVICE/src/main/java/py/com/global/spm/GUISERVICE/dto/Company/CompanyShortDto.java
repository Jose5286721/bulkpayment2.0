package py.com.global.spm.GUISERVICE.dto.Company;


import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

@JsonView({ScopeViews.Basics.class,ScopeViews.Details.class})
public class CompanyShortDto {
    private Long idcompanyPk;
    private String companynameChr;

    public CompanyShortDto(Long idcompanyPk, String companynameChr) {
        this.idcompanyPk = idcompanyPk;
        this.companynameChr = companynameChr;
    }

    public Long getIdcompanyPk() {
        return idcompanyPk;
    }

    public void setIdcompanyPk(Long idcompanyPk) {
        this.idcompanyPk = idcompanyPk;
    }

    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    @Override
    public String toString() {
        return "CompanyShortDto{" +
                "idcompanyPk=" + idcompanyPk +
                ", companynameChr='" + companynameChr + '\'' +
                '}';
    }
}
