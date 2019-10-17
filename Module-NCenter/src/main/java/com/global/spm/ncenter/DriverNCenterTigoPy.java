package com.global.spm.ncenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.global.spm.basic.BasicResponseDto;
import com.global.spm.notificationmanager.NotificationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.global.common.helper.Configuration;
import com.global.common.helper.Print;
import com.global.common.ws.PoolingHttpClient;
import com.global.common.ws.ResponseHttpClient;
import com.global.common.ws.soap.FasterXMLMapper;
import com.global.spm.ncenter.doc.NCenterMessage;
import com.global.spm.ncenter.doc.NCenterRequestBody;
import com.global.spm.ncenter.doc.NCenterRequestEnvelope;
import com.global.spm.ncenter.doc.NCenterRequestHeader;
import com.global.spm.ncenter.doc.NCenterResponseEnvelope;
import com.global.spm.ncenter.util.Parameter;
import com.global.spm.notificationmanager.IDriverNotificationManager;
import com.global.spm.notificationmanager.RequestNotificationManager;
import com.global.spm.notificationmanager.ResponseNotificationManager;

public class DriverNCenterTigoPy implements IDriverNotificationManager {
	protected static Logger logger = LogManager.getLogger(DriverNCenterTigoPy.class);

	// Atributos del objeto
	private PoolingHttpClient callWS = null;
	private Integer maxTotal = 0;
	private Integer maxPerRoute = 0;
	private String ncenterUrl = null;
	private String successRegExpresion = null;
	private Integer smsPlatformId = null;
	private Integer smsProcessId = null;
	private Integer smsMaxMessageLength = null;
	private Integer smsMinMessageLength = null;
	private Integer emailPlatformId = null;
	private Integer emailProcessId = null;

	@Override
	public void initialize(Map<String, String> parameters) {
		Configuration conf = new Configuration(parameters);

		// Extraer parametros
		// URLs de servicios
		this.ncenterUrl = conf.value(Parameter.NOTIFICATIONCENTER_SERVICEURL);

		// Parametros de conexion
		this.maxTotal = conf.intValue(Parameter.CONNECTION_MAXPERROUTE);
		this.maxPerRoute = conf.intValue(Parameter.CONNECTION_MAXTOTAL);
		this.successRegExpresion = conf.value(Parameter.SUCCESS_REGULAREXPRESSION);
		this.smsPlatformId = conf.intValue(Parameter.SMS_PLATFORMID);
		this.smsProcessId = conf.intValue(Parameter.SMS_PROCESSID);
		this.smsMaxMessageLength = conf.intValue(Parameter.SMS_MAXMESSAGELENGTH);
		this.smsMinMessageLength = conf.intValue(Parameter.SMS_MINMESSAGELENGTH);
		this.emailPlatformId = conf.intValue(Parameter.EMAIL_PLATFORMID);
		this.emailProcessId = conf.intValue(Parameter.EMAIL_PROCESSID);

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
		if (this.ncenterUrl == null)
			logger.error("Se requiere el parametro --> " + Parameter.NOTIFICATIONCENTER_SERVICEURL.key());
		else
			logger.info("Parametro " + Parameter.NOTIFICATIONCENTER_SERVICEURL.key() + " --> " + this.ncenterUrl);

		if (this.successRegExpresion == null)
			logger.error("Se requiere el parametro --> " + Parameter.SUCCESS_REGULAREXPRESSION.key());
		else
			logger.info("Parametro " + Parameter.SUCCESS_REGULAREXPRESSION.key() + " --> " + this.successRegExpresion);

		if (this.smsPlatformId == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SMS_PLATFORMID.key());
		else
			logger.info("Parametro " + Parameter.SMS_PLATFORMID.key() + " --> " + this.smsPlatformId);

		if (this.smsProcessId == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SMS_PROCESSID.key());
		else
			logger.info("Parametro " + Parameter.SMS_PROCESSID.key() + " --> " + this.smsProcessId);

		if (this.smsMaxMessageLength == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SMS_MAXMESSAGELENGTH.key());
		else
			logger.info("Parametro " + Parameter.SMS_MAXMESSAGELENGTH.key() + " --> " + this.smsMaxMessageLength);

		if (this.smsMinMessageLength == null)
			logger.warn("Se requiere el parametro --> " + Parameter.SMS_MINMESSAGELENGTH.key());
		else
			logger.info("Parametro " + Parameter.SMS_MINMESSAGELENGTH.key() + " --> " + this.smsMinMessageLength);

		if (this.emailPlatformId == null)
			logger.warn("Se requiere el parametro --> " + Parameter.EMAIL_PLATFORMID.key());
		else
			logger.info("Parametro " + Parameter.EMAIL_PLATFORMID.key() + " --> " + this.emailPlatformId);

		if (this.emailProcessId == null)
			logger.warn("Se requiere el parametro --> " + Parameter.EMAIL_PROCESSID.key());
		else
			logger.info("Parametro " + Parameter.EMAIL_PROCESSID.key() + " --> " + this.emailProcessId);

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
	public ResponseNotificationManager sendMessage(RequestNotificationManager request) {
		ResponseNotificationManager responseNCenter = null;

		// Verificar request
		if (request == null) {
			logger.error("Valor incorrecto del request");
		} else // Verificar coneccion al WS
		if (this.callWS == null) {
			logger.error("Fallo en la conexion Web Service");
		} else //
		if (this.ncenterUrl == null) {
			logger.error("No parametrizado la url del servicio --> " + Parameter.NOTIFICATIONCENTER_SERVICEURL.key());
		} else // Verificar mensaje
		if (request.getMessage() == null) {
			logger.error("Se requiere del mensaje a enviar");
		} else {
			// Verificar y enviar SMS
			if (request.isSendSms() != null && request.isSendSms() && request.getAccounts() != null) {
				responseNCenter = sendSms(request);
			}
			// Verificar y enviar EMail
			if (request.isSendEmail() != null && request.isSendEmail() && request.getEmails() != null) {
				responseNCenter = sendEmail(request);
			}
		}

		return responseNCenter;
	}

	/**
	 * Enviar por SMS
	 * 
	 * @param request
	 * @return
	 */
	public ResponseNotificationManager sendSms(RequestNotificationManager request) {
		ResponseNotificationManager responseNCenter = null;

		// Completar cabecera de la respuesta
		responseNCenter = new ResponseNotificationManager();
		responseNCenter.setStatus(false);
		responseNCenter.setSessionId(request.getSessionId());
		responseNCenter.setLogNotificationId(request.getLogNotificationId());
		responseNCenter.setStatusCode(Parameter.STATUS_CODE_GENERALERROR.key());
		responseNCenter.setDescription(Parameter.STATUS_CODE_GENERALERROR.defaultValue());
		// Respuesta a al WS
		ResponseHttpClient response = null;
		try {
			NCenterRequestEnvelope envelope = new NCenterRequestEnvelope();
			NCenterRequestEnvelope.NCenterBody body = envelope.new NCenterBody();
			NCenterRequestEnvelope.NCenterBody.NCenterSendNotificationRequest sendNotificationRequest = body.new NCenterSendNotificationRequest();
			NCenterRequestHeader headerRequest = new NCenterRequestHeader();
			NCenterRequestBody bodyRequest = new NCenterRequestBody();
			NCenterMessage message = new NCenterMessage();
			NCenterRequestBody.NCenterParameters parameters = bodyRequest.new NCenterParameters();
			NCenterRequestBody.NCenterParameters.NCenterParamType paramType = parameters.new NCenterParamType();

			String[] messages = splitMessage(request.getMessage());
			for (int idx = 0; idx < messages.length; idx++) {
				paramType.setName("mensaje");
				paramType.setValue(messages[idx]);

				parameters.setParamType(paramType);

				bodyRequest.setParameters(parameters);

				message.setMessageIdCorrelation(request.getSessionId());
				message.setMessageId(request.getLogNotificationId());
				message.setConversationId(idx + 1);
				headerRequest.setMessage(message);

				sendNotificationRequest.setRequestHeader(headerRequest);
				sendNotificationRequest.setRequestBody(bodyRequest);

				body.setSendNotificationRequest(sendNotificationRequest);
				envelope.setBody(body);


				for (String account : request.getAccounts()) {
					NotificationResponse responseSms = new NotificationResponse();
					bodyRequest.setDestiny(account);
					bodyRequest.setIdPlatform(this.smsPlatformId);
					bodyRequest.setIdProcess(this.smsProcessId);

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
						response = this.callWS.doSoapPost(this.ncenterUrl, null, soapEnvBody);
						if (response == null) {
							logger.error("No hubo respuesta a la ejecucion del Web Service");
							responseNCenter.setStatusCode(Parameter.STATUS_CODE_CONNECTIONERROR.key());
						} else {
							logger.debug("Response --> " + response);
							responseNCenter.setStatusCode(String.valueOf(response.getStatusCode()));
							if (response.getStatusCode() != 200) {
								logger.error(
										"Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
							} else {
								mapper.isNameSpaceAware(true);
								NCenterResponseEnvelope nCenterResponseEnvelope = (NCenterResponseEnvelope) mapper
										.readValue(response.getResponse(), NCenterResponseEnvelope.class);
								responseSms.setResponse(checkResponse(nCenterResponseEnvelope));
								responseSms.setDestiny(account);
								responseNCenter.setStatus(true);
								responseNCenter.setDescription(Parameter.STATUS_CODE_SUCCESS.defaultValue());
								responseNCenter.addResponseSms(responseSms);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Fallo en la ejecucion --> " + e.getMessage());
			logger.debug(Print.toString(e));
		}

		return responseNCenter;
	}

	/**
	 * Dividir el mensaje
	 * 
	 * @param message
	 * @return
	 */
	private String[] splitMessage(String message) {
		List<String> messages = new LinkedList<String>();

		// Verificar longitud maxima del sms
		if (this.smsMaxMessageLength == null) {
			logger.warn("Se requiere la parametrizacion de la longitud maxima del mensaje");
		} else // Verificar mensaje
		if (message == null) {
			logger.error("Se requiere del mensaje a procesar");
		} else // Verificar si el mensaje supera la longitud maxima
		if (message.length() <= this.smsMaxMessageLength) {
			messages.add(message);
		} else {
			// Split message por fin de linea
			char[] cmessage = message.toCharArray();
			int offset = 0, length = 0;
			int lastEndLine = 0, lastSpace = 0, lastEndDot = 0;
			boolean flagEndDot = false;
			String token = null;
			for (int idx = 0; idx < cmessage.length; idx++) {
				char c = cmessage[idx];
				// Registrar posicion de fin de linea
				if (c == '\n')
					lastEndLine = idx;
				else // Registrar posicion del espacio
				if (c == ' ') {
					lastSpace = idx;
				} else // Verificar Punto final
				if (c == '.') {
					flagEndDot = true;
				} else // Verificar si es digito
				if (c >= '0' && c <= '9') {
					flagEndDot = false;
				} else // Verificar
				if (flagEndDot)
					lastEndDot = idx - 1;
				length = idx - offset;
				if (length >= this.smsMaxMessageLength) {
					// Verificar ultimo Fin de Linea
					if (lastEndLine > offset && (lastEndLine - offset) > this.smsMinMessageLength) {
						token = message.substring(offset, lastEndLine + 1);
						offset = lastEndLine + 1;
					} else // Verificar ultimo Punto Final
					if (lastEndDot > offset && (lastEndDot - offset) > this.smsMinMessageLength) {
						token = message.substring(offset, lastEndDot + 1);
						offset = lastEndDot + 1;
					} else // Verificar ultimo espacio
					if (lastSpace > offset && (lastSpace - offset) > this.smsMinMessageLength) {
						token = message.substring(offset, lastSpace + 1);
						offset = lastSpace + 1;
					} else { // No hubo una marca validad de fin de linea, punto final o espacio
						token = message.substring(offset, idx + 1);
						offset = idx + 1;
					}
					messages.add(token);
				}
			}
			// Agregar la cadena restante
			length = cmessage.length - offset;
			if (length > 0 && length < this.smsMaxMessageLength) {
				token = message.substring(offset, cmessage.length);
				messages.add(token);
			}
		}

		return (String[]) messages.toArray(new String[messages.size()]);
	}

	/**
	 * Cortar el mensaje private String[] splitMessage() {
	 * 
	 * return null; }
	 * 
	 * /** Enviar correos
	 * 
	 * @param request
	 * @return
	 */
	private ResponseNotificationManager sendEmail(RequestNotificationManager request) {
		ResponseNotificationManager responseNCenter = null;

		// Completar cabecera de la respuesta
		responseNCenter = new ResponseNotificationManager();
		responseNCenter.setStatus(true);
		responseNCenter.setSessionId(request.getSessionId());
		responseNCenter.setLogNotificationId(request.getLogNotificationId());
		responseNCenter.setStatusCode(Parameter.STATUS_CODE_GENERALERROR.key());
		responseNCenter.setDescription(Parameter.STATUS_CODE_GENERALERROR.defaultValue()); // Respuesta a al WS
		ResponseHttpClient response = null;
		try {
			NCenterRequestEnvelope envelope = new NCenterRequestEnvelope();
			NCenterRequestEnvelope.NCenterBody body = envelope.new NCenterBody();
			NCenterRequestEnvelope.NCenterBody.NCenterSendNotificationRequest sendNotificationRequest = body.new NCenterSendNotificationRequest();
			NCenterRequestHeader headerRequest = new NCenterRequestHeader();
			NCenterRequestBody bodyRequest = new NCenterRequestBody();
			NCenterMessage message = new NCenterMessage();
			NCenterRequestBody.NCenterParameters parameters = bodyRequest.new NCenterParameters();
			NCenterRequestBody.NCenterParameters.NCenterParamType paramType = parameters.new NCenterParamType();

			paramType.setName("mensaje");
			paramType.setValue(request.getMessage());

			parameters.setParamType(paramType);

			bodyRequest.setParameters(parameters);

			message.setMessageIdCorrelation(request.getSessionId());
			message.setMessageId(request.getLogNotificationId());
			message.setConversationId(1);
			headerRequest.setMessage(message);

			sendNotificationRequest.setRequestHeader(headerRequest);
			sendNotificationRequest.setRequestBody(bodyRequest);

			body.setSendNotificationRequest(sendNotificationRequest);
			envelope.setBody(body);

			// Verificar y enviar EMAIL
			for (String email : request.getEmails()) {
				NotificationResponse responseEmail = new NotificationResponse();
				bodyRequest.setDestiny(email);
				bodyRequest.setIdPlatform(this.emailPlatformId);
				bodyRequest.setIdProcess(this.emailProcessId);

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
					response = this.callWS.doSoapPost(this.ncenterUrl, null, soapEnvBody);
					if (response == null) {
						logger.error("No hubo respuesta a la ejecucion del Web Service");
						responseNCenter.setStatusCode(Parameter.STATUS_CODE_CONNECTIONERROR.key());
					} else {
						logger.debug("Response --> " + response);
						responseNCenter.setStatusCode(String.valueOf(response.getStatusCode()));
						if (response.getStatusCode() != 200) {
							logger.error("Fallo en la ejecucion del Web Service, code -->" + response.getStatusCode());
						} else {
							mapper.isNameSpaceAware(true);
							NCenterResponseEnvelope nCenterResponse = (NCenterResponseEnvelope) mapper
									.readValue(response.getResponse(), NCenterResponseEnvelope.class);
							responseEmail.setResponse(checkResponse(nCenterResponse));
							responseEmail.setDestiny(email);
							responseNCenter.setStatus(true);
							responseNCenter.setDescription(Parameter.STATUS_CODE_SUCCESS.defaultValue());
							responseNCenter.addResponseEmail(responseEmail);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Fallo en la ejecucion --> " + e.getMessage());
			logger.debug(Print.toString(e));
		}

		return responseNCenter;
	}

	/**
	 * Verificar la respuesta
	 * 
	 * @param nCenterResponse
	 * @return
	 */
	private BasicResponseDto checkResponse(NCenterResponseEnvelope nCenterResponse) {
		BasicResponseDto response = new BasicResponseDto();

		response.setStatus(false);
		if (nCenterResponse == null) {
			logger.error("Valor incorrecto de la respuesta -Envelope-");
		} else { // Tomar datos de la respuesta
			logger.debug("Response --> " + nCenterResponse);
			if (nCenterResponse.getBody() == null) {
				logger.error("Valor incorrecto de la respuesta -Body-");
			} else // Verificar SendNotificationResponse
			if (nCenterResponse.getBody().getSendNotificationResponse() == null) {
				logger.error("Valor incorrecto de la respuesta -SendNotificationResponse-");
			} else // Verificar ResponseBody
			if (nCenterResponse.getBody().getSendNotificationResponse().getBody() == null) {
				logger.error("Valor incorrecto de la respuesta -ResponseBody-");
			} else // Verificar Success
			if (nCenterResponse.getBody().getSendNotificationResponse().getBody().getSuccess() == null) {
				if (nCenterResponse.getBody().getSendNotificationResponse().getBody().getError() == null) {
					logger.error("Valor incorrecto de la respuesta -Error-");
				} else {
					response.setStatus(false);
					response.setStatusCode(
							nCenterResponse.getBody().getSendNotificationResponse().getBody().getError().getCode());
					response.setDescription(nCenterResponse.getBody().getSendNotificationResponse().getBody().getError()
							.getDescription());
				}
			} else { // Verificar
				response.setStatus(nCenterResponse.getBody().getSendNotificationResponse().getBody().getSuccess());
				response.setStatusCode(nCenterResponse.getBody().getSendNotificationResponse().getBody()
						.getTransactionCode().toString());
			}
		}

		return response;
	}

	public void destroy() {
		this.callWS.shutdown();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverNCenterTigoPy [maxTotal=");
		builder.append(maxTotal);
		builder.append(", maxPerRoute=");
		builder.append(maxPerRoute);
		builder.append(", ");
		if (ncenterUrl != null) {
			builder.append("ncenterUrl=");
			builder.append(ncenterUrl);
			builder.append(", ");
		}
		if (smsPlatformId != null) {
			builder.append("platformaId=");
			builder.append(smsPlatformId);
			builder.append(", ");
		}
		if (smsProcessId != null) {
			builder.append("additionalDataId=");
			builder.append(smsProcessId);
			builder.append(", ");
		}
		if (successRegExpresion != null) {
			builder.append("successRegExpresion=");
			builder.append(successRegExpresion);
		}
		builder.append("]");
		return builder.toString();
	}

}
