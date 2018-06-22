package cl.qr.business;

import cl.qr.cl.qr.pjp.MetlifeException;
import cl.qr.cl.qr.pjp.PaymentFlowInformation;
import cl.qr.ws2.ObjectFactory;
import cl.qr.ws2.*;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.lang.Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by BluePrints Developer on 24-08-2017.
 */
@Stateless
public class PaymentManager {

    private final static Logger logger = Logger.getLogger(PaymentManager.class.getName());

    private String wsdlUrl;
    private String endpointUrl;

    static private PagoWS_Service service;

    public PaymentFlowInformation initTransaction(String nombre){
        InformacionPagoV4 informacionPago = getInformacionPago("http://www.google.cl/", "Dummy", true, nombre); // True por defecto, ya que es UF.

        InformacionFlujoPagoV2 informacionFlujoPago = null;
        try {
            informacionFlujoPago = getBotonPagoService().solicitarPagoV4(informacionPago);
        } catch (Exception_Exception e) {
            //logger.error("Error al invocar servicio de Boton de Pago: " + e.getMessage() ,e);
            return null;
        } catch(RuntimeException e){
            //logger.error("Error al invocar servicio de Boton de Pago: " + e.getMessage() ,e);
            return null;
        }

        return mapPaymentFlow(informacionFlujoPago);
    }

    private PaymentFlowInformation mapPaymentFlow(InformacionFlujoPagoV2 informacionFlujoPago) {
        PaymentFlowInformation info = new PaymentFlowInformation();

        info.setIdPago(informacionFlujoPago.getIdPago());
        info.setToken(informacionFlujoPago.getToken());
        info.setUrlEntradaFlujoPago(informacionFlujoPago.getUrlEntradaFlujoPago());
        info.setUrlLanding(informacionFlujoPago.getUrlLanding());

        return info;
    }

    public EstadoPago checkPaymentStatus(Long idPago){
        EstadoPago response = null;
        try {
            response = getBotonPagoService().consultarEstadoPago((int) (long) idPago);
        } catch (Exception_Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private InformacionPagoV4 getInformacionPago(String baseUrl, String medioPago, boolean pagoEnUF, String nombre) {
        ObjectFactory objectFactory = new ObjectFactory();

        InformacionPagoV4 informacionPago = objectFactory.createInformacionPagoV4();

        Item boleta = getBoleta(objectFactory, pagoEnUF);
        informacionPago.getItem().add(boleta);

        Cliente2 cliente = makeCliente2(objectFactory, nombre);
        informacionPago.setCliente(cliente);
        informacionPago.setUrlFinPago(baseUrl);

        // No es recurrente por requerimiento. (Iván)
        informacionPago.setPagoRecurrente(false);

        informacionPago.setFormaDePagoForzada(null);

        return informacionPago;
    }

    private Cliente2 makeCliente2(ObjectFactory objectFactory, String nombre) {
        Cliente2 cliente = objectFactory.createCliente2();
        cliente.setEmail("QRTesting@blueprintsit.cl");
        cliente.setNombre(nombre);

        cliente.setRUT("11111111-1");
        return cliente;
    }

    private PagoWS getBotonPagoService() {
        PagoWS_Service service = getService();
        PagoWS pagoWS = service.getPago();

        return pagoWS;
    }

    private PagoWS_Service getService(){
        try {
            service = new PagoWS_Service();
        } catch (Exception e) {
            //logger.error("Error con URL de servicio de boton depago",e);
        }

        return service;
    }

    private Item getBoleta(ObjectFactory objectFactory, boolean pagoEnUF) {
        String configuration = "Walmart";
        String lineaDeNegocio = configuration;

        Item item = new Item();

        item.setDescripcion("Pago dummy");
        item.setLineaDeNegocio("VI");
        item.setInfoAdicional("");
        item.setProducto("123456");         // Falta agregarlo en BOTON_BOLETA
        item.setCertificado("");  // Falta agregarlo en BOTON_BOLETA
        item.setCuota("3");  // Va aquí este valor?
        item.setExtraordinario(false);  // Falta agregarlo en BOTON_BOLETA boolean
        item.setMonto(1990D);
        item.setDivisa("CLP");                  // Falta agregarlo en BOTON_BOLETA
        item.setVencimientoPago(getXmlGregorianCalendar());

        return item;
    }

    private XMLGregorianCalendar getXmlGregorianCalendar() {
        /**
         * Se deja fecha fija en el futuro según indicaciones de transbank.
         */
        GregorianCalendar gregCal = new GregorianCalendar(2099, 1, 1);
        XMLGregorianCalendar xmlGregCal = null;

        try {
            xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    gregCal);
        } catch (DatatypeConfigurationException e) {
            MetlifeException metlifeException = new MetlifeException(
                    "error al preparar fecha de expiracion para enviar a transbank",
                    e);
            throw metlifeException;
        }
        return xmlGregCal;
    }
}
