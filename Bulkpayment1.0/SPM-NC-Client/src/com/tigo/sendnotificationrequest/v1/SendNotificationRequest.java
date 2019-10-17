
package com.tigo.sendnotificationrequest.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.tigo.core.common.header.request.v1.RequestHeader;
import com.tigo.sendnotification.v1.AttachmentType;
import com.tigo.sendnotification.v1.ParamType;


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
 *         &lt;element ref="{http://www.tigo.com/Core/Common/Header/Request/V1}RequestHeader"/>
 *         &lt;element name="requestBody">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="destiny" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idPlatform" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="parameters">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://www.tigo.com/SendNotification/V1}ParamType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="attachments">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://www.tigo.com/SendNotification/V1}AttachmentType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "requestHeader",
    "requestBody"
})
@XmlRootElement(name = "SendNotificationRequest")
public class SendNotificationRequest {

    @XmlElement(name = "RequestHeader", namespace = "http://www.tigo.com/Core/Common/Header/Request/V1", required = true)
    protected RequestHeader requestHeader;
    @XmlElement(required = true)
    protected SendNotificationRequest.RequestBody requestBody;

    /**
     * Gets the value of the requestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader }
     *     
     */
    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    /**
     * Sets the value of the requestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader }
     *     
     */
    public void setRequestHeader(RequestHeader value) {
        this.requestHeader = value;
    }

    /**
     * Gets the value of the requestBody property.
     * 
     * @return
     *     possible object is
     *     {@link SendNotificationRequest.RequestBody }
     *     
     */
    public SendNotificationRequest.RequestBody getRequestBody() {
        return requestBody;
    }

    /**
     * Sets the value of the requestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendNotificationRequest.RequestBody }
     *     
     */
    public void setRequestBody(SendNotificationRequest.RequestBody value) {
        this.requestBody = value;
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
     *         &lt;element name="destiny" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idPlatform" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="parameters">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.tigo.com/SendNotification/V1}ParamType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="attachments">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.tigo.com/SendNotification/V1}AttachmentType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "destiny",
        "idPlatform",
        "idProcess",
        "parameters",
        "attachments"
    })
    public static class RequestBody {

        @XmlElement(required = true)
        protected String destiny;
        @XmlElement(required = true)
        protected String idPlatform;
        @XmlElement(required = true)
        protected String idProcess;
        @XmlElement(required = true)
        protected SendNotificationRequest.RequestBody.Parameters parameters;
        @XmlElement(required = true)
        protected SendNotificationRequest.RequestBody.Attachments attachments;

        /**
         * Gets the value of the destiny property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDestiny() {
            return destiny;
        }

        /**
         * Sets the value of the destiny property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDestiny(String value) {
            this.destiny = value;
        }

        /**
         * Gets the value of the idPlatform property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdPlatform() {
            return idPlatform;
        }

        /**
         * Sets the value of the idPlatform property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdPlatform(String value) {
            this.idPlatform = value;
        }

        /**
         * Gets the value of the idProcess property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdProcess() {
            return idProcess;
        }

        /**
         * Sets the value of the idProcess property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdProcess(String value) {
            this.idProcess = value;
        }

        /**
         * Gets the value of the parameters property.
         * 
         * @return
         *     possible object is
         *     {@link SendNotificationRequest.RequestBody.Parameters }
         *     
         */
        public SendNotificationRequest.RequestBody.Parameters getParameters() {
            return parameters;
        }

        /**
         * Sets the value of the parameters property.
         * 
         * @param value
         *     allowed object is
         *     {@link SendNotificationRequest.RequestBody.Parameters }
         *     
         */
        public void setParameters(SendNotificationRequest.RequestBody.Parameters value) {
            this.parameters = value;
        }

        /**
         * Gets the value of the attachments property.
         * 
         * @return
         *     possible object is
         *     {@link SendNotificationRequest.RequestBody.Attachments }
         *     
         */
        public SendNotificationRequest.RequestBody.Attachments getAttachments() {
            return attachments;
        }

        /**
         * Sets the value of the attachments property.
         * 
         * @param value
         *     allowed object is
         *     {@link SendNotificationRequest.RequestBody.Attachments }
         *     
         */
        public void setAttachments(SendNotificationRequest.RequestBody.Attachments value) {
            this.attachments = value;
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
         *         &lt;element ref="{http://www.tigo.com/SendNotification/V1}AttachmentType" maxOccurs="unbounded"/>
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
            "attachmentType"
        })
        public static class Attachments {

            @XmlElement(name = "AttachmentType", namespace = "http://www.tigo.com/SendNotification/V1", required = true)
            protected List<AttachmentType> attachmentType;

            /**
             * Gets the value of the attachmentType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the attachmentType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAttachmentType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttachmentType }
             * 
             * 
             */
            public List<AttachmentType> getAttachmentType() {
                if (attachmentType == null) {
                    attachmentType = new ArrayList<AttachmentType>();
                }
                return this.attachmentType;
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
         *       &lt;sequence>
         *         &lt;element ref="{http://www.tigo.com/SendNotification/V1}ParamType" maxOccurs="unbounded"/>
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
            "paramType"
        })
        public static class Parameters {

            @XmlElement(name = "ParamType", namespace = "http://www.tigo.com/SendNotification/V1", required = true)
            protected List<ParamType> paramType;

            /**
             * Gets the value of the paramType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the paramType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getParamType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParamType }
             * 
             * 
             */
            public List<ParamType> getParamType() {
                if (paramType == null) {
                    paramType = new ArrayList<ParamType>();
                }
                return this.paramType;
            }

        }

    }

}
