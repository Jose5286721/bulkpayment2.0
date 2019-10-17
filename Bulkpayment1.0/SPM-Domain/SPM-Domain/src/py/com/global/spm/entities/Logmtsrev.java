package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import py.com.global.spm.domain.utils.CryptoUtils;

import java.sql.Timestamp;


/**
 * The persistent class for the LOGMTSREV database table.
 * 
 */
@Entity
@Table(name="LOGMTSREV")
public class Logmtsrev implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOGMTSREV_IDLOGMTSREVPK_GENERATOR", sequenceName="LOGMTSREV_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOGMTSREV_IDLOGMTSREVPK_GENERATOR")
	@Column(name="IDLOGMTSREV_PK", unique=true, nullable=false, length=20)
	private Long idlogmtsrevPk;

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

	@Column(name="IDBENEFICIARY_PK", nullable=false, precision=22)
	private long idbeneficiaryPk;

	@Column(name="IDCOMPANY_PK", nullable=false, precision=22)
	private long idcompanyPk;

	@Column(name="IDEVENTCODE_NUM", nullable=false, precision=5)
	private long ideventcodeNum;

	@Column(name="IDLOGMTS_PK", nullable=false, precision=22)
	private long idlogmtsPk;

	@Column(name="IDPAYMENT_NUM", nullable=false, precision=22)
	private long idpaymentNum;

	@Column(name="IDPAYMENTORDER_PK", nullable=false, precision=22)
	private long idpaymentorderPk;

	@Column(name="IDPROCESS_PK", nullable=false, precision=4)
	private long idprocessPk;

	@Column(name="IDTRXMTS_CHR", length=20)
	private String idtrxmtsChr;

	@Column(name="METHOD_CHR", nullable=false, length=40)
	private String methodChr;

	@Column(name="REQUEST_CHR", nullable=false, length=400)
	private String requestChr;

	@Column(name="REQUESTDATE_TIM", nullable=false)
	private Timestamp requestdateTim;
	
	@Column(name="RESPONSE_CHR", length=400)
	private String responseChr;

	@Column(name="RESPONSEDATE_TIM")
	private Timestamp responsedateTim;

	@Column(name="STATE_CHR", nullable=false, length=1)
	private String stateChr;

	@Column(name="TRXTYPE_CHR", nullable=false, length=30)
	private String trxtypeChr;
	
	@Column(name="IDSESSIONMTS_CHR", nullable=false, length=50)
	private String idsessionmtsChr;

	public Logmtsrev() {
	}

	public Long getIdlogmtsrevPk() {
		return this.idlogmtsrevPk;
	}

	public void setIdlogmtsrevPk(Long idlogmtsrevPk) {
		this.idlogmtsrevPk = idlogmtsrevPk;
	}

	public Double getAmountNum() {
		CryptoUtils cu = new CryptoUtils();
		
		return cu.desencriptarMonto(this.getAmountChr());
	}

	public void setAmountNum(Double amountNum) {
		CryptoUtils cu = new CryptoUtils();
		this.amountChr = cu.encriptarMonto(amountNum);
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

	public long getIdlogmtsPk() {
		return this.idlogmtsPk;
	}

	public void setIdlogmtsPk(long idlogmtsPk) {
		this.idlogmtsPk = idlogmtsPk;
	}

	public long getIdpaymentNum() {
		return this.idpaymentNum;
	}

	public void setIdpaymentNum(long idpaymentNum) {
		this.idpaymentNum = idpaymentNum;
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

	public String getIdtrxmtsChr() {
		return this.idtrxmtsChr;
	}

	public void setIdtrxmtsChr(String idtrxmtsChr) {
		this.idtrxmtsChr = idtrxmtsChr;
	}

	public String getMethodChr() {
		return this.methodChr;
	}

	public void setMethodChr(String methodChr) {
		this.methodChr = methodChr;
	}

	public String getRequestChr() {
		return this.requestChr;
	}

	public void setRequestChr(String requestChr) {
		this.requestChr = requestChr;
	}

	public Timestamp getRequestdateTim() {
		return this.requestdateTim;
	}

	public void setRequestdateTim(Timestamp requestdateTim) {
		this.requestdateTim = requestdateTim;
	}

	public String getResponseChr() {
		return this.responseChr;
	}

	public void setResponseChr(String responseTim) {
		this.responseChr = responseTim;
	}

	public Timestamp getResponsedateTim() {
		return this.responsedateTim;
	}

	public void setResponsedateTim(Timestamp responsedateTim) {
		this.responsedateTim = responsedateTim;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public String getTrxtypeChr() {
		return this.trxtypeChr;
	}

	public void setTrxtypeChr(String trxtypeChr) {
		this.trxtypeChr = trxtypeChr;
	}

	public String getIdsessionmtsChr() {
		return idsessionmtsChr;
	}

	public void setIdsessionmtsChr(String idsessionmtsChr) {
		this.idsessionmtsChr = idsessionmtsChr;
	}

	@Override
	public String toString() {
		return "Logmtsrev [idlogmtsrevPk=" + idlogmtsrevPk + ", amountNum="
				+ getAmountNum() + ", idbeneficiaryPk=" + idbeneficiaryPk
				+ ", idcompanyPk=" + idcompanyPk + ", ideventcodeNum="
				+ ideventcodeNum + ", idlogmtsPk=" + idlogmtsPk
				+ ", idpaymentNum=" + idpaymentNum + ", idpaymentorderPk="
				+ idpaymentorderPk + ", idprocessPk=" + idprocessPk
				+ ", idtrxmtsChr=" + idtrxmtsChr + ", methodChr=" + methodChr
				+ ", requestChr=" + requestChr + ", requestdateTim="
				+ requestdateTim + ", responseChr=" + responseChr
				+ ", responsedateTim=" + responsedateTim + ", stateChr="
				+ stateChr + ", trxtypeChr=" + trxtypeChr
				+ ", idsessionmtsChr=" + idsessionmtsChr + "]";
	}

	
}
