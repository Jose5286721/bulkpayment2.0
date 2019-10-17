package py.com.global.spm.GUISERVICE.csvimporter.exceptions;

import java.util.List;

public class DataException extends Exception {

	private final String code;
	private final List<String> replaceText;
	private static final long serialVersionUID = 6L;
	
	public DataException( String code, List<String> replaceText) {
		this.code = code;
		this.replaceText = replaceText;
	}


	public String getCode() {
		return code;
	}

	public List<String> getReplaceText() {
		return replaceText;
	}
}
