package py.com.global.spm.model.ws.managers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Use;

import org.apache.log4j.Logger;
import org.jboss.wsf.spi.annotation.WebContext;

import py.com.global.spm.model.interfaces.ConsultaSaldoWSLocal;
import py.com.global.spm.model.interfaces.MTSTransferInterfaceManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.util.SpmProcesses;

/**
 * Session Bean implementation class ConsultaSaldoWs
 * 
 * @author Lino Chamorro
 */
@WebContext(transportGuarantee = "CONFIDENTIAL", secureWSDLAccess = false)
@WebService(targetNamespace = "urn:spm.global.com.py", name = "ConsultaSaldoWS")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = Use.LITERAL)
@Stateless
public class ConsultaSaldoWS implements ConsultaSaldoWSLocal {

	private final Logger log = Logger.getLogger(ConsultaSaldoWS.class);
	@EJB
	private MTSTransferInterfaceManagerLocal mtsManager;
	@EJB
	private SystemparameterManagerLocal systemParameterManager;

	/**
	 * Default constructor.
	 */
	public ConsultaSaldoWS() {
	}

	@WebMethod(action = "urn:spm.global.com.py:ConsultaSaldoWS:consultaSaldo")
	@WebResult(name = "consultaSaldoResponse")
	@Override
	public Double consultaSaldo(@WebParam(name = "code") String code,
			@WebParam(name = "idCompany") long idCompany) {
		String validationCode = systemParameterManager.getParameterValue(
				SpmProcesses.GUI, "consultaSaldoValidationCode");
		if (code == null || !code.equalsIgnoreCase(validationCode)) {
			log.warn("invalid validation code --> code=" + code
					+ ", idCompany=" + idCompany);
			return -1D;
		}
		return this.mtsManager.getBalanceCompany(idCompany);
	}

}
