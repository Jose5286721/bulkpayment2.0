package py.com.global.spm.GUISERVICE.dto.OrdenDePago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelPaymentOrderCoreDto {
    private Long idPaymentOrder;
    private String motivo;
    private Long idUser;


    public CancelPaymentOrderCoreDto(Long idPaymentOrder, String motivo, Long idUser) {
        this.idPaymentOrder = idPaymentOrder;
        this.motivo = motivo;
        this.idUser = idUser;
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
