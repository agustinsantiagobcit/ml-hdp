package cl.metlife.hdp.botonpago.servlet.facilitadorcomercio;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericFinishedPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ivan on 11-08-2014.
 */
public class Notify extends HttpServlet {//extends GenericFinishedPaymentSupportServlet

    PagoDAWS pagoDAWS;
    PaymentSupport ps = new PaymentSupport();

    public void init(ServletConfig config) throws ServletException {
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Notificacion Facilitador Comercio");

        PaymentInfo pi = (PaymentInfo)request.getSession().getAttribute("PaymentInfo");

        String tid = request.getParameter("tid");
        String v = request.getParameter("v");
        String res = request.getParameter("res");

        Integer id = 0;


        try {
            id = Integer.parseInt(tid);
        }
        catch (Exception e) {
            System.out.println("No se puede parsear el id de pago. Recibido:'" + tid + "';");
            response.getWriter().print("No se puede parsear el id de transaccion. Recibido:'" + tid + "';");
        }

        if(pi==null || pi.getPago().getId() != id) {
            pi = pagoDAWS.getPago(id, request.getRemoteAddr());
        }

        //String destinationURL = pi.getPago().getUrlFinPago();

        if(res.compareTo("OK")==0) {
            if(ps.md5("OK" + id + "TOKEN").equalsIgnoreCase(v)){
                System.out.println("PI=" + pi + ";pagoDAWS=" + pagoDAWS);
                pi.getPago().setCodigoTransaccion("0000");
                pi.getPago().setCodigoAutorizacion("0000");
                //Integer agreement = Integer.parseInt(ps.getAgreementId("FacilitadorComercio", pi));
                Integer agreement = pi.getPago().getBotonConvenio().getId();
                //System.out.println("Facilitador. Marcando aceptado. ID Convenio:" + agreement);
                pagoDAWS.markAsPayedBotonPago(pi.getPago(), agreement, "Marcando aceptado", request.getRemoteAddr());

                String finishUrl = "";
                String urlFinalizadoFacilitador = PaymentSupport.getConfigParam(request, "REMOTE_Facilitador_End");
                finishUrl = urlFinalizadoFacilitador + "?res=OK&tid=" + id + "&v=" + PaymentSupport.md5("SUSCRITOOK" + id + "TOKEN");
                response.getWriter().println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "NotificacionOK");
                response.getWriter().println("End[" + finishUrl + "]");
            }
            else {
                this.pagoDAWS.markAsRejected(id, pi.getPago().getBotonConvenio().getId(), "Validacion incorrecta", "", request.getRemoteAddr());
                response.getWriter().println("Marcando rechazado. Firma invalida.");
                String urlFinalizadoFacilitador = PaymentSupport.getConfigParam(request, "REMOTE_Facilitador_End");
                String finishUrl = urlFinalizadoFacilitador + "?res=NOK&tid=" + id + "&v=" + PaymentSupport.md5("SUSCRITONOK" + id + "TOKEN");
                response.getWriter().println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                        "NotificacionNOK");
                response.getWriter().println("End[" + finishUrl + "]");

            }


        }
        else {
            response.getWriter().println("Notificacion Aceptada. Rechazado");
            this.pagoDAWS.markAsRejected(id, pi.getPago().getBotonConvenio().getId(), "Rechazado por Transbank", "", request.getRemoteAddr());
            String urlFinalizadoFacilitador = PaymentSupport.getConfigParam(request, "REMOTE_Facilitador_End");
            String finishUrl = urlFinalizadoFacilitador + "?res=NOK&tid=" + id + "&v=" + PaymentSupport.md5("SUSCRITONOK" + id + "TOKEN");
            response.getWriter().println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                    "NotificacionNOK");
            response.getWriter().println("End[" + finishUrl + "]");
        }

    }


}
