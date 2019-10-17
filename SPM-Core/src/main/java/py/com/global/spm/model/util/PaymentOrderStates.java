package py.com.global.spm.model.util;

/**
 * Estados que puede tomar una orden de pago en sus distintas etapas
 *
 * @author Lino Chamorro
 *
 */
public class PaymentOrderStates {

    public static final String FIRMA_EN_PROCESO = "FX";

    public static final String CANCELADA = "CA";

    public static final String PAGO_PENDIENTE = "PP";

    public static final String PAGO_EN_PROCESO = "PX";

    public static final String SATISFACTORIO = "SU";

    public static final String PARCIALMENTE_PAGADA = "SP";

    public static final String ERROR = "ER";

    public static final String REVERSION_EN_PROCESO = "RX";

    public static final String REVERTIDA = "RE";

    public static final String REVERTIDA_PARCIALMENTE = "RP";

    public static final String NO_REVERTIDA = "RN";

}
