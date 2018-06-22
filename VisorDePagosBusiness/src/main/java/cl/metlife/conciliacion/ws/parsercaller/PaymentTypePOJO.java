package cl.metlife.conciliacion.ws.parsercaller;


import javax.xml.datatype.XMLGregorianCalendar;

public class PaymentTypePOJO {

    protected String documentNumber;
    protected String productNumber;
    protected Integer productMonth;
    protected Integer productYear;
    protected Double initialToPayAmount;
    protected XMLGregorianCalendar paymentDate;
    protected Double paidAmount;
    protected Moneda currencyCode;
    protected String debtorRUT;
    protected String debtorName;
    protected String controlAccount;
    protected String errors;
    protected Integer agreementNumber;
    // Adds
    protected Long boletaId;
    protected Double archivePaidAmount;
    protected String periodo;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getProductMonth() {
        return productMonth;
    }

    public void setProductMonth(Integer productMonth) {
        this.productMonth = productMonth;
    }

    public Integer getProductYear() {
        return productYear;
    }

    public void setProductYear(Integer productYear) {
        this.productYear = productYear;
    }

    public Double getInitialToPayAmount() {
        return initialToPayAmount;
    }

    public void setInitialToPayAmount(Double initialToPayAmount) {
        this.initialToPayAmount = initialToPayAmount;
    }

    public XMLGregorianCalendar getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(XMLGregorianCalendar paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Moneda getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Moneda currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDebtorRUT() {
        return debtorRUT;
    }

    public void setDebtorRUT(String debtorRUT) {
        this.debtorRUT = debtorRUT;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getControlAccount() {
        return controlAccount;
    }

    public void setControlAccount(String controlAccount) {
        this.controlAccount = controlAccount;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Integer getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(Integer agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Long getBoletaId() {
        return boletaId;
    }

    public void setBoletaId(Long boletaId) {
        this.boletaId = boletaId;
    }

    public Double getArchivePaidAmount() {
        return archivePaidAmount;
    }

    public void setArchivePaidAmount(Double archivePaidAmount) {
        this.archivePaidAmount = archivePaidAmount;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
