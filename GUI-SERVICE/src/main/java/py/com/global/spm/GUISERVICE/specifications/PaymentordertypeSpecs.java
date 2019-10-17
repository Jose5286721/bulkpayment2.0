package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Paymentordertype;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Created by global on 18-04-04
 */
public class PaymentordertypeSpecs {

    public PaymentordertypeSpecs(){

    }
    /**
     * Filtro por nombre del tipo de orden de pago
     * @param nombre
     * @return
     */
    public static Specification<Paymentordertype> getByNameChr(final String nombre) {
        return new Specification<Paymentordertype>() {

            @Override
            public Predicate toPredicate(Root<Paymentordertype> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("nameChr")), concatParam(nombre.toLowerCase()));
            }
        };
    }

    /**
     * Filtro por descripcion del tipo de orden de pago, filtro de tipo like and lower
     * @param description
     * @return
     */
    public static Specification<Paymentordertype> getByDescription(final String description) {
        return new Specification<Paymentordertype>() {

            @Override
            public Predicate toPredicate(Root<Paymentordertype> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("descriptionChr")), concatParam(description.toLowerCase()));
            }
        };
    }

    /**
     * Retorna todos los campos
     * @return
     */
    public static Specification<Paymentordertype> getAll() {
        return new Specification<Paymentordertype>() {

            @Override
            public Predicate toPredicate(Root<Paymentordertype> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }



}
