package py.com.global.spm.GUISERVICE.dto.Perfil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class BasicProfile {
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userName;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userLastName;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userJobTitle;

    private boolean firmante;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserJobTitle() {
        return userJobTitle;
    }

    public void setUserJobTitle(String userJobTitle) {
        this.userJobTitle = userJobTitle;
    }

    public boolean isFirmante() {
        return firmante;
    }

    public void setFirmante(boolean firmante) {
        this.firmante = firmante;
    }
}
