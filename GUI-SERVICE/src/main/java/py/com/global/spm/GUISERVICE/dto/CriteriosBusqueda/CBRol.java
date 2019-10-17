package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;


import javax.validation.constraints.Size;

/**
 * Created by global on 3/22/18.
 */
public class CBRol {
    @Size(max = 20, message = "0037")
    private String rolnameChr;
    private Boolean defaultrolNum;
    private Boolean stateNum;


    public String getRolnameChr() {
        return rolnameChr;
    }

    public void setRolnameChr(String rolnameChr) {
        this.rolnameChr = rolnameChr;
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
}
