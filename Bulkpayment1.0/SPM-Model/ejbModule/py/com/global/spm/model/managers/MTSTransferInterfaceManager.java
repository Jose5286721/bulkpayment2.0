package py.com.global.spm.model.managers;

import java.sql.Timestamp;
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
import py.com.global.spm.entities.Logmts;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.model.eventcodes.MTSTransferProcessEventCodes;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.LogmtsManagerLocal;
import py.com.global.spm.model.interfaces.MTSTransferInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.MtsManagerLocal;
import py.com.global.spm.model.interfaces.PaymentordertypeManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.ws.dto.BilleteraMts;
import py.com.global.spm.model.ws.dto.SalaryPaymentResponse;
import py.com.global.spm.model.ws.dto.TransactionInfoResponse;

/**
 * Session Bean implementation class MTSTransferInteface
 */
@Stateless
public class MTSTransferInterfaceManager implements
		MTSTransferInterfaceManagerLocal {

	public static final Logger log = Logger
			.getLogger(MTSTransferInterfaceManager.class);

	public static final String LIMITED_AMOUNT = "10000";

	@EJB
	SystemparameterManagerLocal systemParameter;
	
	@EJB
	SystemparameterManagerLocal systemParameterManager;

	@EJB
	MtsManagerLocal mtsMng;

	@EJB
	public SystemparameterManagerLocal systemParameterMgr;

	@EJB
	public CompanyManagerLocal companyMgr;

	@EJB
	public BeneficiaryManagerLocal beneficiaryMgr;

	@EJB
	LogmtsManagerLocal logmtsMng;

	@EJB
	PaymentordertypeManagerLocal paymentOrderTypeManager;

	private JMSSender queueAsyncUpdater;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	/**
	 * Default constructor.
	 */
	public MTSTransferInterfaceManager() {

	}

	@PostConstruct
	public void init() {
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

	/*
	 * MTSTransferProcessEventCodes.NO_COINCIDE_CEDULA.getIdEventCode() == -2
	 * MTSTransferProcessEventCodes.CLIENTE_NO_EXISTE.getIdEventCode() == -4
	 * SUCCESS(0L, "Success"), NO_COINCIDE_CEDULA(-2L,
	 * "No coincide cedula del suscriptor"),
	 * CLIENTE_NO_EXISTE(-4L,"Cliente no existe en MTS"),
	 * ERROR_VALIDACION(-5L,"Error al validar suscriptor en MTS");
	 */
	@Override
	public void processPayment(Logpayment payment) {
		String isDummy;
		// 0.1 Verificar el estado.
		if (payment.getStateChr().equalsIgnoreCase(PaymentStates.EN_PROCESO)) {
			// 1- Obtener de systemParameters monto para particionar el pago en
			// transacciones.
			String montoLimite = systemParameter.getParameter(
					MtsInterfaceParameters.LIMITED_AMOUNT, LIMITED_AMOUNT);
			Double amountDouble = Double.valueOf(montoLimite);
			Double[] montos = this.getAmounts(payment.getAmountNum(),
					amountDouble);
			// 2- Generar los registros para LogMts.
			Double montoPagado = 0.0;
			String idSesion = null;
			try {
				// 3-Inicio sesion para ejecutar pagos.
				idSesion = mtsMng.bind(payment.getIdcompanyPk());
				Company company = companyMgr.getCompanyById(payment
						.getIdcompanyPk());
				for (int i = 0; i < montos.length; i++) {
					// 4- Persistir cada registro
					Beneficiary beneficiario = beneficiaryMgr
							.getBeneficiaryById(payment.getIdbeneficiaryPk());
					Logmts logmts = new Logmts();
					logmts.setAmountNum(montos[i]);// Monto dividido.
					logmts.setIdbeneficiaryPk(payment.getIdbeneficiaryPk());
					logmts.setIdcompanyPk(payment.getIdcompanyPk());
					logmts.setIdeventcodeNum(0);// Estado 0 es que todo esta
												// bien.
					logmts.setIdpaymentNum(payment.getIdpaymentNum());
					logmts.setIdpaymentorderPk(payment.getIdpaymentorderPk());
					logmts.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
					logmts.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT);
					logmts.setRequestdateTim(new Timestamp(System
							.currentTimeMillis()));
					logmts.setIdsessionmtsChr(idSesion);

					// Cambio hakure. Obtener Rol,wallet y currency de la tabla
					// beneficiary.
					logmts.setRequestChr("idSession=" + idSesion
							+ ",PhoneNumber=" + payment.getPhonenumberChr()
							+ ",Rol=" + beneficiario.getRolspChr() + ",Monto="
							+ montos[i] + ",Currency="
							+ beneficiario.getCurrencyChr() + ",Band="
							+ company.getMtsfield1Chr() + ",Wallet="
							+ beneficiario.getWalletChr());
					logmts.setMethodChr(SpmConstants.MTS_SALARYPAYMENT);

					// 5- Ejecutar cada transaccion.
					try {
						// 1- Obtener datos para conexion.

						SalaryPaymentResponse response = mtsMng.salaryPayment(
								idSesion, payment.getPhonenumberChr(),
								beneficiario.getRolspChr(), montos[i],
								beneficiario.getCurrencyChr(),
								company.getMtsfield1Chr(),
								beneficiario.getWalletChr());

						// 6- Enviar actualizacion de registro al AsyncUpdater.
						logmts.setResponsedateTim(new Timestamp(System
								.currentTimeMillis()));
						logmts.setIdtrxmtsChr(response.getNroTransaction()
								.toString());
						logmts.setResponseChr(response.getResponseStr());
						logmts.setStateChr(SpmConstants.SUCCESS);
						
						String key1 = systemParameterManager
								.getParameterValue(MtsInterfaceParameters.TRX_INFO_KEY1);
						log.info("key1: " + key1);
						String key2 = systemParameterManager
								.getParameterValue(MtsInterfaceParameters.TRX_INFO_KEY2);
						log.info("key2: " + key2);
						String key3 = systemParameterManager
								.getParameterValue(MtsInterfaceParameters.TRX_INFO_KEY1);
						log.info("key3: " + key3);

						String pot = paymentOrderTypeManager
								.getPaymentOrderType(payment
										.getIdpaymentorderPk());
						String value1 = pot;
						String value2 = company.getDescriptionChr();
						

						String value3= "";
						if (isCompanyReposicionGuardaSaldo(payment.getIdcompanyPk()))
						{
							value3 = "NroRef: " + beneficiario.getGenerico1Chr() + "; TicketSiebel: " + beneficiario.getGenerico2Chr();
						}
						isDummy = systemParameterMgr.getParameter(
								MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);
						//Si no es un dummy se ejecuta
						if (!isDummy.equalsIgnoreCase(SpmConstants.YES)) {
							TransactionInfoResponse tiResp = mtsMng
									.insertTransactionInfo(idSesion, response
													.getNroTransaction().toString(), key1,
											value1,key2,value2,key3,value3);
						}
						// TODO: RESPUESTA DE TRX INFO IMPRIMIR EN EL LOG
						log.info("INSERT INTO TRANSACTION INFO --> [Key1: "
								+ key1 + " Value1: " + value1 + ", Key2: " + key2
								+ " Value2: " + value2 + ", Key3: " + key3
								+ " Value3: " + value3 + "]");

					} catch (MTSInterfaceException e) {
						logmts.setResponsedateTim(new Timestamp(System
								.currentTimeMillis()));
						logmts.setStateChr(SpmConstants.ERROR);
						logmts.setIdeventcodeNum(e.getIdEvent());
						log.error("Error mts:IdEvent->" + e.getIdEvent()
								+ ",msg=" + e.getMessage());
					}
					
					// 7-Contabilizamos el monto pagado.
					if (logmts.getStateChr().equalsIgnoreCase(
							SpmConstants.SUCCESS)) {
						montoPagado += montos[i];
					}
					// 8-Enviar a AsyncUpdater
					this.sendAsyncUpdater(logmts);

				}

				// 9- Actualizar logpayment con datos actualizados.
				// 9.1- Verificar si el pago se queda con estado parcial.
				String estadoOp = PaymentStates.SATISFACTORIO;
				if (montoPagado.compareTo(0.0) == 0) {
					estadoOp = PaymentStates.ERROR;
				} else if (montoPagado.compareTo(payment.getAmountNum()) < 0) {
					estadoOp = PaymentStates.PARCIALMENTE_SATISFACTORIO;
				}
				// 9.2- Actualizar el monto pagado y el estado.
				payment.setStateChr(estadoOp);
				payment.setAmountpaidNum(montoPagado);
				payment.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
			} catch (Exception e1) {
				payment.setStateChr(PaymentStates.ERROR);
				payment.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
				payment.setIdeventcodeNum(MTSTransferProcessEventCodes.CONNECTION_PROBLEM
						.getIdEventCode());
				log.error("Error al ejecutar pago en MTS->" + e1.getMessage(),
						e1);

			} finally {
				if (idSesion != null) {
					// 10- Me desconecto del MTS.
					mtsMng.unBind(payment.getIdcompanyPk(), idSesion);
				}
			}

		} else if (payment.getStateChr().equalsIgnoreCase(PaymentStates.ERROR)
				|| payment.getStateChr().equalsIgnoreCase(
						PaymentStates.PARCIALMENTE_SATISFACTORIO)) {
			// Esta parte es para el reintento de pago.
			// 1- Buscar transacciones mts del pago con estado error.
			List<Logmts> retryLogmts = this.logmtsMng.findLogmtsToRetry(payment
					.getIdpaymentNum());
			// 2- Efectuar transaccion MTS.
			// 2.1-Inicio sesion para ejecutar pagos.
			String idSesion = null;
			try {
				if (retryLogmts != null && retryLogmts.size() > 0) {
					Double montoPagado = payment.getAmountpaidNum();
					idSesion = mtsMng.bind(payment.getIdcompanyPk());
					Company company = companyMgr.getCompanyById(payment
							.getIdcompanyPk());
					for (Logmts l : retryLogmts) {
						Beneficiary beneficiario = beneficiaryMgr
								.getBeneficiaryById(l.getIdbeneficiaryPk());
						Logmts log2 = new Logmts();
						log2.setIdlogmtsretryPk(l.getIdlogmtsPk());
						log2.setAmountNum(l.getAmountNum());// Monto dividido.
						log2.setIdbeneficiaryPk(l.getIdbeneficiaryPk());
						log2.setIdcompanyPk(l.getIdcompanyPk());
						log2.setIdeventcodeNum(0);// Estado 0 es que todo esta
													// bien.
						log2.setIdpaymentNum(l.getIdpaymentNum());
						log2.setIdpaymentorderPk(l.getIdpaymentorderPk());
						log2.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
						log2.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT);// Para
																				// evitar
																				// pagos
																				// repetidos.
						log2.setRequestdateTim(new Timestamp(System
								.currentTimeMillis()));
						log2.setIdsessionmtsChr(idSesion);

						// Cambio hakure. Obtener Rol,wallet y currency de la
						// tabla
						// beneficiary.
						log2.setRequestChr("idSession=" + idSesion
								+ ",PhoneNumber=" + payment.getPhonenumberChr()
								+ ",Rol=" + beneficiario.getRolspChr()
								+ ",Monto=" + l.getAmountNum() + ",Currency="
								+ beneficiario.getCurrencyChr() + ",Band="
								+ company.getMtsfield1Chr() + ",Wallet="
								+ beneficiario.getWalletChr());
						log2.setMethodChr(SpmConstants.MTS_SALARYPAYMENT);

						// 3- Ejecutar cada transaccion.
						try {
							// 3.1- Obtener datos para conexion.

							SalaryPaymentResponse response = mtsMng
									.salaryPayment(idSesion,
											payment.getPhonenumberChr(),
											beneficiario.getRolspChr(),
											l.getAmountNum(),
											beneficiario.getCurrencyChr(),
											company.getMtsfield1Chr(),
											beneficiario.getWalletChr());

							// 3.2- Enviar actualizacion de registro al
							// AsyncUpdater.
							log2.setResponsedateTim(new Timestamp(System
									.currentTimeMillis()));
							log2.setIdtrxmtsChr(response.getNroTransaction()
									.toString());
							log2.setResponseChr(response.getResponseStr());
							log2.setStateChr(SpmConstants.SUCCESS);
						} catch (MTSInterfaceException e) {
							log2.setResponsedateTim(new Timestamp(System
									.currentTimeMillis()));
							log2.setStateChr(SpmConstants.ERROR);
							log2.setIdeventcodeNum(e.getIdEvent());
							log.error("Error mts:IdEvent->" + e.getIdEvent()
									+ ",msg=" + e.getMessage());
						}
						// 4-Contabilizamos el monto pagado.
						if (log2.getStateChr().equalsIgnoreCase(
								SpmConstants.SUCCESS)) {
							montoPagado += l.getAmountNum();
						}
						// 5-Enviar a AsyncUpdater
						this.sendAsyncUpdater(log2);

						// 6- Cambiar operacion que se aplico al registro.
						l.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT_RETRY);
						this.sendAsyncUpdater(l);

					}// Fin For_LogRetry

					// 6- Actualizar logpayment con datos actualizados.
					// 6.1- Verificar si el pago se queda con estado parcial.
					String estadoOp = PaymentStates.SATISFACTORIO;
					if (montoPagado.compareTo(payment.getAmountpaidNum()) == 0) {
						estadoOp = payment.getStateChr();
					} else if (montoPagado.compareTo(payment.getAmountNum()) < 0) {
						estadoOp = PaymentStates.PARCIALMENTE_SATISFACTORIO;
					}
					// 6.2- Actualizar el monto pagado y el estado.
					payment.setStateChr(estadoOp);
					payment.setAmountpaidNum(montoPagado);
					payment.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
				} else {
					log.warn("No hay registros de logmts para reintentar el pago->"
							+ payment);
				}
			} catch (Exception e1) {
				log.error(
						"Error al ejecutar reintento de pago en MTS->"
								+ e1.getMessage(), e1);

			} finally {
				if (idSesion != null) {
					// 7- Me desconecto del MTS.
					mtsMng.unBind(payment.getIdcompanyPk(), idSesion);
				}
			}
		} else {
			log.warn("Estado de pago invalido -->" + payment);
		}

	}

	@SuppressWarnings("static-access")
	public boolean isCompanyReposicionGuardaSaldo(Long idcompanyPk) {

		String idCompanyForReposicionGuardaSaldo = systemParameterMgr
				.getParameterValue(MtsInterfaceParameters.ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO);
		log.info("idEmpresaActual --> " + idcompanyPk
				+ "  idCompanyForReposicionGuardaSaldo --> " + idCompanyForReposicionGuardaSaldo);
		Boolean res = false;

		String idcompany = Long.toString(idcompanyPk);
		// String idcompany = null;
		// idcompany.valueOf(idcompanyPk);
		String[] idCompanies = idCompanyForReposicionGuardaSaldo.split(",");
		for (String id : idCompanies) {
			if (id.equals(idcompany)) {
				res = true;
				log.info("[COMPANY REPOSICION GUARDA SALDO: " + res + "]");
				break;
			}
		}

		return res;
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

		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	@PreDestroy
	private void closeDtq() {
		// Cerrando colas de datos
		try {
			if (queueAsyncUpdater != null) {
				queueAsyncUpdater.close();
			}
		} catch (Exception e) {
			log.error("Fallo al cerrar cola --> queueAsyncUpdater", e);
		}
	}

	private boolean sendAsyncUpdater(Logmts tpu) {
		boolean ready = false;
		if (queueAsyncUpdater != null && queueAsyncUpdater.isConnected()) {
			log.trace("Mensaje a AsyncUpdater --> " + tpu);
			if (!(ready = queueAsyncUpdater.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.ASYNC_UPDATER_QUEUE);
				try {
					queueAsyncUpdater.close();
				} catch (Exception e) {
					log.error(
							"Fallo al cerrar cola. --> " + "cola="
									+ SpmQueues.ASYNC_UPDATER_QUEUE + ",Error="
									+ e.getCause(), e);
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.ASYNC_UPDATER_QUEUE);
		}
		return ready;
	}

	/**
	 * Calcula los montos para las solicitudes.
	 * 
	 * @param montoTotal
	 *            Monto total a dividir.
	 * @param montoLimite
	 *            Monto maximo por transaccion.
	 * @return Array con montos para transacciones.
	 */
	private Double[] getAmounts(Double montoTotal, Double montoLimite) {
		Double[] montos = null;
		if (montoTotal > montoLimite) {
			int cantAmount = (int) (montoTotal / montoLimite);
			double resto = (montoTotal % montoLimite);
			cantAmount = (resto != 0) ? (cantAmount + 1) : cantAmount;
			montos = new Double[cantAmount];
			for (int i = 0; i < montos.length - 1; i++) {
				montos[i] = montoLimite;
			}

			montos[montos.length - 1] = (resto != 0) ? resto : montoLimite;

		} else {
			montos = new Double[1];
			montos[0] = montoTotal;
		}
		return montos;
	}

	@Override
	public Double getBalanceCompany(long idCompany) {
		Double montoDisponible = 0d;
		try {
			List<BilleteraMts> bill = this.mtsMng.consultaSaldo(idCompany);
			String wallet = systemParameterMgr
					.getParameterValue(MtsInterfaceParameters.COMISION_WALLET);
			for (BilleteraMts billeteraMts : bill) {
				if (billeteraMts.getWallet().compareTo(wallet) != 0) {
					montoDisponible += billeteraMts.getAmount();
				}
			}
			log.debug("validateAmountPaymentOrder-> Monto disponible para comercio "
					+ idCompany + ":" + montoDisponible);

			return montoDisponible;

		} catch (MTSInterfaceException e) {
			log.error(
					"Problemas al validar monto de orden de pago-->"
							+ e.getMessage(), e);
			return -1d;
		}

	}
}