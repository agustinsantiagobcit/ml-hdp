package cl.metlife.hdp.botonpago.servlet.dummypayment;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Ivan on 28-07-2014.
 */
public class DummyPayed extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaymentSupport ps = new PaymentSupport();
        int tid = Integer.parseInt(request.getParameter("info"));

        System.out.println("DUMMY Cierre (vista de comprobante) de pago con ID " + tid);
        PaymentInfo pi = pagoDAWS.getPago(tid, request.getRemoteAddr());
        String resultadoPago = request.getParameter("status");


        if (resultadoPago.compareTo("OK")==0) {
            //Aceptado


            request.getSession().setAttribute("tid", pi.getPago().getId());
            request.getSession().setAttribute("Boletas", pi.getPago().getBotonBoletas());
            request.getSession().setAttribute("PaymentSupport", new PaymentSupport());
            request.getSession().setAttribute("montoTotal", pi.getPago().getMontoTotal());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            request.getSession().setAttribute("fechaPago", sdf.format(pi.getPago().getHoraCreacion().toGregorianCalendar().getTimeInMillis()));
            request.getSession().setAttribute("urlVuelta", pi.getPago().getUrlFinPago());

            if (pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
                return;
            } else {
                response.getWriter().println("<!DOCTYPE html>");
                response.getWriter().println("<html>");
                response.getWriter().println("<head>");
                response.getWriter().println("<title>Boton de Pago</title>");
                response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" + pi.getPago().getUrlFinPago() + "\" />");
                response.getWriter().println("</head>");
                response.getWriter().println(" <body>");
                response.getWriter().println("Espere por favor... redirigiendo");
                response.getWriter().println("<!--estado=" + pi.getPago().getBotonEstado().getNombre() + "-->");
                response.getWriter().println(" </body>");

                response.getWriter().println("</html>");

                request.getSession().removeAttribute("PaymentInfo");
                request.getSession().removeAttribute("LinksPago");
                request.getSession().removeAttribute("tid");
                request.getSession().removeAttribute("Boletas");
                return;
            }
        } else {
            //rechazado

            request.getSession().setAttribute("ordenPago", tid);
            request.getSession().setAttribute("razonRechazo", "Pago cancelado por usuario");
            request.getSession().setAttribute("urlVuelta", pi.getPago().getUrlFinPago());


            request.getRequestDispatcher("PagoRechazado.jsp").forward(request, response);
        }

    }

}
