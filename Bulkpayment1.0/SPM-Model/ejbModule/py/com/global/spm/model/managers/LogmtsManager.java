package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logmts;
import py.com.global.spm.model.interfaces.LogmtsManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Maneja operaciones basicas de base de datos sobre el entity Logmts.
 * 
 * @author R2
 */
@Stateless
public class LogmtsManager implements LogmtsManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(LogmtsManager.class);

	/**
	 * Default constructor.
	 */
	public LogmtsManager() {
	}

	public void updateLogmts(Logmts logMts) {
		if (logMts == null) {
			log.error("logMts can't be null");
			return;
		}
		log.trace("Merge logMts --> " + logMts);
		try {
			logMts.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
			em.merge(logMts);
			em.flush();
		} catch (Exception e) {
			log.error("Merge logMts --> " + logMts, e);
		}
	}

	@Override
	public List<Logmts> findLogmtsToRevert(Long paymentId) {
		String consulta = "select l from Logmts l where l.idpaymentNum = :idpayment and l.stateChr= :state and l.trxtypeChr= :metodo";
		Query q = em.createQuery(consulta);
		q.setParameter("idpayment", paymentId);
		q.setParameter("state", SpmConstants.SUCCESS);
		q.setParameter("metodo", SpmConstants.MTS_OPERATION_PAYMENT);
		@SuppressWarnings("unchecked")
		List<Logmts> result = q.getResultList();
		return result;
	}

	@Override
	public List<Logmts> findLogmtsToRetry(Long paymentId) {
		String consulta = "select l from Logmts l where l.idpaymentNum = :idpayment and l.stateChr= :state and l.trxtypeChr= :metodo";
		Query q = em.createQuery(consulta);
		q.setParameter("idpayment", paymentId);
		q.setParameter("state", SpmConstants.ERROR);
		q.setParameter("metodo", SpmConstants.MTS_OPERATION_PAYMENT);
		@SuppressWarnings("unchecked")
		List<Logmts> result = q.getResultList();
		return result;
	}

}
