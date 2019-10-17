package py.com.global.spm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Logaudit;

@Repository
public interface AuditDAO extends JpaRepository<Logaudit, Long> {

}
