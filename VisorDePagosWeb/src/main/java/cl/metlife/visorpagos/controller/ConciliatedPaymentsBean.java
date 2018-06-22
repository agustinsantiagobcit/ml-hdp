package cl.metlife.visorpagos.controller;

import cl.blueprintsit.framework.auth.AuthenticationBean;
import cl.metlife.hdp.domain.BotonBoleta;
import cl.metlife.hdp.domain.BotonInstitucionesPago;
import cl.metlife.hdp.domain.BotonLineasDeNegocio;
import cl.metlife.hdp.domain.BotonPago;
import cl.metlife.hdp.managers.*;
import cl.metlife.visorpagos.model.BotonBoletaLazyDataModel;
import cl.metlife.visorpagos.model.BotonPagoLazyDataModel;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Blueprints on 6/25/2015.
 */

@ManagedBean(name="conciliatedPaymentsBean")
@ViewScoped
public class ConciliatedPaymentsBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static final Logger logger = LoggerFactory.getLogger(ConciliatedPaymentsBean.class);

    /* Search parameters */
    private String businessLine;
    private String rut;
    private String producto;
    private Date fechaDesde;
    private Date fechaHasta;
    private String estado;

    /* botonPago List lazy */
    private BotonPagoLazyDataModel botonPagos;
    /* botonPago List lazy */
    private List<BotonBoleta> botonBoletas;
    /* Selected BotonPago */
    private BotonBoleta selectedBoleta;
    private BotonPago selected;

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
    }

    public void search(){
        if(businessLine == null){
            showError("Error", "Debe seleccionar linea de negocios para realizar la búsqueda");
            return;
        }

        if(fechaDesde == null || fechaHasta == null){
            showError("Error", "Debe seleccionar ambas fechas para realizar la búsqueda");
            return;
        }

        botonBoletas = botonBoletaManager.findConciliatedByBLAndDates(this.businessLine, this.fechaDesde, fechaHasta);
    }

    public BotonPagoLazyDataModel getBotonPagos() {
        if ( botonPagos == null )
            botonPagos = new BotonPagoLazyDataModel(botonPagoDAO);
        return botonPagos;
    }

    public void setBotonPagos(BotonPagoLazyDataModel botonPagos) {
        this.botonPagos = botonPagos;
    }

    public List<BotonBoleta> getBotonBoletas() {
        return botonBoletas;
    }

    public void setBotonBoletas(List<BotonBoleta> botonBoletas) {
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

    public List<BotonLineasDeNegocio> getBusinessLines(){
        // FIXME: Aquí se deben obtener sólo las lineas de negocio de la sesión.
        return botonLineasDeNegocioManager.findAll();
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

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}

