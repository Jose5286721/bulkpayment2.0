package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Digits;
import java.io.Serializable;

public class CBLogdraftOp implements Serializable {
    private static long serialVersionUID= 1L;

    @Digits(integer = 19, fraction = 0)
    private Long idCompany;

    @Digits(integer = 19, fraction = 0)
    private Long idDraftOp;

    @Digits(integer = 19, fraction = 0)
    private Long idDraft;

    @Digits(integer = 10, fraction = 0)
    private Long idEventCode;

    private String sinceDate;

    private String toDate;

    public CBLogdraftOp() {
    }

    public CBLogdraftOp(Long idCompany, Long idDraftOp, Long idDraft, Long idEventCode, String sinceDate, String toDate) {
        this.idCompany = idCompany;
        this.idDraftOp = idDraftOp;
        this.idDraft = idDraft;
        this.idEventCode = idEventCode;
        this.sinceDate = sinceDate;
        this.toDate = toDate;
    }



    public Long getIdCompany() {
        return idCompany;
    }

    public Long getIdDraftOp() {
        return idDraftOp;
    }

    public Long getIdDraft() {
        return idDraft;
    }

    public Long getIdEventCode() {
        return idEventCode;
    }

    public String getSinceDate() {
        return sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CBLogdraftOp{" +
                "idCompany=" + idCompany +
                ", idDraftOp=" + idDraftOp +
                ", idDraft=" + idDraft +
                ", idEventCode=" + idEventCode +
                ", sinceDate='" + sinceDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
