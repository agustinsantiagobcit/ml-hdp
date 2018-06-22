package cl.metlife.hdp.botonpago.servlet;

import cl.metlife.hdp.botonpago.dawsclient.BotonConvenio;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.verificarV;

/**
 * Created by Ivan on 03-12-2015.
 */
public class PagoDirecto extends HttpServlet {
    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {

        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PaymentSupport ps = new PaymentSupport();
            ps.loadConfig(request, this.pagoDAWS);


            String tid = tid = (String) request.getParameter("tid");
            String v = (String) request.getParameter("v");
            String formaPago = (String)  request.getParameter("fp");
            int id = Integer.parseInt(tid);
            PaymentInfo p;

            p = pagoDAWS.getPago(id, request.getRemoteAddr());
            request.getSession().setAttribute("PaymentInfo", p);
            request.getSession().setAttribute("LinksPago", p.getConvenios());
            request.getSession().setAttribute("tid", id);
            request.getSession().setAttribute("Boletas", p.getPago().getBotonBoletas());
            BotonConvenio convenioForzado = null;
            for(BotonConvenio c : p.getConvenios()) {
                if(c.getBotonInstitucionesPago().getNombre().compareTo(formaPago) == 0) {
                    convenioForzado = c;
                }
            }

            String simboloDivisa="$";
            String codMon = p.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda();
            if (codMon.compareToIgnoreCase("UF") == 0) {
                simboloDivisa = "UF ";
            }

            request.getSession().setAttribute("SimboloDivisa", simboloDivisa);

            if(convenioForzado==null) {
                request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
                return;
            }
            request.getSession().setAttribute("ConvenioForzado", convenioForzado);

            if(p.getPago().getBotonEstado().getNombre().equals("CREADO")&& verificarV(v, p.getPago())) {
                request.getRequestDispatcher("PagoDirecto.jsp").forward(request, response);
            }
            else {
                request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("EstadoPagoInvalido.jsp").forward(request,response);
        }


    }
}
