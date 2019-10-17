package py.com.global.spm.GUISERVICE.specifications;

import org.springframework.data.jpa.domain.Specification;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Logdraftop;

import javax.persistence.criteria.*;
import java.lang.Process;
import java.sql.Timestamp;
import java.util.Date;

public class LogdraftOpSpecs {

    /**
     * Filtro por id de log DraftOp
     * @param id
     * @return
     */
    public static Specification<Logdraftop> getById(final Long id) {
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                return cb.equal(root.<String> get("iddrafopPk"), id);
            }
        };
    }

    /**
     * Filtro por id de borrador
     * @param idDraft Identificador del borrador
     * @return Specification<Logdraftop>
     */
    public static Specification<Logdraftop> getByIdDraft(final Long idDraft) {
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logdraftop, Draft> groupJoin = root.join("draft");
                return cb.equal((groupJoin.<String> get("iddraftPk")), idDraft);
            }
        };
    }

    /**
     * Filtro por id de codigo de evento
     * @param idEventCode Identificador del codigo de evento
     * @return Specification<Logdraftop>
     */
    public static Specification<Logdraftop> getByIdEventCode(final Long idEventCode) {
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logdraftop, Eventcode> groupJoin = root.join("eventcode");
                Join<Eventcode, EventcodeId> secgroupJoin = groupJoin.join("id");
                return cb.equal((secgroupJoin.<String> get("ideventcodeNum")), idEventCode);
            }
        };
    }

    /**
     * Filtro por rango de fecha
     * @param fromDate fecha inicial para el filtro
     * @param toDate   fecha final para el filtro
     * @return
     */
    public static Specification<Logdraftop> getByRangeDate(final Date fromDate, final Date toDate) {
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                final String dateColumn = "creationdateTim";

                if (fromDate != null && toDate != null) {
                    return cb.between(root.get(dateColumn).as(Timestamp.class), fromDate, toDate);

                }else if(fromDate != null){
                   return cb.greaterThanOrEqualTo(root.get(dateColumn).as(Timestamp.class), fromDate);

                }
                return cb.lessThanOrEqualTo(root.get(dateColumn).as(Timestamp.class), toDate);
            }
        };
    }

    /**
     * @param idProcess
     * @return
     */
    public static Specification<Logdraftop> getByProcess(final Short idProcess){
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logdraftop, Draft> groupJoin = root.join("eventcode");
                Join<Logdraftop, Process> secGroupJoin = groupJoin.join("process");
                return cb.equal((secGroupJoin.<String> get("idprocessPk")), idProcess);
            }
        };
    }

    /**
     * Obtencion del registro por id de compania
     * @return
     */
    public static Specification<Logdraftop> getByCompanyId(final Long idCompany) {
        return new Specification<Logdraftop>() {

            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Join<Logdraftop, Company> groupJoin = root.join("company");
                return cb.equal((groupJoin.<String>get("idcompanyPk")), idCompany);
            }
        };
    }

    /**
     * Obtencion del registro completo de LogDraftop
     * @return
     */
    public static Specification<Logdraftop> getAll(){
        return new Specification<Logdraftop>() {
            @Override
            public Predicate toPredicate(Root<Logdraftop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.conjunction();
            }
        };
    }




}
