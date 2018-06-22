package cl.metlife.conciliacion.masterconciliator;

import cl.metlife.conciliacion.domain.ConciliatorConnector;
import cl.metlife.conciliacion.domain.PaymentInstitution;
import cl.metlife.conciliacion.managers.MutuariaConciliatorServiceManager;
import cl.metlife.conciliacion.managers.VIConciliatorServiceManager;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.*;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.ConectorConciliadorWSSoap;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.Pago;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.ResultadoConciliacion;
import cl.metlife.conciliacion.ws.connectormut.*;
import cl.metlife.conciliacion.ws.connectormut.Moneda;
import cl.metlife.conciliacion.ws.connectormut.TipoDocumento;
import cl.metlife.conciliacion.ws.connectorvi.ObjectFactory;
import cl.metlife.conciliacion.ws.connectorvi.SalidaResultadoConciliacion;
import cl.metlife.conciliacion.ws.connectorvi.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 6/26/14.
 */
@Singleton
public class MasterConciliator {
    private static MasterConciliator instance = null;
    private Map<Long, ConectorConciliadorWSSoap> conciliators;

    static final Logger logger = LoggerFactory.getLogger(MasterConciliator.class);

    @EJB
    MutuariaConciliatorServiceManager mutService;

    @EJB
    VIConciliatorServiceManager viService;


    public MasterConciliator() {
        conciliators = new HashMap<Long, ConectorConciliadorWSSoap>();
    }

    public MasterConciliator getInstance() {
        if(instance == null){
            instance = new MasterConciliator();
        }
        return instance;
    }

    private ConectorConciliadorWSSoap getRemoteConciliator(PaymentInstitution paymentInstitution, String businessLines, List<ConciliatorConnector> connectors) throws ConciliatorConnectorException, MalformedURLException {
        ConectorConciliadorWSSoap c = null;

        for (ConciliatorConnector connector : connectors) {
            if(connector.getBusinessLine().getName().equals(businessLines) && connector.getPaymentInstitutionId().equals(paymentInstitution.getId())){
                if(connector.getClient().equals("1")){
                    String urlConciliatorConnector = connector.getWsdl();

                    logger.debug("Se conciliaría con WSDL: " + connector.getWsdl());
                    logger.info("Se conciliaría con WSDL: " + connector.getWsdl());
                    System.out.println("Se conciliaría con WSDL: " + connector.getWsdl());

                    ConectorConciliadorWS locator = new ConectorConciliadorWS(urlConciliatorConnector);

                    c = locator.getConectorConciliadorWSSoap();

                    this.conciliators.put(paymentInstitution.getId(), c);
                } else {
                    return null;
                }
            }
        }

        return c;
    }

    public cl.metlife.conciliacion.ws.connectormut.ConectorConciliadorWSSoap getMutuariaServiceTest(){
        cl.metlife.conciliacion.ws.connectormut.ConectorConciliadorWSSoap ret = mutService.getService();

        return ret;
    }

    public ResultadoConciliacion conciliar(Pago p, PaymentInstitution paymentInstitution, String businessLine, List<ConciliatorConnector> connectors) throws ConciliatorConnectorException, MalformedURLException {
        ResultadoConciliacion rc = null;
        cl.metlife.conciliacion.ws.connectormut.ResultadoConciliacion rc2 = null;

        // Aca se obtiene el conector
        logger.debug("Se conciliaría pago: {agnocuota: " + p.getAgnoCuota()+ ", mescuota: " + p.getMesCuota() + ", numconvenio: " + p.getNumeroConvenio() + ", numdocumento: " + p.getNumeroDocumento() + ", monto: " + p.getMontoPagado() + ", fechapago: " + p.getFechaPago() + "}");
        logger.info("Se conciliaría pago: {agnocuota: " + p.getAgnoCuota()+ ", mescuota: " + p.getMesCuota() + ", numconvenio: " + p.getNumeroConvenio() + ", numdocumento: " + p.getNumeroDocumento() + ", monto: " + p.getMontoPagado() + ", fechapago: " + p.getFechaPago() + "}");
        System.out.println("Se conciliaría pago: {agnocuota: " + p.getAgnoCuota()+ ", mescuota: " + p.getMesCuota() + ", numconvenio: " + p.getNumeroConvenio() + ", numdocumento: " + p.getNumeroDocumento() + ", monto: " + p.getMontoPagado() + ", fechapago: " + p.getFechaPago() + "}");

        ConectorConciliadorWSSoap c = this.getRemoteConciliator(paymentInstitution, businessLine, connectors);

        try {
            if(c == null){
                if(businessLine.equals("Mutuaria")){
                    rc = mapResultMu(mutService.getService().conciliarPago(mapPago(p)));
                } else if(businessLine.equals("VI")){
                    cl.metlife.conciliacion.ws.connectorvi.Pago pago = mapPagoVi(p);

                    cl.metlife.conciliacion.ws.connectorvi.SalidaResultadoConciliacion a = viService.getService().conciliarPago(pago);

                    rc = mapResultVi(a);
                }

            } else {
                rc = c.conciliarPago(p);
            }
        } catch (Exception e){
            return null;
        }

        logger.debug("Respuesta conciliador: " + rc.getNuevoEstado() + ", " + rc.getDetalleEstado() + ", " + rc.getNumeroFactura() + ", " + rc.getCuentaControl() + ".");
        logger.info("Respuesta conciliador: " + rc.getNuevoEstado() + ", " + rc.getDetalleEstado() + ", " + rc.getNumeroFactura() + ", " + rc.getCuentaControl() + ".");

        return rc;
    }

    private ResultadoConciliacion mapResultMu(cl.metlife.conciliacion.ws.connectormut.ResultadoConciliacion resultadoConciliacion) {
        ResultadoConciliacion resultado = new ResultadoConciliacion();

        resultado.setCuentaControl(resultadoConciliacion.getCuentaControl());
        resultado.setDetalleEstado(resultadoConciliacion.getDetalleEstado());
        resultado.setNuevoEstado(resultadoConciliacion.getNuevoEstado());
        resultado.setNumeroFactura(resultadoConciliacion.getNumeroFactura());

        return resultado;
    }

    private ResultadoConciliacion mapResultVi(cl.metlife.conciliacion.ws.connectorvi.SalidaResultadoConciliacion resultadoConciliacion) {
        ResultadoConciliacion resultado = new ResultadoConciliacion();

        resultado.setCuentaControl(resultadoConciliacion.getResultadoConciliacion().getValue().getCuentaControl().getValue());
        resultado.setDetalleEstado(resultadoConciliacion.getResultadoConciliacion().getValue().getDetalleEstado().getValue());
        resultado.setNuevoEstado(resultadoConciliacion.getResultadoConciliacion().getValue().getNuevoEstado().getValue());
        resultado.setNumeroFactura(resultadoConciliacion.getResultadoConciliacion().getValue().getNumeroFactura().getValue());

        return resultado;
    }

    private cl.metlife.conciliacion.ws.connectormut.Pago mapPago(Pago p) {
        cl.metlife.conciliacion.ws.connectormut.Pago pagom = new cl.metlife.conciliacion.ws.connectormut.Pago();

        pagom.setAgnoCuota(p.getAgnoCuota());

        cl.metlife.conciliacion.ws.connectormut.Moneda mon = Moneda.CLP;
        pagom.setCodigoMoneda(mon);
        pagom.setCuentaControl(p.getCuentaControl());
        pagom.setFechaPago(p.getFechaPago());
        pagom.setMesCuota(p.getMesCuota());
        pagom.setMontoPagado(p.getMontoPagado());
        pagom.setNumeroConvenio(p.getNumeroConvenio());
        pagom.setNumeroDocumento(p.getNumeroDocumento());
        pagom.setNumeroProducto(p.getNumeroProducto());

        cl.metlife.conciliacion.ws.connectormut.TipoDocumento tipoDocumento = null;
        if(p.getTipoDocumentoPago().value().equals("Cupon")){
            tipoDocumento = TipoDocumento.CUPON;
        } else if(p.getTipoDocumentoPago().value().equals("OnLine")){
            tipoDocumento = TipoDocumento.ON_LINE;
        } else if(p.getTipoDocumentoPago().value().equals("PAT")){
            tipoDocumento = TipoDocumento.PAT;
        } else if(p.getTipoDocumentoPago().value().equals("PAC")){
            tipoDocumento = TipoDocumento.PAC;
        }

        pagom.setTipoDocumentoPago(tipoDocumento);

        return pagom;
    }

    private cl.metlife.conciliacion.ws.connectorvi.Pago mapPagoVi(Pago p) {
        cl.metlife.conciliacion.ws.connectorvi.Pago pagom = new cl.metlife.conciliacion.ws.connectorvi.Pago();

        cl.metlife.conciliacion.ws.connectorvi.ObjectFactory objFactory = new ObjectFactory();
        pagom.setAgnoCuota(objFactory.createPagoAgnoCuota(p.getAgnoCuota()));

        cl.metlife.conciliacion.ws.connectorvi.Moneda mon = cl.metlife.conciliacion.ws.connectorvi.Moneda.CLP;
        pagom.setCodigoMoneda(mon);

        pagom.setCuentaControl(objFactory.createPagoCuentaControl(p.getCuentaControl()));

        pagom.setFechaPago(p.getFechaPago());

        pagom.setMesCuota(objFactory.createPagoMesCuota(p.getMesCuota()));

        pagom.setMontoPagado(p.getMontoPagado());

        pagom.setNumeroConvenio(objFactory.createPagoNumeroConvenio(p.getNumeroConvenio()));

        pagom.setNumeroDocumento(objFactory.createPagoNumeroDocumento(p.getNumeroDocumento()));

        pagom.setNumeroProducto(objFactory.createPagoNumeroProducto(p.getNumeroProducto()));

        cl.metlife.conciliacion.ws.connectorvi.TipoDocumento tipoDocumento = null;
        if(p.getTipoDocumentoPago().value().equals("Cupon")){
            tipoDocumento = cl.metlife.conciliacion.ws.connectorvi.TipoDocumento.CUPON;
        } else if(p.getTipoDocumentoPago().value().equals("OnLine")){
            tipoDocumento = cl.metlife.conciliacion.ws.connectorvi.TipoDocumento.ON_LINE;
        } else if(p.getTipoDocumentoPago().value().equals("PAT")){
            tipoDocumento = cl.metlife.conciliacion.ws.connectorvi.TipoDocumento.PAT;
        } else if(p.getTipoDocumentoPago().value().equals("PAC")){
            tipoDocumento = cl.metlife.conciliacion.ws.connectorvi.TipoDocumento.PAC;
        }

        pagom.setTipoDocumentoPago(tipoDocumento);

        return pagom;
    }
}
