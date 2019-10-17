package py.com.global.spm.model.interfaces;
import javax.ejb.Local;

/**
 *  
 * @author Lino Chamorro
 *
 */
@Local
public interface SequenceManagerLocal {
	
	public static final String PAYMENTORDER_SEQ = "PAYMENTORDER_SEQ";
	
	 public Long next(String sequenceName);
	 
}
