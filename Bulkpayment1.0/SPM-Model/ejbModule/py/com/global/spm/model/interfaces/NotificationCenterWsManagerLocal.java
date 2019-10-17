package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import com.tigo.sendnotificationresponse.v1.SendNotificationResponse;

@Local
public interface NotificationCenterWsManagerLocal {
	public SendNotificationResponse sendNotificationSms(String mensaje,
			String nroTelefonoDestino);
	
	public SendNotificationResponse sendNotificationEmail(String mensaje,
			String emailDestino);
	public SendNotificationResponse SendNotificationPassRecovery(String mensaje,
			String emailDestino, String asunto);
}
