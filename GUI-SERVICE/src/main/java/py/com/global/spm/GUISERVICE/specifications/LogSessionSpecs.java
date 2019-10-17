package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Logsession;

import javax.persistence.criteria.*;
import java.util.Date;

public class LogSessionSpecs {

    /**
     * Filto por Usuario
     *
     * @param username
     * @return
     */
    public static Specification<Logsession> getByUsername(final String username) {
        return new Specification<Logsession>() {

            @Override
            public Predicate toPredicate(Root<Logsession> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.<String>get("userChr")), concatParam(username.toLowerCase()));
            }
        };
    }

    /**
     * Get all
     *
     * @return
     */
    public static Specification<Logsession> getAll() {
        return new Specification<Logsession>() {

            @Override
            public Predicate toPredicate(Root<Logsession> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.conjunction();
            }
        };
    }



    public static Specification<Logsession> getFechaDesdeHasta(final String fecha, final Date creationdateTimStart, final Date creationdateTimEnd) {
        return new Specification<Logsession>() {

            @Override
            public Predicate toPredicate(Root<Logsession> root, CriteriaQuery<?> criteriaQuery,
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

    private static String concatParam(String param) {
        return '%' + param + '%';
    }


}
