package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.model.systemparameters.GeneralParameters;

import javax.annotation.PostConstruct;
import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;


@Service
public class ActiveMQClientService {
    private static final Logger log = LoggerFactory
            .getLogger(ActiveMQClientService.class);
    private MBeanServerConnection connection;
    private String brokerName;

    private final SystemParameterService systemParameterService;

    @Autowired
    public ActiveMQClientService(SystemParameterService systemParameterService){
        this.systemParameterService = systemParameterService;
    }

    public Long getQueueSize(String queueName, String attribute) throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException {
        ObjectName objectNameRequest = new ObjectName(
                    "org.apache.activemq:type=Broker,brokerName="+brokerName+",destinationType=Queue,destinationName="
                            + queueName);
        return (Long) connection.getAttribute(objectNameRequest, attribute);
    }


    @PostConstruct
    public void initialize(){
        try {
            brokerName = systemParameterService.getParameterValue(GeneralParameters.QUEUE_BROKER_NAME.getIdProcess(),GeneralParameters.QUEUE_BROKER_NAME.getParameterName());
            String endpoint = systemParameterService.getParameterValue(GeneralParameters.QUEUE_ENDPOINT_URL.getIdProcess(),GeneralParameters.QUEUE_ENDPOINT_URL.getParameterName());

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+endpoint+"/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url);
            connection = jmxc.getMBeanServerConnection();
        } catch (Exception e) {
            log.error("Error al crear conexion con el jmx {}", e.getMessage());
        }
    }

}


