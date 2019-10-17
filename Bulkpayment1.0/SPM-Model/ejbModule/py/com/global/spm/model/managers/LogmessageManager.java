package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logmessage;
import py.com.global.spm.model.interfaces.LogmessageManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Maneja operaciones basicas de base de datos sobre el entity Logmessage.
 * 
 * @author Rodolfo
 */
@Stateless
public class LogmessageManager implements LogmessageManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(LogmessageManager.class);

	/**
	 * Default constructor.
	 */
	public LogmessageManager() {
	}
	
	@Override
	public Boolean insertLogmessage(Logmessage logMessage) {
		if (logMessage == null) {
			log.error("logMessage can't be null");
			return false;
		}
		//log.debug("Inserting logMessage --> " + logMessage);
		
		try {
			em.persist(logMessage);
			em.flush();
			return true;
		} catch (Exception e) {
			log.error("Inserting logMessage --> " + logMessage, e);
		}
		return false;
	}
}
