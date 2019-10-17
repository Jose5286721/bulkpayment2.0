package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CBLogMessage implements Serializable {
    private static long serialVersionUID= 1L;
    @Digits(integer = 19, fraction = 0)
    private Long messageId;

    @Digits(integer = 19, fraction = 0)
    private Long paymentOrderId;

    @Size(max = 100, message = "0037")
    private String notificationEventChr;

    @Digits(integer = 19, fraction = 0)
    private Long companyId;
    private String cuentaOrigen;
    private String stateChr;
    private String sinceDate;
    private String toDate;


    public CBLogMessage() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getPaymentOrderId() {
        return paymentOrderId;
    }

    public String getNotificationEventChr() {
        return notificationEventChr;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String  getStateChr() {
        return stateChr;
    }

    public String getSinceDate() {
        return sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CBLogMessage{" +
                "messageId=" + messageId +
                ", paymentOrderId=" + paymentOrderId +
                ", notificationEventChr='" + notificationEventChr + '\'' +
                ", companyId=" + companyId +
                ", cuentaOrigen='" + cuentaOrigen + '\'' +
                ", stateChr='" + stateChr + '\'' +
                ", sinceDate='" + sinceDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
