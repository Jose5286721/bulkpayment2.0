
package us.inswitch.mts.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saldoPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saldoPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="billeteras" type="{http://server.ws.mts.inswitch.us/}billeteraPack" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cantBilleteras" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saldoPack", propOrder = {
    "billeteras",
    "cantBilleteras"
})
public class SaldoPack
    extends BasicResponsePack
{

    @XmlElement(nillable = true)
    protected List<BilleteraPack> billeteras;
    protected Integer cantBilleteras;

    /**
     * Gets the value of the billeteras property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billeteras property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBilleteras().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BilleteraPack }
     * 
     * 
     */
    public List<BilleteraPack> getBilleteras() {
        if (billeteras == null) {
            billeteras = new ArrayList<BilleteraPack>();
        }
        return this.billeteras;
    }

    /**
     * Gets the value of the cantBilleteras property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantBilleteras() {
        return cantBilleteras;
    }

    /**
     * Sets the value of the cantBilleteras property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantBilleteras(Integer value) {
        this.cantBilleteras = value;
    }

}
