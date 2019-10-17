
package com.tigo.core.common.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransportCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransportCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BPEL10G"/>
 *     &lt;enumeration value="EJB"/>
 *     &lt;enumeration value="EMAIL"/>
 *     &lt;enumeration value="ESB"/>
 *     &lt;enumeration value="FILE_SYSTEM"/>
 *     &lt;enumeration value="FTP"/>
 *     &lt;enumeration value="FTPS"/>
 *     &lt;enumeration value="HTTP"/>
 *     &lt;enumeration value="HTTPS"/>
 *     &lt;enumeration value="JCA"/>
 *     &lt;enumeration value="JMS"/>
 *     &lt;enumeration value="MQ"/>
 *     &lt;enumeration value="POJO"/>
 *     &lt;enumeration value="SOA_DIRECT"/>
 *     &lt;enumeration value="TUXEDO"/>
 *     &lt;enumeration value="WS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransportCodeType", namespace = "http://www.tigo.com/Core/Common/V1")
@XmlEnum
public enum TransportCodeType {

    @XmlEnumValue("BPEL10G")
    BPEL_10_G("BPEL10G"),
    EJB("EJB"),
    EMAIL("EMAIL"),
    ESB("ESB"),
    FILE_SYSTEM("FILE_SYSTEM"),
    FTP("FTP"),
    FTPS("FTPS"),
    HTTP("HTTP"),
    HTTPS("HTTPS"),
    JCA("JCA"),
    JMS("JMS"),
    MQ("MQ"),
    POJO("POJO"),
    SOA_DIRECT("SOA_DIRECT"),
    TUXEDO("TUXEDO"),
    WS("WS");
    private final String value;

    TransportCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransportCodeType fromValue(String v) {
        for (TransportCodeType c: TransportCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
