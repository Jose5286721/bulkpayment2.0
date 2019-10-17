package py.com.global.spm.model.mts;

import com.global.common.helper.Configuration;
import com.global.common.helper.ObjectFactory;
import com.global.spm.drivertransaction.IDriverMonetaryTransaction;
import com.global.spm.drivertransaction.RequestForReverseTransaction;
import com.global.spm.drivertransaction.RequestForTransferTransaction;
import com.global.spm.drivertransaction.ResponseMonetaryTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.services.ErrorLogService;
import py.com.global.spm.model.services.SystemParameterService;
import py.com.global.spm.model.systemparameters.DriversParameters;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MtsService {
	protected static Logger logger = LogManager.getLogger(MtsService.class);

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private SystemParameterService systemParameterService;
	@Autowired
	private ErrorLogService errorLogService;

	// Driver Factory
	private ObjectFactory factory = null;

	// Driver Classpath en ejecucion
	private String driverClasspath = null;

	// Parametrso del driver
	private Map<String, String> parameters = null;

	private final String processName = "MtsManager";
	private boolean isDummy;

	/**
	 * Inicializar factory
	 */
	/**
	 * Inicializar factory
	 */
	@PostConstruct
	private void initialize() {
		// Buscar driver en parametros del sistema
		String classpath = systemParameterService.getParameterValue(SpmProcesses.DRIVER_MTS,
				DriversParameters.MONETARYTRANSACTION_DRIVERCLASSPATH.getDataBase());
		if (classpath == null) {
			logger.error(
					"Se requiere parametro --> " + DriversParameters.MONETARYTRANSACTION_DRIVERCLASSPATH.getDataBase());
		} else {
			// Asegurar inicializacion
			synchronized (this) {
				if (this.driverClasspath == null || !this.driverClasspath.equals(classpath)) {
					this.driverClasspath = classpath;
					// Inicializar object factory
					logger.info("Cargando driver --> " + this.driverClasspath);
					if (this.factory == null)
						this.factory = new ObjectFactory(driverClasspath, applicationContext.getClassLoader());
					else
						this.factory.reset(driverClasspath);
				}

				// Buscar y verificar los parametros
				ConcurrentHashMap<String, String> param = this.systemParameterService
						.getParameters(SpmProcesses.DRIVER_MTS);
				if (this.parameters == null
						|| Configuration.hashCode(this.parameters) != Configuration.hashCode(param)) {
					this.factory.reset();
					this.parameters = param;
					IDriverMonetaryTransaction driver = (IDriverMonetaryTransaction) factory.getInstance();
					if (driver == null) {
						logger.error("Driver Validar Agente no instanciado");
					} else {
						logger.info("Cargando parametros en el driver, Process Id --> "
								+ SpmProcesses.DRIVER_MTS);
						driver.initialize(this.parameters);
					}
				}
			}
		}
	}
	public ReverseResponse reverse(RequestForReverseTransaction request) throws MTSInterfaceException {
		String isDummy = systemParameterService.getParameterValue(
				MtsInterfaceParameters.IS_DUMMY.getIdProcess(),MtsInterfaceParameters.IS_DUMMY.getParameterName(), SpmConstants.NO);
		ReverseResponse resp = new ReverseResponse();
		if (isDummy.equalsIgnoreCase(SpmConstants.YES)) {
			logger.info("MtsManager DUMMY-->reverseTransation=" );
			resp.setNroTransaction(4345322);
			resp.setResponseStr("Es una reversion dummy.");
		} else {
			ResponseMonetaryTransaction response = executeReverse(request);
			ReverseResponse reverseResponse = new ReverseResponse();
			if (response == null)
				throw new MTSInterfaceException(-1L, "Respuesta es null.");
			else {
				reverseResponse.setNroTransaction(Integer.valueOf(response.getMonetaryTransactionId()));
				reverseResponse.setResponseStr(response.toString());
			}
		}
		return resp;

	}

	public SalaryPaymentResponse transfer(RequestForTransferTransaction request) throws MTSInterfaceException {
		String isDummy = systemParameterService.getParameterValue(
				MtsInterfaceParameters.IS_DUMMY.getIdProcess(),MtsInterfaceParameters.IS_DUMMY.getParameterName(), SpmConstants.NO);
		//isDummy = !isDummy;
		if (isDummy.equalsIgnoreCase(SpmConstants.YES)/*isDummy*/) {
			logger.info("IN Salary Payment:" + request.getSessionId() + "," + request.getAccount() + ","
					 + request.getAmount()+ ","
					+ request.getCollectAccount());
			SalaryPaymentResponse slr = new SalaryPaymentResponse();
			slr.setNroTransaction((new Random()).nextInt());
			slr.setResponseStr("Cadena de Respuesta.");
			return slr;
		} else {
			ResponseMonetaryTransaction response = executeTransfer(request);
			if (response == null)
				throw new MTSInterfaceException(-1L, "Respuesta es null.");
			else if (response.getStatusCode().equals("503")){
				String description = "Error Method Salary Payment: sesion="
						+ request.getSessionId() + ", destino=" + request.getAccount();
				errorLogService.insertErrorlog(processName, description);
				logger.error(description, response.getDescription());
				throw new MTSInterfaceException(-100L,response.getDescription());
			}else if(!response.getStatusCode().equals("200")){
				String description = "Error Method Salary Payment: Status:"
						+ response.getStatusCode()
						+ ", sesion="
						+ request.getSessionId()
						+ ", destino="
						+ request.getAccount()
						+ ", Message:"
						+ response.getDescription();
				errorLogService.insertErrorlog(processName, description);
				logger.error(description);
				throw new MTSInterfaceException(Long.parseLong(response.getStatusCode()), response.getDescription());
			}else {
				SalaryPaymentResponse slr = new SalaryPaymentResponse();
				slr.setNroTransaction(Integer.parseInt(response.getMonetaryTransactionId()));
				slr.setResponseStr(response.toString());
				return slr;
			}
		}
	}


	private ResponseMonetaryTransaction executeTransfer(RequestForTransferTransaction request) {
		ResponseMonetaryTransaction response = null;

		IDriverMonetaryTransaction driver = getInstance();
		if (driver == null) {
			logger.error("Driver no instanciado");
		} else {
			// Asignar atributos del driver
			response = driver.executeTransfer(request);
		}

		return response;
	}

	private ResponseMonetaryTransaction executeReverse(RequestForReverseTransaction request) {
		ResponseMonetaryTransaction response = null;

		IDriverMonetaryTransaction driver = getInstance();
		if (driver == null) {
			logger.error("Driver no instanciado");
		} else {
			// Asignar atributos del driver
			response = driver.executeReverse(request);
		}

		return response;
	}

	private IDriverMonetaryTransaction getInstance() {
		IDriverMonetaryTransaction driver = null;
		if (this.factory == null)
			initialize();
		synchronized (this) {
			if (this.factory != null)
				driver = (IDriverMonetaryTransaction) this.factory.getInstance();
		}
		return driver;
	}
}
