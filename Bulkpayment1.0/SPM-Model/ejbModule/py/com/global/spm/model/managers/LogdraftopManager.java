package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logdraftop;
import py.com.global.spm.model.interfaces.LogdraftopManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class LogdraftopManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class LogdraftopManager implements LogdraftopManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(LogdraftopManager.class);

	/**
	 * Default constructor.
	 */
	public LogdraftopManager() {
	}

	@Override
	public void insertLogdraftop(Logdraftop logdraftop) {
		log.trace("Persisting --> " + logdraftop);
		try {
			em.persist(logdraftop);
		} catch (Exception e) {
			log.error("Creating logdraftop --> " + logdraftop, e);
		}
	}

}
