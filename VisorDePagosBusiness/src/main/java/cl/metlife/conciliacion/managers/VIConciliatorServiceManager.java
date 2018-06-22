package cl.metlife.conciliacion.managers;


import cl.metlife.conciliacion.ws.connectorvi.IService;
import cl.metlife.hdp.dao.ConfigDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Stateless
public class VIConciliatorServiceManager {

    private final String WSDL_CONFIG_KEY = "vi.service.wsdl";
    private final String ENDPOINT_CONFIG_KEY = "vi.service.endpoint";

    @EJB
    ConfigDAO configDAO;

    private IService service;

    public IService getService(){
        if (service == null) {
            String wsdl_url = configDAO.getConfiguration(WSDL_CONFIG_KEY);
            String endpoint_url = configDAO.getConfiguration(ENDPOINT_CONFIG_KEY);

            IService service = null;
            try {
                service = new cl.metlife.conciliacion.ws.connectorvi.Service(new URL(wsdl_url), new QName("http://tempuri.org/", "Service")).getBasicHttpBindingIService();
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