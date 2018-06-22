package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.*;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.Map;

@Stateless
@LocalBean
public class ApplicationConfigManager {

    private static final Logger LOGGER = Logger.getLogger(ApplicationConfigManager.class);

    @EJB
    private ApplicationConfigDAO dao;

    public Map<String, String> getAllConfigurations() {
        return dao.getAllConfigurations();
    }

}