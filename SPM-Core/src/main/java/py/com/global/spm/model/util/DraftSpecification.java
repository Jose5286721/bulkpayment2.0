package py.com.global.spm.model.util;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Draft;

import java.util.Date;
import java.util.List;

public class DraftSpecification {
    public static Specification<Draft> isRecurrent(boolean recurrent) {
        return (root, query, cb) -> cb.equal(root.get("recurrentNum"), recurrent);
    }

    public static Specification<Draft> byStateChr( String stateChr) {
        return (root, query, cb) -> cb.equal(root.get("stateChr"), stateChr);
    }

    public static Specification<Draft> byGenerateDays(List<Byte> generateDay) {
        return (root, query, cb) -> root.get("generatedayNum").in(generateDay);
    }

    public static Specification<Draft> betweenFromDateAndToDate(final Date date) {
        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("todateTim").as(java.sql.Timestamp.class),date),
                cb.lessThanOrEqualTo(root.get("fromdateTim").as(java.sql.Timestamp.class),date)
        );
    }

    public static Specification<Draft> byLastGenerationDate(Date today) {
        return (root, query, cb) -> cb.or(
                cb.lessThan(root.get("lastGenerationDate"),today),
                cb.isNull(root.get("lastGenerationDate"))
        );
    }
}
