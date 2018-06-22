package cl.metlife.visorpagos.controller;

import cl.metlife.conciliacion.business.ConciliationLog;
import cl.metlife.conciliacion.business.Configurations;
import cl.metlife.conciliacion.domain.*;
import cl.metlife.conciliacion.managers.*;
import cl.metlife.conciliacion.managers.LogManager;
import cl.metlife.conciliacion.masterconciliator.ConciliatorConnectorException;
import cl.metlife.conciliacion.masterconciliator.MasterConciliator;
import cl.metlife.conciliacion.masterparser.MasterParser;
import cl.metlife.conciliacion.masterparser.ParserConnectionException;
import cl.metlife.conciliacion.masterparser.ParserException;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.Moneda;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.Pago;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.ResultadoConciliacion;
import cl.metlife.conciliacion.ws.conciliatorconnector.caller.TipoDocumento;
import cl.metlife.conciliacion.ws.connectormut.ConectorConciliadorWSSoap;
import cl.metlife.conciliacion.ws.parsercaller.FileParsingException_Exception;
import cl.metlife.conciliacion.ws.parsercaller.ParsedBankFileType;
import cl.metlife.conciliacion.ws.parsercaller.PaymentType;
import cl.metlife.conciliacion.ws.parsercaller.PaymentTypePOJO;
import cl.metlife.hdp.domain.BotonBoleta;
import cl.metlife.hdp.domain.BotonPago;
import cl.metlife.hdp.managers.*;
import cl.metlife.hdp.domain.BotonLineasDeNegocio;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Blueprints on 6/25/2015.
 */

@ManagedBean(name="conciliadorBean")
@SessionScoped
public class ConciliatorBean extends BaseBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ConciliatorBean.class);

    private String businessLine;
    private String paymentInstitutionId;
    private UploadedFile file;
    private List<PaymentInstitution> paymentInstitutions;

    // Results
    private List<Payment> processedPayments;
    private List<Payment> processedPaymentsAlreadyConciliated;
    private List<Payment> processedPaymentsWithErrors;

    @EJB private BotonInstitucionesPagoManager botonInstitucionesPagoManager;
    @EJB private BotonLineasDeNegocioManager botonLineasDeNegocioManager;
    @EJB private LogManager logManager;
    @EJB private PaymentInstitutionManager paymentInstitutionManager;
    @EJB private ParserConfigManager parserConfigManager;
    @EJB private ConciliationFileManager conciliationFileManager;
    @EJB private PaymentStatusManager paymentStatusManager;
    @EJB private PaymentManager paymentManager;
    @EJB private BotonPagoManager botonPagoManager;
    @EJB private BotonBoletaManager botonBoletaManager;
    @EJB private CustomerManager customerManager;
    @EJB private CurrencyCodeManager currencyCodeManager;
    @EJB private ApplicationConfigManager applicationConfigManager;
    @EJB private UserManager userManager;
    @EJB private ConciliatorConnectorManager conciliatorConnectorManager;
    @EJB private ConvenioConciliacionManager convenioConciliacionManager;
    @EJB private LineasDeNegocioManager lineasDeNegocioManager;
    @EJB private MasterConciliator masterConciliator;


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws IOException {
        if(file != null) {
            logger.info("El archivo se pudo subir correctamente.");
        } else {
            logger.info("Seleccionar archivo de conciliación");
            return;
        }

        ConciliationAgreement convenioConciliacion = convenioConciliacionManager.getByBusinessLineAndPaymentInstitution(businessLine, Long.valueOf(paymentInstitutionId));

        User executorUser = userManager.getUserByRUT("1-9");

        ConciliationLog conciliationLog = ConciliationLog.getInstance(logManager);

        Configurations configurations = Configurations.getInstance();

        configurations.setConfigurationMap(applicationConfigManager.getAllConfigurations());

        String backupFilePath = configurations.getParameter("ruta_archivo_resp");

        /* The Conciliation file is loaded */

        PaymentInstitution paymentInstitution = retrievePaymentInstFromRequest(paymentInstitutionId);

        String withoutBlankName = file.getFileName().replace(" ", "_");

        // Validating File extension
        if( !hasValidFileExtension(file, paymentInstitution.getTipoParser())) {
            FacesMessage message = new FacesMessage("Intento de conciliar archivo de formato incorrecto." + "El archivo está mal formado o no corresponde a la institución " +
                    "seleccionada.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Intento de conciliar archivo de formato incorrecto." + "El archivo está mal formado o no corresponde a la institución " +
                    "seleccionada.");
            return;
        }

        // Converting file content
        String serializedFile = "";

        if (paymentInstitution.getTipoParser().equalsIgnoreCase("Excel")) {
            serializedFile = excelToCSV(file, parserConfigManager.getParserConfigurations(paymentInstitution.getId()));
        } else {
            serializedFile = fileToString(file, parserConfigManager.getParserConfigurations(paymentInstitution.getId()));
        }

        // Validating File has content
        if (serializedFile == null || serializedFile.isEmpty()) {
            logger.debug("Intento de conciliar archivo de formato incorrecto." + " El archivo está mal formado o no corresponde a la institución " +
                    "seleccionada.");
            FacesMessage message = new FacesMessage("Intento de conciliar archivo de formato incorrecto." + " El archivo está mal formado o no corresponde a la institución " +
                    "seleccionada.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        // Saving Conciliation File backup in configurations specified path  -- HASTA ACA FUNCIONA BIEN
        logger.debug("Saving Conciliation File backup to " + backupFilePath);
        String backupName = backupConciliationFile(backupFilePath, withoutBlankName, serializedFile);

        // Creating and persisting Conciliation File Descriptor in database
        ConciliationFile conciliationFileDescriptor = conciliationFileManager.createConciliationFile(backupName,
                backupFilePath + backupName, paymentInstitution, executorUser);

        // The file content is sent to the parser, if there are any problem trying to read it,
        // the user is redirected to the upload page with an error notification

        ParsedBankFileType parsedBankFile = null;

        try {
            parsedBankFile = MasterParser.getInstance().parse(parserConfigManager, serializedFile, paymentInstitution);

        } catch (FileParsingException_Exception e) {
            conciliationLog.parseErrorLog("admin", paymentInstitution, file.getFileName());
            logger.info("El archivo <b>" + file.getFileName() + "</b> contiene los " +
                    "siguientes errores: <br>" + e.getMessage());
            FacesMessage message = new FacesMessage("El archivo no se pudo parsear por errores.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;

        } catch (ParserException e) {
            logger.error("The file cannot be parsed, redirecting user to file upload page. " + e.getMessage());
            FacesMessage message = new FacesMessage("El archivo no pudo ser parseado.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;

        } catch (ParserConnectionException e) {
            logger.error("Error while trying to connect with Parser Service for payment institution " +
                    paymentInstitution.getName());
            FacesMessage message = new FacesMessage("Hubo un error en la conexión con el parser.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        logger.debug("Parsed successfully, redirecting to the Conciliation Process.");

        conciliationLog.parseSuccessLog(conciliationFileDescriptor, executorUser, paymentInstitution);

        //request.setAttribute("paymentsForProcessing", parsedBankFile);
        //request.setAttribute("conciliationFileDescriptor", conciliationFileDescriptor);

        // ACA SE LLAMA AL CONCILIATION-PROCESS
        //request.getRequestDispatcher("/conciliation-process").forward(request, response);
        conciliationProcess(parsedBankFile, file, conciliationFileDescriptor, executorUser, convenioConciliacion);
    }

    private void conciliationProcess(ParsedBankFileType parsedBankFileType, UploadedFile file, ConciliationFile conciliationFileDescriptor, User executorUser, ConciliationAgreement convenioConciliacion) throws IOException {

        ConciliationLog conciliationLogger = ConciliationLog.getInstance(logManager);

        /* Getting previously parsed payments */
        ParsedBankFileType parsedBankFile = parsedBankFileType;
        ConciliationFile conciliationFile = conciliationFileDescriptor;

        List<PaymentType> payments = parsedBankFile.getPayments();

        List<Payment> processedPayments = new ArrayList<Payment>();
        List<Payment> processedPaymentsWithErrors = new ArrayList<Payment>();
        List<Payment> processedPaymentsAlreadyConciliated = new ArrayList<Payment>();

        PaymentStatus communicationErrorStatus = paymentStatusManager.getPaymentStatus("Error Comunicacion");
        PaymentStatus unknownBusinessLine = paymentStatusManager.getPaymentStatus(3);

        List<PaymentTypePOJO> freshList = new ArrayList<PaymentTypePOJO>();

        if(convenioConciliacion.isAbrirPagos()){
            for (PaymentType payment : payments) {
                logger.info("El ID de pago obtenido del archivo es: " + payment.getDocumentNumber().toString());
                logger.debug("El ID de pago obtenido del archivo es: " + payment.getDocumentNumber().toString());

                BotonPago botonPago = botonPagoManager.getById(Long.valueOf(payment.getDocumentNumber().toString()));
                PaymentInstitution institution = paymentInstitutionManager.getPaymentInstitution(Long.valueOf(paymentInstitutionId));

                if(botonPago != null){
                    for (BotonBoleta botonBoleta : botonPago.getBotonBoletas()) {

                        PaymentTypePOJO paymentType = new PaymentTypePOJO();

                        paymentType.setDocumentNumber(botonBoleta.getIdentificadorItem());
                        paymentType.setProductNumber(botonBoleta.getProducto());

                        if("Seguros Colectivos".equals(this.businessLine)){
                            paymentType.setDocumentNumber("" + botonBoleta.getNumeroBoleta());
                            paymentType.setProductNumber("" + botonBoleta.getNumeroBoleta());
                        }

                        Calendar createDate = botonPago.getHoraEstado();
                        Date cDate = createDate.getTime();

                        SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
                        SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");

                        if(botonBoleta.getCuota() != null){
                            paymentType.setProductYear(Integer.parseInt(botonBoleta.getCuota().substring(0, 4)));
                            paymentType.setProductMonth(Integer.parseInt(botonBoleta.getCuota().substring(4, 6)));
                            paymentType.setPeriodo(botonBoleta.getCuota());
                        }else{
                            paymentType.setProductYear(-1);
                            paymentType.setProductMonth(-1);
                            paymentType.setPeriodo("1");
                        }

                        if(botonBoleta.getMonto() == null) {
                            logger.debug("error, el pago no trae el monto");
                        }
                        paymentType.setPaidAmount(botonBoleta.getMonto());
                        paymentType.setInitialToPayAmount(botonBoleta.getMonto());
                        paymentType.setAgreementNumber(100);

                        if(botonBoleta.getCodigosMoneda().getCodigoMoneda().equals("CLP")){
                            paymentType.setCurrencyCode(cl.metlife.conciliacion.ws.parsercaller.Moneda.CLP);
                        }

                        if(botonBoleta.getCodigosMoneda().getCodigoMoneda().equals("UF")){
                            paymentType.setCurrencyCode(cl.metlife.conciliacion.ws.parsercaller.Moneda.UF);
                        }

                        if(botonBoleta.getCodigosMoneda().getCodigoMoneda().equals("USD")){
                            paymentType.setCurrencyCode(cl.metlife.conciliacion.ws.parsercaller.Moneda.USD);
                        }

                        if(botonPago.getClienteBean() != null){
                            paymentType.setDebtorName(botonPago.getClienteBean().getNombreCliente());
                            paymentType.setDebtorRUT(botonPago.getClienteBean().getRutCliente());
                        }

                        String a = "" + payment.getPaidAmount();

                        if(a.endsWith(".0"))
                            a = a.substring(0, a.length() - 2);

                        a = a.replace(".", "");

                        paymentType.setArchivePaidAmount(Double.parseDouble(a));

                        GregorianCalendar c = new GregorianCalendar();
                        c.setTime(cDate);
                        XMLGregorianCalendar date2 = null;
                        try {
                            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                        } catch (DatatypeConfigurationException e) {
                            e.printStackTrace();
                        }

                        paymentType.setPaymentDate(date2);
                        paymentType.setBoletaId(botonBoleta.getId());

                        // NEW FIXs
                        paymentType.setControlAccount("");
                        // FIXME 1 or 2, Iván tiene que indicarlo
                        if(this.businessLine.equals("VI") && botonBoleta.isExtraordinario())
                            paymentType.setControlAccount("2");
                        if(this.businessLine.equals("VI") && !botonBoleta.isExtraordinario())
                            paymentType.setControlAccount("1");

                        freshList.add(paymentType);
                    }
                } else {
                    freshList.add(mapPayment(payment));
                }
            }
        } else {
            for (PaymentType payment : payments) {
                freshList.add(mapPayment(payment));
            }
        }

        try {
            // Sorting
            Collections.sort(freshList, new Comparator<PaymentTypePOJO>() {
                @Override
                public int compare(PaymentTypePOJO pay1, PaymentTypePOJO pay2)
                {
                    return  pay1.getPeriodo().compareTo(pay2.getPeriodo());
                }
            });
        } catch (Exception e){
            System.out.println("Hubo un error al ordenar los pagos. Se conciliarán desordenados");
        }


        for (PaymentTypePOJO parsedPayment : freshList) {
            /* Creating and persisting application managed payment */
            Payment payment;

            if(convenioConciliacion.isAbrirPagos())
                payment = createPaymentFromParsedPayment(parsedPayment, conciliationFile);
            else
                payment = createPaymentFromParsedPaymentOld(parsedPayment, conciliationFile);

            Payment paymentAlreadyConciliated = null;

            /* If the payment is parsed and validated correctly, re-processing validation is performed */
            if (!payment.getStatus().getName().equalsIgnoreCase("No Conciliado (Con errores)")) {

                paymentAlreadyConciliated = paymentManager.getAlreadyConciliated(payment.getProductNumber(),
                        payment.getDocumentNumber());

                if(!this.businessLine.equals("Mutuaria")){
                    if (paymentAlreadyConciliated != null && paymentAlreadyConciliated.getStatus() != null &&
                            paymentAlreadyConciliated.getStatus().getName().equals("Pagado")) {

                        payment.setStatus(paymentStatusManager.getPaymentStatus("No Conciliado (Ya procesado)"));
                    }
                }
            }

            /* Checking if the payment contains errors before conciliate payment */
            if(payment.getStatus().getName().equalsIgnoreCase("No Conciliado (Con errores)")) {
                processedPaymentsWithErrors.add(payment);
                continue;

            } else if (payment.getStatus().getName().equalsIgnoreCase("No Conciliado (Ya procesado)")) {
                /* Retrieving data for report */
                payment.setControlAccount(paymentAlreadyConciliated.getControlAccount());
                payment.setInvoiceNumber(paymentAlreadyConciliated.getInvoiceNumber());

                paymentManager.updateAfterConciliation(payment.getId(), payment.getControlAccount(),
                        payment.getInvoiceNumber(), payment.getStatus());

                processedPaymentsAlreadyConciliated.add(payment);
                continue;
            }

            /* Sending Payment to the Conciliation Service */

            /* The Conciliator payment will be created for sending */
            Pago pago = createConciliatorPayment(payment);

            /* "Error Comunicacion" as default but if is not an error it will be updated later */
            if(payment.getStatus().getName().equalsIgnoreCase("Creado")) {
                payment.setStatus(communicationErrorStatus);
                paymentManager.updateStatus(payment.getId(), payment.getStatus());
            }

            try {
                long beforeCallTime = System.currentTimeMillis();

                PaymentInstitution paymentInstitution = paymentInstitutionManager.getPaymentInstitution(Long.valueOf(this.paymentInstitutionId));

                /* Este cambio se hizo el 12 de octubre del 2017 a las 16:29. Ahora se envía la institución de pago
                seleccionada en la vista al proceso de conciliación. */
                ResultadoConciliacion conciliationResult = masterConciliator.conciliar(pago,
                        paymentInstitution, this.businessLine, conciliatorConnectorManager.getAllConnectors());

                long afterCallTime = System.currentTimeMillis();

                /* Updating data of the conciliated payment */
                if(conciliationResult != null) {
                    payment = updatePayment(payment, conciliationResult);

                    /* Adding to the list of processed payments or error payment according to the conciliation service
                    * response */
                    if(payment.getStatus().getName().equalsIgnoreCase("Pagado") || payment.getStatus().getName().equalsIgnoreCase("0")) {
                        processedPayments.add(payment);
                        /* Log status change */
                        conciliationLogger.paymentConciliatedLog(parsedPayment,
                                (afterCallTime - beforeCallTime), conciliationFile, executorUser, payment);

                        if(convenioConciliacion.isAbrirPagos())
                            botonBoletaManager.updateBoleta(parsedPayment.getBoletaId(), true, new Date()); // TODO:Actualizar pago conciliado 1 y fecha actual.
                    } else if(payment.getStatus().getName().equalsIgnoreCase("Pagado_Parcial")) {
                        processedPayments.add(payment);
                        /* Log status change */
                        conciliationLogger.paymentPartiallyConciliatedLog(parsedPayment,
                                (afterCallTime - beforeCallTime), conciliationFile, executorUser, payment);

                        // TODO:Actualizar pago conciliado 1 y fecha actual.
                        if(convenioConciliacion.isAbrirPagos())
                            botonBoletaManager.updateBoleta(parsedPayment.getBoletaId(), true, new Date());
                    } else {
                        processedPaymentsWithErrors.add(payment);
                        /* Log status change */
                        conciliationLogger.paymentConciliationErrorResponseLog(parsedPayment,
                                conciliationFile, executorUser, payment, (afterCallTime - beforeCallTime));
                        // TODO:Actualizar pago conciliado 0 y fecha actual.
                        if(convenioConciliacion.isAbrirPagos())
                            botonBoletaManager.updateBoleta(parsedPayment.getBoletaId(), false, new Date());
                    }
                } else {
                    payment.setStatus(communicationErrorStatus);
                    processedPaymentsWithErrors.add(payment);
                    ConciliationLog.getInstance(logManager).paymentConciliationErrorLog(parsedPayment, conciliationFile,
                            executorUser, payment);
                    // TODO:Actualizar pago conciliado 0 y fecha actual.
                    if(convenioConciliacion.isAbrirPagos())
                        botonBoletaManager.updateBoleta(parsedPayment.getBoletaId(), false, new Date());
                }

            } catch (ConciliatorConnectorException e) {
                logger.error("Communication error with external business system. " + e.getMessage());
                /* Log conciliation error */
                ConciliationLog.getInstance(logManager).paymentConciliationErrorLog(parsedPayment, conciliationFile,
                        executorUser, payment);
            } catch (RepositoryException e) {
                logger.error("Cannot retrieve paymentStatus or persist new one into DB");

            } catch (WebServiceException e) {
                processedPaymentsWithErrors.add(payment);
                ConciliationLog.getInstance(logManager).paymentConciliationErrorLog(parsedPayment, conciliationFile,
                        executorUser, payment);

            } catch (NullPointerException e) {
                payment.setStatus(unknownBusinessLine);
                paymentManager.updateStatus(payment.getId(), payment.getStatus());
                processedPaymentsWithErrors.add(payment);
                ConciliationLog.getInstance(logManager).paymentConciliationErrorLog(parsedPayment, conciliationFile,
                        executorUser, payment);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        String appHost = Configurations.getInstance().getParameter("appHost");
        String appPort = Configurations.getInstance().getParameter("appPort");

        /* Mark Conciliated File as processed */
        conciliationFileManager.markAsProcessed(conciliationFile.getId());

        /* Redirecting to the report with the processed payments */
        logger.debug("ProcessedPayments: " + processedPayments.size());
        logger.debug("ProcessedPayments Already Conciliated: " + processedPaymentsAlreadyConciliated.size());
        logger.debug("ProcessedPayments with Errors: " + processedPaymentsWithErrors.size());
        logger.debug("Redirecting to the Report Page.");

        // Resultado conciliacion.
        //request.getRequestDispatcher("/resultado-conciliacion").forward(request, response);

        this.processedPayments = processedPayments;
        this.processedPaymentsAlreadyConciliated = processedPaymentsAlreadyConciliated;
        this.processedPaymentsWithErrors = processedPaymentsWithErrors;

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/views/conciliator/results.xhtml");
    }

    private Payment createPaymentFromParsedPaymentOld(PaymentTypePOJO parsedPayment, ConciliationFile conciliationFile) {
        String errors = "";
        String warnings = "";

        String documentNumber = "-1";
        String productNumber = "-1";
        double paidAmount = -1;
        double initialToPayAmount = -1;
        Calendar paymentDate = null;
        Customer customer = null;
        BusinessLine businessLine = null;
        CurrencyCode currencyCode = null;
        String periodMonth = "";
        String periodYear = "";

        /* Validating Document Number */
        if(parsedPayment.getDocumentNumber() != null && !parsedPayment.getDocumentNumber().equals("")) {
            documentNumber = parsedPayment.getDocumentNumber();
        } else {
            errors = errors.concat("No se pudo obtener <b>Número de Documento</b>. <br>");
        }

        /* Validating Paid Amount */
        if(parsedPayment.getPaidAmount() != null && parsedPayment.getPaidAmount() >= 0) {
            paidAmount = parsedPayment.getPaidAmount();
        } else {
            errors = errors.concat("No se pudo obtener <b>Monto Pagado</b>. <br>");
        }

        /* Validating Initial to Pay Amount */
        if(parsedPayment.getInitialToPayAmount() != null && parsedPayment.getInitialToPayAmount() >= 0) {
            initialToPayAmount = parsedPayment.getInitialToPayAmount();
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Monto a pagar</b>. <br>");
        }

        /* Validating Payment Date */
        if(parsedPayment.getPaymentDate() != null) {
            paymentDate = Calendar.getInstance();
            Date date = parsedPayment.getPaymentDate().toGregorianCalendar().getTime();
            paymentDate.setTime(date);
        } else {
            errors = errors.concat("No se pudo obtener <b>Fecha de Pago</b>. <br>");
        }

        /* Validating Customer. If this customer doesn't exist, it is created, and persisted */
        if(parsedPayment.getDebtorRUT() == null || parsedPayment.getDebtorName() == null ||
                parsedPayment.getDebtorName().isEmpty() || parsedPayment.getDebtorRUT().isEmpty()) {
            warnings = warnings.concat("No se pudieron obtener datos de <b>Cliente</b>. <br>");
        } else {
            String rutAsString = "";

            try {
                RUT rut = RUT.parseRUT(parsedPayment.getDebtorRUT());
                rutAsString = rut.toString();
                customer = customerManager.getCustomer(parsedPayment.getDebtorName(), rutAsString);

            } catch (InvalidRUTException e) {
                warnings = warnings.concat("No se pudieron obtener datos de <b>Cliente</b>. <br>");
            }
        }

        /* Getting Payment Institution */
        PaymentInstitution paymentInstitution = conciliationFile.getPaymentInstitution();

        if(paymentInstitution == null) {
            warnings = warnings.concat("No se pudo obtener <b>Institución de pago</b>. <br>");
        }

        /* Getting Business Line */
        Integer agreementNumberFromConciliatedPayment = parsedPayment.getAgreementNumber();
        if ((paymentInstitution.getIdCupon() == null || paymentInstitution.getIdCupon().isEmpty()) &&
                agreementNumberFromConciliatedPayment > 0) {
            PaymentInstitution institution =
                    paymentInstitutionManager.getPaymentInstitutionByAgreementNumber(agreementNumberFromConciliatedPayment);
            if (institution != null) {
                paymentInstitution = institution;
                businessLine = institution.getBusinessLine();
            }
        } else {
            businessLine = paymentInstitution.getBusinessLine();
        }

        /* Validating Currency Code */
        if(parsedPayment.getCurrencyCode() != null) {
            logger.debug("El currency code es: " + parsedPayment.getCurrencyCode());
            currencyCode = currencyCodeManager.getCurrencyCode(parsedPayment.getCurrencyCode().value());
            logger.error("Currency code doesn't exist1");

            if(currencyCode == null) {
                logger.debug("NO EXISTE");
                errors = errors.concat("No se pudo obtener <b>Divisa</b>. <br>");
                logger.error("Currency code doesn't exist2");
            }
        } else {
            logger.debug("Es nuloooo ERRORR");
            errors = errors.concat("No se pudo obtener <b>Divisa</b>. <br>");
            logger.error("Currency code doesn't exist3");
        }

        /* Validating Product Number */
        if(!parsedPayment.getProductNumber().isEmpty()) {
            try {
                productNumber = parsedPayment.getProductNumber();
            } catch (NumberFormatException e) {
                warnings = warnings.concat("No se pudo obtener <b>Número de Producto</b>. <br>");
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Número de Producto</b>. <br>");
        }

        /* Getting Period month */
        if(parsedPayment.getProductMonth() >= 0) {
            int month = parsedPayment.getProductMonth();
            if(month < 1 || month > 12) {
                warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
            } else {
            /* valid month */
                periodMonth = parsedPayment.getProductMonth() + "";
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
        }

        /* Getting Period year */
        if(parsedPayment.getProductYear() >= 0) {
            int year = parsedPayment.getProductYear();
            if(year < 0 || year > 9999) {
                warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
            } else {
            /* valid year */
                periodYear = parsedPayment.getProductYear() + "";
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Año producto</b>. <br>");
        }

        /* Getting control account */
        /* Not mandatory */
        String controlAccount = parsedPayment.getControlAccount();

        /* Creating Payment Status "creado", "creado with warnings" or "with errors" */
        PaymentStatus paymentStatus;

        if(errors.isEmpty()) {
            if(warnings.isEmpty()) {
                paymentStatus = paymentStatusManager.getPaymentStatus("Creado", "(Estado transitorio)");
            } else {
                paymentStatus = paymentStatusManager.getPaymentStatus("Creado", "(Estado transitorio) Advertencias: <br>"
                        + warnings);
            }
        } else {
            if(parsedPayment.getErrors() == null){
                parsedPayment.setErrors("");
                logger.debug("getErrors viene null");
            }

            if(errors == null){
                errors = "";
                logger.debug("errors viene null");
            }

            paymentStatus = paymentStatusManager.getPaymentStatus("No Conciliado (Con errores)",
                    parsedPayment.getErrors().concat(errors));
        }

        /* Creating and persisting payment */
        Payment payment = paymentManager.createPayment(conciliationFile, customer, paymentInstitution,
                businessLine, initialToPayAmount, paidAmount, paymentDate, paymentStatus,
                documentNumber, currencyCode, periodMonth, periodYear, productNumber, controlAccount);

        return payment;
    }

    private PaymentTypePOJO mapPayment(PaymentType payment) {
        PaymentTypePOJO paymentTypePOJO = new PaymentTypePOJO();

        paymentTypePOJO.setAgreementNumber(payment.getAgreementNumber());
        paymentTypePOJO.setControlAccount(payment.getControlAccount());
        paymentTypePOJO.setCurrencyCode(payment.getCurrencyCode());
        paymentTypePOJO.setDebtorName(payment.getDebtorName());
        paymentTypePOJO.setDebtorRUT(payment.getDebtorRUT());
        paymentTypePOJO.setInitialToPayAmount(payment.getInitialToPayAmount());
        paymentTypePOJO.setPaidAmount(payment.getPaidAmount());
        paymentTypePOJO.setPaymentDate(payment.getPaymentDate());
        paymentTypePOJO.setDocumentNumber("" + payment.getDocumentNumber());
        paymentTypePOJO.setProductMonth(payment.getProductMonth());
        paymentTypePOJO.setProductYear(payment.getProductYear());
        paymentTypePOJO.setProductNumber(payment.getProductNumber());

        return paymentTypePOJO;
    }

    private boolean hasValidFileExtension(UploadedFile conciliationFile, String parserKind) {

        try {
            /* Getting file extension */
            int extensionIndex = conciliationFile.getFileName().lastIndexOf(".");
            String extension = conciliationFile.getFileName().substring(extensionIndex, conciliationFile.getFileName().length());

            if (parserKind.equalsIgnoreCase("Excel") &&
                    (extension.equalsIgnoreCase(".xls") || extension.equalsIgnoreCase(".xlsx"))) {
                return true;
            }
            if (parserKind.equalsIgnoreCase("CSV") &&
                    (extension.equalsIgnoreCase(".csv") || extension.equalsIgnoreCase(".dat") )) {
                return true;
            }
            if (parserKind.equalsIgnoreCase("Ancho Fijo") &&
                    (extension.equalsIgnoreCase(".txt") || extension.equalsIgnoreCase(".asc") )) {
                return true;
            }
            if (parserKind.equalsIgnoreCase("XML") && extension.equalsIgnoreCase(".xml")) {
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    private String fileToString(UploadedFile configurationFile, Map<String, String> parserConfigurations) throws IOException {

        StringWriter writer = new StringWriter();
        IOUtils.copy(configurationFile.getInputstream(), writer, "UTF-8");
        String theString = writer.toString();

        String returnString = "";
        if(this.paymentInstitutionId.equals("15")){ // BANCO_ESTADO
            String[] firstCell = parserConfigurations.get("FirstCell").split(",");
            String[] rows = theString.split("\r\n");

            // Remover la primera y última linea
            int fromRow = 1;

            for(int i = fromRow; rows.length -1 > i ; i++){
                if(rows[i].startsWith("*"))
                    continue;

                returnString += rows[i] + "\n";
            }

            returnString = returnString.substring(0, returnString.length() - 1);

            // Juntar 2 filas, 1 pago.
            String finalReturnString = "";
            String[] pagos = returnString.split("\n");
            for(int j = 0;  pagos.length > j;){
                finalReturnString += pagos[j];
                finalReturnString += pagos[j+1] + "\n";
                j += 2;
            }

            finalReturnString = finalReturnString.substring(0, finalReturnString.length() - 1);

            returnString = finalReturnString;
        } else {
            String[] firstCell = parserConfigurations.get("FirstCell").split(",");
            String[] rows = theString.split("\n");

            int fromCell = Integer.valueOf(firstCell[0]);

            for(int i = fromCell; rows.length > i ; i++){
                if(rows[i].startsWith("*"))
                    continue;

                returnString += rows[i] + "\n";
            }

            returnString = returnString.substring(0, returnString.length() - 1);
        }

        /*String output = "";

        FileReader fileReader = new FileReader(configurationFile);
        BufferedReader bufferedReader = new BufferedReader(configurationFile.getInputstream());

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            output += line + "\n";
        }
        bufferedReader.close();*/

        return returnString;
    }

    private String excelToCSV(UploadedFile excelFile, Map<String, String> parserConfigurations) {

        String output = "";
        String parserDateFormat = parserConfigurations.get("DateFormat");
        String parserSeparator = parserConfigurations.get("Separator");
        String[] firstCell = parserConfigurations.get("FirstCell").split(",");

        try {
            Workbook wb = WorkbookFactory.create(excelFile.getInputstream());
            int firstCellRow = Integer.parseInt(firstCell[0]);
            int firstCellColumn = Integer.parseInt(firstCell[1]);

            for(int s=0; s<wb.getNumberOfSheets(); s++) {
                Sheet sheet = wb.getSheetAt(s);

                Row row = null;
                for (int i = firstCellRow; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);

                    if(row == null || row.getCell(firstCellColumn) == null || String.valueOf(row.getCell(firstCellColumn)).isEmpty()) {
                        break;
                    } else {
                        for (int j = firstCellColumn; j < row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            String cellValue;

                            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

                                if (DateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(parserDateFormat);
                                    cellValue = dateFormat.format(cell.getDateCellValue());
                                } else {
                                    Double value = cell.getNumericCellValue();
                                    if(FormatUtils.isDouble(value)) {
                                        cellValue = String.valueOf(value);
                                    } else {
                                        cellValue = String.valueOf(value.longValue());
                                    }
                                }
                            } else {
                                cellValue = cell.getStringCellValue();
                            }

                            output = output.concat(cellValue + parserSeparator);

                        }
                        output = output.concat("\n");
                    }
                }
            }
        } catch (InvalidFormatException e) {
            logger.error("Invalid Format");
        } catch (IllegalStateException e) {
            logger.error("Invalid Format");
        } catch (FileNotFoundException e) {
            logger.error("File not found");
        } catch (IOException e) {
            logger.error("IO Exception");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Format");
        } finally {

        }

        return output;
    }

    private String backupConciliationFile(String backupFilePath, String originalFileName, String serializedFile) {

        String backupFileName = "";

        try {
            backupFileName = System.currentTimeMillis() + "_" + originalFileName;

            /* Saving to file */
            File outputFile = new File(backupFilePath + backupFileName);
            outputFile.getParentFile().mkdirs();

            PrintWriter printWriter = new PrintWriter(outputFile);

            printWriter.print(serializedFile);
            printWriter.close();

        } catch (FileNotFoundException e) {
            logger.error("The conciliation file could not be backed up in the specified path.");
        }

        return backupFileName;

    }

    private PaymentInstitution retrievePaymentInstFromRequest(String id) {

        PaymentInstitution selectedInstitution = new PaymentInstitution();

        try {
            long selectedInstitutionID = Long.parseLong(id);

            selectedInstitution = paymentInstitutionManager.getPaymentInstitution(selectedInstitutionID);

        } catch (Exception e) {
            logger.error("Problems while trying to retrieve Selected Payment Institution from the database");
        }

        return selectedInstitution;
    }

    private Payment updatePayment(Payment payment, ResultadoConciliacion conciliationResult) throws RepositoryException {

        /* New status and New status Description are mandatory */
        String newStatusName = "";
        String newStatusDescription = "";

        try {
            newStatusName = conciliationResult.getNuevoEstado();
            newStatusDescription = conciliationResult.getDetalleEstado();

        } catch (NullPointerException e) {
            logger.error("No se pudo recibir Nuevo Estado o Descripcion del Estado desde el servicio web externo");
        }

        String controlAccount = "";
        String invoiceNumberAsString = "";

        if(conciliationResult.getCuentaControl() != null) {
            controlAccount = conciliationResult.getCuentaControl();
        }

        if(conciliationResult.getNumeroFactura() != null && !conciliationResult.getNumeroFactura().equalsIgnoreCase("")) {
            invoiceNumberAsString = conciliationResult.getNumeroFactura();
        }

        /* If the status doesn't exist, create it */
        if(newStatusName == null || newStatusName.equals("")) {
            newStatusName = "No Conciliado";
        }
        PaymentStatus paymentStatus = paymentStatusManager.getPaymentStatus(newStatusName, newStatusDescription);

        /* Update information retrieved from conciliation */
        payment.setStatus(paymentStatus);
        payment.setInvoiceNumber(invoiceNumberAsString);
        payment.setControlAccount(controlAccount);

        /* Update in DB */
        paymentManager.updateAfterConciliation(payment.getId(), controlAccount, invoiceNumberAsString, paymentStatus);

        return payment;
    }

    private Pago createConciliatorPayment(Payment payment) {

        /* At this point the Payment Object its supposed to be valid or valid with warnings */
        String numeroConvenio = ""; /* Business Line */
        TipoDocumento tipoDocumentoPago = TipoDocumento.valueOf(payment.getPaymentInstitution().getTipoDocumento());
        String numeroDocumento = payment.getDocumentNumber() + "";
        String numeroProducto = "";
        String mesCuota = payment.getPeriodMonth();
        String agnoCuota = payment.getPeriodYear();
        double montoPagado = payment.getPaidAmount();
        Moneda codigoMoneda = Moneda.fromValue(payment.getCurrencyCode().getCode());
        String cuentaControl = ""; /* Not mandatory */


        if(payment.getControlAccount() != null) {
            cuentaControl = payment.getControlAccount();
        }

        if(payment.getBusinessLine() != null && payment.getPaymentInstitution().getIdCupon() != null) {
            // FIXME Get the agreement number from the JPA Entity ConciliationAgreement
            ConciliationAgreement conciliationAgreement = convenioConciliacionManager.getByBusinessLineAndPaymentInstitution(payment.getBusinessLine().getName(), payment.getPaymentInstitution().getId());

            numeroConvenio = conciliationAgreement.getConvenio();
        }

        if(payment.getProductNumber() != null && !payment.getProductNumber().equals("")) {
            numeroProducto = payment.getProductNumber() + "";
        }

        Calendar calendar = payment.getPaymentDate();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(calendar.getTime());
        XMLGregorianCalendar fechaPago = null;

        try {
            fechaPago = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            logger.error("Error converting Calendar to XMLGregorianCalendar");
        }

        return new Pago(numeroConvenio, cuentaControl, tipoDocumentoPago, numeroDocumento,
                numeroProducto, mesCuota, agnoCuota, montoPagado, codigoMoneda, fechaPago);
    }

    private Payment createPaymentFromParsedPayment(PaymentTypePOJO parsedPayment, ConciliationFile conciliationFile) {

        String errors = "";
        String warnings = "";

        String documentNumber = "-99";
        String productNumber = "-99";
        double paidAmount = -1;
        double initialToPayAmount = -1;
        Calendar paymentDate = null;
        Customer customer = null;
        BusinessLine businessLine = null;
        CurrencyCode currencyCode = null;
        String periodMonth = "";
        String periodYear = "";

        /* Validating Document Number */
        if(parsedPayment.getDocumentNumber() != null) {
            documentNumber = parsedPayment.getDocumentNumber();
        } else {
            errors = errors.concat("No se pudo obtener <b>Número de Documento</b>. <br>");
        }

        /* Validating Paid Amount */
        if(parsedPayment.getPaidAmount() != null && parsedPayment.getPaidAmount() >= 0) {
            paidAmount = parsedPayment.getPaidAmount();
        } else {
            errors = errors.concat("No se pudo obtener <b>Monto Pagado</b>. <br>");
        }

        /* Validating Initial to Pay Amount */
        if(parsedPayment.getInitialToPayAmount() != null && parsedPayment.getInitialToPayAmount() >= 0) {
            initialToPayAmount = parsedPayment.getInitialToPayAmount();
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Monto a pagar</b>. <br>");
        }

        /* Validating Payment Date */
        if(parsedPayment.getPaymentDate() != null) {
            paymentDate = Calendar.getInstance();
            Date date = parsedPayment.getPaymentDate().toGregorianCalendar().getTime();
            paymentDate.setTime(date);
        } else {
            errors = errors.concat("No se pudo obtener <b>Fecha de Pago</b>. <br>");
        }

        /* Validating Customer. If this customer doesn't exist, it is created, and persisted */
        if(parsedPayment.getDebtorRUT() == null || parsedPayment.getDebtorName() == null ||
                parsedPayment.getDebtorName().isEmpty() || parsedPayment.getDebtorRUT().isEmpty()) {
            warnings = warnings.concat("No se pudieron obtener datos de <b>Cliente</b>. <br>");
        } else {
            String rutAsString = "";

            try {
                RUT rut = RUT.parseRUT(parsedPayment.getDebtorRUT());
                rutAsString = rut.toString();
                customer = customerManager.getCustomer(parsedPayment.getDebtorName(), rutAsString);

            } catch (InvalidRUTException e) {
                warnings = warnings.concat("No se pudieron obtener datos de <b>Cliente</b>. <br>");
            }
        }

        /* Getting Payment Institution */
        PaymentInstitution paymentInstitution = conciliationFile.getPaymentInstitution();

        if(paymentInstitution == null) {
            warnings = warnings.concat("No se pudo obtener <b>Institución de pago</b>. <br>");
        }

        /* Getting Business Line */
        Integer agreementNumberFromConciliatedPayment = parsedPayment.getAgreementNumber();
        if ((paymentInstitution.getIdCupon() == null || paymentInstitution.getIdCupon().isEmpty()) &&
                agreementNumberFromConciliatedPayment > 0) {
            PaymentInstitution institution =
                    paymentInstitutionManager.getPaymentInstitutionByAgreementNumber(agreementNumberFromConciliatedPayment);
            if (institution != null) {
                paymentInstitution = institution;
                businessLine = institution.getBusinessLine();
            }
        } else {
            BusinessLine businessLine1 = lineasDeNegocioManager.getByName(this.businessLine);
            businessLine = businessLine1;
        }

        /* Validating Currency Code */
        if(parsedPayment.getCurrencyCode() != null) {
            currencyCode = currencyCodeManager.getCurrencyCode(parsedPayment.getCurrencyCode().value());

            if(currencyCode == null) {
                errors = errors.concat("No se pudo obtener <b>Divisa</b>. <br>");
                logger.error("Currency code doesn't exist");
            }
        } else {
            errors = errors.concat("No se pudo obtener <b>Divisa</b>. <br>");
        }

        /* Validating BoletaID */
        if(parsedPayment.getBoletaId() == null) {
            errors = errors.concat("No se pudo obtener <b>ID de pago</b>. <br>");
            logger.error("ID de pago desconocido.");
        } else {
            BotonBoleta botonBoleta = botonBoletaManager.getById(parsedPayment.getBoletaId());

            logger.debug("1 valor: " + parsedPayment.getArchivePaidAmount());
            logger.debug("2 valor: " + parsedPayment.getPaidAmount());

            logger.debug("3 valor: " + (parsedPayment.getArchivePaidAmount().intValue()));
            logger.debug("4 valor: " + (parsedPayment.getPaidAmount().intValue()));

            logger.debug("Comparacion: " + Double.compare(parsedPayment.getArchivePaidAmount(), parsedPayment.getPaidAmount()));

            /*
            if((parsedPayment.getArchivePaidAmount().intValue()) != (parsedPayment.getPaidAmount().intValue())){
                errors = errors.concat("El monto del archivo a conciliar, correspondiente a " + parsedPayment.getArchivePaidAmount() + " es distinto al persistido en la base de datos: " + parsedPayment.getPaidAmount().doubleValue());
                logger.error("El monto del archivo a conciliar, correspondiente a " + parsedPayment.getArchivePaidAmount() + " es distinto al persistido en la base de datos: " + parsedPayment.getPaidAmount().doubleValue());
            }*/
        }

        /* Validating Product Number */
        if(parsedPayment.getProductNumber() != null) {
            try {
                productNumber = parsedPayment.getProductNumber();
            } catch (NumberFormatException e) {
                warnings = warnings.concat("No se pudo obtener <b>Número de Producto</b>. <br>");
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Número de Producto</b>. <br>");
        }

        /* Getting Period month */
        if(parsedPayment.getProductMonth() != null && parsedPayment.getProductMonth() >= 0) {
            int month = parsedPayment.getProductMonth();
            if(month < 1 || month > 12) {
                warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
            } else {
                /* valid month */
                periodMonth = parsedPayment.getProductMonth() + "";
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
        }

        /* Getting Period year */
        if(parsedPayment.getProductYear() != null && parsedPayment.getProductYear() >= 0) {
            int year = parsedPayment.getProductYear();
            if(year < 0 || year > 9999) {
                warnings = warnings.concat("No se pudo obtener <b>Mes producto</b>. <br>");
            } else {
                /* valid year */
                periodYear = parsedPayment.getProductYear() + "";
            }
        } else {
            warnings = warnings.concat("No se pudo obtener <b>Año producto</b>. <br>");
        }

        /* Getting control account */
        /* Not mandatory */
        String controlAccount = parsedPayment.getControlAccount();

        /* Creating Payment Status "creado", "creado with warnings" or "with errors" */
        PaymentStatus paymentStatus;

        if(errors.isEmpty()) {
            if(warnings.isEmpty()) {
                paymentStatus = paymentStatusManager.getPaymentStatus("Creado", "(Estado transitorio)");
            } else {
                paymentStatus = paymentStatusManager.getPaymentStatus("Creado", "(Estado transitorio) Advertencias: <br>"
                        + warnings);
            }
        } else {
            if(parsedPayment.getErrors() == null)
                parsedPayment.setErrors("");

            paymentStatus = paymentStatusManager.getPaymentStatus("No Conciliado (Con errores)",
                    parsedPayment.getErrors().concat(errors));
        }

        /* Creating and persisting payment */
        Payment payment = paymentManager.createPayment(conciliationFile, customer, paymentInstitution,
                businessLine, initialToPayAmount, paidAmount, paymentDate, paymentStatus,
                documentNumber, currencyCode, periodMonth, periodYear, productNumber, controlAccount);

        return payment;
    }

    /**
     * Descarga el excel con el formato a conciliar.
     */
    public void downloadExcelButtonMethod() throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("PagosConError");

        /* The header row */
        createHeaderRow(sheet);

        /* For every payment create a new Row */
        int row = 1;

        for(Payment payment : this.processedPaymentsWithErrors) {
            createPaymentRow(payment, row, sheet);
            ++row;
        }

        for(int i=0; i <= 8; i++) {
            sheet.autoSizeColumn(i);
        }

        long concFileId = this.processedPaymentsWithErrors.get(0).getConciliationFile().getId();

        String filePath = Configurations.getInstance().getParameter("ruta_archivo_resp") +
                "ErroresConciliacion_" + concFileId + ".xls";

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        download(filePath);
    }

    private void createHeaderRow(Sheet sheet) {

        Row headerRow = sheet.createRow(0);

        Cell paymentDateHeaderCell = headerRow.createCell(0);
        paymentDateHeaderCell.setCellValue("Fecha de Pago");

        Cell documentNumberHeaderCell = headerRow.createCell(1);
        documentNumberHeaderCell.setCellValue("Número de Documento (cupón)");

        Cell productNumberHeaderCell = headerRow.createCell(2);
        productNumberHeaderCell.setCellValue("Número de Producto (ej. póliza)");

        Cell agreementNumberHeaderCell = headerRow.createCell(3);
        agreementNumberHeaderCell.setCellValue("Número de Convenio");

        Cell productMonthHeaderCell = headerRow.createCell(4);
        productMonthHeaderCell.setCellValue("Mes Producto");

        Cell productYearHeaderCell = headerRow.createCell(5);
        productYearHeaderCell.setCellValue("Año Producto");

        Cell currencyHeaderCell = headerRow.createCell(6);
        currencyHeaderCell.setCellValue("Divisa");

        Cell paidAmountHeaderCell = headerRow.createCell(7);
        paidAmountHeaderCell.setCellValue("Monto Pagado");

        Cell errorDescriptionHeaderCell = headerRow.createCell(8);
        errorDescriptionHeaderCell.setCellValue("Descripción Error");
    }

    private void createPaymentRow(Payment payment, int row, Sheet sheet) {
        Row dataRow = sheet.createRow(row);

        /* Payment Date */
        Cell dateCell = dataRow.createCell(0);
        Cell documentNumberCell = dataRow.createCell(1);
        Cell productNumberCell = dataRow.createCell(2);
        Cell agreementNumberCell = dataRow.createCell(3);
        Cell periodMonthCell = dataRow.createCell(4);
        Cell periodYearCell = dataRow.createCell(5);
        Cell currencyCell = dataRow.createCell(6);
        Cell paidAmountCell = dataRow.createCell(7);
        Cell errorDescriptionCell = dataRow.createCell(8);

        if (payment.getPaymentDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(payment.getPaymentDate().getTime());
            dateCell.setCellValue(date);
        } else {
            dateCell.setCellValue("");
        }

        /* Document Number */
        if (payment.getDocumentNumber() != null) {
            documentNumberCell.setCellValue(payment.getDocumentNumber());
        } else {
            documentNumberCell.setCellValue("");
        }

        /* Product Number */
        if (payment.getProductNumber() != null && !payment.getProductNumber().equals("")) {
            productNumberCell.setCellValue(payment.getProductNumber());
        } else {
            productNumberCell.setCellValue("");
        }

        /* Agreement Number */
        /*if (payment.getBusinessLine() != null && !payment.getPaymentInstitution().getCouponIdentifier().equals("")) {
            agreementNumberCell.setCellValue(payment.getPaymentInstitution().getCouponIdentifier());
        } else {
            agreementNumberCell.setCellValue("");
        }*/ // FIXME COMENTADO PARA COMPILAR

        /* Product Month */
        try {
            if (Integer.parseInt(payment.getPeriodMonth()) >= 0) {
                periodMonthCell.setCellValue(payment.getPeriodMonth());
            } else {
                periodMonthCell.setCellValue("");
            }
        } catch (NumberFormatException e) {
            periodMonthCell.setCellValue("");
        }

        /* Product Year */
        try {
            if (Integer.parseInt(payment.getPeriodYear()) >= 0) {
                periodYearCell.setCellValue(payment.getPeriodYear());
            } else {
                periodYearCell.setCellValue("");
            }
        } catch (NumberFormatException e) {
            periodYearCell.setCellValue("");
        }

        /* Currency */
        if (payment.getCurrencyCode() != null) {
            currencyCell.setCellValue(payment.getCurrencyCode().getCode());
        } else {
            currencyCell.setCellValue("");
        }

        /* Paid Amount */
        paidAmountCell.setCellValue(payment.getExportablePaidAmount());

        /* Error Description */
        errorDescriptionCell.setCellValue(payment.getStatus().getExportableDescription());
    }

    private void download(String filePath)
            throws IOException {

        /* Reads input file from an absolute path */
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);


        // ----

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String mimeType = ec.getMimeType(filePath);

        if (mimeType == null) {
            /* Set to binary type if MIME mapping not found */
            mimeType = "application/octet-stream";
        }
        logger.debug("MIME type: " + mimeType);

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(mimeType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength((int) downloadFile.length()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream outStream = ec.getResponseOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }

    public void subjectSelectionChanged(final AjaxBehaviorEvent event){
        if(businessLine != null && !businessLine.equals("") ){
            this.paymentInstitutions = new ArrayList<PaymentInstitution>();
            List<ConciliationAgreement> list = convenioConciliacionManager.getByBusinessLineName(businessLine);

            for (ConciliationAgreement conciliationAgreement : list) {
                this.paymentInstitutions.add(conciliationAgreement.getPaymentInstitution());
            }
        }
    }

    public List<PaymentInstitution> getPaymentInstitutions(){
        return this.paymentInstitutions;
    }

    public void setPaymentInstitutions(List<PaymentInstitution> paymentInstitutions) {
        this.paymentInstitutions = paymentInstitutions;
    }

    public List<BotonLineasDeNegocio> getBusinessLines(){
        // FIXME Aquí sólo se deben mostrar las lineas de negocio asociadas al usuario.
        return botonLineasDeNegocioManager.findAll();
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getPaymentInstitutionId() {
        return paymentInstitutionId;
    }

    public void setPaymentInstitutionId(String paymentInstitutionId) {
        this.paymentInstitutionId = paymentInstitutionId;
    }

    public List<Payment> getProcessedPayments() {
        return processedPayments;
    }

    public void setProcessedPayments(List<Payment> processedPayments) {
        this.processedPayments = processedPayments;
    }

    public List<Payment> getProcessedPaymentsAlreadyConciliated() {
        return processedPaymentsAlreadyConciliated;
    }

    public void setProcessedPaymentsAlreadyConciliated(List<Payment> processedPaymentsAlreadyConciliated) {
        this.processedPaymentsAlreadyConciliated = processedPaymentsAlreadyConciliated;
    }

    public List<Payment> getProcessedPaymentsWithErrors() {
        return processedPaymentsWithErrors;
    }

    public void setProcessedPaymentsWithErrors(List<Payment> processedPaymentsWithErrors) {
        this.processedPaymentsWithErrors = processedPaymentsWithErrors;
    }
}