package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Eventcode;
import py.com.global.spm.domain.entity.EventcodeId;

@Repository
public interface ICodigoEventos extends JpaRepository<Eventcode, Long>, JpaSpecificationExecutor<Eventcode> {
    /**
     * Busca un Codigo de Evenos por id
     * @param id
     * @return
     */

    Eventcode findById(EventcodeId id);

}
