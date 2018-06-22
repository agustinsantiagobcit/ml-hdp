package cl.metlife.hdp.botonpago.servlet.patpass;

import cl.metlife.hdp.botonpago.kitpatpass.TransbankWrapper;
import cl.metlife.hdp.botonpago.kitpatpass.TransbankWrapperService;

import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Created by Ivan on 29-01-2016.
 */
public class PatPassUtils {

    public TransbankWrapper getPatPassWs(String wsdlUrl) {
        String wsdlLocation = wsdlUrl;//;
        URL baseUrl;
        baseUrl = TransbankWrapperService.class.getResource(".");

        TransbankWrapperService locator = null;
        URL url = null;

        try {
            url = new URL(baseUrl, wsdlLocation);
            String protocol = url.getProtocol();
            String userInfo = url.getUserInfo();
            locator = new TransbankWrapperService(url,new QName("http://transbankwrapper.ws.transbankwrapper.metlife.cl/", "TransbankWrapperService"));
            return locator.getTransbankWrapperPort();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        ////
        /*
        WSWebpayService port = locator.getWSWebpayServiceImplPort();

        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(port);
        org.apache.cxf.endpoint.Endpoint cxfEndpoint = client.getEndpoint();

        Map<String,Object> inProps = new HashMap<String,Object>();
        // how to configure the properties is outlined below;

        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
        cxfEndpoint.getInInterceptors().add(wssIn);

        Map<String,Object> outProps = new HashMap<String,Object>();
        // how to configure the properties is outlined below;

        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        cxfEndpoint.getOutInterceptors().add(wssOut);

        return port;
        */
    }


}
