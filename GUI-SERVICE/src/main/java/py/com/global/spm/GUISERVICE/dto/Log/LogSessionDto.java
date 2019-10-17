package py.com.global.spm.GUISERVICE.dto.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import py.com.global.spm.domain.entity.Logsession;

import java.util.Date;

@JsonAutoDetect
public class LogSessionDto {
    private Long idlogPk;
    private String userChr;
    private String successlogin;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date logindateTim;
    private String messageChr;
    private String ipChr;

    public LogSessionDto() {
    }

    public LogSessionDto(Logsession logsession) {
        this.idlogPk = logsession.getIdlogPk();
        this.userChr = logsession.getUserChr();
        this.successlogin = logsession.isSuccessloginNum() ? "SI":"NO";
        this.logindateTim = logsession.getLogindateTim();
        this.messageChr = logsession.getMessageChr();
        this.ipChr = logsession.getIpChr();
    }

    public Long getIdlogPk() {
        return idlogPk;
    }

    public void setIdlogPk(Long idlogPk) {
        this.idlogPk = idlogPk;
    }

    public String getUserChr() {
        return userChr;
    }

    public void setUserChr(String userChr) {
        this.userChr = userChr;
    }

    public String getSuccesslogin() {
        return successlogin;
    }

    public void setSuccesslogin(String  successlogin) {
        this.successlogin= successlogin;
    }

    public Date getLogindateTim() {
        return logindateTim;
    }

    public void setLogindateTim(Date logindateTim) {
        this.logindateTim = logindateTim;
    }

    public String getMessageChr() {
        return messageChr;
    }

    public void setMessageChr(String messageChr) {
        this.messageChr = messageChr;
    }

    public String getIpChr() {
        return ipChr;
    }

    public void setIpChr(String ipChr) {
        this.ipChr = ipChr;
    }
}
