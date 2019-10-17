package py.com.global.spm.model.services.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.repository.redis.ILogPaymentCacheDao;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogPaymentCacheService {
    private final ILogPaymentCacheDao dao;


    private final Logger logger = LoggerFactory.getLogger(LogPaymentCacheService.class);

    @Autowired
    public LogPaymentCacheService(ILogPaymentCacheDao dao){
        this.dao=dao;
    }

    public void saveAll(List<LogPaymentCache> logPaymentCache){
       try {
           dao.saveAll(logPaymentCache);
       }catch (Exception e){
           logger.error("Al guardar PaymentOrderCache",e);
           throw e;
       }

    }

    public void saveOrUpdate(LogPaymentCache logPaymentCache){
        try {
            dao.save(logPaymentCache);
        }catch (Exception e){
            logger.error("Al guardar LogPaymentCache",e);
            throw e;
        }

    }

    public  List<LogPaymentCache> findByPaymentOrderId(Long id){
        return dao.findByIdpaymentorderPk(id);

    }

    public void deleteAll(List<LogPaymentCache> logPaymentCaches){
        dao.deleteAll(logPaymentCaches);

    }
    public LogPaymentCache findById(Long id){
        Optional<LogPaymentCache> optional=dao.findById(id);
        return optional.orElse(null);

    }

    public boolean existLogPaymentInPo(Long id){
        try{
            return dao.existsById(id);
        }catch (Exception e){
            logger.info("Al obtener LogPayment",e);
            throw e;
        }
    }
}
