package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the PROCESSPERFORMANCE database table.
 * 
 */
@Entity
@Table(name="PROCESSPERFORMANCE")
public class Processperformance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProcessperformancePK id;

	@Column(name="AVERAGE_NUM", precision=22)
	private Long averageNum;

	@Column(name="CHANGEDATE_TIME")
	private Timestamp changedateTime;

	@Column(name="CREATIONDATE_TIM")
	private Timestamp creationdateTim;

	@Column(name="DESCRIPTION_CHR", length=20)
	private String descriptionChr;

	@Column(name="DISCARDED_NUM", precision=22)
	private long discardedNum;

	@Column(name="EXPIRED_NUM", precision=22)
	private long expiredNum;

	@Column(name="REQUESTED_NUM", precision=22)
	private long requestedNum;

	@Column(name="RESPONDED_NUM", precision=22)
	private long respondedNum;

	public Processperformance() {
	}

	public ProcessperformancePK getId() {
		return this.id;
	}

	public void setId(ProcessperformancePK id) {
		this.id = id;
	}

	public Long getAverageNum() {
		return this.averageNum;
	}

	public void setAverageNum(Long averageNum) {
		this.averageNum = averageNum;
	}

	public Timestamp getChangedateTime() {
		return this.changedateTime;
	}

	public void setChangedateTime(Timestamp changedateTime) {
		this.changedateTime = changedateTime;
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

	public long getDiscardedNum() {
		return this.discardedNum;
	}

	public void setDiscardedNum(long discardedNum) {
		this.discardedNum = discardedNum;
	}

	public long getExpiredNum() {
		return this.expiredNum;
	}

	public void setExpiredNum(long expiredNum) {
		this.expiredNum = expiredNum;
	}

	public long getRequestedNum() {
		return this.requestedNum;
	}

	public void setRequestedNum(long requestedNum) {
		this.requestedNum = requestedNum;
	}

	public long getRespondedNum() {
		return this.respondedNum;
	}

	public void setRespondedNum(long respondedNum) {
		this.respondedNum = respondedNum;
	}

}