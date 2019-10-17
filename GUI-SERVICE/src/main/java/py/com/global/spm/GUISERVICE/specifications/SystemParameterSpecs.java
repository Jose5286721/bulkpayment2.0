package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Systemparameter;

import javax.persistence.criteria.*;

public class SystemParameterSpecs {
    SystemParameterSpecs(){
    }

    /**
     *  Todos los System Parameters
     * @param
     * @return
     */
    public static Specification<Systemparameter> getAll() {
        return new Specification<Systemparameter>() {

            @Override
            public Predicate toPredicate(Root<Systemparameter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filtro por el Valor del System Parameter
     * @param valor
     * @return
     */

    public static Specification<Systemparameter> getByValor(final String valor) {
        return new Specification<Systemparameter>() {

            @Override
            public Predicate toPredicate(Root<Systemparameter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("valueChr")), concatParam(valor.toLowerCase()));

            }
        };
    }

    /**
     * Filtro por parametro
     * @param parametro
     * @return
     */
    public static Specification<Systemparameter> getByParametro(final String parametro) {
        return new Specification<Systemparameter>() {

            @Override
            public Predicate toPredicate(Root<Systemparameter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("id").get("parameterPk")), concatParam(parametro.toLowerCase()));

            }
        };
    }

    /**
     * Filto por Id de Proceso
     * @param id
     * @return
     */
    public static Specification<Systemparameter> getByProcessId(final Short id) {
        return new Specification<Systemparameter>() {

            @Override
            public Predicate toPredicate(Root<Systemparameter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Systemparameter, Process> groupJoin = root.join("process");
                return cb.equal((groupJoin.<String> get("idprocessPk")), id);
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }

}
