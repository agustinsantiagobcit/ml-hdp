package cl.metlife.hdp.botonpago.servlet.webpay;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ivan on 28-07-2014.
 */
public class InicioPagoWebPay extends HttpServlet {

    PaymentSupport util = new PaymentSupport();
    boolean debug = false;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Forbidden");
    }

    private String buildPostString(Map<String, String> params) {
        String postString = "";
        Set<String> names = params.keySet();
        boolean first = true;
        for(String key: names) {
            if(!first) {
                postString += "&";
            } else {
                first = false;
            }

            postString += key + "=" + params.get(key);
        }
        return postString;
    }

    private Map<String, String> buildPaymentParameters(int id, PaymentInfo payment, String idSesion, String urlExitoBaseWebPay, String urlFracasoBaseWebPay) throws PaymentException {

        List<BotonBoleta> boletas = payment.getPago().getBotonBoletas();
        int counter = 0;
        if(boletas.size()>6){
            throw new PaymentException("Transbank solo soporta hasta 6 boletas, se recibieron "+ boletas.size());
        }

        String vOK = PaymentSupport.generarV(payment.getPago(), "exito");
        String vNOK = PaymentSupport.generarV(payment.getPago(), "fracaso");

        String urlExito = "";
        String urlFracaso = "";
        try {
            urlExito = urlExitoBaseWebPay + "?status=exito&tid=" + id+"&v="+vOK;
            urlFracaso =  urlFracasoBaseWebPay+"?status=fracaso&tid="+id+"&v="+vNOK;
            if(!debug) {
                URLEncoder.encode(urlExito, "UTF-8");
                URLEncoder.encode(urlFracaso, "UTF-8");

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new PaymentException("Couldn't encode Exit URL");
        }


        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("TBK_TIPO_TRANSACCION" , "TR_MALL");
        paramsMap.put("TBK_MONTO" , payment.getPago().getMontoTotal().intValue()*100 + "");
        paramsMap.put("TBK_ORDEN_COMPRA" , id+"");
        paramsMap.put("TBK_ID_SESION" , idSesion);
        paramsMap.put("TBK_URL_RESULTADO" , urlExito);
        paramsMap.put("TBK_URL_FRACASO" , urlFracaso);
        paramsMap.put("TBK_NUM_TRX" , boletas.size()+"");

        for(BotonBoleta b: boletas) {
            counter++;
            paramsMap.put("TBK_CODIGO_TIENDA_M00" + counter , util.getAgreementNumber("WebPay", payment));
            paramsMap.put("TBK_ORDEN_TIENDA_M00" + counter , b.getNumeroBoleta());
            paramsMap.put("TBK_MONTO_TIENDA_M00" + counter , (b.getMonto().intValue()*100)+"");
        }


        return paramsMap;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.debug = Boolean.parseBoolean(util.getEnviromentConfig("PagoDebug", this.getServletContext()));

        int id = Integer.parseInt(request.getParameter("tid"));
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("WebPay", payment));

            String urlFinalizadoWebPay = PaymentSupport.getConfigParam(request, "REMOTE_pagoFinUrlWebPay");
            Map<String, String> params = null;
            try {
                params = buildPaymentParameters(id, payment, request.getSession().getId(), urlFinalizadoWebPay, urlFinalizadoWebPay);
            } catch (PaymentException e) {
                throw new ServletException(e.getCause());
            }
            String urlParameters = buildPostString(params);
            String cgiUrl = PaymentSupport.getConfigParam(request, "REMOTE_tbk_bp_pago.cgi");

            if(!debug) {
                /*URL url = new URL(cgiUrl);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(urlParameters);
                writer.flush();

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    response.getWriter().println(line);
                    //System.out.println(line);
                }
                writer.close();
                reader.close();*/

                System.out.println("Iniciando pago con WebPay. XML=\n" + urlParameters);

                String remoteUrl = cgiUrl;

                request.getSession().setAttribute("postToUrl", remoteUrl);
                request.getSession().setAttribute("postMapContent", params);
                request.getSession().setAttribute("postMapKeys", params.keySet());


                String target = "_self";
                if (payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True")) {
                    target = "_blank";
                }

                request.getRequestDispatcher("/generic/mapPoster.jsp").forward(request, response);
            }
            else {
                System.out.println("Iniciando pago con WebPay. XML=\n" + urlParameters);

                String remoteUrl = cgiUrl;

                request.getSession().setAttribute("postToUrl", remoteUrl);
                request.getSession().setAttribute("postMapContent", params);
                request.getSession().setAttribute("postMapKeys", params.keySet());

                String target = "_self";
                if (payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True")) {
                    target = "_blank";
                }

                request.getRequestDispatcher("/WebpayDebug/poster.jsp").forward(request, response);
            }
        }
        else {
            response.getWriter().print("Error. ID no coincide");
            //PaymentInfo payment = new PaymentInfo();
        }
    }
}
