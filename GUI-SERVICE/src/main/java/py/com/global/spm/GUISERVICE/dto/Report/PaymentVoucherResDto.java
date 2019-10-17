package py.com.global.spm.GUISERVICE.dto.Report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentVoucherResDto {
    private List<PaymentVoucherBodyDto> paymentVoucherBody = null;
    private BigDecimal total;

    public PaymentVoucherResDto() {
        total = new BigDecimal(0);
    }

    private void addTotal(BigDecimal amount) {
        if (amount != null)
            total = total.add(amount);
    }

    public BigDecimal getTotal() {
        if(paymentVoucherBody != null){
             paymentVoucherBody.forEach(value ->{
                 addTotal(new BigDecimal(value.getAccreditedAmount()));
             });
        }
        return total;
    }

    public List<PaymentVoucherBodyDto> getPaymentVoucherBody() {
        return paymentVoucherBody;
    }

    public void setPaymentVoucherBody(List<PaymentVoucherBodyDto> paymentVoucherBody) {
        this.paymentVoucherBody = (paymentVoucherBody != null) ? paymentVoucherBody : new ArrayList<>();

    }
}
