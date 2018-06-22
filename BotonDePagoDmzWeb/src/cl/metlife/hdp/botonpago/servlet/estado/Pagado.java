package cl.metlife.hdp.botonpago.servlet.estado;

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

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.writeFile;

/**
 * Created by Ivan on 28-07-2014.
 */
public class Pagado extends GenericFinishedPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Banco Estado Pagado POST");
        this.process(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Banco Estado Pagado GET");
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando en Banco Estado pagado.");
        request.getSession().setAttribute("ordenPago", "");
        request.getSession().setAttribute("razonRechazo", "");
        String stringBody = request.getParameter("idTrx");//getPostBodyString(request);
        System.out.println("Banco Estado. Comprobante: " + stringBody);


        String idPago=stringBody;

        try {
            //String codRet = (String) xPath.evaluate("/MPFIN/CODRET", postBody, XPathConstants.STRING);

            if(true){

                    PaymentInfo pi;

                    try {
                        int id = Integer.parseInt(idPago);

                        pi = pagoDAWS.getPago(id, request.getRemoteAddr());
                        if ( pi == null ) {
                            throw new Exception("pago no existe en base de datos");
                        }

                    }
                    catch (Exception e) {
                        System.out.println("Banco Estado Pagado. Error. No se pudo obtener el pago para el id " + idPago);
                        idPago = "Desconocido";
                        request.getSession().setAttribute("ordenPago", idPago);
                        request.getSession().setAttribute("razonRechazo", "La orden de pago no ha sido encontrada en nuestros registros");
                        request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                        return;
                    }

                    if(pi.getPago().getBotonEstado().getId()!=2) {
                        System.out.println("Banco Estado Pagado. Error. El status del pago es distinto de 'PAGADO' " + idPago);
                        request.getSession().setAttribute("ordenPago", pi.getPago().getId());
                        request.getSession().setAttribute("razonRechazo", "La transacci?n no pudo completarse");
                        request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                        return;
                    }


                    writeFile(stringBody, idPago + "_xml4", pi.getPago().getBotonConvenio().getLogPath());
                    String destinationURL = pi.getPago().getUrlFinPago();

                    int montoTotal=0;

                    for(BotonBoleta b : pi.getPago().getBotonBoletas()){
                        montoTotal += b.getMonto();
                    }


                    String fechaPago = " " ;
                    char[] fecha;
                    if(pi.getPago().getBotonBoletas().get(0).getFechaContable() != null) {
                        System.out.println("fecha contable = " + pi.getPago().getBotonBoletas().get(0).getFechaContable());
                        fechaPago = pi.getPago().getBotonBoletas().get(0).getFechaContable();
                    }

                    fecha = fechaPago.toCharArray();

                    if(fecha.length == 8) {
                        fechaPago = "" + fecha[6] + fecha[7] + "/" + fecha[4] + fecha[5] + "/" + fecha[0] + fecha[1] + fecha[2] + fecha[3];
                    }
                    else{
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
                    request.getSession().setAttribute("tid", idPago);
                    request.getSession().setAttribute("Boletas", pi.getPago().getBotonBoletas());
                    request.getSession().setAttribute("PaymentSupport",new PaymentSupport());
                    request.getSession().setAttribute("montoTotal",montoTotal);
                    request.getSession().setAttribute("fechaPago",fechaPago);
                    request.getSession().setAttribute("urlVuelta",destinationURL);

                    if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                        request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
                    }
                    else {
                        response.getWriter().println("<!DOCTYPE html>");
                        response.getWriter().println("<html>");
                        response.getWriter().println("<head>");
                        response.getWriter().println("<title>Boton de Pago</title>");
                        response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" + destinationURL + "\" />");
                        response.getWriter().println("</head>");
                        response.getWriter().println(" <body>");
                        response.getWriter().println("Espere por favor... redirigiendo");
                        response.getWriter().println("<!--estado="+pi.getPago().getBotonEstado().getNombre()+"-->");
                        response.getWriter().println(" </body>");

                        response.getWriter().println("</html>");

                        request.getSession().removeAttribute("PaymentInfo");
                        request.getSession().removeAttribute("LinksPago");
                        request.getSession().removeAttribute("tid");
                        request.getSession().removeAttribute("Boletas");
                    }
                }
                else {
                    System.out.println("Redirigiendo a rechazado, parametro recibido:" + idPago);
                    request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                }


        } catch (Exception e) {
            e.printStackTrace();
            redirigirEstadoInvalido(request,response);
        }

        System.out.println("Banco Estado pagado. Fin servlet");
    }


}
