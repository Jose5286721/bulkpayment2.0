package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelPaymentOrderDto {
    private Long idPaymentOrder;
    private String motivo;
    private Long idUser;

    public Long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public void setIdPaymentOrder(Long idPaymentOrder) {
        this.idPaymentOrder = idPaymentOrder;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "CancelPaymentOrderDto{" +
                "idPaymentOrder=" + idPaymentOrder +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
