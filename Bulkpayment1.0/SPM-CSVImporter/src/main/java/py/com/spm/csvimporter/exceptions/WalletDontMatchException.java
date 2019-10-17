package py.com.spm.csvimporter.exceptions;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class WalletDontMatchException extends Exception {

	/**
	 * 
	 */
	private String wallet;
	private static final long serialVersionUID = -1478909574732049025L;

	public WalletDontMatchException(String wallet) {
		this.wallet = wallet;
	}
	public String getWallet(){
		return this.wallet;
	}

}
