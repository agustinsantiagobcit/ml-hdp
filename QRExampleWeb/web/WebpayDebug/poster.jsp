<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 27-08-2014
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Iniciando el pago Webpay DEBUG...</title>
    </head>
        <h1>Datos a enviar:</h1>
        <form name="form" id="form" action="${postToUrl}" target="${PaymentTarget}" method="post">
            <c:forEach var="key" items="${postMapKeys}"> <!--postMapContent-->
                <p>
                    <label for="${key}">${key}</label>
                    <input type="text" id="${key}" name="${key}" value="${postMapContent[key]}" />
                </p>
            </c:forEach>

            <input type="submit"/>
        </form>
    </body>
</html>
