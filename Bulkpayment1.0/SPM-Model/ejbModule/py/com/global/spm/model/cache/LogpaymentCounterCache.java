package py.com.global.spm.model.cache;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.util.LogpaymentCounter;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class LogpaymentCounterCache {

	private Logger log = Logger.getLogger(LogpaymentCounterCache.class);
	private Hashtable<Long, LogpaymentCounter> cache;
	private static LogpaymentCounterCache instance;

	static {
		instance = new LogpaymentCounterCache();
	}

	private LogpaymentCounterCache() {
		super();
		this.cache = new Hashtable<Long, LogpaymentCounter>();
	}

	public static LogpaymentCounterCache getInstance() {
		return instance;
	}

	public void addPaymentOrder(Paymentorder po) {
		if (cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("PaymentOrder already added --> " + po);
		}
		log.debug("Adding to cache -- > " + po);
		cache.put(po.getIdpaymentorderPk(),
				new LogpaymentCounter(po.getIdpaymentorderPk(), false, 0));
	}

	synchronized public void increment(Paymentorder po) {
		if (!cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("Increment. PaymentOrder not exist --> " + po);
		}
		LogpaymentCounter paymentCounter = cache.get(po.getIdpaymentorderPk());
		if (!paymentCounter.incrementCounter()) {
			log.error("Increment. No se puede incrementar contador --> " + po
					+ ", index=" + paymentCounter.toString());
		}
		log.trace("Incrementing --> counter=" + paymentCounter.getCounter()
				+ ", " + po);
	}

	synchronized public void decrement(Paymentorder po) {
		if (!cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("Decrement. PaymentOrder not exist --> " + po);
		}
		LogpaymentCounter paymentCounter = cache.get(po.getIdpaymentorderPk());
		if (!paymentCounter.decrementCounter()) {
			log.error("Decrement. No se puede decrementar contador --> " + po
					+ ", index=" + paymentCounter.toString());
		}
		if (paymentCounter.getCounter().intValue() < 0) {
			log.warn("Decrement. Algo anda muy mal --> " + po + ", "
					+ paymentCounter.toString());
		}
		log.trace("Decrementing --> counter=" + paymentCounter.getCounter()
				+ ", " + po);
	}

	synchronized public void reset(Paymentorder po) {
		if (!cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("Reset. PaymentOrder not exist --> " + po);
		}
		cache.put(po.getIdpaymentorderPk(),
				new LogpaymentCounter(po.getIdpaymentorderPk(), false, 0));
	}

	synchronized public Integer getCounter(Paymentorder po) {
		if (!cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("GetCounter. PaymentOrder not exist --> " + po);
			return -1;
		}
		LogpaymentCounter paymentCounter = cache.get(po.getIdpaymentorderPk());
		return paymentCounter.getCounter();
	}

	synchronized public void prepareToRemove(Paymentorder po) {
		LogpaymentCounter paymentCounter = cache.get(po.getIdpaymentorderPk());
		if (paymentCounter == null) {
			log.error("prepareToRemove. LogpaymentCounter is null --> "
					+ paymentCounter);
			return;
		}
		paymentCounter.setRemove(true);
		cache.put(po.getIdpaymentorderPk(), paymentCounter);
	}

	synchronized public boolean isReadyToRemove(Paymentorder po) {
		LogpaymentCounter paymentCounter = cache.get(po.getIdpaymentorderPk());
		if (paymentCounter == null) {
			log.error("isReadyToRemove. LogpaymentCounter is null --> "
					+ paymentCounter);
			return true;
		}
		return paymentCounter.isRemove();
	}

	synchronized public void removePaymentOrder(Paymentorder po) {
		if (!cache.containsKey(po.getIdpaymentorderPk())) {
			log.warn("Remove. PaymentOrder already removed --> " + po);
		}
		log.debug("Removing to cache -- > " + po);
		cache.remove(po.getIdpaymentorderPk());
	}

}
