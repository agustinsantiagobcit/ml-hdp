
package cl.metlife.hdp.botonpago.kitpatpass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para initTransaction complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="initTransaction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="wsInitTransactionInput" type="{http://service.wswebpay.webpay.transbank.com/}wsInitTransactionInput"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initTransaction", propOrder = {
    "wsInitTransactionInput"
})
public class InitTransaction {

    @XmlElement(required = true)
    protected WsInitTransactionInput wsInitTransactionInput;

    /**
     * Obtiene el valor de la propiedad wsInitTransactionInput.
     * 
     * @return
     *     possible object is
     *     {@link WsInitTransactionInput }
     *     
     */
    public WsInitTransactionInput getWsInitTransactionInput() {
        return wsInitTransactionInput;
    }

    /**
     * Define el valor de la propiedad wsInitTransactionInput.
     * 
     * @param value
     *     allowed object is
     *     {@link WsInitTransactionInput }
     *     
     */
    public void setWsInitTransactionInput(WsInitTransactionInput value) {
        this.wsInitTransactionInput = value;
    }

}
