
package us.inswitch.mts.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for movBilleteraPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="movBilleteraPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="cantMovBilletera" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="transacciones" type="{http://server.ws.mts.inswitch.us/}transaccionV2Pack" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movBilleteraPack", propOrder = {
    "cantMovBilletera",
    "transacciones"
})
public class MovBilleteraPack
    extends BasicResponsePack
{

    protected Integer cantMovBilletera;
    @XmlElement(nillable = true)
    protected List<TransaccionV2Pack> transacciones;

    /**
     * Gets the value of the cantMovBilletera property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantMovBilletera() {
        return cantMovBilletera;
    }

    /**
     * Sets the value of the cantMovBilletera property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantMovBilletera(Integer value) {
        this.cantMovBilletera = value;
    }

    /**
     * Gets the value of the transacciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transacciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransacciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransaccionV2Pack }
     * 
     * 
     */
    public List<TransaccionV2Pack> getTransacciones() {
        if (transacciones == null) {
            transacciones = new ArrayList<TransaccionV2Pack>();
        }
        return this.transacciones;
    }

}
