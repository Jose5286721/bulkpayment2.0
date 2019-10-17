package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Smslogmessage;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface SmslogmessageManagerLocal {
	
	public void insertSmslogmessage(Smslogmessage smslogmessage);

}
