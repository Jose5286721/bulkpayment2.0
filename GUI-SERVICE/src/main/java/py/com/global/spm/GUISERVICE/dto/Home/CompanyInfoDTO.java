package py.com.global.spm.GUISERVICE.dto.Home;

import java.math.BigDecimal;
import java.util.Date;

public class CompanyInfoDTO {
    private Integer totalPayment;
    private String totalAmount;
    private Date lastPaymentDate;

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }
}
