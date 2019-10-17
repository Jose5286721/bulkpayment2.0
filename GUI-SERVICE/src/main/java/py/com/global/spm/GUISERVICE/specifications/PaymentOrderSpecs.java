package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.Paymentordertype;
import py.com.global.spm.domain.entity.Workflow;

import javax.persistence.criteria.*;
import java.util.Date;

public class PaymentOrderSpecs {


    public PaymentOrderSpecs() {
    }

    /**
     * Filtro por id de Ordern de Pago
     * @param id
     * @return
     */
    public static Specification<Paymentorder> getById(final Long id) {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idpaymentorderPk"), id);
            }
        };
    }

    public static Specification<Paymentorder> getFechaDesdeHasta(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
        return new Specification<Paymentorder>() {

            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Paymentorder> root, javax.persistence.criteria.CriteriaQuery<?> criteriaQuery,
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

    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<Paymentorder> getByCompanyId(final Long id) {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorder, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }

    /**
     * Get all
     * @return
     */
    public static Specification<Paymentorder> getAll() {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Paymentorder> getByEstado(final String estado) {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(cb.trim(root.<String> get("stateChr")),estado.trim());
            }
        };
    }



    public static Specification<Paymentorder> getByTypePaymentOrderId(final Long id) {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorder, Paymentordertype> groupJoin = root.join("paymentordertype");
                return cb.equal((groupJoin.<String> get("idorderpaymenttypePk")), id);
            }
        };
    }
    public static Specification<Paymentorder> getByWorkflowId(final Long id) {
        return new Specification<Paymentorder>() {

            @Override
            public Predicate toPredicate(Root<Paymentorder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorder, Workflow> groupJoin = root.join("workflow");
                return cb.equal((groupJoin.<String> get("idworkflowPk")), id);
            }
        };
    }
}
