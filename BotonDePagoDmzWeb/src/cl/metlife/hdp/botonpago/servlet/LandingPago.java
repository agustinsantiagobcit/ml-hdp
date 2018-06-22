package cl.metlife.hdp.botonpago.servlet;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.verificarV;

/**
 * Created by Ivan on 14-07-2014.
 */
public class LandingPago extends HttpServlet {
    PagoDAWS pagoDAWS;


    //final static Logger logger = LoggerFactory.getLogger(LandingPago.class);

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
        try {
            PaymentSupport ps = new PaymentSupport();
            ps.loadConfig(request, this.pagoDAWS);


            String tid = tid = (String) request.getParameter("tid");
            String v = (String) request.getParameter("v");
            int id = Integer.parseInt(tid);
            PaymentInfo p;

            p = pagoDAWS.getPago(id, request.getRemoteAddr());
            request.getSession().setAttribute("PaymentInfo", p);
            request.getSession().setAttribute("LinksPago", p.getConvenios());
            request.getSession().setAttribute("tid", id);
            request.getSession().setAttribute("Boletas", p.getPago().getBotonBoletas());
            String simboloDivisa="$";
            String codMon = p.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda();
            if (codMon.compareToIgnoreCase("UF") == 0) {
                simboloDivisa = "UF ";
            }

            request.getSession().setAttribute("SimboloDivisa", simboloDivisa);

            if(p.getPago().getMontoTotal()==0.0) {
                request.getSession().setAttribute("MostrarMontos", "False");
            }
            else {
                request.getSession().setAttribute("MostrarMontos", "True");
            }


            if(p.getPago().getBotonEstado().getNombre().equals("CREADO")&& verificarV(v, p.getPago())) {
                request.getRequestDispatcher("LandingPago.jsp").forward(request, response);
            }
            else {
                request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
            }
        }
        catch (Exception e) {
            //logger.error("error en landing pago patpass",e);
            request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
        }


    }


}
