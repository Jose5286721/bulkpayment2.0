
package com.tigo.core.common.header.request.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tigo.core.common.v1.CommunicationType;
import com.tigo.core.common.v1.TransportCodeType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Consumer">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Credentials" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice minOccurs="0">
 *                               &lt;element name="certificates" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                               &lt;element name="User">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Transport">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="applicationCode" type="{http://www.tigo.com/Core/Common/V1}CodeType" minOccurs="0"/>
 *                   &lt;element name="responseQueue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                   &lt;element name="responseQueueAdministrator" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                   &lt;element name="ServiceCode" type="{http://www.tigo.com/Core/Common/V1}CodeType" minOccurs="0"/>
 *                   &lt;element name="communicationType" type="{http://www.tigo.com/Core/Common/V1}CommunicationType"/>
 *                 &lt;/all>
 *                 &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}TransportCodeType" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Service">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="retryCounter" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="retryInterval" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Message">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="messageId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                 &lt;attribute name="messageIdCorrelation" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                 &lt;attribute name="conversationId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Country">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="isoCode" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "RequestHeader")
public class RequestHeader {

    @XmlElement(name = "Consumer", required = true)
    protected RequestHeader.Consumer consumer;
    @XmlElement(name = "Transport", required = true)
    protected RequestHeader.Transport transport;
    @XmlElement(name = "Service", required = true)
    protected RequestHeader.Service service;
    @XmlElement(name = "Message", required = true)
    protected RequestHeader.Message message;
    @XmlElement(name = "Country", required = true)
    protected RequestHeader.Country country;

    /**
     * Gets the value of the consumer property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader.Consumer }
     *     
     */
    public RequestHeader.Consumer getConsumer() {
        return consumer;
    }

    /**
     * Sets the value of the consumer property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader.Consumer }
     *     
     */
    public void setConsumer(RequestHeader.Consumer value) {
        this.consumer = value;
    }

    /**
     * Gets the value of the transport property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader.Transport }
     *     
     */
    public RequestHeader.Transport getTransport() {
        return transport;
    }

    /**
     * Sets the value of the transport property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader.Transport }
     *     
     */
    public void setTransport(RequestHeader.Transport value) {
        this.transport = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader.Service }
     *     
     */
    public RequestHeader.Service getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader.Service }
     *     
     */
    public void setService(RequestHeader.Service value) {
        this.service = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader.Message }
     *     
     */
    public RequestHeader.Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader.Message }
     *     
     */
    public void setMessage(RequestHeader.Message value) {
        this.message = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader.Country }
     *     
     */
    public RequestHeader.Country getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader.Country }
     *     
     */
    public void setCountry(RequestHeader.Country value) {
        this.country = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Credentials" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice minOccurs="0">
     *                     &lt;element name="certificates" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                     &lt;element name="User">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                               &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "credentials"
    })
    public static class Consumer {

        @XmlElement(name = "Credentials")
        protected RequestHeader.Consumer.Credentials credentials;
        @XmlAttribute(required = true)
        protected String code;
        @XmlAttribute
        protected String name;

        /**
         * Gets the value of the credentials property.
         * 
         * @return
         *     possible object is
         *     {@link RequestHeader.Consumer.Credentials }
         *     
         */
        public RequestHeader.Consumer.Credentials getCredentials() {
            return credentials;
        }

        /**
         * Sets the value of the credentials property.
         * 
         * @param value
         *     allowed object is
         *     {@link RequestHeader.Consumer.Credentials }
         *     
         */
        public void setCredentials(RequestHeader.Consumer.Credentials value) {
            this.credentials = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;choice minOccurs="0">
         *           &lt;element name="certificates" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *           &lt;element name="User">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                     &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *         &lt;/choice>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "certificates",
            "user"
        })
        public static class Credentials {

            protected String certificates;
            @XmlElement(name = "User")
            protected RequestHeader.Consumer.Credentials.User user;

            /**
             * Gets the value of the certificates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCertificates() {
                return certificates;
            }

            /**
             * Sets the value of the certificates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCertificates(String value) {
                this.certificates = value;
            }

            /**
             * Gets the value of the user property.
             * 
             * @return
             *     possible object is
             *     {@link RequestHeader.Consumer.Credentials.User }
             *     
             */
            public RequestHeader.Consumer.Credentials.User getUser() {
                return user;
            }

            /**
             * Sets the value of the user property.
             * 
             * @param value
             *     allowed object is
             *     {@link RequestHeader.Consumer.Credentials.User }
             *     
             */
            public void setUser(RequestHeader.Consumer.Credentials.User value) {
                this.user = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "userName",
                "password"
            })
            public static class User {

                @XmlElement(required = true)
                protected String userName;
                @XmlElement(required = true)
                protected String password;

                /**
                 * Gets the value of the userName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUserName() {
                    return userName;
                }

                /**
                 * Sets the value of the userName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUserName(String value) {
                    this.userName = value;
                }

                /**
                 * Gets the value of the password property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPassword() {
                    return password;
                }

                /**
                 * Sets the value of the password property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPassword(String value) {
                    this.password = value;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="isoCode" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Country {

        @XmlAttribute(required = true)
        protected String name;
        @XmlAttribute(required = true)
        protected String isoCode;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the isoCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIsoCode() {
            return isoCode;
        }

        /**
         * Sets the value of the isoCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsoCode(String value) {
            this.isoCode = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="messageId" type="{http://www.w3.org/2001/XMLSchema}long" />
     *       &lt;attribute name="messageIdCorrelation" type="{http://www.w3.org/2001/XMLSchema}long" />
     *       &lt;attribute name="conversationId" type="{http://www.w3.org/2001/XMLSchema}long" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Message {

        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar timestamp;
        protected BigInteger expiration;
        @XmlAttribute
        protected Long messageId;
        @XmlAttribute
        protected Long messageIdCorrelation;
        @XmlAttribute
        protected Long conversationId;

        /**
         * Gets the value of the timestamp property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the timestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTimestamp(XMLGregorianCalendar value) {
            this.timestamp = value;
        }

        /**
         * Gets the value of the expiration property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getExpiration() {
            return expiration;
        }

        /**
         * Sets the value of the expiration property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setExpiration(BigInteger value) {
            this.expiration = value;
        }

        /**
         * Gets the value of the messageId property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getMessageId() {
            return messageId;
        }

        /**
         * Sets the value of the messageId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setMessageId(Long value) {
            this.messageId = value;
        }

        /**
         * Gets the value of the messageIdCorrelation property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getMessageIdCorrelation() {
            return messageIdCorrelation;
        }

        /**
         * Sets the value of the messageIdCorrelation property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setMessageIdCorrelation(Long value) {
            this.messageIdCorrelation = value;
        }

        /**
         * Gets the value of the conversationId property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getConversationId() {
            return conversationId;
        }

        /**
         * Sets the value of the conversationId property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setConversationId(Long value) {
            this.conversationId = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="retryCounter" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="retryInterval" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Service {

        protected BigInteger retryCounter;
        protected Long retryInterval;
        @XmlAttribute(required = true)
        protected String code;
        @XmlAttribute
        protected String name;

        /**
         * Gets the value of the retryCounter property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRetryCounter() {
            return retryCounter;
        }

        /**
         * Sets the value of the retryCounter property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRetryCounter(BigInteger value) {
            this.retryCounter = value;
        }

        /**
         * Gets the value of the retryInterval property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getRetryInterval() {
            return retryInterval;
        }

        /**
         * Sets the value of the retryInterval property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setRetryInterval(Long value) {
            this.retryInterval = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="applicationCode" type="{http://www.tigo.com/Core/Common/V1}CodeType" minOccurs="0"/>
     *         &lt;element name="responseQueue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *         &lt;element name="responseQueueAdministrator" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *         &lt;element name="ServiceCode" type="{http://www.tigo.com/Core/Common/V1}CodeType" minOccurs="0"/>
     *         &lt;element name="communicationType" type="{http://www.tigo.com/Core/Common/V1}CommunicationType"/>
     *       &lt;/all>
     *       &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}TransportCodeType" />
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Transport {

        protected String applicationCode;
        protected Object responseQueue;
        protected Object responseQueueAdministrator;
        @XmlElement(name = "ServiceCode")
        protected String serviceCode;
        @XmlElement(required = true)
        protected CommunicationType communicationType;
        @XmlAttribute(required = true)
        protected TransportCodeType code;
        @XmlAttribute
        protected String name;

        /**
         * Gets the value of the applicationCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationCode() {
            return applicationCode;
        }

        /**
         * Sets the value of the applicationCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationCode(String value) {
            this.applicationCode = value;
        }

        /**
         * Gets the value of the responseQueue property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getResponseQueue() {
            return responseQueue;
        }

        /**
         * Sets the value of the responseQueue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setResponseQueue(Object value) {
            this.responseQueue = value;
        }

        /**
         * Gets the value of the responseQueueAdministrator property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getResponseQueueAdministrator() {
            return responseQueueAdministrator;
        }

        /**
         * Sets the value of the responseQueueAdministrator property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setResponseQueueAdministrator(Object value) {
            this.responseQueueAdministrator = value;
        }

        /**
         * Gets the value of the serviceCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceCode() {
            return serviceCode;
        }

        /**
         * Sets the value of the serviceCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceCode(String value) {
            this.serviceCode = value;
        }

        /**
         * Gets the value of the communicationType property.
         * 
         * @return
         *     possible object is
         *     {@link CommunicationType }
         *     
         */
        public CommunicationType getCommunicationType() {
            return communicationType;
        }

        /**
         * Sets the value of the communicationType property.
         * 
         * @param value
         *     allowed object is
         *     {@link CommunicationType }
         *     
         */
        public void setCommunicationType(CommunicationType value) {
            this.communicationType = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link TransportCodeType }
         *     
         */
        public TransportCodeType getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link TransportCodeType }
         *     
         */
        public void setCode(TransportCodeType value) {
            this.code = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}
