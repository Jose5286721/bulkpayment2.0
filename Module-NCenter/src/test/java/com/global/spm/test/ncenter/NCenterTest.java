package com.global.spm.test.ncenter;

import java.util.LinkedHashMap;
import java.util.Map;

import com.global.spm.ncenter.DriverNCenterTigoPy;
import com.global.spm.ncenter.util.Parameter;
import com.global.spm.notificationmanager.RequestNotificationManager;

public class NCenterTest {

	public static void main(String[] args) {

		Map<String, String> parameters = new LinkedHashMap<>();
		parameters.put(Parameter.NOTIFICATIONCENTER_SERVICEURL.key(),
				"https://pubapi-qa.telecel.net.py/osb/Platforms/NotificationCenter/SendNotification/V1");
		parameters.put(Parameter.CONNECTION_MAXTOTAL.key(), "2");
		parameters.put(Parameter.CONNECTION_MAXPERROUTE.key(), "2");
		parameters.put(Parameter.SUCCESS_REGULAREXPRESSION.key(), "OK|ok");
		parameters.put(Parameter.SMS_PLATFORMID.key(), "14");
		parameters.put(Parameter.SMS_PROCESSID.key(), "6");

		DriverNCenterTigoPy ncenter = new DriverNCenterTigoPy();
		ncenter.initialize(parameters);

		RequestNotificationManager request = new RequestNotificationManager();
		request.setSessionId(12L);
		request.setLogNotificationId(123L);
		request.setSendSms(true);
		request.setMessage("Hola que tal 1.\nHola que tal 2.\nHola que tal 3.\nHola que tal 4.\nHola que tal 5.\nHola que tal 6.\nHola que tal 7.\nHola que tal 8.\nHola que tal 9.\nHola que tal 10.\nHola que tal 11.\nHola que tal 12.\nHola que tal 13.");
		request.addAccount("0981407441");

		System.out.println("Envio ? --> " + ncenter.sendMessage(request));
		ncenter.destroy();
	}

}
