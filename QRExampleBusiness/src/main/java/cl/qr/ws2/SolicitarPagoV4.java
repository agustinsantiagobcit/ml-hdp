//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitarPagoV4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitarPagoV4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformacionPagoV4" type="{http://ws.botonpago.hdp.metlife.cl/}informacionPagoV4" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitarPagoV4", namespace = "http://ws.botonpago.hdp.metlife.cl/", propOrder = {
    "informacionPagoV4"
})
public class SolicitarPagoV4 {

    @XmlElement(name = "InformacionPagoV4")
    protected InformacionPagoV4 informacionPagoV4;

    /**
     * Gets the value of the informacionPagoV4 property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionPagoV4 }
     *     
     */
    public InformacionPagoV4 getInformacionPagoV4() {
        return informacionPagoV4;
    }

    /**
     * Sets the value of the informacionPagoV4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionPagoV4 }
     *     
     */
    public void setInformacionPagoV4(InformacionPagoV4 value) {
        this.informacionPagoV4 = value;
    }

}
