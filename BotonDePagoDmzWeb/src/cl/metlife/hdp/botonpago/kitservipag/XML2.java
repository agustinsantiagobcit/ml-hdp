/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import org.xml.sax.InputSource;

/**
 *
 * @author Ggonzalez
 */
public class XML2 {
    public String firmaServipag;
    public String idTrxServipag;
    public String idTxCliente;
    public String fechaPago;
    public String codMedioPago;
    public String fechaContable;
    public String codigoIdentificador;
    public String boleta;
    public String monto;
    
    public String getFirmaServipag() {
        return firmaServipag;
    }
    public void setFirmaServipag(String firmaServipag) {
        this.firmaServipag = firmaServipag;
    }
    
    public String getIdTrxServipag() {
        return idTrxServipag;
    }
    public void setIdTrxServipag(String idTrxServipag) {
        this.idTrxServipag = idTrxServipag;
    }
    
    public String getIdTxCliente() {
        return idTxCliente;
    }
    public void setIdTxCliente(String idTxCliente) {
        this.idTxCliente = idTxCliente;
    }    
    
    public String getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public String getCodMedioPago() {
        return codMedioPago;
    }
    public void setCodMedioPago(String codMedioPago) {
        this.codMedioPago = codMedioPago;
    }
    
    public String getFechaContable() {
        return fechaContable;
    }
    public void setFechaContable(String fechaContable) {
        this.fechaContable = fechaContable;
    }
    
    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }
    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }
    
    public String getBoleta() {
        return boleta;
    }
    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }
    
    public String getMonto() {
        return monto;
    }
    public void setMonto(String monto) {
        this.monto = monto;
    }
    
    public boolean ValidaFirmaXML2(String Xml2Entrada, String configPath)
    {
        Variables cargaVariables = new Variables();
        cargaVariables.CargarVariables(configPath);
        String retornoArray[][];
        boolean retorno;
        Log log = new Log();
        try
        {
            int i = 0;
            String compare = "";
            String cadenaNodos = "";
            String firma = "";

            Document document = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(Xml2Entrada)));
            
            NodeList listXml2 = document.getElementsByTagName("Servipag");
             for(int s=0; s<listXml2.getLength() ; s++)
             {
                Node primerRegistroServipag = listXml2.item(s);
                if(primerRegistroServipag.getNodeType() == Node.ELEMENT_NODE){

                    Element primerElementoServipag = (Element)primerRegistroServipag;

                    //-------
                    NodeList FirmaServipagList = primerElementoServipag.getElementsByTagName("FirmaServipag");
                    Element FirmaServipagElement = (Element)FirmaServipagList.item(0);
                    NodeList FirmaServipagText = FirmaServipagElement.getChildNodes();
                    this.firmaServipag = ((Node)FirmaServipagText.item(0)).getNodeValue().trim();
                    
                    //-------
                    NodeList idTrxServipagList = primerElementoServipag.getElementsByTagName("IdTrxServipag");
                    Element idTrxServipagElement = (Element)idTrxServipagList.item(0);
                    NodeList idTrxServipagText = idTrxServipagElement.getChildNodes();
                    this.idTrxServipag = ((Node)idTrxServipagText.item(0)).getNodeValue().trim();

                    //-------
                    NodeList idTxClienteList = primerElementoServipag.getElementsByTagName("IdTxCliente");
                    Element idTxClienteElement = (Element)idTxClienteList.item(0);
                    NodeList idTxClienteText = idTxClienteElement.getChildNodes();
                    this.idTxCliente = ((Node)idTxClienteText.item(0)).getNodeValue().trim();

                    //----
                    NodeList fechaPagoList = primerElementoServipag.getElementsByTagName("FechaPago");
                    Element fechaPagoElement = (Element)fechaPagoList.item(0);
                    NodeList fechaPagoText = fechaPagoElement.getChildNodes();
                    this.fechaPago = ((Node)fechaPagoText.item(0)).getNodeValue().trim();

                    //------
                     
                    NodeList codMedioPagoList = primerElementoServipag.getElementsByTagName("CodMedioPago");
                    Element codMedioPagoElement = (Element)codMedioPagoList.item(0);
                    NodeList codMedioPagoText = codMedioPagoElement.getChildNodes();
                    this.codMedioPago = ((Node)codMedioPagoText.item(0)).getNodeValue().trim();

                    //------
                    
                    NodeList fechaContableList = primerElementoServipag.getElementsByTagName("FechaContable");
                    Element fechaContableElement = (Element)fechaContableList.item(0);
                    NodeList fechaContableText = fechaContableElement.getChildNodes();
                    this.fechaContable = ((Node)fechaContableText.item(0)).getNodeValue().trim();

                    //------
                    
                    NodeList codigoIdentificadorList = primerElementoServipag.getElementsByTagName("CodigoIdentificador");
                    Element codigoIdentificadorElement = (Element)codigoIdentificadorList.item(0);
                    NodeList codigoIdentificadorText = codigoIdentificadorElement.getChildNodes();
                    this.codigoIdentificador = ((Node)codigoIdentificadorText.item(0)).getNodeValue().trim();

                    //------
                    
                    NodeList boletaList = primerElementoServipag.getElementsByTagName("Boleta");
                    Element boletaElement = (Element)boletaList.item(0);
                    NodeList boletaText = boletaElement.getChildNodes();
                    this.boleta = ((Node)boletaText.item(0)).getNodeValue().trim();

                    //------
                    
                    NodeList montoList = primerElementoServipag.getElementsByTagName("Monto");
                    Element montoElement = (Element)montoList.item(0);
                    NodeList montoText = montoElement.getChildNodes();
                    this.monto = ((Node)montoText.item(0)).getNodeValue().trim();

                }
            }
             
             BuscaNodos buscar = new BuscaNodos();
             retornoArray = buscar.leer(Variables.nodo_XML2);
            
                for (i = 0; i < retornoArray.length; i++)
                {
                    compare = retornoArray[i][1];
                    log.genera("XML2 \nNodos a Firmar: " + compare + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML2);
                   
                   if (compare.equals("IdTrxServipag"))
                   {
                            cadenaNodos += this.getIdTrxServipag();
                   }
                   else if (compare.equals("IdTxCliente"))
                   {
                            cadenaNodos += this.getIdTxCliente();
                   }
                   else if (compare.equals("FechaPago"))
                   {
                            cadenaNodos += this.getFechaPago();
                   }
                   else if (compare.equals("CodMedioPago"))
                   {
                            cadenaNodos += this.getCodMedioPago();
                   }
                   else if (compare.equals("FechaContable"))
                   {
                            cadenaNodos += this.getFechaContable();
                   }
                   else if (compare.equals("CodigoIdentificador"))
                   {
                            cadenaNodos += this.getCodigoIdentificador();
                   }
                   else if (compare.equals("Boleta"))
                   {
                            cadenaNodos += this.getBoleta();
                   }
                   else if (compare.equals("Monto"))
                   {
                            cadenaNodos += this.getMonto();
                   }
                }
            log.genera("XML2 \nDatos a Firmar: " + cadenaNodos  + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML2);
            retorno = Crypto.checkSign(cadenaNodos, this.firmaServipag, Variables.rutaLlavePublica);
            log.genera("XML2" + "\nValidacion de Firma: " + retorno + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML2);
        }
        catch(Exception _e)
        {
            log.genera("XML2 \nExcepcion Error en la validacion del XML2 - Funcion ValidaFirmaXML2() :" + _e.getMessage() + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML2);
            retorno = false;
        }
        return retorno;
    }
}
