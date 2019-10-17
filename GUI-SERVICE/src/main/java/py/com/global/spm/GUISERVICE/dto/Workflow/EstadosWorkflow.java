package py.com.global.spm.GUISERVICE.dto.Workflow;


public enum EstadosWorkflow {
	ACTIVE("AC","state_ACTIVE"),
	INACTIVE("IN","state_INACTIVE");
	
	String codigo;
	String descriptionOrKeyMsg;
	private EstadosWorkflow(String codigo0, String descriptionOrKeyMsg) {
		this.codigo = codigo0;
		this.descriptionOrKeyMsg = descriptionOrKeyMsg;
	}

	public String getCodigo0() {
		return codigo;
	}

	@Override
	public String toString() {
		return "EstadosWorkflow{" +
				"codigo=" + codigo +
				", descriptionOrKeyMsg='" + descriptionOrKeyMsg + '\'' +
				'}';
	}
}
