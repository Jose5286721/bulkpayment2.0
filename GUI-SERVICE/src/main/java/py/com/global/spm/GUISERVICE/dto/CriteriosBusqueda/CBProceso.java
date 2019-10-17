package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CBProceso {
    @Size(max = 20, message = "0037")
    private  String nombre;
    @Size(max = 100, message = "0037")
    private  String descripcion;
    private  Long id;
    @NotNull(message = "0030")
    private Boolean allByPage;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAllByPage() {
        return allByPage;
    }

    public void setAllByPage(Boolean allByPage) {
        this.allByPage = allByPage;
    }

    @Override
    public String toString() {
        return "CBProceso{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
