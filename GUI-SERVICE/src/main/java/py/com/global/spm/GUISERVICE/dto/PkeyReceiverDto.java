package py.com.global.spm.GUISERVICE.dto;

import java.io.Serializable;
import java.util.Arrays;

public class PkeyReceiverDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2060114334561870741L;
	byte [] pkey;
	Long idUser;
	
	public PkeyReceiverDto() {
		
	}

	public PkeyReceiverDto(byte[] pkey, Long idUser) {
		this.pkey = pkey;
		this.idUser = idUser;
	}

	public byte[] getPkey() {
		return pkey;
	}

	public void setPkey(byte[] pkey) {
		this.pkey = pkey;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return "PkeyReceiverDto ["
				+ (pkey != null ? "pkey=" + Arrays.toString(pkey) + ", " : "")
				+ (idUser != null ? "idUser=" + idUser : "") + "]";
	}
	
	
}
