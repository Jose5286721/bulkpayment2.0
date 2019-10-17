package py.com.global.spm.model.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.jboss.logging.Logger;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.dto.PaymentOrderDto;

/**
 * @author R2
 */
public class PaymentOrderCache {
	private static PaymentOrderCache instance;
	private Hashtable<Long, PaymentOrderDto> paymentOrderHash;
	private final Logger log = Logger.getLogger(PaymentOrderCache.class);

	// Instanciar el Singleton
	static {
		instance = new PaymentOrderCache();
	}

	// Constructor (llamado una unica vez)
	private PaymentOrderCache() {
		log.debug("Instanciando...");
		paymentOrderHash = new Hashtable<Long, PaymentOrderDto>(128);
	}

	// Referencia del singleton
	synchronized public static PaymentOrderCache getInstance() {
		return instance;
	}

	/**
	 * Para obtener las ordenes de pago con timeOut.
	 * Retira del hash los op vencidos.
	 * 
	 * @param timeOut
	 *            en millisegundos
	 * @return Lista de ordenes de pago o null en caso que no exista ordenes de pagos vencidos.
	 */
	public List<PaymentOrderDto> removePaymentOrderTimeOut(Long timeOut) {
		List<PaymentOrderDto> opVencidos= new ArrayList<PaymentOrderDto>();
		Collection<PaymentOrderDto> opDto = paymentOrderHash.values();

		for (PaymentOrderDto paymentOrderDto : opDto) {
			if ((System.currentTimeMillis() - paymentOrderDto
					.getCreationTimeStamp()) > timeOut) {
				PaymentOrderDto op = paymentOrderHash.remove(paymentOrderDto
						.getPaymentOrder().getIdpaymentorderPk());
				op.setTimeout(true);//La op fue sacada por timeOut.
				opVencidos.add(op);
			}

		}

		if(opVencidos.size()>0)
		{
			return opVencidos;
		}else{
			return null;
		}
	}

	public PaymentOrderDto removePaymentOrder(PaymentOrderDto pod) {
		return paymentOrderHash.remove(pod.getPaymentOrder().getIdpaymentorderPk());
	}
	
	public PaymentOrderDto getPaymentOrder(Long idPaymentOrder) {
		return paymentOrderHash.get(idPaymentOrder);
	}

	public void setPaymentOrder(PaymentOrderDto paymentOrder) {
		paymentOrderHash.put(paymentOrder.getPaymentOrder()
				.getIdpaymentorderPk(), paymentOrder);
	}

	public boolean existLogpayment(Logpayment logpayment) {
		PaymentOrderDto dto = paymentOrderHash.get(logpayment
				.getIdpaymentorderPk());
		if (dto == null) {
			return false;
		} else {
			return dto.existLogpayment(logpayment);
		}
	}
	
	public boolean existPaymentOrder(Paymentorder paymentOrder){
		PaymentOrderDto dto = paymentOrderHash.get(paymentOrder.getIdpaymentorderPk());
		if(dto!=null){
			return true;
		}else{
			return false;
		}
	}

	public String listCachePaymentOrder() {
		
		StringBuilder result=new StringBuilder();
		Enumeration<PaymentOrderDto> listValores= paymentOrderHash.elements();
		result.append("HashSize="+paymentOrderHash.size()+",OP={");
		while(listValores.hasMoreElements()){
			PaymentOrderDto pod=listValores.nextElement();
			result.append(pod.getPaymentOrder().getIdpaymentorderPk()+",");
		}
		result.append("}");
		
		return result.toString();
	}

}
