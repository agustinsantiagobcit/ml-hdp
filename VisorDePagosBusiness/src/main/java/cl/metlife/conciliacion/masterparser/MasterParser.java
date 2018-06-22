package cl.metlife.conciliacion.masterparser;

import cl.metlife.conciliacion.domain.PaymentInstitution;
import cl.metlife.conciliacion.managers.ParserConfigManager;
import cl.metlife.conciliacion.ws.parsercaller.*;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MasterParser {

    private static final Logger LOGGER = Logger.getLogger(MasterParser.class);

    private static MasterParser instance = null;
    private Map<Long, ParserPago> parsers;

    public MasterParser() {
        parsers = new HashMap<Long, ParserPago>();
    }

    public static MasterParser getInstance() {

        if(instance == null){
            instance = new MasterParser();
        }
        return instance;
    }

    private ParserPago getRemoteParser(ParserConfigManager parserConfigDAO, PaymentInstitution inst) throws ParserException {

        ParserPago parser = this.parsers.get(inst.getId());

        if (parser == null) {
            Map<String, String> parserConfigurations = parserConfigDAO.getParserConfigurations(inst.getId());

            String urlParser = parserConfigurations.get("URL");
            String serviceName = parserConfigurations.get("ServiceName");
            String portName = parserConfigurations.get("PortName");
            String tns = parserConfigurations.get("TargetNamespace");

            URL serviceClientClass;

            try {
                /* TODO ver como obtener y guardar esta configuracion
                serviceClientClass = new URL(parserConfigDAO.getParserConfigurations(inst.getId()).get("ServiceClientClass"));
                */
                serviceClientClass = ParserOfficeBankingWS.class.getResource(".");

                parser = getParserPagoReference(portName, serviceName, tns, urlParser, serviceClientClass);

            } catch (MalformedURLException e) {
                throw new ParserException("Invalid url");
            } catch (NullPointerException e) {
                throw new ParserException("No se pudo obtener el parser");
            }
            this.parsers.put(inst.getId(), parser);
        }
        return parser;
    }

    public ParsedBankFileType parse(ParserConfigManager parserConfigDAO, String serializedFile, PaymentInstitution institution)
            throws ParserException, FileParsingException_Exception, ParserConnectionException {

        BigDecimal id = new BigDecimal(institution.getId());
        ParsedBankFileType parsedPayment;

        try {
            ParserPago remoteParser = this.getRemoteParser(parserConfigDAO, institution);
            parsedPayment = remoteParser.parse(serializedFile, id.intValue());
            return parsedPayment;

        } catch (WebServiceException e) {
            throw new ParserConnectionException("No ha sido posible conectar con el servicio Parser, " +
                    "por favor intente nuevamente. <br> Si el error persiste contacte al proveedor.");
        }

    }

    private ParserPago getParserPagoReference(String portName, String serviceName, String tns,
                                              String wsdlLocation, URL baseUrl) throws MalformedURLException {

        ParserOfficeBankingWS locator = null;
        URL url = null;

        try {
            url = new URL(baseUrl, wsdlLocation);

        } catch (Exception e) {
            e.printStackTrace();
        }
        QName qName = new QName(tns, serviceName);
        QName qnPort = new QName(tns, portName);

        locator = new ParserOfficeBankingWS(url, qName);

        return locator.getParserOfficeBanking(qnPort);
    }
}