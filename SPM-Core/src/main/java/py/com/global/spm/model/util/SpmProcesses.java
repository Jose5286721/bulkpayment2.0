package py.com.global.spm.model.util;

/**
 *
 * @author R2
 *
 */
public class SpmProcesses {

    public static final long SPM_GENERAL = 1000L;

    public static final long PO_MANAGER = 1001L;

    public static final long FLOW_MANAGER = 1002L;

    public static final long SMS_MANAGER = 1003L;

    public static final long TRANSFER_PROCESS = 1004L;

    public static final long MTS_TRANSFER_INTERFACE = 1005L;

    public static final long MTS_REVERSION_INTERFACE = 1006L;

    public static final long SPM_NOTIFICATION_MANAGER = 1007L;

    public static final long ASYNC_UPDATER= 1008L;//Id del proceso.

    public static final long UPDATER = 1009L;//Id del proceso.

    public static final long REVERSION_PROCESS = 1010L;//Id del proceso

    public static final long GUI = 1011L;//Id del proceso

    public static final long LOGPAYMENT_PROCESS =1012L; //Proceso para enviar a la cola los LogPayments a pagar

    public static final  long DRIVER_NOTIFICATIONS = 1013L; //Proceso para la comunicación con el driver de notificaciones

    public static final  long DRIVER_MTS = 1014L;

}
