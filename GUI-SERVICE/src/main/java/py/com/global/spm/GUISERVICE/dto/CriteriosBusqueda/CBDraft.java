package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Size;

/**
 * @author christiandelgado on 10/07/18
 * @project GOP
 */
public class CBDraft {

    private Long iddraftPk;
    private Boolean recurrentNum;
    private Long idcompanyPk;
    private Long idworkflowPk;
    private Long idorderpaymenttypePk;
    private String creationdateTimStart;
    private String creationdateTimEnd;
    @Size(max = 100, message = "0037")
    private String descriptionChr;

    public CBDraft() {
    }

    public Long getIddraftPk() {
        return iddraftPk;
    }

    public void setIddraftPk(Long iddraftPk) {
        this.iddraftPk = iddraftPk;
    }

    public Boolean getRecurrentNum() {
        return recurrentNum;
    }

    public void setRecurrentNum(Boolean recurrentNum) {
        this.recurrentNum = recurrentNum;
    }

    public Long getIdcompanyPk() {
        return idcompanyPk;
    }

    public void setIdcompanyPk(Long idcompanyPk) {
        this.idcompanyPk = idcompanyPk;
    }

    public Long getIdworkflowPk() {
        return idworkflowPk;
    }

    public void setIdworkflowPk(Long idworkflowPk) {
        this.idworkflowPk = idworkflowPk;
    }

    public Long getIdorderpaymenttypePk() {
        return idorderpaymenttypePk;
    }

    public void setIdorderpaymenttypePk(Long idorderpaymenttypePk) {
        this.idorderpaymenttypePk = idorderpaymenttypePk;
    }

    public String getCreationdateTimStart() {
        return creationdateTimStart;
    }

    public void setCreationdateTimStart(String creationdateTimStart) {
        this.creationdateTimStart = creationdateTimStart;
    }

    public String getCreationdateTimEnd() {
        return creationdateTimEnd;
    }

    public void setCreationdateTimEnd(String creationdateTimEnd) {
        this.creationdateTimEnd = creationdateTimEnd;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }
}
