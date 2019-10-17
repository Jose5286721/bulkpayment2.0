package py.com.global.spm.GUISERVICE.dto.SystemParameters;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SystemParameterRequestDto {
    @NotNull(message = "0030")
    Long idProceso;
    @NotNull(message = "0030") @Size(max = 50, message = "0037")
    String parametro;
    @Size(max = 400, message = "0037")
    String valor;
    @Size(max = 100, message = "0037")
    String descripcion;

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "SystemParameterRequestDto{" +
                "idProceso=" + idProceso +
                ", parametro='" + parametro + '\'' +
                ", valor='" + valor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
