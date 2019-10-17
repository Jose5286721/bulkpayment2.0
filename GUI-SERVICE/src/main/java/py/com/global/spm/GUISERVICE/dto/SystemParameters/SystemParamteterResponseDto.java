package py.com.global.spm.GUISERVICE.dto.SystemParameters;

public class SystemParamteterResponseDto {
    private  Long idProcess;
    private  String parametro;
    private  String valor;
    private String processnameChr;
    private  ProcesoDto proceso;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProcesoDto getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoDto proceso) {
        this.proceso = proceso;
    }

    public Long getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(Long idProcess) {
        this.idProcess = idProcess;
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


    public String getProcessnameChr() {
        return processnameChr;
    }

    public void setProcessnameChr(String processnameChr) {
        this.processnameChr = processnameChr;
    }

    @Override
    public String toString() {
        return "SystemParamteterResponseDto{" +
                "idProcess=" + idProcess +
                ", parametro='" + parametro + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
