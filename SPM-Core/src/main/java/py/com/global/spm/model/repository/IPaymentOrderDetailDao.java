package py.com.global.spm.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Paymentorderdetail;

import java.util.List;


@Repository
public interface IPaymentOrderDetailDao extends JpaRepository<Paymentorderdetail, Long> {

    List<Paymentorderdetail> findByPaymentorderIdpaymentorderPk(long id, Pageable pageRequest);
}
