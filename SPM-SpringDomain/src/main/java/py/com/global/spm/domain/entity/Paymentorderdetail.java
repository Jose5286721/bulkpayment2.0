package py.com.global.spm.domain.entity;

// Generated 15/07/2013 09:59:46 AM by Hibernate Tools 3.4.0.CR1

import py.com.global.spm.domain.utils.EnumAppHelper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Paymentorderdetail generated by hbm2java
 */
@Entity
@Table(name = "PAYMENTORDERDETAIL")
public class Paymentorderdetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaymentorderdetailId id;
	private Beneficiary beneficiary;
	private Paymentorder paymentorder;
	private BigDecimal amountNum;
	private String amountChr;
	private Set<Logpayment> logpayments = new HashSet<Logpayment>(0);
	private Set<Logmtsrev> logmtsrevs = new HashSet<Logmtsrev>(0);
	private Set<Logmts> logmtses = new HashSet<Logmts>(0);

	private String state;

	public Paymentorderdetail() {
	}

	public Paymentorderdetail(PaymentorderdetailId id, Beneficiary beneficiary,
			Paymentorder paymentorder, BigDecimal amountNum) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.paymentorder = paymentorder;
		this.amountNum = amountNum;
	}

	public Paymentorderdetail(PaymentorderdetailId id, Beneficiary beneficiary,
			Paymentorder paymentorder, BigDecimal amountNum,
			Set<Logpayment> logpayments, Set<Logmtsrev> logmtsrevs,
			Set<Logmts> logmtses) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.paymentorder = paymentorder;
		this.amountNum = amountNum;
		this.logpayments = logpayments;
		this.logmtsrevs = logmtsrevs;
		this.logmtses = logmtses;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idpaymentorderPk", column = @Column(name = "IDPAYMENTORDER_PK", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idbeneficiaryPk", column = @Column(name = "IDBENEFICIARY_PK", nullable = false, precision = 22, scale = 0)) })
	@NotNull
	public PaymentorderdetailId getId() {
		return this.id;
	}

	public void setId(PaymentorderdetailId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDBENEFICIARY_PK", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Beneficiary getBeneficiary() {
		return this.beneficiary;
	}

	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPAYMENTORDER_PK", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Paymentorder getPaymentorder() {
		return this.paymentorder;
	}

	public void setPaymentorder(Paymentorder paymentorder) {
		this.paymentorder = paymentorder;
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
		return new BigDecimal(this.amountChr);
	}

	public void setAmountNum(BigDecimal amountNum) {
		this.amountChr=amountNum.toPlainString();
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorderdetail")
	public Set<Logpayment> getLogpayments() {
		return this.logpayments;
	}

	public void setLogpayments(Set<Logpayment> logpayments) {
		this.logpayments = logpayments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorderdetail")
	public Set<Logmtsrev> getLogmtsrevs() {
		return this.logmtsrevs;
	}

	public void setLogmtsrevs(Set<Logmtsrev> logmtsrevs) {
		this.logmtsrevs = logmtsrevs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentorderdetail")
	public Set<Logmts> getLogmtses() {
		return this.logmtses;
	}

	public void setLogmtses(Set<Logmts> logmtses) {
		this.logmtses = logmtses;
	}

	@Transient
	public Logpayment getLogPayment() {
		if (logpayments == null || logpayments.isEmpty()) {
			return null;
		}
		List<Logpayment> l = new ArrayList<Logpayment>(logpayments);
		return l.get(0);
	}

	@Transient
	public String getState() {
		if (state != null) {
			return state;
		}
		if (getLogPayment() == null) {
			return null;
		}
		try {
			state = EnumAppHelper.valueOfEnum("EstadosPayment", "getCodigo0",
					getLogPayment().getStateChr().toString());
			return state;
		} catch (Exception e) {
			System.out.println("getState() - Transient- " + e);
		}
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toStringAtGlance() {
		return "idpaymentorderPk=" + id.getIdpaymentorderPk()
				+ ", idbeneficiaryPk=" + id.getIdbeneficiaryPk()
				+ ", amountNum=" + getAmountNum();
	}
}
