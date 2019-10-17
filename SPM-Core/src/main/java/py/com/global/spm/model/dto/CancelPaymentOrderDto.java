package py.com.global.spm.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CancelPaymentOrderDto {
    @NotNull(message = "0030") @Max(value = Long.MAX_VALUE, message = "0037")
    private Long idPaymentOrder;
    @Size(max = 50, message = "0037")
    private String motivo;
    @NotNull(message = "0030") @Max(value = Long.MAX_VALUE, message = "0037")
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
