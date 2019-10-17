package py.com.global.spm.model.managers;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Eventcode;
import py.com.global.spm.model.interfaces.EventCodeManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class EventCodeManager
 */
@Stateless
public class EventCodeManager implements EventCodeManagerLocal {
	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(EventCodeManager.class);

    /**
     * Default constructor. 
     */
    public EventCodeManager() {
    }
    

	public Eventcode getEventcodeById(long idEventcode) {
		Eventcode eventCode = null;
		try {
			eventCode = em.find(Eventcode.class, idEventcode);
		} catch (Exception e) {
			log.error("Finding eventCode --> idEventcode= " + idEventcode, e);
			return null;
		}
		return eventCode;
	}

}
