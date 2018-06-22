package cl.metlife.hdp.botonpago.genericxml;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.genericpayment.GenericNotifyPaymentSupportServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static cl.metlife.hdp.botonpago.genericxml.GenericXML3.GeneraXmlNotificar;

/**
 * Created by Ivan on 26-08-2014.
 */
public class GenericXML2 extends GenericNotifyPaymentSupportServlet {

    String DiaTrx;
    String MesTrx;
    String AgnoTrx;
    String DiaContable;
    String MesContable;
    String AgnoContable;
    int cont=0;

    public String ProcesarXMLConfirmacion(String xmlEntrada, String remoteIP){
        String retorno ="";
        javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
        Document xml = convertStringToDocument(xmlEntrada);
        try {
            String IdentificadorComercio = (String) xPath.evaluate("/ConfirmacionSolicitudPago/IdentificadorComercio", xml, XPathConstants.STRING);

            String IdPago = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/IdPago", xml, XPathConstants.STRING);
            PaymentInfo pinfo = this.pagoDAWS.getPago(Integer.parseInt(IdPago), remoteIP);

            this.DiaTrx = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaTransaccion/Dia", xml, XPathConstants.STRING);
            this.MesTrx = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaTransaccion/Mes", xml, XPathConstants.STRING);
            this.AgnoTrx = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaTransaccion/Agno", xml, XPathConstants.STRING);

            this.DiaContable = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaContable/Dia", xml, XPathConstants.STRING);
            this.MesContable = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaContable/Mes", xml, XPathConstants.STRING);
            this.AgnoContable = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/FechaContable/Agno", xml, XPathConstants.STRING);

            String Estado = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/Estado", xml, XPathConstants.STRING);
            String DescriptorRetorno = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/DescriptorRetorno", xml, XPathConstants.STRING);

            String MontoTotal = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/MontoTotal", xml, XPathConstants.STRING);
            String NroBoletas = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/NroBoletas", xml, XPathConstants.STRING);

            String Comprobante = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/Comprobante", xml, XPathConstants.STRING);
            String CodigoAutorizacion = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/CodigoAutorizacion", xml, XPathConstants.STRING);
            String Firma = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/Firma", xml, XPathConstants.STRING);
            String IdTransaccionRemoto = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/IdTransaccionRemoto", xml, XPathConstants.STRING);
            String MedioPago = (String) xPath.evaluate("/ConfirmacionSolicitudPago/InfoPago/MedioPago", xml, XPathConstants.STRING);

            if(ValidarEstado(Estado)){
                if(ValidarBoletas(xml.getElementsByTagName("Boletas"),pinfo)){
                    if(ValidarCantidadBoletas(this.cont,pinfo)){
                        if(!this.pagoDAWS.isPayed(Integer.parseInt(IdPago), remoteIP)) {
                            this.pagoDAWS.markAsPayedBotonPago(pinfo.getPago(), pinfo.getPago().getBotonConvenio().getId(), xmlEntrada, remoteIP);
                            retorno = GeneraXmlNotificar(true);
                        }else{
                            this.pagoDAWS.markAsRejected(Integer.parseInt(IdPago), pinfo.getPago().getBotonConvenio().getId(), "Ya pagado", xmlEntrada, remoteIP);
                            retorno = GeneraXmlNotificar(false);
                        }
                    }else{
                        this.pagoDAWS.markAsRejected(Integer.parseInt(IdPago), pinfo.getPago().getBotonConvenio().getId(), "Cantidad de boletas pagadas no coincide con las boletas a pagar", xmlEntrada, remoteIP);
                        retorno = GeneraXmlNotificar(false);
                    }
                }else{
                    this.pagoDAWS.markAsRejected(Integer.parseInt(IdPago), pinfo.getPago().getBotonConvenio().getId(), "Monto pagado de una de las boleta no coincide con el monto pagado", xmlEntrada, remoteIP);
                    retorno = GeneraXmlNotificar(false);
                }
            }else{
                this.pagoDAWS.markAsRejected( Integer.parseInt(IdPago), pinfo.getPago().getBotonConvenio().getId(), "Codigo de retorno distinto de 0", xmlEntrada, remoteIP);
                retorno = GeneraXmlNotificar(false);
            }


        } catch (XPathExpressionException e) {
            retorno = GeneraXmlNotificar(false);
            e.printStackTrace();
        }
        return retorno;
    }

    public boolean ValidarBoletas(NodeList Boletas, PaymentInfo pinfo){

        for(int s=0; s<Boletas.getLength() ; s++){
            Node Nodo = Boletas.item(s);
            if(Nodo.getNodeType() == Node.ELEMENT_NODE){
                Element MontoElemento = (Element)Nodo;

                cont++;

                Element IdElemento = (Element)Nodo;
                NodeList IdNodeList = IdElemento.getElementsByTagName("IdBoleta");
                Element IdElement = (Element)IdNodeList.item(0);
                NodeList IdChild = IdElement.getChildNodes();
                String Id = ((Node)IdChild.item(0)).getNodeValue().trim();

                for(BotonBoleta boleta : pinfo.getPago().getBotonBoletas()){

                    boleta.setFechaContable(DiaContable+"/"+MesContable+"/"+AgnoContable);
                    boleta.setFechaTransaccion(DiaTrx+"/"+MesTrx+"/"+AgnoTrx);

                    if(Id.equals(boleta.getId())){
                        NodeList MontoNodeList = MontoElemento.getElementsByTagName("Monto");
                        Element MontoElement = (Element)MontoNodeList.item(0);
                        NodeList MontoChild = MontoElement.getChildNodes();
                        String Monto = ((Node)MontoChild.item(0)).getNodeValue().trim();
                        if(boleta.getMonto() != Integer.parseInt(Monto)){
                            System.out.println("Monto pagado de la boleta id: "+boleta.getId()+" no coincide con el monto pagado: "+Monto);
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("Monto de boletas ok");
        return true;
    }

    public boolean ValidarCantidadBoletas(int cont,PaymentInfo pinfo){
        if(cont != pinfo.getPago().getBotonBoletas().size()){
            System.out.println("cantidad de boletas pagadas: "+cont+" no coincide con las boletas a pagar: "+pinfo.getPago().getBotonBoletas().size());
            this.cont = 0;
            return false;
        }
        System.out.println("cantidad de boletas pagadas ok");
        this.cont = 0;
        return true;
    }

    public boolean ValidarEstado(String Estado){
        if(!Estado.equals("0000")){
            System.out.println("Codigo de retorno distinto de 0");
            return false;
        }
        System.out.println("Codigo de retorno ok");
        return true;
    }

}
