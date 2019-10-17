package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.model.repository.ILogMtsDao;
import py.com.global.spm.model.util.SpmConstants;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class LogMtsService {
    private final Logger log = LoggerFactory.getLogger(LogMtsService.class);

    private final ILogMtsDao dao;

    @Autowired
    public LogMtsService(ILogMtsDao dao) {
        this.dao = dao;
    }

    public void updateLogmts(Logmts logMts) {
        if (logMts == null) {
            log.error("logMts no puede ser null");
            return;
        }
        try {
            logMts.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
           dao.saveAndFlush(logMts);
        } catch (Exception e) {
            log.error("Error al actualizar logMts --> " + logMts, e);
            throw e;
        }
    }

    public List<Logmts> findLogmts(Long paymentId, String stateChr) {
        try{
            return dao.findByLogpaymentIdpaymentNumAndStateChrAndTrxtypeChr
                    (paymentId,stateChr, SpmConstants.MTS_OPERATION_PAYMENT);
        }catch (Exception e){
            log.error("Error al buscar el logmts -->",e);
            throw e;
        }
    }

    public List<Logmts> findAllLogmts(Long paymentId) {
        try{
            return dao.findByLogpaymentIdpaymentNum(paymentId);
        }catch (Exception e){
            log.error("Error al buscar el logmts -->",e);
            throw e;
        }
    }

}
