package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import py.com.global.spm.entities.Beneficiary;
import py.com.global.spm.entities.Company;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.Paymentorderdetail;
import py.com.global.spm.model.cache.PaymentOrderCache;
import py.com.global.spm.model.cache.dto.PaymentOrderDto;
import py.com.global.spm.model.eventcodes.TransferProcessEventCodes;
import py.com.global.spm.model.exceptions.LogPaymentException;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.LogpaymentManagerLocal;
import py.com.global.spm.model.interfaces.MTSTransferInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.TransferProcessManagerLocal;
import py.com.global.spm.model.messages.TransferProcessToUpdaterMessage;
import py.com.global.spm.model.systemparameters.TransferProcessParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;

/**
 * Clase encargada de la ejecucion de las ordenes de pago.
 * 
 */
@Stateless
public class TransferProcessManager implements TransferProcessManagerLocal {
	public static long TIMEOUT = 180000;

	private static final Logger log = Logger
			.getLogger(TransferProcessManager.class);

	private JMSSender queueUpdaterRequest;

	@EJB
	PaymentOrderManagerLocal paymentOrderManager;

	@EJB
	LogpaymentManagerLocal logpaymentManager;

	@EJB
	BeneficiaryManagerLocal beneficiaryManager;

	@EJB
	MTSTransferInterfaceManagerLocal mtsManager;

	@EJB
	CompanyManagerLocal companyManager;

	@EJB
	SystemparameterManagerLocal systemParameterManager;

	private PaymentOrderCache paymentOrderCache;

	private JMSSender queueAsyncUpdater;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	public TransferProcessManager() {

	}

	@PostConstruct
	public void init() {
		this.paymentOrderCache = PaymentOrderCache.getInstance();
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
	public List<Logpayment> generateLogPayment(Paymentorder paymentorder) {
		// 1- Generar los logPayment a partir de la orden de pago.
		// 1.1 -Insertar en la BD el logPayment
		List<Logpayment> listaPago = this.persistLogpayments(paymentorder);

		// 2-Se agregan los registros al cache.
		Hashtable<Long, Logpayment> paymentHash = new Hashtable<Long, Logpayment>();
		for (Logpayment logpayment : listaPago) {
			paymentHash.put(logpayment.getIdpaymentNum(), logpayment);
		}

		PaymentOrderDto dto = new PaymentOrderDto(paymentorder, paymentHash);
		dto.setReintento(false);
		this.paymentOrderCache.setPaymentOrder(dto);

		return listaPago;
	}

	@Override
	public boolean existLogpayment(Logpayment logpayment) {
		return this.paymentOrderCache.existLogpayment(logpayment);

	}

	public PaymentOrderDto removePaymentOrderCache(PaymentOrderDto logpayment) {
		return this.paymentOrderCache.removePaymentOrder(logpayment);
	}

	@Override
	public PaymentOrderDto updatePaymentOrderCache(Logpayment payment) {
		// 1-Se actualiza el cache de pagos.
		PaymentOrderDto dto = this.paymentOrderCache.getPaymentOrder(payment
				.getIdpaymentorderPk());

		dto.setLogPayment(payment);

		// 2-Se envia actualiza en la base de datos.
		if (!sendAsyncUpdater(payment)) {
			log.error("Problemas para enviar al AsyncUpdater-->" + payment);
		}

		return dto;
	}

	private List<Logpayment> persistLogpayments(Paymentorder paymentorder) {
		List<Paymentorderdetail> detallesOP = paymentOrderManager
				.getPaymentorderdetails(paymentorder.getIdpaymentorderPk());

		List<Logpayment> pagosList = new ArrayList<Logpayment>();

		for (Paymentorderdetail draft : detallesOP) {
			Logpayment logpay = new Logpayment();
			logpay.setAmountNum(draft.getAmountNum());
			logpay.setIdbeneficiaryPk(draft.getId().getIdbeneficiaryPk());
			logpay.setIdcompanyPk(paymentorder.getIdcompanyPk());
			logpay.setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
			logpay.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
			logpay.setIdeventcodeNum(TransferProcessEventCodes.SUCCESS
					.getIdEventCode());
			logpay.setCreationdateTim(new Timestamp(System.currentTimeMillis()));
			logpay.setAmountpaidNum(0.0);
			logpay.setStateChr(PaymentStates.EN_PROCESO);
			// Obtengo el numero de telefono.
			Beneficiary beneficiario = beneficiaryManager
					.getBeneficiaryById(draft.getId().getIdbeneficiaryPk());
			logpay.setPhonenumberChr(beneficiario.getPhonenumberChr());
			//TODO agregar nro de ci del beneficiario
			logpay.setSubscriberDocNumChr(beneficiario.getSubscriberciChr());
			pagosList.add(logpay);
		}

		try {
			logpaymentManager.persistPaymentList(pagosList);
		} catch (LogPaymentException e) {
			log.error("Evento:" + e.getIdEvent() + ", Mensaje:"
					+ e.getMessage());
		}

		return pagosList;
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

	private boolean sendRequestUpdater(TransferProcessToUpdaterMessage tpu) {
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
		return this.paymentOrderCache.listCachePaymentOrder();
	}

	@Override
	public List<Logpayment> getRetryLogPayment(Paymentorder paymentorder) {

		// 1- Obtener los logPayment a partir de la orden de pago.
		List<Logpayment> listaPago = logpaymentManager
				.getRetryLogPayment(paymentorder.getIdpaymentorderPk());

		if (listaPago != null && listaPago.size() > 0) {
			// 2-Se agregan los registros al cache.
			Hashtable<Long, Logpayment> paymentHash = new Hashtable<Long, Logpayment>();
			for (Logpayment logpayment : listaPago) {
				paymentHash.put(logpayment.getIdpaymentNum(), logpayment);
			}

			PaymentOrderDto dto = new PaymentOrderDto(paymentorder, paymentHash);
			dto.setReintento(true);
			this.paymentOrderCache.setPaymentOrder(dto);

			return listaPago;
		} else {
			return null;
		}

	}

	@Override
	public void cleanCachePaymentOrder() {

		// 1- Buscar las ordenes de pagos con timeOut.
		Long timeOut = this.systemParameterManager.getParameter(
				TransferProcessParameters.TIMEOUT, TIMEOUT);
		List<PaymentOrderDto> arrayOp = this.paymentOrderCache
				.removePaymentOrderTimeOut(timeOut);

		if (arrayOp != null) {
			log.trace("Hay " + arrayOp.size()
					+ " ordenes de pago que estan vencidas.");
			// 2- Ordenes de pago con TimeOut se deben poner los pagos con
			// estado
			// Error
			for (PaymentOrderDto opDto : arrayOp) {
				Collection<Logpayment> logPayments = opDto.getLogPayment();
				for (Logpayment logpayment : logPayments) {
					// 2.1 Cambiar de estado las que se encuentren con estado
					// pendiente y actualizar BD.
					if (logpayment.getStateChr().equalsIgnoreCase(
							PaymentStates.EN_PROCESO)) {
						logpayment
								.setIdprocessPk(SpmProcesses.PAYMENTORDER_CLEANER);
						logpayment.setStateChr(PaymentStates.ERROR);
						logpayment
								.setIdeventcodeNum(TransferProcessEventCodes.TIMEOUT
										.getIdEventCode());
						if (!logpaymentManager.updateLogpayment(logpayment)) {
							log.warn("UPS!!! No se actualizo el logpayment -->"
									+ logpayment);
						}

					}
				}

				// 3- Enviar la orden de pago al UPDATER.
				TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
				tpu.setPaymentorder(opDto.getPaymentOrder());
				List<Logpayment> logs = new ArrayList<Logpayment>(logPayments);
				tpu.setLogpaymentList(logs);
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
		return this.paymentOrderCache.existPaymentOrder(paymentOrder);
	}

	/**
	 * Retorna el monto con la comision incluida, dependiendo del comercio.
	 */
	private Double getMontoComisionIncluida(long idCompany, Double monto) {
		Company empresa = this.companyManager.getCompanyById(idCompany);
		Double comision = empresa.getPercentcommissionNum();
		if (comision > 0) {
			return (monto + (monto * (comision / 100.0)));
		} else {
			return monto;
		}
	}

	@Override
	public boolean validateAmountPaymentOrder(Paymentorder paymentOrder) {
		Double saldoCompany = this.mtsManager.getBalanceCompany(paymentOrder
				.getIdcompanyPk());
		if (saldoCompany == -1) {
			log.warn("El monto es -1.Posiblemente ocurrio un error.");
		} else {
			if (paymentOrder.getStateChr().equalsIgnoreCase(
					PaymentOrderStates.PENDIENTE_DE_PAGO)
					|| paymentOrder.getStateChr().equalsIgnoreCase(
							PaymentOrderStates.EN_PROCESO)) {
				Double montoPago = this.getMontoComisionIncluida(
						paymentOrder.getIdcompanyPk(),
						paymentOrder.getAmountNum());
				log.debug("Consultando saldo: idCompany->"
						+ paymentOrder.getIdcompanyPk() + ",MontoOP->"
						+ montoPago + ",SaldoCompany->" + saldoCompany);
				if (montoPago <= saldoCompany) {
					return true;
				} else {
					log.warn("La empresa no tiene saldo suficente para procesar el pago "
							+ "--> saldoEmpresa="
							+ saldoCompany
							+ ", "
							+ paymentOrder);
				}
			} else if (paymentOrder.getStateChr().equalsIgnoreCase(
					PaymentOrderStates.NO_PAGADA)
					|| paymentOrder.getStateChr().equalsIgnoreCase(
							PaymentOrderStates.PARCIALMENTE_PAGADA)) {
				Double saldoOp = paymentOrder.getAmountNum()
						- paymentOrder.getAmountpaidNum();
				Double montoPago = this.getMontoComisionIncluida(
						paymentOrder.getIdcompanyPk(), saldoOp);
				log.debug("Consultando saldo: idCompany->"
						+ paymentOrder.getIdcompanyPk() + ",MontoOP->"
						+ montoPago + ",SaldoCompany->" + saldoCompany);
				if (montoPago <= saldoCompany) {
					return true;
				}
			} else {
				log.warn("Estado de orden de pago invalido -->" + paymentOrder);
			}
		}
		return false;
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