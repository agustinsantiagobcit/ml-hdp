package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.ConciliatorConnectorDAO;
import cl.metlife.conciliacion.domain.ConciliatorConnector;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ConciliatorConnectorManager {

    @EJB
    ConciliatorConnectorDAO dao;

    private static final Logger LOGGER = Logger.getLogger(ConciliatorConnectorManager.class);

    /* Every payment institution has a specific parser */
    public List<ConciliatorConnector> getAllConnectors() {
        return dao.getAllConnectors();
    }

}