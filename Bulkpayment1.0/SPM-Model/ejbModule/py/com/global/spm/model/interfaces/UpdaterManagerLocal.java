package py.com.global.spm.model.interfaces;

import java.sql.Timestamp;

import javax.ejb.Local;

import py.com.global.spm.entities.Logdraftop;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.domain.utils.NotificationEventEnum;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface UpdaterManagerLocal {

	public boolean updatePaymentorder(Paymentorder paymentOrder, boolean isReintento);

	public boolean updateCompany(long idcompany, long trxCount,
			Timestamp lastopTim);

	public void insertLogdraftop(Logdraftop logdraftop);

	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
													Logpayment logpayment, NotificationEventEnum event,
													Long destinatario);

	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
			NotificationEventEnum event, Long destinatario);

	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
			NotificationEventEnum event, Long[] destinatarios);

	public Long[] getSigners(Paymentorder paymentorder);
	
	public Long[] getSignersWithoutIdDraftCreator(Paymentorder paymentorder, Long idDraftcreator);

}
