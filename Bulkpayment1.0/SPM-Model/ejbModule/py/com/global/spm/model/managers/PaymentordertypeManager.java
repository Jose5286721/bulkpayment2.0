package py.com.global.spm.model.managers;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Paymentordertype;
import py.com.global.spm.model.interfaces.PaymentordertypeManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class PaymentordertypeManager
 */
@Stateless
public class PaymentordertypeManager implements PaymentordertypeManagerLocal {
	
	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(PaymentordertypeManager.class);

	/**
	 * Default constructor.
	 */
	public PaymentordertypeManager() {
	}

	public Paymentordertype getPaymentordertypeById(long idPaymentordertype) {
		Paymentordertype pot = null;
		try {
			pot = em.find(Paymentordertype.class, idPaymentordertype);
		} catch (Exception e) {
			log.error("Finding Paymentordertype --> idPaymentordertype= "
					+ idPaymentordertype, e);
			return null;
		}
		return pot;
	}

	public String getPaymentOrderType(long idpaymentorder) {
		String str=null;
		try {
			String sql = "SELECT pot.NAME_CHR FROM paymentorder po "
					+ " JOIN PAYMENTORDERTYPE pot on po.IDPAYMENTORDERTYPE_PK=pot.IDPAYMENTORDERTYPE_PK "
					+ " WHERE po.IDPAYMENTORDER_PK=:idpaymentorderPk";
			Query q = em.createNativeQuery(sql);
			q.setParameter("idpaymentorderPk", idpaymentorder);
			str = (String) q.getSingleResult();
			if (str == null) {
				return "";
			}
		} catch (Exception e) {
			log.error(e);
			return "";
		}
		return str;
	}

}