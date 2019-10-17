package py.com.global.spm.GUISERVICE.dto.Perfil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserProfile {
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userName;
    @NotNull(message = "0030") @Size(max = 30, message = "0037")
    private String userLastName;
    @Size(max = 100, message = "0037")
    private String password;
    @Size(max = 50, message = "0037")
    private String smspinChr;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmspinChr() {
        return smspinChr;
    }

    public void setSmspinChr(String smspinChr) {
        this.smspinChr = smspinChr;
    }
}
