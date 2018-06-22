package cl.metlife.hdp.botonpago.servlet;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 11-08-2014.
 */
public class PagoFinalizado extends HttpServlet {

    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {
        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaymentInfo pi = (PaymentInfo)request.getSession().getAttribute("PaymentInfo");
        String tid = request.getParameter("tid");

        Integer id = 0;

        try {
            id = Integer.parseInt(tid);
        }
        catch (Exception e) {
            response.getWriter().println("<!DOCTYPE html>");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>Boton de Pago</title>");
            response.getWriter().println("</head>");
            response.getWriter().println(" <body>");
            response.getWriter().println("<h1>Se ha producido un error. La información del pago solicitado no existe.</h1>");
            response.getWriter().println(" </body>");

            response.getWriter().println("</html>");
        }

        if(pi==null || pi.getPago().getId() != id) {
            pi = pagoDAWS.getPago(id, request.getRemoteAddr());
        }

        String destinationURL = pi.getPago().getUrlFinPago();

        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Boton de Pago</title>");
        response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" + destinationURL + "\" />");
        response.getWriter().println("</head>");
        response.getWriter().println(" <body>");
        response.getWriter().println("Espere por favor... redirigiendo");
        response.getWriter().println("<!--estado="+pi.getPago().getBotonEstado().getNombre()+"-->");
        response.getWriter().println(" </body>");

        response.getWriter().println("</html>");

        request.getSession().removeAttribute("PaymentInfo");
        request.getSession().removeAttribute("LinksPago");
        request.getSession().removeAttribute("tid");
        request.getSession().removeAttribute("Boletas");

    }
}
