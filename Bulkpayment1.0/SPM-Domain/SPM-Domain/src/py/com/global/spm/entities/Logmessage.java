package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the LOGMESSAGE database table.
 * 
 */
@Entity
@Table(name = "LOGMESSAGE")
public class Logmessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOGMESSAGE_IDMESSAGEPK_GENERATOR", sequenceName = "LOGMESSAGE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGMESSAGE_IDMESSAGEPK_GENERATOR")
	@Column(name = "IDMESSAGE_PK", unique = true, nullable = false, precision = 22)
	private long idmessagePk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESTNUMBER_CHR", nullable = true, length = 20)
	private String destnumberChr;

	@Column(name = "DESTEMAIL_CHR", nullable = true, length = 200)
	private String destemailChr;

	@Column(name = "IDCOMPANY_PK", precision = 22)
	private Long idcompanyPk;

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	private long ideventcodeNum;

	@Column(name = "IDPAYMENTORDER_PK", precision = 22)
	private Long idpaymentorderPk;

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	private long idprocessPk;

	@Column(name = "MESSAGE_CHR", nullable = false, length = 400)
	private String messageChr;

	@Column(name = "NOTIFICATIONEVENT_CHR", nullable = false, length = 100)
	private String notificationeventChr;
	
	@Column(name = "TRANSACTIONCODE_CHR", nullable = true, length = 30)
	private String transactioncodeChr;

	@Column(name = "ORIGNUMBER_CHR", nullable = true, length = 20)
	private String orignumberChr;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	public Logmessage() {
	}

	public long getIdmessagePk() {
		return this.idmessagePk;
	}

	public void setIdmessagePk(long idmessagePk) {
		this.idmessagePk = idmessagePk;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDestnumberChr() {
		return this.destnumberChr;
	}

	public void setDestnumberChr(String destnumberChr) {
		this.destnumberChr = destnumberChr;
	}

	public Long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(Long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIdeventcodeNum() {
		return this.ideventcodeNum;
	}

	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public Long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(Long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIdprocessPk() {
		return this.idprocessPk;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public String getMessageChr() {
		return this.messageChr;
	}

	public void setMessageChr(String messageChr) {
		this.messageChr = messageChr;
	}

	public String getNotificationeventChr() {
		return this.notificationeventChr;
	}

	public void setNotificationeventChr(String notificationeventChr) {
		this.notificationeventChr = notificationeventChr;
	}

	public String getOrignumberChr() {
		return this.orignumberChr;
	}

	public void setOrignumberChr(String orignumberChr) {
		this.orignumberChr = orignumberChr;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}
	
	public String getTransactioncodeChr() {
		return transactioncodeChr;
	}

	public void setTransactioncodeChr(String transactioncodeChr) {
		this.transactioncodeChr = transactioncodeChr;
	}

	public String getDestemailChr() {
		return destemailChr;
	}

	public void setDestemailChr(String destemailChr) {
		this.destemailChr = destemailChr;
	}
	
	@Override
	public String toString() {
		return "Logmessage [idmessagePk=" + idmessagePk + ",orignumberChr=" 
				+ orignumberChr +", destnumberChr="
				+ destnumberChr + ", destemailChr=" + destemailChr
				+ ", creationdateTim=" + creationdateTim + ",notificationeventChr=" 
				+ notificationeventChr + ", messageChr="
				+ messageChr + ", idcompanyPk=" + idcompanyPk
				+ ", ideventcodeNum=" + ideventcodeNum + ", idpaymentorderPk="
				+ idpaymentorderPk + ", idprocessPk=" + idprocessPk
				+ ", stateChr=" + stateChr + "]";
	}

}