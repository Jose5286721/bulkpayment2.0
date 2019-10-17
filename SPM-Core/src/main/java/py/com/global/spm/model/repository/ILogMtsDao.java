package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Logmts;

import java.util.List;


@Repository
public interface ILogMtsDao extends JpaRepository<Logmts, Long> {
    List<Logmts> findByLogpaymentIdpaymentNumAndStateChrAndTrxtypeChr(Long paymentId,String stateChr,String trxTypeChr );
    List<Logmts> findByLogpaymentIdpaymentNum(Long paymentId);
}
