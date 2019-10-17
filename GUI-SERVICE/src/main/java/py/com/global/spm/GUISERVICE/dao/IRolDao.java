package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Rol;

import java.util.List;

/**
 * Created by cbenitez on 3/21/18.
 */
@Repository
public interface IRolDao extends JpaRepository<Rol, Long>, JpaSpecificationExecutor<Rol> {


    List<Rol> findByDefaultrolNumAndStateNum(Boolean defaultrolNum, boolean stateNum);

    Rol findByIdrolPk(Long id);

    List<Rol> findByRolnameChr(String rolnameChr);

    /**
     * Lista de roles por nombre de una empresa
     * @param rolnameChr
     * @param companyId
     * @return
     */
    //List<Rol> findByRolnameChrAndCompanyIdcompanyPk(String rolnameChr, Long companyId);
}
