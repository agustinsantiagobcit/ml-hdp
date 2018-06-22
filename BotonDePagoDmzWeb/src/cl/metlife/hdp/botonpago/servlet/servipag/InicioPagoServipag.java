package cl.metlife.hdp.botonpago.servlet.servipag;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitservipag.NodosXML1Detalle;
import cl.metlife.hdp.botonpago.kitservipag.XML1;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ivan on 26-08-2014.
 */
public class InicioPagoServipag  extends HttpServlet {

    PaymentSupport util = new PaymentSupport();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().print("Error, Acceso no autorizado");
        System.out.println("Acceso a ServipagNotify via GET");
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("Servipag", payment));


            String requestXML = buildSignedServipagXML(payment, request);


            PaymentSupport ps = new PaymentSupport();
            ps.writeFile(requestXML, payment.getPago().getId()+"_xml_1.txt",  payment.getPago().getBotonConvenio().getLogPath());
            System.out.println("Iniciando pago con Servipag. XML=\n"+requestXML);

            String remoteUrl = PaymentSupport.getConfigParam(request, "REMOTE_servipagInicioURL_" + payment.getPago().getBotonLineasDeNegocio().getId());

            request.getSession().setAttribute("postToUrl", remoteUrl);
            request.getSession().setAttribute("postParamName", "xml");
            request.getSession().setAttribute("postContent", requestXML);

            String target = "_self";
            if( payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True") ){
                target = "_blank";
            }

            request.getRequestDispatcher("/generic/poster.jsp").forward(request, response);
        }
        else {
            response.getWriter().print("ID no coincide");
        }
    }

    private String buildSignedServipagXML(PaymentInfo payment, HttpServletRequest request) {
        XML1 xml1 = null;
        try {
            xml1 = this.buildServipagXML( payment, request);
            return xml1.GeneraXml1(util.getAgreementConfigPath("Servipag", payment));//, util.getAgreementConfigPath("Servipag", payment), request
        } catch (PaymentException e) {
            e.printStackTrace();
        }
        return "";



    }



    private XML1 buildServipagXML(PaymentInfo payment, HttpServletRequest request) throws PaymentException {
        System.out.println("-----------------------LLenando XML1-----------------------------------");
        List<BotonBoleta> boletas = payment.getPago().getBotonBoletas();
        int counter = 0;
        if(boletas.size()>6){
            throw new PaymentException("Solo se puede pagar hasta 6 boletas, se recibieron "+ boletas.size());
        }

        XML1 xml1 = new XML1();
        List<NodosXML1Detalle> detalle = new ArrayList<NodosXML1Detalle>();

        xml1.setCodigoCanalPago(util.getAgreementId("Servipag", payment).toString());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        xml1.setFechaPago(sdf.format(c.getTimeInMillis()));
        xml1.setIdTxCliente(payment.getPago().getId()+""/* + ";" + request.getSession().getId()*/);
        xml1.setMontoTotalDeuda(payment.getPago().getMontoTotal().intValue() + "");
        xml1.setNumeroBoletas(payment.getPago().getBotonBoletas().size()+"");
        xml1.setNombreCliente(payment.getPago().getClienteBean().getNombreCliente());
        if(payment.getPago().getClienteBean().getEmail() == null) {
            xml1.setMailCliente("");
        }
        else {
            xml1.setMailCliente(payment.getPago().getClienteBean().getEmail());
        }

        xml1.setRutCliente(payment.getPago().getClienteBean().getRutCliente());
        int cont = 1;
        for(BotonBoleta b : boletas){
            NodosXML1Detalle xmlDetalle = new NodosXML1Detalle();
            xmlDetalle.IdSubTrx = cont+"";
            xmlDetalle.CodigoIdentificador =b.getNumeroBoleta();
            xmlDetalle.Boleta=b.getId()+"";
            xmlDetalle.Monto = b.getMonto().intValue()+"";
            xmlDetalle.FechaVencimiento = sdf.format(b.getVencimiento().toGregorianCalendar().getTimeInMillis());
            detalle.add(xmlDetalle);
            cont++;
        }
        xml1.setSolicitudPagoDetalle(detalle);
        System.out.println("-----------------------Termino llenado XML1-----------------------------------");
        return xml1;
    }
}
