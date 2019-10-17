package py.com.global.spm.GUISERVICE.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EstadosMessage {
    SATISFACTORIO("SU","Satisfactorio"),
    ERROR("ER","Error"),
    DESCONOCIDO("DE","Desconocido");

    String code;
    String description;
    EstadosMessage(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static EstadosMessage enumOfCode(String  code) {
        Optional<EstadosMessage> optional= Arrays.stream(values())
                .filter(sP -> sP.code.equals(code))
                .findFirst();
        return optional.orElse(DESCONOCIDO);
    }
}
