package py.com.global.spm.domain.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class CryptoUtils {
	private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);
    private static String encryptionKey;
    public static boolean enabled;
	private static final String characterEncoding       = "UTF-8";
	private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
	private static final String aesEncryptionAlgorithem = "AES";

    @Value("${amount.encrypt.password}")
    public void setPassword(String encrypt) {
        encryptionKey = encrypt;
    }
    @Value("${amount.encrypt.enabled}")
    public void setEnabled(String enabledParam) {
        enabled = Boolean.parseBoolean(enabledParam);
    }

	/**
	 * Method for Encrypt Plain String Data
	 * @param amount
	 * @return encryptedAmount
	 */
	public static String encryptAmount(BigDecimal amount) {
		String encryptedAmount;
		if(encryptionKey==null || !enabled)
			encryptedAmount=amount.toPlainString();
		else {
			try {
				Cipher cipher = Cipher.getInstance(cipherTransformation);
				byte[] key = encryptionKey.getBytes(characterEncoding);
				SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
				IvParameterSpec ivparameterspec = new IvParameterSpec(key);
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
				byte[] cipherText = cipher.doFinal(amount.toPlainString().getBytes(StandardCharsets.UTF_8));
				Base64.Encoder encoder = Base64.getEncoder();
				encryptedAmount = encoder.encodeToString(cipherText);

			} catch (Exception e) {
				encryptedAmount = amount.toPlainString();
				log.error("Al encriptar -->", e);
			}
		}
		return encryptedAmount;
	}

	/**
	 * Method For Get encryptedText and Decrypted provided String
	 * @param encryptedAmount
	 * @return decryptedText
	 */
	public static BigDecimal decryptAmount(String encryptedAmount) {
		String decryptedAmount;
		if(encryptionKey==null){
			return null;
		}else {
			try {
				Cipher cipher = Cipher.getInstance(cipherTransformation);
				byte[] key = encryptionKey.getBytes(characterEncoding);
				SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
				IvParameterSpec ivparameterspec = new IvParameterSpec(key);
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] cipherText = decoder.decode(encryptedAmount.getBytes(StandardCharsets.UTF_8));
				decryptedAmount = new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
				return new BigDecimal(decryptedAmount);
			} catch (Exception e) {
				log.error("Al desencriptar --> ", e);
				return null;
			}
		}
	}


	/**
	 * Encriptacion a BCrypt con 12 saltos
	 * @param parametro
	 * @return
	 */
	public static String encryptToBCrypt(String parametro){
		String salt = BCrypt.gensalt(12);
		return BCrypt.hashpw(parametro, salt);
	}


}



