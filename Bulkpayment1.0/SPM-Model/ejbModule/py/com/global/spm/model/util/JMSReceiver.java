package py.com.global.spm.model.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSReceiver {

	private String queueName = null;
	private QueueConnectionFactory queueConnectionFactory = null;
	private QueueConnection queueConnection = null;
	private QueueSession queueSession = null;
	private Queue queue = null;
	private QueueReceiver queueReceiver = null;
	private MessageListener listener = null;
	private boolean connected = false;
	private static Logger log = Logger.getLogger(JMSReceiver.class);

	/*
	 * Constructor
	 */
	public JMSReceiver(InitialContext initialContext, String connectionFactory,
			String queueName) {
		// Nombre de la cola de datos
		this.queueName = queueName;

		// Iniciar el contexto del contenedo
		try {
			if (initialContext == null) {
				log.debug("Iniciando el contexto...");
				initialContext = new InitialContext();
			}
			// Buscar los recursos de connection factory y la cola de datos en
			// el contenedor
			queueConnectionFactory = (QueueConnectionFactory) initialContext
					.lookup(connectionFactory);
			queue = (Queue) initialContext.lookup(queueName);
		} catch (Exception e) {
			log.error("Fallo al cargar el contexto del contenedor. "
					+ e.getMessage());
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

	@Override
	public String toString() {
		return queueName;
	}
}
