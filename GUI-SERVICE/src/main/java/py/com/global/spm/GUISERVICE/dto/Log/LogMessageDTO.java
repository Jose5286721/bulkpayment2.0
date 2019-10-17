package py.com.global.spm.GUISERVICE.dto.Log;

import py.com.global.spm.domain.entity.Logmessage;

import java.util.Date;

/**
 * Respuesta para el servicio de busqueda de log de notificacion por filtro
 * @see py.com.global.spm.GUISERVICE.services.LogMessageService
 */
public class LogMessageDTO {
    private Long idMessagePk;
    private Long idpaymentorderPk;
    private String companynameChr;
    private Date creationDateTim;
    private String destnumberChr;
    private String destemail;
    private String messageChr;
    private String notificationeventChr;
    private String stateChr;
    private Long ideventcodeNum;
    private String processnameChr;
    private String orignumberChr;

    public LogMessageDTO() {
    }

    public LogMessageDTO(Logmessage logMessage){
        this.idMessagePk = logMessage.getIdmessagePk();
        this.idpaymentorderPk = logMessage.getIdpaymentorderPk();
        this.companynameChr = logMessage.getCompany()!=null ? logMessage.getCompany().getCompanynameChr(): null;
        this.creationDateTim = logMessage.getCreationdateTim();
        this.destnumberChr = logMessage.getDestnumberChr();
        this.destemail = logMessage.getDestemail();
        this.messageChr = logMessage.getMessageChr();
        this.notificationeventChr = logMessage.getNotificationeventChr();
        this.stateChr = logMessage.getStateChr();
        this.ideventcodeNum = logMessage.getEventcode().getId().getIdeventcodeNum();
        this.processnameChr = (logMessage.getEventcode()!= null) ? logMessage.getEventcode().getProcess().getProcessnameChr() : null;
        this.orignumberChr = logMessage.getOrignumberChr();

    }

    public Long getIdMessagePk() {
        return idMessagePk;
    }

    public Long getIdpaymentorderPk() {
        return idpaymentorderPk;
    }

    public String getCompanynameChr() {
        return companynameChr;
    }

    public Date getCreationDateTim() {
        return creationDateTim;
    }

    public String getDestnumberChr() {
        return destnumberChr;
    }

    public String getDestemail() {
        return destemail;
    }

    public String getMessageChr() {
        return messageChr;
    }

    public String getNotificationeventChr() {
        return notificationeventChr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public Long getIdeventcodeNum() {
        return ideventcodeNum;
    }

    public String getProcessnameChr() {
        return processnameChr;
    }

    public String getOrignumberChr() {
        return orignumberChr;
    }
}
