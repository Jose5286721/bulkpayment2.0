package py.com.global.spm.model.dto.redis;



import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;

@RedisHash("LogPaymentCache")
public class LogPaymentCache implements Serializable {
    private static final long serialVersionUID = -4564654789413156L;

    @Id
    private Long idpaymentNum;
    @Indexed
    private Long idpaymentorderPk;
    private String stateChr;
    private Long idbeneficiaryPk;
    private BigDecimal amountpaidNum;

    public LogPaymentCache(Long idpaymentorderPk,Long idpaymentNum, String stateChr, Long idbeneficiaryPk, BigDecimal amountpaidNum) {
        this.idpaymentorderPk = idpaymentorderPk;
        this.idpaymentNum = idpaymentNum;
        this.stateChr = stateChr;
        this.idbeneficiaryPk = idbeneficiaryPk;
        this.amountpaidNum = amountpaidNum;
    }

    public LogPaymentCache() {

    }


    public Long getIdpaymentorderPk() {
        return idpaymentorderPk;
    }

    public void setIdpaymentorderPk(Long idpaymentorderPk) {
        this.idpaymentorderPk = idpaymentorderPk;
    }

    public Long getIdpaymentNum() {
        return idpaymentNum;
    }

    public void setIdpaymentNum(Long idpaymentNum) {
        this.idpaymentNum = idpaymentNum;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public Long getIdbeneficiaryPk() {
        return idbeneficiaryPk;
    }

    public void setIdbeneficiaryPk(Long idbeneficiaryPk) {
        this.idbeneficiaryPk = idbeneficiaryPk;
    }

    public BigDecimal getAmountpaidNum() {
        return amountpaidNum;
    }

    public void setAmountpaidNum(BigDecimal amountpaidNum) {
        this.amountpaidNum = amountpaidNum;
    }

    @Override
    public String toString() {
        return "LogPaymentCache{" +
                "idpaymentorderPk=" + idpaymentorderPk +
                ", idpaymentNum=" + idpaymentNum +
                ", stateChr='" + stateChr + '\'' +
                ", idbeneficiaryPk=" + idbeneficiaryPk +
                ", amountpaidNum=" + amountpaidNum +
                '}';
    }
}
