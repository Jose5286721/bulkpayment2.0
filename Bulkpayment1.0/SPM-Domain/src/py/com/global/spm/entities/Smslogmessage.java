package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the SMSLOGMESSAGE database table.
 * 
 * @author Lino Chamorro
 */
@Entity
@Table(name = "SMSLOGMESSAGE")
public class Smslogmessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SMSLOGMESSAGE_IDMESSAGEPK_GENERATOR", sequenceName = "SMSLOGMESSAGE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SMSLOGMESSAGE_IDMESSAGEPK_GENERATOR")
	@Column(name = "IDMESSAGE_PK", unique = true, nullable = false)
	private long idmessagePk;

	@Column(name = "CHANGEDATE_TIM", nullable = false)
	private Timestamp changedateTim;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESTINYNUMBER_CHR", nullable = false, length = 20)
	private String destinynumberChr;

	@Column(name = "IDCOMPANY_PK")
	private Long idcompanyPk;

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	private Long ideventcodeNum;

	@Column(name = "IDPAYMENTORDER_PK")
	private Long idpaymentorderPk;

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	private Long idprocessPk;

	@Column(name = "IDUSER_PK")
	private Long iduserPk;

	@Column(name = "MESSAGE_CHR", length = 300)
	private String messageChr;

	@Column(name = "SOURCENUMBER_CHR", nullable = false, length = 20)
	private String sourcenumberChr;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Column(name = "TYPE_CHR", nullable = false, length = 10)
	private String typeChr;

	@Column(name = "IDEXTERNALMESSAGE_NUM")
	private Long idexternalmessageNum;

	public Smslogmessage() {
	}

	public long getIdmessagePk() {
		return this.idmessagePk;
	}

	public void setIdmessagePk(long idmessagePk) {
		this.idmessagePk = idmessagePk;
	}

	public Timestamp getChangedateTim() {
		return this.changedateTim;
	}

	public void setChangedateTim(Timestamp changedateTim) {
		this.changedateTim = changedateTim;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDestinynumberChr() {
		return this.destinynumberChr;
	}

	public void setDestinynumberChr(String destinynumberChr) {
		this.destinynumberChr = destinynumberChr;
	}

	public Long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(Long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public Long getIdeventcodeNum() {
		return this.ideventcodeNum;
	}

	public void setIdeventcodeNum(Long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public Long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(Long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public Long getIdprocessPk() {
		return this.idprocessPk;
	}

	public void setIdprocessPk(Long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public Long getIduserPk() {
		return this.iduserPk;
	}

	public void setIduserPk(Long iduserPk) {
		this.iduserPk = iduserPk;
	}

	public String getMessageChr() {
		return this.messageChr;
	}

	public void setMessageChr(String messageChr) {
		this.messageChr = messageChr;
	}

	public String getSourcenumberChr() {
		return this.sourcenumberChr;
	}

	public void setSourcenumberChr(String sourcenumberChr) {
		this.sourcenumberChr = sourcenumberChr;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public String getTypeChr() {
		return typeChr;
	}

	public void setTypeChr(String typeChr) {
		this.typeChr = typeChr;
	}

	public Long getIdexternalmessageNum() {
		return idexternalmessageNum;
	}

	public void setIdexternalmessageNum(Long idexternalmessageNum) {
		this.idexternalmessageNum = idexternalmessageNum;
	}

	@Override
	public String toString() {
		return "Smslogmessage [idmessagePk=" + idmessagePk + ", changedateTim="
				+ changedateTim + ", creationdateTim=" + creationdateTim
				+ ", destinynumberChr=" + destinynumberChr + ", idcompanyPk="
				+ idcompanyPk + ", ideventcodeNum=" + ideventcodeNum
				+ ", idpaymentorderPk=" + idpaymentorderPk + ", idprocessPk="
				+ idprocessPk + ", iduserPk=" + iduserPk + ", messageChr="
				+ messageChr + ", sourcenumberChr=" + sourcenumberChr
				+ ", stateChr=" + stateChr + ", typeChr=" + typeChr
				+ ", idexternalmessageNum=" + idexternalmessageNum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idmessagePk ^ (idmessagePk >>> 32));
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
		Smslogmessage other = (Smslogmessage) obj;
		if (idmessagePk != other.idmessagePk)
			return false;
		return true;
	}

}