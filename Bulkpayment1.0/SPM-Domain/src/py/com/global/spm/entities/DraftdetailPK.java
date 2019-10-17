package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DRAFTDETAIL database table.
 * 
 */
@Embeddable
public class DraftdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IDDRAFT_PK", unique=true, nullable=false, precision=22)
	private long iddraftPk;

	@Column(name="IDBENEFICIARY_PK", unique=true, nullable=false, precision=22)
	private long idbeneficiaryPk;

	public DraftdetailPK() {
	}
	public long getIddraftPk() {
		return this.iddraftPk;
	}
	public void setIddraftPk(long iddraftPk) {
		this.iddraftPk = iddraftPk;
	}
	public long getIdbeneficiaryPk() {
		return this.idbeneficiaryPk;
	}
	public void setIdbeneficiaryPk(long idbeneficiaryPk) {
		this.idbeneficiaryPk = idbeneficiaryPk;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DraftdetailPK)) {
			return false;
		}
		DraftdetailPK castOther = (DraftdetailPK)other;
		return 
			(this.iddraftPk == castOther.iddraftPk)
			&& (this.idbeneficiaryPk == castOther.idbeneficiaryPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.iddraftPk ^ (this.iddraftPk >>> 32)));
		hash = hash * prime + ((int) (this.idbeneficiaryPk ^ (this.idbeneficiaryPk >>> 32)));
		
		return hash;
	}
}