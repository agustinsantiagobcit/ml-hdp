package cl.metlife.hdp.botonpago.servlet.genericpayment;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import cl.metlife.hdp.botonpago.utils.xml.XmlProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 25-01-2016.
 */
public class GenericFinishedPaymentSupportServlet extends HttpServlet {

    protected PagoDAWS pagoDAWS;
    protected PaymentSupport ps;

    protected XmlProcessor xmlProcessor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
        this.xmlProcessor = new XmlProcessor();
    }

    protected void redirigirEstadoInvalido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
        request.getSession().removeAttribute("PaymentInfo");
        request.getSession().removeAttribute("LinksPago");
        request.getSession().removeAttribute("tid");
        request.getSession().removeAttribute("Boletas");
        return;
    }

    protected void redirigirRechazado(HttpServletRequest request, HttpServletResponse response, PaymentInfo pi) throws ServletException, IOException {
        request.getSession().setAttribute("ordenPago", pi.getPago().getId());
        request.getSession().setAttribute("razonRechazo", pi.getPago().getRazonRechazo());
        request.getRequestDispatcher("PagoRechazado.jsp").forward(request, response);
        request.getSession().removeAttribute("PaymentInfo");
        request.getSession().removeAttribute("LinksPago");
        request.getSession().removeAttribute("tid");
        request.getSession().removeAttribute("Boletas");
    }

    protected void redirigirSinComprobante(HttpServletRequest request, HttpServletResponse response, PaymentInfo pi, String destinationURL) throws IOException {
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
        return;
    }
}
