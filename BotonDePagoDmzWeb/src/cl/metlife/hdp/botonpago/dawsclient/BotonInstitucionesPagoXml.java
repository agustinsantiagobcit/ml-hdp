//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for botonInstitucionesPagoXml complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="botonInstitucionesPagoXml">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="botonInstitucionesPago" type="{http://daws.botonpago.hdp.metlife.cl/}botonInstitucionesPago" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="xsltAck" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsltInicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsltNotifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsltRedireccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "botonInstitucionesPagoXml", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "botonInstitucionesPago",
    "id",
    "xsltAck",
    "xsltInicio",
    "xsltNotifica",
    "xsltRedireccion"
})
public class BotonInstitucionesPagoXml {

    protected BotonInstitucionesPago botonInstitucionesPago;
    protected int id;
    protected String xsltAck;
    protected String xsltInicio;
    protected String xsltNotifica;
    protected String xsltRedireccion;

    /**
     * Gets the value of the botonInstitucionesPago property.
     * 
     * @return
     *     possible object is
     *     {@link BotonInstitucionesPago }
     *     
     */
    public BotonInstitucionesPago getBotonInstitucionesPago() {
        return botonInstitucionesPago;
    }

    /**
     * Sets the value of the botonInstitucionesPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link BotonInstitucionesPago }
     *     
     */
    public void setBotonInstitucionesPago(BotonInstitucionesPago value) {
        this.botonInstitucionesPago = value;
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
     * Gets the value of the xsltAck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltAck() {
        return xsltAck;
    }

    /**
     * Sets the value of the xsltAck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltAck(String value) {
        this.xsltAck = value;
    }

    /**
     * Gets the value of the xsltInicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltInicio() {
        return xsltInicio;
    }

    /**
     * Sets the value of the xsltInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltInicio(String value) {
        this.xsltInicio = value;
    }

    /**
     * Gets the value of the xsltNotifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltNotifica() {
        return xsltNotifica;
    }

    /**
     * Sets the value of the xsltNotifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltNotifica(String value) {
        this.xsltNotifica = value;
    }

    /**
     * Gets the value of the xsltRedireccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXsltRedireccion() {
        return xsltRedireccion;
    }

    /**
     * Sets the value of the xsltRedireccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXsltRedireccion(String value) {
        this.xsltRedireccion = value;
    }

}