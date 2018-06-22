package cl.metlife.hdp.botonpago.genericxml;

/**
 * Created by Ivan on 26-08-2014.
 */
public class GenericXML3 {

    public static String GeneraXmlNotificar(boolean aceptado){
        String retorno ="<?xml version='1.0' encoding='ISO-8859-1'?>\n";
        if(aceptado){
            retorno += "<NotificacionPago>";
            retorno += "<Codigo>"+aceptado+"</Codigo";
            retorno += "</NotificacionPago>";
        }else{
            retorno += "<NotificacionPago>";
            retorno += "<Codigo>"+aceptado+"</Codigo";
            retorno += "</NotificacionPago>";
        }
        return retorno;
    }
}
