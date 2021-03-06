//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addPaymentInfoToInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addPaymentInfoToInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPago" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idBoleta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codTransaccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codAutorizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaContable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montoPagado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="notifyMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPaymentInfoToInvoice", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "idPago",
    "idBoleta",
    "codTransaccion",
    "codAutorizacion",
    "fechaContable",
    "fechaPago",
    "montoPagado",
    "notifyMessage",
    "clientIP"
})
public class AddPaymentInfoToInvoice {

    protected int idPago;
    protected int idBoleta;
    protected String codTransaccion;
    protected String codAutorizacion;
    protected String fechaContable;
    protected String fechaPago;
    protected int montoPagado;
    protected String notifyMessage;
    protected String clientIP;

    /**
     * Gets the value of the idPago property.
     * 
     */
    public int getIdPago() {
        return idPago;
    }

    /**
     * Sets the value of the idPago property.
     * 
     */
    public void setIdPago(int value) {
        this.idPago = value;
    }

    /**
     * Gets the value of the idBoleta property.
     * 
     */
    public int getIdBoleta() {
        return idBoleta;
    }

    /**
     * Sets the value of the idBoleta property.
     * 
     */
    public void setIdBoleta(int value) {
        this.idBoleta = value;
    }

    /**
     * Gets the value of the codTransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTransaccion() {
        return codTransaccion;
    }

    /**
     * Sets the value of the codTransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTransaccion(String value) {
        this.codTransaccion = value;
    }

    /**
     * Gets the value of the codAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    /**
     * Sets the value of the codAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAutorizacion(String value) {
        this.codAutorizacion = value;
    }

    /**
     * Gets the value of the fechaContable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaContable() {
        return fechaContable;
    }

    /**
     * Sets the value of the fechaContable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaContable(String value) {
        this.fechaContable = value;
    }

    /**
     * Gets the value of the fechaPago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaPago() {
        return fechaPago;
    }

    /**
     * Sets the value of the fechaPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaPago(String value) {
        this.fechaPago = value;
    }

    /**
     * Gets the value of the montoPagado property.
     * 
     */
    public int getMontoPagado() {
        return montoPagado;
    }

    /**
     * Sets the value of the montoPagado property.
     * 
     */
    public void setMontoPagado(int value) {
        this.montoPagado = value;
    }

    /**
     * Gets the value of the notifyMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifyMessage() {
        return notifyMessage;
    }

    /**
     * Sets the value of the notifyMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifyMessage(String value) {
        this.notifyMessage = value;
    }

    /**
     * Gets the value of the clientIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientIP() {
        return clientIP;
    }

    /**
     * Sets the value of the clientIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientIP(String value) {
        this.clientIP = value;
    }

}
