//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for markAsPayedBotonPagoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="markAsPayedBotonPagoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BotonPago" type="{http://daws.botonpago.hdp.metlife.cl/}botonPago" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "markAsPayedBotonPagoResponse", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "botonPago"
})
public class MarkAsPayedBotonPagoResponse {

    @XmlElement(name = "BotonPago")
    protected BotonPago botonPago;

    /**
     * Gets the value of the botonPago property.
     * 
     * @return
     *     possible object is
     *     {@link BotonPago }
     *     
     */
    public BotonPago getBotonPago() {
        return botonPago;
    }

    /**
     * Sets the value of the botonPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link BotonPago }
     *     
     */
    public void setBotonPago(BotonPago value) {
        this.botonPago = value;
    }

}