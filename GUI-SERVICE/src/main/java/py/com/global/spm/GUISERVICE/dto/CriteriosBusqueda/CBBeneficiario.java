package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Size;

public class CBBeneficiario {

    private Long idCompany;
    @Size(max = 20, message = "0037")
    private String phonenumberChr;
    private String stateChr;

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getPhonenumberChr() {
        return phonenumberChr;
    }

    public void setPhonenumberChr(String phonenumberChr) {
        this.phonenumberChr = phonenumberChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    @Override
    public String toString() {
        return "CBBeneficiario{" +
                "idCompany=" + idCompany +
                ", phonenumberChr='" + phonenumberChr + '\'' +
                ", stateChr=" + stateChr +
                '}';
    }
}
