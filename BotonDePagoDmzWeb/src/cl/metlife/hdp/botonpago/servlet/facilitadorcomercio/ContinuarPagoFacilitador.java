package cl.metlife.hdp.botonpago.servlet.facilitadorcomercio;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import cl.metlife.hdp.botonpago.utils.xml.XmlProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.writeFile;

/**
 * Created by Ivan on 28-07-2014.
 */
public class ContinuarPagoFacilitador extends HttpServlet {

    PaymentSupport util = new PaymentSupport();
    boolean debug = false;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Forbidden");
    }


    private String externalPostToUrl(String command, String url, String paramName, String paramValue) {

        String s = null;
        String result = "";
        try {
            Process p = Runtime.getRuntime().exec(command + " " + url + " " + paramName);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedWriter stdOut = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            stdOut.append(paramValue);
            stdOut.close();

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                result += s;
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("Ocurrio un problema");
            e.printStackTrace();
            return "";
        }

        return result;
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tokenRequestXml = ""+
                "<TBKPatData>" +
                    "<Perfil>" +
                        "<NombreUsuario>${UserName}</NombreUsuario>" +
                        "<ContrasenaUsuario>${UserPass}</ContrasenaUsuario>" +
                        "<RutUsuario>${UserRut}</RutUsuario>" +
                    "</Perfil>" +
                    "<DuracionToken>${TokenDuration}</DuracionToken>" +
                    "<DatosComercio>" +
                        "<CodigoComercio>${CODIGOCOMERCIO}</CodigoComercio>" +
                        "<DataAuditoriaComercio>${NOMBRECOMERCIO}</DataAuditoriaComercio>" +
                    "</DatosComercio>" +
                    "<DatosServicios>" +
                        "<IdServicio>${IDSERVICIO}</IdServicio>" +
                    "</DatosServicios>" +
                    "<IP>${IP}</IP>" +
                "</TBKPatData>";



        int id = Integer.parseInt(request.getParameter("tid"));
        String rutTh = request.getParameter("rutTh");
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("FacilitadorComercio", payment));

            String[] identificacion = payment.getPago().getBotonConvenio().getLlave().split("#");
            if(identificacion.length != 2) {
                response.getWriter().print("Error. Configuracion incorrecta. Usuario invalido.");
                return;
            }

            String username = identificacion[0];
            String password = identificacion[1];
            String userRut = rutTh.trim();//.replaceAll(".","").replaceAll(" ","");
            String duracionToken = PaymentSupport.getConfigParam(request, "REMOTE_FacilitadorVidaTokenS"); //en segundos
            String codigoComercio = payment.getPago().getBotonConvenio().getNumeroConvenio();
            String nombreComercio = payment.getPago().getBotonConvenio().getNombreComercio();
            String idServicio = payment.getPago().getBotonBoletas().get(0).getNumeroBoleta();
            String ip = PaymentSupport.getConfigParam(request, "REMOTE_ServerIp"); //request.getRemoteAddr();

            String urlSolicitudToken = payment.getPago().getBotonConvenio().getConfigPath();
            String urlFrameTransbank = payment.getPago().getBotonConvenio().getUrlInicioPago();

            tokenRequestXml = tokenRequestXml.replace("${UserName}", username);
            tokenRequestXml = tokenRequestXml.replace("${UserPass}", password);
            tokenRequestXml = tokenRequestXml.replace("${UserRut}", userRut);
            tokenRequestXml = tokenRequestXml.replace("${TokenDuration}", duracionToken);
            tokenRequestXml = tokenRequestXml.replace("${CODIGOCOMERCIO}", codigoComercio);
            tokenRequestXml = tokenRequestXml.replace("${NOMBRECOMERCIO}", nombreComercio);
            tokenRequestXml = tokenRequestXml.replace("${IDSERVICIO}", idServicio);
            tokenRequestXml = tokenRequestXml.replace("${IP}", ip);

            writeFile(tokenRequestXml, id+"_xml00", payment.getPago().getBotonConvenio().getLogPath());

            String tokenResponseXml = "";
            try {
                writeFile(tokenResponseXml, id+"_xml01", payment.getPago().getBotonConvenio().getLogPath());

                //tokenResponseXml = util.postToURL(urlSolicitudToken, "TBKPatData" ,tokenRequestXml);

                String javaPath = PaymentSupport.getConfigParam(request, "REMOTE_JavaBinPath");
                String classDir = PaymentSupport.getConfigParam(request, "REMOTE_FacilitadorPosterClasspath");
                String className = PaymentSupport.getConfigParam(request, "REMOTE_FacilitadorPosterClassname");

                String command = javaPath + " -classpath " + classDir + " " + className;
                tokenResponseXml = externalPostToUrl(command, urlSolicitudToken,  "TBKPatData" ,tokenRequestXml);

                XmlProcessor xmlProcessor = new XmlProcessor();
                String codRet = xmlProcessor.getValueFromXml(tokenResponseXml,"/MENSAJE/Coderror");
                String desc = xmlProcessor.getValueFromXml(tokenResponseXml,"/MENSAJE/Descerror");
                String token = xmlProcessor.getValueFromXml(tokenResponseXml,"/MENSAJE/Token");

                if(codRet.compareTo("0") != 0) {
                    response.getWriter().print("Error al tratar de conectarse con Transbank. \nError al obtener token.\n"+desc);
                    return;
                }

                String urlNotifyFacilitador = PaymentSupport.getConfigParam(request, "REMOTE_Facilitador_Notify");
                Map<String, String> params = new HashMap<String, String>();
                //params.put("token", token);
                params.put("tokenComercio", token);
                params.put("direccionRegreso", urlNotifyFacilitador + "?res=OK&tid=" + id + "&v=" + util.md5("OK" + id + "TOKEN"));
                params.put("direccionError",  urlNotifyFacilitador + "?res=NOK&tid=" + id + "&v=" + util.md5("NOK" + id + "TOKEN"));

                request.getSession().setAttribute("postToUrl", urlFrameTransbank);
                request.getSession().setAttribute("postMapContent", params);
                request.getSession().setAttribute("postMapKeys", params.keySet());

                String target = "_self";
                if (payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True")) {
                    target = "_blank";
                }



                //request.getRequestDispatcher("/generic/DelayedPoster.jsp").forward(request, response);
                request.getSession().setAttribute("PaymentInfo", payment);
                request.getRequestDispatcher("/generic/ClicPosterIframe.jsp").forward(request, response);
                //request.getRequestDispatcher("/generic/ClicPoster.jsp").forward(request, response);
                //request.getRequestDispatcher("/WebpayDebug/poster.jsp").forward(request, response);


            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            response.getWriter().print("Error. ID no coincide");
            //PaymentInfo payment = new PaymentInfo();
        }
    }
}
