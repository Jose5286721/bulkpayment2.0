package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EVENTCODE database table.
 * 
 */
@Embeddable
public class EventcodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IDPROCESS_PK", unique=true, nullable=false, precision=4)
	private long idprocessPk;

	@Column(name="IDEVENTCODE_NUM", unique=true, nullable=false, precision=5)
	private long ideventcodeNum;

	public EventcodePK() {
	}
	public long getIdprocessPk() {
		return this.idprocessPk;
	}
	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}
	public long getIdeventcodeNum() {
		return this.ideventcodeNum;
	}
	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EventcodePK)) {
			return false;
		}
		EventcodePK castOther = (EventcodePK)other;
		return 
			(this.idprocessPk == castOther.idprocessPk)
			&& (this.ideventcodeNum == castOther.ideventcodeNum);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idprocessPk ^ (this.idprocessPk >>> 32)));
		hash = hash * prime + ((int) (this.ideventcodeNum ^ (this.ideventcodeNum >>> 32)));
		
		return hash;
	}
}