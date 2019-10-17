package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Company;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class CompanyManager
 */
@Stateless
public class CompanyManager implements CompanyManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(CompanyManager.class);

	/**
	 * Default constructor.
	 */
	public CompanyManager() {

	}

	public Company getCompanyById(long idCompany) {
		log.trace("Finding company --> idCompany=" + idCompany);
		Company c = null;
		try {
			c = em.find(Company.class, idCompany);
			if (c != null) {
				this.updateTrxCounters(c);
			}
		} catch (Exception e) {
			log.error("Finding company --> idCompany= " + idCompany, e);
			return null;
		}
		return c;
	}

	private void updateTrxCounters(Company company) {
		GregorianCalendar timestamp = new GregorianCalendar();
		int nowDay = timestamp.get(Calendar.DAY_OF_YEAR);
		int nowMonth = timestamp.get(Calendar.MONTH);
		int nowYear = timestamp.get(Calendar.YEAR);

		// Ultima fecha del ultimo cobro
		Timestamp lastTime = company.getLastopdateTim();
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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean updateCompany(Company company) {
		log.debug("Updating company --> " + company);
		try {
			em.merge(company);
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("Updating company --> " + company, e);
			return false;
		}
		return true;
	}

}
