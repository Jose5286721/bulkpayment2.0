package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logdraftop;
import py.com.global.spm.model.repository.ILogDraftOpDao;

@Service
@Transactional
public class LogDraftOpService {
    private final Logger log = LoggerFactory.getLogger(LogDraftOpService.class);

    private final ILogDraftOpDao dao;

    @Autowired
    public LogDraftOpService(ILogDraftOpDao dao) {
        this.dao = dao;
    }

    public void insertLogdraftop(Logdraftop logdraftop) {
        log.trace("Persisting --> " + logdraftop);
        try {
            dao.saveAndFlush(logdraftop);
        } catch (Exception e) {
            log.error("Creating logdraftop --> " + logdraftop, e);
        }
    }
}
