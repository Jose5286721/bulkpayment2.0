package py.com.global.spm.GUISERVICE.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.services.ResponseDtoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        if(authException instanceof BadCredentialsException || authException instanceof UsernameNotFoundException){
            ObjectMapper mapper = new ObjectMapper();
            String responseMsg = mapper.writeValueAsString(responseDtoService.errorResponse(authException.getMessage(),null));
            response.getWriter().write(responseMsg);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
