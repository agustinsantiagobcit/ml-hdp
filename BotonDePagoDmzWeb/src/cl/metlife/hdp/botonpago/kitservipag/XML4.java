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
public class XML4 {
    public String firmaServipag;
    public String idTrxServipag;
    public String idTxCliente;
    public String estadopago;
    public String mensaje;
    
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
    
    public String getEstadopago() {
        return estadopago;
    }
    public void setEstadopago(String estadopago) {
        this.estadopago = estadopago;
    }
    
     public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public boolean ValidaFirmaXML4(String Xml4Entrada, String configPath)
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

            Document document = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(Xml4Entrada)));
            
             NodeList listXml4 = document.getElementsByTagName("Servipag");
             for(int s=0; s<listXml4.getLength() ; s++)
             {
                Node primerRegistroServipag = listXml4.item(s);
                if(primerRegistroServipag.getNodeType() == Node.ELEMENT_NODE){

                    Element primerElementoServipag = (Element)primerRegistroServipag;

                    //-------
                    NodeList FirmaServipagList = primerElementoServipag.getElementsByTagName("FirmaServipag");
                    Element FirmaServipagElement = (Element)FirmaServipagList.item(0);
                    NodeList FirmaServipagText = FirmaServipagElement.getChildNodes();
                    Node nodo = (Node) FirmaServipagText.item(0);
                    if(nodo != null) {
                        this.firmaServipag = nodo.getNodeValue().trim();
                    }
                    else {
                        this.firmaServipag = "";
                    }
                    //-------
                    NodeList idTrxServipagList = primerElementoServipag.getElementsByTagName("IdTrxServipag");
                    Element idTrxServipagElement = (Element)idTrxServipagList.item(0);
                    NodeList idTrxServipagText = idTrxServipagElement.getChildNodes();
                    nodo = (Node) idTrxServipagText.item(0);
                    this.idTrxServipag = ((Node) idTrxServipagText.item(0)).getNodeValue().trim();


                    //-------
                    NodeList idTxClienteList = primerElementoServipag.getElementsByTagName("IdTxCliente");
                    Element idTxClienteElement = (Element)idTxClienteList.item(0);
                    NodeList idTxClienteText = idTxClienteElement.getChildNodes();
                    this.idTxCliente = ((Node)idTxClienteText.item(0)).getNodeValue().trim();

                    //----
                    NodeList fechaPagoList = primerElementoServipag.getElementsByTagName("EstadoPago");
                    Element fechaPagoElement = (Element)fechaPagoList.item(0);
                    NodeList fechaPagoText = fechaPagoElement.getChildNodes();
                    this.estadopago = ((Node)fechaPagoText.item(0)).getNodeValue().trim();
                    
                    //----
                    NodeList mensajeList = primerElementoServipag.getElementsByTagName("Mensaje");
                    Element mensajeElement = (Element)mensajeList.item(0);
                    NodeList mensajeText = mensajeElement.getChildNodes();
                    this.mensaje = ((Node)mensajeText.item(0)).getNodeValue().trim();

                 }
            }
            
             BuscaNodos buscar = new BuscaNodos();
             retornoArray = buscar.leer(Variables.nodo_XML4);
             
             for (i = 0; i < retornoArray.length; i++)
                {
                   compare = retornoArray[i][1];
                   log.genera("XML4 \nNodos a Firmar: " + compare + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML4);
                   if (compare.equals("IdTrxServipag"))
                   {
                            cadenaNodos += this.getIdTrxServipag();
                   }
                   else if (compare.equals("IdTxCliente"))
                   {
                            cadenaNodos += this.getIdTxCliente();
                   }
                   else if (compare.equals("EstadoPago"))
                   {
                            cadenaNodos += this.getEstadopago();
                   }
                   else if (compare.equals("Mensaje"))
                   {
                            cadenaNodos += this.getMensaje();
                   }
                }
            log.genera("XML4 \nDatos a Firmar: " + cadenaNodos  + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML4);
            if(cadenaNodos.length()==0 && this.firmaServipag.equals("")) {
                return true;
            }
            retorno = Crypto.checkSign(cadenaNodos, this.firmaServipag, Variables.rutaLlavePublica);
            log.genera("XML4 \nValidacion de Firma: " + retorno + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML4);
        
        }
        catch(Exception _e)
        {
           log.genera("XML4 \nExcepcion Error en la validacion del XML4 - Funcion ValidaFirmaXML4() :" + _e.getMessage() + "\n", Variables.rutaLog, Variables.nombre_Archivo_Log_XML4);
           retorno = false;
        }
        return retorno;
    }
}
