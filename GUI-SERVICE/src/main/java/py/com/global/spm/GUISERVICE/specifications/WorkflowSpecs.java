package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Workflow;

import javax.persistence.criteria.*;

/**
 * Created by global on 18-04-04
 */
public class WorkflowSpecs {

    public WorkflowSpecs(){

    }

    /**
     * Filtro de todos los workflows
     * @param
     * @return
     */
    public static Specification<Workflow> getAll() {
        return (root, criteriaQuery, cb) -> cb.conjunction();
    }

    /**
     * Filtro por nombre del workflow
     * @param nombre
     * @return
     */

    public static Specification<Workflow> getByNombre(final String nombre) {
        return (root, criteriaQuery, cb) -> cb.like(cb.lower(root.<String> get("workflownameChr")), concatParam(nombre.toLowerCase()));
    }

    /**
     * Filtro por descripcion del workflow
     * @param description
     * @return
     */

    public static Specification<Workflow> getByDescripcion(final String description) {
        return (root, criteriaQuery, cb) -> cb.like(cb.lower(root.<String> get("descriptionChr")), concatParam(description.toLowerCase()));
    }

    /**
     * Filtro por estado del workflow
     * @param estado
     * @return
     */

    public static Specification<Workflow> getByEstado(final String estado) {
        return (root, criteriaQuery, cb) -> cb.equal(root.<String> get("stateChr"),estado);
    }

    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<Workflow> getByCompanyId(final Long id) {
        return (root, criteriaQuery, cb) -> {
            Join<Workflow, Company> groupJoin = root.join("company");
            return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
        };
    }


    private static String concatParam(String param) {
        return '%' + param + '%';
    }
}
