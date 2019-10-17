package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.*;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;

public class LogMtsSpecs {

    private LogMtsSpecs(){

    }

    /**
     * Filto por Nro de Telefono
     *
     * @param phoneNumber: Nro de telefono del Beneficiario
     * @return Specification<Logmts>
     */
    public static Specification<Logmts> getByBeneficiaryPhoneNumber(final String phoneNumber) {
        return (root, criteriaQuery, cb) -> {
            Join<Logmts, Paymentorderdetail> groupJoin = root.join("paymentorderdetail");
            Join<Paymentorderdetail, Beneficiary> groupJoin2 = groupJoin.join("beneficiary");
            return cb.equal((groupJoin2.<String>get("phonenumberChr")), phoneNumber);
        };
    }

    /**
     * Get all
     *
     * @return Specification<Logmts>
     */
    public static Specification<Logmts> getAll() {
        return (root, criteriaQuery, cb) -> cb.conjunction();
    }

    /**
     * Filto por Nro de Beneficiario EJ: (CI)
     *
     * @param beneficiaryIdentificator
     * @return
     */
    public static Specification<Logmts> getByBeneficiaryIdentificator(final String beneficiaryIdentificator) {
        return (root, criteriaQuery, cb) -> {
            Join<Logmts, Paymentorderdetail> groupJoin = root.join("paymentorderdetail");
            Join<Paymentorderdetail, Beneficiary> groupJoin2 = groupJoin.join("beneficiary");
            return cb.equal((groupJoin2.<String>get("subscriberciChr")), beneficiaryIdentificator);
        };
    }

    /**
     * Filtro por estado del LogMts
     *
     * @param estado
     * @return
     */

    public static Specification<Logmts> getByEstado(final String estado) {
        return (Specification<Logmts>) (root, criteriaQuery, cb) -> cb.equal(root.<String>get("stateChr"), estado);
    }

    public static Specification<Logmts> getFechaDesdeHasta(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (creationdateTimStart != null && creationdateTimEnd != null) {
                return criteriaBuilder.between(root.get(fecha).as(Timestamp.class), creationdateTimStart, creationdateTimEnd);

            } else if (creationdateTimStart != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fecha).as(Timestamp.class), creationdateTimStart);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(fecha).as(Timestamp.class), creationdateTimEnd);
        };
    }

    /**
     * Filto por id de compania
     *
     * @param id
     * @return
     */
    public static Specification<Logmts> getByCompanyId(final Long id) {
        return(root, criteriaQuery, cb) -> {
            Join<Logmts, Company> groupJoin = root.join("company");
            return cb.equal((groupJoin.<String>get("idcompanyPk")), id);
        };
    }

    /**
     * Filto por email del approval
     *
     * @param email
     * @return  Specification<Logmts>
     */
    public static Specification<Logmts> getByEmailApproval(final String email) {
        return (root, criteriaQuery, cb) -> {
            Join<Logmts, Paymentorderdetail> groupJoin = root.join("paymentorderdetail");
            Join<Paymentorderdetail, Paymentorder> groupJoin2 = groupJoin.join("paymentorder");
            Join<Paymentorder, Approval> groupJoin3 = groupJoin2.join("approvals");
            Join<Approval, Workflowdet> groupJoin4 = groupJoin3.join("workflowdet");
            Join<Workflowdet, User> groupJoin5 = groupJoin4.join("user");
            return cb.like(cb.lower(groupJoin5.get("emailChr")), concatParam(email.toLowerCase()));

        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }
}
