package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.ReversalCache;
import py.com.global.spm.model.cache.dto.ReversalDto;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.LogpaymentManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.ReversionProcessManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.messages.ReversionProcessToUpdaterMessage;
import py.com.global.spm.model.systemparameters.ReversionProcessParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.SpmQueues;

/**
 * Clase encargada de la ejecución de las ordenes de pago.
 * 
 */
@Stateless
public class ReversionProcessManager implements ReversionProcessManagerLocal {
	public static long TIMEOUT = 180000;
	public static final long MAX_REVERSION_TIME = 24;

	private static final Logger log = Logger
			.getLogger(ReversionProcessManager.class);

	private JMSSender queueUpdaterRequest;

	@EJB
	PaymentOrderManagerLocal paymentOrderManager;

	@EJB
	LogpaymentManagerLocal logpaymentManager;

	@EJB
	BeneficiaryManagerLocal beneficiaryManager;

	@EJB
	SystemparameterManagerLocal systemParameterManager;

	private ReversalCache reversalCache;

	private JMSSender queueAsyncUpdater;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	public ReversionProcessManager() {

	}

	@PostConstruct
	public void init() {
		this.reversalCache = ReversalCache.getInstance();
		try {
			if (initialContext == null) {
				log.debug("Iniciando el contexto...");
				initialContext = new InitialContext();
			}
			// Iniciamos las colas.
			initDtq();

		} catch (NamingException e) {
			log.error("Fallo al cargar el contexto del Contenedor --> error="
					+ e.getMessage());
		} catch (Exception e) {
			log.error("Error al inicializar Charger->" + e.getMessage());
		}
	}

	private void initDtq() {
		try {
			// Levantar la cola para AsyncUpdater.
			if (queueAsyncUpdater == null)
				queueAsyncUpdater = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.ASYNC_UPDATER_QUEUE);

			if (!queueAsyncUpdater.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueAsyncUpdater.toString() + ", init="
						+ queueAsyncUpdater.init());

			// Levantar la cola para Updater.
			if (queueUpdaterRequest == null)
				queueUpdaterRequest = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.UPDATER_QUEUE);

			if (!queueUpdaterRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueUpdaterRequest.toString() + ", init="
						+ queueUpdaterRequest.init());

		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	@Override
	public boolean existLogpayment(Logpayment logpayment) {
		return this.reversalCache.existLogpayment(logpayment);

	}

	public ReversalDto removeReversalCache(ReversalDto logpayment) {
		return this.reversalCache.removePaymentOrder(logpayment);
	}

	public List<Logpayment> getPaymentsToReversion(Paymentorder paymentOrder) {

		// 1- Obtener pagos a revertir
		List<Logpayment> listaPago = this.logpaymentManager
				.getPaymentsToReversionByIdPaymentOrder(paymentOrder
						.getIdpaymentorderPk());
		// 2-Se agregan los registros al cache.
		if (listaPago != null && listaPago.size() > 0) {
			Hashtable<Long, Logpayment> paymentHash = new Hashtable<Long, Logpayment>();
			for (Logpayment logpayment : listaPago) {
				paymentHash.put(logpayment.getIdpaymentNum(), logpayment);
			}

			ReversalDto dto = new ReversalDto(paymentOrder, paymentHash);
			this.reversalCache.setPaymentOrder(dto);

		}
		return listaPago;
	}

	@Override
	public ReversalDto updateReversalCache(Logpayment payment) {
		// 1-Se actualiza el cache de pagos.
		ReversalDto dto = this.reversalCache.getPaymentOrder(payment
				.getIdpaymentorderPk());

		boolean procesado = dto.setLogPayment(payment);

		if (procesado) {
			// 2-Se envia actualizado en la base de datos.
			if (!sendAsyncUpdater(payment)) {
				log.error("Problemas para enviar al AsyncUpdater-->" + payment);
			}
		}

		return dto;
	}

	private boolean sendAsyncUpdater(Logpayment tpu) {
		boolean ready = false;
		if (queueAsyncUpdater != null && queueAsyncUpdater.isConnected()) {
			log.trace("Mensaje a AsyncUpdater --> " + tpu);
			if (!(ready = queueAsyncUpdater.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.ASYNC_UPDATER_QUEUE);
				try {
					queueAsyncUpdater.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.ASYNC_UPDATER_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.UPDATER_QUEUE);
		}
		return ready;
	}

	private boolean sendRequestUpdater(ReversionProcessToUpdaterMessage tpu) {
		boolean ready = false;
		if (queueUpdaterRequest != null && queueUpdaterRequest.isConnected()) {
			log.trace("Mensaje a Updater desde TransferProcessManager--> "
					+ tpu);
			if (!(ready = queueUpdaterRequest.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.UPDATER_QUEUE);
				try {
					queueUpdaterRequest.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.UPDATER_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ tpu);
		}
		return ready;
	}

	public String listCachePaymentOrder() {
		return this.reversalCache.listCachePaymentOrder();
	}

	@Override
	public void cleanCachePaymentOrder() {

		// 1- Buscar las ordenes de pagos con timeOut.
		Long timeOut = this.systemParameterManager.getParameter(
				ReversionProcessParameters.TIMEOUT, TIMEOUT);
		List<ReversalDto> arrayOp = this.reversalCache
				.removePaymentOrderTimeOut(timeOut);

		if (arrayOp != null) {
			log.trace("Hay " + arrayOp.size()
					+ " ordenes de pago que estan vencidas.");
			// 2- Ordenes de pago con TimeOut se deben poner los pagos con
			// estado
			// Error
			for (ReversalDto opDto : arrayOp) {
				// 3- Enviar la orden de pago al UPDATER.
				ReversionProcessToUpdaterMessage tpu = new ReversionProcessToUpdaterMessage();
				tpu.setPaymentorder(opDto.getPaymentOrder());
				if (!this.sendRequestUpdater(tpu)) {
					log.error("Fallo al envio de mensaje al UPDATER-->" + tpu);
				}
			}
		} else {
			log.debug("No existen ordenes de pago expirados!!");
		}

	}

	@Override
	public boolean existPaymentOrder(Paymentorder paymentOrder) {
		return this.reversalCache.existPaymentOrder(paymentOrder);
	}
	
	@Override
	public boolean validateTimePaymentOrder(Paymentorder paymentOrder) {
		// TODO agregar validacion de tiempo maximo
		Long reversionTime = systemParameterManager.getParameter(
				ReversionProcessParameters.MAX_REVERSION_TIME,
				MAX_REVERSION_TIME);

		if (paymentOrder != null) {
			return this.validateTime(paymentOrder.getCreationdateTim(),
					reversionTime);
		} else {
			log.error("La orden de pago vino null.");
			return false;
		}
	}

	/**
	 * Valida si una fecha y hora ya paso un limite de tiempo.
	 * 
	 * @param fechaValida
	 *            Fecha a validar.
	 * @param cantHours
	 *            Cantidad de horas que la fecha es valida.
	 * @return False si ya pasaron la cantidad de horas recibida como parametro
	 *         a partir de la fechaValida. True en caso contrario.
	 */
	private boolean validateTime(Timestamp fechaValida, Long cantHours) {
		Long fechaMillisLimite = fechaValida.getTime() + (cantHours * 3600000);//

		if (System.currentTimeMillis() < fechaMillisLimite) {
			return true;
		} else {
			return false;
		}
	}

	@PreDestroy
	public void close() {
		try {
			if (queueAsyncUpdater != null) {
				queueAsyncUpdater.close();
			}
			if (queueUpdaterRequest != null) {
				queueUpdaterRequest.close();
			}

		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
