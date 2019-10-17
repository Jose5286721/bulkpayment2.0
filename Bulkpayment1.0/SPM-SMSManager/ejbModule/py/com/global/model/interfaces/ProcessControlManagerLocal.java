package py.com.global.model.interfaces;

import py.com.global.spm.entities.Processcontrol;

import javax.ejb.Local;

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
