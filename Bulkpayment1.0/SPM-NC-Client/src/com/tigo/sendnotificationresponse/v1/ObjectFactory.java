
package com.tigo.sendnotificationresponse.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tigo.sendnotificationresponse.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tigo.sendnotificationresponse.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendNotificationResponse.ResponseBody }
     * 
     */
    public SendNotificationResponse.ResponseBody createSendNotificationResponseResponseBody() {
        return new SendNotificationResponse.ResponseBody();
    }

    /**
     * Create an instance of {@link SendNotificationResponse.ResponseBody.AdditionalResult }
     * 
     */
    public SendNotificationResponse.ResponseBody.AdditionalResult createSendNotificationResponseResponseBodyAdditionalResult() {
        return new SendNotificationResponse.ResponseBody.AdditionalResult();
    }

    /**
     * Create an instance of {@link SendNotificationResponse }
     * 
     */
    public SendNotificationResponse createSendNotificationResponse() {
        return new SendNotificationResponse();
    }

}
