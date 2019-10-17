package py.com.global.spm.model.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DraftPaymentOrderDto {
    Long idPaymentOrder;
    Long idDraft;
    Long idEvent;

    public Long getIdPaymentOrder() {
        return idPaymentOrder;
    }

    public void setIdPaymentOrder(Long idPaymentOrder) {
        this.idPaymentOrder = idPaymentOrder;
    }

    public Long getIdDraft() {
        return idDraft;
    }

    public void setIdDraft(Long idDraft) {
        this.idDraft = idDraft;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public String toString() {
        return "DraftPaymentOrderDto{" +
                "idPaymentOrder=" + idPaymentOrder +
                ", idDraft=" + idDraft +
                ", idEvent=" + idEvent +
                '}';
    }
}
