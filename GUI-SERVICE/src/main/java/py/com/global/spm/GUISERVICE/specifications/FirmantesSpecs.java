package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Approval;
import py.com.global.spm.domain.entity.Paymentorder;

import javax.persistence.criteria.*;

public class FirmantesSpecs {
    /**
     * Filtro por id del Approval
     * @param id
     * @return
     */
    public static Specification<Approval> getById(final Long id) {
        return new Specification<Approval>() {

            @Override
            public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idapprovalPk"), id);
            }
        };
    }
    /**
     * Filto por id de OrdendePago
     * @param id
     * @return
     */
    public static Specification<Approval> getByPaymentOrderId(final Long id) {
        return new Specification<Approval>() {

            @Override
            public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Approval, Paymentorder> groupJoin = root.join("paymentorder");
                return cb.equal((groupJoin.<String> get("idpaymentorderPk")), id);
            }
        };
    }

    /**
     * Filtro Approval por usuario
     * @param id
     * @return
     */
    public static Specification<Approval> getByUserId(final Long id) {
        return new Specification<Approval>() {

            @Override
            public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Approval, Paymentorder> groupJoin = root.join("workflowdet");
                return cb.equal((groupJoin.<String> get("user").get("iduserPk")), id);
            }
        };
    }
    /**
     * Filtro Approval is
     * @param signedNum
     * @return
     */
    public static Specification<Approval> getBySignedNumId(final boolean signedNum) {
        return new Specification<Approval>() {

            @Override
            public Predicate toPredicate(Root<Approval> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("signedNum"), signedNum);
            }
        };
    }

}
