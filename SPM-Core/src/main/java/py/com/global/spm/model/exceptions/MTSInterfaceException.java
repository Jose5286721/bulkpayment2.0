package py.com.global.spm.model.exceptions;

/**
 * 
 * @author R2
 *
 */

public class MTSInterfaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7349196618666432108L;
	
	private Long idEvent=0L;
	private String message;
	
	public MTSInterfaceException(Long idEvent, String message) {
		this.idEvent = idEvent;
		this.message=message;
	}

	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
