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
import com.global.spm.drivertransaction.IDriverValidateAgent;
import com.global.spm.drivertransaction.RequestForValidateAgent;
import com.global.spm.mfs.doc.MfsRequest;
import com.global.spm.mfs.doc.MfsRequestEnvelope;
import com.global.spm.mfs.doc.MfsResponseHeader;
import com.global.spm.mfs.doc.agentinfo.MfsAgentInfoRequestBody;
import com.global.spm.mfs.doc.agentinfo.MfsAgentInfoResponseEnvelope;
import com.global.spm.mfs.util.Parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion (SINGLETON) del Driver Validate Agent para Tigo Py
 * 
 * @author hugo
 *
 */
public class DriverAgentInfoTigoPy implements IDriverValidateAgent {
	protected static Logger logger = LogManager.getLogger(DriverAgentInfoTigoPy.class);

	public static final String XMLNS_SCH_AGENTINFOSCHEMA = "http://xmlns.tigo.com/MFS/GetAgentInfo/V1/schema";
	public static final String XMLNS_V1_AGENTINFOSCHEMA = "http://www.tigo.com/CredentialsType/V1";

	// Correlation ID
	private static long correlationId = 0;

	// Atributos del objeto
	private PoolingHttpClient callWS = null;
	private Integer maxTotal = 0;
	private Integer maxPerRoute = 0;
	private String successRegExpresion = null;
	private String statusActive = null;
	private String country = null;
	private String agentInfoUrl = null;
	private String userName = null;
	private String password = null;

	@Override
	public void initialize(Map<String, String> parameters) {
		if (parameters != null) {
			Configuration conf = new Configuration(parameters);

			// Extraer parametros
			// URLs de servicios
			this.agentInfoUrl = conf.value(Parameter.AGENTINFO_SERVICEURL);

			// Parametros de conexion
			this.maxTotal = conf.intValue(Parameter.CONNECTION_MAXTOTAL);
			this.maxPerRoute = conf.intValue(Parameter.CONNECTION_MAXPERROUTE);
			this.country = conf.value(Parameter.COUNTRY);
			this.successRegExpresion = conf.value(Parameter.SUCCESS_REGULAREXPRESSION);
			this.statusActive = conf.value(Parameter.AGENTINFO_STATUSACTIVE);
			this.userName = conf.value(Parameter.AGENTINFO_USERNAME);
			this.password = conf.value(Parameter.AGENTINFO_PASSWORD);

			// Inicializar CallWebService
			if (this.callWS == null)
				this.callWS = new PoolingHttpClient();

			this.callWS.setMaxTotalConnections(this.maxTotal);
			this.callWS.setMaxConnectionsPerRoute(this.maxPerRoute);

			logger.debug(this);

			// Verificar parametros cargados
			checkParameters();
		}
	}

	/*
	 * Verificar parametros cargados
	 */
	private void checkParameters() {
		if (this.agentInfoUrl == null)
			logger.warn("Se requiere el parametro --> " + Parameter.AGENTINFO_SERVICEURL.key());
		else
			logger.info("Parametro " + Parameter.AGENTINFO_SERVICEURL.key() + " --> " + this.agentInfoUrl);

		if (this.country == null)
			logger.warn("Se requiere el parametro --> " + Parameter.COUNTRY.key());
		else
			logger.info("Parametro " + Parameter.COUNTRY.key() + " --> " + this.country);

		if (this.successRegExpresion == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SUCCESS_REGULAREXPRESSION.key());
		else
			logger.info("Parametro " + Parameter.SUCCESS_REGULAREXPRESSION.key() + " --> " + this.successRegExpresion);

		if (this.userName == null)
			logger.warn("Se requiere el parametro --> " + Parameter.AGENTINFO_USERNAME.key());
		else
			logger.info("Parametro " + Parameter.AGENTINFO_USERNAME.key() + " --> " + this.userName);

		if (this.password == null)
			logger.warn("Se requiere el parametro --> " + Parameter.AGENTINFO_PASSWORD.key());
		else
			logger.info("Parametro " + Parameter.AGENTINFO_PASSWORD.key() + " --> " + this.password);

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
	public Boolean executeValidateAgent(RequestForValidateAgent request) {
		Boolean ready = null;

		// Verificar request
		if (request == null) {
			logger.error("Valor incorrecto del Request");
		} else // Verificar comunicacion
		if (this.callWS == null) {
			logger.error("Fallo en la conexion Web Service");
		} else // Verificar la URL
		if (this.agentInfoUrl == null) {
			logger.error("Se requiere la url del servicio --> " + Parameter.AGENTINFO_SERVICEURL.key());
		} else // Verificar el usuario
		if (this.userName == null) {
			logger.error("Se requiere el parametro --> " + Parameter.AGENTINFO_USERNAME.key());
		} else // Verificar el password
		if (this.password == null) {
			logger.error("Se requiere el parametro --> " + Parameter.AGENTINFO_PASSWORD.key());
		} else {
			ResponseHttpClient response = null;
			try {
				MfsRequestEnvelope<MfsAgentInfoRequestBody> envelope = new MfsRequestEnvelope<MfsAgentInfoRequestBody>();
				MfsAgentInfoRequestBody agentInfoBody = new MfsAgentInfoRequestBody();

				MfsRequest agentInfoRequest = new MfsRequest();
				MfsRequest.MfsRequestHeader header = agentInfoRequest.new MfsRequestHeader();
				MfsRequest.MfsRequestBody body = agentInfoRequest.new MfsRequestBody();
				MfsRequest.MfsRequestHeader.GeneralConsumerInformation consumerInformation = header.new GeneralConsumerInformation();

				consumerInformation.setConsumerID(request.getServiceName());
				consumerInformation.setCountry(this.country);
				consumerInformation.setTransactionID(request.getSessionId());
				consumerInformation.setCorrelationID(getCorrelationId());
				header.setGeneralConsumerInformation(consumerInformation);

				envelope.setSch(XMLNS_SCH_AGENTINFOSCHEMA);
				envelope.setV1(XMLNS_V1_AGENTINFOSCHEMA);

				MfsRequest.MfsRequestBody.EntityType entityType = body.new EntityType();
				entityType.setEntityType("MSISDN");
				entityType.setEntityId(request.getAccount());
				MfsRequest.MfsRequestBody.Credentials credentials = body.new Credentials();
				credentials.setUser(this.userName);
				credentials.setPassword(this.password);

				body.setEntityType(entityType);
				body.setCredentials(credentials);

				agentInfoRequest.setRequestHeader(header);
				agentInfoRequest.setRequestBody(body);
				agentInfoBody.setMfsAgentInfoRequest(agentInfoRequest);
				envelope.setBody(agentInfoBody);

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
					response = this.callWS.doSoapPost(this.agentInfoUrl, null, soapEnvBody);
					if (response == null) {
						logger.error("No hubo respuesta a la ejecucion del Web Service");
					} else {
						logger.debug("Response --> " + response);
						if (response.getStatusCode() != 200) {
							logger.error("Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
						} else {
							ready = false;
							mapper.isNameSpaceAware(true);
							MfsAgentInfoResponseEnvelope agentInfoResponse = (MfsAgentInfoResponseEnvelope) mapper
									.readValue(response.getResponse(), MfsAgentInfoResponseEnvelope.class);
							if (agentInfoResponse == null) {
								logger.error("Fallo al interpretar la respuesta");
							} else { // Tomar datos de la respuesta
								logger.debug(agentInfoResponse);
								MfsAgentInfoResponseEnvelope.MfsAgentInfoResponseBody.MfsResponse mfsResponse = agentInfoResponse.getBody().getSuccessResponse();
								if (mfsResponse != null) {
									MfsResponseHeader responseHeader = mfsResponse.getResponseHeader();
									if (responseHeader != null) {
										MfsResponseHeader.MfsGeneralResponse generalResponse = responseHeader.getMfsGeneralResponse();
										if (isSuccess(generalResponse.getStatus())) {
											MfsAgentInfoResponseEnvelope.MfsAgentInfoResponseBody.MfsResponse.MfsAgentInfoResponseDetail.MfsAgentInfoDetail agentInfoDetail = mfsResponse.getResponseBody()
													.getAgent();
											MfsAgentInfoResponseEnvelope.MfsAgentInfoResponseBody.MfsResponse.MfsAgentInfoResponseDetail.MfsAgentInfoDetail.AgentStatus agentStatus = agentInfoDetail.getAgentStatus();
											if (agentStatus != null)
												ready = isActive(agentStatus.getDescription());
											logger.debug("ready --> " + ready);
										}
									}
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
			value = ++DriverAgentInfoTigoPy.correlationId;
		}

		return value;
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

	/**
	 * Verificar cadena en busca de una palabra que indique error
	 * 
	 * @param message
	 * @return
	 */
	private boolean isActive(String message) {
		boolean ready = false;

		if (message != null && this.statusActive != null) {
			Pattern p = Pattern.compile(this.statusActive);
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
		statusActive = null;
		country = null;
		agentInfoUrl = null;
		userName = null;
		password = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverAgentInfoTigoPy [");
		if (callWS != null) {
			builder.append("callWS=");
			builder.append(callWS);
			builder.append(", ");
		}
		builder.append("maxTotal=");
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
		if (agentInfoUrl != null) {
			builder.append("agentInfoUrl=");
			builder.append(agentInfoUrl);
			builder.append(", ");
		}
		if (userName != null) {
			builder.append("userName=");
			builder.append(userName);
			builder.append(", ");
		}
		if (password != null) {
			builder.append("password=");
			builder.append(password);
		}
		builder.append("]");
		return builder.toString();
	}

}
