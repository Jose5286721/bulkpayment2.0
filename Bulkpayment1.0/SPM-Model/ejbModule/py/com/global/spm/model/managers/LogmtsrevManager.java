package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logmtsrev;
import py.com.global.spm.model.interfaces.LogmtsrevManagerLocal;
import py.com.global.spm.model.util.SpmConstants;




/**
 * Maneja operaciones basicas de base de datos sobre el entity Logmts.
 * 
 * @author R2
 */
@Stateless
public class LogmtsrevManager implements LogmtsrevManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(LogmtsrevManager.class);

	/**
	 * Default constructor.
	 */
	public LogmtsrevManager() {
	}

	public void insertLogmtsrev(Logmtsrev logMts) {
		if (logMts == null) {
			log.error("logMtsRev can't be null");
			return;
		}
		log.trace("Inserting logMtsRev --> " + logMts);
		try {
			em.persist(logMts);
			em.flush();
		} catch (Exception e) {
			log.error("Inserting logMtsRev --> " + logMts, e);
		}
	}

}
