package py.com.global.spm.GUISERVICE.services;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.stereotype.Service;

import py.com.global.spm.GUISERVICE.dto.SendEmailDto;

import java.util.Date;

@Service
public abstract class AbstractEmailService implements EmailService{


    @Override
    public void sendChangePasswordEmail(SendEmailDto dto){
        SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(dto);
        sendEmail(sm);
    }

    private SimpleMailMessage prepareSimpleMailMessageFromOrder(SendEmailDto dto){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom(dto.getFromEmail());
        sm.setTo(dto.getToEmail());
        sm.setSubject(dto.getSubject());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(dto.getMessage());
        return sm;

    }


}
