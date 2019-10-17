package py.com.global.spm.GUISERVICE.security;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 219871233368977989L;

    private String clientId;

    private String key;

    private String userInputCaptchaValue;

    private String captchaEncode;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String clientId, String key) {
        this.setClientId(clientId);
        this.setKey(key);
    }

    @NotNull(message = "0030")
    @NotBlank(message = "0030")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @NotNull(message = "0030")
    @NotBlank(message = "0030")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserInputCaptchaValue() {
        return userInputCaptchaValue;
    }

    public void setUserInputCaptchaValue(String userInputCaptchaValue) {
        this.userInputCaptchaValue = userInputCaptchaValue;
    }
    @NotNull(message = "0030")
    @NotBlank(message = "0030")
    public String getCaptchaEncode() {
        return captchaEncode;
    }

    public void setCaptchaEncode(String captchaEncode) {
        this.captchaEncode = captchaEncode;
    }
}
