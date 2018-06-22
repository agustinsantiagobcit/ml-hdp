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
 * <p>Java class for codigosMoneda complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="codigosMoneda">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="botonBoletas" type="{http://daws.botonpago.hdp.metlife.cl/}botonBoleta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="codigoMoneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idMoneda" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreMoneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pagos" type="{http://daws.botonpago.hdp.metlife.cl/}pago" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codigosMoneda", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "botonBoletas",
    "codigoMoneda",
    "idMoneda",
    "nombreMoneda",
    "pagos"
})
public class CodigosMoneda {

    @XmlElement(nillable = true)
    protected List<BotonBoleta> botonBoletas;
    protected String codigoMoneda;
    protected int idMoneda;
    protected String nombreMoneda;
    @XmlElement(nillable = true)
    protected List<Pago> pagos;

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
     * Gets the value of the codigoMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    /**
     * Sets the value of the codigoMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoMoneda(String value) {
        this.codigoMoneda = value;
    }

    /**
     * Gets the value of the idMoneda property.
     * 
     */
    public int getIdMoneda() {
        return idMoneda;
    }

    /**
     * Sets the value of the idMoneda property.
     * 
     */
    public void setIdMoneda(int value) {
        this.idMoneda = value;
    }

    /**
     * Gets the value of the nombreMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreMoneda() {
        return nombreMoneda;
    }

    /**
     * Sets the value of the nombreMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreMoneda(String value) {
        this.nombreMoneda = value;
    }

    /**
     * Gets the value of the pagos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pago }
     * 
     * 
     */
    public List<Pago> getPagos() {
        if (pagos == null) {
            pagos = new ArrayList<Pago>();
        }
        return this.pagos;
    }

}
