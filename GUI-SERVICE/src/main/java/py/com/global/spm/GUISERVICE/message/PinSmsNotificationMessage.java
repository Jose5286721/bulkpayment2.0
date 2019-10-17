package py.com.global.spm.GUISERVICE.message;

import java.io.Serializable;

public class PinSmsNotificationMessage implements Serializable {
    private static final long serialVersionUID = 8316213115456634926L;

    private Long idUser;
    private String phonenumberChr;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPhonenumberChr() {
        return phonenumberChr;
    }

    public void setPhonenumberChr(String phonenumberChr) {
        this.phonenumberChr = phonenumberChr;
    }
}
