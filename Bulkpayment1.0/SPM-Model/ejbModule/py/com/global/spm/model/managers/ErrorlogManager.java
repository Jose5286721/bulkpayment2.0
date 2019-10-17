package py.com.global.spm.model.managers;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Errorlog;
import py.com.global.spm.model.interfaces.ErrorlogManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class ErrorlogManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class ErrorlogManager implements ErrorlogManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(ErrorlogManager.class);

	/**
	 * Default constructor.
	 */
	public ErrorlogManager() {
	}

	@Override
	public boolean insertErrorLog(Errorlog errorlog) {
		log.debug("insert errorlog --> " + errorlog);
		try {
			if (errorlog.getCreationdateTim() == null) {
				errorlog.setCreationdateTim(new Timestamp(System
						.currentTimeMillis()));
			}
			em.persist(errorlog);
			return true;
		} catch (Exception e) {
			log.error("insertErrorLog --> " + errorlog, e);
		}
		return false;
	}

	@Override
	public boolean insertErrorlog(String processName, String description) {
		Errorlog errorlog = new Errorlog(processName, description);
		return this.insertErrorLog(errorlog);
	}

}
