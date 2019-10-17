package py.com.global.spm.model.mdb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.LogpaymentCounterCache;
import py.com.global.spm.model.cache.dto.PaymentOrderDto;
import py.com.global.spm.model.interfaces.PaymentOrderManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.TransferProcessManagerLocal;
import py.com.global.spm.model.messages.*;
import py.com.global.spm.model.systemparameters.TransferProcessParameters;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.util.SpmUtil;

/**
 * El TransferProcess se encarga de ejecutar la orden de pago.
 * 
 * @author R2
 * */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = SpmQueues.TRANSFER_PROCESS_QUEUE) })
public class TransferProcessMdb implements MessageListener {

	public static final long DELAY = 500;
	public static final long TRX_RUN = 10;
	public static final long DELAY_RUN = 5000;
	public static final long MAX_WAIT_RUN = 10000;

	private static final Logger log = Logger
			.getLogger(TransferProcessMdb.class);
	private JMSSender queueUpdaterRequest;
	private JMSSender queueMTSInterfaceRequest;

	@EJB
	private PaymentOrderManagerLocal paymentOrderManager;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	@EJB
	private TransferProcessManagerLocal transferProcess;

	@EJB
	private SystemparameterManagerLocal systemParameterManager;

	public TransferProcessMdb() {

	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		// Leer el mensaje
		log.debug("Leyendo mensaje...");
		if (message != null && message instanceof ObjectMessage) {
			Object msg = null;
			try {
				msg = ((ObjectMessage) message).getObject();
			} catch (JMSException e) {
				log.error("Al leer un mensaje de la cola --> cola="
						+ SpmQueues.TRANSFER_PROCESS_QUEUE + ", msg="
						+ e.getMessage());
				msg = null;
			}

			if (msg != null) {
				// Procesar una solicitud del cobro del valorizador
				if (msg instanceof FlowManagerToTransferProcessMessage) {
					log.debug("Recibiendo mensaje desde FlowManager");
					FlowManagerToTransferProcessMessage request = (FlowManagerToTransferProcessMessage) msg;
					// Procesar solicitud de cobro
					log.debug("Procesando requerimiento --> " + request);
					processRequest(request);
				} else if (msg instanceof MTSInterfaceToTransferProcessMessage) {
					log.debug("Recibiendo mensaje desde MTSInterface");
					MTSInterfaceToTransferProcessMessage response = (MTSInterfaceToTransferProcessMessage) msg;
					// Procesar respuesta
					log.debug("Procesando respuesta --> " + response);
					processResponse(response);
				} else if (msg instanceof SPMGUIToTransferProcessMessage) {
					log.debug("Recibiendo mensaje desde SPM-GUI");
					SPMGUIToTransferProcessMessage request = (SPMGUIToTransferProcessMessage) msg;
					// Procesar solicitud de cobro
					log.debug("Procesando requerimiento --> " + request);
					processRequest(request);
				} else {
					log.debug("Mensaje NULO!!!");
					return;
				}
			}
		}

	}

	private void processRequest(SPMGUIToTransferProcessMessage request) {
		Paymentorder op = this.paymentOrderManager.getPaymentorder(request
				.getIdpaymentorderPk());
		if (op == null) {
			log.warn("Order payment not found -->"
					+ request.getIdpaymentorderPk());
		} else {
			this.processPaymentOrder(op);
		}

	}

	private boolean validateAmount(Paymentorder op) {
		boolean tieneCredito = false;
		String validarMonto = this.systemParameterManager.getParameter(
				TransferProcessParameters.VALIDATION_AMOUNT, SpmConstants.NO);
		if (validarMonto.equalsIgnoreCase(SpmConstants.YES)) {
			// Consulta de saldo al MTS.
			tieneCredito = this.transferProcess.validateAmountPaymentOrder(op);
		} else {
			tieneCredito = true;
		}
		return tieneCredito;
	}

	/**
	 * Verifica si la orden de pago se encuentra en el hash.
	 * 
	 * @param op
	 *            La orden de pago a validar.
	 * @return true si la orden de pago no es duplicada y false en caso
	 *         contrario.
	 */
	private boolean validateDuplicate(Paymentorder op) {
		// Validar si la orden de pago ya se encuentra en el hash.
		return !this.transferProcess.existPaymentOrder(op);
	}

	private void processPaymentOrder(Paymentorder po) {
		if (validateDuplicate(po)) {
			if (this.validateAmount(po)) {
				if (po.getStateChr().equalsIgnoreCase(
						PaymentOrderStates.PENDIENTE_DE_PAGO)
						|| po.getStateChr().equalsIgnoreCase(
								PaymentOrderStates.EN_PROCESO)) {
					// 1- Si el estado es PENDIENTE DE PAGO o En Proceso.
					// 1.1- Generar pagos en base a la orden de pago con estado
					// pendiente y persiste en la base de datos.
					List<Logpayment> logpaymentList = this.transferProcess
							.generateLogPayment(po);

					// 1.2- Cambiar a estado en Proceso la orden de pago.
					po.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
					po.setIdeventcodeNum(0);
					po.setTotalpaymentNum(logpaymentList.size());
					po.setStateChr(PaymentOrderStates.PAGANDO);
					this.paymentOrderManager.updatePaymentorder(po);

					long trxRun = this.systemParameterManager.getParameter(
							TransferProcessParameters.TRX_RUN, TRX_RUN);
					long delayRun = this.systemParameterManager.getParameter(
							TransferProcessParameters.DELAY_RUN, DELAY_RUN);
					LogpaymentCounterCache counterCache = LogpaymentCounterCache
							.getInstance();
					/*
					 * Se agrega la orden de pago al cache de contador de
					 * logpayment. Se agrega con valor 0
					 */
					counterCache.addPaymentOrder(po);
					String waitResponseStr = systemParameterManager
							.getParameter(
									TransferProcessParameters.WAIT_RESPONSE,
									SpmConstants.NO);
					long maxWait = this.systemParameterManager.getParameter(
							TransferProcessParameters.MAX_WAIT_RESPONSE,
							MAX_WAIT_RUN);
					// 1.3- Enviar cada pago al MTSInterface.
					long delay = this.systemParameterManager.getParameter(
							TransferProcessParameters.DELAY, DELAY);
					for (Logpayment logpayment : logpaymentList) {
						TransferProcessToMTSInterfaceMessage tpmi = new TransferProcessToMTSInterfaceMessage();
						tpmi.setPayment(logpayment);
						if (!this.sendRequestMTSInterface(tpmi)) {
							log.error("Fallo al envio de mensaje al MTSInterface-->"
									+ tpmi);
						} else {
							// incrementar contador de logPayment
							counterCache.increment(po);
						}
						// aca creo que esta mejor el delay
						if (delay > 0) {
							log.trace("Iniciando delay para no reventar el MTS --> "
									+ delay + " ms.");
							SpmUtil.sleep(delay);
						}
						if (counterCache.getCounter(po) < 0) {
							// jamas puede ser menor a 0
							log.warn("Algo esta funcionando muy mal --> " + po
									+ ", counterCache="
									+ counterCache.getCounter(po));
						} else if (trxRun > 0
								&& (counterCache.getCounter(po) == trxRun)) {
							if (waitResponseStr
									.compareToIgnoreCase(SpmConstants.NO) == 0) {
								/*
								 * No se debe esperar por la respuesta de los
								 * enviados para continuar. solo se hace un
								 * sleep y luego se continua.
								 */
								SpmUtil.sleep(delayRun);
								counterCache.reset(po);
							} else {
								/*
								 * Esperar que los logpayment enviados sean
								 * respondidos, luego se continua con la
								 * ejecucion. No es necesario reiniciar puesto a
								 * medida que se responde. se decrementa
								 */
								log.debug("Waiting for all request be responsed --> counter="
										+ counterCache.getCounter(po)
										+ ", "
										+ po);
								long sleepTime = 200;
								long sleepCounter = 0;
								while (counterCache.getCounter(po) > 0) {
									sleepCounter += sleepTime;
									SpmUtil.sleep(sleepTime);
									if (sleepCounter > maxWait) {
										log.warn("Max time wait exceed. Continue --> maxwait="
												+ maxWait
												+ "millis, response pending="
												+ counterCache.getCounter(po)
												+ ", " + po);
										break;
									}
								}
							}
						}
					}
					if (counterCache.getCounter(po) > 0) {
						counterCache.prepareToRemove(po);
					} else {
						counterCache.removePaymentOrder(po);
					}
				} else if (po.getStateChr().equalsIgnoreCase(
						PaymentOrderStates.NO_PAGADA)
						|| po.getStateChr().equalsIgnoreCase(
								PaymentOrderStates.PARCIALMENTE_PAGADA)) {
					// Si el estado es NO PAGADO o PARCIALMENTE PAGADA.
					// Reintento manual de pagos.
					// 2- Obtener pagos con estado Error o parcialmente
					// satisfactorio.
					List<Logpayment> logpaymentList = this.transferProcess
							.getRetryLogPayment(po);

					if (logpaymentList != null) {
						// 2.1- Cambiar a estado en Proceso la orden de pago.
						po.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
						po.setIdeventcodeNum(0);
						po.setStateChr(PaymentOrderStates.PAGANDO);
						this.paymentOrderManager.updatePaymentorder(po);

						// 1.3- Enviar cada pago al MTSInterface.
						for (Logpayment logpayment : logpaymentList) {
							TransferProcessToMTSInterfaceMessage tpmi = new TransferProcessToMTSInterfaceMessage();
							tpmi.setPayment(logpayment);
							if (!this.sendRequestMTSInterface(tpmi)) {
								log.error("Fallo al envio de mensaje al MTSInterface-->"
										+ tpmi);
							}
						}
					} else {
						log.warn("Reintento de pagos: La orden de pago no tiene pagos con estados parciales-->"
								+ po);
					}

				} else {
					log.warn("Estado de orden de pago invalido -->" + po);
				}
			} else {
				// Enviar con estado recibido la op al Updater.
				// 3.1- Enviar resultado al proceso Updater.
				TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
				tpu.setPaymentorder(po);
				if (!this.sendRequestUpdater(tpu)) {
					log.error("Fallo al envio de mensaje al UPDATER-->" + tpu);
				}
			}
		} else {
			log.warn("Orden de pago en proceso, solicitud descartada -->" + po);
		}
	}

	private void processRequest(FlowManagerToTransferProcessMessage request) {
		if (request.getPaymentorder() == null) {
			log.warn("Order payment newInstance flowManager not found -->"
					+ request.getPaymentorder());
		} else {
			this.processPaymentOrder(request.getPaymentorder());
		}

	}

	private void processResponse(MTSInterfaceToTransferProcessMessage response) {
		// 1- Buscar en el cache el pago.
		if (this.transferProcess.existLogpayment(response.getPayment())) {
			// 2- Actualizar el estado del pago.
			PaymentOrderDto pod = this.transferProcess
					.updatePaymentOrderCache(response.getPayment());
			String waitResponseStr = systemParameterManager.getParameter(
					TransferProcessParameters.WAIT_RESPONSE, SpmConstants.NO);
			LogpaymentCounterCache counterCache = LogpaymentCounterCache
					.getInstance();
			// Se necesita decrementar porque espera por las respuestas
			// para continuar
			if (waitResponseStr.compareToIgnoreCase(SpmConstants.NO) != 0) {
				counterCache.decrement(pod.getPaymentOrder());
			}
			// 3- Verificar si es el ultimo pago a procesar.
			if (!pod.getPaymentOrder().getStateChr()
					.equalsIgnoreCase(PaymentOrderStates.PAGANDO)) {
				// 3.1- Enviar resultado al proceso Updater.
				TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
				tpu.setPaymentorder(pod.getPaymentOrder());
				List<Logpayment> logs = new ArrayList<Logpayment>(
						pod.getLogPayment());
				tpu.setLogpaymentList(logs);
				if (pod.isReintento()){
					tpu.setReintento(true);
				}
				if (!this.sendRequestUpdater(tpu)) {
					log.error("Fallo al envio de mensaje al UPDATER-->" + tpu);
				}
				// 3.2- Sacar del cache.
				this.transferProcess.removePaymentOrderCache(pod);
				// ya se enviaron todos los logpayments y ya se preparon todos.
				// limpiar cache de contadores
				if (counterCache.isReadyToRemove(pod.getPaymentOrder())
						&& counterCache.getCounter(pod.getPaymentOrder())
								.intValue() == 0) {
					counterCache.removePaymentOrder(pod.getPaymentOrder());
				}
			}

		} else {
			log.warn("Mensaje descartado -->" + response.toString());
		}

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

	private void initDtq() {

		try {
			// Levantar la cola para MTS Interface.
			if (queueMTSInterfaceRequest == null)
				queueMTSInterfaceRequest = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.MTS_INTERFACE_QUEUE);

			if (!queueMTSInterfaceRequest.isConnected())
				log.info("Iniciando cola de datos --> cola="
						+ queueMTSInterfaceRequest.toString() + ", init="
						+ queueMTSInterfaceRequest.init());

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

	private boolean sendRequestUpdater(TransferProcessToUpdaterMessage tpu) {
		boolean ready = false;
		if (queueUpdaterRequest != null && queueUpdaterRequest.isConnected()) {
			log.trace("Mensaje a Updater desde TransferProcessMdb--> " + tpu);
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
					+ SpmQueues.UPDATER_QUEUE);
		}
		return ready;
	}

	private boolean sendRequestMTSInterface(
			TransferProcessToMTSInterfaceMessage tpu) {
		boolean ready = false;
		if (queueMTSInterfaceRequest != null
				&& queueMTSInterfaceRequest.isConnected()) {
			log.trace("Mensaje a MTSInterface --> " + tpu);
			// movido el delay
			if (!(ready = queueMTSInterfaceRequest.send(tpu))) {
				log.error("Fallo al enviar mensaje a la cola de datos. Mensaje descartado --> "
						+ "cola=" + SpmQueues.MTS_INTERFACE_QUEUE);
				try {
					queueMTSInterfaceRequest.close();
				} catch (Exception e) {
					log.error("Fallo al cerrar cola. --> " + "cola="
							+ SpmQueues.MTS_INTERFACE_QUEUE + ",Error="
							+ e.getCause());
				}
			}
		} else {
			log.error("Fallo de conexion con la cola de datos. Mensaje descartado --> "
					+ SpmQueues.MTS_INTERFACE_QUEUE);
		}
		return ready;
	}

	@PreDestroy
	public void close() {
		try {
			if (queueMTSInterfaceRequest != null) {
				queueMTSInterfaceRequest.close();
			}
			if (queueUpdaterRequest != null) {
				queueUpdaterRequest.close();
			}

		} catch (Exception e) {
			log.error("Fallo al cerrar cola. Error -->" + e.getCause());
		}

	}

}
