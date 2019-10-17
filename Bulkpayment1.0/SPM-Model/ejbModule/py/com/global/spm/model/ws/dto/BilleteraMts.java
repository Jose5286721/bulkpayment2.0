package py.com.global.spm.model.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BilleteraMts implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6547825658221584652L;
	
	private String wallet;
	private Double amount;
	
	
	public BilleteraMts() {
		
	}
	public String getWallet() {
		return wallet;
	}
	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BilleteraMts [wallet=" + wallet + ", amount=" + amount + "]";
	}
	
	

}
