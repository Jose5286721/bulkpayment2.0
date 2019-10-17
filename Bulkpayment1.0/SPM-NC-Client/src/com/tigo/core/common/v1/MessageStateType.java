
package com.tigo.core.common.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageStateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageStateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="OK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MessageStateType", namespace = "http://www.tigo.com/Core/Common/V1")
@XmlEnum
public enum MessageStateType {

    ERROR,
    OK;

    public String value() {
        return name();
    }

    public static MessageStateType fromValue(String v) {
        return valueOf(v);
    }

}
