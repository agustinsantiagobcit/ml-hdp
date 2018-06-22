package cl.metlife.hdp.botonpago.listener;

import javax.ejb.Init;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Ivan on 03-08-2014.
 */
public class PaymentSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        String id = httpSessionEvent.getSession().getId();
        HttpSession session = httpSessionEvent.getSession();
        httpSessionEvent.getSession().getServletContext().setAttribute("SESSION"+id, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String id = httpSessionEvent.getSession().getId();
        httpSessionEvent.getSession().getServletContext().removeAttribute("SESSION"+id);
    }
}
