package py.com.global.spm.GUISERVICE.dto.Workflow;

import java.sql.Blob;
import java.util.Date;

import py.com.global.spm.GUISERVICE.dto.Company.CompanyAddDto;
import py.com.global.spm.domain.entity.Workflowdet;


public class ApprovalDto {
	private Long idapprovalPk;
	private Workflowdet workflowdet;
	private CompanyAddDto company;
	private Blob signature;
	private Boolean smssignedNum;
	private Date creationdateTim;
	private boolean signedNum;
	private Date signdateTim;
	public Long getIdapprovalPk() {
		return idapprovalPk;
	}

	public Workflowdet getWorkflowdet() {
		return workflowdet;
	}

	public void setWorkflowdet(Workflowdet workflowdet) {
		this.workflowdet = workflowdet;
	}

	public void setIdapprovalPk(Long idapprovalPk) {
		this.idapprovalPk = idapprovalPk;
	}

	public CompanyAddDto getCompany() {
		return company;
	}
	public void setCompany(CompanyAddDto company) {
		this.company = company;
	}
	public Blob getSignature() {
		return signature;
	}
	public void setSignature(Blob signature) {
		this.signature = signature;
	}
	public Boolean getSmssignedNum() {
		return smssignedNum;
	}
	public void setSmssignedNum(Boolean smssignedNum) {
		this.smssignedNum = smssignedNum;
	}
	public Date getCreationdateTim() {
		return creationdateTim;
	}
	public void setCreationdateTim(Date creationdateTim) {
		this.creationdateTim = creationdateTim;
	}
	public boolean isSignedNum() {
		return signedNum;
	}
	public void setSignedNum(boolean signedNum) {
		this.signedNum = signedNum;
	}
	public Date getSigndateTim() {
		return signdateTim;
	}
	public void setSigndateTim(Date signdateTim) {
		this.signdateTim = signdateTim;
	}
	@Override
	public String toString() {
		return "AprovalDto [idapprovalPk=" + idapprovalPk + ", workflowdet=" + workflowdet + ", company=" + company
				+ ", signature=" + signature + ", smssignedNum=" + smssignedNum + ", creationdateTim=" + creationdateTim
				+ ", signedNum=" + signedNum + ", signdateTim=" + signdateTim + "]";
	}
}
