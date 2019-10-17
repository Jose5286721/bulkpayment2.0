package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.*;

import javax.persistence.criteria.*;
import java.util.Date;

public class LogPaymentSpecs {


    /**
     * Get all
     *
     * @return
     */
    public static Specification<Logpayment> getAll() {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filto por Id Orden de Pago      *
     * @param idpaymentorderPk
     * @return
     */
    public static Specification<Logpayment> getByIdPaymentOrder(final Long idpaymentorderPk) {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logpayment, Paymentorderdetail> groupJoin = root.join("paymentorderdetail");
                Join<Paymentorderdetail, Paymentorder> groupJoin2 = groupJoin.join("paymentorder");
                return cb.equal((groupJoin2.<String>get("idpaymentorderPk")), idpaymentorderPk);
            }
        };
    }

    /**
     * Filtro por estado del LogPayment
     *
     * @param estado
     * @return
     */

    public static Specification<Logpayment> getByEstado(final String estado) {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("stateChr"), estado);
            }
        };
    }

    /**
     * Filtro por el Id
     *
     * @param id
     * @return
     */

    public static Specification<Logpayment> getById(final Long id) {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("idpaymentNum"), id);
            }
        };
    }

    public static Specification<Logpayment> getFechaDesdeHasta(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery,
                                                                    CriteriaBuilder criteriaBuilder) {
                if (creationdateTimStart != null && creationdateTimEnd != null) {
                    return criteriaBuilder.between(root.get(fecha).as(java.sql.Timestamp.class), creationdateTimStart, creationdateTimEnd);

                } else if (creationdateTimStart != null) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(fecha).as(java.sql.Timestamp.class), creationdateTimStart);
                }
                return criteriaBuilder.lessThanOrEqualTo(root.get(fecha).as(java.sql.Timestamp.class), creationdateTimEnd);
            }
        };
    }

    /**
     * Filto por id de compania
     *
     * @param id
     * @return
     */
    public static Specification<Logpayment> getByCompanyId(final Long id) {
        return new Specification<Logpayment>() {

            @Override
            public Predicate toPredicate(Root<Logpayment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logmts, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String>get("idcompanyPk")), id);
            }
        };
    }

}
