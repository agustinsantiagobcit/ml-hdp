/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;

import java.util.List;

/**
 *
 * @author Ggonzalez
 */
public class XML1 {

    public String Firma;
    public String CodigoCanalPago;
    public String IdTxCliente;
    public String MailCliente;//<EmailCliente>ed@factoryweb.cl</EmailCliente>
    public String NombreCliente; //<NombreCliente>FH FH FH</NombreCliente>
    public String RutCliente; //<RutCliente>1-9</RutCliente>
    public String FechaPago;
    public String MontoTotalDeuda;
    public String NumeroBoletas;
    public String Version;
    private List<NodosXML1Detalle> SolicitudPagoDetalle;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public List<NodosXML1Detalle> getSolicitudPagoDetalle() {
        return SolicitudPagoDetalle;
    }

    public void setSolicitudPagoDetalle(List<NodosXML1Detalle> solicitudPagoDetalle) {
        SolicitudPagoDetalle = solicitudPagoDetalle;
    }

    public String getMailCliente() {
        return MailCliente;
    }

    public void setMailCliente(String mailCliente) {
        MailCliente = mailCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getRutCliente() {
        return RutCliente;
    }

    public void setRutCliente(String rutCliente) {
        RutCliente = rutCliente;
    }

    public String getFirma() {
        return Firma;
    }

    public void setFirma(String Firma) {
        this.Firma = Firma;
    }

    public String getCodigoCanalPago() {
        return CodigoCanalPago;
    }

    public void setCodigoCanalPago(String CodigoCanalPago) {
        this.CodigoCanalPago = CodigoCanalPago;
    }

    public String getIdTxCliente() {
        return IdTxCliente;
    }

    public void setIdTxCliente(String IdTxCliente) {
        this.IdTxCliente = IdTxCliente;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String FechaPago) {
        this.FechaPago = FechaPago;
    }

    public String getMontoTotalDeuda() {
        return MontoTotalDeuda;
    }

    public void setMontoTotalDeuda(String MontoTotalDeuda) {
        this.MontoTotalDeuda = MontoTotalDeuda;
    }

    public String getNumeroBoletas() {
        return NumeroBoletas;
    }

    public void setNumeroBoletas(String NumeroBoletas) {
        this.NumeroBoletas = NumeroBoletas;
    }

    public String GeneraXml1(String configPath) {
        Variables cargaVariables = new Variables();
        cargaVariables.CargarVariables(configPath);
        Log log = new Log();
        String XmlEnvio = "";
        String Firma = "";
        String retornoArray[][];
        String cadenaHeader = "";
        String compare = "";
        int i,x,j;
        try {
            BuscaNodos buscar = new BuscaNodos();
            retornoArray = buscar.leer(Variables.nodo_XML1);

            for (i = 0; i < retornoArray.length; i++) {
                compare = retornoArray[i][1];
                log.genera("Clase XML1 - Funcion GeneraXML1() " + "\nNodos a Firmar: " + compare + "\n",  Variables.rutaLog, Variables.nombre_Archivo_Log_XML1);
                   
                   if( compare.equals("CodigoCanalPago"))
                   {
                        cadenaHeader = cadenaHeader + this.CodigoCanalPago;
                   }
                   else if (compare.equals("IdTxCliente")){
                        cadenaHeader = cadenaHeader + this.IdTxCliente;
                   }
                   else if (compare.equals("FechaPago")){
                        cadenaHeader = cadenaHeader + this.FechaPago;
                   }
                   else if (compare.equals("MontoTotalDeuda")){
                        cadenaHeader = cadenaHeader + this.MontoTotalDeuda;
                        }
                   else if (compare.equals("NumeroBoletas")){
                       cadenaHeader = cadenaHeader + this.NumeroBoletas;
                   }
                   
                for (j = 0; j < Integer.parseInt(this.NumeroBoletas); j++) {
                    
                   if(compare.equals("IdSubTrx")){
                            cadenaHeader = cadenaHeader + this.SolicitudPagoDetalle.get(j).IdSubTrx;
                            }
                   else if (compare.equals("CodigoIdentificador")){
                            cadenaHeader = cadenaHeader + this.SolicitudPagoDetalle.get(j).CodigoIdentificador;
                            }
                   else if (compare.equals("Boleta")){
                            cadenaHeader = cadenaHeader + this.SolicitudPagoDetalle.get(j).Boleta;
                            }
                   else if (compare.equals("Monto")){
                            cadenaHeader = cadenaHeader + this.SolicitudPagoDetalle.get(j).Monto;
                            }
                   else if (compare.equals("FechaVencimiento")){
                            cadenaHeader = cadenaHeader + this.SolicitudPagoDetalle.get(j).FechaVencimiento;
                   }
                    
                }
            }
            
            /*************GENERACION FIRMA*******************/
           log.genera("Clase XML1 - Funcion GeneraXML1() " + "\nDatos a Firmar : " + cadenaHeader + "\n",  Variables.rutaLog, Variables.nombre_Archivo_Log_XML1);
            Firma = Crypto.GeneraFirma(cadenaHeader, Variables.rutaLlavePrivada);
            //SOLO TEST, ELIMINAR ESTO:
            //////////
            //System.out.println("Al validar la firma recien creada obtenemos:" + Crypto.checkSign(cadenaHeader, Firma, "C:/PRODUCCION/Servipag/Colectivos/keys/met_colect_pub.key"));


            /////////
           log.genera("Clase XML1 - Funcion GeneraXML1()" + "\nFirma Generada: " + Firma + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML1);
            /********************FIN*************************/
 
            XmlEnvio = "<?xml version='1.0' encoding='ISO-8859-1'?>\n";
            XmlEnvio += "<Servipag>\n<Header>\n";
            XmlEnvio += "<FirmaEPS>" + Firma + "</FirmaEPS>\n";
            XmlEnvio += "<CodigoCanalPago>"+this.CodigoCanalPago+"</CodigoCanalPago>\n";
            XmlEnvio += "<IdTxPago>"+this.IdTxCliente+"</IdTxPago>\n";
            XmlEnvio += "<EmailCliente>" + this.MailCliente + "</EmailCliente>\n";
            XmlEnvio += "<NombreCliente>" + this.NombreCliente + "</NombreCliente>\n";
            XmlEnvio += "<RutCliente>" + this.RutCliente + "</RutCliente>\n";
            XmlEnvio += "<FechaPago>"+this.FechaPago+"</FechaPago>\n";
            XmlEnvio += "<MontoTotalDeuda>"+this.MontoTotalDeuda+"</MontoTotalDeuda>\n";
            XmlEnvio += "<NumeroBoletas>"+this.NumeroBoletas+"</NumeroBoletas>\n";
            XmlEnvio += "<Version>2</Version>\n";
            XmlEnvio += "</Header>\n";

            for(x=0;x<Integer.parseInt(this.NumeroBoletas);x++)
            {
               XmlEnvio += "<Documentos>\n<IdSubTx>"+this.SolicitudPagoDetalle.get(x).IdSubTrx+"</IdSubTx>\n";
               XmlEnvio += "<Identificador>"+this.SolicitudPagoDetalle.get(x).CodigoIdentificador+"</Identificador>\n";
               XmlEnvio += "<Boleta>"+this.SolicitudPagoDetalle.get(x).Boleta+"</Boleta>\n";
               XmlEnvio += "<Monto>"+this.SolicitudPagoDetalle.get(x).Monto+"</Monto>\n";
               XmlEnvio += "<FechaVencimiento>"+this.SolicitudPagoDetalle.get(x).FechaVencimiento+"</FechaVencimiento>\n</Documentos>\n";
            }
            XmlEnvio += "</Servipag>";
            
            log.genera("Clase XML1 - Funcion GeneraXML1() " + "\nXML1 Generado: " + XmlEnvio + "\n",  Variables.rutaLog, Variables.nombre_Archivo_Log_XML1); 
            
        } catch (Exception _e) {
            log.genera("Clase XML1 - Funcion GeneraXML1() " + "\nError en la creacion del XML1 :" + _e.getMessage() + "\n",  Variables.rutaLog, Variables.nombre_Archivo_Log_XML1); 
            XmlEnvio = "-1";
        }
        return XmlEnvio;
    }
}
