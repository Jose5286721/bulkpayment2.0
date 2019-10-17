package py.com.global.spm.GUISERVICE.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Workflow;

@Repository
public interface IWorkflowDao extends JpaRepository<Workflow, Long>, JpaSpecificationExecutor<Workflow> {
	/**
     * Busca un Workflow por id
     * @param id
     * @return
     */
    Workflow findByidworkflowPk(Long id);


    List<Workflow> findByworkflownameChr(String wokflowName);
    
    List<Workflow> findBydescriptionChr(String descripcion);
    
    List<Workflow> findBystateChr(Character estado);

    /**
     * Lista de todos los workflows con un estado determinado
     * @param state
     * @return
     */
    List<Workflow> findAllByStateChr(String state);

    /**
     * Lista de todos los workflows de una empresa con un estado determinado
     * @param state
     * @return
     */
    List<Workflow> findAllByStateChrAndCompanyIdcompanyPk(String state, Long idCompany);

    /**
     * Cantidad de workflows por empresa y usuario
     * @param idCompany
     * @param idUser
     * @param esFirmante
     * @return Long
     */
    Long countByCompanyIdcompanyPkAndCompanyUsersIduserPkAndCompanyUsersEsFirmante(Long idCompany, Long idUser, boolean esFirmante);

}
