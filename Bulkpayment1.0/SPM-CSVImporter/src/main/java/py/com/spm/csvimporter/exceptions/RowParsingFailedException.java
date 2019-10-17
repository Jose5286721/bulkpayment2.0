package py.com.spm.csvimporter.exceptions;

public class RowParsingFailedException extends Exception {

	private final int row;
	private final String phoneNumber;
	private static final long serialVersionUID = 1L;
	
	public RowParsingFailedException(int row, String phoneNumber) {
		this.row = row;
		this.phoneNumber = phoneNumber;
	}

	public int getRow() {
		return row;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
