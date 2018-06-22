package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.CLogDAO;
import cl.metlife.conciliacion.domain.*;
import cl.metlife.hdp.dao.LogDAO;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;

@Stateless
@LocalBean
public class LogManager {

    @EJB
    CLogDAO dao;

    private static final Logger LOGGER = Logger.getLogger(LogManager.class);

    /**
     * This method creates a business Log in the DB
     * @param level
     * @param description
     * @param conciliationFile
     * @param user
     * @param customer
     * @param paymentInstitution
     * @param businessLine
     * @param payment
     * @param prevStatus
     * @param newStatus
     */
    public void registerLog(String level, String description, ConciliationFile conciliationFile, User user,
                           Customer customer, PaymentInstitution paymentInstitution, BusinessLine businessLine,
                           Payment payment, String prevStatus, String newStatus, long processTime) {

        dao.registerLog(level, description, conciliationFile, user, customer, paymentInstitution, businessLine, payment, prevStatus, newStatus, processTime);
    }

}
