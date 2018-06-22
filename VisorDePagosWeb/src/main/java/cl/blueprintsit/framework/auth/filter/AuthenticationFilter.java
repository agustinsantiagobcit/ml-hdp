package cl.blueprintsit.framework.auth.filter;

import cl.blueprintsit.framework.Constants;
import cl.blueprintsit.framework.auth.AuthenticationBean;
import cl.blueprintsit.framework.auth.Capacity;
import cl.blueprintsit.framework.auth.User;
import cl.metlife.visorpagos.domain.VPCapacity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
public class AuthenticationFilter implements Filter{


    static private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("-- FILTERING --");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (  req.getRequestURI().contains(Constants.LOGIN_PAGE) || req.getRequestURI().contains(Constants.ERRORS_FOLDER) || hasPermission(req)) {
            chain.doFilter(request, response);
        } else if (!isLoggedIn(req)) { //perdio la sesion
            res.sendRedirect(req.getContextPath() +"/" + Constants.VIEWS_FOLDER+ "/" + Constants.LOGIN_PAGE + "?viewExpired=true&originalURI="+req.getRequestURI());
        } else if(!hasPermission(req)) {//no tiene permiso para acceder a la pagina solicitada
            res.sendRedirect(req.getContextPath() +"/" + Constants.VIEWS_FOLDER+ "/" + Constants.ERRORS_FOLDER+ "/" + Constants.AUTH_ERROR_PAGE );
        } else {//otros casos que nunca deberian darse
            res.sendRedirect(req.getContextPath() + "/" + Constants.VIEWS_FOLDER+ "/"  + Constants.LOGIN_PAGE );
        }

        logger.info("-- END-FILTERING --");
    }

    private boolean isLoggedIn(HttpServletRequest req) {
        return req.getSession().getAttribute(AuthenticationBean.AUTH_KEY) != null;
    }

    private boolean hasPermission(HttpServletRequest req) {
        if (!isLoggedIn(req))
            return false;

        AuthenticationBean auth = (AuthenticationBean) req.getSession().getAttribute("authenticationBean");
        User u = auth.getLoggedUser();

        return u != null && checkPermission(u, req.getRequestURI());

    }

    private boolean checkPermission(User u, String requestURI) {
        for (Capacity capacity : u.getCapacities()) {
            if(requestURI.contains("home.xhtml")){
                return true;
            } else if(requestURI.contains("payments.xhtml") && capacity.getId() == VPCapacity.VISOR_DE_PAGOS || requestURI.contains("conciliated/payments.xhtml") && capacity.getId() == VPCapacity.VISOR_DE_PAGOS
                    || requestURI.contains("conciliated/pagos.xhtml") && capacity.getId() == VPCapacity.VISOR_DE_PAGOS){
                return true;
            } else if(requestURI.contains("conciliator/make.xhtml") && capacity.getId() == VPCapacity.CONCILIADOR || requestURI.contains("conciliator/results.xhtml") && capacity.getId() == VPCapacity.CONCILIADOR){
                return true;
            } else if(requestURI.contains("graphs/search.xhtml") && capacity.getId() == VPCapacity.GRAFICOS) {
                return true;
            }
        }

        return false;
    }

    public void destroy() { }

    public void init(FilterConfig arg0) throws ServletException { }


}


