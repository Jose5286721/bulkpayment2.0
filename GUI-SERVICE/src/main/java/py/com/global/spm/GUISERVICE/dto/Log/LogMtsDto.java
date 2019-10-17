package py.com.global.spm.GUISERVICE.dto.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class LogMtsDto {
    private Long idlogmtsPk;
    private String yearAndMonth;
    private String dateHour;
    private Long idpaymentorderPk;
    private String companynameChr;
    private String idtrxmtsChr;
    private String stateChr;
    private String phoneNumber;
    private String beneficiaryCi;
    private String beneficiaryName;
    private String amountChr;

    public Long getIdlogmtsPk() {
        return idlogmtsPk;
    }

    public void setIdlogmtsPk(Long idlogmtsPk) {
        this.idlogmtsPk = idlogmtsPk;
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public Long getIdpaymentorderPk() {
        return idpaymentorderPk;
    }

    public void setIdpaymentorderPk(Long idpaymentorderPk) {
        this.idpaymentorderPk = idpaymentorderPk;
    }

    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    public String getIdtrxmtsChr() {
        return idtrxmtsChr;
    }

    public void setIdtrxmtsChr(String idtrxmtsChr) {
        this.idtrxmtsChr = idtrxmtsChr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBeneficiaryCi() {
        return beneficiaryCi;
    }

    public void setBeneficiaryCi(String beneficiaryCi) {
        this.beneficiaryCi = beneficiaryCi;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAmountChr() {
        return amountChr;
    }

    public void setAmountChr(String amountChr) {
        this.amountChr = amountChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    @Override
    public String toString() {
        return "LogMtsDto{" +
                "idlogmtsPk=" + idlogmtsPk +
                ", yearAndMonth='" + yearAndMonth + '\'' +
                ", dateHour='" + dateHour + '\'' +
                ", idpaymentorderPk=" + idpaymentorderPk +
                ", companynameChr='" + companynameChr + '\'' +
                ", idtrxmtsChr='" + idtrxmtsChr + '\'' +
                ", stateChr='" + stateChr + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", beneficiaryCi='" + beneficiaryCi + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", amountChr='" + amountChr + '\'' +
                '}';
    }
}
