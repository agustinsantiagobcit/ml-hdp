//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.conciliatorconnector.caller;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for moneda.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="moneda">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CLP"/>
 *     &lt;enumeration value="USD"/>
 *     &lt;enumeration value="UF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "moneda", namespace = "http://ws.conciliacion.hdp.metlife.cl/")
@XmlEnum
public enum Moneda {

    CLP,
    USD,
    UF;

    public String value() {
        return name();
    }

    public static Moneda fromValue(String v) {
        return valueOf(v);
    }

}
