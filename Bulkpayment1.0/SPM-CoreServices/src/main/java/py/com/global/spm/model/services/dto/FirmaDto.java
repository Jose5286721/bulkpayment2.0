package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmaDto {
    long idPaymentOrder;
    long idUser;

    public long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public void setIdPaymentOrder(long idPaymentOrder) {
        this.idPaymentOrder = idPaymentOrder;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "FirmaDto{" +
                "idPaymentOrder=" + idPaymentOrder +
                ", idUser=" + idUser +
                '}';
    }
}
