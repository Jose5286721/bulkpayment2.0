package py.com.global.spm.model.managers;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logreversion;
import py.com.global.spm.model.interfaces.LogreversionManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Maneja operaciones basicas de base de datos sobre el entity Logmts.
 * 
 * @author R2
 */
@Stateless
public class LogreversionManager implements LogreversionManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(LogreversionManager.class);

	/**
	 * Default constructor.
	 */
	public LogreversionManager() {
	}

	public void updateLogreversion(Logreversion logReversion) {
		if (logReversion == null) {
			log.error("logReversion can't be null");
			return;
		}
		log.trace("Updating logReversion --> " + logReversion);
		try {
			logReversion.setCreationdateTim(new Timestamp(System
					.currentTimeMillis()));
			em.merge(logReversion);
			em.flush();
		} catch (Exception e) {
			log.error("Updating logReversion --> " + logReversion, e);
		}
	}

}
