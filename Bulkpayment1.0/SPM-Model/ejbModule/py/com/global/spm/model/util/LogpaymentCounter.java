package py.com.global.spm.model.util;

import org.apache.log4j.Logger;

import py.com.global.spm.model.managers.LogpaymentManager;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class LogpaymentCounter {

	private final Logger log = Logger.getLogger(LogpaymentManager.class);

	private long idpaymentorder;
	private boolean remove;
	private Integer counter;

	public LogpaymentCounter(long idpaymentorder, boolean remove,
			Integer counter) {
		super();
		this.idpaymentorder = idpaymentorder;
		this.remove = remove;
		this.counter = counter;
	}

	public boolean isRemove() {
		return remove;
	}

	public long getIdpaymentorder() {
		return idpaymentorder;
	}

	public void setIdpaymentorder(long idpaymentorder) {
		this.idpaymentorder = idpaymentorder;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public boolean incrementCounter() {
		boolean success = false;
		try {
			counter++;
			success = true;
		} catch (Exception e) {
			log.error("Incrementing counter --> " + toString(), e);
		}
		return success;
	}

	public boolean decrementCounter() {
		boolean success = false;
		try {
			counter--;
			success = true;
		} catch (Exception e) {
			log.error("Decrementing counter --> " + toString(), e);
		}
		return success;
	}

	@Override
	public String toString() {
		return "LogpaymentCounter [idpaymentorder=" + idpaymentorder
				+ ", remove=" + remove + ", counter=" + counter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idpaymentorder ^ (idpaymentorder >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogpaymentCounter other = (LogpaymentCounter) obj;
		if (idpaymentorder != other.idpaymentorder)
			return false;
		return true;
	}

}
