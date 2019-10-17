package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

public class CBReport {
    private String fechaDesde;
    private String fechaHasta;
    private Long idcompanyPk;
    private String emailChr;
    private String phonenumberChr;
    private String stateChr;
    private String subscriberciChr;

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

    public Long getIdcompanyPk() {
        return idcompanyPk;
    }

    public void setIdcompanyPk(Long idcompanyPk) {
        this.idcompanyPk = idcompanyPk;
    }

    public String getEmailChr() {
        return emailChr;
    }

    public void setEmailChr(String emailChr) {
        this.emailChr = emailChr;
    }

    public String getPhonenumberChr() {
        return phonenumberChr;
    }

    public void setPhonenumberChr(String phonenumberChr) {
        this.phonenumberChr = phonenumberChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public String getSubscriberciChr() {
        return subscriberciChr;
    }

    public void setSubscriberciChr(String subscriberciChr) {
        this.subscriberciChr = subscriberciChr;
    }

    @Override
    public String toString() {
        return "CBReport{" +
                "fechaDesde='" + fechaDesde + '\'' +
                ", fechaHasta='" + fechaHasta + '\'' +
                ", idcompanyPk=" + idcompanyPk +
                ", emailChr=" + emailChr +
                ", phonenumberChr='" + phonenumberChr + '\'' +
                ", stateChr=" + stateChr +
                ", subscriberciChr='" + subscriberciChr + '\'' +
                '}';
    }
}
