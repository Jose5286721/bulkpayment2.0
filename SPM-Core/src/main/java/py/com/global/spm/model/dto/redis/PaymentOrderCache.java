package py.com.global.spm.model.dto.redis;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import py.com.global.spm.domain.entity.Paymentorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@RedisHash("PaymentOrderCache")
public class PaymentOrderCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746980864963862473L;

	@Id
	private Long idpaymentorderPk;
	private Long idcompanyPk;
	private String stateChr;
	private int cantSuccess = 0; // Cantidad de pagos exitosos.
	private int cantTotal = -1; // Cantidad total de pagos.
	private int cantPending = -1; // Cantidad pagos pendientes.
	private long creationTimeStamp = 0; // TimeStamp de creacion de objeto.
	private boolean isTimeout = false;
	private BigDecimal amountPayOp = BigDecimal.ZERO;
	private boolean isReintento;

	public PaymentOrderCache(Paymentorder paymentOrder, int totalLogPayment,boolean isReintento) {
		this.idpaymentorderPk=paymentOrder.getIdpaymentorderPk();
		this.stateChr = paymentOrder.getStateChr();
		this.idcompanyPk = paymentOrder.getCompany().getIdcompanyPk();
		this.cantSuccess = 0;
		this.cantPending = totalLogPayment;
		this.cantTotal = totalLogPayment;
		this.amountPayOp = paymentOrder.getAmountpaidNum();
		this.creationTimeStamp = System.currentTimeMillis();
		this.isReintento=isReintento;
	}
	public PaymentOrderCache(Long idcompanyPk){
		this.idcompanyPk=idcompanyPk;
	}

	public PaymentOrderCache(){

	}

	public Long getIdpaymentorderPk() {
		return idpaymentorderPk;
	}

	public void setIdpaymentorderPk(Long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public Long getIdcompanyPk() {
		return idcompanyPk;
	}

	public void setIdcompanyPk(Long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public String getStateChr() {
		return stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public int getCantSuccess() {
		return cantSuccess;
	}

	public void setCantSuccess(int cantSuccess) {
		this.cantSuccess = cantSuccess;
	}

	public int getCantTotal() {
		return cantTotal;
	}

	public void setCantTotal(int cantTotal) {
		this.cantTotal = cantTotal;
	}

	public int getCantPending() {
		return cantPending;
	}

	public void setCantPending(int cantPending) {
		this.cantPending = cantPending;
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(long creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	public boolean isTimeout() {
		return isTimeout;
	}

	public void setTimeout(boolean timeout) {
		isTimeout = timeout;
	}

	public BigDecimal getAmountPayOp() {
		return amountPayOp;
	}

	public void setAmountPayOp(BigDecimal amountPayOp) {
		this.amountPayOp = amountPayOp;
	}

	public boolean isReintento() {
		return isReintento;
	}

	public void setReintento(boolean reintento) {
		isReintento = reintento;
	}

	@Override
	public String toString() {
		return "PaymentOrderCache{" +
				"idpaymentorderPk=" + idpaymentorderPk +
				", idcompanyPk=" + idcompanyPk +
				", stateChr='" + stateChr + '\'' +
				", cantSuccess=" + cantSuccess +
				", cantTotal=" + cantTotal +
				", cantPending=" + cantPending +
				", creationTimeStamp=" + creationTimeStamp +
				", isTimeout=" + isTimeout +
				", amountPayOp=" + amountPayOp +
				", isReintento=" + isReintento +
				'}';
	}
}
