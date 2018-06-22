package cl.metlife.visorpagos.controller;

import cl.blueprintsit.framework.auth.AuthenticationBean;
import cl.metlife.hdp.domain.*;
import cl.metlife.hdp.managers.*;
import cl.metlife.visorpagos.model.BotonBoletaLazyDataModel;
import cl.metlife.visorpagos.model.BotonPagoLazyDataModel;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Blueprints on 6/25/2015.
 */

@ManagedBean(name="paymentsBean")
@ViewScoped
public class PaymentsBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static final Logger logger = LoggerFactory.getLogger(PaymentsBean.class);

    /* Search parameters */
    private String businessLine;
    private String rut;
    private String producto;
    private Date fecha;
    private String estado;

    /* botonPago List lazy */
    private BotonPagoLazyDataModel botonPagos;
    /* botonPago List lazy */
    private BotonBoletaLazyDataModel botonBoletas;
    /* Selected BotonPago */
    private BotonBoleta selectedBoleta;
    private BotonPago selected;

    private boolean checkAS400;
    /* Renders */
    private boolean showCheckAS400;

    private List<Long> businessLineForDefault;

    /* Persistence Objects */
    @EJB
    private BotonPagoManager botonPagoDAO;

    @EJB
    private BotonBoletaManager botonBoletaManager;

    @EJB
    private BotonLineasDeNegocioManager botonLineasDeNegocioManager;

    @EJB
    private BotonInstitucionesPagoManager botonInstitucionesPagoManager;

    @EJB
    private BotonAccesoManager botonAccesoManager;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;


    @PostConstruct
    public void init(){
        // Aquí se deberían inicializar las lineas de negocio del usuario.
        List<BotonLineasDeNegocio> userBusinessLines = authenticationBean.getUserBotonLineasNegocio();

        this.businessLineForDefault = new ArrayList<Long>();
        for (BotonLineasDeNegocio userBusinessLine : userBusinessLines) {
            businessLineForDefault.add(userBusinessLine.getId());
        }

        this.showCheckAS400 = false;
    }

    public void search(){
        botonBoletas = null;
    }

    public BotonPagoLazyDataModel getBotonPagos() {
        if ( botonPagos == null )
            botonPagos = new BotonPagoLazyDataModel(botonPagoDAO);
        return botonPagos;
    }

    public void setBotonPagos(BotonPagoLazyDataModel botonPagos) {
        this.botonPagos = botonPagos;
    }

    public BotonBoletaLazyDataModel getBotonBoletas() {
        if ( botonBoletas == null )
            botonBoletas = new BotonBoletaLazyDataModel(botonBoletaManager, businessLineForDefault ,businessLine, ((rut != null) ? rut.replace(".", "") : null), producto, fecha, estado, showCheckAS400 ,checkAS400);
        return botonBoletas;
    }

    public void setBotonBoletas(BotonBoletaLazyDataModel botonBoletas) {
        this.botonBoletas = botonBoletas;
    }

    public boolean filterByDate(Object value, Object filter, Locale locale) {

        if( filter == null ) {
            return true;
        }

        if( value == null ) {
            return false;
        }

        return DateUtils.truncatedEquals((Date) filter, (Date) value, Calendar.DATE);
    }

    public void subjectSelectionChanged(final AjaxBehaviorEvent event){
        if(businessLine != null && businessLine.equals("VI") ){
            // TODO;
            showCheckAS400 = true;
        } else {
            showCheckAS400 = false;
        }
    }

    public List<BotonLineasDeNegocio> getBusinessLines(){
        // FIXME: Aquí se deben obtener sólo las lineas de negocio de la sesión.
        return authenticationBean.getUserBotonLineasNegocio();
    }

    public void updateTable() {
        if(this.checkAS400){
            this.botonBoletas = null;
        }
    }

    public List<BotonInstitucionesPago> getPaymentInstitutions(){
        return botonInstitucionesPagoManager.findAll();
    }

    public BotonPago getSelected() {
        return selected;
    }

    public void setSelected(BotonBoleta selected) {
        this.selectedBoleta = selected;
        this.selected = selected.getBotonPagoBean();
        this.selected.setBotonAccesos(botonAccesoManager.findByBotonPagoId(this.selected.getId()));
    }

    public BotonBoleta getSelectedBoleta() {
        return selectedBoleta;
    }

    public void setSelectedBoleta(BotonBoleta selectedBoleta) {
        this.selectedBoleta = selectedBoleta;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public boolean isShowCheckAS400() {
        return showCheckAS400;
    }

    public void setShowCheckAS400(boolean showCheckAS400) {
        this.showCheckAS400 = showCheckAS400;
    }

    public boolean isCheckAS400() {
        return checkAS400;
    }

    public void setCheckAS400(boolean checkAS400) {
        this.checkAS400 = checkAS400;
    }
}

