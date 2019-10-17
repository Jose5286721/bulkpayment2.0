
package com.tigo.core.common.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="COM"/>
 *     &lt;enumeration value="MSJ"/>
 *     &lt;enumeration value="NEG"/>
 *     &lt;enumeration value="TEC"/>
 *     &lt;enumeration value="SEG"/>
 *     &lt;enumeration value="DES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorType", namespace = "http://www.tigo.com/Core/Common/V1")
@XmlEnum
public enum ErrorType {

    COM,
    MSJ,
    NEG,
    TEC,
    SEG,
    DES;

    public String value() {
        return name();
    }

    public static ErrorType fromValue(String v) {
        return valueOf(v);
    }

}
