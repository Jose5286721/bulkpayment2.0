package py.com.global.spm.model.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.model.dto.redis.LogPaymentCache;

import java.util.List;


@Repository
public interface ILogPaymentCacheDao extends CrudRepository<LogPaymentCache, Long> {
    List<LogPaymentCache> findByIdpaymentorderPk(Long id);
    void deleteByIdpaymentorderPk(Long id);


}
