package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.Eventcode;

import javax.persistence.criteria.*;

public class CodigoEventosSpecs {
    private CodigoEventosSpecs(){
    }
    /**
     * Filto por Id de Proceso
     * @param id
     * @return
     */
    public static Specification<Eventcode> getByProcessId(final Short id) {
        return new Specification<Eventcode>() {

            @Override
            public Predicate toPredicate(Root<Eventcode> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Eventcode, Process> groupJoin = root.join("process");
                return cb.equal((groupJoin.<String> get("idprocessPk")), id);
            }
        };
    }

    /**
     * Filtro por evento
     * @param evento
     * @return
     */
    public static Specification<Eventcode> getByParametro(final Integer evento) {
        return new Specification<Eventcode>() {

            @Override
            public Predicate toPredicate(Root<Eventcode> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(cb.lower(root.<String> get("id").get("ideventcodeNum")), evento);

            }
        };
    }
    private static String concatParam(String param) {
        return '%' + param + '%';
    }

}
