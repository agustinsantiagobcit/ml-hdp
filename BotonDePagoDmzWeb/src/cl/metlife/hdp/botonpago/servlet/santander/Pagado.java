package cl.metlife.hdp.botonpago.servlet.santander;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.writeFile;

/**
 * Created by Ivan on 28-07-2014.
 */
public class Pagado extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Santander Pagado POST");
        this.process(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Santander Pagado GET");
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando en santander pagado.");
        request.getSession().setAttribute("ordenPago", "");
        request.getSession().setAttribute("razonRechazo", "");
        String stringBody = request.getParameter("TX");//getPostBodyString(request);
        System.out.println("XML 4 Santander: " + stringBody);

        Document postBody = convertStringToDocument(stringBody);
        javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
        String idPago="";

        try {
            String codRet = (String) xPath.evaluate("/MPFIN/CODRET", postBody, XPathConstants.STRING);

            if(codRet.equals("0000")){
                String idTrx = (String) xPath.evaluate("/MPFIN/IDTRX", postBody, XPathConstants.STRING);
                String nroPagos = (String) xPath.evaluate("/MPFIN/NROPAGOS", postBody, XPathConstants.STRING);
                String total = (String) xPath.evaluate("/MPFIN/TOTAL", postBody, XPathConstants.STRING);
                String indPago = (String) xPath.evaluate("/MPFIN/INDPAGO", postBody, XPathConstants.STRING);
                String idReg = (String) xPath.evaluate("/MPFIN/IDREG", postBody, XPathConstants.STRING);
                idPago = idTrx.substring(10);

                System.out.println("idTrx" + idTrx);
                System.out.println("nroPagos" + nroPagos);
                System.out.println("indPago" + indPago);
                System.out.println("total" + total);
                System.out.println("idReg" + idReg);
                System.out.println("idPago" + idPago);


                if(indPago.compareTo("S")==0){
                    Integer id = 0;
                    try {
                        id = Integer.parseInt(idPago);
                    }
                    catch (Exception e) {
                        System.out.println("No se ha podido parsear el numero del pago, string=" + idPago);
                    }

                    PaymentInfo pi;

                    try {
                         //pi = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
                        //System.out.println("id obtenido (int) " + id);

                        //if(pi==null || pi.getPago().getId() != id) {
                        //System.out.println("no hay pi disponible, obteniendo desde daws ");
                        pi = pagoDAWS.getPago(id, request.getRemoteAddr());
                        if ( pi == null ) {
                            throw new Exception("pi no existe en base de datos");
                        }

                    }
                    catch (Exception e) {
                        System.out.println("Santander Pagado. Error. No se pudo obtener el pago para el id " + id);
                        request.getSession().setAttribute("ordenPago", id);
                        request.getSession().setAttribute("razonRechazo", "La orden de pago no ha sido encontrada en nuestros registros");
                        request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                        return;
                    }


                    writeFile(stringBody, id + "_xml4", pi.getPago().getBotonConvenio().getLogPath());
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
                    request.getSession().setAttribute("tid", id);
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
                    System.out.println("Redirigiendo a rechazado, mensaje recibido:" + postBody);
                    request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                }
            }
            else {
                request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        System.out.println("Santander pagado. Fin servlet");
    }

}
