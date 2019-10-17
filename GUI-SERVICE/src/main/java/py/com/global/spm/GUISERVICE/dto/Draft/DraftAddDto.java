package py.com.global.spm.GUISERVICE.dto.Draft;

import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author christiandelgado on 03/07/18
 * @project GOP
 */
public class DraftAddDto {
    @Digits(integer = 19, fraction = 0)
    private Long idWorkflow;
    @Digits(integer = 19, fraction = 0)
    private Long idCompany;
    @NotNull(message = "0030") @Digits(integer = 19, fraction = 0)
    private Long idPaymentordertype;
    @Size(max = 100, message = "0037")
    private String descriptionChr;
    private String paiddateTim;
    @NotNull(message = "0030")
    private Boolean recurrentNum;
    private String fromdateTim;
    private String todateTim;
    @NotNull(message = "0030")
    private boolean notifybenficiaryNum;          //Pago Beneficiario
    @NotNull(message = "0030")
    private boolean notifysignerNum;            //Solicitud de aprobacion a Firmantes
    @NotNull(message = "0030")
    private boolean notifygenNum;               //Generacion a Firmantes
    @NotNull(message = "0030")
    private boolean notifycancelNum;            //Cancelacion de Firmantes
    @NotNull(message = "0030")
    private List<BeneficiaryDraftDetailDto> beneficiariesToAdd;
    private Byte generateDayNum;

    private Boolean fastDraft;

    public DraftAddDto() {
    }

    public Long getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(Long idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public Long getIdPaymentordertype() {
        return idPaymentordertype;
    }

    public void setIdPaymentordertype(Long idPaymentordertype) {
        this.idPaymentordertype = idPaymentordertype;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public String getPaiddateTim() {
        return paiddateTim;
    }

    public void setPaiddateTim(String paiddateTim) {
        this.paiddateTim = paiddateTim;
    }

    public Boolean getRecurrentNum() {
        return recurrentNum;
    }

    public void setRecurrentNum(Boolean recurrentNum) {
        this.recurrentNum = recurrentNum;
    }

    public String getFromdateTim() {
        return fromdateTim;
    }

    public void setFromdateTim(String fromdateTim) {
        this.fromdateTim = fromdateTim;
    }

    public String getTodateTim() {
        return todateTim;
    }

    public void setTodateTim(String todateTim) {
        this.todateTim = todateTim;
    }

    public boolean isNotifybenficiaryNum() {
        return notifybenficiaryNum;
    }

    public void setNotifybenficiaryNum(boolean notifybenficiaryNum) {
        this.notifybenficiaryNum = notifybenficiaryNum;
    }

    public boolean isNotifysignerNum() {
        return notifysignerNum;
    }

    public void setNotifysignerNum(boolean notifysignerNum) {
        this.notifysignerNum = notifysignerNum;
    }

    public boolean isNotifygenNum() {
        return notifygenNum;
    }

    public void setNotifygenNum(boolean notifygenNum) {
        this.notifygenNum = notifygenNum;
    }

    public boolean isNotifycancelNum() {
        return notifycancelNum;
    }

    public void setNotifycancelNum(boolean notifycancelNum) {
        this.notifycancelNum = notifycancelNum;
    }

    public List<BeneficiaryDraftDetailDto> getBeneficiariesToAdd() {
        return beneficiariesToAdd;
    }

    public void setBeneficiariesToAdd(List<BeneficiaryDraftDetailDto> beneficiariesToAdd) {
        this.beneficiariesToAdd = beneficiariesToAdd;
    }

    public Byte getGenerateDayNum() {
        return generateDayNum;
    }

    public void setGenerateDayNum(Byte generateDayNum) {
        this.generateDayNum = generateDayNum;
    }

    public Boolean getFastDraft() {
        return fastDraft;
    }

    public void setFastDraft(Boolean fastDraft) {
        this.fastDraft = fastDraft;
    }
}
