package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SignRequestDto {
    Long idPaymentOrder;
    Long idUser;

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
        return "SignRequestDto{" +
                "idPaymentOrder=" + idPaymentOrder +
                '}';
    }
}
