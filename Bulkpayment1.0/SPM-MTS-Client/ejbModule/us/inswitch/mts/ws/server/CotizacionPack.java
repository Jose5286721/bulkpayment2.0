
package us.inswitch.mts.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cotizacionPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cotizacionPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="cantMonedas" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monedas" type="{http://server.ws.mts.inswitch.us/}monedaPack" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cotizacionPack", propOrder = {
    "cantMonedas",
    "monedas"
})
public class CotizacionPack
    extends BasicResponsePack
{

    protected Integer cantMonedas;
    @XmlElement(nillable = true)
    protected List<MonedaPack> monedas;

    /**
     * Gets the value of the cantMonedas property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantMonedas() {
        return cantMonedas;
    }

    /**
     * Sets the value of the cantMonedas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantMonedas(Integer value) {
        this.cantMonedas = value;
    }

    /**
     * Gets the value of the monedas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monedas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonedas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonedaPack }
     * 
     * 
     */
    public List<MonedaPack> getMonedas() {
        if (monedas == null) {
            monedas = new ArrayList<MonedaPack>();
        }
        return this.monedas;
    }

}
