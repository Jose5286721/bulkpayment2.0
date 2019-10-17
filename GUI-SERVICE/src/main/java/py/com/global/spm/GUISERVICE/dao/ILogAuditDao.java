package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Logaudit;

/**
 * Created by global on 3/14/18.
 */
@Repository
public interface ILogAuditDao extends JpaRepository<Logaudit, Long>, JpaSpecificationExecutor<Logaudit> {
    /**
     *
     * @param id
     * @return
     */
    Logaudit findByidlogaudit(Long id);

}
