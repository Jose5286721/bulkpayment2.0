package py.com.global.spm.GUISERVICE.enums;


import java.util.Arrays;
import java.util.Optional;

public enum EstadosMts {
	SATISFACTORIO("SU","Satisfactorio"),
	REVERTIDO("RE","Revertido"),
	ERROR("ER","Error"),
	DESCONOCIDO("DE","Desconocido");
	
	String code;
	String description;
	EstadosMts(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static EstadosMts enumOfCode(String  code) {
		Optional<EstadosMts> optional= Arrays.stream(values())
				.filter(sP -> sP.code.equals(code))
				.findFirst();
		return optional.orElse(DESCONOCIDO);
	}
	
//	@Override
//	public String toString() {
//		return GeneralHelper.getMessages(getDescriptionOrKey());
//	}
//
	
}
