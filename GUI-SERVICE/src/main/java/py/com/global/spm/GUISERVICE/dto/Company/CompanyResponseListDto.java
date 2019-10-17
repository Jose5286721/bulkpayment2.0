package py.com.global.spm.GUISERVICE.dto.Company;

import java.util.Date;

/**
 * Created by cbenitez on 3/19/18.
 */
public class CompanyResponseListDto {
    private Long idcompanyPk;
    private String companynameChr;
    private Date creationdateTim;
    private String descriptionChr;
    private String stateChr;
    private String contactnameChr;
    private String emailChr;
    private String phonenumberChr;
    private Date lastopdateTim;
    private Long trxdaycountNum;
    private Long trxmonthcountNum;
    private Long trxtotalcountNum;


    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public String getContactnameChr() {
        return contactnameChr;
    }

    public void setContactnameChr(String contactnameChr) {
        this.contactnameChr = contactnameChr;
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


    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public Date getLastopdateTim() {
        return lastopdateTim;
    }

    public void setLastopdateTim(Date lastopdateTim) {
        this.lastopdateTim = lastopdateTim;
    }

    public Long getTrxdaycountNum() {
        return trxdaycountNum;
    }

    public void setTrxdaycountNum(Long trxdaycountNum) {
        this.trxdaycountNum = trxdaycountNum;
    }

    public Long getTrxmonthcountNum() {
        return trxmonthcountNum;
    }

    public void setTrxmonthcountNum(Long trxmonthcountNum) {
        this.trxmonthcountNum = trxmonthcountNum;
    }

    public Long getTrxtotalcountNum() {
        return trxtotalcountNum;
    }

    public void setTrxtotalcountNum(Long trxtotalcountNum) {
        this.trxtotalcountNum = trxtotalcountNum;
    }

    public Long getIdcompanyPk() {
        return idcompanyPk;
    }

    public void setIdcompanyPk(Long idcompanyPk) {
        this.idcompanyPk = idcompanyPk;
    }
}
