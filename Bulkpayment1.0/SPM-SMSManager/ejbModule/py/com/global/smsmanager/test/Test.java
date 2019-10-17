package py.com.global.smsmanager.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class Test {

	public static void main(String[] args) {
		getSiteLocalAddress();
	}

	private static void stringTest() {
		String messageEmpty = "";
		print("empty", messageEmpty, " ");
		String messageWithColon1 = "nroOp, pin";
		print("colon", messageWithColon1, ",");
		String messageWithColon2 = "456,pin";
		print("colon", messageWithColon2, ",");
		String messageWithColon3 = "           nroOp            ,      pin    ";
		print("colon", messageWithColon3, ",");
		String messageWithSpace1 = "nroOp pin";
		print("space", messageWithSpace1, " ");
		String messageWithSpace2 = "  nroOp       pin   ";
		print("space", messageWithSpace2, " ");
		String messageWithDot1 = "nroOp. pin";
		print("dot", messageWithDot1, "\\.");
		String messageWithDot2 = "nroOp.pin";
		print("dot", messageWithDot2, "\\.");
		String messageWithDot3 = "     nroOp       ............    pin   ";
		print("dot", messageWithDot3, "\\.");
	}

	public static void getSiteLocalAddress() {
		String local = null;

		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			InetAddress addrs[] = InetAddress.getAllByName(hostName);
			for (InetAddress addr : addrs) {
				if (addr != null && !addr.isLoopbackAddress()) {
					local = addr.getHostAddress();
					break;
				}
			}
		} catch (UnknownHostException e) {
			System.out.println("Fallo al identificar direccion. "
					+ e.getMessage());
		}
		System.out.println("IP=" + local);
	}

	private static void print(String type, String s, String regex) {
		String[] result = s.trim().split(regex);
		if (result.length == 2) {
			if (isValidIdPaymentorder(result)) {
				System.out.println("OK. Type=" + type + "; string=[" + s
						+ "]; length=" + result.length + " --> ["
						+ result[0].trim() + "], [" + result[1].trim() + "]");
			} else {
				System.out.println("ERROR. Invalid idPaymentOrder. Type="
						+ type + "; string=[" + s + "]");
			}
		} else {
			System.out.println("ERROR. Type=" + type + "; string=[" + s
					+ "]; length=" + result.length);
		}
	}

	private static boolean isValidIdPaymentorder(String[] result) {
		try {
			Long.parseLong(result[0]);
		} catch (Exception e) {
			System.out.println("\tInvalid idPaymentorder --> " + result[0]);
			return false;
		}
		return true;
	}

}
