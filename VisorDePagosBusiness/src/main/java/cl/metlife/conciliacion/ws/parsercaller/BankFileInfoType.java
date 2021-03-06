//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.parsercaller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for bankFileInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankFileInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgreementNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RUT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PaymentsCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankFileInfoType", namespace = "http://parsepago.ws.conciliacion.hdp.metlife.cl/", propOrder = {
    "agreementNumber",
    "rut",
    "date",
    "paymentsCount"
})
public class BankFileInfoType {

    @XmlElement(name = "AgreementNumber")
    protected int agreementNumber;
    @XmlElement(name = "RUT", required = true)
    protected String rut;
    @XmlElement(name = "Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "PaymentsCount")
    protected int paymentsCount;

    /**
     * Gets the value of the agreementNumber property.
     * 
     */
    public int getAgreementNumber() {
        return agreementNumber;
    }

    /**
     * Sets the value of the agreementNumber property.
     * 
     */
    public void setAgreementNumber(int value) {
        this.agreementNumber = value;
    }

    /**
     * Gets the value of the rut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRUT() {
        return rut;
    }

    /**
     * Sets the value of the rut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRUT(String value) {
        this.rut = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the paymentsCount property.
     * 
     */
    public int getPaymentsCount() {
        return paymentsCount;
    }

    /**
     * Sets the value of the paymentsCount property.
     * 
     */
    public void setPaymentsCount(int value) {
        this.paymentsCount = value;
    }

}
