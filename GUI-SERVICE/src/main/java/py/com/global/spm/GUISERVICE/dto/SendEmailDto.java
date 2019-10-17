package py.com.global.spm.GUISERVICE.dto;

public class SendEmailDto {
    private String toEmail;
    private String fromEmail;
    private String message;
    private String subject;

    public SendEmailDto(String toEmail, String fromEmail, String message, String subject) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.message = message;
        this.subject = subject;
    }

    public SendEmailDto() {
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
