package py.com.global.spm.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the APPROVAL database table.
 * 
 */
@Entity
@Table(name = "APPROVAL")
@NamedQueries({ @NamedQuery(name = "getApprovalsByIdPaymentorder", query = "SELECT a FROM Approval a WHERE a.idpaymentorderPk = :idpaymentorder ORDER BY a.stepPk ASC") })
public class Approval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPROVAL_IDAPPROVALPK_GENERATOR", sequenceName = "APPROVAL_SEQ", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPROVAL_IDAPPROVALPK_GENERATOR")
	@Column(name = "IDAPPROVAL_PK", unique = true, nullable = false, precision = 22)
	private long idapprovalPk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "IDCOMPANY_PK", precision = 22)
	private long idcompanyPk;

	@Column(name = "IDPAYMENTORDER_PK", nullable = false, precision = 22)
	private long idpaymentorderPk;

	@Column(name = "IDUSER_PK", nullable = false, precision = 22)
	private long iduserPk;

	@Column(name = "IDWORKFLOW_PK", nullable = false, precision = 22)
	private long idworkflowPk;

	@Lob
	private byte[] signature;

	@Column(name = "SIGNDATE_TIM")
	private Timestamp signdateTim;

	@Column(name = "SIGNED_NUM", nullable = false, precision = 1)
	private Boolean signedNum;

	@Column(name = "SMSSIGNED_NUM", precision = 1)
	private Boolean smssignedNum;

	@Column(name = "STEP_PK", nullable = false, precision = 2)
	private long stepPk;


	public Approval() {
	}
	public long getIdapprovalPk() {
		return this.idapprovalPk;
	}

	public void setIdapprovalPk(long idapprovalPk) {
		this.idapprovalPk = idapprovalPk;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
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

	public byte[] getSignature() {
		return this.signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public Timestamp getSigndateTim() {
		return this.signdateTim;
	}

	public void setSigndateTim(Timestamp signdateTim) {
		this.signdateTim = signdateTim;
	}

	public Boolean getSignedNum() {
		return this.signedNum;
	}

	public void setSignedNum(Boolean signedNum) {
		this.signedNum = signedNum;
	}

	public Boolean getSmssignedNum() {
		return this.smssignedNum;
	}

	public void setSmssignedNum(Boolean smssignedNum) {
		this.smssignedNum = smssignedNum;
	}

	public long getStepPk() {
		return this.stepPk;
	}

	public void setStepPk(long stepPk) {
		this.stepPk = stepPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idapprovalPk ^ (idapprovalPk >>> 32));
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
		Approval other = (Approval) obj;
		if (idapprovalPk != other.idapprovalPk)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Approval [idapprovalPk=" + idapprovalPk + ", creationdateTim="
				+ creationdateTim + ", idcompanyPk=" + idcompanyPk
				+ ", idpaymentorderPk=" + idpaymentorderPk + ", iduserPk="
				+ iduserPk + ", idworkflowPk=" + idworkflowPk
				+ ", signdateTim=" + signdateTim + ", signedNum=" + signedNum
				+ ", smssignedNum=" + smssignedNum + ", stepPk=" + stepPk + "]";
	}

}