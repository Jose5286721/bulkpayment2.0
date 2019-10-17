package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Logaudit;

import javax.persistence.criteria.*;
import java.util.Date;

public class AuditSpecs {

    /**
     * Get all
     *
     * @return
     */
    public static Specification<Logaudit> getAll() {
        return new Specification<Logaudit>() {

            @Override
            public Predicate toPredicate(Root<Logaudit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Filtro por nombre de usuario
     * @param nombre
     * @return
     */
    public static Specification<Logaudit> getByUsuario(final String nombre) {
        return new Specification<Logaudit>() {

            @Override
            public Predicate toPredicate(Root<Logaudit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("usernameChr")), concatParam(nombre.toLowerCase()));
            }
        };
    }

    /**
     * Filtro por ip
     * @param ip
     * @return
     */
    public static Specification<Logaudit> getByIp(final String ip) {
        return new Specification<Logaudit>() {

            @Override
            public Predicate toPredicate(Root<Logaudit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("ipChr")), concatParam(ip.toLowerCase()));
            }
        };
    }

    public static Specification<Logaudit> getByRangeDate(final Date desdeFecha, final Date hastaFecha) {
        return new Specification<Logaudit>() {

            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Logaudit> root, javax.persistence.criteria.CriteriaQuery<?> criteriaQuery,
                                                                    CriteriaBuilder criteriaBuilder) {
                if (desdeFecha != null && hastaFecha != null) {
                    return criteriaBuilder.between(root.get("fechacreacionTim").as(java.sql.Timestamp.class), desdeFecha, hastaFecha);

                } else if (desdeFecha != null) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("fechacreacionTim").as(java.sql.Timestamp.class), desdeFecha);
                }
                return criteriaBuilder.lessThanOrEqualTo(root.get("fechacreacionTim").as(java.sql.Timestamp.class), hastaFecha);
            }
        };
    }

    /**
     * Filtro por tipo de acceso
     * @param accessType
     * @return
     */
    public static Specification<Logaudit> getByAccessType(final String accessType) {
        return new Specification<Logaudit>() {

            @Override
            public Predicate toPredicate(Root<Logaudit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String> get("accesstypeChr")), concatParam(accessType.toLowerCase()));
            }
        };
    }

    private static String concatParam(String param) {
        return '%' + param + '%';
    }
}
