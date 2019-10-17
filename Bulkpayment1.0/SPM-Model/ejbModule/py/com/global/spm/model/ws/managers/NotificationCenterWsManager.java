package py.com.global.spm.model.ws.managers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.apache.log4j.Logger;

import py.com.global.spm.model.interfaces.NotificationCenterWsManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.systemparameters.NotificationCenterWsParameters;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;

import com.tigo.core.common.header.request.v1.RequestHeader;
import com.tigo.core.common.header.request.v1.RequestHeader.Consumer;
import com.tigo.core.common.header.request.v1.RequestHeader.Country;
import com.tigo.core.common.header.request.v1.RequestHeader.Message;
import com.tigo.core.common.header.request.v1.RequestHeader.Service;
import com.tigo.core.common.header.request.v1.RequestHeader.Transport;
import com.tigo.core.common.v1.CommunicationType;
import com.tigo.core.common.v1.TransportCodeType;
import com.tigo.sendnotification.SendNotification;
import com.tigo.sendnotification.SendNotificationDirectBinding11QSService;
import com.tigo.sendnotification.v1.AttachmentType;
import com.tigo.sendnotification.v1.ParamType;
import com.tigo.sendnotificationrequest.v1.SendNotificationRequest;
import com.tigo.sendnotificationrequest.v1.SendNotificationRequest.RequestBody;
import com.tigo.sendnotificationrequest.v1.SendNotificationRequest.RequestBody.Attachments;
import com.tigo.sendnotificationrequest.v1.SendNotificationRequest.RequestBody.Parameters;
import com.tigo.sendnotificationresponse.v1.SendNotificationResponse;

/**
 * Session Bean implementation class NotificationCenterManager
 */
@Stateless
public class NotificationCenterWsManager implements
		NotificationCenterWsManagerLocal {

	/**
	 * Invoca al ws NOTIFICATION CENTER Se crean requestHeader y requestBody
	 * para el WS de Notificaciones
	 * 
	 * */
	@EJB
	private SystemparameterManagerLocal systemParametersManager;
	private static final Logger log = Logger
			.getLogger(NotificationCenterWsManager.class);

	/**
	 * Default constructor.
	 */
	public NotificationCenterWsManager() {
	}

	// Notifica via sms
	public SendNotificationResponse sendNotificationSms(String mensaje,
			String nroTelefonoDestino) {
		SendNotificationResponse response = null;
		try {
			RequestHeader requestHeader = new RequestHeader();
			requestHeader = setRequestHeader();
			RequestBody requestBody = new RequestBody();
			requestBody = setRequestBodySms(mensaje, nroTelefonoDestino);
			SendNotificationRequest request = new SendNotificationRequest();
			request.setRequestBody(requestBody);
			request.setRequestHeader(requestHeader);
			// Invocacion al metodo del ws.
			try {

				log.trace("Request WS de Notificaciones:"
						+ "RequestBody [Destino: " + requestBody.getDestiny()
						+ "] ");
				for (ParamType paramType : requestBody.getParameters()
						.getParamType()) {
					log.trace("[ " + paramType.getName() + "--> "
							+ paramType.getValue() + "] ");
				}

				log.trace("RequestBody: [" + "IdPlataform: "
						+ requestBody.getIdPlatform() + "," + "IdProcess: "
						+ requestBody.getIdProcess() + "] "
						+ "RequestHeader [ConsumerCode: "
						+ requestHeader.getConsumer().getCode() + ","
						+ "ConsumerName: "
						+ requestHeader.getConsumer().getName() + ","
						+ "TransportCode:"
						+ requestHeader.getTransport().getCode() + ","
						+ "TransportName:"
						+ requestHeader.getTransport().getName() + ","
						+ "TransportCommunicationType:"
						+ requestHeader.getTransport().getCommunicationType()
						+ "," + "ServiceCode:"
						+ requestHeader.getService().getCode() + ","
						+ "ServiceName:" + requestHeader.getService().getName()
						+ "," + "MessageId:"
						+ requestHeader.getMessage().getMessageId() + ","
						+ "MessageIdCorrelation:"
						+ requestHeader.getMessage().getMessageIdCorrelation()
						+ "," + "MessageConversationId:"
						+ requestHeader.getMessage().getConversationId() + ","
						+ "CountryIsoCode:"
						+ requestHeader.getCountry().getIsoCode() + ","
						+ "CountryName:" + requestHeader.getCountry().getName()
						+ " ] ");

				/*
				 * Invocacion al WS de notificaciones
				 */
				response = invokeWs(request);
				if (response == null) {
					return null;
				} else if (response.getResponseBody() == null) {
					return null;
				} else if (response.getResponseBody().getError() == null) {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Destino: "
							+ response.getResponseBody().getDestiny() + ","
							+ "Exito: "
							+ response.getResponseBody().isSuccess() + ","
							+ "Cod. Transaccion: "
							+ response.getResponseBody().getTransactionCode()
							+ " ] ");
				} else {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Exito: "
							+ response.getResponseBody().isSuccess()
							+ ","
							+ " Tipo Error: "
							+ response.getResponseBody().getError()
									.getErrorType()
							+ ","
							+ " Codigo Error: "
							+ response.getResponseBody().getError().getCode()
							+ ","
							+ " Razon Error: "
							+ response.getResponseBody().getError().getReason()
							+ ","
							+ " Descripcion Error: "
							+ response.getResponseBody().getError()
									.getDescription() + "," + " ] ");
				}

			} catch (WebServiceException ex) {
				log.error("Error WS de Notificaciones -->" + ex.getMessage());
				return null;
			}

		} catch (Exception ex) {
			log.error("Invocando WS Notificaciones Sms --> " + ex.getMessage(),
					ex);
			return null;
		}
		return response;
	}

	// Notifica via email
	public SendNotificationResponse sendNotificationEmail(String mensaje,
			String emailDestino) {
		SendNotificationResponse response = null;
		try {
			RequestHeader requestHeader = new RequestHeader();
			requestHeader = setRequestHeader();
			RequestBody requestBody = new RequestBody();
			requestBody = setRequestBodyEmail(mensaje, emailDestino, null);
			SendNotificationRequest request = new SendNotificationRequest();
			request.setRequestBody(requestBody);
			request.setRequestHeader(requestHeader);

			try {

				log.trace("Request WS de Notificaciones:"
						+ "RequestBody [Destino: " + requestBody.getDestiny()
						+ "] ");
				for (ParamType paramType : requestBody.getParameters()
						.getParamType()) {
					log.trace("[ " + paramType.getName() + "--> "
							+ paramType.getValue() + "] ");
				}

				log.trace("RequestBody: [" + "IdPlataform: "
						+ requestBody.getIdPlatform() + "," + "IdProcess: "
						+ requestBody.getIdProcess() + "] "
						+ "RequestHeader [ConsumerCode: "
						+ requestHeader.getConsumer().getCode() + ","
						+ "ConsumerName: "
						+ requestHeader.getConsumer().getName() + ","
						+ "TransportCode:"
						+ requestHeader.getTransport().getCode() + ","
						+ "TransportName:"
						+ requestHeader.getTransport().getName() + ","
						+ "TransportCommunicationType:"
						+ requestHeader.getTransport().getCommunicationType()
						+ "," + "ServiceCode:"
						+ requestHeader.getService().getCode() + ","
						+ "ServiceName:" + requestHeader.getService().getName()
						+ "," + "MessageId:"
						+ requestHeader.getMessage().getMessageId() + ","
						+ "MessageIdCorrelation:"
						+ requestHeader.getMessage().getMessageIdCorrelation()
						+ "," + "MessageConversationId:"
						+ requestHeader.getMessage().getConversationId() + ","
						+ "CountryIsoCode:"
						+ requestHeader.getCountry().getIsoCode() + ","
						+ "CountryName:" + requestHeader.getCountry().getName()
						+ " ] ");

				response = invokeWs(request);




				if (response == null) {
					return null;
				} else if (response.getResponseBody() == null) {
					return null;
				} else if (response.getResponseBody().getError() == null) {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Destino: "
							+ response.getResponseBody().getDestiny() + ","
							+ "Exito: "
							+ response.getResponseBody().isSuccess() + ","
							+ "Cod. Transaccion: "
							+ response.getResponseBody().getTransactionCode()
							+ " ] ");
				} else {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Exito: "
							+ response.getResponseBody().isSuccess()
							+ ","
							+ " Tipo Error: "
							+ response.getResponseBody().getError()
									.getErrorType()
							+ ","
							+ " Codigo Error: "
							+ response.getResponseBody().getError().getCode()
							+ ","
							+ " Razon Error: "
							+ response.getResponseBody().getError().getReason()
							+ ","
							+ " Descripcion Error: "
							+ response.getResponseBody().getError()
									.getDescription() + "," + " ] ");
				}
			} catch (WebServiceException ex) {
				log.error("Error WS de Notificaciones --> " + ex.getMessage());
				return null;
			}

		} catch (Exception ex) {
			log.error("Invocando WS Notificaciones Email " + ex.getMessage(),
					ex);
		}
		return response;
	}

	// METODO DE INVOCACION AL WS
	private SendNotificationResponse invokeWs(
			com.tigo.sendnotificationrequest.v1.SendNotificationRequest request) {

		String isDummyPar = systemParametersManager.getParameter(
				MtsInterfaceParameters.IS_DUMMY, SpmConstants.NO);
		Boolean isDummy = ("Y".equalsIgnoreCase(isDummyPar) ? true : false);

		if(isDummy){
			SendNotificationResponse response = new SendNotificationResponse();
			SendNotificationResponse.ResponseBody responseBody = new SendNotificationResponse.ResponseBody();
			responseBody.setSuccess(true);
			responseBody.setError(null);
			response.setResponseBody(responseBody);
			log.info("Es un Dummy");
			return response;

		}

		try {

			String wsdlLocation = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.NC_ENDPOINT.getValue(),
					"default");
			URL url = null;

			try {
				url = new URL(wsdlLocation);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			SendNotificationDirectBinding11QSService service = new SendNotificationDirectBinding11QSService(
					url, new QName("http://www.tigo.com/SendNotification",
							"SendNotificationDirectBinding1.1QSService"));
			
			SendNotification servicePort = service
					.getSendNotificationDirectBinding11QSPort();

			BindingProvider bindingProvider = (BindingProvider) servicePort;
			bindingProvider.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation);

			return servicePort.process(request);
		} catch (Exception ex) {
			log.error("Error Invocacion al WS de notificaciones -->" + ex, ex);
			return null;
		}
	}

	private RequestBody setRequestBodyEmail(String mensaje, String destino,
			String asuntoPassRecovery) {
		RequestBody rb = new RequestBody();
		try {
			rb.setDestiny(destino);

			String idPlataform = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.EMAIL_PLATAFORM.getValue(),
					"Obteniendo --> email plataform");
			rb.setIdPlatform(idPlataform);

			String idProcess = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.EMAIL_PROCESS.getValue(),
					"Obteniendo --> email process");
			rb.setIdProcess(idProcess);

			ParamType porigen = new ParamType();
			String from = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.FROM.getValue(),
					"Obteniendo --> fromLabel - email");
			porigen.setName(from);
			String fromValue = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.FROM_VALUE.getValue(),
					"Obteniendo --> fromValue - email");
			porigen.setValue(fromValue);

			ParamType pasunto = new ParamType();
			String subject = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.SUBJECT.getValue(),
					"Obteniendo --> subjectLabel - email");
			pasunto.setName(subject);

			String subjectValue = null;
			if (asuntoPassRecovery != null) {
				subjectValue = asuntoPassRecovery;
			} else {
				subjectValue = systemParametersManager
						.getParameterValue(
								SpmProcesses.SPM_NOTIFICATION_MANAGER,
								NotificationCenterWsParameters.SUBJECT_VALUE
										.getValue(),
								"Obteniendo --> subjectValue - email");
			}
			pasunto.setValue(subjectValue);

			ParamType pbody = new ParamType();
			String message = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.MESSAGE.getValue(),
					"Obteniendo --> messageLabel - email");
			pbody.setName(message);
			pbody.setValue(mensaje);

			Parameters parameters = new Parameters();
			List<ParamType> listParam = parameters.getParamType();
			listParam.add(porigen);
			listParam.add(pasunto);
			listParam.add(pbody);
			rb.setParameters(parameters);
			rb.setAttachments(new Attachments());
		} catch (Exception ex) {
			log.error("Creando el request body del e-mail de notificaciones "
					+ ex.getMessage(), ex);
		}

		return rb;
	}

	private RequestBody setRequestBodySms(String mensaje, String destino) {
		RequestBody rb = new RequestBody();
		try {
			rb.setDestiny(destino);
			try {
				String idPlataform = systemParametersManager
						.getParameterValue(
								SpmProcesses.SPM_NOTIFICATION_MANAGER,
								NotificationCenterWsParameters.SMS_PLATAFORM
										.getValue(),
								"Obteniendo --> plataform - sms");
				rb.setIdPlatform(idPlataform);
			} catch (Exception ex) {
				log.error(
						"Obteniendo idPlataform del SMS --> " + ex.getMessage(),
						ex);
			}

			try {
				String idProcess = systemParametersManager.getParameterValue(
						SpmProcesses.SPM_NOTIFICATION_MANAGER,
						NotificationCenterWsParameters.SMS_PROCESS.getValue(),
						"Obteniendo --> process - sms");
				rb.setIdProcess(idProcess);
			} catch (Exception ex) {
				log.error(
						"Obteniendo idProcess del SMS --> " + ex.getMessage(),
						ex);
			}

			ParamType pbody = new ParamType();
			ParamType nroCorto = new ParamType();
			try {
				String message = systemParametersManager.getParameterValue(
						SpmProcesses.SPM_NOTIFICATION_MANAGER,
						NotificationCenterWsParameters.MESSAGE.getValue(),
						"Obteniendo --> messageLabel - sms");
				pbody.setName(message);
				pbody.setValue(mensaje);
			} catch (Exception ex) {
				log.error(
						"Obteniendo etiqueta mensaje del SMS --> "
								+ ex.getMessage(), ex);
			}

			// Attachments para las notificaciones
			AttachmentType att = new AttachmentType();
			att.setName("");
			att.setMimeType("");

			Attachments attachments = new Attachments();
			List<AttachmentType> listAtt = attachments.getAttachmentType();
			listAtt.add(att);

			try {
				String cortoLabel = systemParametersManager.getParameterValue(
						SpmProcesses.SPM_NOTIFICATION_MANAGER,
						NotificationCenterWsParameters.SHORT_NUMBER.getValue(),
						"Obteniendo --> cortoLabel - sms");
				nroCorto.setName(cortoLabel);

				String cortoValue = systemParametersManager.getParameterValue(
						SpmProcesses.SPM_NOTIFICATION_MANAGER,
						NotificationCenterWsParameters.SHORT_NUMBER_VALUE
								.getValue(), "Obteniendo --> cortoValue - sms");
				nroCorto.setValue(cortoValue);
			} catch (Exception ex) {
				log.error(
						"Obteniendo numero corto del SMS --> "
								+ ex.getMessage(), ex);
			}

			Parameters parameters = new Parameters();
			List<ParamType> listParam = parameters.getParamType();
			listParam.add(pbody);
			listParam.add(nroCorto);
			rb.setParameters(parameters);
			rb.setAttachments(attachments);
		} catch (Exception ex) {
			log.error("Creando el request body del sms de notificaciones --> "
					+ ex.getMessage(), ex);
		}
		return rb;
	}

	private RequestHeader setRequestHeader() {
		RequestHeader rh = new RequestHeader();
		try {
			Consumer consumer = new Consumer();
			String consumerCode = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.CONSUMER_CODE.getValue());

			String consumerName = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.CONSUMER_NAME.getValue());

			consumer.setCode(consumerCode);
			consumer.setName(consumerName);
			rh.setConsumer(consumer);

			Transport transport = new Transport();
			TransportCodeType code = TransportCodeType.WS;
			String transportName = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.TRANSPORT_NAME.getValue());

			transport.setCode(code);
			transport.setName(transportName);
			CommunicationType ct = CommunicationType.SYN;
			transport.setCommunicationType(ct);
			rh.setTransport(transport);

			Service service = new Service();

			String ServiceName = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.SERVICE_NAME.getValue());

			String ServiceCode = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.SERVICE_CODE.getValue());

			service.setName(ServiceName);
			service.setCode(ServiceCode);
			rh.setService(service);

			Message m = new Message();
			Long conversationId = Long.parseLong(systemParametersManager
					.getParameterValue(SpmProcesses.SPM_NOTIFICATION_MANAGER,
							NotificationCenterWsParameters.CONVERSATION_ID
									.getValue()));
			m.setConversationId(conversationId);

			m.setMessageId(Long.parseLong(systemParametersManager
					.getParameterValue(SpmProcesses.SPM_NOTIFICATION_MANAGER,
							NotificationCenterWsParameters.MESSAGE_ID
									.getValue())));
			m.setMessageIdCorrelation(Long.parseLong(systemParametersManager
					.getParameterValue(
							SpmProcesses.SPM_NOTIFICATION_MANAGER,
							NotificationCenterWsParameters.MESSAGE_ID_CORRELATION
									.getValue())));
			rh.setMessage(m);

			Country c = new Country();

			c.setIsoCode(systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.COUNTRY_ISO_CODE.getValue()));

			c.setName(systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.COUNTRY_NAME.getValue()));
			rh.setCountry(c);
		} catch (Exception ex) {
			log.error(
					"Creando el request header de notificaciones "
							+ ex.getMessage(), ex);
		}

		return rh;
	}

	@Override
	public SendNotificationResponse SendNotificationPassRecovery(
			String mensaje, String emailDestino, String asunto) {
		SendNotificationResponse response = null;
		try {
			RequestHeader requestHeader = new RequestHeader();
			requestHeader = setRequestHeader();
			RequestBody requestBody = new RequestBody();
			requestBody = setRequestBodyEmail(mensaje, emailDestino, asunto);
			SendNotificationRequest request = new SendNotificationRequest();
			request.setRequestBody(requestBody);
			request.setRequestHeader(requestHeader);

			try {
				log.trace("Request WS de Notificaciones:"
						+ "RequestBody [Destino: " + requestBody.getDestiny()
						+ "] ");
				for (ParamType paramType : requestBody.getParameters()
						.getParamType()) {
					log.trace("[ " + paramType.getName() + "--> "
							+ paramType.getValue() + "] ");
				}

				log.trace("RequestBody: [" + "IdPlataform: "
						+ requestBody.getIdPlatform() + "," + "IdProcess: "
						+ requestBody.getIdProcess() + "] "
						+ "RequestHeader [ConsumerCode: "
						+ requestHeader.getConsumer().getCode() + ","
						+ "ConsumerName: "
						+ requestHeader.getConsumer().getName() + ","
						+ "TransportCode:"
						+ requestHeader.getTransport().getCode() + ","
						+ "TransportName:"
						+ requestHeader.getTransport().getName() + ","
						+ "TransportCommunicationType:"
						+ requestHeader.getTransport().getCommunicationType()
						+ "," + "ServiceCode:"
						+ requestHeader.getService().getCode() + ","
						+ "ServiceName:" + requestHeader.getService().getName()
						+ "," + "MessageId:"
						+ requestHeader.getMessage().getMessageId() + ","
						+ "MessageIdCorrelation:"
						+ requestHeader.getMessage().getMessageIdCorrelation()
						+ "," + "MessageConversationId:"
						+ requestHeader.getMessage().getConversationId() + ","
						+ "CountryIsoCode:"
						+ requestHeader.getCountry().getIsoCode() + ","
						+ "CountryName:" + requestHeader.getCountry().getName()
						+ " ] ");
				response = invokeWs(request);
				if (response == null) {
					return null;
				} else if (response.getResponseBody() == null) {
					return null;
				} else if (response.getResponseBody().getError() == null) {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Destino: "
							+ response.getResponseBody().getDestiny() + ","
							+ "Exito: "
							+ response.getResponseBody().isSuccess() + ","
							+ "Cod. Transaccion: "
							+ response.getResponseBody().getTransactionCode()
							+ " ] ");
				} else {
					log.trace("Respuesta Ws Notificaciones: --> "
							+ "[Exito: "
							+ response.getResponseBody().isSuccess()
							+ ","
							+ " Tipo Error: "
							+ response.getResponseBody().getError()
									.getErrorType()
							+ ","
							+ " Codigo Error: "
							+ response.getResponseBody().getError().getCode()
							+ ","
							+ " Razon Error: "
							+ response.getResponseBody().getError().getReason()
							+ ","
							+ " Descripcion Error: "
							+ response.getResponseBody().getError()
									.getDescription() + "," + " ] ");
				}

			} catch (WebServiceException ex) {
				log.error("Error WS de Notificaciones -->" + ex.getMessage());
				return null;
			}

		} catch (Exception ex) {
			log.error("Invocando WS Notificaciones Email " + ex.getMessage(),
					ex);
		}
		return response;
	}
}
