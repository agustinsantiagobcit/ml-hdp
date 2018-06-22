//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for botonAcceso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="botonAcceso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="botonPago" type="{http://daws.botonpago.hdp.metlife.cl/}botonPago" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="info" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipAcceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "botonAcceso", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "accion",
    "botonPago",
    "fecha",
    "id",
    "info",
    "ipAcceso"
})
public class BotonAcceso {

    protected String accion;
    protected BotonPago botonPago;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected int id;
    protected String info;
    protected String ipAcceso;

    /**
     * Gets the value of the accion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Sets the value of the accion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccion(String value) {
        this.accion = value;
    }

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

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the info property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo(String value) {
        this.info = value;
    }

    /**
     * Gets the value of the ipAcceso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAcceso() {
        return ipAcceso;
    }

    /**
     * Sets the value of the ipAcceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAcceso(String value) {
        this.ipAcceso = value;
    }

}