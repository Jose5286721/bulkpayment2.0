
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cambioPin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cambioPin">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actual_pin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuevo_pin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cambioPin", propOrder = {
    "sesion",
    "actualPin",
    "nuevoPin"
})
public class CambioPin {

    protected String sesion;
    @XmlElement(name = "actual_pin")
    protected String actualPin;
    @XmlElement(name = "nuevo_pin")
    protected String nuevoPin;

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
     * Gets the value of the actualPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActualPin() {
        return actualPin;
    }

    /**
     * Sets the value of the actualPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActualPin(String value) {
        this.actualPin = value;
    }

    /**
     * Gets the value of the nuevoPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuevoPin() {
        return nuevoPin;
    }

    /**
     * Sets the value of the nuevoPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuevoPin(String value) {
        this.nuevoPin = value;
    }

}
