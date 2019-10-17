package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logreversion;
import py.com.global.spm.model.repository.ILogReversionDao;

import java.sql.Timestamp;

@Service
@Transactional
public class LogReversionService {
    private final Logger log = LoggerFactory.getLogger(LogReversionService.class);

    private final ILogReversionDao dao;

    @Autowired
    public LogReversionService(ILogReversionDao dao) {
        this.dao = dao;
    }

    public void updateLogreversion(Logreversion logReversion) {
        if (logReversion == null) {
            log.error("logReversion can't be null");
            return;
        }
        log.trace("Updating logReversion --> " + logReversion);
        try {
            logReversion.setCreationdateTim(new Timestamp(System
                    .currentTimeMillis()));
            dao.saveAndFlush(logReversion);
        } catch (Exception e) {
            log.error("Updating logReversion --> " + logReversion, e);
        }
    }

}
