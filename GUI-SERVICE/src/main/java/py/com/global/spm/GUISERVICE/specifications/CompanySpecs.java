package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;

import javax.persistence.criteria.*;

/**
 * Created by cbenitez on 3/16/18.
 */
public class CompanySpecs {

    public CompanySpecs() {
    }

    /**
     * Filtro por id de la empresa
     * @param id
     * @return
     */
    public static Specification<Company> getByIdCompany(final Long id) {
        return new Specification<Company>() {

            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idcompanyPk"), id);
            }
        };
    }

    /**
     * Filtro por nombre de la empresa
     * @param nombre
     * @return
     */
    public static Specification<Company> getByNombre(final String nombre) {
        return new Specification<Company>() {

            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("companynameChr")), concatParam(nombre.toLowerCase()));
            }
        };
    }


    /**
     * Filtro por empresas activas
     * @param activo
     * @return
     */
    public static Specification<Company> getByActivos(final String activo) {
        return new Specification<Company>() {

            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("stateChr"), activo);
            }
        };
    }

    public static Specification<Company> getByMtsfield5Chr(final String cuenta){
        return new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("mtsfield5Chr"), cuenta);
            }
        };

    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }


}
