package py.com.global.spm.GUISERVICE.enums;


import py.com.global.spm.GUISERVICE.utils.GeneralHelper;

public enum EstadosMtsRev {

		SATISFACTORIO('S',"Logmtsrev_state_success"),
		ERROR('E',"Logmtsrev_state_error");
		
		Character codigo0;
		String descriptionOrKey;
		private EstadosMtsRev(Character codigo0, String descriptionOrKey) {
			this.codigo0 = codigo0;
			this.descriptionOrKey = descriptionOrKey;
		}
		public Character getCodigo0() {
			return codigo0;
		}
		public String getDescriptionOrKey() {
			return descriptionOrKey;
		}
		
//		@Override
//		public String toString() {
//			return GeneralHelper.getMessages(getDescriptionOrKey());
//		}

}
