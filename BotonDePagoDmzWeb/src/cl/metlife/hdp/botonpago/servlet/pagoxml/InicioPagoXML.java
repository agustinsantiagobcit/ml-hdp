package cl.metlife.hdp.botonpago.servlet.pagoxml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.BotonConvenio;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.genericxml.GenericXML1;
import cl.metlife.hdp.botonpago.kitservipag.NodosXML1Detalle;
import cl.metlife.hdp.botonpago.kitservipag.XML1;
import cl.metlife.hdp.botonpago.kitservipag.Log;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

/**
 * Created by Ivan on 28-07-2014.
 */
public class InicioPagoXML extends HttpServlet {

    PaymentSupport util = new PaymentSupport();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        int idSistema = Integer.parseInt(request.getParameter("idSistema"));
        int idConvenio = Integer.parseInt(request.getParameter("idConvenio"));

        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id) {
            HttpSession userSession = (HttpSession) request.getSession().getServletContext().getAttribute("SESSION" + request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");

            BotonConvenio convenio = util.getConvenio(idConvenio, payment);

            userSession.setAttribute("convenio", idConvenio);

            GenericXML1 xml1 = new GenericXML1();
            String notifyUrl = PaymentSupport.getConfigParam(request, "REMOTE_pagoNotificacionXML") + "?convenio=" + idConvenio + "&id="+id;
            String finishUrl = PaymentSupport.getConfigParam(request, "REMOTE_pagoFinXML") + "?convenio=" + idConvenio + "&id="+id;
            String startUrl = convenio.getUrlInicioPago();
            String postParamName = PaymentSupport.getConfigParam(request, "REMOTE_postParamName_"+idConvenio);

            String xmlString = xml1.GeneraXmlInicioPago(payment, convenio, "", notifyUrl, finishUrl);

            PaymentSupport ps = new PaymentSupport();
            ps.writeFile(xmlString, payment.getPago().getId()+"_xml1", payment.getPago().getBotonConvenio().getLogPath());
            System.out.println("Iniciando pago con " + payment.getPago().getBotonConvenio().getBotonInstitucionesPago().getNombre() +
                    " via XML generico. XML=\n"+xmlString+"\nURL: " + startUrl);

            request.getSession().setAttribute("postToUrl", startUrl);
            request.getSession().setAttribute("postParamName", postParamName);
            request.getSession().setAttribute("postContent", startUrl);

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
        response.getWriter().print("Forbidden");
    }

}
