package py.com.global.spm.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Logpayment;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;


@Repository
public interface ILogPaymentDao extends JpaRepository<Logpayment, Long> {
    List<Logpayment> findByPaymentorderdetailIdIdpaymentorderPk(Long paymentOrderId);
    long countByPaymentorderdetailIdIdpaymentorderPkAndStateChr(Long paymentOrderId, String stateChr);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // Previene lecturas, y actualizaciones sucias
    @Query("select l from Logpayment l where l.paymentorderdetail.id.idpaymentorderPk = :idpaymentorder" +
            " and (l.stateChr= :pagado or l.stateChr= :parcialPagado)")
    List<Logpayment> findByPaymentorderAnAndStateChr(@Param("idpaymentorder") Long idPaymentOrder,
                                                     @Param("pagado") String pagado, @Param("parcialPagado")String parcial);
    List<Logpayment> findByStateChrIsNotAndCompany_IdcompanyPk(String stateChr, Long idcompanyPk);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Logpayment> findByPaymentorderdetailIdIdpaymentorderPkAndStateChrIsInAndRetryIsLessThan(Long idpaymentOrderPk, List<String> stateChr, Integer retry ,Pageable page);
}
