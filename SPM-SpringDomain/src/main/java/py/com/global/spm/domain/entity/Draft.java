package py.com.global.spm.domain.entity;

// Generated 15/07/2013 09:59:46 AM by Hibernate Tools 3.4.0.CR1

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import py.com.global.spm.domain.utils.ScopeViews;
import py.com.global.spm.domain.audit.EntityAuditEventListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Draft generated by hbm2java
 */
@Entity
@Table(name = "DRAFT" )
@EntityListeners(EntityAuditEventListener.class)
public class Draft implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonView({ScopeViews.Basics.class})
	private Long iddraftPk;
	@JsonView({ScopeViews.Details.class})
	private User user;
	@JsonView({ScopeViews.Basics.class})
	private Workflow workflow;
	@JsonView({ScopeViews.Basics.class})
	private Company company;
	@JsonView({ScopeViews.Basics.class})
	private Paymentordertype paymentordertype;
	@JsonView({ScopeViews.Basics.class})
	private String stateChr;
	@JsonView({ScopeViews.Basics.class})
	private String descriptionChr;
	@JsonView({ScopeViews.Basics.class})
	private Date paiddateTim;
	@JsonView({ScopeViews.Basics.class})
	private Date creationdateTim;
	@JsonView({ScopeViews.Basics.class})
	private Date lastGenerationDate;
	@JsonView({ScopeViews.Basics.class})
	private boolean recurrentNum;
	@JsonView({ScopeViews.Basics.class})
	private Byte generatedayNum;
	@JsonView({ScopeViews.Basics.class})
	private boolean fromtemplateNum;
	@JsonView({ScopeViews.NoBasics.class})
	private Blob template;
	@JsonView({ScopeViews.NoBasics.class})
	private String templatenameChr;
	@JsonView({ScopeViews.NoBasics.class})
	private String mimetypeChr;
	@JsonView({ScopeViews.Basics.class})
	private Date fromdateTim;
	@JsonView({ScopeViews.Basics.class})
	private Date todateTim;
	@JsonView({ScopeViews.Basics.class})
	private boolean notifybenficiaryNum;
	@JsonView({ScopeViews.Basics.class})
	private boolean notifysignerNum;
	@JsonView({ScopeViews.Basics.class})
	private boolean notifygenNum;
	@JsonView({ScopeViews.Basics.class})
	private boolean notifycancelNum;
	@JsonView({ScopeViews.NoBasics.class})
	private Set<Draftdetail> draftdetails = new HashSet<Draftdetail>(0);
	@JsonView({ScopeViews.NoBasics.class})
	private Set<Paymentorder> paymentorders = new HashSet<Paymentorder>(0);
	@JsonView({ScopeViews.NoBasics.class})
	private Set<Logdraftop> logdraftops = new HashSet<Logdraftop>(0);

	public Draft() {
	}

	public Draft(Long iddraftPk, Company company,
			Paymentordertype paymentordertype, String stateChr,
			Date paiddateTim, Date creationdateTim,
			boolean recurrentNum, boolean fromtemplateNum,
			boolean notifybenficiaryNum, boolean notifysignerNum,
			boolean notifygenNum, boolean notifycancelNum) {
		this.iddraftPk = iddraftPk;
		this.company = company;
		this.paymentordertype = paymentordertype;
		this.stateChr = stateChr;
		this.paiddateTim = paiddateTim;
		this.creationdateTim = creationdateTim;
		this.recurrentNum = recurrentNum;
		this.fromtemplateNum = fromtemplateNum;
		this.notifybenficiaryNum = notifybenficiaryNum;
		this.notifysignerNum = notifysignerNum;
		this.notifygenNum = notifygenNum;
		this.notifycancelNum = notifycancelNum;
	}

	public Draft(Long iddraftPk, User user, Workflow workflow,
			Company company, Paymentordertype paymentordertype, String stateChr,
			String descriptionChr, Date paiddateTim,
			Date creationdateTim, boolean recurrentNum,
			Byte generatedayNum, boolean fromtemplateNum, Blob template,
			String templatenameChr, String mimetypeChr,
			boolean notifybenficiaryNum, boolean notifysignerNum,
			boolean notifygenNum, boolean notifycancelNum,
			Set<Draftdetail> draftdetails, Set<Paymentorder> paymentorders,
			Set<Logdraftop> logdraftops) {
		this.iddraftPk = iddraftPk;
		this.user = user;
		this.workflow = workflow;
		this.company = company;
		this.paymentordertype = paymentordertype;
		this.stateChr = stateChr;
		this.descriptionChr = descriptionChr;
		this.paiddateTim = paiddateTim;
		this.creationdateTim = creationdateTim;
		this.recurrentNum = recurrentNum;
		this.generatedayNum = generatedayNum;
		this.fromtemplateNum = fromtemplateNum;
		this.template = template;
		this.templatenameChr = templatenameChr;
		this.mimetypeChr = mimetypeChr;
		this.notifybenficiaryNum = notifybenficiaryNum;
		this.notifysignerNum = notifysignerNum;
		this.notifygenNum = notifygenNum;
		this.notifycancelNum = notifycancelNum;
		this.draftdetails = draftdetails;
		this.paymentorders = paymentorders;
		this.logdraftops = logdraftops;
	}

	@Id
	@Column(name = "IDDRAFT_PK", unique = true, nullable = false, precision = 22, scale = 0)
	@NotNull
	@SequenceGenerator(name="DRAFT_SEQ_GEN",sequenceName="DRAFT_SEQ",allocationSize=1)
	@GeneratedValue(generator="DRAFT_SEQ_GEN")
	public Long getIddraftPk() {
		return this.iddraftPk;
	}

	public void setIddraftPk(Long iddraftPk) {
		this.iddraftPk = iddraftPk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDUSER_PK")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDWORKFLOW_PK")
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

	@Column(name = "PAIDDATE_TIM")
	public Date getPaiddateTim() {
		return this.paiddateTim;
	}

	public void setPaiddateTim(Date paiddateTim) {
		this.paiddateTim = paiddateTim;
	}

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	@NotNull
	public Date getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Date creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	@Column(name = "LASTGENERATIONDATE_TIM")
	public Date getLastGenerationDate() {
		return lastGenerationDate;
	}

	public void setLastGenerationDate(Date lastGenerationDate) {
		this.lastGenerationDate = lastGenerationDate;
	}

	@Column(name = "RECURRENT_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isRecurrentNum() {
		return this.recurrentNum;
	}

	public void setRecurrentNum(boolean recurrentNum) {
		this.recurrentNum = recurrentNum;
	}

	@Column(name = "GENERATEDAY_NUM", precision = 2, scale = 0)
	public Byte getGeneratedayNum() {
		return this.generatedayNum;
	}

	public void setGeneratedayNum(Byte generatedayNum) {
		this.generatedayNum = generatedayNum;
	}

	@Column(name = "FROMTEMPLATE_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isFromtemplateNum() {
		return this.fromtemplateNum;
	}

	public void setFromtemplateNum(boolean fromtemplateNum) {
		this.fromtemplateNum = fromtemplateNum;
	}

	@Column(name = "TEMPLATE")
	public Blob getTemplate() {
		return this.template;
	}

	public void setTemplate(Blob template) {
		this.template = template;
	}

	@Column(name = "TEMPLATENAME_CHR")
	public String getTemplatenameChr() {
		return this.templatenameChr;
	}

	public void setTemplatenameChr(String templatenameChr) {
		this.templatenameChr = templatenameChr;
	}

	@Column(name = "MIMETYPE_CHR", length = 50)
	@Length(max = 50)
	public String getMimetypeChr() {
		return this.mimetypeChr;
	}

	public void setMimetypeChr(String mimetypeChr) {
		this.mimetypeChr = mimetypeChr;
	}

	@Column(name = "NOTIFYBENFICIARY_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isNotifybenficiaryNum() {
		return this.notifybenficiaryNum;
	}

	public void setNotifybenficiaryNum(boolean notifybenficiaryNum) {
		this.notifybenficiaryNum = notifybenficiaryNum;
	}

	@Column(name = "NOTIFYSIGNER_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isNotifysignerNum() {
		return this.notifysignerNum;
	}

	public void setNotifysignerNum(boolean notifysignerNum) {
		this.notifysignerNum = notifysignerNum;
	}

	@Column(name = "NOTIFYGEN_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isNotifygenNum() {
		return this.notifygenNum;
	}

	public void setNotifygenNum(boolean notifygenNum) {
		this.notifygenNum = notifygenNum;
	}

	@Column(name = "NOTIFYCANCEL_NUM", nullable = false, precision = 1, scale = 0)
	public boolean isNotifycancelNum() {
		return this.notifycancelNum;
	}

	public void setNotifycancelNum(boolean notifycancelNum) {
		this.notifycancelNum = notifycancelNum;
	}
	@Column(name = "FROMDATE_TIM")
	public Date getFromdateTim() {
		return fromdateTim;
	}

	public void setFromdateTim(Date fromdateTim) {
		this.fromdateTim = fromdateTim;
	}
	@Column(name = "TODATE_TIM")
	public Date getTodateTim() {
		return todateTim;
	}

	public void setTodateTim(Date todateTim) {
		this.todateTim = todateTim;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "draft")
	public Set<Draftdetail> getDraftdetails() {
		return this.draftdetails;
	}

	public void setDraftdetails(Set<Draftdetail> draftdetails) {
		this.draftdetails = draftdetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "draft")
	public Set<Paymentorder> getPaymentorders() {
		return this.paymentorders;
	}

	public void setPaymentorders(Set<Paymentorder> paymentorders) {
		this.paymentorders = paymentorders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "draft")
	public Set<Logdraftop> getLogdraftops() {
		return this.logdraftops;
	}

	public void setLogdraftops(Set<Logdraftop> logdraftops) {
		this.logdraftops = logdraftops;
	}

	@Override
	public String toString() {
		return "Draft{" +
				"iddraftPk=" + iddraftPk +
				", idUser=" + user.getIduserPk() +
				", idWorkflow=" + workflow.getIdworkflowPk() +
				", idCompany=" + company.getIdcompanyPk() +
				", idPaymentordertype=" + paymentordertype.getIdorderpaymenttypePk() +
				", stateChr=" + stateChr +
				", descriptionChr='" + descriptionChr + '\'' +
				", paiddateTim=" + paiddateTim +
				", creationdateTim=" + creationdateTim +
				", recurrentNum=" + recurrentNum +
				", generatedayNum=" + generatedayNum +
				", fromdateTim=" + fromdateTim +
				", todateTim=" + todateTim +
				", notifybenficiaryNum=" + notifybenficiaryNum +
				", notifysignerNum=" + notifysignerNum +
				", notifygenNum=" + notifygenNum +
				", notifycancelNum=" + notifycancelNum +
				'}';
	}
}
