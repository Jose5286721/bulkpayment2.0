package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Company;

import java.util.List;

/**
 * Created by global on 3/14/18.
 */
@Repository
public interface ICompanyDao  extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    /**
     * Busca una empresa por id
     * @param id
     * @return
     */
    Company findByIdcompanyPk(Long id);


    List<Company> findByCompanynameChr(String companyName);

    /**
     * Lista de todas las empresas con un estado determinado
     * @param state
     * @return
     */
    List<Company> findAllByStateChr(String state);

    Integer countByCompanynameChr(String name);

}
