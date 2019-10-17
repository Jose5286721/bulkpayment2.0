package py.com.global.spm.model.services.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum EstadosPaymentorder {
	EN_PROCESO("EP", "Paymentorder_state_EP"), 
	CANCELADA("C", "Paymentorder_state_C"), 
	PENDIENTE_PAGO("PP", "Paymentorder_state_PP"), 
	PAGANDO("P", "Paymentorder_state_P"), 
	PAGADA("TP", "Paymentorder_state_TP"), 
	PARCIALMENTE_PAGADA("PA", "Paymentorder_state_PA"), 
	NO_PAGADA("NP", "Paymentorder_state_NP"), 
	REVIRTIENDO("R", "Paymentorder_state_R"), 
	REVERTIDA("TR", "Paymentorder_state_TR"), 
	PARCIALMENTE_REVERTIDA("PR", "Paymentorder_state_PR"), 
	NO_REVERTIDA("NR", "Paymentorder_state_NR");

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
		case EN_PROCESO:
			states = new EstadosPaymentorder[] { EN_PROCESO, PENDIENTE_PAGO,
					CANCELADA };
			break;
		case PENDIENTE_PAGO:
			states = new EstadosPaymentorder[] { PENDIENTE_PAGO, PAGANDO,
					CANCELADA };
			break;
		case PAGANDO:
			states = new EstadosPaymentorder[] { PENDIENTE_PAGO, PAGADA,
					PARCIALMENTE_PAGADA, NO_PAGADA };
			break;
		case PAGADA:
			states = new EstadosPaymentorder[] { REVIRTIENDO };
			break;
		case PARCIALMENTE_PAGADA:
			states = new EstadosPaymentorder[] { REVIRTIENDO, PAGANDO };
			break;
		case NO_PAGADA:
			states = new EstadosPaymentorder[] { PAGANDO };
			break;
		case REVIRTIENDO:
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
	public static boolean stateIsChildOf(EstadosPaymentorder parentState,
			EstadosPaymentorder childState) {
		List<EstadosPaymentorder> l = EstadosPaymentorder
				.getNextStatesFor(parentState);
		return l.contains(childState);
	}

}
