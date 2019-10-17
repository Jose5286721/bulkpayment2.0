
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for trnResponsePack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trnResponsePack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="idTransaccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trnResponsePack", propOrder = {
    "idTransaccion"
})
public class TrnResponsePack
    extends BasicResponsePack
{

    protected Integer idTransaccion;

    /**
     * Gets the value of the idTransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * Sets the value of the idTransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdTransaccion(Integer value) {
        this.idTransaccion = value;
    }

}
