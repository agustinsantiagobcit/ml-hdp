//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for botonLineasDeNegocio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="botonLineasDeNegocio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="botonBoletas" type="{http://daws.botonpago.hdp.metlife.cl/}botonBoleta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="botonConvenios" type="{http://daws.botonpago.hdp.metlife.cl/}botonConvenio" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mailContacto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "botonLineasDeNegocio", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "botonBoletas",
    "botonConvenios",
    "id",
    "mailContacto",
    "nombre"
})
public class BotonLineasDeNegocio {

    @XmlElement(nillable = true)
    protected List<BotonBoleta> botonBoletas;
    @XmlElement(nillable = true)
    protected List<BotonConvenio> botonConvenios;
    protected int id;
    protected String mailContacto;
    protected String nombre;

    /**
     * Gets the value of the botonBoletas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the botonBoletas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBotonBoletas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BotonBoleta }
     * 
     * 
     */
    public List<BotonBoleta> getBotonBoletas() {
        if (botonBoletas == null) {
            botonBoletas = new ArrayList<BotonBoleta>();
        }
        return this.botonBoletas;
    }

    /**
     * Gets the value of the botonConvenios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the botonConvenios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBotonConvenios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BotonConvenio }
     * 
     * 
     */
    public List<BotonConvenio> getBotonConvenios() {
        if (botonConvenios == null) {
            botonConvenios = new ArrayList<BotonConvenio>();
        }
        return this.botonConvenios;
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
     * Gets the value of the mailContacto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailContacto() {
        return mailContacto;
    }

    /**
     * Sets the value of the mailContacto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailContacto(String value) {
        this.mailContacto = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

}
