//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitarPagoV2Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitarPagoV2Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RespuestaSolicitudPago" type="{http://ws.botonpago.hdp.metlife.cl/}informacionFlujoPagoV2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitarPagoV2Response", namespace = "http://ws.botonpago.hdp.metlife.cl/", propOrder = {
    "respuestaSolicitudPago"
})
public class SolicitarPagoV2Response {

    @XmlElement(name = "RespuestaSolicitudPago")
    protected InformacionFlujoPagoV2 respuestaSolicitudPago;

    /**
     * Gets the value of the respuestaSolicitudPago property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionFlujoPagoV2 }
     *     
     */
    public InformacionFlujoPagoV2 getRespuestaSolicitudPago() {
        return respuestaSolicitudPago;
    }

    /**
     * Sets the value of the respuestaSolicitudPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionFlujoPagoV2 }
     *     
     */
    public void setRespuestaSolicitudPago(InformacionFlujoPagoV2 value) {
        this.respuestaSolicitudPago = value;
    }

}