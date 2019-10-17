package py.com.global.spm.model.test;

import javax.ejb.Local;

@Local
public interface mdbTestingLocal {

	public boolean sendReversion(Long idPaymentOrder);
}
