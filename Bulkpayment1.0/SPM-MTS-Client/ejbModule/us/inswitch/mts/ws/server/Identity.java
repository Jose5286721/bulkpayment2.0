
package us.inswitch.mts.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="theString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identity", propOrder = {
    "theString"
})
public class Identity {

    protected String theString;

    /**
     * Gets the value of the theString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTheString() {
        return theString;
    }

    /**
     * Sets the value of the theString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTheString(String value) {
        this.theString = value;
    }

}
