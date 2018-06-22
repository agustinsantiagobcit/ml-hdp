package cl.metlife.hdp.botonpago.servlet.dummypayment;

import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ivan on 26-08-2014.
 */
public class DummyStartPayment extends HttpServlet {

    PaymentSupport util = new PaymentSupport();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().print("Error, Acceso no autorizado");
        System.out.println("Acceso a DummyStartPayment via GET");
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id){
            HttpSession userSession = (HttpSession)request.getSession().getServletContext().getAttribute("SESSION"+request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("Dummy", payment));

            String requestContent = payment.getPago().getId() + "";

            PaymentSupport ps = new PaymentSupport();
            String remoteUrl = PaymentSupport.getConfigParam(request, "REMOTE_DummyStartURL");

            request.getSession().setAttribute("postToUrl", remoteUrl);
            request.getSession().setAttribute("postParamName", "info");
            request.getSession().setAttribute("postContent", requestContent);

            String target = "_self";
            if( payment.getPago().getBotonConvenio().getPagoEnPopUp().equalsIgnoreCase("True") ){
                target = "_blank";
            }

            request.getRequestDispatcher("/generic/poster.jsp").forward(request, response);
        }
        else {
            response.getWriter().print("ID no coincide");
        }
    }
}
