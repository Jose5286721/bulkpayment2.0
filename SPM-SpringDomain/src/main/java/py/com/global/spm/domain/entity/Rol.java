package py.com.global.spm.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import py.com.global.spm.domain.utils.ScopeViews;
import py.com.global.spm.domain.audit.EntityAuditEventListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by global on 3/13/18.
 */
@Entity
@Table(name = "ROL")
@EntityListeners(EntityAuditEventListener.class)
public class Rol implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long idrolPk;
    private String rolnameChr;
    private String descriptionChr;
    private Boolean defaultrolNum;
    private boolean stateNum;
    private boolean isuperrolNum;
    private boolean selected;

    private Set<RolPermission> rolPermissions = new HashSet<RolPermission>(0);

    public Rol() {
    }

    public Rol(Long idrolPk, String rolnameChr, boolean stateNum,
               boolean isuperrolNum) {
        this.idrolPk = idrolPk;
        this.rolnameChr = rolnameChr;
        this.stateNum = stateNum;
        this.isuperrolNum = isuperrolNum;
    }

    public Rol(Long idrolPk, String rolnameChr, String descriptionChr,
               boolean stateNum, boolean isuperrolNum) {
        this.idrolPk = idrolPk;
        this.rolnameChr = rolnameChr;
        this.descriptionChr = descriptionChr;
        this.stateNum = stateNum;
        this.isuperrolNum = isuperrolNum;
    }

    @Id
    @Column(name = "IDROL_PK", unique = true, nullable = false, precision = 22, scale = 0)
    @NotNull
    @JsonView({ScopeViews.Basics.class})
    @SequenceGenerator(name = "ROL_SEQ_GEN", sequenceName = "ROL_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "ROL_SEQ_GEN")
    public Long getIdrolPk() {
        return this.idrolPk;
    }

    public void setIdrolPk(Long idrolPk) {
        this.idrolPk = idrolPk;
    }

    @JsonView({ScopeViews.Basics.class})
    @Column(name = "ROLNAME_CHR", nullable = false, length = 20)
    @NotNull
    @Length(max = 20)
    public String getRolnameChr() {
        return this.rolnameChr;
    }

    public void setRolnameChr(String rolnameChr) {
        this.rolnameChr = rolnameChr;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @Column(name = "DESCRIPTION_CHR", length = 50)
    @Length(max = 50)
    public String getDescriptionChr() {
        return this.descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @Column(name = "STATE_NUM", nullable = false, precision = 1, scale = 0)
    public boolean isStateNum() {
        return this.stateNum;
    }

    public void setStateNum(boolean stateNum) {
        this.stateNum = stateNum;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @Column(name = "DEFAULTROL_NUM", nullable = true, precision = 1, scale = 0)
    public Boolean getDefaultrolNum() {
        return defaultrolNum;
    }

    public void setDefaultrolNum(Boolean defaultrolNum) {
        this.defaultrolNum = defaultrolNum;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @Column(name = "ISUPERROL_NUM", nullable = false, precision = 1, scale = 0)
    public boolean isIsuperrolNum() {
        return this.isuperrolNum;
    }

    public void setIsuperrolNum(boolean isuperrolNum) {
        this.isuperrolNum = isuperrolNum;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @Transient
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @JsonView({ScopeViews.NoBasics.class})
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "rol")
    public Set<RolPermission> getRolPermissions() {
        return rolPermissions;
    }

    public void setRolPermissions(Set<RolPermission> rolPermissions) {
        this.rolPermissions = rolPermissions;
    }

    @Override
    public String toString() {
        return "Rol [idrolPk=" + idrolPk + ", rolnameChr=" + rolnameChr
                + ", descriptionChr=" + descriptionChr// + ", company=" + company
                + ", defaultrolNum=" + defaultrolNum + ", stateNum=" + stateNum
                + ", isuperrolNum=" + isuperrolNum + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idrolPk == null) ? 0 : idrolPk.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rol other = (Rol) obj;
        if (idrolPk == null) {
            if (other.idrolPk != null)
                return false;
        } else if (!idrolPk.equals(other.idrolPk))
            return false;
        return true;
    }

}
