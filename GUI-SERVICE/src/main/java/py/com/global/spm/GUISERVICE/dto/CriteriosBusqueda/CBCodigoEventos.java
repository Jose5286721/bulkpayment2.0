package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class CBCodigoEventos {
    @NotNull(message = "0030")
    private Boolean allByPage;
    @Digits(integer = 5, fraction = 0)
    private  Short idProceso;
    @Digits(integer = 10, fraction = 0)
    private Integer evento;

    public Boolean getAllByPage() {
        return allByPage;
    }

    public void setAllByPage(Boolean allByPage) {
        this.allByPage = allByPage;
    }

    public Short getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Short idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "CBCodigoEventos{" +
                "allByPage=" + allByPage +
                ", idProceso=" + idProceso +
                ", evento=" + evento +
                '}';
    }
}
