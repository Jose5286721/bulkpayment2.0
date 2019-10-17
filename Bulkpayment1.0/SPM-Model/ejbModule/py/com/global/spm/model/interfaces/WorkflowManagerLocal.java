package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Workflow;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface WorkflowManagerLocal {
	
	public Workflow getWorkflowById(long idworkflowPk);

}
