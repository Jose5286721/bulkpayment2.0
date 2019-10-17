package py.com.global.spm.model.services.service;

import py.com.global.spm.model.services.ConstantsDefaults;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Singleton
public class SessionSingleton {

    private Properties props;
    private Session session;

    @PostConstruct
    public void start(){
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ConstantsDefaults.MAIL_SERVER, ConstantsDefaults.MAIL_PASS);
                    }
                });

    }

    public Session getSession(){
        return this.session;
    }

    public Properties getProps() {
        return props;
    }
}
