//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitarPagoV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitarPagoV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformacionPagoV2" type="{http://ws.botonpago.hdp.metlife.cl/}informacionPagoV2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitarPagoV2", namespace = "http://ws.botonpago.hdp.metlife.cl/", propOrder = {
    "informacionPagoV2"
})
public class SolicitarPagoV2 {

    @XmlElement(name = "InformacionPagoV2")
    protected InformacionPagoV2 informacionPagoV2;

    /**
     * Gets the value of the informacionPagoV2 property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionPagoV2 }
     *     
     */
    public InformacionPagoV2 getInformacionPagoV2() {
        return informacionPagoV2;
    }

    /**
     * Sets the value of the informacionPagoV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionPagoV2 }
     *     
     */
    public void setInformacionPagoV2(InformacionPagoV2 value) {
        this.informacionPagoV2 = value;
    }

}