package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Approval;

import java.util.List;

@Repository
public interface IApprovalDao extends JpaRepository<Approval, Long> {
    List<Approval> findByPaymentorderIdpaymentorderPk(Long idpaymentorderPk);
    Approval findByPaymentorderIdpaymentorderPkAndWorkflowdetUserIduserPk(Long idpaymentorderPk, Long iduserPk);
    Approval findFirstByPaymentorderIdpaymentorderPkAndSignedNumOrderByStepPkAsc(Long idpaymentorderPk, Boolean signed);
    Integer countByPaymentorderIdpaymentorderPkAndSignedNum(Long idpaymentorderPk, Boolean signed);


}
