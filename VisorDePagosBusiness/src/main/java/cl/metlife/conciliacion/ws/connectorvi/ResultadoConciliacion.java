//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.connectorvi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resultadoConciliacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultadoConciliacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CuentaControl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DetalleEstado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NuevoEstado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroFactura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoConciliacion", namespace = "http://schemas.datacontract.org/2004/07/", propOrder = {
    "cuentaControl",
    "detalleEstado",
    "nuevoEstado",
    "numeroFactura"
})
public class ResultadoConciliacion {

    @XmlElementRef(name = "CuentaControl", namespace = "http://schemas.datacontract.org/2004/07/", type = JAXBElement.class)
    protected JAXBElement<String> cuentaControl;
    @XmlElementRef(name = "DetalleEstado", namespace = "http://schemas.datacontract.org/2004/07/", type = JAXBElement.class)
    protected JAXBElement<String> detalleEstado;
    @XmlElementRef(name = "NuevoEstado", namespace = "http://schemas.datacontract.org/2004/07/", type = JAXBElement.class)
    protected JAXBElement<String> nuevoEstado;
    @XmlElementRef(name = "NumeroFactura", namespace = "http://schemas.datacontract.org/2004/07/", type = JAXBElement.class)
    protected JAXBElement<String> numeroFactura;

    /**
     * Gets the value of the cuentaControl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCuentaControl() {
        return cuentaControl;
    }

    /**
     * Sets the value of the cuentaControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCuentaControl(JAXBElement<String> value) {
        this.cuentaControl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the detalleEstado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDetalleEstado() {
        return detalleEstado;
    }

    /**
     * Sets the value of the detalleEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDetalleEstado(JAXBElement<String> value) {
        this.detalleEstado = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the nuevoEstado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuevoEstado() {
        return nuevoEstado;
    }

    /**
     * Sets the value of the nuevoEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuevoEstado(JAXBElement<String> value) {
        this.nuevoEstado = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the numeroFactura property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Sets the value of the numeroFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroFactura(JAXBElement<String> value) {
        this.numeroFactura = ((JAXBElement<String> ) value);
    }

}