package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Rol;

import javax.persistence.criteria.*;

/**
 * Created by global on 3/22/18.
 */
public class RolSpecs {

    public RolSpecs() {
    }

    /**
     * Filto por defaulrolNum del Rol
     * @param id
     * @return
     */
    public static Specification<Rol> getByDefaultrolNum(final Boolean id) {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("defaultrolNum"), id);
            }
        };
    }

    /**
     * Filto por defaulrolNum del Rol
     * @return
     */
    public static Specification<Rol> getAll() {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filto por nombre del rol
     * @param nombre
     * @return
     */
    public static Specification<Rol> getByNombre(final String nombre) {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("rolnameChr")), concatParam(nombre.toLowerCase()));
            }
        };
    }

    /**
     * Filto por estado del rol
     * @param state
     * @return
     */
    public static Specification<Rol> getByState(final Boolean state) {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal((root.<String> get("stateNum")), state ? 1: 0);
            }
        };
    }

    public static Specification<Rol> getByIsNotSuperRol() {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.notEqual((root.<String> get("isuperrolNum")), 1);
            }
        };
    }



  /*  public static Specification<Rol> getByCompanyId(final Long id) {
        return new Specification<Rol>() {

            @Override
            public Predicate toPredicate(Root<Rol> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Rol, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }*/


    private static String concatParam(String param) {
        return '%' + param + '%';
    }



}
