package cl.metlife.hdp.botonpago.servlet;


import cl.metlife.hdp.botonpago.dawsclient.*;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ivan on 14-07-2014.
 */
public class InicioPago extends HttpServlet {

    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {

        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Error, Acceso no autorizado");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tid = (String) request.getParameter("tid");
        String idSistema = (String) request.getParameter("idSistema");


        int id = Integer.parseInt(tid);
        int sistema = Integer.parseInt(idSistema);

        List<BotonConvenio> lp = (List<BotonConvenio>) request.getSession().getAttribute("LinksPago");
        BotonInstitucionesPago institucionPago = null;
        for(BotonConvenio c: lp){
            if(c.getBotonInstitucionesPago().getId() == sistema){
                institucionPago = c.getBotonInstitucionesPago();
                PaymentInfo pi = (PaymentInfo)request.getSession().getAttribute("PaymentInfo");
                BotonConvenio bc = this.pagoDAWS.setAgreementToPayment(id, c.getId(), request.getRemoteAddr()).getBotonConvenio();
                pi.getPago().setBotonConvenio(bc);

                break;
            }
        }


        request.getRequestDispatcher(institucionPago.getServletInicioPago()).forward(request, response);

    }

}
