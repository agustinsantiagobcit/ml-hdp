
package cl.metlife.hdp.botonpago.kitpatpass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para acknowledgeTransaction complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="acknowledgeTransaction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tokenInput" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "acknowledgeTransaction", propOrder = {
    "tokenInput"
})
public class AcknowledgeTransaction {

    @XmlElement(required = true)
    protected String tokenInput;

    /**
     * Obtiene el valor de la propiedad tokenInput.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenInput() {
        return tokenInput;
    }

    /**
     * Define el valor de la propiedad tokenInput.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenInput(String value) {
        this.tokenInput = value;
    }

}
