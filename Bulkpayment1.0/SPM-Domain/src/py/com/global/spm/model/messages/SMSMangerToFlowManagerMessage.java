package py.com.global.spm.model.messages;


import py.com.global.spm.model.eventcodes.SMSManagerEventCodes;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SMSMangerToFlowManagerMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -322737824767213274L;

	private long idpaymentorder;
	private long iduser;
	private Long idcompanyPk;
	private String pin;
	private Date receiptDate;
	private SMSManagerEventCodes event;

	public long getIdpaymentorder() {
		return idpaymentorder;
	}

	public void setIdpaymentorder(long idpaymentorder) {
		this.idpaymentorder = idpaymentorder;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public long getIduser() {
		return iduser;
	}

	public void setIduser(long iduser) {
		this.iduser = iduser;
	}

	public SMSManagerEventCodes getEvent() {
		return event;
	}

	public void setEvent(SMSManagerEventCodes event) {
		this.event = event;
	}

	public Long getIdcompanyPk() {
		return idcompanyPk;
	}

	public void setIdcompanyPk(Long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	@Override
	public String toString() {
		return "SMSMangerToFlowManagerMessage [idpaymentorder="
				+ idpaymentorder + ", iduser=" + iduser + ", idcompanyPk="
				+ idcompanyPk + ", pin=" + pin + ", receiptDate=" + receiptDate
				+ ", event=" + event + "]";
	}

}
