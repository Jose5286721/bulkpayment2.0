package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Permission;

/**
 * @author christiandelgado on 23/04/18
 * @project GOP
 */
@Repository
public interface IPermissionDao extends JpaRepository<Permission, Long>{

    /**
     * Busca un permiso por su id
     * @param id
     * @return
     */
    Permission findByIdpermissionPk(Long id);

}
