package py.com.global.model.systemparameters;

/**
 * JMSSenderParameters Establece los procesos/parametros a ser utilizados en el flujo del JMSSender
 * @author Global SI
 * @version 18.08.02
 * @see {@link py.com.global.model.util.JMSSender}, {@link py.com.global.model.util.QueueManager}
 */
/*TODO pasar a ENUM*/
public class JMSSenderParameters {
    /*Configuraciones por default*/
    public static final String DEFAULT_USERNAME = "appuser";
    public static final String DEFAULT_PASSWORD = "admin123+";
    public static final String DEFAULT_PROVIDER_URL = "127.0.0.1:8081";

    /*Contexto inicial*/
    public static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";

    /* SystemParameter, url para el contexto*/
    public static final String SYSTEM_PARAMETER_CTX_PROVIDER_URL="CtxProviderUrl";

    /* SystemParameter, user para el contexto*/
    public static final String SYSTEM_PARAMETER_CTX_PROVIDER_USER="CtxProviderUser";

    /* SystemParameter, pass para el contexto*/
    public static final String SYSTEM_PARAMETER_CTX_PROVIDER_PASS="CtxProviderPass";


    /* SystemParameter,  nro de proceso para el contexto*/
    public static final long SYSTEM_PARAMETER_JMSSENDER_PROCESS=1011;

}
