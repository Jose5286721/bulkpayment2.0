package py.com.global.spm.model.interfaces;
import javax.ejb.Local;

import py.com.global.spm.entities.Logreversion;



/**
 * 
 * @author R2
 *
 */
@Local
public interface LogreversionManagerLocal {
	
	public void updateLogreversion(Logreversion logMts);

}
