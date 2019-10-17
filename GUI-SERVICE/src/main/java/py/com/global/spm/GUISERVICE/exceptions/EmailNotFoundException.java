package py.com.global.spm.GUISERVICE.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailNotFoundException extends AuthenticationException {

    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
