package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.BusinessLineDAO;
import cl.metlife.conciliacion.domain.BusinessLine;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@LocalBean
public class BusinessLineManager {

    @EJB
    BusinessLineDAO dao;

    private static final Logger LOGGER = Logger.getLogger(BusinessLineManager.class);

    public BusinessLine getBusinessLineByID(long businessLineID) {
        return dao.getBusinessLineByID(businessLineID);
    }

    public List<BusinessLine> getAllBusinessLines() {
        return dao.getAllBusinessLines();
    }

    public List<String> getBusinessLinesNames() {
        return dao.getBusinessLinesNames();
    }

}