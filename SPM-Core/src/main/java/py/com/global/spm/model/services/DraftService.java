package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Draft;
import py.com.global.spm.domain.entity.Draftdetail;
import py.com.global.spm.model.repository.IDraftDao;
import py.com.global.spm.model.util.DraftSpecification;
import py.com.global.spm.model.util.DraftStates;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class DraftService {
    private final Logger log = LoggerFactory.getLogger(DraftService.class);

    private final IDraftDao dao;

    @Autowired
    public DraftService(IDraftDao dao) {
        this.dao = dao;
    }

    public Draft getDraftById(long iddraft) {
        try {
            return dao.findById( iddraft).orElse(null);

        } catch (Exception e) {
            log.error("Error al buscar el draft con id --> iddraft=" + iddraft, e);
            throw e;
        }
    }

    public List<Draftdetail> getDraftDetail(long iddraft) {
        List<Draftdetail> draftdetailList = null;
        try {
            Draft draft = getDraftById(iddraft);
            if(draft!=null) draftdetailList= new ArrayList<>(draft.getDraftdetails());
        } catch (Exception e) {
            log.error("Obteniendo detalles de draft", e);
        }
        return draftdetailList;
    }

    public List<Draft> getProgramedPaymentorder(Timestamp paiddate) {
        List<Draft> draftList = null;
        try {
            draftList = dao.getByRecurrentNumAndStateChrAndPaiddateTimBefore(Boolean.FALSE, DraftStates.ACTIVE,paiddate);
        } catch (Exception e) {
            log.error("Obteniendo draft programados", e);
        }
        return draftList;
    }

    public List<Draft> getRecurrentPaymentorder(Timestamp now) {
        List<Draft> draftList = null;
        try {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(now);
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            List<Byte> generateDays = new ArrayList<>();
            YearMonth yearMonthObject = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
            /*
                Si hoy es el ultimo dia del mes, y es menor a 31,
                hay que buscar los borradores con dia de generacion
                desde el ultimo dia del mes actual hasta el 31
             */
            generateDays.add(day.byteValue());
            if(day.intValue() == yearMonthObject.lengthOfMonth() && day<31 ){
                while(day<31){
                    day++;
                    generateDays.add(day.byteValue());
                }
            }
            Specification<Draft> where;
            Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            where = DraftSpecification.isRecurrent(true);
            where = where.and(DraftSpecification.byStateChr(DraftStates.ACTIVE));
            where = where.and(DraftSpecification.byGenerateDays(generateDays));
            where = where.and(DraftSpecification.betweenFromDateAndToDate(today));
            where = where.and(DraftSpecification.byLastGenerationDate(today));
            draftList = dao.findAll(where);
        } catch (Exception e) {
            log.error("Obteniendo draft recurrentes", e);
        }
        return draftList;
    }

    public boolean saveOrUpdate(Draft draft) {
        try {
            log.debug("Updating draft --> " + draft);
            dao.save(draft);
            return true;
        } catch (Exception e) {
            log.error("Error udpating draft --> " + draft, e);
        }
        return false;
    }
}
