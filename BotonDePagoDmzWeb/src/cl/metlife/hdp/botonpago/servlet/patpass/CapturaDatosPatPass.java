package cl.metlife.hdp.botonpago.servlet.patpass;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS_Service;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitpatpass.*;
import cl.metlife.hdp.botonpago.kitsantander.XML;
import cl.metlife.hdp.botonpago.kitsantander.XMLDetalle;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 28-07-2014.
 */
public class CapturaDatosPatPass extends HttpServlet {

    PaymentSupport util = new PaymentSupport();

    /*public WSWebpayService getPatPassWs(String wsdlUrl) {
        String wsdlLocation = wsdlUrl;
        URL baseUrl;
        baseUrl = WSWebpayServiceImplService.class.getResource(".");

        WSWebpayServiceImplService locator = null;
        URL url = null;

        try {
            url = new URL(baseUrl, wsdlLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        locator = new WSWebpayServiceImplService(url);

        return locator.getWSWebpayServiceImplPort();
    }*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("tid"));
        PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");

        String nombreCompleto = payment.getPago().getClienteBean().getNombreCliente().trim();
        String rut = payment.getPago().getClienteBean().getRutCliente();
        String[] partesNombre = nombreCompleto.split(" ");

        String nombre = "", apellidoP = "", apellidoM = "";

        if(partesNombre.length == 4) {
            nombre = partesNombre[0] + " " + partesNombre[1];
            apellidoP = partesNombre[2];
            apellidoM = partesNombre[3];
        }
        else if(partesNombre.length == 3) {
            nombre = partesNombre[0];
            apellidoP = partesNombre[1];
            apellidoM = partesNombre[2];
        }
        else if(partesNombre.length == 2) {
            nombre = partesNombre[0];
            apellidoP = partesNombre[1];
        }
        else {
            nombre = partesNombre[0];
        }

        String simboloDivisa="$";
        String codMon = payment.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda();
        if (codMon.compareToIgnoreCase("UF") == 0) {
            simboloDivisa = "UF ";
        }

        request.getSession().setAttribute("payment", payment);
        request.getSession().setAttribute("nombre", nombre);
        request.getSession().setAttribute("apellidoP", apellidoP);
        request.getSession().setAttribute("apellidoM", apellidoM);
        request.getSession().setAttribute("rut", rut);

        request.getRequestDispatcher("/PatPass/CapturaDatos.jsp").forward(request, response);



            /*
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
        }*/

    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Error, Acceso no autorizado");
    }

}
