/**
 * MTSServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package us.inswitch.mts.ws.server;

/*
 *  MTSServiceStub java implementation
 */

public class MTSServiceStub extends org.apache.axis2.client.Stub implements
		MTSService {
	protected org.apache.axis2.description.AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();

	private static int counter = 0;

	private static synchronized java.lang.String getUniqueSuffix() {
		// reset the counter if it is greater than 99999
		if (counter > 99999) {
			counter = 0;
		}
		counter = counter + 1;
		return java.lang.Long.toString(java.lang.System.currentTimeMillis())
				+ "_" + counter;
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault {

		// creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("MTSService"
				+ getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[58];

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "expressProtocol"));
		_service.addOperation(__operation);

		_operations[0] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cambioPin"));
		_service.addOperation(__operation);

		_operations[1] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitConfirm_sp"));
		_service.addOperation(__operation);

		_operations[2] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutPreEscrow"));
		_service.addOperation(__operation);

		_operations[3] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/",
				"cashOutConfirmEscrow_WO_Pin"));
		_service.addOperation(__operation);

		_operations[4] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "removeBankingAccount"));
		_service.addOperation(__operation);

		_operations[5] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "selfTopUpConfirm"));
		_service.addOperation(__operation);

		_operations[6] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cobroConfirm_sp"));
		_service.addOperation(__operation);

		_operations[7] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitPre_sp"));
		_service.addOperation(__operation);

		_operations[8] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutConfirm_WO_Pin"));
		_service.addOperation(__operation);

		_operations[9] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutPre_With_Pin"));
		_service.addOperation(__operation);

		_operations[10] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "agentAutoReversion"));
		_service.addOperation(__operation);

		_operations[11] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "usuario"));
		_service.addOperation(__operation);

		_operations[12] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "companion_card_unblock"));
		_service.addOperation(__operation);

		_operations[13] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutPre_WO_Pin"));
		_service.addOperation(__operation);

		_operations[14] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "getSubscriberBalance"));
		_service.addOperation(__operation);

		_operations[15] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cobroConfirm"));
		_service.addOperation(__operation);

		_operations[16] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "selfTopUpPre"));
		_service.addOperation(__operation);

		_operations[17] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "serviceParameter"));
		_service.addOperation(__operation);

		_operations[18] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "salaryPayment"));
		_service.addOperation(__operation);

		_operations[19] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutPre"));
		_service.addOperation(__operation);

		_operations[20] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "consultaTrn"));
		_service.addOperation(__operation);

		_operations[21] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation
				.setName(new javax.xml.namespace.QName(
						"http://server.ws.mts.inswitch.us/",
						"remitNationalConfirm_sp"));
		_service.addOperation(__operation);

		_operations[22] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashInPreEscrow"));
		_service.addOperation(__operation);

		_operations[23] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "unBind"));
		_service.addOperation(__operation);

		_operations[24] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "insertTransactionInfo"));
		_service.addOperation(__operation);

		_operations[25] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutConfirm"));
		_service.addOperation(__operation);

		_operations[26] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitNationalConfirm"));
		_service.addOperation(__operation);

		_operations[27] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cobroPre"));
		_service.addOperation(__operation);

		_operations[28] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "companion_card_block"));
		_service.addOperation(__operation);

		_operations[29] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/",
				"remitNational_ModifyTransaction"));
		_service.addOperation(__operation);

		_operations[30] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cobroPre_sp"));
		_service.addOperation(__operation);

		_operations[31] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashInConfirmEscrow"));
		_service.addOperation(__operation);

		_operations[32] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "movBilletera"));
		_service.addOperation(__operation);

		_operations[33] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "identity"));
		_service.addOperation(__operation);

		_operations[34] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "bind"));
		_service.addOperation(__operation);

		_operations[35] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitConfirm"));
		_service.addOperation(__operation);

		_operations[36] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "getTarjetaInfo"));
		_service.addOperation(__operation);

		_operations[37] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cobroConfirm_withPin"));
		_service.addOperation(__operation);

		_operations[38] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cotizacion"));
		_service.addOperation(__operation);

		_operations[39] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitNationalPre_sp"));
		_service.addOperation(__operation);

		_operations[40] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/",
				"insertOrModifySubscriberEEPin"));
		_service.addOperation(__operation);

		_operations[41] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "reversion"));
		_service.addOperation(__operation);

		_operations[42] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitPre"));
		_service.addOperation(__operation);

		_operations[43] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "getServiceParameter"));
		_service.addOperation(__operation);

		_operations[44] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation
				.setName(new javax.xml.namespace.QName(
						"http://server.ws.mts.inswitch.us/",
						"cashOutConfirm_With_Pin"));
		_service.addOperation(__operation);

		_operations[45] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashInPre"));
		_service.addOperation(__operation);

		_operations[46] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "consultaSaldo"));
		_service.addOperation(__operation);

		_operations[47] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "companion_card_link"));
		_service.addOperation(__operation);

		_operations[48] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashOutConfirmEscrow"));
		_service.addOperation(__operation);

		_operations[49] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "companion_card_unlink"));
		_service.addOperation(__operation);

		_operations[50] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation
				.setName(new javax.xml.namespace.QName(
						"http://server.ws.mts.inswitch.us/",
						"cobroConfirm_withPin_sp"));
		_service.addOperation(__operation);

		_operations[51] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "rol"));
		_service.addOperation(__operation);

		_operations[52] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "deleteSubscriberEEPin"));
		_service.addOperation(__operation);

		_operations[53] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation
				.setName(new javax.xml.namespace.QName(
						"http://server.ws.mts.inswitch.us/",
						"cashOutPreEscrow_WO_Pin"));
		_service.addOperation(__operation);

		_operations[54] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "remitNationalPre"));
		_service.addOperation(__operation);

		_operations[55] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "cashInConfirm"));
		_service.addOperation(__operation);

		_operations[56] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "resetPin"));
		_service.addOperation(__operation);

		_operations[57] = __operation;

	}

	// populates the faults
	private void populateFaults() {

		faultExceptionNameMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionException");
		faultExceptionClassNameMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionException");
		faultMessageMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionE");

		faultExceptionNameMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionException");
		faultExceptionClassNameMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionException");
		faultMessageMap.put(new javax.xml.namespace.QName(
				"http://server.ws.mts.inswitch.us/", "Exception"),
				"us.inswitch.mts.ws.server.ExceptionE");

	}

	/**
	 * Constructor that takes in a configContext
	 */

	public MTSServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(configurationContext, targetEndpoint, false);
	}

	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	public MTSServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint, boolean useSeparateListener)
			throws org.apache.axis2.AxisFault {
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new org.apache.axis2.client.ServiceClient(
				configurationContext, _service);

		_serviceClient.getOptions().setTo(
				new org.apache.axis2.addressing.EndpointReference(
						targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

	}

	/**
	 * Default Constructor
	 */
	public MTSServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext)
			throws org.apache.axis2.AxisFault {

		this(configurationContext, "https://10.255.8.11:6443/MTS/services");

	}

	/**
	 * Default Constructor
	 */
	public MTSServiceStub() throws org.apache.axis2.AxisFault {

		this("https://10.255.8.11:6443/MTS/services");

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public MTSServiceStub(java.lang.String targetEndpoint)
			throws org.apache.axis2.AxisFault {
		this(null, targetEndpoint);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#expressProtocol
	 * @param expressProtocol0
	 */

	public us.inswitch.mts.ws.server.ExpressProtocolResponseE expressProtocol(

	us.inswitch.mts.ws.server.ExpressProtocolE expressProtocol0)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/expressProtocolRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), expressProtocol0,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"expressProtocol")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ExpressProtocolResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ExpressProtocolResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cambioPin
	 * @param cambioPin2
	 */

	public us.inswitch.mts.ws.server.CambioPinResponseE cambioPin(

	us.inswitch.mts.ws.server.CambioPinE cambioPin2)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cambioPinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cambioPin2,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cambioPin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CambioPinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CambioPinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitConfirm_sp
	 * @param remitConfirm_sp4
	 */

	public us.inswitch.mts.ws.server.RemitConfirm_spResponseE remitConfirm_sp(

	us.inswitch.mts.ws.server.RemitConfirm_spE remitConfirm_sp4)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[2].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitConfirm_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitConfirm_sp4,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitConfirm_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RemitConfirm_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitConfirm_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutPreEscrow
	 * @param cashOutPreEscrow6
	 */

	public us.inswitch.mts.ws.server.CashOutPreEscrowResponseE cashOutPreEscrow(

	us.inswitch.mts.ws.server.CashOutPreEscrowE cashOutPreEscrow6)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[3].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutPreEscrowRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutPreEscrow6,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutPreEscrow")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashOutPreEscrowResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutPreEscrowResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutConfirmEscrow_WO_Pin
	 * @param cashOutConfirmEscrow_WO_Pin8
	 */

	public us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE cashOutConfirmEscrow_WO_Pin(

			us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE cashOutConfirmEscrow_WO_Pin8)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[4].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutConfirmEscrow_WO_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutConfirmEscrow_WO_Pin8,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutConfirmEscrow_WO_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#removeBankingAccount
	 * @param removeBankingAccount10
	 */

	public us.inswitch.mts.ws.server.RemoveBankingAccountResponseE removeBankingAccount(

	us.inswitch.mts.ws.server.RemoveBankingAccountE removeBankingAccount10)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[5].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/removeBankingAccountRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), removeBankingAccount10,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"removeBankingAccount")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.RemoveBankingAccountResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemoveBankingAccountResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#selfTopUpConfirm
	 * @param selfTopUpConfirm12
	 */

	public us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE selfTopUpConfirm(

	us.inswitch.mts.ws.server.SelfTopUpConfirmE selfTopUpConfirm12)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[6].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/selfTopUpConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), selfTopUpConfirm12,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"selfTopUpConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroConfirm_sp
	 * @param cobroConfirm_sp14
	 */

	public us.inswitch.mts.ws.server.CobroConfirm_spResponseE cobroConfirm_sp(

	us.inswitch.mts.ws.server.CobroConfirm_spE cobroConfirm_sp14)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[7].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cobroConfirm_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cobroConfirm_sp14,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cobroConfirm_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CobroConfirm_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroConfirm_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitPre_sp
	 * @param remitPre_sp16
	 */

	public us.inswitch.mts.ws.server.RemitPre_spResponseE remitPre_sp(

	us.inswitch.mts.ws.server.RemitPre_spE remitPre_sp16)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[8].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/remitPre_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					remitPre_sp16,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "remitPre_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RemitPre_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitPre_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutConfirm_WO_Pin
	 * @param cashOutConfirm_WO_Pin18
	 */

	public us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE cashOutConfirm_WO_Pin(

	us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE cashOutConfirm_WO_Pin18)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[9].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutConfirm_WO_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutConfirm_WO_Pin18,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutConfirm_WO_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutPre_With_Pin
	 * @param cashOutPre_With_Pin20
	 */

	public us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE cashOutPre_With_Pin(

	us.inswitch.mts.ws.server.CashOutPre_With_PinE cashOutPre_With_Pin20)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[10].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutPre_With_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutPre_With_Pin20,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutPre_With_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#agentAutoReversion
	 * @param agentAutoReversion22
	 */

	public us.inswitch.mts.ws.server.AgentAutoReversionResponseE agentAutoReversion(

	us.inswitch.mts.ws.server.AgentAutoReversionE agentAutoReversion22)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[11].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/agentAutoReversionRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), agentAutoReversion22,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"agentAutoReversion")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.AgentAutoReversionResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.AgentAutoReversionResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#usuario
	 * @param usuario24
	 */

	public us.inswitch.mts.ws.server.UsuarioResponseE usuario(

	us.inswitch.mts.ws.server.UsuarioE usuario24)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[12].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/usuarioRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), usuario24,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "usuario")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.UsuarioResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.UsuarioResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#companion_card_unblock
	 * @param companion_card_unblock26
	 */

	public us.inswitch.mts.ws.server.Companion_card_unblockResponseE companion_card_unblock(

	us.inswitch.mts.ws.server.Companion_card_unblockE companion_card_unblock26)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[13].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/companion_card_unblockRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), companion_card_unblock26,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"companion_card_unblock")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.Companion_card_unblockResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.Companion_card_unblockResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutPre_WO_Pin
	 * @param cashOutPre_WO_Pin28
	 */

	public us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE cashOutPre_WO_Pin(

	us.inswitch.mts.ws.server.CashOutPre_WO_PinE cashOutPre_WO_Pin28)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[14].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutPre_WO_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutPre_WO_Pin28,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutPre_WO_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#getSubscriberBalance
	 * @param getSubscriberBalance30
	 */

	public us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE getSubscriberBalance(

	us.inswitch.mts.ws.server.GetSubscriberBalanceE getSubscriberBalance30)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[15].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/getSubscriberBalanceRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), getSubscriberBalance30,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"getSubscriberBalance")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroConfirm
	 * @param cobroConfirm32
	 */

	public us.inswitch.mts.ws.server.CobroConfirmResponseE cobroConfirm(

	us.inswitch.mts.ws.server.CobroConfirmE cobroConfirm32)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[16].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cobroConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					cobroConfirm32,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cobroConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CobroConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#selfTopUpPre
	 * @param selfTopUpPre34
	 */

	public us.inswitch.mts.ws.server.SelfTopUpPreResponseE selfTopUpPre(

	us.inswitch.mts.ws.server.SelfTopUpPreE selfTopUpPre34)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[17].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/selfTopUpPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					selfTopUpPre34,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "selfTopUpPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.SelfTopUpPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.SelfTopUpPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#serviceParameter
	 * @param serviceParameter36
	 * 
	 * @throws us.inswitch.mts.ws.server.ExceptionException
	 *             :
	 */

	public us.inswitch.mts.ws.server.ServiceParameterResponseE serviceParameter(

	us.inswitch.mts.ws.server.ServiceParameterE serviceParameter36)

	throws java.rmi.RemoteException

	, us.inswitch.mts.ws.server.ExceptionException {
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[18].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/serviceParameterRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), serviceParameter36,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"serviceParameter")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ServiceParameterResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ServiceParameterResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						if (ex instanceof us.inswitch.mts.ws.server.ExceptionException) {
							throw (us.inswitch.mts.ws.server.ExceptionException) ex;
						}

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#salaryPayment
	 * @param salaryPayment38
	 */

	public us.inswitch.mts.ws.server.SalaryPaymentResponseE salaryPayment(

	us.inswitch.mts.ws.server.SalaryPaymentE salaryPayment38)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[19].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/salaryPaymentRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), salaryPayment38,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"salaryPayment")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.SalaryPaymentResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.SalaryPaymentResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutPre
	 * @param cashOutPre40
	 */

	public us.inswitch.mts.ws.server.CashOutPreResponseE cashOutPre(

	us.inswitch.mts.ws.server.CashOutPreE cashOutPre40)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[20].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cashOutPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutPre40,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cashOutPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashOutPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#consultaTrn
	 * @param consultaTrn42
	 */

	public us.inswitch.mts.ws.server.ConsultaTrnResponseE consultaTrn(

	us.inswitch.mts.ws.server.ConsultaTrnE consultaTrn42)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[21].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/consultaTrnRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					consultaTrn42,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "consultaTrn")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ConsultaTrnResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ConsultaTrnResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitNationalConfirm_sp
	 * @param remitNationalConfirm_sp44
	 */

	public us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE remitNationalConfirm_sp(

	us.inswitch.mts.ws.server.RemitNationalConfirm_spE remitNationalConfirm_sp44)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[22].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitNationalConfirm_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitNationalConfirm_sp44,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitNationalConfirm_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashInPreEscrow
	 * @param cashInPreEscrow46
	 */

	public us.inswitch.mts.ws.server.CashInPreEscrowResponseE cashInPreEscrow(

	us.inswitch.mts.ws.server.CashInPreEscrowE cashInPreEscrow46)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[23].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashInPreEscrowRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashInPreEscrow46,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashInPreEscrow")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashInPreEscrowResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashInPreEscrowResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#unBind
	 * @param unBind48
	 */

	public us.inswitch.mts.ws.server.UnBindResponseE unBind(

	us.inswitch.mts.ws.server.UnBindE unBind48)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[24].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/unBindRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), unBind48,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "unBind")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.UnBindResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.UnBindResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#insertTransactionInfo
	 * @param insertTransactionInfo50
	 */

	public us.inswitch.mts.ws.server.InsertTransactionInfoResponseE insertTransactionInfo(

	us.inswitch.mts.ws.server.InsertTransactionInfoE insertTransactionInfo50)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[25].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/insertTransactionInfoRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), insertTransactionInfo50,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"insertTransactionInfo")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.InsertTransactionInfoResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.InsertTransactionInfoResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutConfirm
	 * @param cashOutConfirm52
	 */

	public us.inswitch.mts.ws.server.CashOutConfirmResponseE cashOutConfirm(

	us.inswitch.mts.ws.server.CashOutConfirmE cashOutConfirm52)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[26].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutConfirm52,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashOutConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitNationalConfirm
	 * @param remitNationalConfirm54
	 */

	public us.inswitch.mts.ws.server.RemitNationalConfirmResponseE remitNationalConfirm(

	us.inswitch.mts.ws.server.RemitNationalConfirmE remitNationalConfirm54)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[27].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitNationalConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitNationalConfirm54,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitNationalConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.RemitNationalConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitNationalConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroPre
	 * @param cobroPre56
	 */

	public us.inswitch.mts.ws.server.CobroPreResponseE cobroPre(

	us.inswitch.mts.ws.server.CobroPreE cobroPre56)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[28].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cobroPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cobroPre56,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cobroPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CobroPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#companion_card_block
	 * @param companion_card_block58
	 */

	public us.inswitch.mts.ws.server.Companion_card_blockResponseE companion_card_block(

	us.inswitch.mts.ws.server.Companion_card_blockE companion_card_block58)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[29].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/companion_card_blockRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), companion_card_block58,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"companion_card_block")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.Companion_card_blockResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.Companion_card_blockResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitNational_ModifyTransaction
	 * @param remitNational_ModifyTransaction60
	 */

	public us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE remitNational_ModifyTransaction(

			us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE remitNational_ModifyTransaction60)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[30].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitNational_ModifyTransactionRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitNational_ModifyTransaction60,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitNational_ModifyTransaction")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroPre_sp
	 * @param cobroPre_sp62
	 */

	public us.inswitch.mts.ws.server.CobroPre_spResponseE cobroPre_sp(

	us.inswitch.mts.ws.server.CobroPre_spE cobroPre_sp62)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[31].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cobroPre_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					cobroPre_sp62,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cobroPre_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CobroPre_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroPre_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashInConfirmEscrow
	 * @param cashInConfirmEscrow64
	 */

	public us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE cashInConfirmEscrow(

	us.inswitch.mts.ws.server.CashInConfirmEscrowE cashInConfirmEscrow64)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[32].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashInConfirmEscrowRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashInConfirmEscrow64,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashInConfirmEscrow")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#movBilletera
	 * @param movBilletera66
	 */

	public us.inswitch.mts.ws.server.MovBilleteraResponseE movBilletera(

	us.inswitch.mts.ws.server.MovBilleteraE movBilletera66)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[33].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/movBilleteraRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					movBilletera66,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "movBilletera")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.MovBilleteraResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.MovBilleteraResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#identity
	 * @param identity68
	 */

	public us.inswitch.mts.ws.server.IdentityResponseE identity(

	us.inswitch.mts.ws.server.IdentityE identity68)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[34].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/identityRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), identity68,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "identity")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.IdentityResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.IdentityResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#bind
	 * @param bind70
	 */

	public us.inswitch.mts.ws.server.BindResponseE bind(

	us.inswitch.mts.ws.server.BindE bind70)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[35].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/bindRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), bind70,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "bind")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.BindResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.BindResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitConfirm
	 * @param remitConfirm72
	 */

	public us.inswitch.mts.ws.server.RemitConfirmResponseE remitConfirm(

	us.inswitch.mts.ws.server.RemitConfirmE remitConfirm72)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[36].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/remitConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(
					getFactory(_operationClient.getOptions()
							.getSoapVersionURI()),
					remitConfirm72,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "remitConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RemitConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#getTarjetaInfo
	 * @param getTarjetaInfo74
	 */

	public us.inswitch.mts.ws.server.GetTarjetaInfoResponseE getTarjetaInfo(

	us.inswitch.mts.ws.server.GetTarjetaInfoE getTarjetaInfo74)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[37].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/getTarjetaInfoRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), getTarjetaInfo74,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"getTarjetaInfo")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.GetTarjetaInfoResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.GetTarjetaInfoResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroConfirm_withPin
	 * @param cobroConfirm_withPin76
	 */

	public us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE cobroConfirm_withPin(

	us.inswitch.mts.ws.server.CobroConfirm_withPinE cobroConfirm_withPin76)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[38].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cobroConfirm_withPinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cobroConfirm_withPin76,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cobroConfirm_withPin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cotizacion
	 * @param cotizacion78
	 */

	public us.inswitch.mts.ws.server.CotizacionResponseE cotizacion(

	us.inswitch.mts.ws.server.CotizacionE cotizacion78)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[39].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cotizacionRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cotizacion78,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cotizacion")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CotizacionResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CotizacionResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitNationalPre_sp
	 * @param remitNationalPre_sp80
	 */

	public us.inswitch.mts.ws.server.RemitNationalPre_spResponseE remitNationalPre_sp(

	us.inswitch.mts.ws.server.RemitNationalPre_spE remitNationalPre_sp80)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[40].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitNationalPre_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitNationalPre_sp80,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitNationalPre_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.RemitNationalPre_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitNationalPre_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#insertOrModifySubscriberEEPin
	 * @param insertOrModifySubscriberEEPin82
	 */

	public us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE insertOrModifySubscriberEEPin(

			us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE insertOrModifySubscriberEEPin82)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[41].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/insertOrModifySubscriberEEPinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), insertOrModifySubscriberEEPin82,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"insertOrModifySubscriberEEPin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#reversion
	 * @param reversion84
	 */

	public us.inswitch.mts.ws.server.ReversionResponseE reversion(

	us.inswitch.mts.ws.server.ReversionE reversion84)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[42].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/reversionRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), reversion84,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "reversion")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ReversionResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ReversionResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitPre
	 * @param remitPre86
	 */

	public us.inswitch.mts.ws.server.RemitPreResponseE remitPre(

	us.inswitch.mts.ws.server.RemitPreE remitPre86)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[43].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/remitPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitPre86,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "remitPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RemitPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#getServiceParameter
	 * @param getServiceParameter88
	 * 
	 * @throws us.inswitch.mts.ws.server.ExceptionException
	 *             :
	 */

	public us.inswitch.mts.ws.server.GetServiceParameterResponseE getServiceParameter(

	us.inswitch.mts.ws.server.GetServiceParameterE getServiceParameter88)

	throws java.rmi.RemoteException

	, us.inswitch.mts.ws.server.ExceptionException {
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[44].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/getServiceParameterRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), getServiceParameter88,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"getServiceParameter")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.GetServiceParameterResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.GetServiceParameterResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						if (ex instanceof us.inswitch.mts.ws.server.ExceptionException) {
							throw (us.inswitch.mts.ws.server.ExceptionException) ex;
						}

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutConfirm_With_Pin
	 * @param cashOutConfirm_With_Pin90
	 */

	public us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE cashOutConfirm_With_Pin(

	us.inswitch.mts.ws.server.CashOutConfirm_With_PinE cashOutConfirm_With_Pin90)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[45].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutConfirm_With_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutConfirm_With_Pin90,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutConfirm_With_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashInPre
	 * @param cashInPre92
	 */

	public us.inswitch.mts.ws.server.CashInPreResponseE cashInPre(

	us.inswitch.mts.ws.server.CashInPreE cashInPre92)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[46].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/cashInPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashInPre92,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "cashInPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashInPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashInPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#consultaSaldo
	 * @param consultaSaldo94
	 */

	public us.inswitch.mts.ws.server.ConsultaSaldoResponseE consultaSaldo(

	us.inswitch.mts.ws.server.ConsultaSaldoE consultaSaldo94)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[47].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/consultaSaldoRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), consultaSaldo94,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"consultaSaldo")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ConsultaSaldoResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ConsultaSaldoResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#companion_card_link
	 * @param companion_card_link96
	 */

	public us.inswitch.mts.ws.server.Companion_card_linkResponseE companion_card_link(

	us.inswitch.mts.ws.server.Companion_card_linkE companion_card_link96)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[48].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/companion_card_linkRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), companion_card_link96,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"companion_card_link")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.Companion_card_linkResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.Companion_card_linkResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutConfirmEscrow
	 * @param cashOutConfirmEscrow98
	 */

	public us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE cashOutConfirmEscrow(

	us.inswitch.mts.ws.server.CashOutConfirmEscrowE cashOutConfirmEscrow98)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[49].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutConfirmEscrowRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutConfirmEscrow98,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutConfirmEscrow")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#companion_card_unlink
	 * @param companion_card_unlink100
	 */

	public us.inswitch.mts.ws.server.Companion_card_unlinkResponseE companion_card_unlink(

	us.inswitch.mts.ws.server.Companion_card_unlinkE companion_card_unlink100)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[50].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/companion_card_unlinkRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), companion_card_unlink100,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"companion_card_unlink")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.Companion_card_unlinkResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.Companion_card_unlinkResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cobroConfirm_withPin_sp
	 * @param cobroConfirm_withPin_sp102
	 */

	public us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE cobroConfirm_withPin_sp(

			us.inswitch.mts.ws.server.CobroConfirm_withPin_spE cobroConfirm_withPin_sp102)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[51].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cobroConfirm_withPin_spRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cobroConfirm_withPin_sp102,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cobroConfirm_withPin_sp")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#rol
	 * @param rol104
	 */

	public us.inswitch.mts.ws.server.RolResponseE rol(

	us.inswitch.mts.ws.server.RolE rol104)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[52].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/rolRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), rol104,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "rol")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RolResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RolResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#deleteSubscriberEEPin
	 * @param deleteSubscriberEEPin106
	 */

	public us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE deleteSubscriberEEPin(

	us.inswitch.mts.ws.server.DeleteSubscriberEEPinE deleteSubscriberEEPin106)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[53].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/deleteSubscriberEEPinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), deleteSubscriberEEPin106,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"deleteSubscriberEEPin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashOutPreEscrow_WO_Pin
	 * @param cashOutPreEscrow_WO_Pin108
	 */

	public us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE cashOutPreEscrow_WO_Pin(

			us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE cashOutPreEscrow_WO_Pin108)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[54].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashOutPreEscrow_WO_PinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashOutPreEscrow_WO_Pin108,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashOutPreEscrow_WO_Pin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#remitNationalPre
	 * @param remitNationalPre110
	 */

	public us.inswitch.mts.ws.server.RemitNationalPreResponseE remitNationalPre(

	us.inswitch.mts.ws.server.RemitNationalPreE remitNationalPre110)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[55].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/remitNationalPreRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), remitNationalPre110,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"remitNationalPre")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.RemitNationalPreResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.RemitNationalPreResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#cashInConfirm
	 * @param cashInConfirm112
	 */

	public us.inswitch.mts.ws.server.CashInConfirmResponseE cashInConfirm(

	us.inswitch.mts.ws.server.CashInConfirmE cashInConfirm112)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[56].getName());
			_operationClient
					.getOptions()
					.setAction(
							"http://server.ws.mts.inswitch.us/MTS/cashInConfirmRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), cashInConfirm112,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/",
							"cashInConfirm")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.CashInConfirmResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.CashInConfirmResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see us.inswitch.mts.ws.server.MTSService#resetPin
	 * @param resetPin114
	 */

	public us.inswitch.mts.ws.server.ResetPinResponseE resetPin(

	us.inswitch.mts.ws.server.ResetPinE resetPin114)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[57].getName());
			_operationClient.getOptions().setAction(
					"http://server.ws.mts.inswitch.us/MTS/resetPinRequest");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), resetPin114,
					optimizeContent(new javax.xml.namespace.QName(
							"http://server.ws.mts.inswitch.us/", "resetPin")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					us.inswitch.mts.ws.server.ResetPinResponseE.class,
					getEnvelopeNamespaces(_returnEnv));

			return (us.inswitch.mts.ws.server.ResetPinResponseE) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(faultElt.getQName());
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(faultElt.getQName());
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			_messageContext.getTransportOut().getSender()
					.cleanup(_messageContext);
		}
	}

	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private java.util.Map getEnvelopeNamespaces(
			org.apache.axiom.soap.SOAPEnvelope env) {
		java.util.Map returnMap = new java.util.HashMap();
		java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext()) {
			org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator
					.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private javax.xml.namespace.QName[] opNameArray = null;

	private boolean optimizeContent(javax.xml.namespace.QName opName) {

		if (opNameArray == null) {
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;
			}
		}
		return false;
	}

	// https://10.255.8.11:6443/MTS/services
	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ExpressProtocolE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ExpressProtocolE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ExpressProtocolResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.ExpressProtocolResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CambioPinE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CambioPinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CambioPinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CambioPinResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitConfirm_spE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitConfirm_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitConfirm_spResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutPreEscrowE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreEscrowResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutPreEscrowResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemoveBankingAccountE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemoveBankingAccountE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemoveBankingAccountResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemoveBankingAccountResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SelfTopUpConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.SelfTopUpConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroConfirm_spE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CobroConfirm_spResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitPre_spE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitPre_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitPre_spResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPre_With_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutPre_With_PinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.AgentAutoReversionE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.AgentAutoReversionE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.AgentAutoReversionResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.AgentAutoReversionResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.UsuarioE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.UsuarioE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.UsuarioResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.UsuarioResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_unblockE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.Companion_card_unblockE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_unblockResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.Companion_card_unblockResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPre_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutPre_WO_PinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetSubscriberBalanceE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.GetSubscriberBalanceE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroConfirmResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SelfTopUpPreE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.SelfTopUpPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SelfTopUpPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.SelfTopUpPreResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ServiceParameterE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ServiceParameterE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ServiceParameterResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.ServiceParameterResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ExceptionE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ExceptionE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SalaryPaymentE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.SalaryPaymentE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.SalaryPaymentResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.SalaryPaymentResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutPreResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ConsultaTrnE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ConsultaTrnE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ConsultaTrnResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ConsultaTrnResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNationalConfirm_spE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInPreEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInPreEscrowE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInPreEscrowResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashInPreEscrowResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.UnBindE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.UnBindE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.UnBindResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.UnBindResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.InsertTransactionInfoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.InsertTransactionInfoE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.InsertTransactionInfoResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.InsertTransactionInfoResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutConfirmResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitNationalConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNationalConfirmResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroPreResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_blockE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.Companion_card_blockE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_blockResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.Companion_card_blockResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroPre_spE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroPre_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroPre_spResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInConfirmEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInConfirmEscrowE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.MovBilleteraE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.MovBilleteraE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.MovBilleteraResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.MovBilleteraResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.IdentityE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.IdentityE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.IdentityResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.IdentityResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.BindE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(us.inswitch.mts.ws.server.BindE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.BindResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.BindResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitConfirmResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetTarjetaInfoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.GetTarjetaInfoE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetTarjetaInfoResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.GetTarjetaInfoResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_withPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CobroConfirm_withPinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CotizacionE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CotizacionE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CotizacionResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CotizacionResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitNationalPre_spE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalPre_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNationalPre_spResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ReversionE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ReversionE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ReversionResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ReversionResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitPreResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetServiceParameterE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.GetServiceParameterE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.GetServiceParameterResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.GetServiceParameterResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirm_With_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirm_With_PinE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInPreResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ConsultaSaldoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ConsultaSaldoE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ConsultaSaldoResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ConsultaSaldoResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_linkE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.Companion_card_linkE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_linkResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.Companion_card_linkResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashOutConfirmEscrowE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_unlinkE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.Companion_card_unlinkE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.Companion_card_unlinkResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.Companion_card_unlinkResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_withPin_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CobroConfirm_withPin_spE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RolE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(us.inswitch.mts.ws.server.RolE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RolResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RolResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.DeleteSubscriberEEPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.DeleteSubscriberEEPinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalPreE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.RemitNationalPreE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.RemitNationalPreResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param
					.getOMElement(
							us.inswitch.mts.ws.server.RemitNationalPreResponseE.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInConfirmE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.CashInConfirmResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.CashInConfirmResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ResetPinE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ResetPinE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			us.inswitch.mts.ws.server.ResetPinResponseE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					us.inswitch.mts.ws.server.ResetPinResponseE.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ExpressProtocolE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.ExpressProtocolE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CambioPinE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CambioPinE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitConfirm_spE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutPreEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutPreEscrowE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemoveBankingAccountE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemoveBankingAccountE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.SelfTopUpConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.SelfTopUpConfirmE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CobroConfirm_spE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.RemitPre_spE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutPre_With_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutPre_With_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.AgentAutoReversionE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.AgentAutoReversionE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.UsuarioE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.UsuarioE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.Companion_card_unblockE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.Companion_card_unblockE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutPre_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutPre_WO_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.GetSubscriberBalanceE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.GetSubscriberBalanceE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CobroConfirmE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.SelfTopUpPreE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.SelfTopUpPreE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ServiceParameterE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.ServiceParameterE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.SalaryPaymentE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.SalaryPaymentE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CashOutPreE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ConsultaTrnE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.ConsultaTrnE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitNationalConfirm_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitNationalConfirm_spE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashInPreEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashInPreEscrowE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.UnBindE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.UnBindE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.InsertTransactionInfoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.InsertTransactionInfoE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CashOutConfirmE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitNationalConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitNationalConfirmE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CobroPreE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.Companion_card_blockE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.Companion_card_blockE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CobroPre_spE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashInConfirmEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashInConfirmEscrowE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.MovBilleteraE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.MovBilleteraE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.IdentityE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.IdentityE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.BindE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.BindE.MY_QNAME, factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.RemitConfirmE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.GetTarjetaInfoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.GetTarjetaInfoE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroConfirm_withPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CobroConfirm_withPinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CotizacionE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CotizacionE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitNationalPre_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitNationalPre_spE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ReversionE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.ReversionE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.RemitPreE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.GetServiceParameterE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.GetServiceParameterE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutConfirm_With_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutConfirm_With_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashInPreE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CashInPreE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ConsultaSaldoE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.ConsultaSaldoE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.Companion_card_linkE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.Companion_card_linkE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutConfirmEscrowE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutConfirmEscrowE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.Companion_card_unlinkE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.Companion_card_unlinkE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CobroConfirm_withPin_spE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CobroConfirm_withPin_spE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RolE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(us.inswitch.mts.ws.server.RolE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.DeleteSubscriberEEPinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.DeleteSubscriberEEPinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.RemitNationalPreE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									us.inswitch.mts.ws.server.RemitNationalPreE.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.CashInConfirmE param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.CashInConfirmE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			us.inswitch.mts.ws.server.ResetPinE param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							us.inswitch.mts.ws.server.ResetPinE.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type, java.util.Map extraNamespaces)
			throws org.apache.axis2.AxisFault {

		try {

			if (us.inswitch.mts.ws.server.ExpressProtocolE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ExpressProtocolE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ExpressProtocolResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.ExpressProtocolResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CambioPinE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CambioPinE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CambioPinResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CambioPinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitConfirm_spE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitConfirm_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitConfirm_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitConfirm_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreEscrowE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreEscrowE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreEscrowResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreEscrowResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmEscrow_WO_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemoveBankingAccountE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemoveBankingAccountE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemoveBankingAccountResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemoveBankingAccountResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SelfTopUpConfirmE.class.equals(type)) {

				return us.inswitch.mts.ws.server.SelfTopUpConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.SelfTopUpConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_spE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitPre_spE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitPre_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitPre_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitPre_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirm_WO_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirm_WO_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPre_With_PinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPre_With_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPre_With_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.AgentAutoReversionE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.AgentAutoReversionE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.AgentAutoReversionResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.AgentAutoReversionResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.UsuarioE.class.equals(type)) {

				return us.inswitch.mts.ws.server.UsuarioE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.UsuarioResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.UsuarioResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_unblockE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_unblockE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_unblockResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_unblockResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPre_WO_PinE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPre_WO_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPre_WO_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetSubscriberBalanceE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.GetSubscriberBalanceE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.GetSubscriberBalanceResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirmE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SelfTopUpPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.SelfTopUpPreE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SelfTopUpPreResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.SelfTopUpPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ServiceParameterE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ServiceParameterE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ServiceParameterResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.ServiceParameterResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ExceptionE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ExceptionE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SalaryPaymentE.class.equals(type)) {

				return us.inswitch.mts.ws.server.SalaryPaymentE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.SalaryPaymentResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.SalaryPaymentResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ConsultaTrnE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ConsultaTrnE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ConsultaTrnResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.ConsultaTrnResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalConfirm_spE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalConfirm_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalConfirm_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInPreEscrowE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashInPreEscrowE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInPreEscrowResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashInPreEscrowResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.UnBindE.class.equals(type)) {

				return us.inswitch.mts.ws.server.UnBindE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.UnBindResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.UnBindResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.InsertTransactionInfoE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.InsertTransactionInfoE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.InsertTransactionInfoResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.InsertTransactionInfoResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalConfirmE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CobroPreE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroPreResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CobroPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_blockE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_blockE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_blockResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_blockResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNational_ModifyTransactionE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNational_ModifyTransactionResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroPre_spE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CobroPre_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroPre_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroPre_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInConfirmEscrowE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashInConfirmEscrowE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashInConfirmEscrowResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.MovBilleteraE.class.equals(type)) {

				return us.inswitch.mts.ws.server.MovBilleteraE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.MovBilleteraResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.MovBilleteraResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.IdentityE.class.equals(type)) {

				return us.inswitch.mts.ws.server.IdentityE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.IdentityResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.IdentityResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.BindE.class.equals(type)) {

				return us.inswitch.mts.ws.server.BindE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.BindResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.BindResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitConfirmE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetTarjetaInfoE.class.equals(type)) {

				return us.inswitch.mts.ws.server.GetTarjetaInfoE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetTarjetaInfoResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.GetTarjetaInfoResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_withPinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_withPinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_withPinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CotizacionE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CotizacionE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CotizacionResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CotizacionResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalPre_spE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalPre_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalPre_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalPre_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.InsertOrModifySubscriberEEPinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ReversionE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ReversionE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ReversionResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ReversionResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitPreE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitPreResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetServiceParameterE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.GetServiceParameterE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.GetServiceParameterResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.GetServiceParameterResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ExceptionE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ExceptionE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirm_With_PinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirm_With_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirm_With_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashInPreE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInPreResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashInPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ConsultaSaldoE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ConsultaSaldoE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ConsultaSaldoResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.ConsultaSaldoResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_linkE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_linkE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_linkResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_linkResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmEscrowE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmEscrowE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutConfirmEscrowResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_unlinkE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_unlinkE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.Companion_card_unlinkResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.Companion_card_unlinkResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_withPin_spE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_withPin_spE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CobroConfirm_withPin_spResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RolE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RolE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RolResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RolResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.DeleteSubscriberEEPinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.DeleteSubscriberEEPinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.DeleteSubscriberEEPinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashOutPreEscrow_WO_PinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalPreE.class.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalPreE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.RemitNationalPreResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.RemitNationalPreResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInConfirmE.class.equals(type)) {

				return us.inswitch.mts.ws.server.CashInConfirmE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.CashInConfirmResponseE.class
					.equals(type)) {

				return us.inswitch.mts.ws.server.CashInConfirmResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ResetPinE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ResetPinE.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (us.inswitch.mts.ws.server.ResetPinResponseE.class.equals(type)) {

				return us.inswitch.mts.ws.server.ResetPinResponseE.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

}
