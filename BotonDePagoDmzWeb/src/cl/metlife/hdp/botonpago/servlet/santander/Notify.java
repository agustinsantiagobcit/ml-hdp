package cl.metlife.hdp.botonpago.servlet.santander;

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
        String descRet="";
        String idCom="";
        String idTrx="";
        String total="";
        String nroPagos="";
        String fechaTrx="";
        String fechaCont="";
        String numComp="";
        String idReg="";


        System.out.println("Santander notify post");
        String stringPost = request.getParameter("TX");
        System.out.println(stringPost);

        Document postBody = convertStringToDocument(stringPost);

        javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
        PaymentInfo pinfo;
        try {
            String codRet = (String) xPath.evaluate("/MPOUT/CODRET", postBody, XPathConstants.STRING);
            idTrx = (String) xPath.evaluate("/MPOUT/IDTRX", postBody, XPathConstants.STRING);
            idPago = Integer.parseInt(idTrx.substring(10));
            pinfo = pagoDAWS.getPago(idPago, request.getRemoteAddr());


            if(!codRet.equals("0000")){
                System.out.println("Codigo de retorno distinto de 0000, RECHAZANDO");
                this.pagoDAWS.markAsRejected(idPago, pinfo.getPago().getBotonConvenio().getId(), "Codigo != 0", stringPost, request.getRemoteAddr());
                respond(response, idPago, "NOK", pinfo.getPago().getBotonConvenio().getLogPath());

            }
            else{
                descRet = (String) xPath.evaluate("/MPOUT/DESCRET", postBody, XPathConstants.STRING);
                idCom = (String) xPath.evaluate("/MPOUT/IDCOM", postBody, XPathConstants.STRING);
                total = (String) xPath.evaluate("/MPOUT/TOTAL", postBody, XPathConstants.STRING);
                nroPagos = (String) xPath.evaluate("/MPOUT/NROPAGOS", postBody, XPathConstants.STRING);
                fechaTrx = (String) xPath.evaluate("/MPOUT/FECHATRX", postBody, XPathConstants.STRING);
                fechaCont = (String) xPath.evaluate("/MPOUT/FECHACONT", postBody, XPathConstants.STRING);
                numComp = (String) xPath.evaluate("/MPOUT/NUMCOMP", postBody, XPathConstants.STRING);
                idReg = (String) xPath.evaluate("/MPOUT/IDREG", postBody, XPathConstants.STRING);


                int tid = Integer.parseInt(idTrx.substring(10));
                System.out.println("Notificacion del pago " + tid);

                writeFile(stringPost, idPago+"_xml2",  pinfo.getPago().getBotonConvenio().getLogPath());

                //validación para la cantidad de boletas pagadas
                if(!validarBoletasPagadas(pinfo,Integer.parseInt(nroPagos)))
                {
                    System.out.println("Cantidad de boletas pagadas no coincide con las solicitadas, RECHAZANDO");
                    this.pagoDAWS.markAsRejected(idPago,pinfo.getPago().getBotonConvenio().getId(), "Boletas pagadas no coincide con el numero de boletas a pagar", stringPost, request.getRemoteAddr());
                    respond(response, idPago, "NOK", pinfo.getPago().getBotonConvenio().getLogPath());

                }
                else if(validarMonto(pinfo,Integer.parseInt(total))){

                    for( BotonBoleta bb : pinfo.getPago().getBotonBoletas() ) {
                        bb.setFechaTransaccion(fechaTrx.substring(0,8));
                        bb.setFechaContable(fechaCont.substring(0,8));
                        bb.setIdTransaccion(idTrx);
                        bb.setCodigoAutorizacion(numComp);
                        bb.setHoraTransaccion(fechaTrx.substring(8));
                    }

                    this.pagoDAWS.markAsPayedBotonPago(pinfo.getPago(),pinfo.getPago().getBotonConvenio().getId(), stringPost, request.getRemoteAddr());
                    System.out.println("Todo OK, Aceptando pago");
                    respond(response, idPago, "OK", pinfo.getPago().getBotonConvenio().getLogPath());
                }
                else {
                    this.pagoDAWS.markAsRejected(idPago, pinfo.getPago().getBotonConvenio().getId(), "Monto pagado no coincide con monto por cobrar", stringPost, request.getRemoteAddr());
                    System.out.println("Monto pagado no corresponde al solicitado, RECHAZANDO");
                    respond(response, idPago,"NOK", pinfo.getPago().getBotonConvenio().getLogPath());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            String xmlStr = "<NOTIFICA>NOK</NOTIFICA>";
            System.out.println("respondiendo con: " + xmlStr);
            response.getWriter().print(xmlStr);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Santander notify get");
        response.getWriter().print("Error, Acceso no autorizado");
    }

    public boolean validarMonto(PaymentInfo pago,Integer montoPagado){
        Double montoTotal = pago.getPago().getMontoTotal();
        if(montoTotal.intValue() == montoPagado.intValue()) {
            return true;
        }
        else {
            return false;
        }    }

    public boolean validarBoletasPagadas(PaymentInfo pinfo, Integer nroPagos){
        boolean resp = false;
        if(nroPagos == pinfo.getPago().getBotonBoletas().size()){
            resp = true;
        }
        return resp;
    }

    private void respond(HttpServletResponse response, int idPago, String message, String logPath) throws IOException {
        String xmlStr = "<NOTIFICA>" + message + "</NOTIFICA>";
        System.out.println("respondiendo con: " + xmlStr);
        writeFile("<NOTIFICA>OK</NOTIFICA>",idPago+"_xml3", logPath);
        response.getWriter().print(xmlStr);
    }



}
