package py.com.global.spm.GUISERVICE.enums;


import py.com.global.spm.GUISERVICE.utils.GeneralHelper;

public enum EstadosDraft {

	ACTIVE("AC","state_ACTIVE"),
	INACTIVE("IN","state_INACTIVE");
	
	String codigo0;
	String descriptionOrKeyMsg;
	
	private EstadosDraft(String codigo0, String descriptionOrKeyMsg) {
		this.codigo0 = codigo0;
		this.descriptionOrKeyMsg = descriptionOrKeyMsg;
	}

	public String getCodigo0() {
		return codigo0;
	}

//	public String getDescriptionOrKeyMsg() {
//		return GeneralHelper.getMessages(descriptionOrKeyMsg);
//	}
//
//	@Override
//	public String toString() {
//		return getDescriptionOrKeyMsg();
//	}
	
	
	
	
	
}
