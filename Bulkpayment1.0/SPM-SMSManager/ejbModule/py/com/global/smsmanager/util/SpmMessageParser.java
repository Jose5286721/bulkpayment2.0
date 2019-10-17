package py.com.global.smsmanager.util;

import org.apache.log4j.Logger;

import py.com.global.spm.model.eventcodes.SMSManagerEventCodes;
import py.com.global.spm.model.messages.SMSMangerToFlowManagerMessage;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SpmMessageParser {

	private static final Logger log = LogManager.getLogger(SpmMessageParser.class);

	public static SMSManagerEventCodes parsePaymentOrderAndPin(
			SMSMangerToFlowManagerMessage receipt, String s) {
		log.debug("Parsing message --> " + receipt + ", [" + s + "]");
		if (s == null || s.trim().length() == 0) {
			log.error("Invalid message. Message empty --> " + receipt + ", ["
					+ s + "]");
			return SMSManagerEventCodes.INVALID_MESSAGE;
		}
		s = s.trim();
		String[] result;
		// con coma
		result = s.trim().split(",");
		if (result.length != 2) {
			// con punto
			result = s.trim().split("\\.");
			if (result.length != 2) {
				// con espacio
				result = s.trim().split(" ");
				// error de mensaje
				if (result.length != 2) {
					log.error("Invalid message --> " + receipt + ", [" + s
							+ "]");
					return SMSManagerEventCodes.INVALID_MESSAGE;
				}
			}
		}
		trim(result);
		if (!isValidIdPaymentorder(receipt, result)) {
			return SMSManagerEventCodes.INVALID_ID_PAYMENT_ORDER;
		}
		receipt.setPin(result[1]);
		return SMSManagerEventCodes.SUCCESS;
	}

	private static void trim(String[] result) {
		result[0] = result[0].trim();
		result[1] = result[1].trim();
	}

	private static boolean isValidIdPaymentorder(
			SMSMangerToFlowManagerMessage receipt, String[] result) {
		try {
			receipt.setIdpaymentorder(Long.parseLong(result[0]));
		} catch (Exception e) {
			log.error("Invalid idPaymentorder --> " + receipt + ", ["
					+ result[0] + "]");
			return false;
		}
		return true;
	}

}
