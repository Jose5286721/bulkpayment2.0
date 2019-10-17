package py.com.global.spm.GUISERVICE.enums;


import java.util.Arrays;
import java.util.Optional;

public enum EstadosPayment {
	PAGO_PENDIENTE("EP","Pendiente de Pago"),
	PAGO_EN_PROCESO("PX","Pago en Proceso"),
	SATISFACTORIO("SU","Satisfactorio"),
	PARCIALMENTE_PAGADA("SP","Parcialmente Pagada"),
	REVERTIDO("RE","Revertido"),
	PARCIALMENTE_REVERTIDO("RP","Parcialmente Revertido"),
	ERROR("ER","Error"),
	DESCONOCIDO("DE","Desconocido")
	;

	String code;
	String description;
	
	EstadosPayment(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public static EstadosPayment enumOfCode(String code) {
		Optional<EstadosPayment> optional= Arrays.stream(values())
				.filter(sP -> sP.code.equals(code))
				.findFirst();
		return optional.orElse(DESCONOCIDO);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
