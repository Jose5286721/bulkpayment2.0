package py.com.global.spm.model.util;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pResponse;

import javax.management.MalformedObjectNameException;

public class ActiveMQClient {
    private J4pClient j4pClient;
    private String brokerName;
    private String queuename;

    public ActiveMQClient(String host, String user, String password, String brokerName, String queuename) {
        this.brokerName = brokerName;
        j4pClient = J4pClient.url("http://" + host + ":8161/api/jolokia")
                .user(user)
                .password(password)
                .build();
        this.queuename = queuename;
    }

    public Long getNumberOfPendingMessages() throws MalformedObjectNameException, J4pException {
        J4pReadRequest j4pReadRequest = new J4pReadRequest("org.apache.activemq:brokerName=" + brokerName + ",destinationName=" + queuename + ",destinationType=Queue,type=Broker", "QueueSize");
        J4pResponse<J4pReadRequest> response = j4pClient.execute(j4pReadRequest);
        return response.getValue();
    }

    public Long getNumberOfEnqueuedMessages(String queueName) throws MalformedObjectNameException, J4pException {
        J4pReadRequest j4pReadRequest = new J4pReadRequest("org.apache.activemq:brokerName=" + brokerName + ",destinationName=" + queueName + ",destinationType=Queue,type=Broker", "EnqueueCount");
        J4pResponse<J4pReadRequest> response = j4pClient.execute(j4pReadRequest);
        return response.getValue();
    }
}
