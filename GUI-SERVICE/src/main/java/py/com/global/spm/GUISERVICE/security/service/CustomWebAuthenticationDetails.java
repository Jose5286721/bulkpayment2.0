package py.com.global.spm.GUISERVICE.security.service;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;


public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private String token;

    public CustomWebAuthenticationDetails(HttpServletRequest request, String myToken) {
        super(request);
        this.token = myToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CustomWebAuthenticationDetails{" +
                super.toString()+
                "token=" + token +
                '}';
    }
}
