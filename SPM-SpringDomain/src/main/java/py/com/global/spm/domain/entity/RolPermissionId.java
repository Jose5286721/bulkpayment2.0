package py.com.global.spm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by global on 3/14/18.
 */
@Embeddable
public class RolPermissionId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long idrolPk;
    private Long idpermissionPk;

    public RolPermissionId() {
    }

    public RolPermissionId(Long idrolPk, Long idpermissionPk) {
        this.idrolPk = idrolPk;
        this.idpermissionPk = idpermissionPk;
    }

    @Column(name = "IDROL_PK", nullable = false, precision = 22, scale = 0)
    @NotNull
    public Long getIdrolPk() {
        return this.idrolPk;
    }

    public void setIdrolPk(Long idrolPk) {
        this.idrolPk = idrolPk;
    }



    public void setIdpermissionPk(Long idpermissionPk) {
        this.idpermissionPk = idpermissionPk;
    }

    @Column(name = "IDPERMISSION_PK", nullable = false, precision = 22, scale = 0)
    @NotNull
    public Long getIdpermissionPk() {
        return idpermissionPk;
    }




    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof RolPermissionId))
            return false;
        RolPermissionId castOther = (RolPermissionId) other;

        return ((this.getIdrolPk() == castOther.getIdrolPk()) || (this
                .getIdrolPk() != null && castOther.getIdrolPk() != null && this
                .getIdrolPk().equals(castOther.getIdrolPk())))
                && ((this.getIdpermissionPk() == castOther.getIdpermissionPk()) || (this
                .getIdpermissionPk() != null
                && castOther.getIdpermissionPk() != null && this
                .getIdpermissionPk().equals(castOther.getIdpermissionPk())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getIdrolPk() == null ? 0 : this.getIdrolPk().hashCode());
        result = 37 * result
                + (getIdpermissionPk() == null ? 0 : this.getIdpermissionPk().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "idrolPk=" + idrolPk +
                ", idpermissionPk=" + idpermissionPk +
                '}';
    }
}
