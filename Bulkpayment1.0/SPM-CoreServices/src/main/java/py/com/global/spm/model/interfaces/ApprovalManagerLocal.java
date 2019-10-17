package py.com.global.spm.model.interfaces;

import py.com.global.spm.entities.Approval;

import javax.ejb.Local;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 *
 */
@Local
public interface ApprovalManagerLocal {
	
	public List<Long> getIdSignersByIdPaymentorder(long idpaymentorder);
	
	public List<Approval> getApprovalsByIdPaymentorder(long idpaymentorder);
	
	public boolean insertApproval(List<Approval> approvalList);
	
	public boolean updateApproval(Approval approval);

}
