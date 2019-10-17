package py.com.global.spm.model.util;

/**
 * Estados que puede tomar una orden de pago en sus distintas etapas
 * 
 * @author Lino Chamorro
 * 
 */
public class PaymentOrderStates {

	public static final String EN_PROCESO = "EP";

	public static final String CANCELADA = "C";

	public static final String PENDIENTE_DE_PAGO = "PP";

	public static final String PAGANDO = "P";

	public static final String PAGADA = "TP";

	public static final String PARCIALMENTE_PAGADA = "PA";

	public static final String NO_PAGADA = "NP";

	public static final String REVIRTIENDO = "R";

	public static final String REVERTIDA = "TR";

	public static final String PARCIALMENTE_REVERTIDA = "PR";

	public static final String NO_REVERTIDA = "NR";

}
