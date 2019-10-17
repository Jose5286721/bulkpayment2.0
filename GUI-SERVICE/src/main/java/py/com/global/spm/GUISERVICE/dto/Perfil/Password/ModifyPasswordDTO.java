package py.com.global.spm.GUISERVICE.dto.Perfil.Password;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ModifyPasswordDTO {

    @NotNull(message = "0030") @Size(max = 100, message = "0037")
    private String password;

    public String getPassword() {
        return password;
    }

}
