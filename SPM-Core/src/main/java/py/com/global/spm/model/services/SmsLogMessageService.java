package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Smslogmessage;
import py.com.global.spm.model.repository.ISmsLogMessageDao;

@Service
@Transactional
public class SmsLogMessageService {
    private final Logger log = LoggerFactory.getLogger(SmsLogMessageService.class);

    private final ISmsLogMessageDao dao;

    @Autowired
    public SmsLogMessageService(ISmsLogMessageDao dao) {
        this.dao = dao;
    }

    public void insertSmslogmessage(Smslogmessage smslogmessage) {
        log.trace("Persisting --> " + smslogmessage);
        try {
            dao.save(smslogmessage);
        } catch (Exception e) {
            log.error("Creating logdraftop --> " + smslogmessage, e);
        }
    }
}
