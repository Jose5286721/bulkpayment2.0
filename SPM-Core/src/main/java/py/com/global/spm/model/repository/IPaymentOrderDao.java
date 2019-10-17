package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Paymentorder;

import java.util.List;


@Repository
public interface IPaymentOrderDao extends JpaRepository<Paymentorder, Long> {

    Paymentorder findByIdpaymentorderPk(long id);

    List<Paymentorder> findByStateChr(String stateChr);
}
