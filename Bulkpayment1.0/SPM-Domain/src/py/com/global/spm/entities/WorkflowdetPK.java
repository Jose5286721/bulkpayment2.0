package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORKFLOWDET database table.
 * 
 */
@Embeddable
public class WorkflowdetPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "IDUSER_PK", unique = true, nullable = false)
	private long iduserPk;

	@Column(name = "IDWORKFLOW_PK", unique = true, nullable = false)
	private long idworkflowPk;

	public WorkflowdetPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkflowdetPK)) {
			return false;
		}
		WorkflowdetPK castOther = (WorkflowdetPK) other;
		return (this.iduserPk == castOther.iduserPk)
				&& (this.idworkflowPk == castOther.idworkflowPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.iduserPk ^ (this.iduserPk >>> 32)));
		hash = hash * prime
				+ ((int) (this.idworkflowPk ^ (this.idworkflowPk >>> 32)));

		return hash;
	}
}