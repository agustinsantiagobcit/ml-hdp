package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.PaymentDAO;
import cl.metlife.conciliacion.domain.*;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;

@Stateless
@LocalBean
public class PaymentManager {

    @EJB
    PaymentDAO dao;

    private static final Logger LOGGER = Logger.getLogger(PaymentManager.class);

    public Payment getPayment(long paymentID) {
        return dao.getPayment(paymentID);
    }

    public Payment getAlreadyConciliated(String productNumber, String documentNumber) {
        return dao.getAlreadyConciliated(productNumber, documentNumber);
    }

    public List<Payment> retrieveReportPayments(ConciliationFile conciliationFile) {
        return dao.retrieveReportPayments(conciliationFile);
    }

    public Payment createPayment(ConciliationFile conciliationFile, Customer customer,
                              PaymentInstitution paymentInstitution, BusinessLine businessLine,
                              double initialToPay, double paidAmount, Calendar paymentDate, PaymentStatus paymentStatus, String documentNumber, CurrencyCode currencyCode, String periodMonth, String periodYear, String productNumber, String controlAccount) {

        return dao.createPayment(conciliationFile, customer, paymentInstitution,
                businessLine, initialToPay, paidAmount, paymentDate, paymentStatus,
                documentNumber, currencyCode, periodMonth, periodYear, productNumber,
                controlAccount);
    }

    public void updateAfterConciliation(long paymentID, String controlAccount, String invoiceNumber,
                                        PaymentStatus paymentStatus) {
        dao.updateAfterConciliation(paymentID, controlAccount, invoiceNumber, paymentStatus);
    }

    public void updateStatus(long paymentID, PaymentStatus paymentStatus) {
        dao.updateStatus(paymentID, paymentStatus);
    }

    public List<Payment> searchUsingCriteria(Calendar dateFrom, Calendar dateUntil, String periodMonth, String periodYear,
                                             String paymentInstitution, String businessLineName, String paymentStatus) {
        return dao.searchUsingCriteria(dateFrom, dateUntil, periodMonth, periodYear, paymentInstitution, businessLineName, paymentStatus);
    }

    public List<Payment> retrieveReportConciliatedPayments(long conciliationFileID, String conciliationFileName,
                                                           String statusName) throws FailedReportParametersException {
        return retrieveReportConciliatedPayments(conciliationFileID, conciliationFileName, statusName);
    }
}