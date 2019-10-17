package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.SystemparameterId;

import java.util.List;


/**
 * Created by global
 */
@Repository
public interface ISystemParameterDao extends JpaRepository<Systemparameter, Long>, JpaSpecificationExecutor<Systemparameter> {

    Systemparameter findByIdParameterPk(String parameter);
    /**
     * Devuelve el System Parameter por su Valor
     * @param valor
     * @return
     */
    Systemparameter findByValueChr(String valor);

    Systemparameter findById(SystemparameterId id);

    List<Systemparameter> findByProcessIdprocessPk(Long idProcess);


}
