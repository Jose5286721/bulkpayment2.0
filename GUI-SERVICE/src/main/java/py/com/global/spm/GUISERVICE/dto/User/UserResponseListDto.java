package py.com.global.spm.GUISERVICE.dto.User;



import java.util.Date;

public class UserResponseListDto {
    private Long iduserPk;
    private String emailChr;
    private String usernameChr;
    private String stateChr;
    private String companyNameChr;
    private String userlastnameChr;
    private String phonenumberChr;
    private String codigoequipohumanoNum;
    private Date creationdateTim;
    private Boolean esFirmante;

    public String getEmailChr() { return emailChr; }

    public void setEmailChr(String emailChr) { this.emailChr = emailChr; }

    public String getUsernameChr() { return usernameChr; }

    public void setUsernameChr(String usernameChr) { this.usernameChr = usernameChr; }

    public String getStateChr() { return stateChr; }

    public void setStateChr(String stateChr) { this.stateChr = stateChr; }

    public String getUserlastnameChr() { return userlastnameChr; }

    public void setUserlastnameChr(String userlastnameChr) { this.userlastnameChr = userlastnameChr; }

    public String getPhonenumberChr() { return phonenumberChr; }

    public void setPhonenumberChr(String phonenumberChr) { this.phonenumberChr = phonenumberChr; }

    public String getCodigoequipohumanoNum() { return codigoequipohumanoNum; }

    public void setCodigoequipohumanoNum(String codigoequipohumanoNum) { this.codigoequipohumanoNum = codigoequipohumanoNum; }

    public Long getIduserPk() {
        return iduserPk;
    }

    public void setIduserPk(Long iduserPk) {
        this.iduserPk = iduserPk;
    }

    public String getCompanyNameChr() {
        return companyNameChr;
    }

    public void setCompanyNameChr(String companyNameChr) {
        this.companyNameChr = companyNameChr;
    }

    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public Boolean getEsFirmante() {
        return esFirmante;
    }

    public void setEsFirmante(Boolean esFirmante) {
        this.esFirmante = esFirmante;
    }
}
