package cl.metlife.hdp.botonpago.servlet.santander;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitsantander.XML;
import cl.metlife.hdp.botonpago.kitsantander.XMLDetalle;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 28-07-2014.
 */
public class InicioPagoSantander extends HttpServlet {

    PaymentSupport util = new PaymentSupport();



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("Santander", payment));

            XML xmlSantander = null;
            try {
                xmlSantander = buildPaymentParameters(payment);
            } catch (PaymentException e) {
                throw new ServletException(e.getCause());
            }
            //Agregar ruta del exe de santander
            String remoteUrl = PaymentSupport.getConfigParam(request, "REMOTE_santanderInicioURL_" + payment.getPago().getBotonLineasDeNegocio().getId());
            String requestXML = xmlSantander.generarXML();

            PaymentSupport ps = new PaymentSupport();
            ps.writeFile(requestXML,payment.getPago().getId()+"_xml1", payment.getPago().getBotonConvenio().getLogPath());
            System.out.println("Iniciando pago con Santander. XML=\n"+requestXML+"\nURL: " + remoteUrl);

            request.getSession().setAttribute("postToUrl", remoteUrl);
            request.getSession().setAttribute("postParamName", "TX"); //Era data, se solicita cambio a TX
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

    private XML buildPaymentParameters(PaymentInfo payment) throws PaymentException {

        List<BotonBoleta> boletas = payment.getPago().getBotonBoletas();
        int counter = 0;
        if(boletas.size()>6){
            throw new PaymentException("Solo se puede pagar hasta 6 boletas, se recibieron "+ boletas.size());
        }

        XML xmlSantander = new XML();
        List<XMLDetalle> detalle = new ArrayList<XMLDetalle>();

        xmlSantander.setIdComercio(payment.getPago().getBotonConvenio().getNumeroConvenio());
        xmlSantander.setIDTRX(payment.getPago().getBotonConvenio().getNumeroConvenio() + String.format("%06d", payment.getPago().getId()));
        xmlSantander.setTotal(payment.getPago().getMontoTotal()+"");
        xmlSantander.setNroPagos(boletas.size()+"");
        for(BotonBoleta b : boletas){
            XMLDetalle xmlDetalle = new XMLDetalle();
            xmlDetalle.SRVREC = payment.getPago().getBotonConvenio().getLlave();//"9023";
            xmlDetalle.monto = b.getMonto()+"";
            xmlDetalle.glosa = b.getDescripcion();
            xmlDetalle.cantidad = "1";
            xmlDetalle.precioUnitario = b.getMonto()+"";
            xmlDetalle.datosAdicionales = b.getNumeroBoleta();//payment.getPago().getId()+";"+b.getId(); //se guarda el id del pago, id de la boleta
            detalle.add(xmlDetalle);
        }
        xmlSantander.setDetalle(detalle);
        return xmlSantander;
    }
}
