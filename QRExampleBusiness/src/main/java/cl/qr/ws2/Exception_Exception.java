//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.qr.ws2;

import javax.xml.ws.WebFault;

@WebFault(name = "Exception", targetNamespace = "http://ws.botonpago.hdp.metlife.cl/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private cl.qr.ws2.Exception faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, cl.qr.ws2.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public Exception_Exception(String message, cl.qr.ws2.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: cl.qr.ws2.Exception
     */
    public cl.qr.ws2.Exception getFaultInfo() {
        return faultInfo;
    }

}
