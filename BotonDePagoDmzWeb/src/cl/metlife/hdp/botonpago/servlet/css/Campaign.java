package cl.metlife.hdp.botonpago.servlet.css;

import cl.metlife.hdp.botonpago.dawsclient.BotonCampaign;
import cl.metlife.hdp.botonpago.dawsclient.PagoDAWS;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ivan on 21-12-2015.
 */
public class Campaign extends HttpServlet {
    PagoDAWS pagoDAWS;

    @Override
    public void init(ServletConfig config) throws ServletException {

        PaymentSupport ps = new PaymentSupport();
        String wsdlUrl = ps.getEnviromentConfig("PagoDaWsUrl", config.getServletContext());
        this.pagoDAWS = ps.getPagoDawsReference(wsdlUrl);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/css");
        String campaignName = getCampaignName(request);

        PaymentSupport ps = new PaymentSupport();
        BotonCampaign camp = this.pagoDAWS.getCampaignCSS(campaignName);
        response.getWriter().print( camp.getCss() );
    }

    private String getCampaignName(HttpServletRequest request) {
        String campaignName = "";
        campaignName = request.getParameter("cName");
        if(campaignName == null || campaignName == ""){
            campaignName = "default";
        }
        return campaignName;
    }
}
