package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.ws.connectormut.ConectorConciliadorWSSoap;
import cl.metlife.hdp.dao.ConfigDAO;
import cl.metlife.hdp.dao.ConfiguracionesAppDAO;
import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Stateless
public class MutuariaConciliatorServiceManager {

    private final String WSDL_CONFIG_KEY = "mutuaria.service.wsdl";
    private final String ENDPOINT_CONFIG_KEY = "mutuaria.service.endpoint";

    @EJB
    ConfigDAO configDAO;

    private ConectorConciliadorWSSoap service;

    public ConectorConciliadorWSSoap getService(){
        if (service == null) {
            String wsdl_url = configDAO.getConfiguration(WSDL_CONFIG_KEY);
            String endpoint_url = configDAO.getConfiguration(ENDPOINT_CONFIG_KEY);

            ConectorConciliadorWSSoap service = null;
            try {
                service = new cl.metlife.conciliacion.ws.connectormut.ConciliadorWsService(new URL(wsdl_url), new QName("http://servicio.ws.metlife/", "ConciliadorWsService")).getConciliadorWsPort();
            } catch (MalformedURLException e) {
                throw new RuntimeException("Error con URL", e);
            }

            setEndpointAddress(service, endpoint_url);
            this.service = service;
        }

        return service;
    }


    private void setEndpointAddress(Object port, String newAddress) {
        BindingProvider bp = (BindingProvider) port;
        Map<String, Object> context = bp.getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newAddress);
    }

}