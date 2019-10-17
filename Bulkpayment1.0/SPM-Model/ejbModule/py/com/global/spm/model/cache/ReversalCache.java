package py.com.global.spm.model.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.jboss.logging.Logger;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.dto.ReversalDto;

/**
 * @author R2
 */
public class ReversalCache {
	private static ReversalCache instance;
	private Hashtable<Long, ReversalDto> paymentOrderHash;
	private final Logger log = Logger.getLogger(ReversalCache.class);

	// Instanciar el Singleton
	static {
		instance = new ReversalCache();
	}

	// Constructor (llamado una unica vez)
	private ReversalCache() {
		log.debug("Instanciando...");
		paymentOrderHash = new Hashtable<Long, ReversalDto>(128);
	}

	// Referencia del singleton
	synchronized public static ReversalCache getInstance() {
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
	public List<ReversalDto> removePaymentOrderTimeOut(Long timeOut) {
		List<ReversalDto> opVencidos= new ArrayList<ReversalDto>();
		Collection<ReversalDto> opDto = paymentOrderHash.values();

		for (ReversalDto paymentOrderDto : opDto) {
			if ((System.currentTimeMillis() - paymentOrderDto
					.getCreationTimeStamp()) > timeOut) {
				ReversalDto op = paymentOrderHash.remove(paymentOrderDto
						.getPaymentOrder().getIdpaymentorderPk());
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

	public ReversalDto removePaymentOrder(ReversalDto pod) {
		return paymentOrderHash.remove(pod.getPaymentOrder().getIdpaymentorderPk());
	}
	
	public ReversalDto getPaymentOrder(Long idPaymentOrder) {
		return paymentOrderHash.get(idPaymentOrder);
	}

	public void setPaymentOrder(ReversalDto paymentOrder) {
		paymentOrderHash.put(paymentOrder.getPaymentOrder()
				.getIdpaymentorderPk(), paymentOrder);
	}

	public boolean existLogpayment(Logpayment logpayment) {
		ReversalDto dto = paymentOrderHash.get(logpayment
				.getIdpaymentorderPk());
		if (dto == null) {
			return false;
		} else {
			return dto.existLogpayment(logpayment);
		}
	}
	
	public boolean existPaymentOrder(Paymentorder paymentOrder){
		ReversalDto dto = paymentOrderHash.get(paymentOrder.getIdpaymentorderPk());
		if(dto!=null){
			return true;
		}else{
			return false;
		}
	}

	public String listCachePaymentOrder() {
		
		StringBuilder result=new StringBuilder();
		Enumeration<ReversalDto> listValores= paymentOrderHash.elements();
		result.append("HashSize="+paymentOrderHash.size()+",OP={");
		while(listValores.hasMoreElements()){
			ReversalDto pod=listValores.nextElement();
			result.append(pod.getPaymentOrder().getIdpaymentorderPk()+",");
		}
		result.append("}");
		
		return result.toString();
	}

}
