package py.com.global.spm.model.message;

import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.eventcodes.NotificationEventEnum;


import java.io.Serializable;
import java.util.Arrays;

public class NotificationRequestMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -86453540507324862L;
	private NotificationEventEnum event; // Evento que genera el mensaje.
	private Paymentorder op; // Orden de pago: Enviado por FlowManager
	private LogPaymentCache pago; // Datos del pago, enviado por el updater.
	private Integer tiempoValidezSms; // Este valor es llenado por el
										// FlowManager.
	private Integer cantReintentos; // Reintentos para bloqueo, es llenado por
									// FlowManager.
	private Long[] idDestinatarios; // Este valor es llenado por el Flowmanager
									// o el Updater.
									// Son id de usuarios o beneficiarios a
									// quien se notificara.
	private String userPhoneNumber;
	private String pass;

	public NotificationEventEnum getEvent() {
		return event;
	}

	public void setEvent(NotificationEventEnum event) {
		this.event = event;
	}

	public Paymentorder getOp() {
		return op;
	}

	public void setOp(Paymentorder op) {
		this.op = op;
	}

	public LogPaymentCache getPago() {
		return pago;
	}

	public void setPago(LogPaymentCache pago) {
		this.pago = pago;
	}

	public Integer getTiempoValidezSms() {
		return tiempoValidezSms;
	}

	public void setTiempoValidezSms(Integer tiempoValidezSms) {
		this.tiempoValidezSms = tiempoValidezSms;
	}

	public Integer getCantReintentos() {
		return cantReintentos;
	}

	public void setCantReintentos(Integer cantReintentos) {
		this.cantReintentos = cantReintentos;
	}

	public Long[] getIdDestinatarios() {
		return idDestinatarios;
	}

	public void setIdDestinatarios(Long[] idDestinatarios) {
		this.idDestinatarios = idDestinatarios;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "NotificationRequestMessage [event="
				+ event
				+ ", op="
				+ op
				+ ", pago="
				+ pago
				+ ", tiempoValidezSms="
				+ tiempoValidezSms
				+ ", cantReintentos="
				+ cantReintentos
				+ ", idDestinatarios="
				+ (idDestinatarios != null ? Arrays.toString(idDestinatarios)
						: "null") + ", userPhoneNumber=" + userPhoneNumber
				+ "]";
	}

}
