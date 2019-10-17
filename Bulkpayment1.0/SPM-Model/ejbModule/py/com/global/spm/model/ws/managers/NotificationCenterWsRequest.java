package py.com.global.spm.model.ws.managers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.tigo.sendnotification.SendNotification;
import com.tigo.sendnotification.SendNotificationDirectBinding11QSService;
import com.tigo.sendnotificationrequest.v1.SendNotificationRequest;
import com.tigo.sendnotificationresponse.v1.SendNotificationResponse;

public class NotificationCenterWsRequest {

	/*
	 * Constructor
	 */

	public NotificationCenterWsRequest() {

	}

	public void notificationProcess() {
		try {
			SendNotificationRequest request = new SendNotificationRequest();

			// Invocacion al metodo del ws.
			SendNotificationResponse response = invokeWs(request);

		} catch (Exception ex) {
			
			Logger.getLogger(NotificationCenterWsRequest.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
			ex.printStackTrace();
		}

	}

	// METODO DE INVOCACION AL WS
	private static SendNotificationResponse invokeWs(
			com.tigo.sendnotificationrequest.v1.SendNotificationRequest request) {

		String wsdlLocation = "http://osb-test.telecel.net.py:80/SatelliteSystems/SendNotification/PS/PS_SendNotification";
		// String wsdlLocation =
		// "http://osb-test.telecel.net.py/SatelliteSystems/SendNotification/PS/PS_SendNotification?wsdl";
		// String wsdlLocation =
		// "http://osb-test.telecel.net.py/SatelliteSystems/SendNotification/PS/PS_SendNotification";

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
	}

}
