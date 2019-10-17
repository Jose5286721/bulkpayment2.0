package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Paymentorder;

import java.util.Date;
import java.util.List;

@Repository
public interface IPaymentOrderDao extends JpaRepository<Paymentorder, Long>, JpaSpecificationExecutor<Paymentorder>  {
    Paymentorder findByidpaymentorderPk(Long aLong);
    Long countByWorkflowIdworkflowPk(Long id);
    List<Paymentorder> findByCompanyIdcompanyPkAndStateChr(Long id, String state);
    @Transactional(readOnly = true)
    List<Paymentorder> findBypaymentorderdetailsBeneficiarySubscriberciChrAndStateChr(String subscriberCI, String stateChr);
    @Transactional(readOnly = true)
    List<Paymentorder> findBypaymentorderdetailsBeneficiarySubscriberciChrAndIdpaymentorderPkAndStateChr(String subscriberCI, Long id, String stateChr);

    List<Paymentorder> findByCompanyIdcompanyPkAndUpdatedateTimBetweenAndStateChrOrderByUpdatedateTimDesc(Long idCompanyPk, Date fromDate, Date toDate, String state);
}
