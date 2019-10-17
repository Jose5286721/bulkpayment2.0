package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Beneficiary;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.Paymentorderdetail;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;;

/**
 * Filtros para obtencion de detalles de ordenes de pago
 */
public class PaymentOrderDetailSpecs {

    /**
     * Consulta de detalle/s de orden/es de pago segun identificador de orden de pago.
     * @param paymentOrderId identificador del orden de pago
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getByPaymentOrderId(final Long paymentOrderId){
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorderdetail, Paymentorder> groupJoin = root.join("paymentorder");
                return cb.equal((groupJoin.<String> get("idpaymentorderPk")), paymentOrderId);
            }
        };
    }

    /**
     * Consulta de detalles de ordenes de pago segun identificador de compania
     * @param idCompany
     * @return
     */
    public static Specification<Paymentorderdetail> getByCompanyId(final Long idCompany) {
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorderdetail, Paymentorder> groupJoin = root.join("paymentorder");
                Join<Paymentorder, Company> secgroupJoin = groupJoin.join("company");
                return cb.equal((secgroupJoin.<String>get("idcompanyPk")), idCompany);
            }
        };
    }

    /**
     * Consulta de detalle de orden de pago segun cedula de identidad del suscriptor
     * @param subscriberCiChr: cedula de identidad
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getByBeneficiaryCI(final String subscriberCiChr){
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorderdetail, Beneficiary> groupJoin = root.join("beneficiary");
                return cb.equal((groupJoin.<String> get("subscriberciChr")), subscriberCiChr);
            }
        };
    }

    /**
     * Consulta de detalle de orden de pago segun numero de telefono del suscriptor
     * @param phonenumberChr: numero de telefono
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getByPhoneNumberChr(final String phonenumberChr){
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorderdetail, Beneficiary> groupJoin = root.join("beneficiary");
                return cb.like(cb.lower(groupJoin.get("phonenumberChr")), concatParam(phonenumberChr.toLowerCase()));
            }
        };
    }

    /**
     * Obtencion de detalles de ordenes de pago segun rango de fecha
     * @param fromDate fecha inicial
     * @param toDate   fecha final
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getByRangeDate(final Date fromDate, final Date toDate) {
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                final String dateColumn = "updatedateTim";
                Join<Paymentorderdetail, Paymentorder> groupJoin = root.join("paymentorder");
                if (fromDate != null && toDate != null) {
                    return cb.between(groupJoin.get(dateColumn).as(Timestamp.class), fromDate, toDate);

                }else if(fromDate != null){
                    return cb.greaterThanOrEqualTo(groupJoin.get(dateColumn).as(Timestamp.class), fromDate);

                }
                return cb.lessThanOrEqualTo(groupJoin.get(dateColumn).as(Timestamp.class), toDate);

            }
        };
    }

    /**
     * Obtencion de detalle de orden de pago segun estado de la orden de pago
     * @param paymentState estado del pago en la orden de pago
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getByStateChr(final String paymentState) {
        return new Specification<Paymentorderdetail>() {

            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Paymentorderdetail, Paymentorder> groupJoin = root.join("paymentorder");
                return cb.equal((groupJoin.<String>get("stateChr")), paymentState);
            }
        };
    }

    /**
     * Consulta de todos los detalles de ordenes de pago
     * @return Specification<Paymentorderdetail> detalles de ordenes de pago
     */
    public static Specification<Paymentorderdetail> getAll(){
        return new Specification<Paymentorderdetail>() {
            @Override
            public Predicate toPredicate(Root<Paymentorderdetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }

}
