package py.com.global.spm.GUISERVICE.dto.User;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserEditDto {
    @NotNull(message = "0030") @Digits(integer = 19, fraction = 0)
    private Long iduserPk;
    //@NotNull(message = "0030") @Digits(integer = 19, fraction = 0)
    private List<Long> idcompany;
    @NotNull(message = "0030") @Email(message = "0038") @Size(max = 100, message = "0037")
    private String emailChr;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String usernameChr;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userlastnameChr;
    @NotNull(message = "0030") @Size(max = 50, message = "0037")
    private String userjobtitleChr;
    @NotNull(message = "0030") @Size(max = 2, message = "0037")
    private String stateChr;
    @NotNull(message = "0030") @Size(max = 20, message = "0037")
    private String phonenumberChr;

    private String temporaryPasswordChr;
    @Size(max = 15, message = "0037")
    private String creationdateTim;
    private Boolean smssignNum;
    @Size(max = 50, message = "0037")
    private String smspinChr;
    private String codigoequipohumanoNum;
    private Boolean notifysmsNum;
    private Boolean notifyemailNum;
    private Boolean canviewcompanycreditNum;
    @NotNull(message = "0030") @Digits(integer = 19, fraction = 0)
    private Long rolId;
    private Boolean esFirmante;

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

    public String getTemporaryPasswordChr() {
        return temporaryPasswordChr;
    }

    public void setTemporaryPasswordChr(String temporaryPasswordChr) {
        this.temporaryPasswordChr = temporaryPasswordChr;
    }

    public boolean isSmssignNum() { return smssignNum; }

    public void setSmssignNum(boolean smssignNum) { this.smssignNum = smssignNum; }

    public String getSmspinChr() { return smspinChr; }

    public void setSmspinChr(String smspinChr) { this.smspinChr = smspinChr; }

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

    public List<Long> getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(List<Long> idcompany) {
        this.idcompany = idcompany;
    }

    public Boolean getSmssignNum() {
        return smssignNum;
    }

    public void setSmssignNum(Boolean smssignNum) {
        this.smssignNum = smssignNum;
    }

    public Boolean getNotifysmsNum() {
        return notifysmsNum;
    }

    public void setNotifysmsNum(Boolean notifysmsNum) {
        this.notifysmsNum = notifysmsNum;
    }

    public Boolean getNotifyemailNum() {
        return notifyemailNum;
    }

    public void setNotifyemailNum(Boolean notifyemailNum) {
        this.notifyemailNum = notifyemailNum;
    }

    public Boolean getCanviewcompanycreditNum() {
        return canviewcompanycreditNum;
    }

    public void setCanviewcompanycreditNum(Boolean canviewcompanycreditNum) {
        this.canviewcompanycreditNum = canviewcompanycreditNum;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

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

    public String getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(String creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public Boolean getEsFirmante() {
        return esFirmante;
    }

    public void setEsFirmante(Boolean esFirmante) {
        this.esFirmante = esFirmante;
    }
}
