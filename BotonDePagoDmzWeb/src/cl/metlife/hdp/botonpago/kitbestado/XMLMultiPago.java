package cl.metlife.hdp.botonpago.kitbestado;

import java.util.List;

/**
 * Created by BlueprintsIT on 12/29/2015.
 */
public class XMLMultiPago {

    public String IDTRX;
    public String idComercio;
    public String idConvenio;
    public String servicio;
    public String RUTCliente;
    public String pagComercio;
    public String tipoConf;
    public String metodoRend;
    public String pagComercioRend;
    public String banco;
    public String nroPagos;
    public String total;

    public String glosaMultipago;
    public String idMPago;
    List<XMLMultiPagoDetalle> detalleMPago;

    public String generarXML(){
        String xmlEnvio ="";
        xmlEnvio += "<INICIO>";
        xmlEnvio += "<ENCABEZADO>";
        xmlEnvio += "<ID_SESION>"+this.IDTRX+"</ID_SESION>";
        xmlEnvio += "<RUT_DV_CON>"+this.idConvenio+"</RUT_DV_CON>";
        xmlEnvio += "<CONV_CON>"+this.idComercio+"</CONV_CON>";
        xmlEnvio += "<SERVICIO>"+this.servicio+"</SERVICIO>";
        xmlEnvio += "<RUT_DV_CLIENTE>"+this.RUTCliente+"</RUT_DV_CLIENTE>";
        xmlEnvio += "<PAG_RET>"+this.pagComercio+"</PAG_RET>";
        xmlEnvio += "<TIPO_CONF>"+this.tipoConf+"</TIPO_CONF>";
        xmlEnvio += "<METODO_REND>"+this.metodoRend+"</METODO_REND>";
        xmlEnvio += "<PAG_REND>"+this.pagComercioRend+"</PAG_REND>";
        xmlEnvio += "<BANCO>"+this.banco+"</BANCO>";
        xmlEnvio += "<CANT_MPAGO>"+this.nroPagos+"</CANT_MPAGO>";
        xmlEnvio += "<TOTAL>"+this.total+"</TOTAL>";
        xmlEnvio += "</ENCABEZADO>";
        xmlEnvio += "<MULTIPAGO>";
        xmlEnvio += "<GLOSA_MPAGO>"+this.glosaMultipago+"</GLOSA_MPAGO>";
        xmlEnvio += "<ID_MPAGO>"+this.idMPago+"</ID_MPAGO>";


        for(int i =0;i<Integer.parseInt(this.nroPagos);i++)
        {
            xmlEnvio += "<PAGO>";
            xmlEnvio += "<RUT_DV_EMP>" + this.detalleMPago.get(i).RUTEmpresa + "</RUT_DV_EMP>";
            xmlEnvio += "<NUM_CONV>" + this.detalleMPago.get(i).numConvenio + "</NUM_CONV>";
            xmlEnvio += "<FEC_TRX>" + this.detalleMPago.get(i).fechaTRX + "</FEC_TRX>";
            xmlEnvio += "<HOR_TRX>" + this.detalleMPago.get(i).horaTRX + "</HOR_TRX>";
            xmlEnvio += "<FEC_VENC>" + this.detalleMPago.get(i).fechaVen + "</FEC_VENC>";
            xmlEnvio += "<GLOSA>" + this.detalleMPago.get(i).glosa + "</GLOSA>";
            xmlEnvio += "<COD_PAGO/>";// + this.detalleMPago.get(i).idPago + "</COD_PAGO>";
            xmlEnvio += "<MONTO>" + this.detalleMPago.get(i).monto + "</MONTO>";
            xmlEnvio += "</PAGO>";
        }
        xmlEnvio += "</MULTIPAGO></INICIO>";
        return xmlEnvio;
    }

    public String getIDTRX() {
        return IDTRX;
    }

    public void setIDTRX(String IDTRX) {
        this.IDTRX = IDTRX;
    }

    public String getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(String idComercio) {
        this.idComercio = idComercio;
    }

    public String getIdConvenio() {
        return idConvenio;
    }

    public void setRutEmpresa(String idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getRUTCliente() {
        return RUTCliente;
    }

    public void setRUTCliente(String RUTCliente) {
        this.RUTCliente = RUTCliente;
    }

    public String getPagComercio() {
        return pagComercio;
    }

    public void setURLFinPago(String pagComercio) {
        this.pagComercio = pagComercio;
    }

    public String getTipoConf() {
        return tipoConf;
    }

    public void setTipoConf(String tipoConf) {
        this.tipoConf = tipoConf;
    }

    public String getMetodoRend() {
        return metodoRend;
    }

    public void setMetodoRend(String metodoRend) {
        this.metodoRend = metodoRend;
    }

    public String getPagComercioRend() {
        return pagComercioRend;
    }

    public void setPagComercioRend(String pagComercioRend) {
        this.pagComercioRend = pagComercioRend;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNroPagos() {
        return nroPagos;
    }

    public void setNroPagos(String nroPagos) {
        this.nroPagos = nroPagos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGlosaMultipago() {
        return glosaMultipago;
    }

    public void setGlosaMultipago(String glosaMultipago) {
        this.glosaMultipago = glosaMultipago;
    }

    public String getIdMPago() {
        return idMPago;
    }

    public void setIdMPago(String idMPago) {
        this.idMPago = idMPago;
    }

    public List<XMLMultiPagoDetalle> getDetalleMPago() {
        return detalleMPago;
    }

    public void setDetalleMPago(List<XMLMultiPagoDetalle> detalleMPago) {
        this.detalleMPago = detalleMPago;
    }
}
