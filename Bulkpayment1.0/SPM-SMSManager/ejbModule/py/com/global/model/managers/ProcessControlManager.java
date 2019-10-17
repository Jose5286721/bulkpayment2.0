package py.com.global.model.managers;

import org.apache.log4j.Logger;
import py.com.global.model.interfaces.ProcessControlManagerLocal;
import py.com.global.model.util.SpmConstants;
import py.com.global.spm.entities.Processcontrol;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class ProcessControlManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class ProcessControlManager implements ProcessControlManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(Processcontrol.class);

	/**
	 * Default constructor.
	 */
	public ProcessControlManager() {
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean createProcessControl(Processcontrol processControl) {
		try {
			em.merge(processControl);
			return true;
		} catch (Exception e) {
			log.error("Creating processControl --> " + processControl, e);
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean updateProcessControl(Processcontrol processControl) {
		try {
			em.merge(processControl);
			return true;
		} catch (Exception e) {
			log.error("Updating processControl --> " + processControl, e);
		}
		return false;
	}

}
