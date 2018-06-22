package cl.blueprintsit.framework.config;


import cl.metlife.visorpagos.domain.VPConfiguration;
import cl.blueprintsit.framework.persistence.dao.VPConfigurationDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@Stateless
public class VPConfigurationManager {

    @EJB
    VPConfigurationDAO dao;

    public VPConfiguration getByKey(String key) {
        return dao.getByKey(key);
    }

    public List<VPConfiguration> findAll() {
        return dao.findAll();
    }

    public VPConfiguration create(VPConfiguration configuration) {
        return dao.create(configuration);
    }

    public VPConfiguration update(VPConfiguration configuration) {
        return dao.update(configuration);
    }

    public void delete(VPConfiguration configuration) {
        dao.delete(configuration);
    }


}
