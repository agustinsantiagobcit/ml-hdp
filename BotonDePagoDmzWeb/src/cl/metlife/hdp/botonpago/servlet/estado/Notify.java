package cl.metlife.hdp.botonpago.servlet.estado;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.writeFile;

/**
 * Created by Ivan on 28-07-2014.
 */
public class Notify extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idPago = 0;
        String idTrx="";
        String total="";
        String fechaTrx="";
        String numComp="";
        String horaTrx="";

        System.out.println("Banco Estado notify post");
        String stringPost = request.getParameter("xml");
        System.out.println(stringPost);

        Document postBody = convertStringToDocument(stringPost);

        javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
        PaymentInfo pinfo;
        try {
            String codRet = (String) xPath.evaluate("INICIO/MULTIPAGO/RESULTADO/RESULT_MPAGO", postBody, XPathConstants.STRING);
            idTrx = (String) xPath.evaluate("INICIO/ENCABEZADO/ID_SESION", postBody, XPathConstants.STRING);
            System.out.println("codRet=" + codRet + "; idtrx=" + idTrx);
            idPago = Integer.parseInt(idTrx);
            pinfo = pagoDAWS.getPago(idPago, request.getRemoteAddr());
            total = (String) xPath.evaluate("INICIO/ENCABEZADO/TOTAL", postBody, XPathConstants.STRING);

            if(!codRet.equals("OK")){
                System.out.println("Codigo de retorno distinto de OK, RECHAZANDO");
                this.pagoDAWS.markAsRejected(idPago, pinfo.getPago().getBotonConvenio().getId(), "Codigo != 0", stringPost, request.getRemoteAddr());
                respond(response, idPago, total,"NOK",  pinfo.getPago().getBotonConvenio().getLogPath());

            }
            else{
                fechaTrx = (String) xPath.evaluate("INICIO/RESULTADO/FEC_MPAGO", postBody, XPathConstants.STRING);
                numComp = (String) xPath.evaluate("INICIO/MULTIPAGO/RESULTADO/TRX_MPAGO", postBody, XPathConstants.STRING);

                int tid = idPago;
                System.out.println("Notificacion del pago " + tid);

                writeFile(stringPost, idPago+"_xml2",  pinfo.getPago().getBotonConvenio().getLogPath());
                System.out.println("Monto Solicitado: " + pinfo.getPago().getMontoTotal().intValue() + ";+montoPagado="+Integer.parseInt(total));

                if(pinfo.getPago().getMontoTotal().intValue() == Integer.parseInt(total)){
                    System.out.println("Monto correcto, actualizando pago en BD");
                    pinfo.getPago().setCodigoTransaccion(numComp);

                    for( BotonBoleta bb : pinfo.getPago().getBotonBoletas() ) {
                        System.out.println("actualizando boleta " + bb.getNumeroBoleta());
                        System.out.println(fechaTrx+";" + fechaTrx + ";" + idTrx +";" + numComp + ";" + horaTrx);
                        bb.setFechaTransaccion(fechaTrx);
                        bb.setFechaContable(fechaTrx);
                        bb.setIdTransaccion(idTrx);
                        bb.setCodigoAutorizacion(numComp);
                        bb.setHoraTransaccion(horaTrx);
                    }

                    this.pagoDAWS.markAsPayedBotonPago(pinfo.getPago(),pinfo.getPago().getBotonConvenio().getId(), stringPost, request.getRemoteAddr());
                    System.out.println("Todo OK, Aceptando pago");
                    respond(response, idPago,total, "OK", pinfo.getPago().getBotonConvenio().getLogPath());
                }
                else {
                    this.pagoDAWS.markAsRejected(idPago, pinfo.getPago().getBotonConvenio().getId(), "Monto pagado no coincide con monto por cobrar", stringPost, request.getRemoteAddr());
                    System.out.println("Monto pagado no corresponde al solicitado, RECHAZANDO");
                    respond(response, idPago,total,"NOK",  pinfo.getPago().getBotonConvenio().getLogPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String xmlStr = "<INICIO>";
            xmlStr += "<IDSESION>"+idPago+"</IDSESION>";
            xmlStr += "<RESP_ONLINE>NOK</RESP_ONLINE></INICIO>";

            System.out.println("respondiendo con: " + xmlStr);
            response.getWriter().print(xmlStr);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Banco Estado notify get");
        response.getWriter().print("Error, Acceso no autorizado");
    }


    private void respond(HttpServletResponse response, int idPago, String total, String message, String logPath) throws IOException {
        String xmlStr = "<INICIO>";
        xmlStr += "<IDSESION>"+idPago+"</IDSESION>";
        xmlStr += "<RESP_ONLINE>"+message+"</RESP_ONLINE>";
        xmlStr += "<TOTAL>"+total+"</TOTAL></INICIO>";

        System.out.println("respondiendo con: " + xmlStr);
        writeFile(xmlStr,idPago+"_xml3", logPath);
        response.getWriter().print(xmlStr);
    }

}
