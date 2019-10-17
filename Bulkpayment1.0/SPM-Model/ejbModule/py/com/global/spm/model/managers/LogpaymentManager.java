package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.model.exceptions.LogPaymentException;
import py.com.global.spm.model.interfaces.LogpaymentManagerLocal;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class LogdraftopManager
 * 
 * @author R2
 */
@Stateless
public class LogpaymentManager implements LogpaymentManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(LogpaymentManager.class);

	/**
	 * Default constructor.
	 */
	public LogpaymentManager() {

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistPaymentList(List<Logpayment> pagosList)
			throws LogPaymentException {
		for (Logpayment logpayment : pagosList) {
			try {
				this.persistPayment(logpayment);
			} catch (Exception e) {
				context.setRollbackOnly();
				log.error("Creating logPayment--> " + logpayment, e);
				break;
			}
		}
	}

	public void persistPayment(Logpayment logpayment) throws Exception {
		logpayment.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
		log.debug("insert logpayment --> " + logpayment);
		em.persist(logpayment);
		em.flush();
	}

	@Override
	public boolean updateLogpayment(Logpayment payment) {
		try {
			payment.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
			log.debug("updating logpayment --> " + payment);
			em.merge(payment);
			return true;
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("updating logpayment --> " + payment, e);
		}
		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logpayment> getPaymentsToReversionByIdPaymentOrder(
			long idPaymentOrder) {
		String consulta = "select l from Logpayment l where l.idpaymentorderPk = :idpaymentorder "
				+ "and (l.stateChr= :pagado or l.stateChr= :parcialPagado)";

		Query q = em.createQuery(consulta);
		q.setParameter("idpaymentorder", idPaymentOrder);
		q.setParameter("pagado", PaymentStates.SATISFACTORIO);
		q.setParameter("parcialPagado",
				PaymentStates.PARCIALMENTE_SATISFACTORIO);
		List<Logpayment> result = q.getResultList();
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logpayment> getRetryLogPayment(long paymentorderId) {
		String consulta = "select l from Logpayment l where l.idpaymentorderPk = :idpaymentorder "
				+ "and (l.stateChr= :error or l.stateChr= :parcialPagado)";

		Query q = em.createQuery(consulta);
		q.setParameter("idpaymentorder", paymentorderId);
		q.setParameter("error", PaymentStates.ERROR);
		q.setParameter("parcialPagado",
				PaymentStates.PARCIALMENTE_SATISFACTORIO);
		List<Logpayment> result = q.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logpayment> getPaymentsByIdPaymentOrder(long idPaymentOrder) {
		String consulta = "select l from Logpayment l where l.idpaymentorderPk = :idpaymentorder ";
		Query q = em.createQuery(consulta);
		q.setParameter("idpaymentorder", idPaymentOrder);;
		List<Logpayment> result = q.getResultList();
		return result;
	}

}
