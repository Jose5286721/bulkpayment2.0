package com.global.spm.mfs;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.global.common.helper.Configuration;
import com.global.common.helper.IParameter;
import com.global.common.helper.Print;
import com.global.common.ws.PoolingHttpClient;
import com.global.common.ws.ResponseHttpClient;
import com.global.common.ws.soap.FasterXMLMapper;
import com.global.spm.drivertransaction.IDriverMonetaryTransaction;
import com.global.spm.drivertransaction.RequestForReverseTransaction;
import com.global.spm.drivertransaction.RequestForTransferTransaction;
import com.global.spm.drivertransaction.ResponseMonetaryTransaction;
import com.global.spm.mfs.doc.MfsRequest;
import com.global.spm.mfs.doc.MfsRequestEnvelope;
import com.global.spm.mfs.doc.MfsResponse;
import com.global.spm.mfs.doc.MfsResponseBody;
import com.global.spm.mfs.doc.MfsResponseHeader;
import com.global.spm.mfs.doc.collect.MfsCollectRequestBody;
import com.global.spm.mfs.doc.collect.MfsCollectResponseEnvelop;
import com.global.spm.mfs.doc.reversion.MfsReverseRequestBody;
import com.global.spm.mfs.doc.reversion.MfsReverseResponseEnvelop;
import com.global.spm.mfs.util.Parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion (SINGLETON) del Driver Monetary Transaction para Tigo Py
 * 
 * @author hugo
 *
 */
public class DriverMfsTigoPy implements IDriverMonetaryTransaction {
	private static Logger logger = LogManager.getLogger(DriverMfsTigoPy.class);

	public static final String XMLNS_SCH_COLLECTSCHEMA = "http://xmlns.tigo.com/MFS/MFSServiceRequest/V2/schema";
	public static final String XMLNS_SCH_REVERSESCHEMA = "http://xmlns.tigo.com/MFS/MFSServiceRequest/V2/schema";

	private PoolingHttpClient callWS = null;
	private Integer maxTotal = 0;
	private Integer maxPerRoute = 0;
	private String collectUrl = null;
	private String reverseUrl = null;
	private String successRegExpresion = null;
	private String country = null;

	@Override
	public void initialize(Map<String, String> parameters) {
		Configuration conf = new Configuration(parameters);

		// URLs de servicios
		this.collectUrl = conf.value(Parameter.COLLECT_SERVICEURL);
		this.reverseUrl = conf.value(Parameter.REVERSE_SERVICEURL);

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
		if (this.collectUrl == null)
			logger.warn("Se requiere el parametro --> " + Parameter.COLLECT_SERVICEURL.key());
		else
			logger.info("Parametro " + Parameter.COLLECT_SERVICEURL.key() + " --> " + this.collectUrl);

		if (this.reverseUrl == null)
			logger.warn("Se requiere el parametro --> " + Parameter.REVERSE_SERVICEURL.key());
		else
			logger.info("Parametro " + Parameter.REVERSE_SERVICEURL.key() + " --> " + this.reverseUrl);

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
	public ResponseMonetaryTransaction executeTransfer(RequestForTransferTransaction request) {
		ResponseMonetaryTransaction responseMonetaryTransaction = getResponse(Parameter.STATUS_CODE_GENERALERROR);

		// Verificar request
		if (request == null) {
			logger.error("Valor incorrecto del Request");
		} else // Verificar comunicacion
		if (this.callWS == null) {
			logger.error("Fallo en la conexion Web Service");
		} else // Verificar URL
		if (this.collectUrl == null) {
			logger.error("No parametrizado la url del servicio --> " + Parameter.COLLECT_SERVICEURL.key());
		} else {
			ResponseHttpClient response = null;
			try {
				MfsRequestEnvelope<MfsCollectRequestBody> envelope = new MfsRequestEnvelope<MfsCollectRequestBody>();
				MfsCollectRequestBody collectBody = new MfsCollectRequestBody();
				MfsRequest collectRequest = new MfsRequest();
				MfsRequest.MfsRequestHeader header = collectRequest.new MfsRequestHeader();
				MfsRequest.MfsRequestBody body = collectRequest.new MfsRequestBody();
				MfsRequest.MfsRequestHeader.GeneralConsumerInformation consumerInformation = header.new GeneralConsumerInformation();

				envelope.setSch(XMLNS_SCH_COLLECTSCHEMA);

				consumerInformation.setConsumerID(request.getServiceName());
				consumerInformation.setCountry(this.country);
				consumerInformation.setTransactionID(request.getRpmTransactionId());
				consumerInformation.setCorrelationID(request.getSessionId());
				header.setGeneralConsumerInformation(consumerInformation);

				body.setMsisdn(request.getAccount());
				body.setPin(request.getAccountPin());
				body.setAgendId(request.getCollectAccount());
				body.setAgendPin(request.getCollectAccountPin());
				BigDecimal amount = request.getAmount();
				if (amount != null)	
					body.setAmount(String.format(Locale.US, "%.3f", amount));
				body.setPriceModel(request.getCollectProfile());
				body.setParameterType("ENTITY_TYPE", "NOMBRE_COMERCIO");
				body.setParameterType("ENTITY", request.getCommerceName());
				body.setParameterType("SERVICE_TYPE", "SERVICE_DESCRIPTION");
				body.setParameterType("SERVICE", request.getServiceName());

				collectRequest.setRequestHeader(header);
				collectRequest.setRequestBody(body);
				collectBody.setMfsCollectRequest(collectRequest);
				envelope.setBody(collectBody);

				FasterXMLMapper mapper = new FasterXMLMapper();
				mapper.isNameSpaceAware(false);
				mapper.isFailOnUnknownProperties(false);
				mapper.setSerializationInclusion(Include.NON_NULL);
				// mapper.setSerializationFeature(SerializationFeature.INDENT_OUTPUT);

				// Imprimir en pantalla
				String soapEnvBody = mapper.writeValueAsString(envelope);
				if (soapEnvBody == null) {
					logger.error("No se ha generado el cuerpo del Request");
				} else {
					logger.debug("Request --> " + soapEnvBody);
					response = this.callWS.doSoapPost(this.collectUrl, null, soapEnvBody);
					if (response == null) {
						logger.error("No hubo respuesta a la ejecucion del Web Service");
						responseMonetaryTransaction.setStatusCode(Parameter.STATUS_CODE_CONNECTIONERROR.key());
					} else {
						logger.trace("Response --> " + response);
						if (response.getStatusCode() != 200) {
							logger.error("Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
							responseMonetaryTransaction.setStatusCode(String.valueOf(response.getStatusCode()));
						} else {
							mapper.isNameSpaceAware(true);
							MfsCollectResponseEnvelop collectResponse = (MfsCollectResponseEnvelop) mapper
									.readValue(response.getResponse(), MfsCollectResponseEnvelop.class);
							if (collectResponse == null) {
								logger.error("Fallo al interpretar la respuesta");
							} else { // Tomar datos de la respuesta
								logger.debug("Response --> " + collectResponse);
								MfsCollectResponseEnvelop.MfsCollectResponseBody responseBody = collectResponse
										.getBody();
								MfsResponse mfsCollectResponse = null;
								if (responseBody == null) {
									logger.error("Fallo al localizar la respuesta -Body-");
								} else // Verificar el response
								if ((mfsCollectResponse = responseBody.getResponse()) == null) {
									logger.error("Fallo al localizar la respuesta -mfsCollectResponse-");
								} else {
									responseMonetaryTransaction = getResponse(mfsCollectResponse.getHeader(),
											mfsCollectResponse.getBody());
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

		return responseMonetaryTransaction;
	}

	@Override
	public ResponseMonetaryTransaction executeReverse(RequestForReverseTransaction request) {
		ResponseMonetaryTransaction responseMonetaryTransaction = getResponse(Parameter.STATUS_CODE_GENERALERROR);

		// Verificar request
		if (request == null) {
			logger.error("Valor incorrecto del request");
		} else // Verificar comunicacion
		if (this.callWS == null) {
			logger.error("Fallo en la conexion Web Service");
		} else //
		if (this.reverseUrl == null) {
			logger.error("No parametrizado la url del servicio --> " + Parameter.REVERSE_SERVICEURL.key());
		} else {
			ResponseHttpClient response = null;
			try {
				MfsRequestEnvelope<MfsReverseRequestBody> envelope = new MfsRequestEnvelope<MfsReverseRequestBody>();
				MfsReverseRequestBody reverseBody = new MfsReverseRequestBody();

				MfsRequest reverseRequest = new MfsRequest();
				MfsRequest.MfsRequestHeader header = reverseRequest.new MfsRequestHeader();
				MfsRequest.MfsRequestBody body = reverseRequest.new MfsRequestBody();
				MfsRequest.MfsRequestHeader.GeneralConsumerInformation consumerInformation = header.new GeneralConsumerInformation();

				consumerInformation.setConsumerID(request.getServiceName());
				consumerInformation.setCountry(this.country);
				consumerInformation.setTransactionID(request.getRpmTransactionId());
				consumerInformation.setCorrelationID(request.getSessionId());
				header.setGeneralConsumerInformation(consumerInformation);

				envelope.setSch(XMLNS_SCH_REVERSESCHEMA);

				body.setMsisdn(request.getCollectAccount());
				body.setPin(request.getCollectAccountPin());

				reverseRequest.setRequestHeader(header);
				reverseRequest.setRequestBody(body);
				reverseBody.setMfsReverseRequest(reverseRequest);
				envelope.setBody(reverseBody);

				FasterXMLMapper mapper = new FasterXMLMapper();
				mapper.isNameSpaceAware(false);
				mapper.isFailOnUnknownProperties(false);
				mapper.setSerializationInclusion(Include.NON_NULL);
				// mapper.setSerializationFeature(SerializationFeature.INDENT_OUTPUT);

				// Imprimir en pantalla
				String soapEnvBody = mapper.writeValueAsString(envelope);
				if (soapEnvBody == null) {
					logger.error("No se ha generado el cuerpo del Request");
				} else {
					logger.debug("Request --> " + soapEnvBody);
					response = this.callWS.doSoapPost(this.reverseUrl, null, soapEnvBody);
					if (response == null) {
						logger.error("No hubo respuesta a la ejecucion del Web Service");
						responseMonetaryTransaction.setStatusCode(Parameter.STATUS_CODE_CONNECTIONERROR.key());
					} else {
						logger.trace("Response --> " + response);
						if (response.getStatusCode() != 200) {
							logger.error("Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
							responseMonetaryTransaction.setStatusCode(String.valueOf(response.getStatusCode()));
						} else {
							mapper.isNameSpaceAware(true);
							MfsReverseResponseEnvelop validatePinResponse = (MfsReverseResponseEnvelop) mapper
									.readValue(response.getResponse(), MfsReverseResponseEnvelop.class);
							if (validatePinResponse == null) {
								logger.error("Fallo al interpretar la respuesta");
							} else { // Tomar datos de la respuesta
								logger.debug("Response --> " + validatePinResponse);
								MfsReverseResponseEnvelop.MfsReverseResponseBody responseBody = validatePinResponse
										.getBody();
								MfsResponse mfsCollectResponse = null;
								if (responseBody == null) {
									logger.error("Fallo al intentar localizar la respuesta -Body-");
								} else // Verificar respuesta del Body
								if ((mfsCollectResponse = responseBody.getResponse()) == null) {
									logger.error("Fallo al intentar localizar la respuesta -mfsCollectResponse-");
								} else {
									responseMonetaryTransaction = getResponse(mfsCollectResponse.getHeader(),
											mfsCollectResponse.getBody());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("Fallo en ejecucion --> " + e.getMessage());
				Print.toString(e);
			}

		}

		return responseMonetaryTransaction;
	}

	/**
	 * Procesar la respuesta
	 * 
	 * @param header
	 * @param body
	 * @return
	 */
	private ResponseMonetaryTransaction getResponse(MfsResponseHeader header, MfsResponseBody body) {
		ResponseMonetaryTransaction response = null;

		MfsResponseHeader.MfsGeneralResponse generalResponse = null;
		// Verificar header
		if (header == null) {
			logger.error("Fallo en el valor del header -ResponseHeader-");
		} else // Tomar datos del header
		if ((generalResponse = header.getMfsGeneralResponse()) == null) {
			logger.error("Fallo al localizar la respuesta -GeneralResponse-");
		} else { //
			response = new ResponseMonetaryTransaction();
			response.setStatus(isSuccess(generalResponse.getStatus()));
			response.setStatusCode(generalResponse.getCode());
			response.setDescription(generalResponse.getDescription());
			if (body != null)
				response.setMonetaryTransactionId(body.getTransactionId());
		}

		return response;
	}

	/**
	 * Procesar
	 * 
	 * @param parameter
	 * @return
	 */
	private ResponseMonetaryTransaction getResponse(IParameter parameter) {
		ResponseMonetaryTransaction response = null;

		if (parameter != null) {
			response = new ResponseMonetaryTransaction();
			response.setStatus(false);
			response.setStatusCode(parameter.key());
			response.setDescription(parameter.defaultValue());
		}

		return response;
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

	public void destroy() {
		if (this.callWS != null)
			this.callWS.shutdown();
		this.callWS = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverMfsTigoPy [maxTotal=");
		builder.append(maxTotal);
		builder.append(", maxPerRoute=");
		builder.append(maxPerRoute);
		builder.append(", ");
		if (successRegExpresion != null) {
			builder.append("successRegExpresion=");
			builder.append(successRegExpresion);
			builder.append(", ");
		}
		if (country != null) {
			builder.append("country=");
			builder.append(country);
			builder.append(", ");
		}
		if (collectUrl != null) {
			builder.append("collectUrl=");
			builder.append(collectUrl);
			builder.append(", ");
		}
		if (reverseUrl != null) {
			builder.append("reverseUrl=");
			builder.append(reverseUrl);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

}
