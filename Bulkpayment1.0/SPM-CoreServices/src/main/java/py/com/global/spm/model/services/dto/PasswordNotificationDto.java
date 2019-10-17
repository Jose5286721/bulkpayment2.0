package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordNotificationDto {
    private  Long idUserpk;
    private String password;

    public Long getIdUserpk() {
        return idUserpk;
    }

    public void setIdUserpk(Long idUserpk) {
        this.idUserpk = idUserpk;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordNotificationDto{" +
                "idUserpk=" + idUserpk +
                ", password='" + password + '\'' +
                '}';
    }
}