
package us.inswitch.mts.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rolPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rolPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="cantPermisos" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="permisos" type="{http://server.ws.mts.inswitch.us/}idPack" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rolPack", propOrder = {
    "cantPermisos",
    "permisos"
})
public class RolPack
    extends BasicResponsePack
{

    protected Integer cantPermisos;
    @XmlElement(nillable = true)
    protected List<IdPack> permisos;

    /**
     * Gets the value of the cantPermisos property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantPermisos() {
        return cantPermisos;
    }

    /**
     * Sets the value of the cantPermisos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantPermisos(Integer value) {
        this.cantPermisos = value;
    }

    /**
     * Gets the value of the permisos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permisos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermisos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdPack }
     * 
     * 
     */
    public List<IdPack> getPermisos() {
        if (permisos == null) {
            permisos = new ArrayList<IdPack>();
        }
        return this.permisos;
    }

}
