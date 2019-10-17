package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * The persistent class for the DRAFT database table.
 * 
 */
@Entity
@Table(name = "DRAFT")
public class Draft implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="DRAFT_SEQ_GEN",sequenceName="DRAFT_SEQ",allocationSize=0)
	@GeneratedValue(generator="DRAFT_SEQ_GEN")
	@Column(name = "IDDRAFT_PK", unique = true, nullable = false, precision = 22)
	private long iddraftPk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESCRIPTION_CHR", length = 100)
	private String descriptionChr;

	@Column(name = "FROMTEMPLATE_NUM", nullable = false, precision = 1)
	private Boolean fromtemplateNum;

	@Column(name = "GENERATEDAY_NUM", precision = 2)
	private Integer generatedayNum;

	@Column(name = "IDCOMPANY_PK", nullable = false, precision = 22)
	private long idcompanyPk;

	@Column(name = "IDPAYMENTORDERTYPE_PK", nullable = false, precision = 22)
	private long idpaymentordertypePk;

	@Column(name = "IDUSER_PK", precision = 22)
	private long iduserPk;

	@Column(name = "IDWORKFLOW_PK", precision = 22)
	private long idworkflowPk;

	@Column(name = "MIMETYPE_CHR", length = 50)
	private String mimetypeChr;

	@Column(name = "NOTIFYBENFICIARY_NUM", nullable = false, precision = 1)
	private Boolean notifybenficiaryNum;

	@Column(name = "NOTIFYCANCEL_NUM", nullable = false, precision = 1)
	private Boolean notifycancelNum;

	@Column(name = "NOTIFYGEN_NUM", nullable = false, precision = 1)
	private Boolean notifygenNum;

	@Column(name = "NOTIFYSIGNER_NUM", nullable = false, precision = 1)
	private Boolean notifysignerNum;

	@Column(name = "PAIDDATE_TIM", nullable = true)
	private Timestamp paiddateTim;

	@Column(name = "RECURRENT_NUM", nullable = false, precision = 1)
	private Boolean recurrentNum;

	@Column(name = "FROMDATE_TIM", nullable = true)
	private Timestamp fromdateTim;

	@Column(name = "TODATE_TIM", nullable = true)
	private Timestamp todateTim;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Lob
	private byte[] template;

	@Column(name = "TEMPLATENAME_CHR", length = 255)
	private String templatenameChr;

	public Draft() {
	}

	public long getIddraftPk() {
		return this.iddraftPk;
	}

	public void setIddraftPk(long iddraftPk) {
		this.iddraftPk = iddraftPk;
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

	public Boolean getFromtemplateNum() {
		return this.fromtemplateNum;
	}

	public void setFromtemplateNum(Boolean fromtemplateNum) {
		this.fromtemplateNum = fromtemplateNum;
	}

	public Integer getGeneratedayNum() {
		return this.generatedayNum;
	}

	public void setGeneratedayNum(Integer generatedayNum) {
		this.generatedayNum = generatedayNum;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIdpaymentordertypePk() {
		return this.idpaymentordertypePk;
	}

	public void setIdpaymentordertypePk(long idorderpaymenttypePk) {
		this.idpaymentordertypePk = idorderpaymenttypePk;
	}

	public long getIduserPk() {
		return this.iduserPk;
	}

	public void setIduserPk(long iduserPk) {
		this.iduserPk = iduserPk;
	}

	public long getIdworkflowPk() {
		return this.idworkflowPk;
	}

	public void setIdworkflowPk(long idworkflowPk) {
		this.idworkflowPk = idworkflowPk;
	}

	public String getMimetypeChr() {
		return this.mimetypeChr;
	}

	public void setMimetypeChr(String mimetypeChr) {
		this.mimetypeChr = mimetypeChr;
	}

	public Boolean getNotifybenficiaryNum() {
		return this.notifybenficiaryNum;
	}

	public void setNotifybenficiaryNum(Boolean notifybenficiaryNum) {
		this.notifybenficiaryNum = notifybenficiaryNum;
	}

	public Boolean getNotifycancelNum() {
		return this.notifycancelNum;
	}

	public void setNotifycancelNum(Boolean notifycancelNum) {
		this.notifycancelNum = notifycancelNum;
	}

	public Boolean getNotifygenNum() {
		return this.notifygenNum;
	}

	public void setNotifygenNum(Boolean notifygenNum) {
		this.notifygenNum = notifygenNum;
	}

	public Boolean getNotifysignerNum() {
		return this.notifysignerNum;
	}

	public void setNotifysignerNum(Boolean notifysignerNum) {
		this.notifysignerNum = notifysignerNum;
	}

	public Timestamp getPaiddateTim() {
		return this.paiddateTim;
	}

	public void setPaiddateTim(Timestamp paiddateTim) {
		this.paiddateTim = paiddateTim;
	}

	public Boolean getRecurrentNum() {
		return this.recurrentNum;
	}

	public void setRecurrentNum(Boolean recurrentNum) {
		this.recurrentNum = recurrentNum;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public byte[] getTemplate() {
		return this.template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

	public String getTemplatenameChr() {
		return this.templatenameChr;
	}

	public void setTemplatenameChr(String templatenameChr) {
		this.templatenameChr = templatenameChr;
	}

	public Timestamp getFromdateTim() {
		return fromdateTim;
	}

	public void setFromdateTim(Timestamp fromdateTim) {
		this.fromdateTim = fromdateTim;
	}

	public Timestamp getTodateTim() {
		return todateTim;
	}

	public void setTodateTim(Timestamp todateTim) {
		this.todateTim = todateTim;
	}

	@Override
	public String toString() {
		return "Draft [iddraftPk=" + iddraftPk + ", creationdateTim="
				+ creationdateTim + ", descriptionChr=" + descriptionChr
				+ ", fromtemplateNum=" + fromtemplateNum + ", generatedayNum="
				+ generatedayNum + ", idcompanyPk=" + idcompanyPk
				+ ", idpaymentordertypePk=" + idpaymentordertypePk
				+ ", iduserPk=" + iduserPk + ", idworkflowPk=" + idworkflowPk
				+ ", mimetypeChr=" + mimetypeChr + ", notifybenficiaryNum="
				+ notifybenficiaryNum + ", notifycancelNum=" + notifycancelNum
				+ ", notifygenNum=" + notifygenNum + ", notifysignerNum="
				+ notifysignerNum + ", paiddateTim=" + paiddateTim
				+ ", recurrentNum=" + recurrentNum + ", fromdateTim="
				+ fromdateTim + ", todateTim=" + todateTim + ", stateChr="
				+ stateChr + ", template=" + Arrays.toString(template)
				+ ", templatenameChr=" + templatenameChr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (iddraftPk ^ (iddraftPk >>> 32));
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
		Draft other = (Draft) obj;
		if (iddraftPk != other.iddraftPk)
			return false;
		return true;
	}

}