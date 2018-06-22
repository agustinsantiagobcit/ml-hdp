package cl.blueprintsit.framework.auth;

import cl.blueprintsit.framework.Constants;
import cl.blueprintsit.framework.config.*;
import cl.metlife.conciliacion.domain.BusinessLine;
import cl.metlife.conciliacion.managers.BusinessLineManager;
import cl.metlife.hdp.domain.BotonLineasDeNegocio;
import cl.metlife.hdp.managers.BotonLineasDeNegocioManager;
import cl.metlife.visorpagos.domain.VPCapacity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@ManagedBean(name = "authenticationBean")
@SessionScoped
public class AuthenticationBean {

    static public final String AUTH_KEY = "bp.session.user";

    static private final Logger logger = LoggerFactory.getLogger(AuthenticationBean.class);


    @EJB(name = "AuthenticationManagerEJB")
    private AuthenticationManager authenticationManager;

    @EJB(name = "AuthorizationManagerEJB")
    private AuthorizationManager authorizationManager;

    @EJB
    VPUserManager userManager;

    @EJB
    VPConfigurationManager vPConfigurationBean;

    @EJB
    BotonLineasDeNegocioManager botonLineasDeNegocioManager;

    @EJB
    BusinessLineManager businessLineManager;

    private String username;
    private String password;

    private User loggedUser;


    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY) != null;
    }


    public void login() {
        try {

            String enableService = vPConfigurationBean.getByKey("enable.metlife.auth").getValor();

            String metlifeResponse = "NOOK";
            //valida user y pass
            if("true".equals(enableService)) {
                metlifeResponse = authenticationManager.authenticate(username, password);
            }

            loggedUser = authorizationManager.getUser(username, password);

            if("true".equals(enableService)){
                if("NOK".equals(metlifeResponse)){
                    if(loggedUser == null)
                        throw new AuthenticationException("Nombre de usuario y/o contraseña incorrectos");
                }
            }

            //quitar password de la memoria
            password=null;

            authorizationManager.markLogin(username, password);

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put(AUTH_KEY, username);

            //redirigir a pagina de inicio
            context.redirect(context.getRequestContextPath() + Constants.HOME_PAGE);

            logger.info("Usuario [{}] ha iniciado sesión.",username);

        } catch (AuthenticationException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre de usuario y/o contraseña incorrecta.", "Nombre de usuario y/o contraseña incorrecta."));
            logger.error("Error trying to login", e);
        }
        catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error intentando redirigir usuario a página de Inicio.", e.getMessage()));
            logger.error("Error intentando redirigir usuario a página de inicio {}", Constants.HOME_PAGE , e);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al Ingresar. Consulte con su administrador", e.getMessage()));
            logger.error("Error trying to login", e);

        }
    }

    private List<cl.blueprintsit.framework.auth.Capacity> makeCapacities(List<VPCapacity> capacities) {
        List<cl.blueprintsit.framework.auth.Capacity> response = new ArrayList<cl.blueprintsit.framework.auth.Capacity>();

        for (VPCapacity capacity : capacities) {
            cl.blueprintsit.framework.auth.Capacity cap = new cl.blueprintsit.framework.auth.Capacity();

            cap.setName(capacity.getName());
            cap.setDescription(capacity.getDescription());

            response.add(cap);
        }

        return response;
    }

    private boolean canLogin(User loggedUser) {
        return !(loggedUser == null || loggedUser.getUsername() == null);
    }

    public void logout() {
        logger.info("Usuario: " + username + " ha cerrado su sesión.");

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSessionMap().remove(AUTH_KEY);

        username = null;
        password = null;
        loggedUser = null;

        try {
            context.redirect(context.getRequestContextPath() + "/" +Constants.VIEWS_FOLDER+ "/" + Constants.LOGIN_PAGE );
        } catch (IOException e) {
            logger.error("Error en logout", e);
        }

    }

    public void testException() {
        logger.debug("Throwing test exception");
        throw new RuntimeException("This is a test exception");
    }

    public List<BotonLineasDeNegocio> getUserBotonLineasNegocio(){
        List<BotonLineasDeNegocio> allLines = botonLineasDeNegocioManager.findAll();
        List<BotonLineasDeNegocio> userLines = new ArrayList<BotonLineasDeNegocio>();

        for (BotonLineaNegocio buttonLine : this.loggedUser.getBotonLineas()) {
            for (BotonLineasDeNegocio line : allLines) {
                if(buttonLine.getName().equals(line.getNombre())){
                    userLines.add(line);
                    break;
                }

            }

        }

        return userLines;
    }

    public List<BusinessLine> getUserBusinessLines(){
        List<BusinessLine> allLines = businessLineManager.getAllBusinessLines();
        List<BusinessLine> userLines = new ArrayList<BusinessLine>();

        for (LineaNegocio buttonLine : this.loggedUser.getLinea()) {
            for (BusinessLine line : allLines) {
                if(buttonLine.getName().equals(line.getName())){
                    userLines.add(line);
                    break;
                }

            }

        }

        return userLines;
    }

    public boolean isAuthorizedByViewId(int number){
        for (Capacity o : loggedUser.getCapacities()) {
            if(o.getId() == number)
                return true;
        }

        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }



}
