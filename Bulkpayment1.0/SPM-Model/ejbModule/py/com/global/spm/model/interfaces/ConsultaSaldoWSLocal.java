package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface ConsultaSaldoWSLocal {

	public Double consultaSaldo(String code, long idCompany);

}
