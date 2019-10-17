package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Processcontrol;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface ProcessControlManagerLocal {
	
	public boolean createProcessControl(Processcontrol processControl);
	
	public boolean updateProcessControl(Processcontrol processControl);

}
