
package us.inswitch.mts.ws.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transaccionPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transaccionPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="codPagos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descPagos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detalleTrans" type="{http://server.ws.mts.inswitch.us/}movimientoPack" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idTransaccion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="interfaz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="movilDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="movilOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultado" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="servicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transaccionPack", propOrder = {
    "codPagos",
    "descPagos",
    "detalleTrans",
    "error",
    "fecha",
    "idTransaccion",
    "interfaz",
    "moneda",
    "monto",
    "movilDestino",
    "movilOrigen",
    "resultado",
    "servicio"
})
public class TransaccionPack
    extends BasicResponsePack
{

    protected String codPagos;
    protected String descPagos;
    @XmlElement(nillable = true)
    protected List<MovimientoPack> detalleTrans;
    protected String error;
    protected String fecha;
    protected Integer idTransaccion;
    protected String interfaz;
    protected String moneda;
    protected BigDecimal monto;
    protected String movilDestino;
    protected String movilOrigen;
    protected Integer resultado;
    protected String servicio;

    /**
     * Gets the value of the codPagos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPagos() {
        return codPagos;
    }

    /**
     * Sets the value of the codPagos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPagos(String value) {
        this.codPagos = value;
    }

    /**
     * Gets the value of the descPagos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPagos() {
        return descPagos;
    }

    /**
     * Sets the value of the descPagos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPagos(String value) {
        this.descPagos = value;
    }

    /**
     * Gets the value of the detalleTrans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detalleTrans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetalleTrans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MovimientoPack }
     * 
     * 
     */
    public List<MovimientoPack> getDetalleTrans() {
        if (detalleTrans == null) {
            detalleTrans = new ArrayList<MovimientoPack>();
        }
        return this.detalleTrans;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

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

    /**
     * Gets the value of the interfaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaz() {
        return interfaz;
    }

    /**
     * Sets the value of the interfaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaz(String value) {
        this.interfaz = value;
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
     * Gets the value of the monto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Sets the value of the monto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonto(BigDecimal value) {
        this.monto = value;
    }

    /**
     * Gets the value of the movilDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovilDestino() {
        return movilDestino;
    }

    /**
     * Sets the value of the movilDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovilDestino(String value) {
        this.movilDestino = value;
    }

    /**
     * Gets the value of the movilOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovilOrigen() {
        return movilOrigen;
    }

    /**
     * Sets the value of the movilOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovilOrigen(String value) {
        this.movilOrigen = value;
    }

    /**
     * Gets the value of the resultado property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getResultado() {
        return resultado;
    }

    /**
     * Sets the value of the resultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setResultado(Integer value) {
        this.resultado = value;
    }

    /**
     * Gets the value of the servicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * Sets the value of the servicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicio(String value) {
        this.servicio = value;
    }

}
