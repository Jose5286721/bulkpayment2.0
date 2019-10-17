package py.com.global.spm.GUISERVICE.dto.Draft;

import java.util.Date;

/**
 * @author christiandelgado on 11/07/18
 * @project GOP
 */
public class DraftResponseListDto {

    private Long iddraftPk;
    private String user;
    private String workflow;
    private String company;
    private String paymentordertype;
    private String stateChr;
    private String descriptionChr;
    private Date paiddateTim;
    private Date creationdateTim;
    private boolean recurrentNum;
    private Date fromdateTim;
    private Date todateTim;
    private Byte generatedayNum;

    public DraftResponseListDto() {
    }

    public Long getIddraftPk() {
        return iddraftPk;
    }

    public void setIddraftPk(Long iddraftPk) {
        this.iddraftPk = iddraftPk;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPaymentordertype() {
        return paymentordertype;
    }

    public void setPaymentordertype(String paymentordertype) {
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

    public Byte getGeneratedayNum() {
        return generatedayNum;
    }

    public void setGeneratedayNum(Byte generatedayNum) {
        this.generatedayNum = generatedayNum;
    }
}
