package cl.qr.cl.qr.pjp;

import java.io.Serializable;

/**
 * Created by BluePrints Developer on 10-11-2016.
 */
public class PaymentFlowInformation implements Serializable{

        protected int idPago;
        protected String urlEntradaFlujoPago;
        protected String token;
        protected String urlLanding;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getUrlEntradaFlujoPago() {
        return urlEntradaFlujoPago;
    }

    public void setUrlEntradaFlujoPago(String urlEntradaFlujoPago) {
        this.urlEntradaFlujoPago = urlEntradaFlujoPago;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlLanding() {
        return urlLanding;
    }

    public void setUrlLanding(String urlLanding) {
        this.urlLanding = urlLanding;
    }
}
