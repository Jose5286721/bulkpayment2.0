package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;

/**
 * The persistent class for the WORKFLOW database table.
 * 
 */
@Entity
@Table(name = "WORKFLOW")
public class Workflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDWORKFLOW_PK", unique = true, nullable = false)
	private long idworkflowPk;

	@Column(name = "DESCRIPTION_CHR", length = 100)
	private String descriptionChr;

	@Column(name = "IDCOMPANY_PK", nullable = false)
	private long idcompanyPk;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Column(name = "WORKFLOWNAME_CHR", nullable = false, length = 30)
	private String workflownameChr;

	@Column(name = "WALLET_CHR")
	private String walletChr;

	// bi-directional many-to-one association to Workflowdet
	@OneToMany(mappedBy = "workflow", fetch = FetchType.EAGER)
	private Set<Workflowdet> workflowdets;

	public Workflow() {
	}

	public long getIdworkflowPk() {
		return this.idworkflowPk;
	}

	public void setIdworkflowPk(long idworkflowPk) {
		this.idworkflowPk = idworkflowPk;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public String getWorkflownameChr() {
		return this.workflownameChr;
	}

	public void setWorkflownameChr(String workflownameChr) {
		this.workflownameChr = workflownameChr;
	}

	public String getWalletChr() {
		return walletChr;
	}

	public void setWalletChr(String walletChr) {
		this.walletChr = walletChr;
	}

	public Set<Workflowdet> getWorkflowdets() {
		return this.workflowdets;
	}

	public void setWorkflowdets(Set<Workflowdet> workflowdets) {
		this.workflowdets = workflowdets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idworkflowPk ^ (idworkflowPk >>> 32));
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
		Workflow other = (Workflow) obj;
		if (idworkflowPk != other.idworkflowPk)
			return false;
		return true;
	}

}