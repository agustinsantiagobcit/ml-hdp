package cl.metlife.hdp.botonpago.utils;

import cl.metlife.hdp.botonpago.dawsclient.*;
import com.sun.deploy.net.HttpRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ivan on 31-07-2014.
 */
public class PaymentSupport {
   // private static final Logger logger = LogManager.getLogger(PaymentSupport.class);

    public String getAgreementId(String nombreInstitucion, PaymentInfo p) {
        List<BotonConvenio> convenios = p.getConvenios();
        for(BotonConvenio convenio : convenios) {
            if(convenio.getBotonInstitucionesPago().getNombre().equalsIgnoreCase(nombreInstitucion) ) {
                return convenio.getNumeroConvenio();
            }
        }

        return "0";
    }

    public String getAgreementNumber(String nombreInstitucion, PaymentInfo p) {
        List<BotonConvenio> convenios = p.getConvenios();
        for(BotonConvenio convenio : convenios) {
            if(convenio.getBotonInstitucionesPago().getNombre().equalsIgnoreCase(nombreInstitucion) ) {
                return convenio.getNumeroConvenio();
            }
        }

        return "000000";
    }

    public String getAgreementConfigPath(String nombreInstitucion, PaymentInfo p) {
        List<BotonConvenio> convenios = p.getConvenios();
        for(BotonConvenio convenio : convenios) {
            if(convenio.getBotonInstitucionesPago().getNombre().equalsIgnoreCase(nombreInstitucion) ) {
                return convenio.getConfigPath();
            }
        }

        return "000000";
    }


    public PagoDAWS getPagoDawsReference(String wsdlUrl) {
        String wsdlLocation = wsdlUrl;//;
        URL baseUrl;
        baseUrl = cl.metlife.hdp.botonpago.dawsclient.PagoDAWS_Service.class.getResource(".");
        PagoDAWS_Service locator = null;
        URL url = null;
        System.out.println("Getting PagoDAWS client instance: " + wsdlLocation);

        try {
            url = new URL(baseUrl, wsdlLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QName qn = new QName("http://daws.botonpago.hdp.metlife.cl/", "PagoDAWS");
        locator = new PagoDAWS_Service(url,  qn);


        return locator.getPagoDAWS();
    }

    public static String md5(String clear){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(clear.getBytes());
            int size = b.length;
            StringBuffer h = new StringBuffer(size);
            for (int i = 0; i < size; i++) {
                int u = b[i] & 255;
                if (u < 16) {
                    h.append("0" + Integer.toHexString(u));
                } else {
                    h.append(Integer.toHexString(u));
                }
            }
            result = h.toString();
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static String generarV(BotonPago pago){
        String v ="";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        v = "TOKEN" + pago.getId() +
                "PAGAR" + pago.getMontoTotal() +
                "CREADO" + sdf.format(pago.getHoraCreacion().toGregorianCalendar().getTime()) +
                "LINEA" + pago.getBotonLineasDeNegocio().getId() +
                "RUT" + pago.getClienteBean().getRutCliente() +
                "Blueprints IT es la mejor empresa del mundo" ;

        return md5(md5(v));
    }

    public static String generarV(BotonPago pago, String estado){
        String v ="";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        v = "TOKEN" + pago.getId() +
            "PAGAR" + pago.getMontoTotal() +
            "CREADO" + sdf.format(pago.getHoraCreacion().toGregorianCalendar().getTime()) +
            "LINEA" + pago.getBotonLineasDeNegocio().getId() +
            "RUT" + pago.getClienteBean().getRutCliente() +
            "ESTADO" + estado +
            "Blueprints IT es la mejor empresa del mundo" ;

        return md5(md5(v));
    }

    public static boolean verificarV(String v, BotonPago pago, String estado){
        if(v==null){
            return false;
        }

        boolean resp=false;
        String vGenerado = generarV(pago, estado);
        if (vGenerado.compareTo(v)==0){
            resp = true;
        }

        return resp;
    }

    public static boolean verificarV(String v, BotonPago pago){
        if(v==null){
            return false;
        }

        boolean resp=false;
        String vGenerado = generarV(pago);
        if (vGenerado.compareTo(v)==0){
            resp = true;
        }

        return resp;
    }

    public static void loadConfig(HttpServletRequest req, PagoDAWS pws) {
        ServletContext ctx = req.getSession().getServletContext();
        HashMap<String, ConfiguracionesApp> configs = (HashMap<String, ConfiguracionesApp>)ctx.getAttribute("APP_CONFIG");

        /**/

        if(configs == null){
            List<ConfiguracionesApp> configList = pws.loadConfig();
            HashMap<String, ConfiguracionesApp> configMap = new HashMap<String, ConfiguracionesApp>();

            for(ConfiguracionesApp c: configList) {
                configMap.put(c.getNombreParametroApp(), c);
            }

            ctx.setAttribute("APP_CONFIG", configMap);
        }

        return;
    }

    public static void reloadConfig(HttpServletRequest req, PagoDAWS pws) {
        ServletContext ctx = req.getSession().getServletContext();

        List<ConfiguracionesApp> configList = pws.loadConfig();
        HashMap<String, ConfiguracionesApp> configMap = new HashMap<String, ConfiguracionesApp>();

        for(ConfiguracionesApp c: configList) {
            configMap.put(c.getNombreParametroApp(), c);
        }

        ctx.setAttribute("APP_CONFIG", configMap);

        return;
    }

    public static String getConfigParam(HttpServletRequest req, String paramName) {
        /*
        Obtiene el parametro de configuración por su nombre. Estos parámetros se leen desde la base de datos a
        través del pagoDAWS y se dejan en cache para mejor performance. Esta función accede a dicho cache.
         */
        ServletContext ctx = req.getSession().getServletContext();
        HashMap<String, ConfiguracionesApp> configs = (HashMap<String, ConfiguracionesApp>)ctx.getAttribute("APP_CONFIG");

        try {
            String value = configs.get(paramName).getValorParametroApp();
            System.out.println("Getting parameter " + paramName + "=" + value + ";");
            return value;

        }
        catch (Exception e) {
            System.out.println("Getting parameter " + paramName + " NOT FOUND");
            return null;
        }

    }

    public static String obtenerTipoCuota(String tipoPago){
        String tipoCuotas = "";
        if("VD".equals(tipoPago)){
            tipoCuotas ="Venta Débito";
        }
        if("VN".equals(tipoPago)){
            tipoCuotas ="Sin Cuotas";
        }
        if("VC".equals(tipoPago)){
            tipoCuotas ="Normales";
        }
        if("SI".equals(tipoPago)){
            tipoCuotas ="Sin Interés";
        }
        if("S2".equals(tipoPago)){
            tipoCuotas ="Sin Interés";
        }
        if("CI".equals(tipoPago)){
            tipoCuotas ="Cuotas Comercio";
        }
        return tipoCuotas;
    }

    public static String obtenerTipoPago(String tp){
        String tipoPago = "";
        if("VD".equals(tp)){
            tipoPago = "Redcompra";
        }
        else{
            tipoPago = "Crédito";
        }
        return tipoPago;
    }

    public static String writeFile(String contenido, String nombreArchivo, String carpeta) {
        String pathFiles = carpeta;
        try {
            File archivoDeControl = new File(pathFiles + nombreArchivo + ".txt");
            FileOutputStream is = new FileOutputStream(archivoDeControl);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(contenido);
            w.close();




            return archivoDeControl.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void putInSession(HttpServletRequest request, String idSession, String nombre, Object value){
        HttpSession sesion;
        sesion = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+idSession);
        sesion.setAttribute(nombre,value);

    }

    public Object getFromSession(HttpServletRequest request, String idSession, String nombre){
        HttpSession sesion;
        sesion = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+idSession);
        return sesion.getAttribute(nombre);
    }

    public String getEnviromentConfig(String paramName, ServletContext context) {
        String prod = context.getInitParameter("production");
        if(prod == null) {
            prod = "true";
        }

        if(prod.compareToIgnoreCase("True") == 0 ) {
            return System.getProperty(paramName);
        }

        return context.getInitParameter(paramName);
    }


    public BotonConvenio getConvenio(int idConvenio, PaymentInfo payment) {
        List<BotonConvenio> convenios = payment.getConvenios();
        for(BotonConvenio c : convenios) {
            if(c.getId() == idConvenio){
                return c;
            }
        }

        return null;
    }

    public String postToURL(String httpsUrl, String parameterName, String content) throws IOException {
        URL myurl = new URL(httpsUrl);

        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-length", String.valueOf(content.length()));
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());

        String query = parameterName + "=" + URLEncoder.encode(content, "ISO-8859-1");
        output.writeBytes(query);

        output.close();

        DataInputStream input = new DataInputStream( con.getInputStream() );


        String response = "";
        for( int c = input.read(); c != -1; c = input.read() ) {
            response += (char) c;
        }
        input.close();

        //System.out.println("Resp Code:"+con .getResponseCode());
        //System.out.println("Resp Message:"+ con.getResponseMessage());
        return response;
    }

    public String removeAccents(String input) {
        String out = input.replaceAll("á","a");
        out = out.replaceAll("é","e");
        out = out.replaceAll("í","i");
        out = out.replaceAll("ó","o");
        out = out.replaceAll("ú","u");
        out = out.replaceAll("Á","A");
        out = out.replaceAll("É","E");
        out = out.replaceAll("Í","I");
        out = out.replaceAll("Ó","O");
        out = out.replaceAll("Ú","U");
        out = out.replaceAll("Ñ","N");
        out = out.replaceAll("ñ","n");

        return out;
    }
}
