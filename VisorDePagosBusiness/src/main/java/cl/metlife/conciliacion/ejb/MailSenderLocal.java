package cl.metlife.conciliacion.ejb;

import cl.metlife.conciliacion.domain.ConciliationFile;
import cl.metlife.conciliacion.domain.User;

import javax.ejb.Local;

@Local
public interface MailSenderLocal {

    public void sendReportMail(int processed, ConciliationFile conciliationFile, String host, String port,
                               User user);
}
