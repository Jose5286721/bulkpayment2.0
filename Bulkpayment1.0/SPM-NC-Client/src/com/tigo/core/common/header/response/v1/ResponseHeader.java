
package com.tigo.core.common.header.response.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tigo.core.common.v1.MessageStateType;


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
 *                 &lt;attribute name="code" use="required" type="{http://www.tigo.com/Core/Common/V1}CodeType" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Service">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
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
 *                   &lt;element name="state" type="{http://www.tigo.com/Core/Common/V1}MessageStateType" minOccurs="0"/>
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
@XmlRootElement(name = "ResponseHeader")
public class ResponseHeader {

    @XmlElement(name = "Consumer", required = true)
    protected ResponseHeader.Consumer consumer;
    @XmlElement(name = "Service", required = true)
    protected ResponseHeader.Service service;
    @XmlElement(name = "Message", required = true)
    protected ResponseHeader.Message message;
    @XmlElement(name = "Country", required = true)
    protected ResponseHeader.Country country;

    /**
     * Gets the value of the consumer property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader.Consumer }
     *     
     */
    public ResponseHeader.Consumer getConsumer() {
        return consumer;
    }

    /**
     * Sets the value of the consumer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader.Consumer }
     *     
     */
    public void setConsumer(ResponseHeader.Consumer value) {
        this.consumer = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader.Service }
     *     
     */
    public ResponseHeader.Service getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader.Service }
     *     
     */
    public void setService(ResponseHeader.Service value) {
        this.service = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader.Message }
     *     
     */
    public ResponseHeader.Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader.Message }
     *     
     */
    public void setMessage(ResponseHeader.Message value) {
        this.message = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader.Country }
     *     
     */
    public ResponseHeader.Country getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader.Country }
     *     
     */
    public void setCountry(ResponseHeader.Country value) {
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
    @XmlType(name = "")
    public static class Consumer {

        @XmlAttribute(required = true)
        protected String code;
        @XmlAttribute
        protected String name;

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
     *         &lt;element name="state" type="{http://www.tigo.com/Core/Common/V1}MessageStateType" minOccurs="0"/>
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
        protected MessageStateType state;
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
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link MessageStateType }
         *     
         */
        public MessageStateType getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link MessageStateType }
         *     
         */
        public void setState(MessageStateType value) {
            this.state = value;
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
    @XmlType(name = "")
    public static class Service {

        @XmlAttribute(required = true)
        protected String code;
        @XmlAttribute
        protected String name;

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

}
