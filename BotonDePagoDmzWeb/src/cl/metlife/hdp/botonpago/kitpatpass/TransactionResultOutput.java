
package cl.metlife.hdp.botonpago.kitpatpass;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para transactionResultOutput complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="transactionResultOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accountingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="buyOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cardDetail" type="{http://service.wswebpay.webpay.transbank.com/}cardDetail" minOccurs="0"/&gt;
 *         &lt;element name="detailOutput" type="{http://service.wswebpay.webpay.transbank.com/}wsTransactionDetailOutput" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="transactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="urlRedirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactionResultOutput", propOrder = {
    "accountingDate",
    "buyOrder",
    "cardDetail",
    "detailOutput",
    "sessionId",
    "transactionDate",
    "urlRedirection",
    "vci"
})
public class TransactionResultOutput {

    protected String accountingDate;
    protected String buyOrder;
    protected CardDetail cardDetail;
    @XmlElement(nillable = true)
    protected List<WsTransactionDetailOutput> detailOutput;
    protected String sessionId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactionDate;
    protected String urlRedirection;
    @XmlElement(name = "VCI")
    protected String vci;

    /**
     * Obtiene el valor de la propiedad accountingDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingDate() {
        return accountingDate;
    }

    /**
     * Define el valor de la propiedad accountingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingDate(String value) {
        this.accountingDate = value;
    }

    /**
     * Obtiene el valor de la propiedad buyOrder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyOrder() {
        return buyOrder;
    }

    /**
     * Define el valor de la propiedad buyOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyOrder(String value) {
        this.buyOrder = value;
    }

    /**
     * Obtiene el valor de la propiedad cardDetail.
     * 
     * @return
     *     possible object is
     *     {@link CardDetail }
     *     
     */
    public CardDetail getCardDetail() {
        return cardDetail;
    }

    /**
     * Define el valor de la propiedad cardDetail.
     * 
     * @param value
     *     allowed object is
     *     {@link CardDetail }
     *     
     */
    public void setCardDetail(CardDetail value) {
        this.cardDetail = value;
    }

    /**
     * Gets the value of the detailOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsTransactionDetailOutput }
     * 
     * 
     */
    public List<WsTransactionDetailOutput> getDetailOutput() {
        if (detailOutput == null) {
            detailOutput = new ArrayList<WsTransactionDetailOutput>();
        }
        return this.detailOutput;
    }

    /**
     * Obtiene el valor de la propiedad sessionId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Define el valor de la propiedad sessionId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Obtiene el valor de la propiedad transactionDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Define el valor de la propiedad transactionDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Obtiene el valor de la propiedad urlRedirection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRedirection() {
        return urlRedirection;
    }

    /**
     * Define el valor de la propiedad urlRedirection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRedirection(String value) {
        this.urlRedirection = value;
    }

    /**
     * Obtiene el valor de la propiedad vci.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCI() {
        return vci;
    }

    /**
     * Define el valor de la propiedad vci.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCI(String value) {
        this.vci = value;
    }

}
