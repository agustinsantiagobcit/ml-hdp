package cl.metlife.hdp.botonpago.servlet.patpass;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericFinishedPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Ivan on 28-07-2014.
 */
public class Pagado  extends GenericFinishedPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PatPass pagado Post");
        this.process(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PatPass Pagado GET");
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("ordenPago", "");
        request.getSession().setAttribute("razonRechazo", "");
        String tid = request.getParameter("p");
        System.out.println("Finalizado PatPass ID " + tid);

        int id = Integer.parseInt(tid);

        PaymentInfo pi = pagoDAWS.getPago(id, request.getRemoteAddr());

        if(pi.getPago().getBotonEstado().getId()!=2) {
            //suscripcion no se ha completado
            if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {

                if (pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                    redirigirRechazado(request, response, pi);
                    return;
                } else {
                    redirigirSinComprobante(request, response, pi, pi.getPago().getUrlFinPago());
                    return;
                }
            }
        }

        String simboloDivisa="$";
        String codMon = pi.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda();
        if (codMon.compareToIgnoreCase("UF") == 0) {
            simboloDivisa = "UF ";
        }

        request.getSession().setAttribute("SimboloDivisa", simboloDivisa);

        request.getSession().setAttribute("PaymentInfo", pi);
        request.getSession().setAttribute("tid", id);
        request.getSession().setAttribute("Boletas", pi.getPago().getBotonBoletas());
        request.getSession().setAttribute("PaymentSupport", new PaymentSupport());
        request.getSession().setAttribute("montoTotal", pi.getPago().getMontoTotal());
        request.getSession().setAttribute("fechaPago", pi.getPago().getBotonBoletas().get(0).getFechaTransaccion());
        request.getSession().setAttribute("urlVuelta", pi.getPago().getUrlFinPago());




        if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
            request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
        }
        else {
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
        }



    }

}
