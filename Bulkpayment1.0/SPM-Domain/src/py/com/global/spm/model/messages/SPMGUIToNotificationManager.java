package py.com.global.spm.model.messages;

import java.io.Serializable;

public class SPMGUIToNotificationManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8018861488961545916L;
	private String userEmail;
	private String mensaje;
	private String subject;
	
	
	
	public SPMGUIToNotificationManager(String userEmail, String mensaje,
			String subject) {
	
		this.userEmail = userEmail;
		this.mensaje = mensaje;
		this.subject = subject;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "SPMGUIToNotificationManager [userEmail=" + userEmail
				+ ", mensaje=" + mensaje + ", subject=" + subject + "]";
	}
	
	
	

}
