package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import py.com.global.spm.domain.utils.CryptoUtils;

import java.sql.Timestamp;

/**
 * The persistent class for the LOGPAYMENT database table.
 * 
 */
@Entity
@Table(name = "LOGPAYMENT")
public class Logpayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOGPAYMENT_IDPAYMENTNUM_GENERATOR", sequenceName = "LOGPAYMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGPAYMENT_IDPAYMENTNUM_GENERATOR")
	@Column(name = "IDPAYMENT_NUM", unique = true, nullable = false, precision = 22)
	private long idpaymentNum;

	@Transient
	private Double amountNum;
	
	@Column(name = "AMOUNT_CHR", nullable = false, length=48)
	private String amountChr;
	
	public String getAmountChr() {
		return this.amountChr;
	}

	public void setAmountChr(String amountChr) {
		this.amountChr = amountChr;
	}

	@Transient
	private Double amountpaidNum;
	
	@Column(name = "AMOUNTPAID_CHR", nullable = false, length=48)
	private String amountPaidChr;
	
	public String getAmountPaidChr() {
		return this.amountPaidChr;
	}

	public void setAmountPaidChr(String amountPaidChr) {
		this.amountPaidChr = amountPaidChr;
	}
	
	@Column(name = "SUBSCRIBERDOCNUM_CHR", length = 20)
	private String subscriberDocNumChr;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "IDBENEFICIARY_PK", nullable = false, precision = 22)
	private long idbeneficiaryPk;
	
	@Column(name = "PHONENUMBER_CHR", nullable = false, precision = 22)
	private String phonenumberChr;

	@Column(name = "IDCOMPANY_PK", nullable = false, precision = 22)
	private long idcompanyPk;

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	private long ideventcodeNum;

	@Column(name = "IDPAYMENTORDER_PK", nullable = false, precision = 22)
	private long idpaymentorderPk;

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	private long idprocessPk;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;
	
	@Column(name = "UPDATEDATE_TIM", nullable = false)
	private Timestamp updatedateTim;

	public Logpayment() {
	}

	public long getIdpaymentNum() {
		return this.idpaymentNum;
	}

	public void setIdpaymentNum(long idpaymentNum) {
		this.idpaymentNum = idpaymentNum;
	}

	public Double getAmountNum() {
		CryptoUtils cu = new CryptoUtils();
		
		return cu.desencriptarMonto(this.getAmountChr());
	}

	public void setAmountNum(Double amountNum) {
		CryptoUtils cu = new CryptoUtils();
		this.amountChr = cu.encriptarMonto(amountNum);
	}

	public Double getAmountpaidNum() {
		CryptoUtils cu = new CryptoUtils();
		
		return cu.desencriptarMonto(this.getAmountPaidChr());
	}

	public void setAmountpaidNum(Double amountpaidNum) {
		CryptoUtils cu = new CryptoUtils();
		this.amountPaidChr = cu.encriptarMonto(amountpaidNum);
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public long getIdbeneficiaryPk() {
		return this.idbeneficiaryPk;
	}

	public void setIdbeneficiaryPk(long idbeneficiaryPk) {
		this.idbeneficiaryPk = idbeneficiaryPk;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIdeventcodeNum() {
		return this.ideventcodeNum;
	}

	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIdprocessPk() {
		return this.idprocessPk;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}
	
	public String getSubscriberDocNumChr() {
		return subscriberDocNumChr;
	}

	public void setSubscriberDocNumChr(String subscriberDocNumChr) {
		this.subscriberDocNumChr = subscriberDocNumChr;
	}

	@Override
	public String toString() {
		return "Logpayment [idpaymentNum=" + idpaymentNum + ", amountNum="
				+ getAmountNum() + ", amountpaidNum=" + amountpaidNum
				+ ", creationdateTim=" + creationdateTim + ", idbeneficiaryPk="
				+ idbeneficiaryPk + ", idcompanyPk=" + idcompanyPk
				+ ", ideventcodeNum=" + ideventcodeNum + ", idpaymentorderPk="
				+ idpaymentorderPk + ", idprocessPk=" + idprocessPk
				+ ", subscriberDocNumChr=" + subscriberDocNumChr
				+ ", stateChr=" + stateChr + "]";
	}

	public void setPhonenumberChr(String phonenumberChr) {
		this.phonenumberChr = phonenumberChr;
	}

	public String getPhonenumberChr() {
		return phonenumberChr;
	}

	public Timestamp getUpdatedateTim() {
		return updatedateTim;
	}

	public void setUpdatedateTim(Timestamp updatedateTim) {
		this.updatedateTim = updatedateTim;
	}

}
