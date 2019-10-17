package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Logmessage;

@Local
public interface LogmessageManagerLocal {
	public Boolean insertLogmessage(Logmessage logMessage);

}
