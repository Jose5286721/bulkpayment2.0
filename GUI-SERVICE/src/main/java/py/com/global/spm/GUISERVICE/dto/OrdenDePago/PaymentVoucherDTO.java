package py.com.global.spm.GUISERVICE.dto.OrdenDePago;

import py.com.global.spm.domain.entity.Logdraftop;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentVoucherDTO {
    private String name;

    private String conceptPayment;

    private BigDecimal accreditedAmount;
    private String accreditedRefNumber;
    private Date emittedDate;

    private String receptorCiNumber;
    private String amountChr;
    private String accreditedLineChr;

    public PaymentVoucherDTO() {
    }

    public PaymentVoucherDTO(Logdraftop logdraftop){


    }

    public String getName() {
        return name;
    }

    public String getConceptPayment() {
        return conceptPayment;
    }

    public BigDecimal getAccreditedAmount() {
        return accreditedAmount;
    }

    public String getAccreditedRefNumber() {
        return accreditedRefNumber;
    }

    public Date getEmittedDate() {
        return emittedDate;
    }

    public String getReceptorCiNumber() {
        return receptorCiNumber;
    }

    public String getAccreditedLineChr() {
        return accreditedLineChr;
    }
}
