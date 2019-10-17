package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Logdraftop;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface LogdraftopManagerLocal {
	
	public void insertLogdraftop(Logdraftop logdraftop);

}
