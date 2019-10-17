package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Workflow;
import py.com.global.spm.model.interfaces.WorkflowManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class WorkflowManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class WorkflowManager implements WorkflowManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private final Logger log = Logger.getLogger(WorkflowManager.class);

	/**
	 * Default constructor.
	 */
	public WorkflowManager() {
	}

	@Override
	public Workflow getWorkflowById(long idworkflowPk) {
		log.trace("Finding workflow by id --> idworkflowPk=" + idworkflowPk);
		Workflow workflow = null;
		try {
			workflow = em.find(Workflow.class, idworkflowPk);
		} catch (Exception e) {
			log.error(
					"Finding workflow by id --> idworkflowPk=" + idworkflowPk,
					e);
		}
		return workflow;
	}

}
