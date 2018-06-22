package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.CustomerDAO;
import cl.metlife.conciliacion.domain.Customer;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CustomerManager {

    @EJB
    CustomerDAO dao;

    private static final Logger LOGGER = Logger.getLogger(CustomerManager.class);

    public Customer getCustomerByID(long customerID) {
        return dao.getCustomerByID(customerID);
    }

    private Customer createCustomer(String name, String rut) {
        return dao.createCustomer(name, rut);
    }

    /**
     * Retrieve Customer by rut and name
     * @param rut The Customer rut
     * @param name The Customer name
     * @return
     */
    public Customer getCustomer(String name, String rut) {
        return dao.getCustomer(name, rut);
    }

    public void updateMail(String name, String rut, String mail) {
        dao.updateMail(name, rut, mail);
    }
}