package cl.metlife.hdp.botonpago.servlet.servipag;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitservipag.XML2;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.kitservipag.XML3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 28-07-2014.
 */
public class ServipagNotify extends GenericNotifyPaymentSupportServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("SERVIPAG NOTIFY");
        String xmlEntrada = request.getParameter("XML");
        System.out.println("\n" + xmlEntrada + "\n");

        XML2 xmlNotificacion = new XML2();
        XML3 xmlRespuesta = new XML3();

        int tid = Integer.parseInt( xmlProcessor.getValueFromXml(xmlEntrada, "/Servipag/IdTxCliente"));
        System.out.println("Notificacion de pago con ID " + tid);
        PaymentInfo pi = pagoDAWS.getPago(tid, request.getRemoteAddr());

        if(pi == null) {
            System.out.println("PI = null, error.");
        }
        if(null == xmlEntrada) {
            System.out.println("xmlEntrada = null, error.");
        }
        if(null == xmlNotificacion) {
            System.out.println("xmlNotificacion = null, error.");
        }

        boolean firmaEsValida = xmlNotificacion.ValidaFirmaXML2(xmlEntrada, pi.getPago().getBotonConvenio().getConfigPath() /*ps.getAgreementConfigPath("Servipag", pi)*/);

        System.out.println("Firma XML2 valida: "+firmaEsValida);

        int idBoleta = Integer.parseInt(xmlNotificacion.getBoleta());

        ps.writeFile(xmlEntrada,tid+ "_" + idBoleta +"_xml_2.txt", pi.getPago().getBotonConvenio().getLogPath());
        //firmaEsValida = true; //Solo con propositos de test
        if(firmaEsValida) {
            String codAut = xmlNotificacion.getCodMedioPago(); //codigo del medio de pago, lo guardaremos como codigo de autorizacion
            String fechaCont = xmlNotificacion.getFechaContable();
            String fechaPago = xmlNotificacion.getFechaPago();
            String idTransaccion = xmlNotificacion.getIdTrxServipag();
            int montoPagado = Integer.parseInt(xmlNotificacion.getMonto());

            Boolean guardado = this.pagoDAWS.addPaymentInfoToInvoice(tid, idBoleta, idTransaccion, codAut, fechaCont, fechaPago, montoPagado, xmlEntrada, request.getRemoteAddr());
            this.pagoDAWS.markAsPayedBotonPago(pi.getPago(),pi.getPago().getBotonConvenio().getId(), xmlEntrada, request.getRemoteAddr());

            if(guardado) {
                System.out.println("Aceptando notificacion");
                response.getWriter().print(this.generateAcceptedXML(tid, idBoleta, pi.getPago().getBotonConvenio().getLogPath()));

            }
            else {
                System.out.println("Aceptando notificacion, incosistencia datos");

                response.getWriter().print(this.generateRejectedXML(tid, idBoleta, pi.getPago().getBotonConvenio().getLogPath()));
            }
        }
        else {
            System.out.println("Rechazando notificacion, firma invalida");
            response.getWriter().print(this.generateRejectedXML(tid, idBoleta, pi.getPago().getBotonConvenio().getLogPath()));
        }
    }

    private String generateAcceptedXML(int tid, int idBoleta, String logPath) {
        return this.generateResponseXML(tid, idBoleta, true, logPath);
    }

    private String generateRejectedXML(int tid, int idBoleta, String logPath) {
        return this.generateResponseXML(tid, idBoleta, false, logPath);
    }

    private String generateResponseXML(int tid, int idBoleta, boolean accepted, String logPath) {
        XML3 xmlSalida = new XML3();
        String xml = xmlSalida.GeneraFirmaXML3(accepted);
        ps.writeFile(xml, tid+"_" + idBoleta + "_xml_3.txt", logPath);
        return xml;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }




}
