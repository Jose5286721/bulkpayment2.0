package py.com.global.spm.model.exceptions;

/**
 * 
 * @author R2
 *
 */

public class LogPaymentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7349196618666432108L;
	
	private int idEvent=0;
	private String message;
	
	public LogPaymentException(int idEvent, String message) {
		this.idEvent = idEvent;
		this.message=message;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
