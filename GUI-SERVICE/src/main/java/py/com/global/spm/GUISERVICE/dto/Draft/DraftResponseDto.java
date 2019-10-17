package py.com.global.spm.GUISERVICE.dto.Draft;

import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Paymentordertype;
import py.com.global.spm.domain.entity.Workflow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author christiandelgado on 03/07/18
 * @project GOP
 */
public class DraftResponseDto implements Serializable{


    private static final long serialVersionUID = -5068014171356225850L;

    private Long iddraftPk;
    private Workflow workflow;
    private Company company;
    private Paymentordertype paymentordertype;
    private String stateChr;
    private String descriptionChr;
    private Date paiddateTim;
    private Date creationdateTim;
    private boolean recurrentNum;
    private Byte generatedayNum;
    private boolean fromtemplateNum;
    private Date fromdateTim;
    private Date todateTim;
    private boolean notifybenficiaryNum;
    private boolean notifysignerNum;
    private boolean notifygenNum;
    private boolean notifycancelNum;
    private BigDecimal amount;
    private boolean availableBeneficiaries;
    public DraftResponseDto() {
    }

    public Long getIddraftPk() {
        return iddraftPk;
    }

    public void setIddraftPk(Long iddraftPk) {
        this.iddraftPk = iddraftPk;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Paymentordertype getPaymentordertype() {
        return paymentordertype;
    }

    public void setPaymentordertype(Paymentordertype paymentordertype) {
        this.paymentordertype = paymentordertype;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public Date getPaiddateTim() {
        return paiddateTim;
    }

    public void setPaiddateTim(Date paiddateTim) {
        this.paiddateTim = paiddateTim;
    }

    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public boolean isRecurrentNum() {
        return recurrentNum;
    }

    public void setRecurrentNum(boolean recurrentNum) {
        this.recurrentNum = recurrentNum;
    }

    public boolean isFromtemplateNum() {
        return fromtemplateNum;
    }

    public void setFromtemplateNum(boolean fromtemplateNum) {
        this.fromtemplateNum = fromtemplateNum;
    }

    public Date getFromdateTim() {
        return fromdateTim;
    }

    public void setFromdateTim(Date fromdateTim) {
        this.fromdateTim = fromdateTim;
    }

    public Date getTodateTim() {
        return todateTim;
    }

    public void setTodateTim(Date todateTim) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public Byte getGeneratedayNum() {
        return generatedayNum;
    }

    public void setGeneratedayNum(Byte generatedayNum) {
        this.generatedayNum = generatedayNum;
    }

    public boolean isAvailableBeneficiaries() {
        return availableBeneficiaries;
    }

    public void setAvailableBeneficiaries(boolean availableBeneficiaries) {
        this.availableBeneficiaries = availableBeneficiaries;
    }
}
