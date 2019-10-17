package py.com.global.spm.mts.manager;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import py.com.global.mts.entities.Subscriber;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class SubscriberValidationManager
 */
@Stateless
public class SubscriberValidationManager implements
		SubscriberValidationManagerLocal {

	@PersistenceContext(unitName = "SPM-MTS-PU")
	private EntityManager em;

	private final Logger log = Logger
			.getLogger(SubscriberValidationManager.class);

	public SubscriberValidationManager() {
	}

	public Integer getSucriberValitation(String ci, String msisdn) {
		//TODO Remove-Verify this siempre es exitoso
		if(true)
			return SpmConstants.EVENT_SUBSCRIBER_EXIST;

		log.trace("validate subscriber CI and MSISDN --> ci=" + ci
				+ " CI --> msisdn= " + msisdn);
		Subscriber s = new Subscriber();
		Integer eventCode = null;
		try {
			Query q = em
					.createQuery("SELECT s FROM Subscriber s WHERE s.msisdn= :msisdn AND s.deleted is null");
			q.setParameter("msisdn", msisdn);
			int t = q.getResultList().size();
			if (t > 0) {// EXITE
				eventCode = SpmConstants.EVENT_SUBSCRIBER_INVALID_DOC_NUMBER;
				// eventCode=0;//Solo existe pero la cedula no coincide. -2
				s = (Subscriber) q.getResultList().get(0);
				if (s.getDocumentNumber().equals(ci)) {
					eventCode = SpmConstants.EVENT_SUBSCRIBER_EXIST;
				}
			} else {// SUBSCRIBER NO EXISTE EN MTS. -4
				eventCode = SpmConstants.EVENT_SUBSCRIBER_NOT_EXIST;
			}
		} catch (Exception e) {
			log.error("getting subscriber validation by msisdn --> msisdn="
					+ msisdn + " and ci --> ci=" + ci, e);
			return eventCode = SpmConstants.EVENT_SUBSCRIBER_VALIDATION_ERROR;
		}

		return eventCode;
	}
}
