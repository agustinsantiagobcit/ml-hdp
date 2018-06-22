package cl.metlife.hdp.botonpago.kitsantander;

import java.util.List;

/**
 * Created by Ivan on 07-08-2014.
 */
public class XML {

    public String idComercio;
    public String IDTRX;
    public String total;
    public String nroPagos;
    List<XMLDetalle> detalle;

    public List<XMLDetalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<XMLDetalle> detalle) {
        this.detalle = detalle;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(String idComercio) {
        this.idComercio = idComercio;
    }

    public String getIDTRX() {
        return IDTRX;
    }

    public void setIDTRX(String IDTRX) {
        this.IDTRX = IDTRX;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNroPagos() {
        return nroPagos;
    }

    public void setNroPagos(String nroPagos) {
        this.nroPagos = nroPagos;
    }

    public String generarXML(){
        String xmlEnvio ="";
        xmlEnvio += "<MPINI>";
        xmlEnvio += "<IDCOM>"+this.idComercio+"</IDCOM>";
        xmlEnvio += "<IDTRX>"+this.IDTRX+"</IDTRX>";
        xmlEnvio += "<TOTAL>"+this.total+"</TOTAL>";
        xmlEnvio += "<NROPAGOS>"+this.nroPagos+"</NROPAGOS>";
        for(int i =0;i<Integer.parseInt(this.nroPagos);i++)
        {
            xmlEnvio += "<DETALLE>";
            xmlEnvio += "<SRVREC>"+this.detalle.get(i).SRVREC+"</SRVREC>";
            xmlEnvio += "<MONTO>"+this.detalle.get(i).monto+"</MONTO>";
            xmlEnvio += "<GLOSA>"+this.detalle.get(i).glosa+"</GLOSA>";
            xmlEnvio += "<CANTIDAD>"+this.detalle.get(i).cantidad+"</CANTIDAD>";
            xmlEnvio += "<PRECIO>"+this.detalle.get(i).precioUnitario+"</PRECIO>";
            xmlEnvio += "<DATOADIC>"+this.detalle.get(i).datosAdicionales+"</DATOADIC></DETALLE>";
        }
        xmlEnvio += "</MPINI>";
        return xmlEnvio;
    }
}
