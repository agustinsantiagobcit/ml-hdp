//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.conciliatorconnector.caller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "ConectorConciliadorWSSoap", targetNamespace = "http://ws.conciliacion.hdp.metlife.cl/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ConectorConciliadorWSSoap {


    /**
     * 
     * @param informacionPago
     * @return
     *     returns cl.metlife.hdp.conciliacion.ws.conciliatorconnector.caller.ResultadoConciliacion
     */
    @WebMethod(operationName = "ConciliarPago", action = "ConciliarPago")
    @WebResult(name = "Resultado", targetNamespace = "")
    @RequestWrapper(localName = "ConciliarPago", targetNamespace = "http://ws.conciliacion.hdp.metlife.cl/", className = "cl.metlife.hdp.conciliacion.ws.conciliatorconnector.caller.ConciliarPago")
    @ResponseWrapper(localName = "ConciliarPagoResponse", targetNamespace = "http://ws.conciliacion.hdp.metlife.cl/", className = "cl.metlife.hdp.conciliacion.ws.conciliatorconnector.caller.ConciliarPagoResponse")
    public ResultadoConciliacion conciliarPago(
        @WebParam(name = "InformacionPago", targetNamespace = "http://ws.conciliacion.hdp.metlife.cl/")
        Pago informacionPago);

}
