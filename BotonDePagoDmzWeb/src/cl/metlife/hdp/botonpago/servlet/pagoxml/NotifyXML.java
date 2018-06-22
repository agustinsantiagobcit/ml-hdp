package cl.metlife.hdp.botonpago.servlet.pagoxml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitservipag.XML3;
import cl.metlife.hdp.botonpago.kitservipag.Log;
import cl.metlife.hdp.botonpago.kitservipag.Variables;
import cl.metlife.hdp.botonpago.kitservipag.XML2;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet.convertStringToDocument;

/**
 * Created by Ivan on 28-07-2014.
 */
public class NotifyXML extends HttpServlet {
    PaymentSupport util = new PaymentSupport();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        String XmlSalida = "";
        Log log = new Log();
        Variables cargaVariables = new Variables();
        cargaVariables.CargarVariables("aaa"/*, request*/);
        System.out.println("-----------------------Inicio H2H-----------------------\n");
        XML2 newXML2 = new XML2();
        try
        {
            String XmlEntrada = "";
            boolean  logicaInternaCliente = false;
            XmlEntrada = request.getParameter("xml");
            System.out.println("XML2" + "\nRecepcion Xml2 desde Servipag: " + XmlEntrada + "\n");
            Document postBody = convertStringToDocument(XmlEntrada);
            NodeList idNodeList = postBody.getElementsByTagName("IdTrxServipag");
            String idString = idNodeList.item(0).getNodeValue();
            String idPagoString = idString.split("|")[0];
            String idPagoSession = idString.split("|")[1];

            PaymentInfo payment = (PaymentInfo) util.getFromSession(request, idPagoSession, "PaymentInfo");

            XML3 newXML3 = new XML3();

            boolean firmaEsValida = newXML2.ValidaFirmaXML2(XmlEntrada, util.getAgreementConfigPath("Servipag", payment));
            if (firmaEsValida)
            {
                XmlSalida = newXML3.GeneraFirmaXML3(logicaInternaCliente/*, newXML2.getIdTxCliente()*/);
                response.setHeader("Content-Type", "application/x-www-form-urlencoded");
                System.out.println("XML2 \nRespuesta a Servipag XML3: " + XmlSalida + "\n");
                System.out.println("-----------------------Fin H2H-----------------------\n\n");
                out.print(XmlSalida);
            }
            else
            {
                response.setHeader("Content-Type", "application/x-www-form-urlencoded");
                XmlSalida = newXML3.GeneraFirmaXML3(logicaInternaCliente/*,newXML2.getIdTxCliente()*/);
                System.out.println("XML2 \nRespuesta a Servipag XML3: " + XmlSalida + "\n");
                System.out.println("-----------------------Fin H2H-----------------------\n\n");
                out.print(XmlSalida);
            }
        }
        catch(Exception _e)
        {
            XML3 newXML3 = new XML3();
            XmlSalida = newXML3.GeneraFirmaXML3(false/*,newXML2.getIdTxCliente()*/);
            response.setHeader("Content-Type", "application/x-www-form-urlencoded");
            System.out.println(" XML2 \nExcepcion Error en la recepcion del XML2 :" + _e.getMessage() + "\n");
            System.out.println("-----------------------Fin H2H-----------------------\n\n");
            out.print(XmlSalida);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public boolean validarMonto(PaymentInfo pinfo,Integer total){
        return pinfo.getPago().getMontoTotal().equals(total);
    }

    public boolean validarBoletasPagadas(PaymentInfo pinfo, Integer nroPagos){
        boolean resp = false;
        if(nroPagos == pinfo.getPago().getBotonBoletas().size()){
            resp = true;
        }
        return resp;
    }

   /* private PaymentInfo llenarBotonPago(PaymentInfo pinfo){
        for(BotonBoleta boleta : pinfo.getPago().getBotonBoletas()){
            boleta.setFechaContable(this.fechaCont);
            boleta.setFechaTransaccion(this.fechaTrx);
            boleta.setIdTransaccion(this.idTrx);
        }
        return pinfo;
    }*/
}
