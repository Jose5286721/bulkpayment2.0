package py.com.global.spm.model.interfaces;


import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Draftdetail;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface DraftManagerLocal {

	public Draft getDraftById(long iddraft);
	
	public boolean updateDraft(Draft draft);
	
	public List<Draftdetail> getDraftDetail(long iddraft);

	public List<Draft> getProgramedPaymentorder(Timestamp paiddate);

	public List<Draft> getRecurrentPaymentorder(int generateDay, Timestamp now);

}
