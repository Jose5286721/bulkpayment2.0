package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.model.repository.ICompanyDao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Service
@Transactional
public class CompanyService  {
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final ICompanyDao dao;

    @Autowired
    public CompanyService(ICompanyDao dao) {
        this.dao = dao;
    }

    public Company getCompanyById(long idCompany) {
        log.trace("Finding company --> idCompany=" + idCompany);
        Company company = null;
        try {
            Optional<Company> c = dao.findById(idCompany);
            if (c.isPresent()) {
                company = c.get();
                this.updateTrxCounters(company);
            }
        } catch (Exception e) {
            log.error("Finding company --> idCompany= " + idCompany, e);
        }
        return company;
    }

    private void updateTrxCounters(Company company) {
        GregorianCalendar timestamp = new GregorianCalendar();
        int nowDay = timestamp.get(Calendar.DAY_OF_YEAR);
        int nowMonth = timestamp.get(Calendar.MONTH);
        int nowYear = timestamp.get(Calendar.YEAR);

        // Ultima fecha del ultimo cobro
        Date lastTime = company.getLastopdateTim();
        timestamp.setTimeInMillis(lastTime.getTime());
        int lastDay = timestamp.get(Calendar.DAY_OF_YEAR);
        int lastMonth = timestamp.get(Calendar.MONTH);
        int lastYear = timestamp.get(Calendar.YEAR);

        if (nowYear > lastYear) {
            company.setTrxdaycountNum(0L);
            company.setTrxmonthcountNum(0L);
        } else {
            if (nowMonth > lastMonth) {
                company.setTrxdaycountNum(0L);
                company.setTrxmonthcountNum(0L);
            } else {
                if (nowDay > lastDay) {
                    company.setTrxdaycountNum(0L);
                }
            }
        }
    }

    public Company saveOrUpdate(Company company) {
        log.debug("Updating company --> " + company);
        try {
           return dao.save(company);
        } catch (Exception e) {
            log.error("Updating company --> " + company, e);
           throw e;
        }
    }
}
