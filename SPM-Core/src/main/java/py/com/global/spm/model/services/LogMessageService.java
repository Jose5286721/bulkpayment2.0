package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logmessage;
import py.com.global.spm.model.repository.ILogMessageDao;

@Service
@Transactional
public class LogMessageService {
    private final Logger log = LoggerFactory.getLogger(LogMessageService.class);

    private final ILogMessageDao dao;

    @Autowired
    public LogMessageService(ILogMessageDao dao) {
        this.dao = dao;
    }

    public Boolean insertLogmessage(Logmessage logMessage) {
        if (logMessage == null) {
            log.error("logMessage can't be null");
            return false;
        }
        //log.debug("Inserting logMessage --> " + logMessage);

        try {
            dao.saveAndFlush(logMessage);
            return true;
        } catch (Exception e) {
            log.error("Inserting logMessage --> " + logMessage, e);
        }
        return false;
    }
}
