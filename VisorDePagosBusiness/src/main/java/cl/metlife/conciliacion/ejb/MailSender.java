package cl.metlife.conciliacion.ejb;

import cl.metlife.conciliacion.business.ConciliationLog;
import cl.metlife.conciliacion.externals.mailws.MailSenderService;
import cl.metlife.conciliacion.domain.ConciliationFile;
import cl.metlife.conciliacion.domain.User;
import cl.metlife.conciliacion.managers.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless(mappedName = "MailSender")
@Local(MailSenderLocal.class)
public class MailSender implements MailSenderLocal {

    private static final Logger LOGGER = Logger.getLogger(MailSenderLocal.class);

    @EJB
    LogManager logDAO;

    @Asynchronous
    public void sendReportMail(int processed, ConciliationFile conciliationFile, String host, String port,
                               User user) {

        LOGGER.debug("Sending report email.");

        String link = "Se han procesado " + processed + " pagos en el sistema de Conciliación. <br><br> " +
                "Para revisarlos haga <a href='http://" + host + ":" + port + "/conciliacion/report?";
        String idKey = "id=" + conciliationFile.getId();
        String concFileName = conciliationFile.getFileName();
        String idTS = "ts=" + concFileName;

        link += idKey + "&" + idTS + "'>click aqu&iacute;</a>";

        cl.metlife.conciliacion.externals.mailws.MailSender sender = (new MailSenderService()).getMailSenderPort();
        sender.sendMail("Reporte Conciliación", user.getUserMail(), link, "HTML");

        LOGGER.debug("Report mail sent asynchronously");
        ConciliationLog.getInstance(logDAO).reportMailSentLog(conciliationFile, user);
    }

}
