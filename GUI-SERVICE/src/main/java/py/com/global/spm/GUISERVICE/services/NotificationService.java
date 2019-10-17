package py.com.global.spm.GUISERVICE.services;

import com.global.common.helper.Configuration;
import com.global.common.helper.ObjectFactory;
import com.global.spm.notificationmanager.IDriverNotificationManager;
import com.global.spm.notificationmanager.RequestNotificationManager;
import com.global.spm.notificationmanager.ResponseNotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.utils.DriversParameters;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final ApplicationContext applicationContext;

    private final SystemParameterService systemParameterService;

    // Driver Classpath en ejecucion
    private String driverClasspath = null;

    // Atributos del objeto
    private ObjectFactory factory = null;

    // Parametrso del driver
    private ConcurrentHashMap<String, String> parameters = null;

    /**
     * Inicializar factory
     */
    @PostConstruct
    private void initialize() {
        // Buscar driver en parametros del sistema
        String classpath = systemParameterService.getSystemParameterValue(SPM_GUI_Constants.DRIVER_NOTIFICATIONS,
                DriversParameters.NOTIFICATIONMANAGER_DRIVERCLASSPATH.getDataBase(),null);
        if (classpath == null) {
            log.error(
                    "Se requiere parametro --> {}",DriversParameters.NOTIFICATIONMANAGER_DRIVERCLASSPATH.getDataBase());
        } else {
            // Asegurar inicializacion
            synchronized (this) {
                if (this.driverClasspath == null || !this.driverClasspath.equals(classpath)) {
                    this.driverClasspath = classpath;
                    // Inicializar object factory
                    log.info("Cargando driver --> {} ",this.driverClasspath);
                    if (this.factory == null)
                        this.factory = new ObjectFactory(driverClasspath, applicationContext.getClassLoader());
                    else
                        this.factory.reset(driverClasspath);
                }

                // Buscar y verificar los parametros
                ConcurrentHashMap<String, String> param = this.systemParameterService
                        .getDriversParameters();
                if (this.parameters == null
                        || Configuration.hashCode(this.parameters) != Configuration.hashCode(param)) {
                    this.factory.reset();
                    this.parameters = param;
                    IDriverNotificationManager driver = (IDriverNotificationManager) factory.getInstance();
                    if (driver == null) {
                        log.error("Driver Notificaciones no instanciado");
                    } else {
                        log.info("Cargando parametros en el driver, Process Id --> {}"
                                ,SPM_GUI_Constants.DRIVER_NOTIFICATIONS);
                        driver.initialize(this.parameters);
                    }
                }
            }
        }
    }

    @Autowired
    public NotificationService(SystemParameterService systemParameterService, ApplicationContext applicationContext) {
        this.systemParameterService = systemParameterService;
        this.applicationContext = applicationContext;
    }

    /*
     * Enviar notificacion
     */
    public ResponseNotificationManager executeNotification(RequestNotificationManager message) {
        ResponseNotificationManager response = null;

        IDriverNotificationManager driver = getInstance();
        if (driver == null) {
            log.error("Driver no instanciado");
        } else {
            // Buscar todos los parametros
            response = driver.sendMessage(message);
        }

        return response;
    }

    private IDriverNotificationManager getInstance() {
        IDriverNotificationManager driver = null;
        synchronized (this) {
            if (this.factory != null)
                driver = (IDriverNotificationManager) this.factory.getInstance();
        }
        return driver;
    }

}
