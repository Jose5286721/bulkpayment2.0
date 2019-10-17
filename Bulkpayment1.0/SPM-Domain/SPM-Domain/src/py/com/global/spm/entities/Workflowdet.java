package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the WORKFLOWDET database table.
 * 
 */
@Entity
@Table(name = "WORKFLOWDET")
public class Workflowdet implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WorkflowdetPK id;

	@Column(name = "STEP_NUM", unique = true, nullable = false, precision = 2)
	private long stepNum;

	// bi-directional many-to-one association to Workflow
	@ManyToOne
	@JoinColumn(name = "IDWORKFLOW_PK", nullable = false, insertable = false, updatable = false)
	private Workflow workflow;

	public Workflowdet() {
	}

	public WorkflowdetPK getId() {
		return this.id;
	}

	public void setId(WorkflowdetPK id) {
		this.id = id;
	}

	public Workflow getWorkflow() {
		return this.workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public long getStepNum() {
		return stepNum;
	}

	public void setStepNum(long stepNum) {
		this.stepNum = stepNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Workflowdet other = (Workflowdet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}