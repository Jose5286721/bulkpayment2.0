package py.com.global.spm.GUISERVICE.dto.OrdenDePago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelPaymentOrderDto {
    @NotNull(message = "0030")
    private Long idPaymentOrder;
    @Size(max = 100, message = "0030")
    private String motivo;

    public CancelPaymentOrderDto(Long idPaymentOrder, String motivo) {
        this.idPaymentOrder = idPaymentOrder;
        this.motivo = motivo;
    }

    public CancelPaymentOrderDto() {
    }

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

    @Override
    public String toString() {
        return "CancelPaymentOrderDto{" +
                "idPaymentOrder=" + idPaymentOrder +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
