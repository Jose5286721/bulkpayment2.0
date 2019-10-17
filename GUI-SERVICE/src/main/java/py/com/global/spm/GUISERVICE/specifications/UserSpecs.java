package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.User;

import javax.persistence.criteria.*;

/**
 * Created by global on 18-04-04
 */
public class UserSpecs {

    public UserSpecs(){

    }


    /**
     * Filtro de todos los usuarios
     * @param
     * @return
     */
    public static Specification<User> getAll() {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }


    /**
     * Filtro por email del user
     * @param email
     * @return
     */

    public static Specification<User> getByEmail(final String email) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("emailChr")), concatParam(email.toLowerCase()));
            }
        };
    }

    /**
     * Filtro por estado del user
     * @param name
     * @return
     */

    public static Specification<User> getByNombre(final String name) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("usernameChr")), concatParam(name.toLowerCase()));
            }
        };
    }

    /**
     * Filtro por estado del usuario
     * @param state
     * @return
     */

    public static Specification<User> getByEstado(final String state) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("stateChr"), state);
            }
        };
    }
    /**
     * Filto por rol del usuario
     * @return
     */
    public static Specification<User> getByIsNotSuperUser() {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<User, Company> groupJoin = root.join("rols");
                return cb.equal((groupJoin.<String> get("isuperrolNum")), 0);
            }
        };
    }

    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<User> getByCompanyId(final Long id) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<User, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }
}
