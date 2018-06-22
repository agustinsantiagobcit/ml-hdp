package cl.metlife.hdp.botonpago.servlet.webpay;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericFinishedPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ivan on 11-08-2014.
 */
public class PagoFinalizadoWebPay extends GenericFinishedPaymentSupportServlet {

    /*PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {
        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
    }*/


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PaymentInfo pi = (PaymentInfo)request.getSession().getAttribute("PaymentInfo");

        String tid = request.getParameter("tid");
        String v = "";
        v = request.getParameter("v");
        Integer id = 0;

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

        //token invalido
        if(!PaymentSupport.verificarV(v, pi.getPago(), request.getParameter("status"))) {
                redirigirEstadoInvalido(request, response);
        }

        //pago fallido
        if( !request.getParameter("status").equals("exito")) {
            if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                redirigirRechazado(request, response, pi);
                return;
            }
            else {
                redirigirSinComprobante(request, response, pi, destinationURL);
                return;
            }
        }



        //pago OK
        String digitosTarjeta;
        String numeroOrden = id+"";
        int montoTotal=0;

        for(BotonBoleta b : pi.getPago().getBotonBoletas()){
            montoTotal += b.getMonto();
        }

        Calendar c = Calendar.getInstance();
        char[] fecha;
        String fechaPago;

        try {
            fecha = pi.getPago().getBotonBoletas().get(0).getFechaTransaccion().toCharArray();
            if(fecha.length == 4) {
                fechaPago = "" + fecha[2] + fecha[3] + "/" + fecha[0] + fecha[1] + "/" + c.get(Calendar.YEAR);
            }
            else{
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaPago = sdf.format(cal.getTimeInMillis());
            }
        }
        catch (Exception e) {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fechaPago = sdf.format(cal.getTimeInMillis());
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
        request.getSession().setAttribute("PaymentSupport",new PaymentSupport());
        request.getSession().setAttribute("montoTotal",montoTotal);
        request.getSession().setAttribute("fechaPago",fechaPago);
        request.getSession().setAttribute("urlVuelta",destinationURL);

        if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
            request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
            return;
        }
        else {
            redirigirSinComprobante(request, response, pi, destinationURL);
            return;
        }


    }


}
