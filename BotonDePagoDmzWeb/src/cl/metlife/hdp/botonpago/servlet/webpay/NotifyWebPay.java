package cl.metlife.hdp.botonpago.servlet.webpay;

import cl.metlife.hdp.botonpago.dawsclient.*;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * Created by Ivan on 28-07-2014.
 */
public class NotifyWebPay extends GenericNotifyPaymentSupportServlet {
    Map<String, String> paramsMap = new HashMap<String, String>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("RECHAZADO");
    }

    //@Init
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            System.out.println("Recibiendo una notificacion de pago de WebPay");
            String postBodyString = this.getPostBodyString(request);
        try {
            leerParametros(postBodyString);
            int idPago = Integer.parseInt(paramsMap.get("TBK_ORDEN_COMPRA"));
            HttpSession userSession = this.loadSession(request, paramsMap.get("TBK_ID_SESION"), idPago);
            PaymentInfo pinfo = (PaymentInfo) userSession.getAttribute("PaymentInfo");

            System.out.println("Recibida notificacion de WebPay:\n" + postBodyString);
            PrintWriter out = response.getWriter();

            //String codAutorizacion = this.armarCodigoAutorizacion();
            String codTransaccion = paramsMap.get("TBK_ID_TRANSACCION");
            Integer montoPagado = Integer.parseInt(paramsMap.get("TBK_MONTO")) / 100;

            Integer idConvenio = pinfo.getPago().getBotonConvenio().getId();
            String archivoControl = crearArchivo(postBodyString, pinfo.getPago().getBotonConvenio().getLogPath());
            if (!transaccionExitosa()) {
                System.out.println("TRANSACCION RECHAZADO CON RESULTADO != 0, persistiendo como rechazado y respndiendo RECHAZADO.");
                this.pagoDAWS.markAsRejected(idPago, idConvenio, marcarRechazado(), postBodyString, request.getRemoteAddr());
                response.getWriter().print("RECHAZADO");
                return;
            } else {
                if (validarMonto(pinfo, montoPagado)) {
                    if (validarMAC(archivoControl, PaymentSupport.getConfigParam(request, "REMOTE_tbkMacExe"))) {
                        if (!this.pagoDAWS.isPayed(idPago, request.getRemoteAddr())) {
                            //this.pagoDAWS.markAsPayed(idPago, codTransaccion, codAutorizacion, idConvenio, request.getRemoteAddr());
                            //PaymentInfo pinfo = this.llenarBotonPago((PaymentInfo)userSession.getAttribute("PaymentInfo"));
                            pinfo = this.llenarBotonPago(pinfo);
                            pinfo.getPago().setCodigoTransaccion(codTransaccion);
                            System.out.println("Aceptando pago Nº"+idPago);
                            this.pagoDAWS.markAsPayedBotonPago(pinfo.getPago(), idConvenio, postBodyString, request.getRemoteAddr());
                            response.getWriter().print("ACEPTADO");
                        } else {
                            System.out.println("Rechazando pago Nº"+idPago + " Ya pagado.");
                            this.pagoDAWS.markAsRejected(idPago, idConvenio, "Ya pagado", postBodyString, request.getRemoteAddr());
                            response.getWriter().print("RECHAZADO");
                        }
                    } else {
                        System.out.println("Rechazando pago Nº"+idPago + " MAC incorrecto");
                        this.pagoDAWS.markAsRejected(idPago, idConvenio, "MAC incorrecto - No se puede validar la autenticidad del mensaje recibido", postBodyString, request.getRemoteAddr());
                        response.getWriter().print("RECHAZADO");
                    }
                } else {
                    System.out.println("Rechazando pago Nº"+idPago + " Monto pagado no coincide con monto por cobrar.");
                    this.pagoDAWS.markAsRejected(idPago, idConvenio, "Monto pagado no coincide con monto por cobrar", postBodyString, request.getRemoteAddr());
                    response.getWriter().print("RECHAZADO");
                }

            }
        }
        catch (Exception e) {
            System.out.println("Se ha producido en WebPay Notify. Rechazando. El post string es: " + postBodyString);
            e.printStackTrace();
            response.getWriter().print("RECHAZADO");
        }

    }

    private String marcarRechazado(){
        String retorno = "";
        switch (Integer.parseInt(paramsMap.get("TBK_RESPUESTA").trim())){
            case -1:
                retorno = "Rechazo de transacción.";
                break;
            case -2:
                retorno = "Transacción debe reintentarse.";
                break;
            case -3:
                retorno = "Error en transacción.";
                break;
            case -4:
                retorno = "Rechazo de transacción.";
                break;
            case -5:
                retorno = "Rechazo por error de tasa.";
                break;
            case -6:
                retorno = "Excede cupo máximo mensual.";
                break;
            case -7:
                retorno = "Excede límite diario por transacción.";
                break;
            case -8:
                retorno = "Rubro no autorizado.";
                break;
            default:
                retorno = "Transacción Rechazada";
                break;
        }
        return retorno;
    }

    private PaymentInfo llenarBotonPago(PaymentInfo pinfo){
        HashMap<String, BotonBoleta> hmap = new HashMap<String, BotonBoleta>();
        for(BotonBoleta b: pinfo.getPago().getBotonBoletas()){
            hmap.put(b.getNumeroBoleta(),b);
        }
        for(int x = 1; x<=pinfo.getPago().getBotonBoletas().size();x++){
            BotonBoleta boleta = hmap.get(buscarParametro("ORDEN_TIENDA_M00",x));
            if(boleta != null) {
                /*if(boleta.getBotonBoletaInfoPagos().get(0) == null){
                    boleta.getBotonBoletaInfoPagos().add(new BotonBoletaInfoPago());
                }*/
                //boleta.getBotonBoletaInfoPagos().get(0).setMonto(buscarParametro("MONTO_TIENDA_M00", x));
                boleta.setCodigoAutorizacion(buscarParametro("COD_AUT_M00", x));
                boleta.setFechaTransaccion(paramsMap.get("TBK_FECHA_TRANSACCION"));
                boleta.setHoraTransaccion(paramsMap.get("TBK_HORA_TRANSACCION"));
                boleta.setFechaContable(paramsMap.get("TBK_FECHA_CONTABLE"));
                boleta.setNumeroTarjeta(paramsMap.get("TBK_FINAL_NUMERO_TARJETA"));
                boleta.setRespuestaTransaccion(buscarParametro("COD_RESP_M00",x));
                boleta.setTipoPago(buscarParametro("TIPO_PAGO_M00",x));
                boleta.setFechaContable(paramsMap.get("TBK_FECHA_CONTABLE"));
                boleta.setIdTransaccion(paramsMap.get("TBK_ID_TRANSACCION"));


                String fechaExpiracion = paramsMap.get("TBK_FECHA_EXPIRACION");
                String montoCuota = buscarParametro("MONTO_CUOTA_M00",x);
                String numCuotas = buscarParametro("NUMERO_CUOTAS_M00",x);

                if(fechaExpiracion==null) {
                    fechaExpiracion = "";
                }
                if(montoCuota==null || montoCuota.equals("")) {
                    montoCuota = "0";
                }
                if(numCuotas==null) {
                    numCuotas="0";
                }

                boleta.setExpiracionTarjeta(fechaExpiracion);
                boleta.setMontoCuota(Double.parseDouble(montoCuota));
                boleta.setNumeroCuotas(Integer.parseInt(numCuotas));

            }
        }
        return pinfo;
    }

    public String buscarParametro(String param, Integer posicion){
        Iterator iter = paramsMap.entrySet().iterator();
        Map.Entry e;
        while (iter.hasNext()) {
            e = (Map.Entry) iter.next();
            if (e.getKey().toString().contains(param+posicion)) {
                return e.getValue().toString();
            }
        }
        return "";
    }

    private boolean validarMonto(PaymentInfo pago, Integer montoPagado) {
        Double montoTotal = pago.getPago().getMontoTotal();
        if(montoTotal.intValue() == montoPagado.intValue()) {
            return true;
        }
        else {
            return false;
        }
    }

    private void leerParametros(String input) {
        paramsMap = new HashMap<String, String>();
        String[] post = input.split("&");

        for(String s: post){
            String[] parametros = s.split("=");
            paramsMap.put(parametros[0],parametros[1]);
        }
    }

    public String armarCodigoAutorizacion(){
        Iterator iter = paramsMap.entrySet().iterator();
        Map.Entry e;
        String codigoAutorizacion = "";
        while (iter.hasNext()) {
            e = (Map.Entry) iter.next();
            if (e.getKey().toString().contains("TBK_COD_AUT_M")) {
                codigoAutorizacion +=  paramsMap.get(e.getKey().toString());
                codigoAutorizacion += ";";
            }

        }

        return codigoAutorizacion;
    }

    public String crearArchivo(String requestString, String logPath) {
        String ordenCompra = paramsMap.get("TBK_ORDEN_COMPRA");
        int idPago = Integer.parseInt(ordenCompra);

        String pathArchivoControl = escribirArchivo(requestString, idPago, logPath);
        return pathArchivoControl;
    }

    public String escribirArchivo(String contenido, int idPago, String logPath) {
        String pathFiles = logPath;
        try {
            File archivoDeControl = new File(pathFiles + idPago + ".txt");
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

    public boolean transaccionExitosa() {
        //validar transaccion
        if(  paramsMap.get("TBK_RESPUESTA").trim().compareTo("0") != 0 ) {
            return false;
        }
        //validar cada una de las boletas
        for(int i = 1; i<=6; i++) {
            String respBoleta = paramsMap.get("TBK_COD_RESP_M00"+i);
            if(respBoleta != null && respBoleta.trim().compareTo("")!=0) {
                if( respBoleta.trim().compareTo("0") !=0 ){
                    return false;
                }
            }

        }

        return true;
    }

    private boolean validarMAC(String paramFilePath, String tbkMacExe) {
        String s = null;
        boolean result = false;
        try {
            String comando = tbkMacExe + " " +  paramFilePath;
            Process p = Runtime.getRuntime().exec(comando);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                if (s.toUpperCase().compareTo("CORRECTO") == 0) {
                    return true;
                } else if (s.toUpperCase().compareTo("INVALIDO") == 0) {
                    return false;
                }
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("Ocurrio un problema");
            e.printStackTrace();
            return false;
        }

        return false;
    }


}
