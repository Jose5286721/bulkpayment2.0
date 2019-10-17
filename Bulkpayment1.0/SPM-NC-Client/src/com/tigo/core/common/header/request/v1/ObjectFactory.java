
package com.tigo.core.common.header.request.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tigo.core.common.header.request.v1 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tigo.core.common.header.request.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestHeader.Consumer }
     * 
     */
    public RequestHeader.Consumer createRequestHeaderConsumer() {
        return new RequestHeader.Consumer();
    }

    /**
     * Create an instance of {@link RequestHeader.Message }
     * 
     */
    public RequestHeader.Message createRequestHeaderMessage() {
        return new RequestHeader.Message();
    }

    /**
     * Create an instance of {@link RequestHeader }
     * 
     */
    public RequestHeader createRequestHeader() {
        return new RequestHeader();
    }

    /**
     * Create an instance of {@link RequestHeader.Consumer.Credentials }
     * 
     */
    public RequestHeader.Consumer.Credentials createRequestHeaderConsumerCredentials() {
        return new RequestHeader.Consumer.Credentials();
    }

    /**
     * Create an instance of {@link RequestHeader.Consumer.Credentials.User }
     * 
     */
    public RequestHeader.Consumer.Credentials.User createRequestHeaderConsumerCredentialsUser() {
        return new RequestHeader.Consumer.Credentials.User();
    }

    /**
     * Create an instance of {@link RequestHeader.Country }
     * 
     */
    public RequestHeader.Country createRequestHeaderCountry() {
        return new RequestHeader.Country();
    }

    /**
     * Create an instance of {@link RequestHeader.Service }
     * 
     */
    public RequestHeader.Service createRequestHeaderService() {
        return new RequestHeader.Service();
    }

    /**
     * Create an instance of {@link RequestHeader.Transport }
     * 
     */
    public RequestHeader.Transport createRequestHeaderTransport() {
        return new RequestHeader.Transport();
    }

}
