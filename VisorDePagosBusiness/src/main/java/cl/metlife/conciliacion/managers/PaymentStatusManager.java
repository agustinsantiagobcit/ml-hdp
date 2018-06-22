package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.PaymentStatusDAO;
import cl.metlife.conciliacion.domain.PaymentStatus;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@LocalBean
public class PaymentStatusManager {

    @EJB
    PaymentStatusDAO dao;

    private static final Logger LOGGER = Logger.getLogger(PaymentStatusManager.class);

    /**
     * Retrieve Payment Status by ID
     * @param id The status ID
     * @return PaymentStatus
     */
    public PaymentStatus getPaymentStatus(long id) {
        return dao.getPaymentStatus(id);
    }

    public PaymentStatus createPaymentStatus(String name, String description) {
        return dao.createPaymentStatus(name, description);
    }

    /**
     * Retrieve Payment Status or create one if it doesn't exist
     * @param name The status name that its supposed to be unique
     * @return PaymentStatus
     */
    public PaymentStatus getPaymentStatus(String name, String description) {
        return dao.getPaymentStatus(name, description);
    }

    /**
     * Retrieve Payment Status by name (first obtained)
     * @param name The status name
     * @return Payment Status
     */
    public PaymentStatus getPaymentStatus(String name) {
        return dao.getPaymentStatus(name);
    }

    /**
     * Retrieve all possible Payment Status
     * @return Payment Status List
     */
    public List<PaymentStatus> getAllPaymentStatus() {
        return dao.getAllPaymentStatus();
    }

    /**
     * Retrieve Payment Status for Searching
     * @return Payment Status List
     */
    public List<String> getPaymentStatusNames() {
        return dao.getPaymentStatusNames();
    }

}