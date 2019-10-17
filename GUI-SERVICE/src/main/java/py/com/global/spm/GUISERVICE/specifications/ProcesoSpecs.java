package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import py.com.global.spm.domain.entity.Process;
public class ProcesoSpecs {
    public ProcesoSpecs(){

    }

    /**
     * Filtro por id del proceso
     * @param id
     * @return
     */
    public static Specification<Process> getByIdProcess(final Long id) {
        return new Specification<Process>() {

            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idprocessPk"), id);
            }
        };
    }

    /**
     * Filtro de todos los Procesos
     * @param
     * @return
     */
    public static Specification<Process> getAll() {
        return new Specification<Process>() {

            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filtro por nombre del Proceso
     * @param nombre
     * @return
     */

    public static Specification<Process> getByNombre(final String nombre) {
        return new Specification<Process>() {

            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("processnameChr")), concatParam(nombre.toLowerCase()));

            }
        };
    }

    /**
     * Filtro por descripcion del Proceso
     * @param description
     * @return
     */

    public static Specification<Process> getByDescripcion(final String description) {
        return new Specification<Process>() {

            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("descriptionChr")), concatParam(description.toLowerCase()));
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }

}
