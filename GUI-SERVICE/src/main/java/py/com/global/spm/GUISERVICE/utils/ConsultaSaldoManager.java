package py.com.global.spm.GUISERVICE.utils;

import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import py.com.global.spm.model.ws.managers.ConsultaSaldoWS;


@Component("consultaSaldoManager")
public class ConsultaSaldoManager {
//
//	@Autowired
//
//	private static final Logger log = LoggerFactory.getLogger(ConsultaSaldoManager.class);
//
//	public String getConsultarSaldoEmpresa(long idCompany) {
//		Double saldo = null;
//		if (systemparameterHelper.isConsultaSaldoDummy()) {
//			saldo = systemparameterHelper.getConsultaSaldoMonto();
//		} else {
//			saldo = this.consultarSaldo(idCompany);
//		}
//		if (saldo != null) {
//			return this.formatNumber(saldo);
//		} else {
//			log.warn("Error consultando saldo --> idCompany=" + idCompany);
//		}
//		return "No se logro consultar saldo empresa";
//	}
//
//	private String formatNumber(Double d) {
//		if (d != null) {
//			NumberFormat format = NumberFormat
//					.getNumberInstance(Locale.GERMANY);
//			return format.format(d);
//		}
//		return "null";
//	}
//
//	private Double consultarSaldo(long idCompany) {
//		String code = systemparameterHelper.getConsultaSaldoValidationCode();
//		String endpoint = systemparameterHelper.getConsultaSaldoEndpoint();
//		//
//		Double saldo = null;
//		try {
//			/**
//			 * TODO: agregar como dependencia a SPM-ConsultaSaldoClient
//			 */
//			ConsultaSaldoWSService service = new ConsultaSaldoWSService();
//			ConsultaSaldoWS consultaSaldoWSPort = service.getConsultaSaldoWSPort();
//			BindingProvider bp = (BindingProvider) consultaSaldoWSPort;
//			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
//					endpoint);
//
//			saldo = consultaSaldoWSPort.consultaSaldo(code, idCompany);
//			//saldo = new Double(5000000L);
//		} catch (Exception e) {
//			log.error("Error invocando ws de consulta de saldo --> idCompany="
//					+ idCompany + ", endpoint=" + endpoint, e);
//		}
//		return saldo;
//	}

}
