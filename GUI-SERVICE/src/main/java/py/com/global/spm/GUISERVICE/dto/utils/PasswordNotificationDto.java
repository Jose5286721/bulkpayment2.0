package py.com.global.spm.GUISERVICE.dto.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordNotificationDto {
    @NotNull(message = "0030") @Digits(integer = 19, fraction = 0)
    private  Long idUserpk;
    @NotNull(message = "0030") @Size(max = 255, message = "0037")
    private String password;

    public PasswordNotificationDto(Long idUserpk, String password) {
        this.idUserpk = idUserpk;
        this.password = password;
    }

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
