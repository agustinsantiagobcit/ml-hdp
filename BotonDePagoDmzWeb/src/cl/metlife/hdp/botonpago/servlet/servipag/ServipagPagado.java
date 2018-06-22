package cl.metlife.hdp.botonpago.servlet.servipag;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitservipag.XML4;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Ivan on 28-07-2014.
 */
public class ServipagPagado extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().print("Servipag pagado post");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaymentSupport ps = new PaymentSupport();
        String xmlEntrada = request.getParameter("xml");

        System.out.println("Recibido Servipag Pagado\n" + xmlEntrada);

        int tid = Integer.parseInt( xmlProcessor.getValueFromXml(xmlEntrada, "/Servipag/IdTxCliente"));
        System.out.println("Cierre (vista de comprobante) de pago con ID " + tid);
        PaymentInfo pi = pagoDAWS.getPago(tid, request.getRemoteAddr());

        XML4 newXML4 = new XML4();
        boolean firmaEsValida = newXML4.ValidaFirmaXML4(xmlEntrada, ps.getAgreementConfigPath("Servipag", pi));

        //PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
        //int tid = Integer.parseInt(newXML4.idTxCliente);
        ps.writeFile(xmlEntrada,tid+"_xml_4.txt", pi.getPago().getBotonConvenio().getLogPath());

        if(pi.getPago().getId() == tid) {
            System.out.println("Servipag: ID no coincide");

        }

        if (firmaEsValida) {

            Boolean aceptado = newXML4.estadopago.trim().equals("0");

            if (aceptado) {
                //Aceptado
                pi.setPago(this.pagoDAWS.markAsPayed(tid,newXML4.idTrxServipag, "", pi.getPago().getBotonConvenio().getId(), xmlEntrada, request.getRemoteAddr().toString()));

                String simboloDivisa="$";
                String codMon = pi.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda();
                if (codMon.compareToIgnoreCase("UF") == 0) {
                    simboloDivisa = "UF ";
                }

                request.getSession().setAttribute("SimboloDivisa", simboloDivisa);
                request.getSession().setAttribute("tid", pi.getPago().getId());
                request.getSession().setAttribute("Boletas", pi.getPago().getBotonBoletas());
                request.getSession().setAttribute("PaymentSupport",new PaymentSupport());
                request.getSession().setAttribute("montoTotal",pi.getPago().getMontoTotal());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                request.getSession().setAttribute("fechaPago", sdf.format( pi.getPago().getHoraCreacion().toGregorianCalendar().getTimeInMillis()));
                request.getSession().setAttribute("urlVuelta", pi.getPago().getUrlFinPago());

                if(pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                    request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
                    return;
                }
                else {
                    response.getWriter().println("<!DOCTYPE html>");
                    response.getWriter().println("<html>");
                    response.getWriter().println("<head>");
                    response.getWriter().println("<title>Boton de Pago</title>");
                    response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" +  pi.getPago().getUrlFinPago() + "\" />");
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
                    return;
                }
            }
            else {
                //rechazado
                this.pagoDAWS.markAsRejected(tid, pi.getPago().getBotonConvenio().getId(), newXML4.getMensaje(), xmlEntrada, request.getRemoteAddr().toString());
                request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            }

        }
        else {
            //Firma invalida o ID no coincide
            System.out.println("Error ServipagPagado. Firma valida:" + firmaEsValida + " ID recibido:"+tid + " ID esperado:"+  pi.getPago().getId() + "\nXML=" + xmlEntrada);
            response.getWriter().println("Error. No se pudo validar el mensaje desde Servipag. Por favor, valide el pago en su banco y en el sitio del comercio.");
        }


    }
}
