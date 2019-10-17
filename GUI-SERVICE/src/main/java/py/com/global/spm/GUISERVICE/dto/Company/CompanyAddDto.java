package py.com.global.spm.GUISERVICE.dto.Company;


import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;


/**
 * @author christiandelgado
 */
public class CompanyAddDto {

    private String stateChr;
    @NotNull(message = "0030")
    private String companynameChr;
    @Size(max = 200, message = "0037")
    private String descriptionChr;
    @NotNull(message = "0030") @Size(max = 50)
    private String contactnameChr;
    @Email(message = "0038") @Size(max = 50, message = "0037")
    private String emailChr;
    @Size(max = 20, message = "0037")
    private String phonenumberChr;
    @NotNull(message = "0030")
    private Double percentcommissionNum;
    @NotNull(message = "0030") @Digits(integer = 10, fraction = 0)
    private Long trxdaylimitNum;
    @NotNull(message = "0030") @Digits(integer = 10, fraction = 0)
    private Long trxmonthlimitNum;
    @NotNull(message = "0030") @Digits(integer = 10, fraction = 0)
    private Long trxtotallimitNum;
    @Digits(integer = 10, fraction = 0)
    private Long trxdaycountNum;
    @Digits(integer = 10, fraction = 0)
    private Long trxmonthcountNum;
    @Digits(integer = 10, fraction = 0)
    private Long trxtotalcountNum;
    @NotNull @Size(max = 30, message = "0037")
    private String mtsusrChr;
    @NotNull @Size(max = 30, message = "0037")
    private String mtsbandChr;
    @NotNull @Size(max = 30, message = "0037")
    private String mtsrolbindChr;
    @NotNull @Size(max = 30, message = "0037")
    private String mtspasswordChr;

    @NotNull(message = "0030")
    @Max(value = 7,message = "0054")
    @Min(value = 1,message = "0055")
    private Integer priority;

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

    public Double getPercentcommissionNum() {
        return percentcommissionNum;
    }

    public void setPercentcommissionNum(Double percentcommissionNum) {
        this.percentcommissionNum = percentcommissionNum;
    }

    public Long getTrxdaylimitNum() {
        return trxdaylimitNum;
    }

    public void setTrxdaylimitNum(Long trxdaylimitNum) {
        this.trxdaylimitNum = trxdaylimitNum;
    }

    public Long getTrxmonthlimitNum() {
        return trxmonthlimitNum;
    }

    public void setTrxmonthlimitNum(Long trxmonthlimitNum) {
        this.trxmonthlimitNum = trxmonthlimitNum;
    }

    public Long getTrxtotallimitNum() {
        return trxtotallimitNum;
    }

    public void setTrxtotallimitNum(Long trxtotallimitNum) {
        this.trxtotallimitNum = trxtotallimitNum;
    }

    public String getMtsusrChr() {
        return mtsusrChr;
    }

    public void setMtsusrChr(String mtsusrChr) {
        this.mtsusrChr = mtsusrChr;
    }

    public String getMtsbandChr() {
        return mtsbandChr;
    }

    public void setMtsbandChr(String mtsbandChr) {
        this.mtsbandChr = mtsbandChr;
    }

    public String getMtsrolbindChr() {
        return mtsrolbindChr;
    }

    public void setMtsrolbindChr(String mtsrolbindChr) {
        this.mtsrolbindChr = mtsrolbindChr;
    }

    public String getMtspasswordChr() {
        return mtspasswordChr;
    }

    public void setMtspasswordChr(String mtspasswordChr) {
        this.mtspasswordChr = mtspasswordChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
