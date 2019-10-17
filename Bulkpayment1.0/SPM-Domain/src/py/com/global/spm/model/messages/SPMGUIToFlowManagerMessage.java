package py.com.global.spm.model.messages;

import java.io.Serializable;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SPMGUIToFlowManagerMessage implements Serializable {

	public static final int CANCELACION_ORDENDEPAGO = 1;
	public static final int FIRMA_ORDENDEPAGO = 2;
	public static final int REVERSION_ORDENDEPAGO = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8907525595185315543L;
	private long idpaymentorder;
	private long idApproval;
	private long iduser;
	private int event;
	private boolean lastSigner;

	public long getIdpaymentorder() {
		return idpaymentorder;
	}

	public void setIdpaymentorder(long idpaymentorder) {
		this.idpaymentorder = idpaymentorder;
	}

	public long getIduser() {
		return iduser;
	}

	public void setIduser(long iduser) {
		this.iduser = iduser;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public boolean isLastSigner() {
		return lastSigner;
	}

	public void setLastSigner(boolean lastSigner) {
		this.lastSigner = lastSigner;
	}

	public long getIdApproval() {
		return idApproval;
	}

	public void setIdApproval(long idApproval) {
		this.idApproval = idApproval;
	}

	@Override
	public String toString() {
		return "SPMGUIToFlowManagerMessage [idpaymentorder=" + idpaymentorder
				+ ", idApproval=" + idApproval + ", iduser=" + iduser
				+ ", event=" + event + ", lastSigner=" + lastSigner + "]";
	}

}
