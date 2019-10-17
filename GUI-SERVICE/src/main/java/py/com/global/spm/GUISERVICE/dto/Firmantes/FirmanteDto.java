package py.com.global.spm.GUISERVICE.dto.Firmantes;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

@JsonView({ScopeViews.Details.class})
public class FirmanteDto {
    Long id;
    String nombre;
    Boolean firma;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getFirma() {
        return firma;
    }

    public void setFirma(Boolean firma) {
        this.firma = firma;
    }

    @Override
    public String toString() {
        return "FirmanteDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
