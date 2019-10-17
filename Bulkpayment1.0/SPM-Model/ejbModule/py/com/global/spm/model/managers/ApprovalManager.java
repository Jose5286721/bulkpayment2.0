package py.com.global.spm.model.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Approval;
import py.com.global.spm.model.interfaces.ApprovalManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class ApprovalManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class ApprovalManager implements ApprovalManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(ApprovalManager.class);

	/**
	 * Default constructor.
	 */
	public ApprovalManager() {
	}

	@SuppressWarnings("unchecked")
	public List<Approval> getApprovalsByIdPaymentorder(long idpaymentorder) {
		log.trace("getting approvals by idPaymentorder --> idpaymentorder="
				+ idpaymentorder);
		List<Approval> approvalList = null;
		try {
			Query q = em.createNamedQuery("getApprovalsByIdPaymentorder");
			q.setParameter("idpaymentorder", idpaymentorder);
			approvalList = q.getResultList();
		} catch (Exception e) {
			log.error("getting approvals by idPaymentorder --> idpaymentorder="
					+ idpaymentorder, e);
		}
		return approvalList;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean insertApproval(List<Approval> approvalList) {
		try {
			for (Approval approval : approvalList) {
				log.debug("inserting approval --> " + approval);
				em.persist(approval);
			}
		} catch (Exception e) {
			log.error("Inserting approval", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateApproval(Approval approval) {
		try {
			log.debug("updating approval --> " + approval);
			em.merge(approval);
			return true;
		} catch (Exception e) {
			log.error("updating user --> " + approval, e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getIdSignersByIdPaymentorder(long idpaymentorder) {
		log.trace("getting id signers by idPaymentorder --> idpaymentorder="
				+ idpaymentorder);
		List<Long> idSignersList = new ArrayList<Long>();
		try {
			List<Approval> approvalList = null;
			Query q = em.createNamedQuery("getApprovalsByIdPaymentorder");
			q.setParameter("idpaymentorder", idpaymentorder);
			approvalList = q.getResultList();
			if (approvalList != null && approvalList.size() > 0) {
				for (Approval a : approvalList) {
					idSignersList.add(a.getIduserPk());
				}
			}
		} catch (Exception e) {
			log.error("getting id signers by idPaymentorder --> idpaymentorder="
					+ idpaymentorder, e);
		}
		return idSignersList;
	}
}
