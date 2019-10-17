package py.com.global.spm.GUISERVICE.enums;


import py.com.global.spm.GUISERVICE.utils.GeneralHelper;

public enum PaymentorderActions {
	FIRMAR("F","Paymentorder_actions_sign"),
	CANCELAR("C","Paymentorder_actions_cancel"),
	REVERTIR("R","Paymentorder_actions_revert"),
	REINTENTAR("T","Paymentorder_actions_reintentar");
	
	String codigo0;
	String descripcionOrKey;
	
	private PaymentorderActions(String codigo0, String descripcionOrKey) {
		this.codigo0 = codigo0;
		this.descripcionOrKey = descripcionOrKey;
	}

	public String getCodigo0() {
		return codigo0;
	}

	public String getDescripcionOrKey() {
		return descripcionOrKey;
	}
	
//	@Override
//	public String toString() {
//		return GeneralHelper.getMessages(getDescripcionOrKey());
//	}
	

	public static PaymentorderActions getPaymentorderActionForCode(String code){
		if (code==null) {
			return null;
		}
		
		for (PaymentorderActions action : PaymentorderActions.values()) {
			if (action.getCodigo0().equalsIgnoreCase(code)) {
				return action;
			}
		}
		return null;
	}
	
}
