package py.com.global.spm.GUISERVICE.audit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;
import py.com.global.spm.domain.audit.EntityAuditEventListener;

@Configuration
public class EntityAuditEventConfig implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        EntityAuditEventListener.setBeanFactory(beanFactory);
    }
}
