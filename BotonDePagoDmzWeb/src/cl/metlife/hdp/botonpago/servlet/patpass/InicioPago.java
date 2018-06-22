package cl.metlife.hdp.botonpago.servlet.patpass;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitpatpass.*;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Ivan on 28-07-2014.
 */
public class InicioPago extends HttpServlet {

    PaymentSupport util = new PaymentSupport();
    PatPassUtils pputil = new PatPassUtils();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Obteniendo token para pago con PatPass.");

        int id = Integer.parseInt(request.getParameter("tid"));
        String nombre = request.getParameter("name");
        String apellidoP = request.getParameter("fSurname");
        String apellidoM = request.getParameter("mSurname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String rut = request.getParameter("rut");

        HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
        PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
        userSession.setAttribute("convenio", util.getAgreementId("PatPass", payment));
        String wsdlUrl = payment.getPago().getBotonConvenio().getUrlInicioPago();
        TransbankWrapper wsPatPass = pputil.getPatPassWs(wsdlUrl);

        WsInitTransactionInput wsIni = this.buildInitTransactionInput(payment,request, nombre, apellidoP, apellidoM, email, phone);

        WsInitTransactionOutput wsOut = null;
        try {
            wsOut = wsPatPass.initTransaction(wsIni);
        }catch (Exception e){

            // Se asume que hay un error de Seguridad

            //this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Error de webservice wrapper", "token="+token + ";error="+e.getMessage()+";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());
            request.getSession().setAttribute("ordenPago", payment.getPago().getId());
            request.getSession().setAttribute("razonRechazo", "Error de Seguridad");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            return;

        }
        String token = wsOut.getToken();
        String redirectUrl = wsOut.getUrl();

        util.writeFile("Token:"+token+";\nUrl=" + redirectUrl,payment.getPago().getId() + "_1GetToken", payment.getPago().getBotonConvenio().getLogPath());

        System.out.println("Iniciando pago con PatPass.");

        request.getSession().setAttribute("postToUrl", redirectUrl);
        request.getSession().setAttribute("postParamName", "token_ws"); //Era data, se solicita cambio a TX
        request.getSession().setAttribute("postContent", token);

        String target = "_self";
        if( payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True") ){
            target = "_blank";
        }

        request.getSession().setAttribute("PaymentTarget", target);


        request.getRequestDispatcher("/generic/poster.jsp").forward(request, response);

    }

    private WsInitTransactionInput buildInitTransactionInput(PaymentInfo payment, HttpServletRequest request, String name, String fSurname, String mSurname, String email, String phone) {
        WsInitTransactionInput out = new WsInitTransactionInput();

        out.setWSTransactionType(WsTransactionType.TR_NORMAL_WS_WPM);
        out.setSessionId(payment.getPago().getId() + "");
        out.setReturnURL(this.buildNotifyUrl(payment, request));
        out.setFinalURL(this.buildCierreUrl(payment, request));

        WsTransactionDetail wsDet = new WsTransactionDetail();

        BotonBoleta bol = payment.getPago().getBotonBoletas().get(0);

        wsDet.setAmount(BigDecimal.valueOf(bol.getMonto()));
        wsDet.setBuyOrder(""+payment.getPago().getId());
        wsDet.setCommerceCode(payment.getPago().getBotonConvenio().getNumeroConvenio());
        //wsDet.setSharesAmount();
        //wsDet.setSharesNumber();

        out.getTransactionDetails().add(wsDet);

        WpmDetailInput wpmDet = new WpmDetailInput();


        //wpmDet.setServiceId("335456675433"); // habilitar para QA
        wpmDet.setServiceId(bol.getNumeroBoleta()); //habilitar para Produccion


        wpmDet.setCardHolderId(request.getParameter("rut"));
        wpmDet.setCardHolderLastName1(request.getParameter("fSurname"));
        wpmDet.setCardHolderLastName2(request.getParameter("mSurname"));
        wpmDet.setCardHolderName(request.getParameter("name"));
        wpmDet.setCardHolderMail(request.getParameter("email"));
        wpmDet.setCellPhoneNumber(request.getParameter("phone"));
        wpmDet.setExpirationDate(payment.getPago().getBotonBoletas().get(0).getVencimiento());
        wpmDet.setCommerceMail(payment.getPago().getBotonConvenio().getBotonLineasDeNegocio().getMailContacto());

        System.out.println("Pat Pass. Moneda de pago:"+payment.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda());
        wpmDet.setUfFlag(payment.getPago().getBotonBoletas().get(0).getCodigosMoneda().getCodigoMoneda().compareTo("UF")==0);

        out.setWPMDetail(wpmDet);



        return out;
    }

    private String buildCierreUrl(PaymentInfo payment, HttpServletRequest request) {
        String baseUrl = util.getConfigParam(request, "REMOTE_pagoCierreUrlPatPass");
        return baseUrl + "?p="+payment.getPago().getId();    }

    private String buildNotifyUrl(PaymentInfo payment, HttpServletRequest request) {
        String baseUrl = util.getConfigParam(request, "REMOTE_pagoNotifyUrlPatPass");
        return baseUrl + "?p="+payment.getPago().getId();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Error, Acceso no autorizado");
    }

/*    private XML buildPaymentParameters(PaymentInfo payment) throws PaymentException {

        List<BotonBoleta> boletas = payment.getPago().getBotonBoletas();
        int counter = 0;
        if(boletas.size()>6){
            throw new PaymentException("Solo se puede pagar hasta 6 boletas, se recibieron "+ boletas.size());
        }

        XML xmlSantander = new XML();
        List<XMLDetalle> detalle = new ArrayList<XMLDetalle>();

        xmlSantander.setIdComercio(payment.getPago().getBotonConvenio().getNumeroConvenio());
        xmlSantander.setIDTRX(payment.getPago().getBotonConvenio().getNumeroConvenio() + String.format("%06d", payment.getPago().getId()));
        xmlSantander.setTotal(payment.getPago().getMontoTotal()+"");
        xmlSantander.setNroPagos(boletas.size()+"");
        for(BotonBoleta b : boletas){
            XMLDetalle xmlDetalle = new XMLDetalle();
            xmlDetalle.SRVREC = payment.getPago().getBotonConvenio().getLlave();//"9023";
            xmlDetalle.monto = b.getMonto()+"";
            xmlDetalle.glosa = b.getDescripcion();
            xmlDetalle.cantidad = "1";
            xmlDetalle.precioUnitario = b.getMonto()+"";
            xmlDetalle.datosAdicionales = b.getNumeroBoleta();//payment.getPago().getId()+";"+b.getId(); //se guarda el id del pago, id de la boleta
            detalle.add(xmlDetalle);
        }
        xmlSantander.setDetalle(detalle);
        return xmlSantander;
    }*/
}
