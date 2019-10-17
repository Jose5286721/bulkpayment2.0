package py.com.global.spm.GUISERVICE.dto.Rol;

/**
 * Created by global on 3/23/18.
 */
public class RolResponseListDto {
    private String companyNameChr;
    private Long idrolPk;
    private String rolnameChr;
    private String descriptionChr;
    private Boolean defaultrolNum;
    private boolean stateNum;
    private boolean canSeeSaldo;


    public String getCompanyNameChr() {
        return companyNameChr;
    }

    public void setCompanyNameChr(String companyNameChr) {
        this.companyNameChr = companyNameChr;
    }

    public Long getIdrolPk() {
        return idrolPk;
    }

    public void setIdrolPk(Long idrolPk) {
        this.idrolPk = idrolPk;
    }

    public String getRolnameChr() {
        return rolnameChr;
    }

    public void setRolnameChr(String rolnameChr) {
        this.rolnameChr = rolnameChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public Boolean getDefaultrolNum() {
        return defaultrolNum;
    }

    public void setDefaultrolNum(Boolean defaultrolNum) {
        this.defaultrolNum = defaultrolNum;
    }

    public boolean isStateNum() {
        return stateNum;
    }

    public void setStateNum(boolean stateNum) {
        this.stateNum = stateNum;
    }

    public boolean isCanSeeSaldo() {
        return canSeeSaldo;
    }

    public void setCanSeeSaldo(boolean canSeeSaldo) {
        this.canSeeSaldo = canSeeSaldo;
    }
}
