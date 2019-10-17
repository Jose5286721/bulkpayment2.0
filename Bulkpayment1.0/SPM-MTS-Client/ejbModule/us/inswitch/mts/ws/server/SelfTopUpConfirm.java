
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for selfTopUpConfirm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="selfTopUpConfirm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selfTopUpConfirm", propOrder = {
    "sesion",
    "monto"
})
public class SelfTopUpConfirm {

    protected String sesion;
    protected Double monto;

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
     * Gets the value of the monto property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * Sets the value of the monto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMonto(Double value) {
        this.monto = value;
    }

}
