package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

public class CBUser {
    @Size(max = 100, message = "0037")
    private String emailChr;
    @Size(max = 30, message = "0037")
    private String usernameChr;
    @Size(max = 2, message = "0037")
    private String stateChr;
    @Digits(integer = 19, fraction = 0)
    private Long companyId;

    public String getEmailChr() { return emailChr; }

    public void setEmailChr(String emailChr) { this.emailChr = emailChr; }

    public String getUsernameChr() { return usernameChr; }

    public void setUsernameChr(String usernameChr) { this.usernameChr = usernameChr; }

    public String getStateChr() { return stateChr; }

    public void setStateChr(String stateChr) { this.stateChr = stateChr; }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CBUser{" +
                "emailChr='" + emailChr + '\'' +
                ", usernameChr='" + usernameChr + '\'' +
                ", stateChr='" + stateChr + '\'' +
                ", idcompanyPk=" + companyId +
                '}';
    }
}
