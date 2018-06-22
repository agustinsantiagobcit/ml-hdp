package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.UserDAO;
import cl.metlife.conciliacion.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserManager {

    @EJB
    UserDAO dao;

    private static final Logger LOGGER = Logger.getLogger(UserManager.class);

    public User getUserByRUT(String userRUT) {
        return dao.getUserByRUT(userRUT);
    }

}