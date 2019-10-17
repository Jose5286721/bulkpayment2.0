package com.global.spm.mfs;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.global.common.helper.Configuration;
import com.global.common.helper.Print;
import com.global.common.ws.PoolingHttpClient;
import com.global.common.ws.ResponseHttpClient;
import com.global.common.ws.soap.FasterXMLMapper;
import com.global.spm.drivertransaction.IDriverValidatePin;
import com.global.spm.drivertransaction.RequestForValidatePin;
import com.global.spm.mfs.doc.MfsRequest;
import com.global.spm.mfs.doc.MfsRequestEnvelope;
import com.global.spm.mfs.doc.MfsResponse;
import com.global.spm.mfs.doc.MfsResponseBody;
import com.global.spm.mfs.doc.MfsResponseHeader;
import com.global.spm.mfs.doc.validatepin.MfsValidatePinRequestBody;
import com.global.spm.mfs.doc.validatepin.MfsValidatePinResponseBody;
import com.global.spm.mfs.doc.validatepin.MfsValidatePinResponseEnvelop;
import com.global.spm.mfs.util.Parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion (SINGLETON) del Driver Validate PIN para Tigo Py
 * 
 * @author hugo
 *
 */
public class DriverValidatePinTigoPy implements IDriverValidatePin {
	protected static Logger logger = LogManager.getLogger(DriverValidatePinTigoPy.class);

	public static final String XMLNS_SCH_VALIDATEPINSCHEMA = "http://xmlns.tigo.com/MFS/PinManagementRequest/V1/schema";

	// Correlation ID
	private static long correlationId = 0;

	// Atributos del objeto
	private PoolingHttpClient callWS = null;
	private Integer maxTotal = 0;
	private Integer maxPerRoute = 0;
	private String successRegExpresion = null;
	private String validatePinUrl = null;
	private String country = null;

	@Override
	public void initialize(Map<String, String> parameters) {
		Configuration conf = new Configuration(parameters);

		// Extraer parametros
		// URLs de servicios
		this.validatePinUrl = conf.value(Parameter.VALIDATEPIN_SERVICEURL);

		// Parametros de conexion
		this.maxTotal = conf.intValue(Parameter.CONNECTION_MAXTOTAL);
		this.maxPerRoute = conf.intValue(Parameter.CONNECTION_MAXPERROUTE);
		this.country = conf.value(Parameter.COUNTRY);
		this.successRegExpresion = conf.value(Parameter.SUCCESS_REGULAREXPRESSION);

		// Inicializar CallWebService
		if (this.callWS == null)
			this.callWS = new PoolingHttpClient();

		this.callWS.setMaxTotalConnections(this.maxTotal);
		this.callWS.setMaxConnectionsPerRoute(this.maxPerRoute);

		logger.debug(this);

		// Verificar parametros cargados
		checkParameters();
	}

	/*
	 * Verificar parametros cargados
	 */
	private void checkParameters() {
		if (this.validatePinUrl == null)
			logger.warn("Se requiere el parametro --> " + Parameter.VALIDATEPIN_SERVICEURL.key());
		else
			logger.info("Parametro " + Parameter.VALIDATEPIN_SERVICEURL.key() + " --> " + this.validatePinUrl);

		if (this.country == null)
			logger.warn("Se requiere el parametro --> " + Parameter.COUNTRY.key());
		else
			logger.info("Parametro " + Parameter.COUNTRY.key() + " --> " + this.country);

		if (this.successRegExpresion == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SUCCESS_REGULAREXPRESSION.key());
		else
			logger.info("Parametro " + Parameter.SUCCESS_REGULAREXPRESSION.key() + " --> " + this.successRegExpresion);

		if (this.maxPerRoute == null)
			logger.warn("Se requiere el parametro --> " + Parameter.CONNECTION_MAXPERROUTE.key());
		else
			logger.info("Parametro " + Parameter.CONNECTION_MAXPERROUTE.key() + " --> " + this.maxPerRoute);

		if (this.maxTotal == null)
			logger.warn("Se requiere el parametro --> " + Parameter.CONNECTION_MAXTOTAL.key());
		else
			logger.info("Parametro " + Parameter.CONNECTION_MAXTOTAL.key() + " --> " + this.maxTotal);
	}

	@Override
	public Boolean executeValidatePin(RequestForValidatePin request) {
		Boolean ready = null;

		// Verificar request
		if (request == null) {
			logger.error("Valor incorrecto del Request");
		} else // Verificar comunicacion
		if (this.callWS == null) {
			logger.error("Fallo en la conexion Web Service");
		} else //
		if (this.validatePinUrl == null) {
			logger.error("Se requiere la url del servicio --> " + Parameter.VALIDATEPIN_SERVICEURL.key());
		} else {
			ResponseHttpClient response = null;
			try {
				MfsRequestEnvelope<MfsValidatePinRequestBody> envelope = new MfsRequestEnvelope<MfsValidatePinRequestBody>();
				MfsValidatePinRequestBody validatePinBody = new MfsValidatePinRequestBody();

				MfsRequest validatePinRequest = new MfsRequest();
				MfsRequest.MfsRequestHeader header = validatePinRequest.new MfsRequestHeader();
				MfsRequest.MfsRequestBody body = validatePinRequest.new MfsRequestBody();
				MfsRequest.MfsRequestHeader.GeneralConsumerInformation consumerInformation = header.new GeneralConsumerInformation();

				consumerInformation.setConsumerID("spm");
				consumerInformation.setCountry(this.country);
				consumerInformation.setTransactionID(request.getSessionId());
				consumerInformation.setCorrelationID(getCorrelationId());
				header.setGeneralConsumerInformation(consumerInformation);

				envelope.setSch(XMLNS_SCH_VALIDATEPINSCHEMA);

				body.setMsisdn(request.getAccount());
				body.setPin(request.getPin());
				body.setIsAgent(request.getAgent());

				validatePinRequest.setRequestHeader(header);
				validatePinRequest.setRequestBody(body);
				validatePinBody.setValidatePinRequest(validatePinRequest);
				envelope.setBody(validatePinBody);

				FasterXMLMapper mapper = new FasterXMLMapper();
				mapper.isNameSpaceAware(false);
				mapper.isFailOnUnknownProperties(false);
				mapper.setSerializationInclusion(Include.NON_NULL);

				// Imprimir en pantalla
				String soapEnvBody = mapper.writeValueAsString(envelope);
				if (soapEnvBody == null) {
					logger.error("No se ha generado el cuerpo del Request");
				} else {
					logger.debug("Request --> " + soapEnvBody);
					response = this.callWS.doSoapPost(this.validatePinUrl, null, soapEnvBody);
					if (response == null) {
						logger.error("No hubo respuesta a la ejecucion del Web Service");
					} else {
						logger.debug("Response --> " + response);
						if (response.getStatusCode() != 200) {
							logger.error("Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
						} else {
							ready = false;
							mapper.isNameSpaceAware(true);
							MfsValidatePinResponseEnvelop validatePinResponse = (MfsValidatePinResponseEnvelop) mapper
									.readValue(response.getResponse(), MfsValidatePinResponseEnvelop.class);
							if (validatePinResponse == null) {
								logger.error("Fallo al interpretar la respuesta");
							} else { // Tomar datos de la respuesta
								System.out.println("Respuesta --> " + validatePinResponse);
								MfsValidatePinResponseBody responseBody = validatePinResponse.getBody();
								MfsResponse mfsCollectResponse = null;
								if (responseBody == null) {
									logger.error("Fallo al localizar la respuesta -Body-");
								} else //
								if ((mfsCollectResponse = responseBody.getResponse()) == null) {
									logger.error("Fallo al localizar la respuesta -mfsCollectResponse-");
								} else {
									ready = getResponse(mfsCollectResponse.getHeader(), mfsCollectResponse.getBody());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("Fallo en la ejecucion --> " + e.getMessage());
				logger.debug(Print.toString(e));
			}
		}

		return ready;
	}

	/*
	 * Obtener valor para el correlation ID
	 */
	private long getCorrelationId() {
		long value = 0;

		synchronized (this) {
			value = ++DriverValidatePinTigoPy.correlationId;
		}

		return value;
	}

	private boolean getResponse(MfsResponseHeader header, MfsResponseBody body) {
		boolean ready = false;

		MfsResponseHeader.MfsGeneralResponse generalResponse = null;
		// Verificar header
		if (header == null) {
			logger.error("Valor incorrecto del Request -ResponseHeader-");
		} else // Tomar datos del header
		if ((generalResponse = header.getMfsGeneralResponse()) == null) {
			logger.error("Fallo al localizar la respuesta -GeneralResponse-");
		} else { // Verificar valor del estatus
			ready = isSuccess(generalResponse.getStatus());
		}

		return ready;
	}

	/**
	 * Verificar cadena en busca de una palabra que indique error
	 * 
	 * @param message
	 * @return
	 */
	private boolean isSuccess(String message) {
		boolean ready = false;

		if (message != null && this.successRegExpresion != null) {
			Pattern p = Pattern.compile(this.successRegExpresion);
			Matcher m = p.matcher(message);
			ready = (m != null && m.find());
		}

		return ready;
	}

	@Override
	public void destroy() {
		if (this.callWS != null)
			this.callWS.shutdown();
		this.callWS = null;
		maxTotal = null;
		maxPerRoute = null;
		successRegExpresion = null;
		validatePinUrl = null;
		country = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverValidatePinTigoPy [maxTotal=");
		builder.append(maxTotal);
		builder.append(", maxPerRoute=");
		builder.append(maxPerRoute);
		builder.append(", ");
		if (country != null) {
			builder.append("country=");
			builder.append(country);
			builder.append(", ");
		}
		if (successRegExpresion != null) {
			builder.append("successRegExpresion=");
			builder.append(successRegExpresion);
			builder.append(", ");
		}
		if (validatePinUrl != null) {
			builder.append("validatePinUrl=");
			builder.append(validatePinUrl);
		}
		builder.append("]");
		return builder.toString();
	}

}
