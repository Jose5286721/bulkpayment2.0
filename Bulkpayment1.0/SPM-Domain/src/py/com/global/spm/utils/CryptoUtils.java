package py.com.global.spm.domain.utils;

import java.nio.ByteBuffer;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.jboss.logging.Logger;

/**
 * TODO Implementar encriptacion/decriptacion correctamente
 */
public class CryptoUtils {

	private final String CLAVE = "00SiStEmAdEpAgOm";
	// private String charset = null;
	private final String CIPHER_ALGORITHM = "AES";
	private final String CIPHER_SUITE = "AES/CBC/PKCS5Padding";
	private final Logger log = Logger.getLogger(CryptoUtils.class);

	public String encriptarMonto(Double monto) {

		/*String originalInput = String.valueOf(monto);
		Cipher cipher;
		Base64 base64 = new Base64();

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
			*//*
			 * TODO: Representar los bytes como string via base64, asi sera
			 * humanamente leible. La otra opcion es expresar como hexadecimal
			 * 
			 * En este caso lo imprimo en pantalla como bytes.
			 */


			//String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));
			//return encodedString;

		//} catch (Exception e) {
		//	log.error("Error cifrando monto --> monto=" + monto, e);
		//}
		//return null;
		return monto.toString();

	}

	public Double desencriptarMonto(String monto) {
		/*Cipher cipher;
		Base64 base64 = new Base64();

		try {
			SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(),
					CIPHER_ALGORITHM);
			final IvParameterSpec iv = new IvParameterSpec(new byte[16]);
			cipher = Cipher.getInstance(CIPHER_SUITE);
			// Comienzo a desencriptar
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
//			byte[] datosDecifrados = cipher.doFinal(Base64.decode(monto
//					.getBytes()));
//			Double dob = new Double(ByteBuffer.wrap(datosDecifrados)
//					.getDouble());

			String decodedString = new String(Base64.decodeBase64(monto.getBytes()));
			Double decodedDouble = Double.valueOf(decodedString);
			return decodedDouble;
		} catch (Exception e) {
			log.error("Error desencriptando monto --> montoCifrado=" + monto, e);
		}
		return null;
       */
		return Double.valueOf(monto);
	}

}
