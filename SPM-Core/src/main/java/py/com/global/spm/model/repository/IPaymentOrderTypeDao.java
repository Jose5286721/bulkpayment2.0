package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Paymentordertype;


@Repository
public interface IPaymentOrderTypeDao extends JpaRepository<Paymentordertype, Long> {
    Paymentordertype findByPaymentordersIdpaymentorderPk(long idpaymentorderPk);

}
