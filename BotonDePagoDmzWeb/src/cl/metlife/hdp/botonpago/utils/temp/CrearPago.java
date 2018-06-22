package cl.metlife.hdp.botonpago.utils.temp;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 09-09-2014.
 */
public class CrearPago extends HttpServlet {

    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {
        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String m1 = request.getParameter("b1Monto");
        String m2 = request.getParameter("b2Monto");
        String m3 = request.getParameter("b3Monto");
        String m4 = request.getParameter("b4Monto");
        String m5 = request.getParameter("b5Monto");
        String m6 = request.getParameter("b6Monto");

        String id1 = request.getParameter("b1idBoleta");
        String id2 = request.getParameter("b2idBoleta");
        String id3 = request.getParameter("b3idBoleta");
        String id4 = request.getParameter("b4idBoleta");
        String id5 = request.getParameter("b5idBoleta");
        String id6 = request.getParameter("b6idBoleta");

        String desc1 = request.getParameter("b1Descripcion");
        String desc2 = request.getParameter("b2Descripcion");
        String desc3 = request.getParameter("b3Descripcion");
        String desc4 = request.getParameter("b4Descripcion");
        String desc5 = request.getParameter("b5Descripcion");
        String desc6 = request.getParameter("b6Descripcion");

        String nombre = request.getParameter("nombre");
        String rut = request.getParameter("rut");

        PaymentSupport ps = new PaymentSupport();

        //String url = pagoDAWS.solicitarPago(nombre,rut,m1, m2, m3, m4, m5, m6, id1, id2, id3, id4, id5, id6, desc1, desc2, desc3, desc4, desc5, desc6);

        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Boton de Pago</title>");
        //response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" + url + "\" />");
        response.getWriter().println("</head>");
        response.getWriter().println(" <body>");
        response.getWriter().println("Espere por favor... redirigiendo");
        response.getWriter().println(" </body>");

        response.getWriter().println("</html>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
