package py.com.global.spm.GUISERVICE.dto.Workflow;


public class WorkflowDetalleIdDto {
	private Long iduserPk;
	private Long idworkflowPk;
	public Long getIduserPk() {
		return iduserPk;
	}
	public void setIduserPk(Long iduserPk) {
		this.iduserPk = iduserPk;
	}
	public Long getIdworkflowPk() {
		return idworkflowPk;
	}
	public void setIdworkflowPk(Long idworkflowPk) {
		this.idworkflowPk = idworkflowPk;
	}
	@Override
	public String toString() {
		return "WorflowDetalleIdDto [iduserPk=" + iduserPk + ", idworkflowPk=" + idworkflowPk + "]";
	}
}
