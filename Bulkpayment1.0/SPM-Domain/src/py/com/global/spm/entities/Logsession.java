package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the LOGSESSION database table.
 * 
 */
@Entity
@Table(name="LOGSESSION")
public class Logsession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDLOG_PK", unique=true, nullable=false, precision=22)
	private long idlogPk;

	@Column(name="LOGINDATE_TIM", nullable=false)
	private Timestamp logindateTim;

	@Column(name="MESSAGE_CHR", length=200)
	private String messageChr;

	@Column(name="SUCCESSLOGIN_NUM", nullable=false, precision=22)
	private Boolean successloginNum;

	@Column(name="USER_CHR", nullable=false, length=50)
	private String userChr;

	public Logsession() {
	}

	public long getIdlogPk() {
		return this.idlogPk;
	}

	public void setIdlogPk(long idlogPk) {
		this.idlogPk = idlogPk;
	}

	public Timestamp getLogindateTim() {
		return this.logindateTim;
	}

	public void setLogindateTim(Timestamp logindateTim) {
		this.logindateTim = logindateTim;
	}

	public String getMessageChr() {
		return this.messageChr;
	}

	public void setMessageChr(String messageChr) {
		this.messageChr = messageChr;
	}

	public Boolean getSuccessloginNum() {
		return this.successloginNum;
	}

	public void setSuccessloginNum(Boolean successloginNum) {
		this.successloginNum = successloginNum;
	}

	public String getUserChr() {
		return this.userChr;
	}

	public void setUserChr(String userChr) {
		this.userChr = userChr;
	}

}