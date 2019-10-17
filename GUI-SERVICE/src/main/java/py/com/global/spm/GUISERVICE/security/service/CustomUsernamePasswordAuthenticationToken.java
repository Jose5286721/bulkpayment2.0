package py.com.global.spm.GUISERVICE.security.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomUsernamePasswordAuthenticationToken  extends UsernamePasswordAuthenticationToken {
    private String captchaEncode;
    private String userInputCaptchaValue;
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, String captchaEncode, String userInputCaptchaValue) {
        super(principal, credentials);
        this.captchaEncode = captchaEncode;
        this.userInputCaptchaValue  = userInputCaptchaValue;
    }

    public String getCaptchaEncode() {
        return captchaEncode;
    }

    public void setCaptchaEncode(String captchaEncode) {
        this.captchaEncode = captchaEncode;
    }

    public String getUserInputCaptchaValue() {
        return userInputCaptchaValue;
    }

    public void setUserInputCaptchaValue(String userInputCaptchaValue) {
        this.userInputCaptchaValue = userInputCaptchaValue;
    }
}
