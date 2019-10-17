
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cashOutConfirmEscrow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cashOutConfirmEscrow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuenta_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documento_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="fecha_nacimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cashOutConfirmEscrow", propOrder = {
    "sesion",
    "cuentaCliente",
    "documentoCliente",
    "monto",
    "fechaNacimiento"
})
public class CashOutConfirmEscrow {

    protected String sesion;
    @XmlElement(name = "cuenta_cliente")
    protected String cuentaCliente;
    @XmlElement(name = "documento_cliente")
    protected String documentoCliente;
    protected Double monto;
    @XmlElement(name = "fecha_nacimiento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaNacimiento;

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
     * Gets the value of the cuentaCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaCliente() {
        return cuentaCliente;
    }

    /**
     * Sets the value of the cuentaCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaCliente(String value) {
        this.cuentaCliente = value;
    }

    /**
     * Gets the value of the documentoCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoCliente() {
        return documentoCliente;
    }

    /**
     * Sets the value of the documentoCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoCliente(String value) {
        this.documentoCliente = value;
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
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaNacimiento(XMLGregorianCalendar value) {
        this.fechaNacimiento = value;
    }

}
