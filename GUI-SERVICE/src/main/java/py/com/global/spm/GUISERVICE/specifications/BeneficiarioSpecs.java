package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Beneficiary;
import py.com.global.spm.domain.entity.Company;

import javax.persistence.criteria.*;

public class BeneficiarioSpecs {

    public BeneficiarioSpecs() {
    }

    /**
     * Filtro por id del Beneficiario
     * @param id
     * @return
     */
    public static Specification<Beneficiary> getById(final Long id) {
        return new Specification<Beneficiary>() {

            @Override
            public Predicate toPredicate(Root<Beneficiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idbeneficiaryPk"), id);
            }
        };
    }

    public static Specification<Beneficiary> getByPhoneNumber(final String number) {
        return new Specification<Beneficiary>() {

            @Override
            public Predicate toPredicate(Root<Beneficiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(root.<String> get("phonenumberChr"), number);
            }
        };
    }

    public static Specification<Beneficiary> getAll() {
        return new Specification<Beneficiary>() {

            @Override
            public Predicate toPredicate(Root<Beneficiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }
    /**
     * Filtro por estado del Beneficiario
     * @param estado
     * @return
     */

    public static Specification<Beneficiary> getByEstado(final String estado) {
        return new Specification<Beneficiary>() {

            @Override
            public Predicate toPredicate(Root<Beneficiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("stateChr"),estado);
            }
        };
    }


    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<Beneficiary> getByCompanyId(final Long id) {
        return new Specification<Beneficiary>() {

            @Override
            public Predicate toPredicate(Root<Beneficiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Beneficiary, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }


}

