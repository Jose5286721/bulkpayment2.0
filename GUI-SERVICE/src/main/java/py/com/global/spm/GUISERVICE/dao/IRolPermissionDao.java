package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.RolPermission;

import java.util.List;

/**
 * @author christiandelgado on 23/04/18
 * @project GOP
 */
@Repository
public interface IRolPermissionDao extends JpaRepository<RolPermission, Long>{

    /**
     * Retorna lista de Rol Permission de un rol
     * @param idRol
     * @return
     */
    List<RolPermission> findByRolIdrolPk(Long idRol);

    /**
     * Retorna un rol-permission que coincida con el id del permiso y del rol
     * @param idPermission
     * @param idRol
     * @return
     */
    RolPermission findByIdIdpermissionPkAndIdIdrolPk(Long idPermission, Long idRol);
}
