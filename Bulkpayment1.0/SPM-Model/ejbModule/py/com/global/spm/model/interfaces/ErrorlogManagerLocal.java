package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Errorlog;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface ErrorlogManagerLocal {

	public boolean insertErrorLog(Errorlog errorlog);
	
	public boolean insertErrorlog(String processName, String description);

}
