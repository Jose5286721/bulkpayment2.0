package py.com.global.spm.domain.audit;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import py.com.global.spm.domain.entity.Logaudit;
import py.com.global.spm.domain.repository.AuditDAO;


/**
 * Async Entity Audit Event writer
 * This is invoked by Hibernate entity listeners to write audit event for entitities
 */
@Component
public class AsyncEntityAuditEventWriter {

    private final Logger log = LogManager.getLogger(AsyncEntityAuditEventWriter.class);

    private final AuditDAO auditingEntityRepository;

    public AsyncEntityAuditEventWriter(AuditDAO auditingEntityRepository) {
        this.auditingEntityRepository = auditingEntityRepository;
    }

    /**
     * Writes audit events to DB asynchronously in a new thread
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeAuditEvent(Object target, AuditActions action) {
        log.debug("-------------- Post {} audit  --------------", action.value());
        try {
          prepareAuditEntity(target, action);

        } catch (Exception e) {
            log.error("Exception while persisting audit entity for {} error: {}", target, e);
        }
    }

    /**
     * Method to prepare auditing entity
     *
     * @param entity
     * @param action
     * @return
     */
    private void prepareAuditEntity(final Object entity, AuditActions action) {
        Logaudit auditedEntity = new Logaudit();
        auditedEntity.setAccesstypeChr(action.value());
        auditedEntity.setDescriptionChr(action.value());
        auditedEntity.setParamsChr(entity.toString());
        auditedEntity.setIpChr(obtainClientAddress());
        auditingEntityRepository.save(auditedEntity);
        log.info("Audit Entity --> {} ", auditedEntity);
    }

    private String obtainClientAddress() {
        if(RequestContextHolder.getRequestAttributes()!=null)
            return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                    .getRequest().getRemoteAddr();
        else
            return "127.0.0.0.1";
    }

}
