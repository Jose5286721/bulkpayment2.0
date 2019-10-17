package py.com.global.spm.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by global on 3/14/18.
 */
@Entity
@Table(name = "ROL_PERMISSION" )
public class RolPermission implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private RolPermissionId id;
    private Permission permission;
    private Rol rol;
    private Boolean activeNum;

    public RolPermission() {
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "idrolPk", column = @Column(name = "IDROL_PK", nullable = false, precision = 22, scale = 0)),
            @AttributeOverride(name = "idpermissionPk", column = @Column(name = "IDPERMISSION_PK", nullable = false, precision = 22, scale = 0)) })
    public RolPermissionId getId() {
        return id;
    }

    public void setId(RolPermissionId id) {
        this.id = id;
    }


    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "IDPERMISSION_PK", nullable = false, insertable = false, updatable = false)
    @NotNull
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDROL_PK", nullable = false, insertable = false, updatable = false)
    @NotNull
    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Column(name = "ACTIVE_NUM", precision = 1, scale = 0)
    public Boolean getActiveNum() {
        return this.activeNum;
    }

    public void setActiveNum(Boolean activeNum) {
        this.activeNum = activeNum;
    }

    @Override
    public String toString() {
        return "RolPermission{" +
                "id=" + id.toString() +
                ", activeNum=" + activeNum +
                '}';
    }
}
