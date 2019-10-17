package py.com.global.spm.GUISERVICE.dto.Rol;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by cbenitez on 3/27/18.
 */
public class RolRequestDto implements Serializable{

    @NotNull(message = "0030")
    private Long idrolPk;
    @NotNull(message = "0030") @Size(max = 20)
    private String rolnameChr;
    private Long companyId;
    @Size(max = 50)
    private String descriptionChr;
    private Boolean defaultrolNum;
    @NotNull(message = "0030")
    private Map<String ,Long> listPermisos;
    private Boolean stateNum;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Boolean getStateNum() {
        return stateNum;
    }

    public void setStateNum(Boolean stateNum) {
        this.stateNum = stateNum;
    }

    public Map<String ,Long> getListPermisos() {
        return listPermisos;
    }

    public void setListPermisos(Map<String ,Long>listPermisos) {
        this.listPermisos = listPermisos;
    }
}
