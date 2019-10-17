package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import py.com.global.spm.domain.entity.Logmts;


public interface ILogMtsDao extends JpaRepository<Logmts, Long>, JpaSpecificationExecutor<Logmts> {
}
