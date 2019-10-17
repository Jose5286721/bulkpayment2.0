package py.com.global.spm.GUISERVICE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IPermissionDao;
import py.com.global.spm.domain.entity.Permission;

import java.util.List;

/**
 * @author christiandelgado on 23/04/18
 * @project GOP
 */
@Transactional
@Service
public class PermissionService {

    @Autowired
    private IPermissionDao dao;

    /**
     * Creacion de permiso
     * @param permission
     * @return
     * @throws Exception
     */
    public Permission saveOrUpdate(Permission permission) {
        try {
            return dao.save(permission);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Obtener lista de todos los permisos
     */
    public List<Permission> getAll() {
        return dao.findAll();
    }


    /**
     * Retona un permiso de acuerdo al id
     * @param id
     * @return
     */
    public Permission getById(Long id){
        return dao.findByIdpermissionPk(id);
    }

}
