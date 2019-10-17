
package com.tigo.core.common.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommunicationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CommunicationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ASY"/>
 *     &lt;enumeration value="SYN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CommunicationType", namespace = "http://www.tigo.com/Core/Common/V1")
@XmlEnum
public enum CommunicationType {

    ASY,
    SYN;

    public String value() {
        return name();
    }

    public static CommunicationType fromValue(String v) {
        return valueOf(v);
    }

}
