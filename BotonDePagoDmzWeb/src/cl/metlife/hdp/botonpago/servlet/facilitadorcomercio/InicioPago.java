package cl.metlife.hdp.botonpago.servlet.facilitadorcomercio;

import cl.metlife.hdp.botonpago.dawsclient.BotonBoleta;
import cl.metlife.hdp.botonpago.dawsclient.PaymentInfo;
import cl.metlife.hdp.botonpago.servlet.PaymentException;
import cl.metlife.hdp.botonpago.utils.PaymentSupport;
import cl.metlife.hdp.botonpago.utils.xml.XmlProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cl.metlife.hdp.botonpago.utils.PaymentSupport.writeFile;

/**
 * Created by Ivan on 28-07-2014.
 */
public class InicioPago extends HttpServlet {

    PaymentSupport util = new PaymentSupport();
    boolean debug = false;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Forbidden");
    }


    private String externalPostToUrl(String command, String url, String paramName, String paramValue) {

        String s = null;
        String result = "";
        try {
            Process p = Runtime.getRuntime().exec(command + " " + url + " " + paramName);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedWriter stdOut = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            stdOut.append(paramValue);
            stdOut.close();

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                result += s;
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("Ocurrio un problema");
            e.printStackTrace();
            return "";
        }

        return result;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tid"));

        if(Integer.parseInt(request.getSession().getAttribute("tid").toString()) == id) {
            HttpSession userSession = (HttpSession) request.getSession().getServletContext().getAttribute("SESSION" + request.getSession().getId());
            PaymentInfo payment = (PaymentInfo) request.getSession().getAttribute("PaymentInfo");
            userSession.setAttribute("convenio", util.getAgreementId("FacilitadorComercio", payment));
            request.getSession().setAttribute("rutTarjetahabiente", payment.getPago().getClienteBean().getRutCliente());

            request.getRequestDispatcher("/facilitador/Facilitador1.jsp").forward(request, response);




        }
        else {
            response.getWriter().print("Error. ID no coincide");
            //PaymentInfo payment = new PaymentInfo();
        }

    }


}
