/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;
/**
 *
 * @author Ggonzalez
 */
public class XML3 {
    
     public String GeneraFirmaXML3(boolean codRetornoServipag)
        {
            String Xml3Salida = "";
            if (codRetornoServipag)
            {
                Xml3Salida = "<?xml version='1.0' encoding='ISO-8859-1'?>";
                Xml3Salida += "<Servipag>";
                Xml3Salida += "<CodigoRetorno>0</CodigoRetorno>";
                Xml3Salida += "<MensajeRetorno>Transaccion Exitosa</MensajeRetorno>";
                Xml3Salida += "</Servipag>";
            }
            else
            {
                Xml3Salida = "<?xml version='1.0' encoding='ISO-8859-1'?>";
                Xml3Salida += "<Servipag>";
                Xml3Salida += "<CodigoRetorno>1</CodigoRetorno>";
                Xml3Salida += "<MensajeRetorno>Transaccion Rechazada</MensajeRetorno>";
                Xml3Salida += "</Servipag>";
            }
            return Xml3Salida;
        }
    
}
