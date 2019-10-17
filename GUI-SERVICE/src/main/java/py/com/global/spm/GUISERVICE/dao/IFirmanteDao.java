package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Approval;

@Repository
public interface IFirmanteDao extends JpaRepository<Approval, Long>, JpaSpecificationExecutor<Approval> {

}
