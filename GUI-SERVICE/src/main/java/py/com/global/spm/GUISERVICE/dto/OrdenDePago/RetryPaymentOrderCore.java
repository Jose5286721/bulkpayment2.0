package py.com.global.spm.GUISERVICE.dto.OrdenDePago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RetryPaymentOrderCore {
    private Long idPaymentOrder;
    private Long idUser;

    public RetryPaymentOrderCore(Long idPaymentOrder, Long idUser) {
        this.idPaymentOrder = idPaymentOrder;
        this.idUser = idUser;
    }

    public Long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public void setIdPaymentOrder(Long idPaymentOrder) {
        this.idPaymentOrder = idPaymentOrder;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "RetryPaymentOrderCore{" +
                "idPaymentOrder=" + idPaymentOrder +
                ", idUser=" + idUser +
                '}';
    }
}
