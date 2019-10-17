package py.com.global.smsmanager.smpp;

import ie.omk.smpp.Connection;
import ie.omk.smpp.event.ConnectionObserver;
import ie.omk.smpp.event.ReceiverExitEvent;
import ie.omk.smpp.event.SMPPEvent;
import ie.omk.smpp.message.DeliverSM;
import ie.omk.smpp.message.SMPPPacket;
import ie.omk.smpp.message.Unbind;
import ie.omk.smpp.message.UnbindResp;
import ie.omk.smpp.version.SMPPVersion;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import py.com.global.model.util.JMSSender;
import py.com.global.model.util.SpmQueues;
import py.com.global.smsmanager.message.DeliverSMWrapper;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class SmppReceiver extends SmppAttributes implements ConnectionObserver {

	// Atributos generales
	private Connection smppConnection = null;
	private long lastActivityTime = 0;
	public static final Logger log = Logger.getLogger(SmppReceiver.class);

	// To send responses to SMSMessageTransmitter
	private JMSSender queueToProcessMsgReceived = null;
	private InitialContext initialContext = null;

	public SmppReceiver(String hostName, int port) {
		if (port > 0) {
			this.hostName = hostName;
			this.port = port;
		} else {
			if (hostName != null) {
				// Descomponer la direccion
				int off = hostName.indexOf(':', 0);
				// Extraer la direccion IP o el host name
				if (off > 0) {
					try {
						this.hostName = hostName.substring(0, off);
						this.port = Integer.parseInt(hostName.substring(
								off + 1, hostName.length()));
					} catch (Exception e) {
						this.hostName = null;
						this.port = 0;
						log.error("Error al cargar la direccion de conexion -"
								+ hostName + "-." + e.getCause(), e);
					}
				}
			}
		}
		try {
			log.debug("Iniciando el contexto...");
			initialContext = new InitialContext();
		} catch (NamingException e) {
			log.error("Fallo al cargar el contexto del Contenedor --> error="
					+ e.getCause(), e);
		}
	}

	/**
	 * Verificar el estado de conexion de las colas de datos
	 */
	synchronized public void checkQueues() {
		try {
			// Levantar la cola de salida
			if (queueToProcessMsgReceived == null) {
				queueToProcessMsgReceived = new JMSSender(initialContext,
						"ConnectionFactory", SpmQueues.SMS_RECEIVED_QUEUE);
			}
			if (!queueToProcessMsgReceived.isConnected()) {
				log.info("Iniciando cola de datos --> cola="
						+ queueToProcessMsgReceived.toString() + ", init="
						+ queueToProcessMsgReceived.init());
				if (!queueToProcessMsgReceived.isConnected()) {
					log.error("Cola no conectada --> cola="
							+ queueToProcessMsgReceived.toString());
				}
			}
		} catch (Exception e) {
			log.error(
					"Al levantar la cola --> " + SpmQueues.SMS_RECEIVED_QUEUE,
					e);
		}
	}

	/**
	 * Conectarse al SMSC
	 * 
	 * @return
	 */
	public boolean Connect() {
		boolean ready = false;
		if (smppConnection == null) {
			if (hostName != null && port > 0 && systemID != null
					&& password != null && systemType != null) {
				try {
					smppConnection = new Connection(hostName, port, true);
				} catch (UnknownHostException e) {
					smppConnection = null;
					log.error(
							"Creating connection object --> " + this.toString(),
							e);
					return false;
				}
				// Asignar el listener para recibir las respuestas
				// asyncronicas
				smppConnection.addObserver(this);
				// Automaticamente responder al ENQUIRE_LINK
				smppConnection.autoAckLink(true);
				smppConnection.autoAckMessages(true);
				smppConnection.setVersion(SMPPVersion.V34);
			} else {
				log.error("Faltan parametros de conexion! --> "
						+ this.toString());
				return false;
			}
		}
		// if (!smppConnection.isBound() && ) {
		if (smppConnection.getState() == Connection.UNBOUND) {
			try {
				log.info("Binding to the SMSC.. --> " + this.toString());
				smppConnection.bind(Connection.RECEIVER, systemID, password,
						systemType, sourceTON, sourceNPI, addrRange);
				ready = true;
				// Actualizar ultima fecha-hora de actividad
				lastActivityTime = System.currentTimeMillis();
			} catch (ConnectException ioEx) {
				log.error(
						"ConnectException. Fallo al establecer conexion con el SMSC --> "
								+ this.toString() + ", " + ioEx.getCause(),
						ioEx);
				ready = false;
			} catch (SocketException ioEx) {
				log.error(
						"SocketException. Fallo al establecer conexion con el SMSC --> "
								+ this.toString() + ", " + ioEx.getCause(),
						ioEx);
				ready = false;
			} catch (Exception e) {
				log.error(
						"Exception. Fallo al establecer conexion con el SMSC --> "
								+ this.toString() + ", " + e.getCause(), e);
				ready = false;
			}
			if (ready == false) {
				try {
					log.warn("Force closing link due binding error --> "
							+ this.toString());
					this.forceUnbind();
				} catch (Exception e) {
					log.error(
							"Can't bound to smsc and problems to closing link",
							e);
				}
			}
		} else if (smppConnection.isBound()) {
			log.warn("Invalid state for binding --> state="
					+ smppConnection.getState() + ", " + this.toString());
			ready = smppConnection.isBound();
		}
		return ready;
	}

	/**
	 * Verificar si la conexion esta activa
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return (smppConnection != null) ? smppConnection.isBound() : false;
	}

	/**
	 * Ultima fecha-hora de actividad
	 * 
	 * @return
	 */
	public long getLastActivityTime() {
		return lastActivityTime;
	}

	/**
	 * Cerrar conexion
	 */
	public void close() {
		if (smppConnection != null) {
			log.debug("Ubinding connection.. --> " + this.toString());
			synchronized (smppConnection) {
				try {
					smppConnection.unbind();
				} catch (Exception e) {
					log.error(
							"Unbinding connection. Fallo al desconectarse del SMSC --> "
									+ e.getCause(), e);
				}
			}
		}
	}

	private void closeLink() {
		if (smppConnection != null) {
			log.debug("Closing link.. --> " + this.toString());
			synchronized (smppConnection) {
				try {
					if (smppConnection != null) {
						smppConnection.closeLink();
					}
				} catch (Exception e) {
					log.error(
							"Closing link. Fallo al desconectarse del SMSC --> "
									+ e.getCause(), e);
				}
			}
		}
		// Limpiando referencias
		smppConnection = null;
	}
	
	private void forceUnbind() {
		if (smppConnection != null) {
			log.debug("Forcing unbind.. --> " + this.toString());
			synchronized (smppConnection) {
				try {
					if (smppConnection != null) {
						smppConnection.force_unbind();
					}
				} catch (Exception e) {
					log.error(
							"Forcing unbind. Fallo al desconectarse del SMSC --> "
									+ e.getCause(), e);
				}
			}
		}
		// Limpiando referencias
		smppConnection = null;
	}

	public boolean sendUnbindResponse(UnbindResp pak) {
		boolean result = false;
		try {
			smppConnection.sendResponse(pak);
			result = true;
		} catch (IOException x) {
			log.error(
					"Fallo al enviar unbind response al SMSC --> "
							+ x.getCause(), x);
			result = false;
		}
		return result;
	}

	@Override
	public void packetReceived(Connection arg0, SMPPPacket pak) {
		log.debug("Packet received --> SMSC Command_Id=0x"
				+ Integer.toHexString(pak.getCommandId()) + ", carrier="
				+ this.carrier);
		switch (pak.getCommandId()) {

		// Bind transmitter response. Check it's status for success...
		case SMPPPacket.BIND_RECEIVER_RESP:
			if (pak.getCommandStatus() != 0) {
				log.info("Error binding to the SMSC --> Command_Status="
						+ Integer.toHexString(pak.getCommandStatus()));
			} else {
				log.info("Successfully bound. Waiting for message delivery..");
			}
			break;

		// Submit message response...
		case SMPPPacket.DELIVER_SM:
			if (pak.getCommandStatus() != 0) {
				log.info("Deliver SM with an error! --> Command_Status="
						+ Integer.toHexString(pak.getCommandStatus()));

			} else {
				lastActivityTime = System.currentTimeMillis();
				this.processPacketReceived((DeliverSM) pak);
			}
			break;

		// Enquire link request
		case SMPPPacket.ENQUIRE_LINK:
			log.trace("SMSC has enquire link --> carrier=" + this.carrier);
			break;

		// Unbind request received from server..
		case SMPPPacket.UNBIND:
			log.info("SMSC has requested unbind! Responding..");
			UnbindResp ubr = new UnbindResp((Unbind) pak);
			if (this.sendUnbindResponse(ubr)) {
				this.closeLink();
			}
			break;

		// Unbind response..
		case SMPPPacket.UNBIND_RESP:
			log.info("Unbound.. --> carrier=" + this.carrier);
			this.closeLink();
			break;

		default:
			log.info("Unexpected packet received! Command_Id = 0x"
					+ Integer.toHexString(pak.getCommandId()));
		}
	}

	private void processPacketReceived(DeliverSM pak) {
		log.debug("Packet received, sending to SMSMessageReceiver --> SequenceNum="
				+ pak.getSequenceNum() + ", carrier=" + carrier);
		DeliverSMWrapper submitSMRespWrap = new DeliverSMWrapper(pak, carrier);
		queueToProcessMsgReceived.send(submitSMRespWrap);
	}

	@Override
	public void update(Connection myConnection, SMPPEvent ev) {
		switch (ev.getType()) {
		case SMPPEvent.RECEIVER_EXIT:
			if (ev instanceof ReceiverExitEvent) {
				ReceiverExitEvent event = (ReceiverExitEvent) ev;
				if (event.getReason() != ReceiverExitEvent.EXCEPTION) {
					if (event.getReason() == ReceiverExitEvent.BIND_TIMEOUT) {
						log.error("Bind timed out waiting for response.");
					}
					log.debug("Receiver thread has exited normally.");
				} else {
					log.debug("Receiver thread died due to exception:");
				}
			}
			break;
		}

	}

	@Override
	public String toString() {
		return "SmppReceiver [index=" + index + ",carrier=" + carrier
				+ ", hostName=" + hostName + ", port=" + port + ", systemID="
				+ systemID + ", systemType=" + systemType + ", password="
				+ password + "]";
	}

}
