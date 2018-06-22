package cl.metlife.hdp.botonpago.servlet.patpass;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.kitpatpass.*;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ivan on 28-07-2014.
 */
public class Notify extends GenericNotifyPaymentSupportServlet {

    static final Logger LOGGER = LoggerFactory.getLogger(Notify.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");


    PatPassUtils pputil = new PatPassUtils();



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Integer idPago = 0;
        String descRet="";
        String idCom="";
        String idTrx="";
        String total="";
        String nroPagos="";
        String fechaTrx="";
        String fechaCont="";
        String numComp="";
        String idReg="";*/


        LOGGER.debug("PatPass notify post");
        String token = request.getParameter("token_ws");

        HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
        PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");

        String wsdlUrl = payment.getPago().getBotonConvenio().getUrlInicioPago();
        TransbankWrapper wsPatPass = pputil.getPatPassWs(wsdlUrl);

        TransactionResultOutput transactionResult = null;

        try{
            transactionResult = wsPatPass.getTransactionResult(token);
        }catch (Exception e){

            this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Error de webservice wrapper", "token="+token + ";error="+e.getMessage()+";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());
            request.getSession().setAttribute("ordenPago", payment.getPago().getId());
            request.getSession().setAttribute("razonRechazo", "Error de Seguridad");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            return;
        }


        String accountingDate = transactionResult.getAccountingDate();
        String buyOrder = transactionResult.getBuyOrder();
        CardDetail cardDetail = transactionResult.getCardDetail();
        List<WsTransactionDetailOutput> detailsOutput = transactionResult.getDetailOutput();
        String sessionId = transactionResult.getSessionId(); //pago.id
        Calendar transactionDate = transactionResult.getTransactionDate().toGregorianCalendar();
        String urlRedirect = transactionResult.getUrlRedirection();
        String vci = transactionResult.getVCI(); //parametro opcional

        if(!sessionId.equalsIgnoreCase(payment.getPago().getId()+"")){
            LOGGER.warn("Transaccion no corresponde a la de la sesion. sid={}, pid={}",sessionId,payment.getPago().getId());
            this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Id en sesion y notificado no coinciden", "sessionId="+sessionId + ";token="+token + ";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());
            request.getSession().setAttribute("ordenPago", sessionId);
            request.getSession().setAttribute("razonRechazo", "Inconsistencia de datos");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
        }

        WsTransactionDetailOutput transactionDetail = detailsOutput.get(0);


        // si se rechaza se marca como no pagada, se consume el acknowledge de transbank y se redirige
        if(transactionDetail.getResponseCode()!= 0){
            LOGGER.info("Transaccion rechazada por TBK. responsecode={}",transactionDetail.getResponseCode());
            this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Rechazado por Transbank", "sessionId="+sessionId + ";token="+token + ";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());

            try {
                wsPatPass.acknowledgeTransaction(token);
            }catch(Exception e){
                LOGGER.warn("error al invocar a acknowledgeTransaction token {}",token,e);
            }
            request.getSession().setAttribute("ordenPago", sessionId);
            request.getSession().setAttribute("razonRechazo", "Rechazado por Transbank");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            return;
        }


        // si es pago duplicado, no se consume ack
        if(this.pagoDAWS.isPayed(payment.getPago().getId(), request.getRemoteAddr())){
            LOGGER.warn("Pago Duplicado. pid ={} ",payment.getPago().getId());
            this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Pago ya se realizo con exito. Transacción cancelada.", "sessionId="+sessionId + ";token="+token + ";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());
            request.getSession().setAttribute("ordenPago", sessionId);
            request.getSession().setAttribute("razonRechazo", "Pago ya se realizo con exito. Transacción cancelada.");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            return;
        }


//2016-03-15 07:47:46
        payment.getPago().setCodigoAutorizacion(transactionDetail.getAuthorizationCode());
        payment.getPago().setCodigoTransaccion(transactionDetail.getResponseCode()+"");
        payment.getPago().getBotonBoletas().get(0).setCodigoAutorizacion(transactionDetail.getAuthorizationCode());
        payment.getPago().getBotonBoletas().get(0).setExpiracionTarjeta(cardDetail.getCardExpirationDate());
        payment.getPago().getBotonBoletas().get(0).setFechaContable(accountingDate);
        payment.getPago().getBotonBoletas().get(0).setFechaTransaccion(formatoFecha.format(transactionDate.getTime()));
        payment.getPago().getBotonBoletas().get(0).setHoraTransaccion(formatoHora.format(transactionDate.getTime()));
        payment.getPago().getBotonBoletas().get(0).setIdTransaccion(token);

        payment.getPago().getBotonBoletas().get(0).setMonto(transactionDetail.getAmount()==null?null:transactionDetail.getAmount().doubleValue());
        payment.getPago().getBotonBoletas().get(0).setNumeroBoleta(transactionDetail.getBuyOrder());
        payment.getPago().getBotonBoletas().get(0).setMontoCuota(transactionDetail.getSharesAmount()==null?null:transactionDetail.getSharesAmount().doubleValue());
        payment.getPago().getBotonBoletas().get(0).setNumeroCuotas(transactionDetail.getSharesNumber());
        payment.getPago().getBotonBoletas().get(0).setNumeroTarjeta(cardDetail.getCardNumber());
        payment.getPago().getBotonBoletas().get(0).setRespuestaTransaccion(""+transactionDetail.getResponseCode());
        payment.getPago().getBotonBoletas().get(0).setTipoPago(transactionDetail.getPaymentTypeCode());


        //System.out.println("Todo OK, Aceptando notificacion id="+payment.getPago().getId());
        this.pagoDAWS.markAsPayedBotonPago(payment.getPago(), payment.getPago().getBotonConvenio().getId(),  transactionResult.toString(), request.getRemoteAddr());



        //Aceptar transaccion
        try {
            LOGGER.debug("pago valido, llamando a acknowledgeTransaction");
            wsPatPass.acknowledgeTransaction(token);
        }
        catch(Exception e){
            LOGGER.error("error al llamar a acknowledgeTransaction",e);
            this.pagoDAWS.markAsRejected(payment.getPago().getId(), payment.getPago().getBotonConvenio().getId(), "Fallo Acknowledge patpass.", "sessionId="+sessionId + ";token="+token + ";PaymentInSession="+payment.getPago().getId(), request.getRemoteAddr());
            request.getSession().setAttribute("ordenPago", sessionId);
            request.getSession().setAttribute("razonRechazo", "Error de seguridad.");
            request.getRequestDispatcher("PagoRechazado.jsp").forward(request,response);
            return;
        }


        LOGGER.debug("pago realizado exitosamente, redirigiendo a pagina de exito");

        //redirigir al usuario a TBK
        request.getSession().setAttribute("postToUrl", urlRedirect);
        request.getSession().setAttribute("postParamName", "token_ws"); //Era data, se solicita cambio a TX
        request.getSession().setAttribute("postContent", token);

        String target = "_self";
        if( payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True") ){
            target = "_blank";
        }

        request.getSession().setAttribute("PaymentTarget", target);


        request.getRequestDispatcher("/generic/poster.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PatPass notify get");
        response.getWriter().print("Error, Acceso no autorizado");
    }


}
