package py.com.global.spm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author christiandelgado on 18/04/18
 * @project GOP
 */
@Embeddable
public class UserRolId implements Serializable {


    private static final long serialVersionUID = 4777877248321846681L;
    private Long iduserPk;
    private Long idrolPk;

    @Column(name = "IDUSER_PK", nullable = false)
    @NotNull
    public Long getIduserPk() {
        return iduserPk;
    }

    public void setIduserPk(Long iduserPk) {
        this.iduserPk = iduserPk;
    }

    @Column(name = "IDROL_PK", nullable = false)
    @NotNull
    public Long getIdrolPk() {
        return idrolPk;
    }

    public void setIdrolPk(Long idrolPk) {
        this.idrolPk = idrolPk;
    }
}
