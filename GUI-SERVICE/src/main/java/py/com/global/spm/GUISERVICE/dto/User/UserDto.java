package py.com.global.spm.GUISERVICE.dto.User;

import py.com.global.spm.GUISERVICE.dto.Company.CompanyShortDto;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Rol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDto {
    private Long iduserPk;
    private String emailChr;
    private String usernameChr;
    private String stateChr;
    private Company company;
    private String userlastnameChr;
    private String userjobtitleChr;
    private String phonenumberChr;
    private boolean smssignNum;
    private String codigoequipohumanoNum;
    private boolean notifysmsNum;
    private boolean notifyemailNum;
    private boolean canviewcompanycreditNum;
    private Date creationdateTim;
    private Rol rol;
    private boolean esFirmante;
    private List<CompanyShortDto> companies = new ArrayList<>();

    public String getEmailChr() { return emailChr; }

    public void setEmailChr(String emailChr) { this.emailChr = emailChr; }

    public String getUsernameChr() { return usernameChr; }

    public void setUsernameChr(String usernameChr) { this.usernameChr = usernameChr; }

    public String getUserlastnameChr() { return userlastnameChr; }

    public void setUserlastnameChr(String userlastnameChr) { this.userlastnameChr = userlastnameChr; }

    public String getUserjobtitleChr() { return userjobtitleChr; }

    public void setUserjobtitleChr(String userjobtitleChr) { this.userjobtitleChr = userjobtitleChr; }

    public String getPhonenumberChr() { return phonenumberChr; }

    public void setPhonenumberChr(String phonenumberChr) { this.phonenumberChr = phonenumberChr; }

    public boolean isSmssignNum() { return smssignNum; }

    public void setSmssignNum(boolean smssignNum) { this.smssignNum = smssignNum; }

    public String getCodigoequipohumanoNum() { return codigoequipohumanoNum; }

    public void setCodigoequipohumanoNum(String codigoequipohumanoNum) {
        this.codigoequipohumanoNum = codigoequipohumanoNum;
    }

    public boolean isNotifysmsNum() { return notifysmsNum; }

    public void setNotifysmsNum(boolean notifysmsNum) { this.notifysmsNum = notifysmsNum; }

    public boolean isNotifyemailNum() { return notifyemailNum; }

    public void setNotifyemailNum(boolean notifyemailNum) { this.notifyemailNum = notifyemailNum; }

    public boolean isCanviewcompanycreditNum() { return canviewcompanycreditNum; }

    public void setCanviewcompanycreditNum(boolean canviewcompanycreditNum) { this.canviewcompanycreditNum = canviewcompanycreditNum; }

    public Long getIduserPk() {
        return iduserPk;
    }

    public void setIduserPk(Long iduserPk) {
        this.iduserPk = iduserPk;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEsFirmante() {
        return esFirmante;
    }

    public void setEsFirmante(boolean esFirmante) {
        this.esFirmante = esFirmante;
    }

    public List<CompanyShortDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyShortDto> companies) {
        this.companies = companies;
    }
}
