package py.com.global.spm.model.interfaces;
import javax.ejb.Local;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface PrefixManagerLocal {
	
	public String prefixSubscriber(String idSubscriber);
}
