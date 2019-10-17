
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usuarioPack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usuarioPack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://server.ws.mts.inswitch.us/}basicResponsePack">
 *       &lt;sequence>
 *         &lt;element name="cdi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nroVisita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="perfil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ultVisita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usuarioPack", propOrder = {
    "cdi",
    "estado",
    "nombre",
    "nroVisita",
    "perfil",
    "rol",
    "ultVisita"
})
public class UsuarioPack
    extends BasicResponsePack
{

    protected String cdi;
    protected String estado;
    protected String nombre;
    protected Integer nroVisita;
    protected String perfil;
    protected String rol;
    protected String ultVisita;

    /**
     * Gets the value of the cdi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdi() {
        return cdi;
    }

    /**
     * Sets the value of the cdi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdi(String value) {
        this.cdi = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the nroVisita property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNroVisita() {
        return nroVisita;
    }

    /**
     * Sets the value of the nroVisita property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNroVisita(Integer value) {
        this.nroVisita = value;
    }

    /**
     * Gets the value of the perfil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * Sets the value of the perfil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerfil(String value) {
        this.perfil = value;
    }

    /**
     * Gets the value of the rol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRol() {
        return rol;
    }

    /**
     * Sets the value of the rol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRol(String value) {
        this.rol = value;
    }

    /**
     * Gets the value of the ultVisita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUltVisita() {
        return ultVisita;
    }

    /**
     * Sets the value of the ultVisita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUltVisita(String value) {
        this.ultVisita = value;
    }

}
