package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Company;
import py.com.global.spm.entities.Logdraftop;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.interfaces.ApprovalManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.LogdraftopManagerLocal;
import py.com.global.spm.model.interfaces.LogpaymentManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.UpdaterManagerLocal;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.domain.utils.NotificationEventEnum;
import py.com.global.spm.model.util.PaymentOrderStates;

/**
 * Session Bean implementation class UpdaterManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class UpdaterManager implements UpdaterManagerLocal {

	@EJB
	private PaymentOrderManagerLocal paymentOrderManager;
	@EJB
	private CompanyManagerLocal companyManager;
	@EJB
	private ApprovalManagerLocal approvalManager;
	@EJB
	private LogdraftopManagerLocal logdraftopManager;
	@EJB
	private LogpaymentManagerLocal logpaymentManager;

	private final Logger log = Logger.getLogger(UpdaterManager.class);

	/**
	 * Default constructor.
	 */
	public UpdaterManager() {

	}

	@Override
	public boolean updatePaymentorder(Paymentorder paymentorder,
			boolean isReintento) {
		log.debug("Updating payment order --> " + paymentorder);
		/*
		 * reintento de pago, la orden de pago antes del reintento tiene estado
		 * parcialmente pagada, el reintento no pudo pagar, la orden de pago se
		 * debe mantener como parcialmente pagada
		 */
		if (isReintento) {
			String state = null;
			if (paymentorder.getTotalpaymentNum() == paymentorder
					.getPaymentsuccessNum()) {
				state = PaymentOrderStates.PAGADA;
			} else if (paymentorder.getPaymentpartsucNum() > 0
					|| (paymentorder.getTotalpaymentNum() == (paymentorder
							.getPaymenterrorNum() + paymentorder
							.getPaymentsuccessNum()))) {
				state = PaymentOrderStates.PARCIALMENTE_PAGADA;
			} else if (paymentorder.getTotalpaymentNum() == paymentorder
					.getPaymenterrorNum()) {
				state = PaymentOrderStates.NO_PAGADA;
			}
			paymentorder.setStateChr(state);
		}
		return paymentOrderManager.updatePaymentorder(paymentorder);
	}

	@Override
	public boolean updateCompany(long idcompany, long trxCount,
			Timestamp lastopTim) {
		Company company = companyManager.getCompanyById(idcompany);
		company.setTrxdaycountNum(company.getTrxdaycountNum() + trxCount);
		company.setTrxmonthcountNum(company.getTrxmonthcountNum() + trxCount);
		company.setTrxtotalcountNum(company.getTrxtotalcountNum() + trxCount);
		company.setLastopdateTim(lastopTim);
		return companyManager.updateCompany(company);
	}

	@Override
	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
			Logpayment logpayment, NotificationEventEnum event,
			Long destinatario) {
		NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
		notificationRequest.setEvent(event);
		notificationRequest.setOp(paymentorder);
		notificationRequest.setPago(logpayment);
		Long[] d = { destinatario };
		notificationRequest.setIdDestinatarios(d);
		return notificationRequest;
	}

	@Override
	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
			NotificationEventEnum event, Long destinatario) {
		NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
		notificationRequest.setEvent(event);
		notificationRequest.setOp(paymentorder);
		Long[] d = { destinatario };
		notificationRequest.setIdDestinatarios(d);
		return notificationRequest;
	}

	@Override
	public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
			NotificationEventEnum event, Long[] destinatarios) {
		NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
		notificationRequest.setEvent(event);
		notificationRequest.setOp(paymentorder);
		notificationRequest.setIdDestinatarios(destinatarios);
		return notificationRequest;
	}

	@Override
	public Long[] getSigners(Paymentorder paymentorder) {
		List<Long> approvalList = approvalManager
				.getIdSignersByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		if (approvalList != null) {
			Long[] arraySegunArturo = new Long[0];
			return approvalList.toArray(arraySegunArturo);
		}
		return null;
	}

	@Override
	public void insertLogdraftop(Logdraftop logdraftop) {
		logdraftopManager.insertLogdraftop(logdraftop);
	}

	@Override
	public Long[] getSignersWithoutIdDraftCreator(Paymentorder paymentorder,
			Long idDraftcreator) {
		List<Long> approvalList = approvalManager
				.getIdSignersByIdPaymentorder(paymentorder
						.getIdpaymentorderPk());
		if (approvalList != null) {
			List<Long> alambre = new ArrayList<Long>();
			for (Long l : approvalList) {
				if (l.longValue() != idDraftcreator.longValue()) {
					alambre.add(l);
				}
			}
			Long[] arraySegunArturo = new Long[0];
			return alambre.toArray(arraySegunArturo);
		}
		return null;
	}

}
