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
 * <p>Java class for paymentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paymentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pago" type="{http://daws.botonpago.hdp.metlife.cl/}botonPago" minOccurs="0"/>
 *         &lt;element name="convenios" type="{http://daws.botonpago.hdp.metlife.cl/}botonConvenio" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentInfo", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "pago",
    "convenios"
})
public class PaymentInfo {

    protected BotonPago pago;
    @XmlElement(nillable = true)
    protected List<BotonConvenio> convenios;

    /**
     * Gets the value of the pago property.
     * 
     * @return
     *     possible object is
     *     {@link BotonPago }
     *     
     */
    public BotonPago getPago() {
        return pago;
    }

    /**
     * Sets the value of the pago property.
     * 
     * @param value
     *     allowed object is
     *     {@link BotonPago }
     *     
     */
    public void setPago(BotonPago value) {
        this.pago = value;
    }

    /**
     * Gets the value of the convenios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the convenios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConvenios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BotonConvenio }
     * 
     * 
     */
    public List<BotonConvenio> getConvenios() {
        if (convenios == null) {
            convenios = new ArrayList<BotonConvenio>();
        }
        return this.convenios;
    }

}
