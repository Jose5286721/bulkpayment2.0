package py.com.global.spm.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author christiandelgado
 */
@Entity
@Table(name = "PERMISSION" )
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDPERMISSION_PK", unique = true, nullable = false, precision = 16, scale = 0)
    @NotNull
    @SequenceGenerator(name="PERMISSION_SEQ_GEN", sequenceName="PERMISSION_SEQ",allocationSize=1)
    @GeneratedValue(generator="PERMISSION_SEQ_GEN")
    @JsonView({ScopeViews.Basics.class})
    private Long idpermissionPk;

    @NotNull
    @Column(name = "NAME_CHR", length = 50)
    @Length(max = 50)
    @JsonView({ScopeViews.Basics.class})
    private String nameChr;

    @Column(name = "DESCRIPTION_CHR")
    @JsonView({ScopeViews.Basics.class})
    private String descriptionChr;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permission")
    @JsonView({ScopeViews.NoBasics.class})
    @JsonBackReference
    private Set<RolPermission> rolPermissions = new HashSet<RolPermission>(0);

    public Permission() {
    }

    public Long getIdpermissionPk() {
        return idpermissionPk;
    }

    public void setIdpermissionPk(Long idpermissionPk) {
        this.idpermissionPk = idpermissionPk;
    }

    public String getNameChr() {
        return nameChr;
    }

    public void setNameChr(String nameChr) {
        this.nameChr = nameChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public Set<RolPermission> getRolPermissions() {
        return rolPermissions;
    }

    public void setRolPermissions(Set<RolPermission> rolPermissions) {
        this.rolPermissions = rolPermissions;
    }


}
