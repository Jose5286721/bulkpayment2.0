package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPaymentOrderDto {
    long idUser;
    long idPaymentOrder;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public void setIdPaymentOrder(long idPaymentOrder) {
        this.idPaymentOrder = idPaymentOrder;
    }

    @Override
    public String toString() {
        return "UserPaymentOrderDto{" +
                "idUser=" + idUser +
                ", idPaymentOrder=" + idPaymentOrder +
                '}';
    }
}
