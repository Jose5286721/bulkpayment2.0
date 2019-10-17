package py.com.global.spm.model.utils;

import org.apache.log4j.Logger;
import py.com.global.spm.entities.Systemparameter;
import py.com.global.spm.model.systemparameters.JMSSenderParameters;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Properties;

/**
 * JMS Object
 * <P> Eventos del JMS y relacionados.
 * @author Global SI
 * @version 1.0
 */
public class JMSSender {
	private static final Logger logger = Logger.getLogger(JMSSender.class);
	private String queueName = null;
	private QueueConnectionFactory queueConnectionFactory = null;
	private QueueConnection queueConnection = null;
	private QueueSession queueSession = null;
	private Queue queue = null;
	private QueueSender queueSender = null;
	private ObjectMessage objMessage = null;
	private TextMessage txtMessage = null;
	private boolean connected = false;
	private Context namingContext = null;
	private String PROVIDER_URL = "http-remoting://";

	/**
	 * Constructor JMSSender
	 * @param initialContext (no se utiliza), para mas informacion @see {@link JMSSenderParameters}
	 * @param connectionFactory (no se utiliza), para mas informacion @see {@link SpmConstants}
	 * @param queueName (required) nombre de la cola previamente configurada
	 */
	public JMSSender(InitialContext initialContext, String connectionFactory,
                     String queueName) {
		this.queueName = queueName;
		try {
			namingContext = initializeContext();
			String connectionFactoryString = System.getProperty("connection.factory", SpmConstants.DEFAULT_CONNECTION_FACTORY);
			logger.debug("Attempting to acquire connection factory Core \"" + connectionFactoryString + "\"");
			queueConnectionFactory = (QueueConnectionFactory) namingContext.lookup(SpmConstants.DEFAULT_CONNECTION_FACTORY);
			logger.debug("Found connection factory \"" + connectionFactoryString + "\" in JNDI");
			logger.debug("Queue: "+queueName);
			queue = (Queue) namingContext.lookup(queueName);
            logger.debug("Found Queue, "+queue.getQueueName());
		} catch (Exception e) {
			logger.error("Error al obtener contexto: ", e);
		}
	}


    /**
     * Inicializar la cola de datos
     * @param connectionFactory
     * @param queue
     */
	public JMSSender(QueueConnectionFactory connectionFactory, Queue queue) {
		this.queueConnectionFactory = connectionFactory;
		this.queue = queue;
		try {
			this.queueName = ( queue != null)? queue.getQueueName() : null;
		} catch (JMSException e) {
			logger.error("Fallo al inicializar cola de datos. "+ e.getMessage());
		}
	}

	/**
	 * Iniciar la conexion con la cola de datos
	 *
	 * @return boolean - True si la conexion fue satisfactoriamente establecida
	 */
	public boolean init() {
		connected = false;
		/*
		 * Crear la conexion Crear una sesion sobre la conexion Crear el queue
		 * sender Crear el mensaje Finally, close connection.
		 */
		try {
			if (queueConnectionFactory != null && queue != null) {
				queueConnection = queueConnectionFactory.createQueueConnection(JMSSenderParameters.DEFAULT_USERNAME, JMSSenderParameters.DEFAULT_PASSWORD);
				queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				queueSender = queueSession.createSender(queue);
				queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				queueName = queue.getQueueName();
				connected = true;
			}
		} catch (Exception e) {
			logger.error("Fallo al iniciar conexion con la cola de datos. " , e);
		}

		return connected;
	}

	/**
	 * Verificar el estado de conexion
	 *
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Cerrar la conexion
	 */
	public void close() {
			if (queueConnection != null) {
				try {
					if (queueSender != null) {
						queueSender.close();
						queueSender = null;
					}
					if (queueSession != null) {
						queueSession.close();
						queueSession = null;
					}
					queueConnection.close();
				} catch (Exception e) {
					logger.error("Error Cerrando Conexion Queue",e);
				}
			}
			connected = false;
	}

	/**
	 * Enviar objeto a la cola de datos
	 *
	 * @param obj Objeto a enviar
	 *
	 * @return boolean - true: satisfactorio
	 */
	public boolean send(Serializable obj) {
		boolean ready = false;

		// Verificar el objeto
		if (obj == null || queueSender == null ||queueSession==null)
			return ready;

		// Verificar la estructura objMessage
		if (objMessage == null) {
			try {
				objMessage = queueSession.createObjectMessage();
			} catch (Exception e) {
				logger.error("Al crear el mensaje para la cola de datos. " + e.getMessage());
				return ready;
			}
		}

		try {
			objMessage.setObject(obj);
			queueSender.send(objMessage);
			ready = true;
		} catch (Exception e) {
			logger.error("Al enviar el mensaje a la cola -" + queueName + "-." + e.getMessage());
		}

		return ready;
	}

	/**
	 * Enviar un mensaje por la cola de datos
	 *
	 * @param txt
	 *            - Objeto a enviar
	 * @return
	 */
	public boolean send(String txt) {
		boolean ready = false;

		// Verificar el objeto
		if (txt == null)
			return ready;

		// Verificar la estructura objMessage
		if (txtMessage == null) {
			try {
				txtMessage = queueSession.createTextMessage();
			} catch (Exception e) {
				logger.error("Al crear el mensaje para la cola de datos. " + e.getMessage());
				return ready;
			}
		}

		// Enviar el mensaje
		try {
			txtMessage.setText(txt);
			queueSender.send(txtMessage);
			ready = true;
		} catch (Exception e) {
			logger.error("Al enviar el mensaje a la cola -" + queueName + "-." + e.getMessage());
		}

		return ready;
	}

	/**
	 * Inicializa el contexto
	 * @return Context: retorna el contexto a utilizar
	 */
	private Context initializeContext() {
		String usrProvided = getParameterValue(JMSSenderParameters.SYSTEM_PARAMETER_JMSSENDER_PROCESS,
				JMSSenderParameters.SYSTEM_PARAMETER_CTX_PROVIDER_USER);
		String pwdProvided = getParameterValue(JMSSenderParameters.SYSTEM_PARAMETER_JMSSENDER_PROCESS,
				JMSSenderParameters.SYSTEM_PARAMETER_CTX_PROVIDER_PASS);
		String provider = getParameterValue(JMSSenderParameters.SYSTEM_PARAMETER_JMSSENDER_PROCESS,
				JMSSenderParameters.SYSTEM_PARAMETER_CTX_PROVIDER_URL);

		String userName = (usrProvided == null) ? JMSSenderParameters.DEFAULT_USERNAME : usrProvided;
		String password = (pwdProvided == null) ? JMSSenderParameters.DEFAULT_PASSWORD : pwdProvided;
		PROVIDER_URL += (provider == null) ? JMSSenderParameters.DEFAULT_PROVIDER_URL : provider;

		try{
//			userName = System.getProperty("username", userName);
//			password = System.getProperty("password", password);
			// Set up the namingContext for the JNDI lookup
			final Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JMSSenderParameters.INITIAL_CONTEXT_FACTORY);
			logger.debug("PROVIDER_URL: "+ PROVIDER_URL);
			env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
			env.put(Context.SECURITY_PRINCIPAL, userName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			namingContext = new InitialContext(env);

		}catch (Exception e){
			logger.error("al inicializar contexto"+ e.getMessage());
		}

        return namingContext;
    }

	/**
	 * @param processId
	 * @param parameterPk nombre del parametro del sistema
	 * @return String: valor del parametro
	 */
    private String getParameterValue(long processId, String parameterPk){
		String result = null;
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtility.getEntityManager();
			String hql = "SELECT par FROM Systemparameter par WHERE par.id.parameterPk= :parameterPk";
			Query query = entityManager.createQuery(hql);
			query.setParameter("parameterPk", parameterPk);
			Systemparameter parameter = (Systemparameter) query.getSingleResult();
			result = parameter.getValueChr();
		}catch (Exception e){
			logger.error("Al obtener parametro --> "+parameterPk);
			logger.error(e);

		}finally {
			if(entityManager!=null) entityManager.close();
			//JPAUtility.close();
		}
		return result;

	}

	@Override
	public String toString() {
		return this.queueName;
	}
}
