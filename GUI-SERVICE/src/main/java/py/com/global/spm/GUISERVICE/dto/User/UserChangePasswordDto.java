package py.com.global.spm.GUISERVICE.dto.User;

import javax.validation.constraints.NotNull;

public class UserChangePasswordDto {
    public String getNewPassword() {
        return newPassword;
    }
    @NotNull(message = "0030")
    private String newPassword;



}
