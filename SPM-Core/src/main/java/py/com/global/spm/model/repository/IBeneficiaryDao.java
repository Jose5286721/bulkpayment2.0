package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Beneficiary;

@Repository
public interface IBeneficiaryDao extends JpaRepository<Beneficiary, Long> {
}
