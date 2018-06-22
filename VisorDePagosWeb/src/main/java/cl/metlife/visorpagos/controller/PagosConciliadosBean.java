package cl.metlife.visorpagos.controller;

import cl.blueprintsit.framework.auth.AuthenticationBean;
import cl.metlife.conciliacion.domain.*;
import cl.metlife.conciliacion.managers.BusinessLineManager;
import cl.metlife.conciliacion.managers.PaymentInstitutionManager;
import cl.metlife.conciliacion.managers.PaymentManager;
import cl.metlife.conciliacion.managers.PaymentStatusManager;
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
import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Blueprints on 6/25/2015.
 */

@ManagedBean(name="pagosConciliadosBean")
@ViewScoped
public class PagosConciliadosBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static final Logger logger = LoggerFactory.getLogger(PagosConciliadosBean.class);

    // View values.
    private Date fechaDesde;
    private Date fechaHasta;
    private String periodoMes;
    private String periodoAnio;
    private String lineaNegocio;
    private String institucionPago;
    private String estadoConciliacion;

    private List<Payment> lista;

    /* Persistence Objects */
    @EJB
    private PaymentInstitutionManager paymentInstitutionManager;

    @EJB
    private BusinessLineManager businessLineManager;

    @EJB
    private PaymentStatusManager paymentStatusManager;

    @EJB
    private PaymentManager paymentManager;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;


    @PostConstruct
    public void init(){

    }

    public void search(){
        String selectedInstitution = institucionPago;
        Date paymentDateFrom = fechaDesde;
        Date paymentDateUntil = fechaHasta;
        String periodMonth = periodoMes;
        String periodYear = periodoAnio;
        String businessLineName = lineaNegocio;
        String paymentStatus = estadoConciliacion;

        Date parsedDateFrom;
        Date parsedDateUntil;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarUntil = Calendar.getInstance();

        try {
            calendarFrom.setTime(paymentDateFrom);
        } catch (Exception e) {
            try {
                Date defaultDateFrom = dateFormat.parse("01/01/0001");
                calendarFrom.setTime(defaultDateFrom);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        try {
            calendarUntil.setTime(paymentDateUntil);
        } catch (Exception e) {
            Date defaultDateUntil = new Date();
            calendarUntil.setTime(defaultDateUntil);
        }

        if(periodYear == null || !FormatUtils.isNumeric(periodYear)) {
            periodYear = "";
        }

        List<Payment> payments;

        lista = paymentManager.searchUsingCriteria(calendarFrom, calendarUntil, periodMonth, periodYear,
                selectedInstitution, businessLineName, paymentStatus);
    }

    public List<BusinessLine> getAllLineasDeNegocio(){
        return authenticationBean.getUserBusinessLines();
    }

    public List<PaymentInstitution> getAllInstitucionesDePago(){
        return paymentInstitutionManager.getAllPaymentInstitutions();
    }

    public List<String> getAllEstados(){
        return paymentStatusManager.getPaymentStatusNames();
    }

    public String getPeriodoMes() {
        return periodoMes;
    }

    public void setPeriodoMes(String periodoMes) {
        this.periodoMes = periodoMes;
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

    public String getPeriodoAnio() {
        return periodoAnio;
    }

    public void setPeriodoAnio(String periodoAnio) {
        this.periodoAnio = periodoAnio;
    }

    public String getLineaNegocio() {
        return lineaNegocio;
    }

    public void setLineaNegocio(String lineaNegocio) {
        this.lineaNegocio = lineaNegocio;
    }

    public String getInstitucionPago() {
        return institucionPago;
    }

    public void setInstitucionPago(String institucionPago) {
        this.institucionPago = institucionPago;
    }

    public String getEstadoConciliacion() {
        return estadoConciliacion;
    }

    public void setEstadoConciliacion(String estadoConciliacion) {
        this.estadoConciliacion = estadoConciliacion;
    }

    public List<Payment> getLista() {
        return lista;
    }

    public void setLista(List<Payment> lista) {
        this.lista = lista;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }
}

