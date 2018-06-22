package cl.metlife.hdp.botonpago.genericxml;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Ivan on 28-08-2014.
 */
public class GenericXML4 extends GenericNotifyPaymentSupportServlet {

    public String ProcesarXMLFin(String xmlEntrada,HttpServletRequest request, HttpServletResponse response){
        String retorno ="";
        javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
        Document xml = convertStringToDocument(xmlEntrada);

        try {
            String IdentificadorComercio = (String) xPath.evaluate("/Fin/IdentificadorComercio", xml, XPathConstants.STRING);
            String CodigoRetorno = (String) xPath.evaluate("/Fin/CodigoRetorno", xml, XPathConstants.STRING);
            String NroBoletas = (String) xPath.evaluate("/Fin/NroBoletas", xml, XPathConstants.STRING);
            String Monto = (String) xPath.evaluate("/Fin/Monto", xml, XPathConstants.STRING);
            String Firma = (String) xPath.evaluate("/Fin/Firma", xml, XPathConstants.STRING);
            String IdTransaccionRemoto = (String) xPath.evaluate("/Fin/IdTransaccionRemoto", xml, XPathConstants.STRING);

                try {
                    if ("0000".equals(CodigoRetorno)) {
                        PaymentInfo pi = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
                        Integer id = 0;
                        try {
                            id = Integer.parseInt(IdentificadorComercio);
                        } catch (Exception e) {

                        }
                        if (pi == null || pi.getPago().getId() != id) {
                            pi = pagoDAWS.getPago(id, request.getRemoteAddr());
                        }
                        String destinationURL = pi.getPago().getUrlFinPago();

                        int montoTotal = 0;

                        for (BotonBoleta b : pi.getPago().getBotonBoletas()) {
                            montoTotal += b.getMonto();
                        }

                        request.getSession().setAttribute("PaymentInfo", pi);
                        request.getSession().setAttribute("tid", id);
                        request.getSession().setAttribute("Boletas", pi.getPago().getBotonBoletas());
                        request.getSession().setAttribute("PaymentSupport", new PaymentSupport());
                        request.getSession().setAttribute("montoTotal", montoTotal);
                        if(pi.getPago().getBotonBoletas() != null && pi.getPago().getBotonBoletas().size()>0) {
                            request.getSession().setAttribute("fechaPago", pi.getPago().getBotonBoletas().get(0).getFechaContable());
                        }

                        request.getSession().setAttribute("urlVuelta", destinationURL);

                        if (pi.getPago().getBotonConvenio().getMostrarCupon().equals("True")) {
                            request.getRequestDispatcher("ComprobantePago.jsp").forward(request, response);
                        } else {
                            response.getWriter().println("<!DOCTYPE html>");
                            response.getWriter().println("<html>");
                            response.getWriter().println("<head>");
                            response.getWriter().println("<title>Boton de Pago</title>");
                            response.getWriter().println("<meta http-equiv=\"refresh\" content=\"0; url=" + destinationURL + "\" />");
                            response.getWriter().println("</head>");
                            response.getWriter().println(" <body>");
                            response.getWriter().println("Espere por favor... redirigiendo");
                            response.getWriter().println("<!--estado=" + pi.getPago().getBotonEstado().getNombre() + "-->");
                            response.getWriter().println(" </body>");

                            response.getWriter().println("</html>");

                            request.getSession().removeAttribute("PaymentInfo");
                            request.getSession().removeAttribute("LinksPago");
                            request.getSession().removeAttribute("tid");
                            request.getSession().removeAttribute("Boletas");
                        }
                    }else{
                        request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}
