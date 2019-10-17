package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;


public class CBPaymentOrder {
    private Long idOrdenPago;
    private Long idWorkflow;
    private Long idTipoOrdenPago;
    private String estado;
    private Long idEmpresa;
    private String fechaDesde;
    private String fechaHasta;

    public Long getIdOrdenPago() {
        return idOrdenPago;
    }

    public void setIdOrdenPago(Long idOrdenPago) {
        this.idOrdenPago = idOrdenPago;
    }

    public Long getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(Long idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public Long getIdTipoOrdenPago() {
        return idTipoOrdenPago;
    }

    public void setIdTipoOrdenPago(Long idTipoOrdenPago) {
        this.idTipoOrdenPago = idTipoOrdenPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public String toString() {
        return "CBPaymentOrder{" +
                "idOrdenPago=" + idOrdenPago +
                ", idWorkflow=" + idWorkflow +
                ", idTipoOrdenPago=" + idTipoOrdenPago +
                ", estadoChr=" + estado +
                ", idEmpresa=" + idEmpresa +
                ", fechaDesde='" + fechaDesde + '\'' +
                ", fechaHasta='" + fechaHasta + '\'' +
                '}';
    }
}
