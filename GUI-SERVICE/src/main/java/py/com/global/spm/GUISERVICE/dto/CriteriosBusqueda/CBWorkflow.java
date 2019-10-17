package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Size;

public class CBWorkflow {
	@Size(max = 100, message = "0037")
	private String descriptionChr;
	@Size(max = 30, message = "0037")
	private String workflownameChr;
	private String stateChr;


	public String getDescriptionChr() {
		return descriptionChr;
	}
	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}
	public String getWorkflownameChr() {
		return workflownameChr;
	}
	public void setWorkflownameChr(String workflownameChr) {
		this.workflownameChr = workflownameChr;
	}
	public String getStateChr() {
		return stateChr;
	}
	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}
	@Override
	public String toString() {
		return "CriteriosBusquedaWorkflow [descriptionChr=" + descriptionChr + ", workflownameChr=" + workflownameChr
				+ ", stateChr=" + stateChr + "]";
	}
}
