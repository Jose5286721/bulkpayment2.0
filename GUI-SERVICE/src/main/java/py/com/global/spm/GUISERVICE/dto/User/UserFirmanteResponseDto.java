package py.com.global.spm.GUISERVICE.dto.User;

/**
 * @author christiandelgado on 24/05/18
 * @project GOP
 */
public class UserFirmanteResponseDto {

    private Long iduserPk;
    private String usernameChr;
    private String userlastnameChr;
    private Integer step;

    public UserFirmanteResponseDto(Long iduserPk, String usernameChr, String userlastnameChr, Integer step) {
        this.iduserPk = iduserPk;
        this.usernameChr = usernameChr;
        this.userlastnameChr = userlastnameChr;
        this.step = step;
    }

    public UserFirmanteResponseDto() {
    }

    public Long getIduserPk() {
        return iduserPk;
    }

    public void setIduserPk(Long iduserPk) {
        this.iduserPk = iduserPk;
    }

    public String getUsernameChr() {
        return usernameChr;
    }

    public void setUsernameChr(String usernameChr) {
        this.usernameChr = usernameChr;
    }

    public String getUserlastnameChr() {
        return userlastnameChr;
    }

    public void setUserlastnameChr(String userlastnameChr) {
        this.userlastnameChr = userlastnameChr;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
