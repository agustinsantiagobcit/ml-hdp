package cl.metlife.visorpagos.controller;

import cl.metlife.conciliacion.domain.Payment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name="params")
@SessionScoped
public class SessionParametersBean {

    private List<Payment> processedPayments;
    private List<Payment> processedPaymentsAlreadyConciliated;
    private List<Payment> processedPaymentsWithErrors;

    public void setParameters(List<Payment> l1, List<Payment> l2, List<Payment> l3){
        this.processedPayments = l1;
        this.processedPaymentsAlreadyConciliated = l2;
        this.processedPaymentsWithErrors = l3;
    }

    public void cleanParameters(){
        this.processedPayments = null;
        this.processedPaymentsAlreadyConciliated = null;
        this.processedPaymentsWithErrors = null;
    }

    public String getInit(){
        cleanParameters();

        return "done";
    }

    public List<Payment> getProcessedPayments() {
        return processedPayments;
    }

    public void setProcessedPayments(List<Payment> processedPayments) {
        this.processedPayments = processedPayments;
    }

    public List<Payment> getProcessedPaymentsAlreadyConciliated() {
        return processedPaymentsAlreadyConciliated;
    }

    public void setProcessedPaymentsAlreadyConciliated(List<Payment> processedPaymentsAlreadyConciliated) {
        this.processedPaymentsAlreadyConciliated = processedPaymentsAlreadyConciliated;
    }

    public List<Payment> getProcessedPaymentsWithErrors() {
        return processedPaymentsWithErrors;
    }

    public void setProcessedPaymentsWithErrors(List<Payment> processedPaymentsWithErrors) {
        this.processedPaymentsWithErrors = processedPaymentsWithErrors;
    }

}