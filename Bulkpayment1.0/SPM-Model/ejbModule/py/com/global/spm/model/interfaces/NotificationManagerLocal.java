package py.com.global.spm.model.interfaces;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface NotificationManagerLocal {
	public void processRequest(Serializable requestMessage);

}
