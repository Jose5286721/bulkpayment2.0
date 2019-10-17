package py.com.global.spm.GUISERVICE.dto.Log;

import com.fasterxml.jackson.annotation.JsonFormat;
import py.com.global.spm.domain.entity.Logpayment;

import java.util.Date;


public class LogPaymentDto {
    private Long idpaymentNum;
    private Long idpaymentorderPk;
    private String beneficiaryPhoneNumber;
    private String processnameChr;
    private String companynameChr;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationdateTim;
    private String stateChr;
    private String eventDescription;

    public LogPaymentDto(Logpayment logpayment) {
        this.idpaymentNum = logpayment.getIdpaymentNum();
        this.idpaymentorderPk = logpayment.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk();
        this.beneficiaryPhoneNumber = logpayment.getPaymentorderdetail().getBeneficiary().getPhonenumberChr();
        this.processnameChr = logpayment.getEventcode().getProcess().getIdprocessPk()+"-"+logpayment.getEventcode().getProcess().getProcessnameChr();
        this.companynameChr = logpayment.getCompany().getCompanynameChr();
        this.creationdateTim = logpayment.getCreationdateTim();
        this.stateChr = logpayment.getStateChr();
        this.eventDescription = logpayment.getEventcode().getDescriptionChr();
    }

    public Long getIdpaymentNum() {
        return idpaymentNum;
    }

    public void setIdpaymentNum(Long idpaymentNum) {
        this.idpaymentNum = idpaymentNum;
    }

    public Long getIdpaymentorderPk() {
        return idpaymentorderPk;
    }

    public void setIdpaymentorderPk(Long idpaymentorderPk) {
        this.idpaymentorderPk = idpaymentorderPk;
    }

    public String getBeneficiaryPhoneNumber() {
        return beneficiaryPhoneNumber;
    }

    public void setBeneficiaryPhoneNumber(String beneficiaryPhoneNumber) {
        this.beneficiaryPhoneNumber = beneficiaryPhoneNumber;
    }

    public String getProcessnameChr() {
        return processnameChr;
    }

    public void setProcessnameChr(String processnameChr) {
        this.processnameChr = processnameChr;
    }

    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
