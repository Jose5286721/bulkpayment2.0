package py.com.global.spm.GUISERVICE.services;

import org.springframework.mail.SimpleMailMessage;
import py.com.global.spm.GUISERVICE.dto.SendEmailDto;


public interface EmailService {
    void sendEmail(SimpleMailMessage msg);

    void sendChangePasswordEmail(SendEmailDto token);

}
