
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for salaryPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="salaryPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuenta_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rol_predeterminado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo_billetera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salaryPayment", propOrder = {
    "sesion",
    "cuentaDestino",
    "rolPredeterminado",
    "monto",
    "moneda",
    "brand",
    "tipoBilletera"
})
public class SalaryPayment {

    protected String sesion;
    @XmlElement(name = "cuenta_destino")
    protected String cuentaDestino;
    @XmlElement(name = "rol_predeterminado")
    protected String rolPredeterminado;
    protected Double monto;
    protected String moneda;
    protected String brand;
    @XmlElement(name = "tipo_billetera")
    protected String tipoBilletera;

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
     * Gets the value of the cuentaDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaDestino() {
        return cuentaDestino;
    }

    /**
     * Sets the value of the cuentaDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaDestino(String value) {
        this.cuentaDestino = value;
    }

    /**
     * Gets the value of the rolPredeterminado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolPredeterminado() {
        return rolPredeterminado;
    }

    /**
     * Sets the value of the rolPredeterminado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolPredeterminado(String value) {
        this.rolPredeterminado = value;
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

    /**
     * Gets the value of the moneda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Sets the value of the moneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the tipoBilletera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoBilletera() {
        return tipoBilletera;
    }

    /**
     * Sets the value of the tipoBilletera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoBilletera(String value) {
        this.tipoBilletera = value;
    }

}
