package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.ParserConfigDAO;
import cl.metlife.conciliacion.domain.ParserConfig;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ParserConfigManager {

    @EJB
    ParserConfigDAO dao;

    private static final Logger LOGGER = Logger.getLogger(ParserConfigManager.class);

    /* Every payment institution has a specific parser */
    public Map<String, String> getParserConfigurations(long paymentInstitutionID) {
        return dao.getParserConfigurations(paymentInstitutionID);
    }

}