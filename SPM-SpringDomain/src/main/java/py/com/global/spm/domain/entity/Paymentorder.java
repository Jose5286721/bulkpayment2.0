package py.com.global.spm.domain.entity;

// Generated 15/07/2013 09:59:46 AM by Hibernate Tools 3.4.0.CR1

import org.hibernate.validator.constraints.Length;
import py.com.global.spm.domain.utils.EnumAppHelper;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Paymentorder generated by hbm2java
 */
@Entity
@Table(name = "PAYMENTORDER")
public class Paymentorder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idpaymentorderPk;
	private Draft draft;
	private Workflow workflow;
	private Company company;
	private Paymentordertype paymentordertype;
	private String stateChr;
	private String descriptionChr;
	private Date creationdateTim;
	private Date updatedateTim;
//	private byte[] pofile;
	private String motiveChr;
//	private String xmlnameChr;
	private String mimetypeChr;
	// private BigDecimal amountNum;
	private String amountChr;
	// private BigDecimal amountpaidNum;
	private String amountpaidChr;
	private Long totalpaymentNum;
	private Long paymentsuccessNum;
	private Long paymenterrorNum;
	private Long paymentpartsucNum;


	private Boolean notifybenficiaryNum;
	private Boolean notifycancelNum;
	private Boolean notifygenNum;
	private Boolean notifysignerNum;

	private Long idprocessPk;
	private Long ideventcodeNum;

	private Integer cantSuccess = 0; // Cantidad de pagos exitosos.
	private Integer cantTotal = -1; // Cantidad total de pagos.
	private Integer cantPending = -1; // Cantidad pagos pendientes.
	private Boolean isTimeout;
	private BigDecimal amountPayOp = BigDecimal.ZERO;
	private Boolean isReintento;

	private Integer cantBeneficiaries = 0;

	private Integer retry = -1;
	private Set<Paymentorderdetail> paymentorderdetails = new HashSet<Paymentorderdetail>(
			0);
	private Set<Logdraftop> logdraftops = new HashSet<Logdraftop>(0);
	private Set<Approval> approvals = new HashSet<Approval>(0);

	public Paymentorder() {
	}

	public Paymentorder(Long idpaymentorderPk, Draft draft, Workflow workflow,
			Company company, Paymentordertype paymentordertype,
			String stateChr, Date creationdateTim, Date updatedateTim) {
		this.idpaymentorderPk = idpaymentorderPk;
		this.draft = draft;
		this.workflow = workflow;
		this.company = company;
		this.paymentordertype = paymentordertype;
		this.stateChr = stateChr;
		this.creationdateTim = creationdateTim;
		this.updatedateTim = updatedateTim;
	}

	public Paymentorder(Long idpaymentorderPk, Draft draft, Workflow workflow,
			Company company, Paymentordertype paymentordertype,
			String stateChr, String descriptionChr, Date creationdateTim,
			Date updatedateTim, String motiveChr,
			Set<Paymentorderdetail> paymentorderdetails,
			Set<Logdraftop> logdraftops, Set<Approval> approvals) {
		this.idpaymentorderPk = idpaymentorderPk;
		this.draft = draft;
		this.workflow = workflow;
		this.company = company;
		this.paymentordertype = paymentordertype;
		this.stateChr = stateChr;
		this.descriptionChr = descriptionChr;
		this.creationdateTim = creationdateTim;
		this.updatedateTim = updatedateTim;
		this.motiveChr = motiveChr;
		this.paymentorderdetails = paymentorderdetails;
		this.logdraftops = logdraftops;
		this.approvals = approvals;
	}

	@Id
	@SequenceGenerator(name = "PAYMENTORDER_IDPAYMENTORDERPK_GENERATOR", sequenceName = "PAYMENTORDER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENTORDER_IDPAYMENTORDERPK_GENERATOR")
	@Column(name = "IDPAYMENTORDER_PK", unique = true, nullable = false, precision = 22, scale = 0)
	@NotNull
	public Long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(Long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDDRAFT_PK", nullable = false)
	@NotNull
	public Draft getDraft() {
		return this.draft;
	}

	public void setDraft(Draft draft) {
		this.draft = draft;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDWORKFLOW_PK", nullable = false)
	@NotNull
	public Workflow getWorkflow() {
		return this.workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCOMPANY_PK", nullable = false)
	@NotNull
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPAYMENTORDERTYPE_PK", nullable = false)
	@NotNull
	public Paymentordertype getPaymentordertype() {
		return this.paymentordertype;
	}

	public void setPaymentordertype(Paymentordertype paymentordertype) {
		this.paymentordertype = paymentordertype;
	}

	@Column(name = "STATE_CHR", nullable = false, length = 2)
	@NotNull
	@Length(max = 2)
	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	@Column(name = "DESCRIPTION_CHR", length = 100)
	@Length(max = 100)
	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	@NotNull
	public Date getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Date creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	@Column(name = "UPDATEDATE_TIM", nullable = false)
	@NotNull
	public Date getUpdatedateTim() {
		return this.updatedateTim;
	}

	public void setUpdatedateTim(Date updatedateTim) {
		this.updatedateTim = updatedateTim;
	}

	@Column(name = "MOTIVE_CHR", length = 100)
	@Length(max = 100)
	public String getMotiveChr() {
		return this.motiveChr;
	}

	public void setMotiveChr(String motiveChr) {
		this.motiveChr = motiveChr;
	}

	@Column(name = "MIMETYPE_CHR", length = 30)
	public String getMimetypeChr() {
		return mimetypeChr;
	}

	public void setMimetypeChr(String mimetypeChr) {
		this.mimetypeChr = mimetypeChr;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorder")
	public Set<Paymentorderdetail> getPaymentorderdetails() {
		return this.paymentorderdetails;
	}

	public void setPaymentorderdetails(
			Set<Paymentorderdetail> paymentorderdetails) {
		this.paymentorderdetails = paymentorderdetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorder")
	public Set<Logdraftop> getLogdraftops() {
		return this.logdraftops;
	}

	public void setLogdraftops(Set<Logdraftop> logdraftops) {
		this.logdraftops = logdraftops;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorder")
	public Set<Approval> getApprovals() {
		return this.approvals;
	}

	public void setApprovals(Set<Approval> approvals) {
		this.approvals = approvals;
	}

	@Column(name = "AMOUNT_CHR", nullable = false, length = 48)
	@NotNull
	public String getAmountChr() {
		return this.amountChr;
	}

	public void setAmountChr(String amountChr) {
		this.amountChr = amountChr;
	}

	@Transient
	public BigDecimal getAmountNum() {
		return this.amountChr == null? null: new BigDecimal(this.amountChr);
	}

	public void setAmountNum(BigDecimal amountNum) {
		this.amountChr = String.valueOf(amountNum);
	}

	@Column(name = "AMOUNTPAID_CHR", nullable = false, length = 48)
	@NotNull
	public String getAmountpaidChr() {
		return this.amountpaidChr;
	}

	public void setAmountpaidChr(String amountpaidChr) {
		this.amountpaidChr = amountpaidChr;
	}

	@Transient
	public BigDecimal getAmountpaidNum() {
		return this.amountpaidChr == null? BigDecimal.ZERO: new BigDecimal(this.amountpaidChr);
	}

	public void setAmountpaidNum(BigDecimal amountpaidNum) {
		this.amountpaidChr = String.valueOf(amountpaidNum);
	}

	@Column(name = "TOTALPAYMENT_NUM", precision = 3, scale = 0)
	public Long getTotalpaymentNum() {
		return totalpaymentNum;
	}

	public void setTotalpaymentNum(Long totalpaymentNum) {
		this.totalpaymentNum = totalpaymentNum;
	}

	@Column(name = "PAYMENTSUCCESS_NUM", precision = 3, scale = 0)
	public Long getPaymentsuccessNum() {
		return paymentsuccessNum;
	}

	public void setPaymentsuccessNum(Long paymentsuccessNum) {
		this.paymentsuccessNum = paymentsuccessNum;
	}

	@Column(name = "PAYMENTERROR_NUM", precision = 3, scale = 0)
	public Long getPaymenterrorNum() {
		return paymenterrorNum;
	}

	public void setPaymenterrorNum(Long paymenterrorNum) {
		this.paymenterrorNum = paymenterrorNum;
	}

	@Column(name = "PAYMENTPARTSUC_NUM", precision = 3, scale = 0)
	public Long getPaymentpartsucNum() {
		return paymentpartsucNum;
	}

	public void setPaymentpartsucNum(Long paymentpartsucNum) {
		this.paymentpartsucNum = paymentpartsucNum;
	}

	@Column(name = "NOTIFYBENFICIARY_NUM", nullable = false, precision = 1)
	public Boolean getNotifybenficiaryNum() {
		return notifybenficiaryNum;
	}

	public void setNotifybenficiaryNum(Boolean notifybenficiaryNum) {
		this.notifybenficiaryNum = notifybenficiaryNum;
	}

	@Column(name = "NOTIFYCANCEL_NUM", nullable = false, precision = 1)
	public Boolean getNotifycancelNum() {
		return notifycancelNum;
	}

	public void setNotifycancelNum(Boolean notifycancelNum) {
		this.notifycancelNum = notifycancelNum;
	}

	@Column(name = "NOTIFYGEN_NUM", nullable = false, precision = 1)
	public Boolean getNotifygenNum() {
		return notifygenNum;
	}

	public void setNotifygenNum(Boolean notifygenNum) {
		this.notifygenNum = notifygenNum;
	}

	@Column(name = "NOTIFYSIGNER_NUM", nullable = false, precision = 1)
	public Boolean getNotifysignerNum() {
		return notifysignerNum;
	}

	public void setNotifysignerNum(Boolean notifysignerNum) {
		this.notifysignerNum = notifysignerNum;
	}

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	public Long getIdprocessPk() {
		return idprocessPk;
	}

	public void setIdprocessPk(Long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	public Long getIdeventcodeNum() {
		return ideventcodeNum;
	}

	public void setIdeventcodeNum(Long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	@Transient
	public String getStateDescription() {
		return EnumAppHelper.valueOfEnum("EstadosPaymentorder", "getCodigo0",
				getStateChr());
	}
	@Column(name = "CANT_SUCCESS_PAYMENT")
	public Integer getCantSuccess() {
		return cantSuccess;
	}

	public void setCantSuccess(Integer cantSuccess) {
		this.cantSuccess = cantSuccess;
	}
	@Column(name = "CANT_TOTAL_PAYMENT")
	public Integer getCantTotal() {
		return cantTotal;
	}

	public void setCantTotal(Integer cantTotal) {
		this.cantTotal = cantTotal;
	}
	@Column(name = "CANT_PENDING_PAYMENT")
	public Integer getCantPending() {
		return cantPending;
	}

	public void setCantPending(Integer cantPending) {
		this.cantPending = cantPending;
	}

	@Column(name = "CANT_BENEFICIARIES")
	public Integer getCantBeneficiaries() {
		return cantBeneficiaries;
	}

	public void setCantBeneficiaries(Integer cantBeneficiaries) {
		this.cantBeneficiaries = cantBeneficiaries;
	}

	@Column(name = "PAYMENT_TIMEOUT")
	public Boolean getTimeout() {
		return isTimeout;
	}

	public void setTimeout(Boolean timeout) {
		isTimeout = timeout;
	}

	@Column(name = "PAYMENT_AMMOUNT")
	public BigDecimal getAmountPayOp() {
		return amountPayOp;
	}

	public void setAmountPayOp(BigDecimal amountPayOp) {
		this.amountPayOp = amountPayOp;
	}
	@Column(name = "PAYMENT_RETRY")
	public Boolean getReintento() {
		return isReintento;
	}

	public void setReintento(Boolean reintento) {
		isReintento = reintento;
	}

	@Column(name = "RETRY")
	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Paymentorder{");
		sb.append("idpaymentorderPk=").append(idpaymentorderPk);
		sb.append(", idDraft=").append(draft.getIddraftPk());
		sb.append(", idWorkflow=").append(workflow.getIdworkflowPk());
		sb.append(", idCompany=").append(company.getIdcompanyPk());
		sb.append(", idPaymentordertype=").append(paymentordertype.getIdorderpaymenttypePk());
		sb.append(", stateChr='").append(stateChr).append('\'');
		sb.append(", descriptionChr='").append(descriptionChr).append('\'');
		sb.append(", amountChr='").append(amountChr).append('\'');
		sb.append(", amountpaidChr='").append(amountpaidChr).append('\'');
		sb.append(", totalpaymentNum=").append(totalpaymentNum);
		sb.append(", idprocessPk=").append(idprocessPk);
		sb.append(", ideventcodeNum=").append(ideventcodeNum);
		sb.append('}');
		return sb.toString();
	}
}