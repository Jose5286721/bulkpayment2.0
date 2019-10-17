package py.com.global.spm.domain.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

public class EntityAuditEventListener extends AuditingEntityListener {

    private final Logger log = LoggerFactory.getLogger(EntityAuditEventListener.class);

    private static BeanFactory beanFactory;

    @PostPersist
    public void onPostCreate(Object target) {
        try {
            if(beanFactory!=null) {
                AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
                asyncEntityAuditEventWriter.writeAuditEvent(target, AuditActions.INSERTED);
            }
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter");
        } catch (Exception e) {
            log.error("Exception while persisting create audit entity ", e);
        }
    }

    @PostUpdate
    public void onPostUpdate(Object target) {
        try {
            if(beanFactory!=null) {
                AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
                asyncEntityAuditEventWriter.writeAuditEvent(target, AuditActions.UPDATED);
            }
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter", e);
        } catch (Exception e) {
            log.error("Exception while persisting update audit entity ", e);
        }
    }

    @PostRemove
    public void onPostRemove(Object target) {
        try {
            if(beanFactory!=null) {
                AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
                asyncEntityAuditEventWriter.writeAuditEvent(target, AuditActions.DELETED);
            }
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter");
        } catch (Exception e) {
            log.error("Exception while persisting delete audit entity ", e);
        }
    }

    public static void setBeanFactory(BeanFactory beanFactory) {
        EntityAuditEventListener.beanFactory = beanFactory;
    }

}
