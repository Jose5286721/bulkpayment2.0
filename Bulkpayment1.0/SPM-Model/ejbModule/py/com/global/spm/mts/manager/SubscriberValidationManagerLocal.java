package py.com.global.spm.mts.manager;

import javax.ejb.Local;

@Local
public interface SubscriberValidationManagerLocal {
	public Integer getSucriberValitation(String ci, String msisdn);
}
