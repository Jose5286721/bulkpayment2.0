package py.com.global.spm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import py.com.global.spm.domain.utils.CryptoUtils;

/**
 * The persistent class for the PAYMENTORDER database table.
 * 
 */
@Entity
@Table(name = "PAYMENTORDER")
public class Paymentorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PAYMENTORDER_IDPAYMENTORDERPK_GENERATOR", sequenceName = "PAYMENTORDER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENTORDER_IDPAYMENTORDERPK_GENERATOR")
	@Column(name = "IDPAYMENTORDER_PK", unique = true, nullable = false, precision = 22)
	private long idpaymentorderPk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESCRIPTION_CHR", length = 100)
	private String descriptionChr;

//	@Column(name = "XMLNAME_CHR", length = 100)
//	private String xmlnameChr;

//	@Column(name = "MIMETYPE_CHR", length = 30)
//	private String mimetypeChr;

	@Column(name = "IDCOMPANY_PK", nullable = false, precision = 22)
	private long idcompanyPk;

	@Column(name = "IDDRAFT_PK", nullable = false, precision = 22)
	private long iddraftPk;

	@Column(name = "IDPAYMENTORDERTYPE_PK", nullable = false, precision = 22)
	private long idpaymentordertypePk;

	@Column(name = "IDWORKFLOW_PK", nullable = false, precision = 22)
	private long idworkflowPk;

//	@Lob
//	@Basic(fetch = FetchType.LAZY)
//	@Column(nullable = true)
//	private byte[] pofile;

	@Column(name = "STATE_CHR", nullable = false, length = 2)
	private String stateChr;

	@Column(name = "UPDATEDATE_TIM", nullable = false)
	private Timestamp updatedateTim;

	@Column(name = "MOTIVE_CHR", length = 100)
	private String motiveChr;

	@Column(name = "NOTIFYBENFICIARY_NUM", nullable = false, precision = 1)
	private Boolean notifybenficiaryNum;

	@Column(name = "NOTIFYCANCEL_NUM", nullable = false, precision = 1)
	private Boolean notifycancelNum;

	@Column(name = "NOTIFYGEN_NUM", nullable = false, precision = 1)
	private Boolean notifygenNum;

	@Column(name = "NOTIFYSIGNER_NUM", nullable = false, precision = 1)
	private Boolean notifysignerNum;

	@Transient
	private Double amountNum;

	@Column(name = "AMOUNT_CHR", nullable = false, length = 48)
	private String amountChr;

	public String getAmountChr() {
		return this.amountChr;
	}

	public void setAmountChr(String amountChr) {
		this.amountChr = amountChr;
	}

	@Transient
	private Double amountpaidNum;

	@Column(name = "AMOUNTPAID_CHR", nullable = false, length = 48)
	private String amountPaidChr;

	public String getAmountPaidChr() {
		return this.amountPaidChr;
	}

	public void setAmountPaidChr(String str) {
		this.amountPaidChr = str;
	}

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	private long idprocessPk;

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	private long ideventcodeNum;

	// total procesado
	@Column(name = "TOTALPAYMENT_NUM", nullable = true, precision = 6)
	private Integer totalpaymentNum;

	// total cant pagos satisfactorio
	@Column(name = "PAYMENTSUCCESS_NUM", nullable = true, precision = 6)
	private Integer paymentsuccessNum;
	// total cant. pagos con errores
	@Column(name = "PAYMENTERROR_NUM", nullable = true, precision = 6)
	private Integer paymenterrorNum;
	// cant. pagos parciales
	@Column(name = "PAYMENTPARTSUC_NUM", nullable = true, precision = 6)
	private Integer paymentpartsucNum;
    @Column(name = "CANT_SUCCESS")
	private Integer cantSuccess = 0; // Cantidad de pagos exitosos.
	@Column(name = "CANT_TOTAL")
	private Integer cantTotal = -1; // Cantidad total de pagos.
	@Column(name = "CANT_PENDING")
	private Integer cantPending = -1; // Cantidad pagos pendientes.
	@Column(name = "IS_TIMEOUT")
	private Boolean isTimeout = false;
	@Column(name = "AMMOUNT_PAID")
	private BigDecimal amountPayOp;
	@Column(name = "RETRY")
	private Boolean isReintento;

	public String getMotiveChr() {
		return motiveChr;
	}

	public void setMotiveChr(String motiveChr) {
		this.motiveChr = motiveChr;
	}

	public Paymentorder() {
	}

	public Paymentorder(long idpaymentorderPk) {
		super();
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIddraftPk() {
		return this.iddraftPk;
	}

	public void setIddraftPk(long iddraftPk) {
		this.iddraftPk = iddraftPk;
	}

	public long getIdpaymentordertypePk() {
		return this.idpaymentordertypePk;
	}

	public void setIdpaymentordertypePk(long idpaymentordertypePk) {
		this.idpaymentordertypePk = idpaymentordertypePk;
	}

	public long getIdworkflowPk() {
		return this.idworkflowPk;
	}

	public void setIdworkflowPk(long idworkflowPk) {
		this.idworkflowPk = idworkflowPk;
	}

//	public byte[] getPofile() {
//		return this.pofile;
//	}
//
//	public void setPofile(byte[] pofile) {
//		this.pofile = pofile;
//	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public Timestamp getUpdatedateTim() {
		return this.updatedateTim;
	}

	public void setUpdatedateTim(Timestamp updatedateTim) {
		this.updatedateTim = updatedateTim;
	}

	public Boolean getNotifybenficiaryNum() {
		return notifybenficiaryNum;
	}

	public void setNotifybenficiaryNum(Boolean notifybenficiaryNum) {
		this.notifybenficiaryNum = notifybenficiaryNum;
	}

	public Boolean getNotifycancelNum() {
		return notifycancelNum;
	}

	public void setNotifycancelNum(Boolean notifycancelNum) {
		this.notifycancelNum = notifycancelNum;
	}

	public Boolean getNotifygenNum() {
		return notifygenNum;
	}

	public void setNotifygenNum(Boolean notifygenNum) {
		this.notifygenNum = notifygenNum;
	}

	public Boolean getNotifysignerNum() {
		return notifysignerNum;
	}

	public void setNotifysignerNum(Boolean notifysignerNum) {
		this.notifysignerNum = notifysignerNum;
	}

	@Override
	public String toString() {
		return "Paymentorder [idpaymentorderPk=" + idpaymentorderPk
				+ ", creationdateTim=" + creationdateTim + ", descriptionChr="
				+ descriptionChr + ", idcompanyPk=" + idcompanyPk
				+ ", iddraftPk=" + iddraftPk
				+ ", idpaymentordertypePk=" + idpaymentordertypePk
				+ ", idworkflowPk=" + idworkflowPk + ", stateChr=" + stateChr
				+ ", updatedateTim=" + updatedateTim + ", motiveChr="
				+ motiveChr + ", notifybenficiaryNum=" + notifybenficiaryNum
				+ ", notifycancelNum=" + notifycancelNum + ", notifygenNum="
				+ notifygenNum + ", notifysignerNum=" + notifysignerNum
				+ ", amountNum=" + getAmountNum() + ", amountpaidNum="
				+ getAmountpaidNum() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idpaymentorderPk ^ (idpaymentorderPk >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paymentorder other = (Paymentorder) obj;
		if (idpaymentorderPk != other.idpaymentorderPk)
			return false;
		return true;
	}

	public Double getAmountpaidNum() {
		//CryptoUtils cu = new CryptoUtils();
		//return cu.desencriptarMonto(this.getAmountPaidChr());

		return Double.valueOf(this.getAmountPaidChr());
	}

	public void setAmountpaidNum(Double amountPaidNum) {
		//CryptoUtils cu = new CryptoUtils();
		//this.amountPaidChr = cu.encriptarMonto(amountPaidNum);
		this.amountPaidChr = String.valueOf(amountPaidNum);
	}

	public Double getAmountNum() {
		//CryptoUtils cu = new CryptoUtils();
		//return cu.desencriptarMonto(this.getAmountChr());
		return Double.valueOf(this.getAmountChr());
	}

	public void setAmountNum(Double amountNum) {
		//CryptoUtils cu = new CryptoUtils();
		//this.amountChr = cu.encriptarMonto(amountNum);
		this.amountChr = String.valueOf(amountNum);
	}

//	public String getXmlnameChr() {
//		return xmlnameChr;
//	}
//
//	public void setXmlnameChr(String xmlnameChr) {
//		this.xmlnameChr = xmlnameChr;
//	}


	public long getIdprocessPk() {
		return idprocessPk;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public long getIdeventcodeNum() {
		return ideventcodeNum;
	}

	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public Integer getTotalpaymentNum() {
		return totalpaymentNum;
	}

	public void setTotalpaymentNum(Integer totalpaymentNum) {
		this.totalpaymentNum = totalpaymentNum;
	}

	public Integer getPaymentsuccessNum() {
		return paymentsuccessNum;
	}

	public void setPaymentsuccessNum(Integer paymentsuccessNum) {
		this.paymentsuccessNum = paymentsuccessNum;
	}

	public Integer getPaymenterrorNum() {
		return paymenterrorNum;
	}

	public void setPaymenterrorNum(Integer paymenterrorNum) {
		this.paymenterrorNum = paymenterrorNum;
	}

	public Integer getPaymentpartsucNum() {
		return paymentpartsucNum;
	}

	public void setPaymentpartsucNum(Integer paymentpartsucNum) {
		this.paymentpartsucNum = paymentpartsucNum;
	}

}
