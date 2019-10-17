package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CBSystemParameter {
    @Size(max = 50, message = "0037")
    private  String parametro;
    private  Short idProceso;
    @NotNull(message = "0030")
    private Boolean allByPage;
    @Size(max = 400, message = "0037")
    private String valor;

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public Short getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Short idProceso) {
        this.idProceso = idProceso;
    }

    public Boolean getAllByPage() {
        return allByPage;
    }

    public void setAllByPage(Boolean allByPage) {
        this.allByPage = allByPage;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "CBSystemParameter{" +
                "parametro='" + parametro + '\'' +
                ", idProceso=" + idProceso +
                ", allByPage=" + allByPage +
                ", valor='" + valor + '\'' +
                '}';
    }
}
