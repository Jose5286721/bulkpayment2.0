package py.com.global.spm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by global on 3/14/18.
 */
@Embeddable
public class RolMenuId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long idrolPk;
    private Long idmenuPk;

    public RolMenuId() {
    }

    public RolMenuId(Long idrolPk, Long idmenuPk) {
        this.idrolPk = idrolPk;
        this.idmenuPk = idmenuPk;
    }

    @Column(name = "IDROL_PK", nullable = false, precision = 22, scale = 0)
    @NotNull
    public Long getIdrolPk() {
        return this.idrolPk;
    }

    public void setIdrolPk(Long idrolPk) {
        this.idrolPk = idrolPk;
    }

    @Column(name = "IDMENU_PK", nullable = false, precision = 22, scale = 0)
    @NotNull
    public Long getIdmenuPk() {
        return this.idmenuPk;
    }

    public void setIdmenuPk(Long idmenuPk) {
        this.idmenuPk = idmenuPk;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof RolMenuId))
            return false;
        RolMenuId castOther = (RolMenuId) other;

        return ((this.getIdrolPk() == castOther.getIdrolPk()) || (this
                .getIdrolPk() != null && castOther.getIdrolPk() != null && this
                .getIdrolPk().equals(castOther.getIdrolPk())))
                && ((this.getIdmenuPk() == castOther.getIdmenuPk()) || (this
                .getIdmenuPk() != null
                && castOther.getIdmenuPk() != null && this
                .getIdmenuPk().equals(castOther.getIdmenuPk())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getIdrolPk() == null ? 0 : this.getIdrolPk().hashCode());
        result = 37 * result
                + (getIdmenuPk() == null ? 0 : this.getIdmenuPk().hashCode());
        return result;
    }

}
