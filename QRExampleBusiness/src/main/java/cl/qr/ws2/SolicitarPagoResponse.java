//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitarPagoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitarPagoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RespuestaSolicitudPago" type="{http://ws.botonpago.hdp.metlife.cl/}informacionFlujoPago" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitarPagoResponse", namespace = "http://ws.botonpago.hdp.metlife.cl/", propOrder = {
    "respuestaSolicitudPago"
})
public class SolicitarPagoResponse {

    @XmlElement(name = "RespuestaSolicitudPago")
    protected InformacionFlujoPago respuestaSolicitudPago;

    /**
     * Gets the value of the respuestaSolicitudPago property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionFlujoPago }
     *     
     */
    public InformacionFlujoPago getRespuestaSolicitudPago() {
        return respuestaSolicitudPago;
    }

    /**
     * Sets the value of the respuestaSolicitudPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionFlujoPago }
     *     
     */
    public void setRespuestaSolicitudPago(InformacionFlujoPago value) {
        this.respuestaSolicitudPago = value;
    }

}
