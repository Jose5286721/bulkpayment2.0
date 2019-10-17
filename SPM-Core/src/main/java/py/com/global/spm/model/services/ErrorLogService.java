package py.com.global.spm.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Errorlog;
import py.com.global.spm.model.repository.IErrorLogDao;

import java.sql.Timestamp;

@Service
public class ErrorLogService {
    protected static Logger logger = LogManager.getLogger(ErrorLogService.class);

    @Autowired
    private IErrorLogDao dao;

    public boolean insertErrorlog(String processName, String description) {
        Errorlog errorlog = new Errorlog();
        errorlog.setProcessChr(processName);
        errorlog.setDescriptionChr(description);
        errorlog.setCreationdateTim(new Timestamp(System
                    .currentTimeMillis()));
        logger.debug("insert errorlog --> " + errorlog);
        try {

            dao.save(errorlog);
            return true;
        } catch (Exception e) {
            logger.error("insertErrorLog --> " + errorlog, e);
        }
        return false;
    }
}
