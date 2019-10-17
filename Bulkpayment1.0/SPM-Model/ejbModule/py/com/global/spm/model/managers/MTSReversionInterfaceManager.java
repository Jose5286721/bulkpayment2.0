package py.com.global.spm.model.managers;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Logmts;
import py.com.global.spm.entities.Logmtsrev;
import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.model.eventcodes.MTSReversionProcessEventCodes;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.LogmtsManagerLocal;
import py.com.global.spm.model.interfaces.MTSReversionInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.MtsManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.util.JMSSender;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmQueues;
import py.com.global.spm.model.ws.dto.ReverseResponse;

/**
 * Session Bean implementation class MTSTransferInteface
 */
@Stateless
public class MTSReversionInterfaceManager implements
		MTSReversionInterfaceManagerLocal {

	public static final Logger log = Logger
			.getLogger(MTSReversionInterfaceManager.class);

	@EJB
	MtsManagerLocal mtsMng;

	@EJB
	public SystemparameterManagerLocal systemParameterMgr;

	@EJB
	public CompanyManagerLocal companyMgr;

	@EJB
	LogmtsManagerLocal logmtsMng;

	private JMSSender queueAsyncUpdater;

	// Recursos del contenedor
	private InitialContext initialContext = null;

	/**
	 * Default constructor.
	 */
	public MTSReversionInterfaceManager() {

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
			log.error("Error al inicializar MTSReversionInterfaceManager->"
					+ e.getMessage());
		}
	}

	@Override
	public void processPayment(Logpayment payment) {
		// 1-Obtener las transacciones MTS a revertir del Logmts.
		List<Logmts> logmtsList = this.logmtsMng.findLogmtsToRevert(payment
				.getIdpaymentNum());

		if (logmtsList != null && logmtsList.size() > 0) {
			int cantPending = logmtsList.size();// Para saber el estado de la
												// orden
			// de pago.
			int cantTotal = logmtsList.size();// Para saber el estado de la
												// orden de
			// pago.
			// 2- Enviar al mts la orden de reversion.
			String idSession = null;
			try {
				idSession = this.mtsMng.bind(payment.getIdcompanyPk());
				for (Logmts logmts2 : logmtsList) {
					Logmtsrev logrev = new Logmtsrev();
					logrev.setAmountNum(logmts2.getAmountNum());
					logrev.setIdbeneficiaryPk(logmts2.getIdbeneficiaryPk());
					logrev.setIdeventcodeNum(0);// Todo bien.
					logrev.setIdcompanyPk(logmts2.getIdcompanyPk());
					logrev.setIdlogmtsPk(logmts2.getIdlogmtsPk());
					logrev.setIdpaymentNum(logmts2.getIdpaymentNum());
					logrev.setIdpaymentorderPk(logmts2.getIdpaymentorderPk());
					logrev.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
					logrev.setIdtrxmtsChr(logmts2.getIdtrxmtsChr());
					logrev.setMethodChr(logmts2.getMethodChr());
					logrev.setTrxtypeChr(logmts2.getTrxtypeChr());
					logrev.setRequestdateTim(new Timestamp(System
							.currentTimeMillis()));
					logrev.setIdsessionmtsChr(idSession);
					logrev.setRequestChr("idSession=" + idSession
							+ ",idTransacion=" + logrev.getIdtrxmtsChr());

					// 5- Ejecutar cada operacion.
					try {
						ReverseResponse response = mtsMng.reverseTransaction(
								idSession, logrev.getIdtrxmtsChr());

						// 6- Enviar actualizacion de registro al AsyncUpdater.
						logrev.setResponsedateTim(new Timestamp(System
								.currentTimeMillis()));
						logrev.setIdtrxmtsChr(response.getNroTransaction()
								.toString());
						logrev.setResponseChr(response.getResponseStr());
						logrev.setStateChr(SpmConstants.SUCCESS);
						cantPending--;
					} catch (MTSInterfaceException e) {
						logrev.setResponsedateTim(new Timestamp(System
								.currentTimeMillis()));
						logrev.setStateChr(SpmConstants.ERROR);
						logrev.setIdeventcodeNum(e.getIdEvent());
						log.error("Error mts:IdEvent->" + e.getIdEvent()
								+ ",msg=" + e.getMessage());
					}
					// 7-Actualizamos el estado del logmts.
					if (logrev.getStateChr().equalsIgnoreCase(
							SpmConstants.SUCCESS)) {
						logmts2.setStateChr(SpmConstants.REVERT);
						logmts2.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
					}
					// 8-Enviar a AsyncUpdater
					this.sendAsyncUpdater(logrev);
					this.sendAsyncUpdater(logmts2);
				}

				// 9- Actualizar logpayment con datos actualizados.
				// 9.1- Verificar si el pago se queda con estado parcial.
				String estadoOp = null;
				if (cantPending == 0) {
					estadoOp = PaymentStates.REVERTIDO;
				} else if (cantPending < cantTotal && cantPending > 0) {
					estadoOp = PaymentStates.PARCIALMENTE_REVERTIDO;
				} else {
					// El estado es invalido si no se realizo
					log.warn("Estado de reversion indeterminado -> CantPending="
							+ cantPending + ",cantTotal=" + cantTotal);
				}
				// 9.2- Actualizar el monto pagado y el estado.
				if (estadoOp != null) {
					payment.setStateChr(estadoOp);
					payment.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
					payment.setIdeventcodeNum(MTSReversionProcessEventCodes.SUCCESS
							.getIdEventCode());
				}

			} catch (MTSInterfaceException e1) {
				payment.setStateChr(PaymentStates.ERROR);
				payment.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
				payment.setIdeventcodeNum(MTSReversionProcessEventCodes.CONNECTION_PROBLEM
						.getIdEventCode());
				log.error("Error al ejecutar pago en MTS->" + e1.getMessage(),
						e1);
			} finally {
				if (idSession != null) {
					// 10- Me desconecto del MTS.
					mtsMng.unBind(payment.getIdcompanyPk(), idSession);
				}
			}

		} else {
			log.warn("No se puede revertir. El pago no tiene ninguna transferencia MTS asociada->"
					+ payment);
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

		} catch (Exception e) {
			log.error("Fallo al iniciar la cola. --> Error=" + e.getCause());
		}
	}

	private boolean sendAsyncUpdater(Logmtsrev logrev) {
		boolean ready = false;
		if (queueAsyncUpdater != null && queueAsyncUpdater.isConnected()) {
			log.trace("Mensaje a AsyncUpdater --> " + logrev);
			if (!(ready = queueAsyncUpdater.send(logrev))) {
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
					+ SpmQueues.ASYNC_UPDATER_QUEUE);
		}
		return ready;
	}

	private boolean sendAsyncUpdater(Logmts logrev) {
		boolean ready = false;
		if (queueAsyncUpdater != null && queueAsyncUpdater.isConnected()) {
			log.trace("Mensaje a AsyncUpdater --> " + logrev);
			if (!(ready = queueAsyncUpdater.send(logrev))) {
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
					+ SpmQueues.ASYNC_UPDATER_QUEUE);
		}
		return ready;
	}

}
