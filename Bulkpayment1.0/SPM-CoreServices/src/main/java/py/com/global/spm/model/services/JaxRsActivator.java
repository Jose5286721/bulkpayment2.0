package py.com.global.spm.model.services;

import py.com.global.spm.model.services.rest.CustomExceptionMapper;
import py.com.global.spm.model.services.rest.MailSMTPGoogle;
import py.com.global.spm.model.services.rest.Notification;
import py.com.global.spm.model.services.rest.PaymentOrderRest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/core/v1")
public class JaxRsActivator extends Application {

    public JaxRsActivator() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(Notification.class);
        resources.add(MailSMTPGoogle.class);
        resources.add(PaymentOrderRest.class);
        resources.add(CustomExceptionMapper.class);
        return resources;
    }
}

