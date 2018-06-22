package cl.metlife.hdp.botonpago.servlet.estado;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitbestado.XMLMultiPago;
import cl.metlife.hdp.botonpago.kitbestado.XMLMultiPagoDetalle;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ivan on 28-11-2015.
 */
public class InicioPago extends HttpServlet {

    PaymentSupport util = new PaymentSupport();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("Estado", payment));

            XMLMultiPago xmlContent = null;
            try {
                xmlContent = buildPaymentParameters(payment, request);
            } catch (PaymentException e) {
                throw new ServletException(e.getCause());
            }


            //String remoteUrl = payment.getPago().getBotonConvenio().getUrlInicioPago();// PaymentSupport.getConfigParam(request, "REMOTE_BancoEstado_Start" + payment.getPago().getBotonLineasDeNegocio().getId());
            String remoteUrl = PaymentSupport.getConfigParam(request, "REMOTE_BancoEstado_Start");

            String requestXML = xmlContent.generarXML();

            PaymentSupport ps = new PaymentSupport();
            ps.writeFile(requestXML,payment.getPago().getId()+"_xml1", payment.getPago().getBotonConvenio().getLogPath());
            System.out.println("Iniciando pago con Banco Estado. XML=\n"+requestXML+"\nURL: " + remoteUrl);

            request.getSession().setAttribute("postToUrl", remoteUrl);
            request.getSession().setAttribute("postParamName", "ent");
            request.getSession().setAttribute("postContent", requestXML);

            String target = "_self";
            if( payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True") ){
                target = "_blank";
            }

            request.getSession().setAttribute("PaymentTarget", target);


            request.getRequestDispatcher("/generic/poster.jsp").forward(request, response);

        }
        else {
            response.getWriter().print("ID no coincide");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Error, Acceso no autorizado");
    }

    private XMLMultiPago buildPaymentParameters(PaymentInfo payment, HttpServletRequest request) throws PaymentException {

        List<BotonBoleta> boletas = payment.getPago().getBotonBoletas();
        int counter = 0;
        if(boletas.size()>6){
            throw new PaymentException("Solo se puede pagar hasta 6 boletas, se recibieron "+ boletas.size());
        }

        XMLMultiPago xmlEstado = new XMLMultiPago();
        List<XMLMultiPagoDetalle> detalle = new ArrayList<XMLMultiPagoDetalle>();

        xmlEstado.setIDTRX( String.format("%06d", payment.getPago().getId()));
        xmlEstado.setRutEmpresa(payment.getPago().getBotonConvenio().getRutComercio().replace("-", ""));
        xmlEstado.setTotal(payment.getPago().getMontoTotal().intValue()+"");
        xmlEstado.setIdComercio(payment.getPago().getBotonConvenio().getNumeroConvenio());
        xmlEstado.setNroPagos(boletas.size()+"");
        xmlEstado.setMetodoRend("POST");
        xmlEstado.setTipoConf("nomq");
        xmlEstado.setGlosaMultipago("Pago " + payment.getPago().getId());
        xmlEstado.setBanco("0012");
        xmlEstado.setURLFinPago(PaymentSupport.getConfigParam(request, "REMOTE_BancoEstado_End")+"?idTrx="+payment.getPago().getId());
        xmlEstado.setPagComercioRend(PaymentSupport.getConfigParam(request, "REMOTE_BancoEstado_Notify"));
        xmlEstado.setServicio("Pago MetLife");
        String rut = payment.getPago().getClienteBean().getRutCliente();
        xmlEstado.setRUTCliente(rut.replace("-",""));

        /*
            xmlEstado.setRUTCliente();
            xmlEstado.setURLFinPago(REMOTE_BancoEstado_Notify);
            xmlEstado.setPagComercioRed(REMOTE_BancoEstado_End);
        */

        Calendar c = Calendar.getInstance();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hora = new SimpleDateFormat("hhmmss");

        for(BotonBoleta b : boletas){
            XMLMultiPagoDetalle xmlDetalle = new XMLMultiPagoDetalle();
            xmlDetalle.idPago = b.getNumeroBoleta();
            xmlEstado.setIdMPago(b.getNumeroBoleta());
            xmlDetalle.monto = b.getMonto().intValue()+"";
            xmlDetalle.glosa = util.removeAccents( b.getDescripcion() );
            xmlDetalle.RUTEmpresa = payment.getPago().getBotonConvenio().getRutComercio().replace("-", "");
            xmlDetalle.numConvenio = payment.getPago().getBotonConvenio().getNumeroConvenio();

            xmlDetalle.fechaTRX = fecha.format(c.getTime());
            xmlDetalle.horaTRX = hora.format(c.getTime());
            xmlDetalle.fechaVen = fecha.format(b.getVencimiento().toGregorianCalendar().getTime());
            detalle.add(xmlDetalle);
        }
        xmlEstado.setDetalleMPago(detalle);
        return xmlEstado;
    }
}
