
package com.tigo.sendnotificationresponse.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.tigo.core.common.error.v1.Error;
import com.tigo.core.common.header.response.v1.ResponseHeader;
import com.tigo.parametertype.v1.ParameterType;


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
 *         &lt;element ref="{http://www.tigo.com/Core/Common/Header/Response/V1}ResponseHeader"/>
 *         &lt;element name="responseBody">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="destiny" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="transactionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="additionalResult">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence maxOccurs="unbounded">
 *                             &lt;element ref="{http://www.tigo.com/ParameterType/V1}ParameterType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{http://www.tigo.com/Core/Common/Error/V1}Error" minOccurs="0"/>
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
    "responseHeader",
    "responseBody"
})
@XmlRootElement(name = "SendNotificationResponse")
public class SendNotificationResponse {

    @XmlElement(name = "ResponseHeader", namespace = "http://www.tigo.com/Core/Common/Header/Response/V1", required = true)
    protected ResponseHeader responseHeader;
    @XmlElement(required = true)
    protected SendNotificationResponse.ResponseBody responseBody;

    /**
     * Gets the value of the responseHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader }
     *     
     */
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     * Sets the value of the responseHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader }
     *     
     */
    public void setResponseHeader(ResponseHeader value) {
        this.responseHeader = value;
    }

    /**
     * Gets the value of the responseBody property.
     * 
     * @return
     *     possible object is
     *     {@link SendNotificationResponse.ResponseBody }
     *     
     */
    public SendNotificationResponse.ResponseBody getResponseBody() {
        return responseBody;
    }

    /**
     * Sets the value of the responseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendNotificationResponse.ResponseBody }
     *     
     */
    public void setResponseBody(SendNotificationResponse.ResponseBody value) {
        this.responseBody = value;
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
     *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="destiny" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="transactionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="additionalResult">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element ref="{http://www.tigo.com/ParameterType/V1}ParameterType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{http://www.tigo.com/Core/Common/Error/V1}Error" minOccurs="0"/>
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
        "success",
        "destiny",
        "transactionCode",
        "additionalResult",
        "error"
    })
    public static class ResponseBody {

        protected boolean success;
        @XmlElement(required = true)
        protected String destiny;
        @XmlElement(required = true)
        protected String transactionCode;
        @XmlElement(required = true)
        protected SendNotificationResponse.ResponseBody.AdditionalResult additionalResult;
        @XmlElement(name = "Error", namespace = "http://www.tigo.com/Core/Common/Error/V1")
        protected Error error;

        /**
         * Gets the value of the success property.
         * 
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Sets the value of the success property.
         * 
         */
        public void setSuccess(boolean value) {
            this.success = value;
        }

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
         * Gets the value of the transactionCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransactionCode() {
            return transactionCode;
        }

        /**
         * Sets the value of the transactionCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransactionCode(String value) {
            this.transactionCode = value;
        }

        /**
         * Gets the value of the additionalResult property.
         * 
         * @return
         *     possible object is
         *     {@link SendNotificationResponse.ResponseBody.AdditionalResult }
         *     
         */
        public SendNotificationResponse.ResponseBody.AdditionalResult getAdditionalResult() {
            return additionalResult;
        }

        /**
         * Sets the value of the additionalResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link SendNotificationResponse.ResponseBody.AdditionalResult }
         *     
         */
        public void setAdditionalResult(SendNotificationResponse.ResponseBody.AdditionalResult value) {
            this.additionalResult = value;
        }

        /**
         * Gets the value of the error property.
         * 
         * @return
         *     possible object is
         *     {@link Error }
         *     
         */
        public Error getError() {
            return error;
        }

        /**
         * Sets the value of the error property.
         * 
         * @param value
         *     allowed object is
         *     {@link Error }
         *     
         */
        public void setError(Error value) {
            this.error = value;
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
         *       &lt;sequence maxOccurs="unbounded">
         *         &lt;element ref="{http://www.tigo.com/ParameterType/V1}ParameterType"/>
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
            "parameterType"
        })
        public static class AdditionalResult {

            @XmlElement(name = "ParameterType", namespace = "http://www.tigo.com/ParameterType/V1", required = true)
            protected List<ParameterType> parameterType;

            /**
             * Gets the value of the parameterType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the parameterType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getParameterType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParameterType }
             * 
             * 
             */
            public List<ParameterType> getParameterType() {
                if (parameterType == null) {
                    parameterType = new ArrayList<ParameterType>();
                }
                return this.parameterType;
            }

        }

    }

}
