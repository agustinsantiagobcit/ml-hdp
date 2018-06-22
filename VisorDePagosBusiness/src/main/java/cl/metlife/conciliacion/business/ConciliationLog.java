package cl.metlife.conciliacion.business;


import cl.metlife.conciliacion.domain.*;
import cl.metlife.conciliacion.managers.LogManager;
import cl.metlife.conciliacion.ws.parsercaller.PaymentType;
import cl.metlife.conciliacion.ws.parsercaller.PaymentTypePOJO;

public class ConciliationLog {

    private static ConciliationLog instance;
    private static LogManager logManager;

    protected ConciliationLog() {
    }

    public static ConciliationLog getInstance(LogManager dao) {
        if (instance == null){
            instance = new ConciliationLog();
        }
        if (logManager == null) {
            logManager = dao;
        }
        return instance;
    }

    public void appStartLog() {
        logManager.registerLog("Info", "La aplicación Conciliación ha sido iniciada", null, null, null, null, null, null,
                "", "Aplicación iniciada", 0);
    }

    public void configLoadErrorLog() {
        logManager.registerLog("Error", "No se pudieron cargar las configuraciones iniciales desde la base de datos. " +
                        "La aplicación no funcionará correctamente", null, null, null, null, null, null,
                        "", "Configuración inicial no obtenida.", 0);
    }

    public void fileUploadedLog(String fileName, User user) {
        logManager.registerLog("Info", "El archivo " + fileName + " ha sido correctamente cargado.", null, user, null,
                           null, null, null, "", "Archivo cargado", 0);
    }

    public void fileNotUploadedLog(String message, User user) {

        logManager.registerLog("Error", "Se intentó cargar un archivo pero no se pudo. " + message, null, user, null,
                           null, null, null, "", "Archivo no cargado", 0);

    }

    public void parseSuccessLog(ConciliationFile conciliationFileDescriptor, User user,
                                PaymentInstitution paymentInstitution) {

        System.out.println("Info" + "El archivo " + conciliationFileDescriptor.getFileName() + " ha sido \"Parseado\" " +
                        "correctamente. " + "user: " + user.getUserName() + ", Institucion: " + paymentInstitution.getName() + "Archivo parseado correctamente");

        logManager.registerLog("Info", "El archivo " + conciliationFileDescriptor.getFileName() + " ha sido \"Parseado\" " +
                        "correctamente. ", conciliationFileDescriptor, user, null, paymentInstitution, null, null,
                        "Archivo cargado", "Archivo parseado correctamente", 0);
    }

    public void parseErrorLog(String string, PaymentInstitution paymentInstitution, String fileName) {

        logManager.registerLog("Error", "El archivo " + fileName + " no ha sido \"Parseado\". User " +string,
                null, null, null, paymentInstitution, null, null, "Archivo cargado", "Archivo no parseado", 0);
    }

    public void paymentConciliationErrorLog(PaymentTypePOJO parsedPayment, ConciliationFile conciliationFile,
                                            User user, Payment payment) {

        logManager.registerLog("Error", "El pago con numero de cupón " + parsedPayment.getDocumentNumber() +
                        " y código de convenio " + parsedPayment.getAgreementNumber() +
                        " no se ha podido conciliar debido a fallo en conexión con el sistema externo de negocio. " ,
                        conciliationFile, user, payment.getCustomer(), conciliationFile.getPaymentInstitution(),
                        payment.getBusinessLine(), payment, "Archivo parseado correctamente", "Pago no conciliado", 0);
    }

    public void paymentConciliatedLog(PaymentTypePOJO parsedPayment, long processTime,
                                      ConciliationFile conciliationFile, User user, Payment payment) {

        logManager.registerLog("Info", "El pago con número de cupón " + parsedPayment.getDocumentNumber() +
                        " y código de convenio " + parsedPayment.getAgreementNumber() + " ha cambiado de estado " +
                        "luego de ser conciliado. El sistema externo de negocio ha demorado" +
                        processTime + " milisegundos.  ", conciliationFile, user,
                payment.getCustomer(), conciliationFile.getPaymentInstitution(), payment.getBusinessLine(),
                payment, "Archivo parseado correctamente", "Pago conciliado", processTime);
    }

    public void paymentPartiallyConciliatedLog(PaymentTypePOJO parsedPayment, long processTime,
                                      ConciliationFile conciliationFile, User user, Payment payment) {

        logManager.registerLog("Info", "El pago con número de cupón " + parsedPayment.getDocumentNumber() +
                        " y código de convenio " + parsedPayment.getAgreementNumber() + " ha cambiado de estado " +
                        "luego de ser conciliado. El sistema externo de negocio ha demorado " +
                        processTime + " milisegundos. ", conciliationFile, user,
                payment.getCustomer(), conciliationFile.getPaymentInstitution(), payment.getBusinessLine(),
                payment, "Archivo parseado correctamente", "Pago parcialmente conciliado", processTime);
    }

    public void paymentConciliationErrorResponseLog(PaymentTypePOJO parsedPayment, ConciliationFile conciliationFile,
                                            User user, Payment payment, long processTime) {

        logManager.registerLog("Error", "El pago con numero de cupón " + parsedPayment.getDocumentNumber() +
                        " y código de convenio " + parsedPayment.getAgreementNumber() +
                        " no se ha podido conciliar debido a fallo que el sistema externo respondió un error. ",
                conciliationFile, user, payment.getCustomer(), conciliationFile.getPaymentInstitution(),
                payment.getBusinessLine(), payment, "Archivo parseado correctamente", "Pago no conciliado", processTime);
    }

    public void reportMailSentLog(ConciliationFile conciliationFile, User user) {

        logManager.registerLog("Info", "Se enviará un correo con el reporte de conciliación a " + user.getUserMail(),
                conciliationFile, user, null, null, null, null, "Pago conciliado", "Reporte enviado", 0);
    }

}
