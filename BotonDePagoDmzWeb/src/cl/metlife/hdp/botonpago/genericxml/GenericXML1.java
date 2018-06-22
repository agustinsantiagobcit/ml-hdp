package cl.metlife.hdp.botonpago.genericxml;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.BotonConvenio;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;

import java.util.Calendar;

/**
 * Created by Ivan on 25-08-2014.
 */
public class GenericXML1 {

    public String GeneraXmlInicioPago(PaymentInfo pinfo,BotonConvenio convenio, String firma, String notifyUrl, String finishUrl) {


        Calendar c = Calendar.getInstance();
        String retorno = "";

        retorno += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<StartPayment>\n" +
                "    <IdPago>" + pinfo.getPago().getId() + "</IdPago>\n" +
                "    <InstitucionRecaudadora>\n" +
                "        <Nombre>" + convenio.getNombreComercio() + "</Nombre>\n" +
                "        <Convenio>" + convenio.getNumeroConvenio() + "</Convenio>\n" +
                "        <RutComercio>" + convenio.getRutComercio() + "</RutComercio>\n" +
                "    </InstitucionRecaudadora>\n" +
                "    <Cliente>\n" +
                "        <RUT>"+pinfo.getPago().getClienteBean().getRutCliente()+"</RUT>\n" +
                "        <Nombre>" + pinfo.getPago().getClienteBean().getNombreCliente() + "</Nombre>\n" +
                "        <Mail>"+pinfo.getPago().getClienteBean().getEmail()+"</Mail>\n" +
                "    </Cliente>\n" +
                "    <OrdenesDeCompra>\n";

        for(BotonBoleta boleta : pinfo.getPago().getBotonBoletas()) {
            retorno += "        <OrdenDeCompra>\n";
            retorno += "            <NumeroOC>" + boleta.getId() + "</NumeroOC>\n";
            retorno += "            <IdentificadorTienda>" + pinfo.getPago().getBotonConvenio().getNumeroConvenio() + "</IdentificadorTienda>";
            retorno += "            <GlosaOC>" + boleta.getDescripcion() + "</GlosaOC>\n" ;
            retorno += "            <MontoOC>" + boleta.getMonto() + "</MontoOC>\n" ;
            retorno += "            <Divisa>" + boleta.getCodigosMoneda().getCodigoMoneda() + "</Divisa>\n" ;
            retorno += "        </OrdenDeCompra>\n";
        }

        retorno += "        <CantidadOC>" + pinfo.getPago().getBotonBoletas().size() + "</CantidadOC>\n" ;
        retorno += "    </OrdenesDeCompra>\n" ;
        retorno += "    <MontoTotal>"+pinfo.getPago().getMontoTotal()+"</MontoTotal>\n";
        retorno += "    <FechaPago>" ;
        retorno += "       <Dia>"+c.get(Calendar.DAY_OF_MONTH)+"</Dia>" ;
        retorno += "       <Mes>"+(c.get(Calendar.MONTH)+1)+"</Mes>" ;
        retorno += "       <Agno>"+c.get(Calendar.YEAR)+"</Agno>" ;
        retorno += "    </FechaPago>" ;
        retorno += "    <URLNotificacionPago>" + notifyUrl.replace("&", "&amp;") + "</URLNotificacionPago>\n" ;
        retorno += "    <URLFinPago>" + finishUrl.replace("&", "&amp;") + "</URLFinPago>\n" ;
        retorno += "    <Firma>"+firma+"</Firma>\n" ;
        retorno += "</StartPayment>";

        return retorno;
    }

}
