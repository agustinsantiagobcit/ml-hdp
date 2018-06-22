package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.CurrencyCodeDAO;
import cl.metlife.conciliacion.domain.CurrencyCode;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CurrencyCodeManager {

    @EJB
    CurrencyCodeDAO dao;

    private static final Logger LOGGER = Logger.getLogger(CurrencyCodeManager.class);

    public CurrencyCode getCurrencyCode(long currencyID) {
        return dao.getCurrencyCode(currencyID);
    }

    public CurrencyCode getCurrencyCode(String currencyCode) {
        return dao.getCurrencyCode(currencyCode);
    }
}