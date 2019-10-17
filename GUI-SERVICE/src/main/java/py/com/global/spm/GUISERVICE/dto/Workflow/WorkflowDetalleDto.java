package py.com.global.spm.GUISERVICE.dto.Workflow;

import py.com.global.spm.domain.entity.User;

import java.util.List;

public class WorkflowDetalleDto {
	

	private User user;
	private Long workflowId;
	private List<ApprovalDto> approvals;
	private Integer step;
	
	public User getUserId() {
		return user;
	}
	public void setUserId(User userId) {
		this.user = userId;
	}
	public Long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	
	public List<ApprovalDto> getApprovals() {
		return approvals;
	}
	public void setApprovals(List<ApprovalDto> approvals) {
		this.approvals = approvals;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "WorkflowDetalleDto{" +
				"userId=" + user +
				", workflowId=" + workflowId +
				", approvals=" + approvals +
				", step=" + step +
				'}';
	}
}
