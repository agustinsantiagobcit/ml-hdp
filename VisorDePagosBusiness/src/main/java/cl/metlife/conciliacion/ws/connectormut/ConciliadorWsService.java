//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.connectormut;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "ConciliadorWsService", targetNamespace = "http://servicio.ws.metlife/", wsdlLocation = "http://mlclwmutprd01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl")
//@WebServiceClient(name = "ConciliadorWsService", targetNamespace = "http://servicio.ws.metlife/", wsdlLocation = "http://mlclwmutqat01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl")
public class ConciliadorWsService
    extends Service
{

    private final static URL CONCILIADORWSSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(cl.metlife.conciliacion.ws.connectormut.ConciliadorWsService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = cl.metlife.conciliacion.ws.connectormut.ConciliadorWsService.class.getResource(".");
            url = new URL(baseUrl, "http://mlclwmutprd01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl");
            //url = new URL(baseUrl, "http://mlclwmutqat01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://mlclwmutprd01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl', retrying as a local file");
            //logger.warning("Failed to create URL for the wsdl Location: 'http://mlclwmutqat01.alico.corp:9080/recaudacion/WsRecaudacion/ConciliadorWsService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CONCILIADORWSSERVICE_WSDL_LOCATION = url;
    }

    public ConciliadorWsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConciliadorWsService() {
        super(CONCILIADORWSSERVICE_WSDL_LOCATION, new QName("http://servicio.ws.metlife/", "ConciliadorWsService"));
    }

    /**
     * 
     * @return
     *     returns ConectorConciliadorWSSoap
     */
    @WebEndpoint(name = "ConciliadorWsPort")
    public ConectorConciliadorWSSoap getConciliadorWsPort() {
        return super.getPort(new QName("http://servicio.ws.metlife/", "ConciliadorWsPort"), ConectorConciliadorWSSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConectorConciliadorWSSoap
     */
    @WebEndpoint(name = "ConciliadorWsPort")
    public ConectorConciliadorWSSoap getConciliadorWsPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://servicio.ws.metlife/", "ConciliadorWsPort"), ConectorConciliadorWSSoap.class, features);
    }

}
