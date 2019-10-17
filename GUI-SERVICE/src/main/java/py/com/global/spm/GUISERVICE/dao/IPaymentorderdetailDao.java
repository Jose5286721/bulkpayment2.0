package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Paymentorderdetail;


@Repository
public interface IPaymentorderdetailDao extends JpaRepository<Paymentorderdetail, Long>, JpaSpecificationExecutor<Paymentorderdetail> {
    @Transactional(readOnly = true)
    Page<Paymentorderdetail> findBybeneficiarySubscriberciChrAndPaymentorderStateChr(String subscriberCI, String stateChr, Pageable pageRequest);
    @Transactional(readOnly = true)
    Page<Paymentorderdetail> findBybeneficiarySubscriberciChrAndPaymentorderIdpaymentorderPkAndPaymentorderStateChr(String subscriberCI, Long id, String stateChr, Pageable pageRequest);
    @Transactional(readOnly = true)
    Page<Paymentorderdetail> findByPaymentorderIdpaymentorderPkAndPaymentorderStateChr(Long id, String stateChr, Pageable pageRequest);
}
