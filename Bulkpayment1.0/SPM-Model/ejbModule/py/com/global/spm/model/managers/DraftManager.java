package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Draftdetail;
import py.com.global.spm.model.interfaces.DraftManagerLocal;
import py.com.global.spm.model.util.DraftStates;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class DraftManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class DraftManager implements DraftManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(DraftManager.class);

	/**
	 * Default constructor.
	 */
	public DraftManager() {
	}

	@Override
	public Draft getDraftById(long iddraft) {
		log.trace("Finding draft by id --> iddraft=" + iddraft);
		Draft draft = null;
		try {
			draft = em.find(Draft.class, iddraft);
		} catch (Exception e) {
			log.error("Finding draft by id --> iddraft=" + iddraft, e);
		}
		return draft;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Draftdetail> getDraftDetail(long iddraft) {
		List<Draftdetail> draftdetailList = null;
		try {
			StringBuilder jpql = new StringBuilder(
					"SELECT d FROM Draftdetail d ");
			jpql.append("WHERE d.id.iddraftPk = :iddraft ");
			Query query = em.createQuery(jpql.toString());
			query.setParameter("iddraft", iddraft);
			draftdetailList = query.getResultList();
		} catch (Exception e) {
			log.error("Obteniendo detalles de draft", e);
		}
		return draftdetailList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Draft> getProgramedPaymentorder(Timestamp paiddate) {
		List<Draft> draftList = null;
		try {
			StringBuilder jpql = new StringBuilder("SELECT d FROM Draft d ");
			jpql.append("WHERE d.recurrentNum = :recurrent ");
			jpql.append("AND d.paiddateTim < :paiddate ");
			jpql.append("AND d.stateChr = :state ");
			Query query = em.createQuery(jpql.toString());
			query.setParameter("recurrent", Boolean.FALSE);
			query.setParameter("paiddate", paiddate);
			query.setParameter("state", DraftStates.ACTIVE);
			draftList = query.getResultList();
		} catch (Exception e) {
			log.error("Obteniendo draft programados", e);
		}
		return draftList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Draft> getRecurrentPaymentorder(int generateDay, Timestamp now) {
		List<Draft> draftList = null;
		try {
			/*
			 * si estamos el 28 de febrero y si el dia de generacion es 29 o 30,
			 * generamos hoy 28 de febrero la orden de pago
			 */
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(now);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH);
			if (month == java.util.Calendar.FEBRUARY && day == 28
					&& generateDay > 28) {
				generateDay = 28;
			}
			//
			// alambre con las fechas
			Calendar alambreDesde = GregorianCalendar.getInstance();
			alambreDesde.setTime(now);
			alambreDesde.set(Calendar.HOUR_OF_DAY, 0);
			alambreDesde.set(Calendar.MINUTE, 0);
			alambreDesde.set(Calendar.SECOND, 0);
			alambreDesde.set(Calendar.MILLISECOND, 0);
			String alambreDesdeStr = SimpleDateFormat.getDateTimeInstance()
					.format(alambreDesde.getTime());
			Calendar alambreHasta = GregorianCalendar.getInstance();
			alambreHasta.setTime(now);
			alambreHasta.set(Calendar.HOUR_OF_DAY, 23);
			alambreHasta.set(Calendar.MINUTE, 59);
			alambreHasta.set(Calendar.SECOND, 59);
			alambreHasta.set(Calendar.MILLISECOND, 59);
			String alambreHastaStr = SimpleDateFormat.getDateTimeInstance()
					.format(alambreHasta.getTime());
			//
			log.trace("generateDay=" + generateDay + ", now=" + now
					+ ", alambreDesde=" + alambreDesdeStr + ", alambreHasta="
					+ alambreHastaStr);
			StringBuilder jpql = new StringBuilder("SELECT d FROM Draft d ");
			jpql.append("WHERE d.recurrentNum = :recurrent ");
			jpql.append("AND d.fromdateTim <= :date ");
			jpql.append("AND d.todateTim > :date ");
			jpql.append("AND d.generatedayNum = :generateday ");
			jpql.append("AND d.stateChr = :state ");
			jpql.append("AND d.iddraftPk NOT IN ");
			jpql.append("(SELECT po.iddraftPk FROM Paymentorder po ");
			jpql.append("WHERE po.creationdateTim >= :alambreDesde ");
			jpql.append("AND po.creationdateTim < :alambreHasta)");
			Query query = em.createQuery(jpql.toString());
			query.setParameter("recurrent", Boolean.TRUE);
			query.setParameter("date", now);
			query.setParameter("generateday", generateDay);
			query.setParameter("state", DraftStates.ACTIVE);
			query.setParameter("alambreDesde",
					new Timestamp(alambreDesde.getTimeInMillis()));
			query.setParameter("alambreHasta",
					new Timestamp(alambreHasta.getTimeInMillis()));
			draftList = query.getResultList();
		} catch (Exception e) {
			log.error("Obteniendo draft recurrentes", e);
		}
		return draftList;
	}

	@Override
	public boolean updateDraft(Draft draft) {
		try {
			log.debug("Updating draft --> " + draft);
			em.merge(draft);
			return true;
		} catch (Exception e) {
			log.error("Error udpating draft --> " + draft, e);
		}
		return false;
	}

}
