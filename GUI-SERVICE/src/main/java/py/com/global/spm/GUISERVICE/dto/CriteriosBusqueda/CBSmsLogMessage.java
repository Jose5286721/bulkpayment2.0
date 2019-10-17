package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

public class CBSmsLogMessage {
    private String sourcenumberChr;
    private Long idCompany;
    private String stateChr;
    private String sinceDate;
    private String toDate;

    public CBSmsLogMessage(String sourcenumberChr, Long idCompany, String stateChr, String sinceDate, String toDate) {
        this.sourcenumberChr = sourcenumberChr;
        this.idCompany = idCompany;
        this.stateChr = stateChr;
        this.sinceDate = sinceDate;
        this.toDate = toDate;
    }
    public CBSmsLogMessage() {

    }

    public String getSourcenumberChr() {
        return sourcenumberChr;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public String getStateChr() {
        return stateChr;
    }

    public String getSinceDate() {
        return sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CBSmsLogMessage{" +
                "sourcenumberChr='" + sourcenumberChr + '\'' +
                ", idCompany=" + idCompany +
                ", stateChr=" + stateChr +
                ", sinceDate='" + sinceDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
