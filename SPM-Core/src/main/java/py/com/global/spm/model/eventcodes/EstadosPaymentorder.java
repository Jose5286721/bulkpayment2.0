package py.com.global.spm.model.eventcodes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum EstadosPaymentorder {
	FIRMA_EN_PROCESO("FX", "En Proceso"),
	CANCELADA("CA", "Cancelada"),
	PAGO_PENDIENTE("PP", "Pago Pendiente"),
	PAGO_EN_PROCESO("PX", "Pago en Proceso"),
	SATISFACTORIO("SU", "Satisfactorio"),
	PARCIALMENTE_PAGADA("SP", "Parcialmente Pagada"),
	ERROR("ER", "Error"),
	REVERSION_EN_PROCESO("RX", "Reversion en Proceso"),
	REVERTIDA("RE", "Revertida"),
	PARCIALMENTE_REVERTIDA("RP", "Parcialmente Revertida"),
	NO_REVERTIDA("RN", "No Revertida");

	String codigo0;
	String descriptionOrKey;

	private EstadosPaymentorder(String codigo0, String descriptionOrKey) {
		this.codigo0 = codigo0;
		this.descriptionOrKey = descriptionOrKey;

	}

	public String getCodigo0() {
		return codigo0;
	}

	public String getDescriptionOrKey() {
		return descriptionOrKey;
	}

	public static EstadosPaymentorder getStateForCode(String code) {
		for (EstadosPaymentorder state : EstadosPaymentorder.values()) {
			if (state.getCodigo0().equalsIgnoreCase(code)) {
				return state;
			}
		}
		return null;
	}

	public static List<EstadosPaymentorder> getNextStatesFor(
			EstadosPaymentorder state) {
		EstadosPaymentorder[] states = null;
		switch (state) {
		case CANCELADA:
			states = new EstadosPaymentorder[] {};
			break;
		case FIRMA_EN_PROCESO:
			states = new EstadosPaymentorder[] {FIRMA_EN_PROCESO, PAGO_PENDIENTE,
					CANCELADA };
			break;
		case PAGO_PENDIENTE:
			states = new EstadosPaymentorder[] {PAGO_PENDIENTE, PAGO_EN_PROCESO,
					CANCELADA };
			break;
		case PAGO_EN_PROCESO:
			states = new EstadosPaymentorder[] {PAGO_PENDIENTE, SATISFACTORIO,
					PARCIALMENTE_PAGADA, ERROR};
			break;
		case SATISFACTORIO:
			states = new EstadosPaymentorder[] {REVERSION_EN_PROCESO};
			break;
		case PARCIALMENTE_PAGADA:
			states = new EstadosPaymentorder[] {REVERSION_EN_PROCESO, PAGO_EN_PROCESO};
			break;
		case ERROR:
			states = new EstadosPaymentorder[] {PAGO_EN_PROCESO};
			break;
		case REVERSION_EN_PROCESO:
			states = new EstadosPaymentorder[] { REVERTIDA,
					PARCIALMENTE_REVERTIDA, NO_REVERTIDA };
			break;
		case REVERTIDA:
			states = new EstadosPaymentorder[] {};
			break;
		case PARCIALMENTE_REVERTIDA:
			states = new EstadosPaymentorder[] {};
			break;
		case NO_REVERTIDA:
			states = new EstadosPaymentorder[] {};
			break;
		default:
			break;
		}

		if (states == null) {
			return new ArrayList<EstadosPaymentorder>();
		}
		return Arrays.asList(states);
	}

//	@Override
//	public String toString() {
//		return GeneralHelper.getMessages(getDescription());
//	}

	public static boolean stateIsChildOf(EstadosPaymentorder parentState,
			EstadosPaymentorder childState) {
		List<EstadosPaymentorder> l = EstadosPaymentorder
				.getNextStatesFor(parentState);
		return l.contains(childState);
	}

}
