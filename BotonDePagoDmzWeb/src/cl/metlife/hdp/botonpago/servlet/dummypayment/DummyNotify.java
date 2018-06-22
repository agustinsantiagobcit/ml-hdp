package cl.metlife.hdp.botonpago.servlet.dummypayment;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Ivan on 28-07-2014.
 */
public class DummyNotify extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DUMMY NOTIFY");
        String idPago, resultadoPago;
        //if(request.getParameter("info1") != null) {
            idPago = request.getParameter("info");
        Map<String, String[]> parmap = request.getParameterMap();
        /*}
        else {
            idPago = request.getParameter("info2");
        }
*/

       // if(request.getParameter("status1") != null) {
            resultadoPago = request.getParameter("status");
        /*}
        else {
            resultadoPago = request.getParameter("status2");
        }*/


          PaymentInfo pi = this.pagoDAWS.getPago(Integer.parseInt(idPago), request.getRemoteAddr());

        if(resultadoPago.compareTo("OK")==0) {
            this.pagoDAWS.markAsPayed(Integer.parseInt(idPago), "pago falso", "pago falso", pi.getPago().getBotonConvenio().getId() ,"pago falso", request.getRemoteAddr());
        }else {
            this.pagoDAWS.markAsRejected(Integer.parseInt(idPago), pi.getPago().getBotonConvenio().getId(), "Pago falso", "Pago falso", request.getRemoteAddr());
        }

        request.getRequestDispatcher("DummyPayed").forward(request,response);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }




}
