package cl.blueprintsit.framework.config;

import cl.blueprintsit.framework.persistence.dao.VPCapacityDAO;
import cl.metlife.visorpagos.domain.VPCapacity;
import cl.metlife.visorpagos.domain.VPUser;
import cl.blueprintsit.framework.persistence.dao.VPUserDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@Stateless
public class VPUserManager {

    @EJB
    VPUserDAO userDAO;



    @EJB
    VPCapacityDAO capacityDAO;

    private List<VPCapacity> allCapacities;

    public VPUser getByName(String name) {
        return userDAO.getByUsername(name);
    }

    public VPUser createUser(VPUser user) {

        try {
            return userDAO.create(user);
        } catch (VPUserDAO.UserExistsException e) {
            return null;
        }
    }

    public VPUser updateUser(VPUser user) {
        VPUser toUpdate = getByName(user.getUsername());

        toUpdate.setBotonLineas(user.getBotonLineas());
        toUpdate.setLineas(user.getLineas());

        try {
            return userDAO.update(user);
        } catch (Exception e) {
            return null;
        }
    }

    public List<VPUser> findAll(){
        return userDAO.findAll();
    }

    public List<VPCapacity> findAllCapacities(){
        return capacityDAO.findAll();
    }

    public VPUser getByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    public boolean delete(VPUser editItem) {
        return userDAO.delete(editItem);
    }

    public void create(VPUser editItem) throws VPUserDAO.UserExistsException {
        userDAO.create(editItem);
    }

    public void update(VPUser editItem) {
        userDAO.update(editItem);
    }

    public VPCapacity getCapacityById(Long id) {
        return capacityDAO.getById(id);
    }

    public List<VPCapacity> getAllCapacities() {
        return capacityDAO.findAll();
    }
}
