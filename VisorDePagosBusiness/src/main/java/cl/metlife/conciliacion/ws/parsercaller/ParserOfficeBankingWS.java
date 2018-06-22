//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.parsercaller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(name = "ParserOfficeBankingWS", targetNamespace = "http://parsepago.ws.conciliacion.hdp.metlife.cl/", wsdlLocation = "http://localhost:9080/ParserAnchoFijo/ParserOfficeBankingWS/ParserOfficeBankingWS.wsdl")
public class ParserOfficeBankingWS
        extends Service
{

    private final static URL PARSEROFFICEBANKINGWS_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(cl.metlife.conciliacion.ws.parsercaller.ParserOfficeBankingWS.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = cl.metlife.conciliacion.ws.parsercaller.ParserOfficeBankingWS.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:9080/ParserAnchoFijo/ParserOfficeBankingWS/ParserOfficeBankingWS.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:9080/ParserAnchoFijo/ParserOfficeBankingWS/ParserOfficeBankingWS.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        PARSEROFFICEBANKINGWS_WSDL_LOCATION = url;
    }

    public ParserOfficeBankingWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ParserOfficeBankingWS() {
        super(PARSEROFFICEBANKINGWS_WSDL_LOCATION, new QName("http://parsepago.ws.conciliacion.hdp.metlife.cl/", "ParserOfficeBankingWS"));
    }

    /**
     *
     * @return
     *     returns ParserPago
     */
    @WebEndpoint(name = "ParserOfficeBanking")
    public ParserPago getParserOfficeBanking() {
        return super.getPort(new QName("http://parsepago.ws.conciliacion.hdp.metlife.cl/", "ParserOfficeBanking"), ParserPago.class);
    }

    @WebEndpoint(name = "ParserOfficeBanking")
    public ParserPago getParserOfficeBanking(QName serviceName) {
        return super.getPort(serviceName, ParserPago.class);
    }

}
