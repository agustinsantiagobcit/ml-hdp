package cl.qr.beans;

import cl.qr.business.ExampleBusiness;
import cl.qr.business.PaymentManager;
import cl.qr.cl.qr.pjp.PaymentFlowInformation;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="exampleBean")
@ViewScoped
public class AltiuzTemplatesBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String qrUrl;
    private String nombre;

    @EJB
    ExampleBusiness exampleBusiness;

    @EJB
    PaymentManager paymentManager;

    @PostConstruct
    public void init(){
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if((String) req.getParameter("nombre") != null){
            PaymentFlowInformation information = paymentManager.initTransaction((String) req.getParameter("nombre"));

            this.nombre = (String) req.getParameter("nombre");
            this.qrUrl = information.getUrlEntradaFlujoPago();
        }
    }

    public void getNothing(){
        System.out.println("Aprete el boton");
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}