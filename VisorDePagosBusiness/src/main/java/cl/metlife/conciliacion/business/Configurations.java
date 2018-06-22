package cl.metlife.conciliacion.business;

import cl.metlife.conciliacion.domain.BusinessLine;
import cl.metlife.conciliacion.domain.PaymentInstitution;
import cl.metlife.conciliacion.domain.PaymentStatus;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * This class stores a map with configurations needed for the application.
 */
public class Configurations {

    private static final Logger LOGGER = Logger.getLogger(Configurations.class);
    private static Configurations instance;
    private Map<String, String> configurationsMap;
    private List<PaymentInstitution> paymentInstitutions;
    private List<PaymentStatus> paymentStatuses;
    private List<BusinessLine> businessLines;
    private List<String> paymentStatusNames;
    private List<String> businessLinesNames;

    protected Configurations() {
    }

    public static Configurations getInstance() {
        if (instance == null){
            instance = new Configurations();
        }
        return instance;
    }

    public String getParameter(String name) {
        return configurationsMap.get(name);
    }

    public void setConfigurationMap(Map<String, String> configurationMap) {
        this.configurationsMap = configurationMap;

        LOGGER.debug("configuration Map set " + this.configurationsMap.toString());
    }

    public void setPaymentInstitutions(List<PaymentInstitution> paymentInstitutions) {
        this.paymentInstitutions = paymentInstitutions;
    }

    public void setBusinessLines(List<BusinessLine> businessLines) {
        this.businessLines = businessLines;
    }

    public void setPaymentStatuses(List<PaymentStatus> paymentStatuses) {
        this.paymentStatuses = paymentStatuses;
    }

    public List<PaymentInstitution> getPaymentsInstitutions() {
        return this.paymentInstitutions;
    }

    public List<PaymentStatus> getPaymentStatuses() {
        return this.paymentStatuses;
    }

    public List<BusinessLine> getBusinessLines() {
        return this.businessLines;
    }

    public void setPaymentStatusNames(List<String> paymentStatusNames) {
        this.paymentStatusNames = paymentStatusNames;
    }

    public List<String> getPaymentStatusNames() {
        return paymentStatusNames;
    }

    public void setBusinessLinesNames(List<String> businessLinesNames) {
        this.businessLinesNames = businessLinesNames;
    }

    public List<String> getBusinessLinesNames() {
        return businessLinesNames;
    }
}