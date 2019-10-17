package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Smslogmessage;
import py.com.global.spm.model.interfaces.SmslogmessageManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class SmslogmessageManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class SmslogmessageManager implements SmslogmessageManagerLocal {
	
	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	
	private Logger log = Logger.getLogger(SmslogmessageManager.class);

	/**
	 * Default constructor.
	 */
	public SmslogmessageManager() {
	}

	@Override
	public void insertSmslogmessage(Smslogmessage smslogmessage) {
		log.trace("Persisting --> " + smslogmessage);
		try {
			em.persist(smslogmessage);
		} catch (Exception e) {
			log.error("Creating logdraftop --> " + smslogmessage, e);
		}
	}

}
