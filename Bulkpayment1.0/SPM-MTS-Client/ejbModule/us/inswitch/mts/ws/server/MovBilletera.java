
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for movBilletera complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="movBilletera">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_ini_anho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_ini_mes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_ini_dia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_fin_anho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_fin_mes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha_fin_dia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movBilletera", propOrder = {
    "sesion",
    "fechaIniAnho",
    "fechaIniMes",
    "fechaIniDia",
    "fechaFinAnho",
    "fechaFinMes",
    "fechaFinDia"
})
public class MovBilletera {

    protected String sesion;
    @XmlElement(name = "fecha_ini_anho")
    protected String fechaIniAnho;
    @XmlElement(name = "fecha_ini_mes")
    protected String fechaIniMes;
    @XmlElement(name = "fecha_ini_dia")
    protected String fechaIniDia;
    @XmlElement(name = "fecha_fin_anho")
    protected String fechaFinAnho;
    @XmlElement(name = "fecha_fin_mes")
    protected String fechaFinMes;
    @XmlElement(name = "fecha_fin_dia")
    protected String fechaFinDia;

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
     * Gets the value of the fechaIniAnho property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaIniAnho() {
        return fechaIniAnho;
    }

    /**
     * Sets the value of the fechaIniAnho property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaIniAnho(String value) {
        this.fechaIniAnho = value;
    }

    /**
     * Gets the value of the fechaIniMes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaIniMes() {
        return fechaIniMes;
    }

    /**
     * Sets the value of the fechaIniMes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaIniMes(String value) {
        this.fechaIniMes = value;
    }

    /**
     * Gets the value of the fechaIniDia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaIniDia() {
        return fechaIniDia;
    }

    /**
     * Sets the value of the fechaIniDia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaIniDia(String value) {
        this.fechaIniDia = value;
    }

    /**
     * Gets the value of the fechaFinAnho property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinAnho() {
        return fechaFinAnho;
    }

    /**
     * Sets the value of the fechaFinAnho property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinAnho(String value) {
        this.fechaFinAnho = value;
    }

    /**
     * Gets the value of the fechaFinMes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinMes() {
        return fechaFinMes;
    }

    /**
     * Sets the value of the fechaFinMes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinMes(String value) {
        this.fechaFinMes = value;
    }

    /**
     * Gets the value of the fechaFinDia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinDia() {
        return fechaFinDia;
    }

    /**
     * Sets the value of the fechaFinDia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinDia(String value) {
        this.fechaFinDia = value;
    }

}
