package py.com.global.spm.model.repository.redis;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;

import javax.persistence.LockModeType;
import java.util.Optional;


@Repository
public interface IPaymenOrderCacheDao extends CrudRepository<PaymentOrderCache, Long> {

//    @Lock(LockModeType.PESSIMISTIC_READ)
//    Optional<PaymentOrderCache> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends PaymentOrderCache> S save(S var1);


}
