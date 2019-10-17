package py.com.global.model.managers;

import org.apache.log4j.Logger;
import py.com.global.model.exceptions.PaymentOrderRequiredValuesException;
import py.com.global.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.model.util.SpmConstants;
import py.com.global.model.util.SpmUtil;
import py.com.global.smsmanager.util.DraftStates;
import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.Paymentorderdetail;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * Session Bean implementation class PaymentOrderManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class PaymentOrderManager implements PaymentOrderManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(PaymentOrderManager.class);

	/**
	 * Default constructor.
	 */
	public PaymentOrderManager() {

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createPaymentorder(Paymentorder paymentorder,
			List<Paymentorderdetail> paymentorderdetails)
			throws PaymentOrderRequiredValuesException, Exception {
		try {
			log.debug("Creating paymentorder --> \n"
					+ paymentorder
					+ ", "
					+ SpmUtil
							.paymentorderdetailListToString(paymentorderdetails));
			// validar campos requeridos
			if (this.isValid(paymentorder, paymentorderdetails)) {
				em.persist(paymentorder);
				for (Paymentorderdetail pod : paymentorderdetails) {
					pod.getId().setIdpaymentorderPk(
							paymentorder.getIdpaymentorderPk());
					em.persist(pod);
				}
			} else {
				throw new PaymentOrderRequiredValuesException();
			}
		} catch (PaymentOrderRequiredValuesException e) {
			log.error("Faltan datos requeridos de la orden de pago --> "
					+ paymentorder);
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("Creating paymentorder --> " + paymentorder, e);
			throw e;
		}

	}

	private boolean isValid(Paymentorder paymentorder,
			List<Paymentorderdetail> paymentorderdetails) {
		if (paymentorder.getCreationdateTim() == null) {
			log.error("creation date is not null --> " + paymentorder);
			return false;
		} else if (paymentorder.getIdcompanyPk() < 0) {
			log.error("falta idcompany --> " + paymentorder);
			return false;
		} else if (paymentorder.getIddraftPk() < 1) {
			log.error("falta iddraft --> " + paymentorder);
			return false;
		} else if (paymentorder.getIdpaymentordertypePk() < 1) {
			log.error("falta tipo de orden de pago --> " + paymentorder);
			return false;
		} else if (paymentorder.getIdworkflowPk() < 1) {
			log.error("falta idworkflow --> " + paymentorder);
			return false;
		} else if (paymentorder.getUpdatedateTim() == null) {
			log.error("updateDate is not null --> " + paymentorder);
			return false;
		}
		if (paymentorderdetails == null) {
			log.error("falta paymentorderdetails --> " + paymentorder);
			return false;
		} else {
			for (Paymentorderdetail pod : paymentorderdetails) {
				if (pod.getId().getIdbeneficiaryPk() < 1) {
					log.error("beneficiario no valido --> " + pod);
					return false;
				} else if (pod.getAmountNum() < 1) {
					log.error("monto no valido --> " + pod);
					return false;
				}
			}
		}
		return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean updatePaymentorder(Paymentorder paymentorder) {
		try {
			paymentorder.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
			log.debug("updating paymentorder --> " + paymentorder);
			em.merge(paymentorder);
			return true;
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("updating paymentorder --> " + paymentorder, e);
		}
		return false;
	}

	public Paymentorder getPaymentorder(long idpaymentorder) {
		Paymentorder p = null;
		try {
			em.flush();
			p = em.find(Paymentorder.class, idpaymentorder);
		} catch (Exception e) {
			log.error("Finding paymentorder --> idpaymentorder="
					+ idpaymentorder, e);
			return null;
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<Paymentorderdetail> getPaymentorderdetails(long idPaymentorder) {
		List<Paymentorderdetail> list = null;
		try {
			Query q = em
					.createNamedQuery("getPaymentorderdetailByIdPaymentorder");
			q.setParameter("idpaymentorder", idPaymentorder);
			list = q.getResultList();
		} catch (Exception e) {
			log.error("Getting paymentorderdetails --> idPaymentorder="
					+ idPaymentorder, e);
		}
		return list;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createPaymentorderAndInactivateDraft(Draft draft,
			Paymentorder paymentorder,
			List<Paymentorderdetail> paymentorderdetails)
			throws PaymentOrderRequiredValuesException, Exception {
		try {
			log.debug("Creating paymentorder --> \n"
					+ paymentorder
					+ ", "
					+ SpmUtil
							.paymentorderdetailListToString(paymentorderdetails));
			// validar campos requeridos
			if (this.isValid(paymentorder, paymentorderdetails)) {
				em.persist(paymentorder);
				for (Paymentorderdetail pod : paymentorderdetails) {
					pod.getId().setIdpaymentorderPk(
							paymentorder.getIdpaymentorderPk());
					em.persist(pod);
				}
				draft.setStateChr(DraftStates.INACTIVE);
				em.merge(draft);
			} else {
				throw new PaymentOrderRequiredValuesException();
			}
		} catch (PaymentOrderRequiredValuesException e) {
			log.error("Faltan datos requeridos de la orden de pago --> "
					+ paymentorder);
			throw e;
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("Creating paymentorder --> " + paymentorder, e);
			throw e;
		}

	}
}
