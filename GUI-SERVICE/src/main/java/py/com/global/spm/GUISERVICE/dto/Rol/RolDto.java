package py.com.global.spm.GUISERVICE.dto.Rol;

import py.com.global.spm.domain.entity.Company;

import java.io.Serializable;

/**
 * Created by global on 3/22/18.
 */
public class RolDto implements Serializable{

    private Long idrolPk;
    private String rolnameChr;
    private String descriptionChr;
    private Company company;
    private Boolean defaultrolNum;
    private boolean stateNum;
    private boolean isuperrolNum;


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

    public boolean isuperrolNum() {
        return isuperrolNum;
    }

    public void setIsuperrolNum(boolean isuperrolNum) {
        this.isuperrolNum = isuperrolNum;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
