package py.com.global.spm.model.interfaces;
import javax.ejb.Local;

import py.com.global.spm.entities.Logmtsrev;



/**
 * 
 * @author R2
 *
 */
@Local
public interface LogmtsrevManagerLocal {
	
	public void insertLogmtsrev(Logmtsrev logMts);

}
