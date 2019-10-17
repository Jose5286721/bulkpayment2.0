package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Draft;
import py.com.global.spm.domain.entity.User;

/**
 * @author christiandelgado on 31/05/18
 * @project GOP
 */
@Repository
public interface IDraftDao extends JpaRepository<Draft, Long>, JpaSpecificationExecutor<Draft> {

    /**
     *
     * @param idDraftPk
     * @return
     */
    Draft findByIddraftPk(Long idDraftPk);

    Long countByUser(User user);

}
