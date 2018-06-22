package cl.metlife.hdp.botonpago.servlet.facilitadorcomercio;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericFinishedPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ivan on 11-08-2014.
 */
public class Pagado extends GenericFinishedPaymentSupportServlet {

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
        String v = request.getParameter("v");
        String res = request.getParameter("res");
        String loads = request.getParameter("loads");


        Integer id = 0;

        System.out.println("Pago finalizado Facilitador Comercio");

        try {
            id = Integer.parseInt(tid);
        }
        catch (Exception e) {
            System.out.println("No se puede parsear el id de pago. Recibido:'" + tid + "';");
            redirigirEstadoInvalido(request, response);
        }

        if(pi==null || pi.getPago().getId() != id) {
            pi = pagoDAWS.getPago(id, request.getRemoteAddr());
        }

        String destinationURL = pi.getPago().getUrlFinPago();
//               finishUrl = finishUrl + "?res=OK&tid=" + id + "&v=" + PaymentSupport.md5("SUSCRITOOK" + id + "TOKEN");

        if(res.compareTo("OK")==0) {
            if(ps.md5("SUSCRITOOK" + id + "TOKEN").equalsIgnoreCase(v)){
                //pagoDAWS.markAsPayedBotonPago(pi.getPago(), Integer.parseInt(ps.getAgreementId("FacilitadorComercio", pi)),"",request.getRemoteAddr());

                    pagoDAWS.addPaymentInfoToInvoice(pi.getPago().getId(),pi.getPago().getBotonBoletas().get(0).getId(), "0000", "0000", "","", 0, "El usuario ha saltado " + loads + " veces por el sitio","0.0.0.0");
                    redirigirSinComprobante(request, response, pi, destinationURL);
                    return;

            }
            else {
                //this.pagoDAWS.markAsRejected(id, pi.getPago().getBotonConvenio().getId(), "Validacion incorrecta", "", request.getRemoteAddr());
                pagoDAWS.addPaymentInfoToInvoice(pi.getPago().getId(),pi.getPago().getBotonBoletas().get(0).getId(), "0000", "0000", "","", 0, "El usuario ha saltado " + loads + " veces por el sitio","0.0.0.0");

                redirigirSinComprobante(request, response, pi, destinationURL);
                return;
            }

        }
        else {
            pagoDAWS.addPaymentInfoToInvoice(pi.getPago().getId(),pi.getPago().getBotonBoletas().get(0).getId(), "0000", "0000", "","", 0, "El usuario ha saltado " + loads + " veces por el sitio","0.0.0.0");

            redirigirSinComprobante(request, response, pi, destinationURL);
            return;
        }

    }


}
