package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logmtsrev;
import py.com.global.spm.model.repository.ILogMtsRevDao;

@Service
@Transactional
public class LogMtsRevService {
    private final Logger log = LoggerFactory.getLogger(LogMtsRevService.class);

    private final ILogMtsRevDao dao;

    @Autowired
    public LogMtsRevService(ILogMtsRevDao dao) {
        this.dao = dao;
    }

    public void insertLogmtsrev(Logmtsrev logmtsrev) {
        if (logmtsrev == null) {
            log.error("logMtsRev no puede ser null");
            return;
        }
        try {
            dao.saveAndFlush(logmtsrev);
        } catch (Exception e) {
            log.error("Error al insertar logMtsRev --> " + logmtsrev, e);
        }
    }

}
