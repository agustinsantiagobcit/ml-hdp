//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitarPago complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitarPago">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformacionPago" type="{http://ws.botonpago.hdp.metlife.cl/}informacionPago" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitarPago", namespace = "http://ws.botonpago.hdp.metlife.cl/", propOrder = {
    "informacionPago"
})
public class SolicitarPago {

    @XmlElement(name = "InformacionPago")
    protected InformacionPago informacionPago;

    /**
     * Gets the value of the informacionPago property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionPago }
     *     
     */
    public InformacionPago getInformacionPago() {
        return informacionPago;
    }

    /**
     * Sets the value of the informacionPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionPago }
     *     
     */
    public void setInformacionPago(InformacionPago value) {
        this.informacionPago = value;
    }

}