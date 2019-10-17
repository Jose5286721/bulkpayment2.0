package py.com.global.model.util;

import org.apache.log4j.Logger;
import py.com.global.model.systemparameters.JMSSenderParameters;
import py.com.global.spm.entities.Systemparameter;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.*;
import java.util.Properties;

public class JMSReceiver {
	private String userName;
	private String password;
	private String queueName = null;
	private QueueConnectionFactory queueConnectionFactory = null;
	private QueueConnection queueConnection = null;
	private QueueSession queueSession = null;
	private Queue queue = null;
	private QueueReceiver queueReceiver = null;
	private MessageListener listener = null;
	private boolean connected = false;
	private static Logger log = Logger.getLogger(JMSReceiver.class);

	private String PROVIDER_URL = "http-remoting://";
	private	Context namingContext = null;

	/**
	 * Constructor JMSReceiver
	 * @param initialContext (no se utiliza), para mas informacion @see {@link JMSSenderParameters}
	 * @param connectionFactory (no se utiliza), para mas informacion @see {@link SpmConstants}
	 * @param queueName (required) nombre de la cola ya establecida
	 */
	public JMSReceiver(InitialContext initialContext, String connectionFactory,
			String queueName) {

		this.queueName = queueName;
		String connectionFactoryString = System.getProperty("connection.factory", SpmConstants.DEFAULT_CONNECTION_FACTORY);
		namingContext = initializeContext();

		try {
			log.debug("Attempting to acquire connection factory Core \"" + connectionFactoryString + "\"");
			queueConnectionFactory = (QueueConnectionFactory) namingContext.lookup(connectionFactory);
			log.debug("Found connection factory \"" + connectionFactoryString + "\" in JNDI");
			log.debug("Queue: "+queueName);
			queue = (Queue) namingContext.lookup(queueName);
			log.info("Found Queue, "+ queue.getQueueName());
		} catch (Exception e) {
			log.error("Fallo al cargar el contexto del contenedor. " + e.getMessage());
		}
	}

	/*
	 * Constructor
	 */
	public JMSReceiver(QueueConnectionFactory connectionFactory, Queue queue) {
		this.queueConnectionFactory = connectionFactory;
		this.queue = queue;
		try {
			this.queueName = (queue != null) ? queue.getQueueName() : null;
		} catch (JMSException e) {
			log.error("Fallo al inicializar la cola de datos. "
					+ e.getMessage());
		}
	}

	/**
	 * Asignar un listener
	 * 
	 * @param listener
	 */
	public void setListener(MessageListener listener) {
		this.listener = listener;
	}

	/**
	 * Iniciar la conexion con la cola de datos
	 * 
	 * @return boolean - True si la conexion fue satisfactoriamente establecida
	 */
	public boolean init() throws Exception {
		connected = false;

		/*
		 * Crear la conexion Crear una sesion sobre la conexion Crear el queue
		 * sender Crear el mensaje Finally, close connection.
		 */
		try {
			if (queueConnectionFactory != null && queue != null) {
				queueConnection = queueConnectionFactory
						.createQueueConnection();
				queueSession = queueConnection.createQueueSession(false,
						Session.AUTO_ACKNOWLEDGE);
				queueReceiver = queueSession.createReceiver(queue);
				if (listener != null)
					queueReceiver.setMessageListener(listener);
				queueConnection.start();
				connected = true;
			}
		} catch (Exception e) {
			log.error("Fallo al iniciar conexion con la cola de datos. ", e);
			close();
			throw e;
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
	 * Cerrar el objeto
	 */
	public void close() {

		if (queueConnection != null) {
			try {
				queueConnection.close();
			} catch (Exception e) {
			}
		}
		connected = false;
	}

	/**
	 * Recibir un mensaje de texto de la cola de datos
	 * 
	 * @param wait
	 *            - tiempo de espera en milisegundos
	 * @return
	 */
	public String receiveText(int wait) {
		String txt = null;

		if (queueReceiver != null) {
			try {
				Message msg = queueReceiver.receive(wait);
				if (msg != null) {
					if (msg instanceof TextMessage)
						txt = ((TextMessage) msg).getText();
				}
			} catch (Exception e) {
				log.error("Al leer un mensaje de la cola -" + queueName + "-. "
						+ e.getMessage());
			}
		}

		return txt;
	}

	/**
	 * Recibir un objeto serializable de la cola de datos
	 * 
	 * @param wait
	 *            - tiempo de espera en milisegundos
	 * @return
	 */
	public Object receiveObject(int wait) {
		Object obj = null;

		if (queueReceiver != null) {
			try {
				Message msg = queueReceiver.receive(wait);
				if (msg != null) {
					if (msg instanceof ObjectMessage)
						obj = ((ObjectMessage) msg).getObject();
				}
			} catch (Exception e) {
				log.error("Al leer un mensaje de la cola -" + queueName + "-. "
						+ e.getMessage());
			}
		}

		return obj;
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

		userName = (usrProvided == null) ? JMSSenderParameters.DEFAULT_USERNAME : usrProvided;
		password = (pwdProvided == null) ? JMSSenderParameters.DEFAULT_PASSWORD : pwdProvided;
		PROVIDER_URL += (provider == null) ? JMSSenderParameters.DEFAULT_PROVIDER_URL : provider;

		try{
			userName = System.getProperty("username", JMSSenderParameters.DEFAULT_USERNAME);
			password = System.getProperty("password", JMSSenderParameters.DEFAULT_PASSWORD);
			final Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JMSSenderParameters.INITIAL_CONTEXT_FACTORY);
			env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
			env.put(Context.SECURITY_PRINCIPAL, userName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			namingContext = new InitialContext(env);

		}catch (Exception e){
			log.error("al inicializar contexto"+ e.getMessage());
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
		JPAUtility jpaUtility = JPAUtility.newInstance();
		try {
			entityManager = jpaUtility.getEntityManager();
			String hql = "SELECT par FROM Systemparameter par WHERE par.id.parameterPk= :parameterPk";
			Query query = entityManager.createQuery(hql);
			query.setParameter("parameterPk", parameterPk);
			Systemparameter parameter = (Systemparameter) query.getSingleResult();
			result = parameter.getValueChr();
			return result;

		}catch (Exception e){
			log.error("Al obtener parametro --> "+parameterPk);
			log.debug(e);
		}finally {
			if (entityManager!=null) entityManager.close();
			jpaUtility.close();
		}
		return result;

	}

	@Override
	public String toString() {
		return this.queueName;
	}
}
