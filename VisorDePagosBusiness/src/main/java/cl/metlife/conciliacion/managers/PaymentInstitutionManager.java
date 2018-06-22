package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.*;
import cl.metlife.conciliacion.domain.PaymentInstitution;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class PaymentInstitutionManager {

    @EJB
    PaymentInstitutionDAO dao;

    private static final Logger LOGGER = Logger.getLogger(PaymentInstitutionManager.class);

    public List<PaymentInstitution> getAllPaymentInstitutions() {
        return dao.getAllPaymentInstitutions();
    }

    public PaymentInstitution getPaymentInstitution(long paymentInstitutionID) {
        return dao.getPaymentInstitution(paymentInstitutionID);
    }

    public PaymentInstitution getPaymentInstitutionByAgreementNumber(Integer agreementNumber) {
        return dao.getPaymentInstitutionByAgreementNumber(agreementNumber);
    }
}