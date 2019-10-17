package py.com.global.spm.GUISERVICE.exceptions;

public class FileNoSignException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileNoSignException() {
		super("No se encontro la firma del usuario.");
	}
	
	public FileNoSignException(String message) {
		super(message);
	}

}
