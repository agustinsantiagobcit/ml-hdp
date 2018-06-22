package cl.metlife.hdp.botonpago.servlet;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 21-04-2015.
 */
public class ReloadConfig extends HttpServlet {

    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {

        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Config reload...");
        PaymentSupport.reloadConfig(request, pagoDAWS);
        response.getWriter().println("DONE");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
