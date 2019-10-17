package py.com.global.spm.GUISERVICE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IRolPermissionDao;
import py.com.global.spm.domain.entity.RolPermission;

import java.util.List;

/**
 * @author christiandelgado on 23/04/18
 * @project GOP
 */
@Transactional
@Service
public class RolPermissionService {

    @Autowired
    private IRolPermissionDao dao;

    /**
     * Creacion de Rol Permission
     * @param rolPermission
     * @return
     * @throws Exception
     */
    public RolPermission saveOrUpdate(RolPermission rolPermission) throws Exception {
        try {
            return dao.save(rolPermission);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<RolPermission> getByIdRol(Long idRol){
        try {
            return dao.findByRolIdrolPk(idRol);
        }catch (Exception e) {
            throw e;
        }
    }



    public RolPermission getByIdRolAndIdPermission(Long idRol,Long idPermission){
        try {
            return dao.findByIdIdpermissionPkAndIdIdrolPk(idPermission,idRol);
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * Crear o actualizar lista de RolPermission
     *
     * @param rolPermissions
     * @return {@link RolPermission}
     * @throws Exception
     */
    public List<RolPermission> saveOrUpdateList(List<RolPermission> rolPermissions) throws Exception {
        try {
            return dao.saveAll(rolPermissions);
        } catch (Exception e) {
            throw e;
        }
    }

}
