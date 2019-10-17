package py.com.global.spm.model.util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class QueueManager {


	private static Map<String, JMSSender> senders= new HashMap<String, JMSSender>();
	//private static JMSSender queueSender;

	private static transient Logger logger= Logger.getLogger(QueueManager.class);
	static Context ctx;
	private static final boolean useWLCTX=false;


	public static boolean closeQueueConn(String queueName){
		if (senders.get(queueName)==null) {
			return false;
		}
		try {
			senders.get(queueName).close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendLine(String line, String queueName){
		try {
			if (useWLCTX) {
				ctx = getWeblogicContext();
				if (ctx==null) {
					return false;
				}
			}
			initQueueSender(queueName);

			return senders.get(queueName)==null?false: senders.get(queueName).send(line);
		} catch (Exception e) {
			logger.error("QueueInManager.sendLine(\""+line+"\")", e);
		}
		return false;
	}

	public static boolean sendObject(Serializable obj, String queueName){
		if (useWLCTX) {
			//ctx = getWeblogicContext();
			if (ctx==null) {
				return false;
			}
		}
		initQueueSender(queueName);
		return  senders.get(queueName)==null?false: senders.get(queueName).send(obj);
	}

	private static Context getWeblogicContext(){
		try {

			Hashtable<String,String> ht = new Hashtable<String, String>();
			ht.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			ht.put(Context.PROVIDER_URL,
					"t3://localhost:7001");
			ctx= new InitialContext(ht);
			return ctx;
		} catch (NamingException e) {
			logger.error("QueueInManager.getContext(): ",e);
		}
		return null;
	}

	private static void initQueueSender(String queueName){
		if (senders.get(queueName)==null) {
			JMSSender qSender= new JMSSender(null, null,queueName);
			senders.put(queueName, qSender);
		}
		try {
			senders.get(queueName).init();
		} catch (Exception e) {
			logger.error("Error init Queue ["+queueName+"]",e);
		}
	}

}