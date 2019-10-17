package py.com.global.spm.GUISERVICE.dto.Log;

import py.com.global.spm.GUISERVICE.services.UtilService;
import py.com.global.spm.domain.entity.Logdraftop;

import java.util.Date;

public class LogdraftOpDTO {
    private Long iddrafopPk;
    private Long idprocessPk;
    private String processNameChr;
    private Long ideventcodeNum;
    private String eventDescriptionChr;
    private Long idDraft;
    private String companyNameChr;
    private Long idPaymentOrder;
    private Date creationDateTim;
    private String usernameChr;
    private String paymentOrderTypeName;
    private String amountChr;


    public LogdraftOpDTO() {
    }

    public LogdraftOpDTO(Logdraftop logdraftop){
        iddrafopPk = logdraftop.getIddrafopPk();
        idprocessPk = logdraftop.getEventcode().getProcess().getIdprocessPk();
        processNameChr = logdraftop.getEventcode().getProcess().getProcessnameChr();
        ideventcodeNum = logdraftop.getEventcode().getId().getIdeventcodeNum();
        eventDescriptionChr = logdraftop.getEventcode().getDescriptionChr();
        idDraft = logdraftop.getDraft().getIddraftPk();
        companyNameChr = logdraftop.getCompany().getCompanynameChr();
        idPaymentOrder = (logdraftop.getPaymentorder() != null) ? logdraftop.getPaymentorder().getIdpaymentorderPk() : null;
        creationDateTim = logdraftop.getCreationdateTim();
        usernameChr = logdraftop.getDraft().getUser().getUsernameChr();
        paymentOrderTypeName =  (logdraftop.getPaymentorder() != null)? logdraftop.getPaymentorder().getPaymentordertype().getNameChr(): null;
        amountChr = UtilService.toNumberFormat(logdraftop.getPaymentorder().getAmountChr());
    }

    public Long getIddrafopPk() {
        return iddrafopPk;
    }

    public Long getIdprocessPk() {
        return idprocessPk;
    }

    public String getProcessNameChr() {
        return processNameChr;
    }

    public Long getIdeventcodeNum() {
        return ideventcodeNum;
    }

    public String getEventDescriptionChr() {
        return eventDescriptionChr;
    }

    public Long getIdDraft() {
        return idDraft;
    }

    public String getCompanyNameChr() {
        return companyNameChr;
    }

    public Long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public Date getCreationDateTim() {
        return creationDateTim;
    }

    public String getUsernameChr() {
        return usernameChr;
    }

    public String getPaymentOrderTypeName() {
        return paymentOrderTypeName;
    }

    public String getAmountChr() {
        return amountChr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogdraftOpDTO{");
        sb.append("iddrafopPk=").append(iddrafopPk);
        sb.append(", idprocessPk=").append(idprocessPk);
        sb.append(", processNameChr='").append(processNameChr).append('\'');
        sb.append(", ideventcodeNum=").append(ideventcodeNum);
        sb.append(", eventDescriptionChr='").append(eventDescriptionChr).append('\'');
        sb.append(", idDraft=").append(idDraft);
        sb.append(", companyNameChr='").append(companyNameChr).append('\'');
        sb.append(", idPaymentOrder=").append(idPaymentOrder);
        sb.append(", creationDateTim=").append(creationDateTim);
        sb.append('}');
        return sb.toString();
    }
}
