package py.com.global.spm.model.ws.managers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import py.com.global.spm.entities.Company;
import py.com.global.spm.model.cache.MTSCompanySessionCache;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.ErrorlogManagerLocal;
import py.com.global.spm.model.interfaces.MtsManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmUtil;
import py.com.global.spm.model.ws.dto.BilleteraMts;
import py.com.global.spm.model.ws.dto.ReverseResponse;
import py.com.global.spm.model.ws.dto.SalaryPaymentResponse;
import py.com.global.spm.model.ws.dto.TransactionInfoResponse;
import us.inswitch.mts.ws.server.BilleteraPack;
import us.inswitch.mts.ws.server.Bind;
import us.inswitch.mts.ws.server.BindE;
import us.inswitch.mts.ws.server.BindPack;
import us.inswitch.mts.ws.server.BindResponse;
import us.inswitch.mts.ws.server.BindResponseE;
import us.inswitch.mts.ws.server.ConsultaSaldo;
import us.inswitch.mts.ws.server.ConsultaSaldoE;
import us.inswitch.mts.ws.server.ConsultaSaldoResponseE;
import us.inswitch.mts.ws.server.InsertTransactionInfo;
import us.inswitch.mts.ws.server.InsertTransactionInfoE;
import us.inswitch.mts.ws.server.InsertTransactionInfoResponseE;
import us.inswitch.mts.ws.server.MTSServiceStub;
import us.inswitch.mts.ws.server.Reversion;
import us.inswitch.mts.ws.server.ReversionE;
import us.inswitch.mts.ws.server.ReversionResponseE;
import us.inswitch.mts.ws.server.SalaryPayment;
import us.inswitch.mts.ws.server.SalaryPaymentE;
import us.inswitch.mts.ws.server.SalaryPaymentResponseE;
import us.inswitch.mts.ws.server.SaldoPack;
import us.inswitch.mts.ws.server.TrnResponsePack;
import us.inswitch.mts.ws.server.UnBind;
import us.inswitch.mts.ws.server.UnBindE;
import us.inswitch.mts.ws.server.UnBindPack;
import us.inswitch.mts.ws.server.UnBindResponseE;

//import us.inswitch.mts.ws.server.MTS;

/**
 * Session Bean implementation class MtsManager
 */
@Stateless
public class MtsManager implements MtsManagerLocal {

	Logger log = Logger.getLogger(MtsManager.class);

	private MTSCompanySessionCache cache;
	@EJB
	public CompanyManagerLocal companyMgr;
	@EJB
	public SystemparameterManagerLocal systemParameterMgr;
	@EJB
	private ErrorlogManagerLocal errorlogManager;

	private final String processName = "MtsManager";

	/**
	 * Default constructor.
	 */
	public MtsManager() {

	}

	@PostConstruct
	public void init() {
		cache = MTSCompanySessionCache.getInstance();
	}

	public String bind(Long idCompany) throws MTSInterfaceException {
		String isDummy = systemParameterMgr.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);

		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			log.info("MtsManager DUMMY-->Bind=" + idCompany);
			return "Es un Dummy";
		} else {
			String idSession = null;
			// 1- Obtener datos para conexion.
			Company company = companyMgr.getCompanyById(idCompany);
			synchronized (cache) {
				idSession = cache.getIdSession(company);
				if (idSession == null) {

					log.debug("Binding with MTS --> " + company);
					idSession = this.bindWithMTS(company);
					cache.addSessionToCache(company, idSession);
				} else {
					log.debug("Reusing mts session --> idSession=" + idSession
							+ ", " + company);
				}
			}
			cache.incrementUsedSession(company);
			return idSession;
		}

	}

	private String bindWithMTS(Company company) throws MTSInterfaceException {
		// 2- Conectarse
		String endPointStr = systemParameterMgr
				.getParameterValue(MtsInterfaceParameters.ENDPOINT);
		try {
			MTSServiceStub service1 = new MTSServiceStub(endPointStr);

			log.trace("OUT Bind:" + company.getMtsfield5Chr() + ","
					+ company.getMtsfield3Chr() + ","
					+ company.getMtsfield4Chr() + ","
					+ SpmUtil.getSiteLocalAddress());
			BindE inputBind = new BindE();
			Bind inBind = new Bind();
			inBind.setIp(SpmUtil.getSiteLocalAddress());
			inBind.setPwd(company.getMtsfield3Chr());
			inBind.setRole(company.getMtsfield4Chr());
			inBind.setUsr(company.getMtsfield5Chr());
			inputBind.setBind(inBind);

			BindResponseE bindResp = service1.bind(inputBind);

			log.trace("IN Bind:" + this.toString(bindResp));

			if (bindResp == null) {
				String description = "Error Method Bind: Respuesta es null. --> user="
						+ company.getMtsfield5Chr();
				errorlogManager.insertErrorlog(processName, description);
				log.error(description);
				throw new MTSInterfaceException(-1, "Respuesta es null.");
			} else if (bindResp.getBindResponse().get_return().getStatus() != 0) {
				String description = "Error Method Bind: user="
						+ company.getMtsfield5Chr() + ", Status:"
						+ bindResp.getBindResponse().get_return().getStatus()
						+ ", Message:"
						+ bindResp.getBindResponse().get_return().getMensaje();
				errorlogManager.insertErrorlog(processName, description);
				log.error(description);
				throw new MTSInterfaceException(bindResp.getBindResponse()
						.get_return().getStatus(), bindResp.getBindResponse()
						.get_return().getMensaje());
			} else {
				return bindResp.getBindResponse().get_return().getSesion();
			}

		} catch (Exception e) {
			String description = "Error Method Bind: user="
					+ company.getMtsfield5Chr() + ", " + e.getMessage();
			errorlogManager.insertErrorlog(processName, description);
			log.error(description, e);
			throw new MTSInterfaceException(-100, e.getMessage());
		}
	}

	public SalaryPaymentResponse salaryPayment(String idSession, String nroCel,
			String rol, Double monto, String currency, String band,
			String wallet) throws MTSInterfaceException {

		String isDummy = systemParameterMgr.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);

		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			log.info("IN Salary Payment:" + idSession + "," + nroCel + ","
					+ rol + "," + monto + "," + currency + "," + band + ","
					+ wallet);
			SalaryPaymentResponse slr = new SalaryPaymentResponse();
			slr.setNroTransaction((new Random()).nextInt());
			slr.setResponseStr("Cadena de Respuesta.");
			return slr;
		} else {
			// 2- Conectarse
			String endPointStr = systemParameterMgr
					.getParameterValue(MtsInterfaceParameters.ENDPOINT);

			try {
				MTSServiceStub service1 = new MTSServiceStub(endPointStr);
				log.trace("OUT Salary Payment:" + idSession + "," + nroCel
						+ "," + rol + "," + monto + "," + currency + "," + band
						+ "," + wallet);

				SalaryPaymentE inputSp = new SalaryPaymentE();
				SalaryPayment inSp = new SalaryPayment();
				inSp.setBrand(band);
				inSp.setCuenta_destino(nroCel);
				inSp.setMoneda(currency);
				inSp.setMonto(monto);
				inSp.setRol_predeterminado(rol);
				inSp.setSesion(idSession);
				inSp.setTipo_billetera(wallet);
				inputSp.setSalaryPayment(inSp);

				SalaryPaymentResponseE trnResp = service1
						.salaryPayment(inputSp);
				log.trace("IN Salary Payment:" + this.toString(trnResp));

				if (trnResp == null) {
					log.error("Error Method Salary Payment: Respuesta es null. sesion="
							+ idSession + ", destino=" + nroCel);
					throw new MTSInterfaceException(-1, "Respuesta es null.");
				} else if (trnResp.getSalaryPaymentResponse().get_return()
						.getStatus() != 0) {
					String description = "Error Method Salary Payment: Status:"
							+ trnResp.getSalaryPaymentResponse().get_return()
									.getStatus()
							+ ", sesion="
							+ idSession
							+ ", destino="
							+ nroCel
							+ ", Message:"
							+ trnResp.getSalaryPaymentResponse().get_return()
									.getMensaje();
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
					throw new MTSInterfaceException(trnResp
							.getSalaryPaymentResponse().get_return()
							.getStatus(), trnResp.getSalaryPaymentResponse()
							.get_return().getMensaje());
				} else {
					SalaryPaymentResponse slr = new SalaryPaymentResponse();
					slr.setNroTransaction(trnResp.getSalaryPaymentResponse()
							.get_return().getIdTransaccion());
					slr.setResponseStr(this.toString(trnResp));
					return slr;
				}

			} catch (RemoteException e) {
				String description = "Error Method Salary Payment: sesion="
						+ idSession + ", destino=" + nroCel + ", "
						+ e.getMessage();
				errorlogManager.insertErrorlog(processName, description);
				log.error(description, e);
				throw new MTSInterfaceException(-100, e.getMessage());
			}
		}

	}

	public List<BilleteraMts> consultaSaldo(String idSession)
			throws MTSInterfaceException {
		String isDummy = systemParameterMgr.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);

		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			log.info("MtsManager DUMMY-->consultaSaldo=" + idSession);
			List<BilleteraMts> billeteras = new ArrayList<BilleteraMts>();
			BilleteraMts b1 = new BilleteraMts();
			b1.setWallet("WALLET GLOBAL");
			b1.setAmount(1000000000D);// Se convierte

			BilleteraMts b2 = new BilleteraMts();
			b2.setWallet("WALLET GLOBAL 2");
			b2.setAmount(5000000000D);// Se convierte

			BilleteraMts b3 = new BilleteraMts();
			b3.setWallet("Comisiones");
			b3.setAmount(20000000000D);// Se convierte

			billeteras.add(b1);
			billeteras.add(b2);
			billeteras.add(b3);

			return billeteras;
		} else {
			// 1- Obtener datos de conexion y Conectarse
			String endPointStr = systemParameterMgr
					.getParameterValue(MtsInterfaceParameters.ENDPOINT);

			try {

				MTSServiceStub service1 = new MTSServiceStub(endPointStr);
				log.trace("OUT Consulta Saldo:" + idSession);
				ConsultaSaldoE cs = new ConsultaSaldoE();
				ConsultaSaldo cs2 = new ConsultaSaldo();
				cs2.setSesion(idSession);
				cs.setConsultaSaldo(cs2);
				ConsultaSaldoResponseE saldoResp = service1.consultaSaldo(cs);

				log.trace("IN Consulta Saldo:" + this.toString(saldoResp));

				if (saldoResp == null) {
					String description = "Error Method Consulta Saldo: Respuesta es null. idSession="
							+ idSession;
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
					throw new MTSInterfaceException(-1, "Respuesta es null.");
				} else if (saldoResp.getConsultaSaldoResponse().get_return()
						.getStatus() != 0) {
					String description = "Error Method Consulta Saldo: Status:"
							+ saldoResp.getConsultaSaldoResponse().get_return()
									.getStatus()
							+ ", Message:"
							+ saldoResp.getConsultaSaldoResponse().get_return()
									.getMensaje();
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
					throw new MTSInterfaceException(saldoResp
							.getConsultaSaldoResponse().get_return()
							.getStatus(), saldoResp.getConsultaSaldoResponse()
							.get_return().getMensaje());
				} else {
					List<BilleteraMts> billeteras = this
							.parseToBilleteraMts(saldoResp
									.getConsultaSaldoResponse().get_return());
					return billeteras;
				}

			} catch (RemoteException e) {
				String description = "Error Method Consulta Saldo: idSession="
						+ idSession + ", " + e.getMessage();
				errorlogManager.insertErrorlog(processName, description);
				log.error(description, e);
				throw new MTSInterfaceException(-100, e.getMessage());
			}

		}

	}

	public List<BilleteraMts> consultaSaldo(Long idCompany)
			throws MTSInterfaceException {
		String idSesion = this.bind(idCompany);
		// 4- Realizo una consulta de saldo.
		List<BilleteraMts> arrayBill = this.consultaSaldo(idSesion);

		// 5-Hago un UnBind.
		this.unBind(idCompany, idSesion);

		// 2-Buscar billetera parametrizada.
		return arrayBill;
	}

	public void unBind(long idCompany, String idSession) {
		// 1- Obtener datos de conexion y Conectarse
		String isDummy = systemParameterMgr.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);

		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			log.info("MtsManager DUMMY-->Unbind=" + idSession);
		} else {
			String idSessionCached = null;
			// 1- Obtener datos para conexion.
			Company company = companyMgr.getCompanyById(idCompany);
			idSessionCached = cache.getIdSession(company);
			if (idSessionCached == null
					|| idSessionCached.compareToIgnoreCase(idSession) != 0) {
				log.warn("idSessionCached and idSession received not match"
						+ " --> idSessionCached=" + idSessionCached
						+ ", idSession=" + idSession + ", " + company);
			} else {
				log.debug("Reusing mts session --> idSession=" + idSession
						+ ", " + company);
			}
			cache.decrementUsedSession(company);
			synchronized (cache) {
				if (cache.hasToRemoveUsedSession(company)) {
					this.unbindWithMTS(idSession);
					cache.removeSessionFromCache(company);
				}
			}
		}
	}

	public void unbindWithMTS(String idSession) {
		String endPointStr = systemParameterMgr
				.getParameterValue(MtsInterfaceParameters.ENDPOINT);
		try {
			MTSServiceStub service1 = new MTSServiceStub(endPointStr);
			log.trace("OUT unBind:" + idSession);
			UnBindE inputUnBind = new UnBindE();
			UnBind inUnBind = new UnBind();
			inUnBind.setSesion(idSession);
			inputUnBind.setUnBind(inUnBind);
			UnBindResponseE unbindResp = service1.unBind(inputUnBind);

			if (unbindResp != null) {
				log.trace("IN unBind:"
						+ this.toString(unbindResp.getUnBindResponse()
								.get_return()));
				if (unbindResp.getUnBindResponse().get_return().getStatus() != 0) {
					String description = "Error Method Unbind: idSession="
							+ idSession
							+ ", Status:"
							+ unbindResp.getUnBindResponse().get_return()
									.getStatus()
							+ ", Message:"
							+ unbindResp.getUnBindResponse().get_return()
									.getMensaje();
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
				}
			} else {
				String description = "unbindResp is null. idSession="
						+ idSession;
				errorlogManager.insertErrorlog(processName, description);
				log.error(description);
			}

		} catch (AxisFault e) {
			String description = "Error Method Unbind: idSession=" + idSession
					+ e.getMessage();
			errorlogManager.insertErrorlog(processName, description);
			log.error(description, e);
		} catch (RemoteException e) {
			String description = "Error Method Unbind: idSession=" + idSession
					+ e.getMessage();
			errorlogManager.insertErrorlog(processName, description);
			log.error(description, e);

		}
	}

	@Override
	public ReverseResponse reverseTransaction(String idSession,
			String idTransaction) throws MTSInterfaceException {
		String isDummy = systemParameterMgr.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);

		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			log.info("MtsManager DUMMY-->reverseTransation=" + idSession);
			ReverseResponse resp = new ReverseResponse();
			resp.setNroTransaction(4345322);
			resp.setResponseStr("Es una reversion dummy.");
			return resp;
		} else {
			// 1- Obtener datos de conexion y Conectarse
			String endPointStr = systemParameterMgr
					.getParameterValue(MtsInterfaceParameters.ENDPOINT);

			try {

				MTSServiceStub service1 = new MTSServiceStub(endPointStr);
				log.trace("OUT Reverse Transaction:" + idSession);
				ReversionE cs = new ReversionE();
				Reversion cs2 = new Reversion();
				cs2.setSesion(idSession);
				cs2.setId_transaccion(idTransaction);
				cs.setReversion(cs2);
				ReversionResponseE revertResp = service1.reversion(cs);

				log.trace("IN Reverse Transaction:" + this.toString(revertResp));

				if (revertResp == null) {
					String description = "Error Method Reverse Transaction: Respuesta es null. idSession="
							+ idSession + ", idTransaction=" + idTransaction;
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
					throw new MTSInterfaceException(-1, "Respuesta es null.");
				} else if (revertResp.getReversionResponse().get_return()
						.getStatus() != 0) {
					String description = "Error Method Reverse Transaction: Status:"
							+ revertResp.getReversionResponse().get_return()
									.getStatus()
							+ ", idSession="
							+ idSession
							+ ", idTransaction="
							+ idTransaction
							+ ", Message:"
							+ revertResp.getReversionResponse().get_return()
									.getMensaje();
					errorlogManager.insertErrorlog(processName, description);
					log.error(description);
					throw new MTSInterfaceException(revertResp
							.getReversionResponse().get_return().getStatus(),
							revertResp.getReversionResponse().get_return()
									.getMensaje());
				} else {
					ReverseResponse resp = new ReverseResponse();
					resp.setNroTransaction(revertResp.getReversionResponse()
							.get_return().getIdTransaccion());
					resp.setResponseStr(this.toString(revertResp));
					return resp;
				}
			} catch (RemoteException e) {
				String description = "Error Method reverseTransaction: idSession="
						+ idSession
						+ ", idTransaction="
						+ idTransaction
						+ ", "
						+ e.getMessage();
				errorlogManager.insertErrorlog(processName, description);
				log.error(description, e);
				throw new MTSInterfaceException(-100, e.getMessage());
			}

		}

	}

	/************************* Metodos Privados *********************************/

	private String toString(SalaryPaymentResponseE trnResp) {
		return this.toString(trnResp.getSalaryPaymentResponse().get_return());
	}

	private String toString(ConsultaSaldoResponseE saldoResp) {
		return this.toString(saldoResp.getConsultaSaldoResponse().get_return());
	}

	private String toString(ReversionResponseE revertResp) {
		return this.toString(revertResp.getReversionResponse().get_return());
	}

	private String toString(BindPack bindResp) {
		String resp = "null";
		if (bindResp != null) {
			resp = "[Mensaje:" + bindResp.getMensaje() + ",Sesion:"
					+ bindResp.getSesion() + ",NroTrans:"
					+ bindResp.getNroTrans() + ",Status:"
					+ bindResp.getStatus() + "]";
		}
		return resp;
	}

	private String toString(TrnResponsePack trnResp) {
		String resp = "null";
		if (trnResp != null) {
			resp = "[Mensaje:" + trnResp.getMensaje() + ",IdTrans:"
					+ trnResp.getIdTransaccion() + ",NroTrans:"
					+ trnResp.getNroTrans() + ",Status:" + trnResp.getStatus()
					+ "]";
		}
		return resp;
	}

	private String toString(UnBindPack saldoResp) {
		String result = "[Status:" + saldoResp.getStatus() + ",Message:"
				+ saldoResp.getMensaje() + ",NroTrans:"
				+ saldoResp.getNroTrans();
		return result;
	}

	private List<BilleteraMts> parseToBilleteraMts(SaldoPack saldoResp) {

		List<BilleteraMts> billeteras = new ArrayList<BilleteraMts>();

		// List<BilleteraPack> billeteraMts = saldoResp.getBilleteras();
		BilleteraPack[] billeteraMts = saldoResp.getBilleteras();
		for (BilleteraPack billeteraPack : billeteraMts) {
			BilleteraMts b = new BilleteraMts();
			b.setWallet(billeteraPack.getTipoBilletera());
			b.setAmount(billeteraPack.getMonto().doubleValue());// Se convierte
																// a doble.
			billeteras.add(b);
		}

		return billeteras;
	}

	private String toString(SaldoPack saldoResp) {
		StringBuilder sb = new StringBuilder();

		sb.append("[Status:" + saldoResp.getStatus());
		sb.append(" ,Message:" + saldoResp.getMensaje());
		sb.append(" ,NroTrans:" + saldoResp.getNroTrans());
		sb.append(" ,CantBilleteras:" + saldoResp.getCantBilleteras());
		sb.append(" ,Billeteras:[");

		// List<BilleteraPack> billeteraMts = saldoResp.getBilleteras();
		BilleteraPack[] billeteraMts = saldoResp.getBilleteras();
		if (billeteraMts != null) {
			for (BilleteraPack b : billeteraMts) {
				sb.append("[TipoBill:" + b.getTipoBilletera());
				sb.append(",Monto:" + b.getMonto());
				sb.append(",SaldoMax:" + b.getSaldoMaximo());
				sb.append(",SaldoMin:" + b.getSaldoMinimo());
				sb.append(",Sobregiro:" + b.getSobregiro());
				sb.append(",FechaVenc:" + b.getFechaVencimiento());
				sb.append(",Moneda:" + b.getMoneda());
				sb.append(",Estado:" + b.getEstado() + "],");
			}
		} else {
			sb.append("[billetera:NULL],");
		}
		sb.append("]");

		return sb.toString();
	}

	private String toString(BindResponseE bindResp) {
		return this.toString(bindResp.getBindResponse().get_return());
	}

	public TransactionInfoResponse insertTransactionInfo(String idSession,
			String idTransaction, String key1, String value1, String key2,
			String value2, String key3,	String value3){
		// Conectarse
		String endPointStr = systemParameterMgr
				.getParameterValue(MtsInterfaceParameters.ENDPOINT);
		
		TransactionInfoResponse tiResponse = new TransactionInfoResponse();

		try {
			MTSServiceStub service1 = new MTSServiceStub(endPointStr);
			log.trace("OUT InserTransactionInfo:" + idSession + ","
					+ idTransaction + "," + key1 + "," + value1 + "," + key2
					+ "," + value2+ "," + key3	+ "," + value3);

			InsertTransactionInfoE inputTi = new InsertTransactionInfoE();
			// key1, value 1
			InsertTransactionInfo inTi1 = new InsertTransactionInfo();
			inTi1.setSesion(idSession);
			inTi1.setId_transaccion(idTransaction);
			inTi1.setInfo_key(key1);
			inTi1.setInfo_data(value1);
			inputTi.setInsertTransactionInfo(inTi1);
			InsertTransactionInfoResponseE tiResp1 = service1
					.insertTransactionInfo(inputTi);
			log.trace("IN Insert Transacion Info - key1 - value1");
			log.trace("IN Insert Transacion Info RESPONSE :" + tiResp1.getInsertTransactionInfoResponse().get_return().getMensaje());
			log.trace("IN Insert Transacion Info RESPONSE :" + tiResp1.getInsertTransactionInfoResponse().get_return().getNroTrans());
			
			tiResponse.setNroTransaction(tiResp1.getInsertTransactionInfoResponse().get_return().getNroTrans());
			tiResponse.setResponseStr(tiResp1.getInsertTransactionInfoResponse().get_return().getMensaje());

			// key2, value2
			InsertTransactionInfo inTi2 = new InsertTransactionInfo();
			inTi2.setSesion(idSession);
			inTi2.setId_transaccion(idTransaction);
			inTi2.setInfo_key(key2);
			inTi2.setInfo_data(value2);
			inputTi.setInsertTransactionInfo(inTi2);
			InsertTransactionInfoResponseE tiResp2 = service1
					.insertTransactionInfo(inputTi);
			log.trace("IN Insert Transacion Info - key2 - value2");
			log.trace("IN Insert Transacion Info RESPONSE :" + tiResp2.getInsertTransactionInfoResponse().get_return().getMensaje());
			log.trace("IN Insert Transacion Info RESPONSE :" + tiResp2.getInsertTransactionInfoResponse().get_return().getNroTrans());

			if (!value3.trim().isEmpty())
			{
				// key3, value3
				InsertTransactionInfo inTi3 = new InsertTransactionInfo();
				inTi3.setSesion(idSession);
				inTi3.setId_transaccion(idTransaction);
				inTi3.setInfo_key(key3);
				inTi3.setInfo_data(value3);
				inputTi.setInsertTransactionInfo(inTi3);
				InsertTransactionInfoResponseE tiResp3 = service1
						.insertTransactionInfo(inputTi);
				log.trace("IN Insert Transacion Info - key3 - value3");
				log.trace("IN Insert Transacion Info RESPONSE :" + tiResp3.getInsertTransactionInfoResponse().get_return().getMensaje());
				log.trace("IN Insert Transacion Info RESPONSE :" + tiResp3.getInsertTransactionInfoResponse().get_return().getNroTrans());
			}
		} catch (RemoteException e) {
			String description = "Error Method Insert Transacion Info: sesion="
					+ idSession + ", destino=" + key1 + ", " + e.getMessage();
			errorlogManager.insertErrorlog(processName, description);
			log.error(description, e);
		}
		return tiResponse;
	}
}
