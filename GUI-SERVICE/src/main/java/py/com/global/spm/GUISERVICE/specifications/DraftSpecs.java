package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Draft;
import py.com.global.spm.domain.entity.Paymentordertype;
import py.com.global.spm.domain.entity.Workflow;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Created by global on 18-04-04
 */
public class DraftSpecs {
    public DraftSpecs(){

    }

    /**
     * Filtro por id de borrador
     * @param id
     * @return
     */
    public static Specification<Draft> getByIdDraft(final Long id) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("iddraftPk"), id);
            }
        };
    }

    /**
     * Filto por recurrencia
     * @param recurrent
     * @return
     */
    public static Specification<Draft> getByRecurrentNum(final Boolean recurrent) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("recurrentNum"), recurrent);
            }
        };
    }

    /**
     * Get all
     * @return
     */
    public static Specification<Draft> getAll() {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<Draft> getByCompanyId(final Long id) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Draft, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }

    /**
     * Filto por id de workflow
     * @param id
     * @return
     */
    public static Specification<Draft> getByWorkflowId(final Long id) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Draft, Workflow> groupJoin = root.join("workflow");
                return cb.equal((groupJoin.<String> get("idworkflowPk")), id);
            }
        };
    }

    /**
     * Filto por id de paymentOrder
     * @param id
     * @return
     */
    public static Specification<Draft> getByPaymentorderTypeId(final Long id) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Draft, Paymentordertype> groupJoin = root.join("paymentordertype");
                return cb.equal((groupJoin.<String> get("idorderpaymenttypePk")), id);
            }
        };
    }

    public static Specification<Draft> getByCreationDateBetween(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
        return new Specification<Draft>() {

            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Draft> root, javax.persistence.criteria.CriteriaQuery<?> criteriaQuery,
                                                                    CriteriaBuilder criteriaBuilder) {
                if(creationdateTimStart!=null && creationdateTimEnd!=null){
                    return criteriaBuilder.between(root.get(fecha).as(java.sql.Timestamp.class),creationdateTimStart, creationdateTimEnd);

                }else if(creationdateTimStart!=null){
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(fecha).as(java.sql.Timestamp.class), creationdateTimStart);
                }
                return criteriaBuilder.lessThanOrEqualTo(root.get(fecha).as(java.sql.Timestamp.class), creationdateTimEnd);
            }
        };
    }

    public static Specification<Draft> getByDescription(final String description) {
        return new Specification<Draft>() {

            @Override
            public Predicate toPredicate(Root<Draft> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("descriptionChr")), concatParam(description.toLowerCase()));
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }
}
