package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Logmessage;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;

public class LogMessageSpecs {

    /**
     * Filtro por id de logmessage
     * @param id: identificador del mensaje de notificacion
     * @return
     */
    public static Specification<Logmessage> getById(final Long id) {
        return new Specification<Logmessage>() {

            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idmessagePk"), id);
            }
        };
    }

    /**
     * Filtro por id de orden de pago
     * @param paymentOrderId: identificador del orden de pago
     * @return Specification<Logmessage>
     */
    public static Specification<Logmessage> getByPaymentOrderId(final Long paymentOrderId) {
        return new Specification<Logmessage>() {

            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("idpaymentorderPk"), paymentOrderId);
            }
        };
    }

    /**
     * Filtro por rango de fecha
     * @param fromDate fecha inicial
     * @param toDate   fecha final
     * @return Specification<Logmessage>
     */
    public static Specification<Logmessage> getByRangeDate(final Date fromDate, final Date toDate) {
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                final String dateColumn = "creationdateTim";

                if (fromDate != null && toDate != null) {
                    return cb.between(root.get(dateColumn).as(Timestamp.class), fromDate, toDate);

                } else if (fromDate != null) {
                    return cb.greaterThanOrEqualTo(root.get(dateColumn).as(Timestamp.class), fromDate);

                }
                return cb.lessThanOrEqualTo(root.get(dateColumn).as(Timestamp.class), toDate);
            }
        };
    }


    /**
     * Filto por id de compania
     * @param id
     * @return
     */
    public static Specification<Logmessage> getByCompanyId(final Long id) {
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logmessage, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String> get("idcompanyPk")), id);
            }
        };
    }

    /**
     * Obtencion por estado del mensaje de notificacion
     * @param stateChr
     * @return
     */
    public static Specification<Logmessage> getByState(final String stateChr) {
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("stateChr"), stateChr);

            }
        };
    }

    /**
     * Filtro por la Cuenta
     *
     * @param cuenta
     * @return
     */
    public static Specification<Logmessage> getByCuentaOrigen(final String cuenta) {
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("orignumberChr"), cuenta);
            }
        };
    }



    public static Specification<Logmessage> getByNotificationEvent(final String notificationEventChr) {
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("notificationeventChr"), notificationEventChr);
            }
        };
    }

    /**
     * Obtencion de todos los mensajes de notificacion.
     *
     * @return Specification<Logmessage>
     */
    public static Specification<Logmessage> getAll(){
        return new Specification<Logmessage>() {
            @Override
            public Predicate toPredicate(Root<Logmessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.conjunction();
            }
        };
    }
}


