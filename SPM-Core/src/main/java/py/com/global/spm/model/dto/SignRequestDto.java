package py.com.global.spm.model.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class SignRequestDto {
    @NotNull(message = "0030") @Max(value = Long.MAX_VALUE, message = "0037")
    private Long idPaymentOrder;
    @NotNull(message = "0030") @Max(value = Long.MAX_VALUE, message = "0037")
    private Long idUser;

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
