package cl.metlife.hdp.botonpago.servlet.genericpayment;

import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import cl.metlife.hdp.botonpago.utils.xml.XmlProcessor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;

/**
 * Created by Ivan on 03-08-2014.
 */
public class GenericNotifyPaymentSupportServlet extends HttpServlet {
    protected PagoDAWS pagoDAWS;
    protected PaymentSupport ps;
    protected XmlProcessor xmlProcessor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);
        this.xmlProcessor = new XmlProcessor();
    }

    protected String getPostBodyString(HttpServletRequest request) {

        try {

            BufferedReader rdr = request.getReader();
            String line;
            String body = "";
            while((line = rdr.readLine())!=null){
                body += line;
            }
            body = URLDecoder.decode(body,"UTF-8");
            return body;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }


    public HttpSession loadSession(HttpServletRequest request, String sessionId, int idPago) {
        HttpSession sesion;
        sesion = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+sessionId);

        if(sesion == null) {
            sesion = request.getSession();

            PaymentInfo p = this.pagoDAWS.getPago(idPago, request.getRemoteAddr());

            sesion.setAttribute("PaymentInfo", p);
            sesion.setAttribute("LinksPago", p.getConvenios());
            sesion.setAttribute("tid", idPago);
            sesion.setAttribute("Boletas", p.getPago().getBotonBoletas());

            System.err.println("Se esta realizando el pago asociado a una sesion vencida! posibles fallos en esta transaccion");


        }
        return sesion;
    }

    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
