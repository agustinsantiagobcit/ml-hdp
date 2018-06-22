
package cl.metlife.hdp.botonpago.kitpatpass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para wpmDetailInput complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="wpmDetailInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderLastName1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderLastName2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardHolderMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cellPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="commerceMail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ufFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wpmDetailInput", propOrder = {
    "serviceId",
    "cardHolderId",
    "cardHolderName",
    "cardHolderLastName1",
    "cardHolderLastName2",
    "cardHolderMail",
    "cellPhoneNumber",
    "expirationDate",
    "commerceMail",
    "ufFlag"
})
public class WpmDetailInput {

    @XmlElement(required = true)
    protected String serviceId;
    @XmlElement(required = true)
    protected String cardHolderId;
    @XmlElement(required = true)
    protected String cardHolderName;
    @XmlElement(required = true)
    protected String cardHolderLastName1;
    @XmlElement(required = true)
    protected String cardHolderLastName2;
    @XmlElement(required = true)
    protected String cardHolderMail;
    @XmlElement(required = true)
    protected String cellPhoneNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(required = true)
    protected String commerceMail;
    protected boolean ufFlag;

    /**
     * Obtiene el valor de la propiedad serviceId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Define el valor de la propiedad serviceId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    /**
     * Obtiene el valor de la propiedad cardHolderId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderId() {
        return cardHolderId;
    }

    /**
     * Define el valor de la propiedad cardHolderId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderId(String value) {
        this.cardHolderId = value;
    }

    /**
     * Obtiene el valor de la propiedad cardHolderName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Define el valor de la propiedad cardHolderName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderName(String value) {
        this.cardHolderName = value;
    }

    /**
     * Obtiene el valor de la propiedad cardHolderLastName1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderLastName1() {
        return cardHolderLastName1;
    }

    /**
     * Define el valor de la propiedad cardHolderLastName1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderLastName1(String value) {
        this.cardHolderLastName1 = value;
    }

    /**
     * Obtiene el valor de la propiedad cardHolderLastName2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderLastName2() {
        return cardHolderLastName2;
    }

    /**
     * Define el valor de la propiedad cardHolderLastName2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderLastName2(String value) {
        this.cardHolderLastName2 = value;
    }

    /**
     * Obtiene el valor de la propiedad cardHolderMail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolderMail() {
        return cardHolderMail;
    }

    /**
     * Define el valor de la propiedad cardHolderMail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolderMail(String value) {
        this.cardHolderMail = value;
    }

    /**
     * Obtiene el valor de la propiedad cellPhoneNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    /**
     * Define el valor de la propiedad cellPhoneNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellPhoneNumber(String value) {
        this.cellPhoneNumber = value;
    }

    /**
     * Obtiene el valor de la propiedad expirationDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Define el valor de la propiedad expirationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Obtiene el valor de la propiedad commerceMail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommerceMail() {
        return commerceMail;
    }

    /**
     * Define el valor de la propiedad commerceMail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommerceMail(String value) {
        this.commerceMail = value;
    }

    /**
     * Obtiene el valor de la propiedad ufFlag.
     * 
     */
    public boolean isUfFlag() {
        return ufFlag;
    }

    /**
     * Define el valor de la propiedad ufFlag.
     * 
     */
    public void setUfFlag(boolean value) {
        this.ufFlag = value;
    }

}
