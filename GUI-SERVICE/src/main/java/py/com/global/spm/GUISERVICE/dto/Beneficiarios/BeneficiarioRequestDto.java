package py.com.global.spm.GUISERVICE.dto.Beneficiarios;

public class BeneficiarioRequestDto {
    private Long idCompany;
    private String phoneNumber;
    private Boolean notificarSMS;
    private Boolean notificarMail;
    private String stateChr;

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getNotificarSMS() {
        return notificarSMS;
    }

    public void setNotificarSMS(Boolean notificarSMS) {
        this.notificarSMS = notificarSMS;
    }

    public Boolean getNotificarMail() {
        return notificarMail;
    }

    public void setNotificarMail(Boolean getNotificarMail) {
        this.notificarMail = getNotificarMail;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    @Override
    public String toString() {
        return "BeneficiarioRequestDto{" +
                "idCompany=" + idCompany +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notificarSMS=" + notificarSMS +
                ", getNotificarMail=" + notificarMail +
                '}';
    }
}
