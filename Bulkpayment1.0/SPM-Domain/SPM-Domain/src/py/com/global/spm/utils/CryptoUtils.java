package py.com.global.spm.domain.utils;

import java.nio.ByteBuffer;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.logging.Logger;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class CryptoUtils {

	private final String CLAVE = "00SiStEmAdEpAgOm";
	// private String charset = null;
	private final String CIPHER_ALGORITHM = "AES";
	private final String CIPHER_SUITE = "AES/CBC/PKCS5Padding";
	private final Logger log = Logger.getLogger(CryptoUtils.class);

	public String encriptarMonto(Double monto) {
		Cipher cipher;
		try {
			SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(),
					CIPHER_ALGORITHM);
			final IvParameterSpec iv = new IvParameterSpec(new byte[16]);

			cipher = Cipher.getInstance(CIPHER_SUITE);
			// Comienzo a encriptar
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] bytes = new byte[32];
			ByteBuffer.wrap(bytes).putDouble(monto.doubleValue());
			byte[] campoCifrado = cipher.doFinal(bytes);
			/*
			 * TODO: Representar los bytes como string v�a base64, as� ser�
			 * humanamente le�ble. La otra opci�n es expresar como hexadecimal
			 * 
			 * En este caso lo imprimo en pantalla como bytes.
			 */
			return Base64.encode(campoCifrado);

		} catch (Exception e) {
			log.error("Error cifrando monto --> monto=" + monto, e);
		}
		return null;

	}

	public Double desencriptarMonto(String monto) {
		Cipher cipher;
		try {
			SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(),
					CIPHER_ALGORITHM);
			final IvParameterSpec iv = new IvParameterSpec(new byte[16]);
			cipher = Cipher.getInstance(CIPHER_SUITE);
			// Comienzo a desencriptar
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] datosDecifrados = cipher.doFinal(Base64.decode(monto
					.getBytes()));
			Double dob = new Double(ByteBuffer.wrap(datosDecifrados)
					.getDouble());

			return (new Double(dob));
		} catch (Exception e) {
			log.error("Error desencriptando monto --> montoCifrado=" + monto, e);
		}
		return null;

	}

}
