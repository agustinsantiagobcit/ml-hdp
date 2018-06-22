package cl.metlife.conciliacion.managers;

import cl.metlife.conciliacion.dao.ConciliationFileDAO;
import cl.metlife.conciliacion.domain.ConciliationFile;
import cl.metlife.conciliacion.domain.PaymentInstitution;
import cl.metlife.conciliacion.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ConciliationFileManager {

    @EJB
    ConciliationFileDAO dao;

    private static final Logger LOGGER = Logger.getLogger(ConciliationFileManager.class);


    public ConciliationFile getConciliationFile(long conciliationFileID) {
        return dao.getConciliationFile(conciliationFileID);
    }

    public ConciliationFile createConciliationFile(String fileName, String filePath,
                                                   PaymentInstitution paymentInstitution, User user) {
        return dao.createConciliationFile(fileName, filePath, paymentInstitution, user);
    }

    public void markAsProcessed(long conciliationFileID) {
        dao.markAsProcessed(conciliationFileID);
    }
}