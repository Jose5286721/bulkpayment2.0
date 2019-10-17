package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Paymentordertype;

/**
 * @author cdelgado
 */
@Repository
public interface IPaymentOrderTypeDao extends JpaRepository<Paymentordertype, Long>,
        JpaSpecificationExecutor<Paymentordertype>{

    /**
     * Retorna un tipo de orden de pago por id
     * @param id
     * @return
     */
    Paymentordertype findByIdorderpaymenttypePk(Long id);
}
