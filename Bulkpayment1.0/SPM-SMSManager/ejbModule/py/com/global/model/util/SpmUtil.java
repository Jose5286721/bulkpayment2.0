package py.com.global.model.util;

import org.apache.log4j.Logger;
import py.com.global.spm.entities.Paymentorderdetail;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author Lino Chamorro
 * @author R2
 * 
 */
public class SpmUtil {

	public static Logger log = Logger.getLogger(SpmUtil.class);

	public static String paymentorderdetailListToString(
			List<Paymentorderdetail> paymentorderdetailList) {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("\n**********");
		if (paymentorderdetailList != null) {
			for (Paymentorderdetail pod : paymentorderdetailList) {
				stringbuilder.append("\n" + pod.toStringAtGlance());
			}
		}
		stringbuilder.append("\n**********\n");
		return stringbuilder.toString();
	}

	public static String millisToSecondStr(Long l) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(Double.valueOf(l) / 1000);
	}

	/**
	 * Para la ejecucion del proceso actual
	 * 
	 * @param wait
	 */
	public static void sleep(long wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Obtener la direccion local
	 * 
	 * @return
	 */
	public static String getSiteLocalAddress() {
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
			if(local==null){
				InetAddress ip;
				ip = InetAddress.getLocalHost();
				local= ip.getHostAddress();
			}

		} catch (UnknownHostException e) {
			log.error("Fallo al identificar direccion. " + e.getMessage(), e);
		}


		return local;
	}

	public static String timeStampToStr(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss,SSS");
		return df.format(date);
	}

	/**
	 * 
	 */
	public static Context getContext() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		return new InitialContext(p);
	}

	public static Object lookup(String name) throws NamingException {
		return getContext().lookup(name);
	}

	@SuppressWarnings("unchecked")
	synchronized public static <T> T getInstance(String name) {
		T instance = null;
		try {
			instance = (T) lookup(name);
		} catch (Exception e) {
			log.error("Obteniendo elemento --> " + name, e);
		}
		return instance;
	}

}
