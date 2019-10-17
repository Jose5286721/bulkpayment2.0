package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.*;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;

public class SmsLogMessageSpecs {


    /**
     * Get all
     *
     * @return
     */
    public static Specification<Smslogmessage> getAll() {
        return new Specification<Smslogmessage>() {

            @Override
            public Predicate toPredicate(Root<Smslogmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }


    /**
     * Filtro por Numero origen
     *
     * @param sourcenumberChr
     * @return
     */

    public static Specification<Smslogmessage> getBySourceNumber(final String sourcenumberChr) {
        return (root, criteriaQuery, cb) -> cb.equal(root.<String>get("sourcenumberChr"), sourcenumberChr);
    }

    /**
     * Filtro por estado del SmsLogMessage
     *
     * @param estado
     * @return
     */

    public static Specification<Smslogmessage> getByEstado(final String estado) {
        return (root, criteriaQuery, cb) -> cb.equal(root.<String>get("stateChr"), estado);
    }

    public static Specification<Smslogmessage> getFechaDesdeHasta(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
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
    public static Specification<Smslogmessage> getByCompanyId(final Long id) {
        return (root, criteriaQuery, cb) -> {
            Join<Logmts, Company> groupJoin = root.join("company");
            return cb.equal((groupJoin.<String>get("idcompanyPk")), id);
        };
    }

}
