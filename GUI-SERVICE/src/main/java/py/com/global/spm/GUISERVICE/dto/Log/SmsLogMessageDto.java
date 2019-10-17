package py.com.global.spm.GUISERVICE.dto.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import py.com.global.spm.domain.entity.Smslogmessage;

import java.util.Date;

@JsonAutoDetect
public class SmsLogMessageDto {
    private Long idmessagePk;
    private String usernameChr;
    private String eventcodeDescriptionChr;
    private String companynameChr;
    private String sourcenumberChr;
    private String messageChr;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationdateTim;
    private String destinynumberChr;
    private String stateChr;

    public SmsLogMessageDto() {
    }

    public SmsLogMessageDto(Smslogmessage smslogmessage) {
        this.idmessagePk = smslogmessage.getIdmessagePk();
        this.usernameChr = smslogmessage.getUsers().getUsernameChr();
        this.eventcodeDescriptionChr = smslogmessage.getEventcode().getId().getIdeventcodeNum()+"-"+smslogmessage.getEventcode().getDescriptionChr();
        this.companynameChr = smslogmessage.getCompany()!=null? smslogmessage.getCompany().getCompanynameChr() : null;
        this.sourcenumberChr = smslogmessage.getSourcenumberChr();
        this.messageChr = smslogmessage.getMessageChr();
        this.creationdateTim = smslogmessage.getCreationdateTim();
        this.destinynumberChr = smslogmessage.getDestinynumberChr();
        this.stateChr = smslogmessage.getStateChr();
    }

    public Long getIdmessagePk() {
        return idmessagePk;
    }

    public void setIdmessagePk(Long idmessagePk) {
        this.idmessagePk = idmessagePk;
    }

    public String getUsernameChr() {
        return usernameChr;
    }

    public void setUsernameChr(String usernameChr) {
        this.usernameChr = usernameChr;
    }

    public String getEventcodeDescriptionChr() {
        return eventcodeDescriptionChr;
    }

    public void setEventcodeDescriptionChr(String eventcodeDescriptionChr) {
        this.eventcodeDescriptionChr = eventcodeDescriptionChr;
    }

    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    public String getSourcenumberChr() {
        return sourcenumberChr;
    }

    public void setSourcenumberChr(String sourcenumberChr) {
        this.sourcenumberChr = sourcenumberChr;
    }

    public String getMessageChr() {
        return messageChr;
    }

    public void setMessageChr(String messageChr) {
        this.messageChr = messageChr;
    }

    public Date getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(Date creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public String getDestinynumberChr() {
        return destinynumberChr;
    }

    public void setDestinynumberChr(String destinynumberChr) {
        this.destinynumberChr = destinynumberChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }
}
