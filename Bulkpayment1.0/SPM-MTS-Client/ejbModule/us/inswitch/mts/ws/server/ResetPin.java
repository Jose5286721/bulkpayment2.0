
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resetPin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resetPin">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn_suscriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documento_remitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resetPin", propOrder = {
    "sesion",
    "msisdnSuscriptor",
    "documentoRemitente"
})
public class ResetPin {

    protected String sesion;
    @XmlElement(name = "msisdn_suscriptor")
    protected String msisdnSuscriptor;
    @XmlElement(name = "documento_remitente")
    protected String documentoRemitente;

    /**
     * Gets the value of the sesion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesion() {
        return sesion;
    }

    /**
     * Sets the value of the sesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesion(String value) {
        this.sesion = value;
    }

    /**
     * Gets the value of the msisdnSuscriptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsisdnSuscriptor() {
        return msisdnSuscriptor;
    }

    /**
     * Sets the value of the msisdnSuscriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsisdnSuscriptor(String value) {
        this.msisdnSuscriptor = value;
    }

    /**
     * Gets the value of the documentoRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoRemitente() {
        return documentoRemitente;
    }

    /**
     * Sets the value of the documentoRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoRemitente(String value) {
        this.documentoRemitente = value;
    }

}
