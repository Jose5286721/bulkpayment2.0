package py.com.global.spm.GUISERVICE.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Manu
 *
 */
public class FileReceiverDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long idPaymentOrder;
	byte [] signHash;
	byte [] fileContent;
	
	
	
	public FileReceiverDto(Long idPaymentOrder, byte[] signHash,
			byte[] fileContent) {
		this.idPaymentOrder = idPaymentOrder;
		this.signHash = signHash;
		this.fileContent = fileContent;
	}
	public Long getIdPaymentOrder() {
		return idPaymentOrder;
	}
	public void setIdPaymentOrder(Long idPaymentOrder) {
		this.idPaymentOrder = idPaymentOrder;
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	public byte[] getSignHash() {
		return signHash;
	}
	public void setSignHash(byte[] signHash) {
		this.signHash = signHash;
	}
	@Override
	public String toString() {
		return "FileReceiverDto ["
				+ (idPaymentOrder != null ? "idPaymentOrder=" + idPaymentOrder
						+ ", " : "")
				+ (signHash != null ? "signHash=" + signHash + ", " : "")
				+ (fileContent != null ? "fileContent="
						+ Arrays.toString(fileContent) : "") + "]";
	}
	
	

}
