package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.messages.FlowManagerToTransferProcessMessage;
import py.com.global.spm.model.messages.SMSMangerToFlowManagerMessage;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface FlowManagerLocal {

	public void generateApprovalFlow(Paymentorder paymentorder);

	public Long notifyDraftCreator(Paymentorder paymentorder,
			FlowManagerEventCodes event);

	public void notifyAllSigners(Paymentorder paymentorder,
			FlowManagerEventCodes event, Long idDraftcreator);

	public void notifyNextSigner(Paymentorder paymentorder);

	public void notifyNextSigner(Paymentorder paymentorder, long idlastapproval);

	public void notifySigner(Paymentorder paymentorder, long iduser,
			FlowManagerEventCodes event);

	public boolean updatePaymentorderState(Paymentorder paymentorder,
			String state);

	public boolean isLastSigner(Paymentorder paymentorder);

	public FlowManagerEventCodes approveBySMS(Paymentorder paymentorder,
			SMSMangerToFlowManagerMessage request);

	public FlowManagerToTransferProcessMessage getFlowManagerToTransferProcessMessage(
			Paymentorder paymentorder);

	public void notifySignEvent(Paymentorder paymentorder, long iduser);
	
	public void notifySignIncorrectPinError(Paymentorder paymentorder, long iduser);

}
