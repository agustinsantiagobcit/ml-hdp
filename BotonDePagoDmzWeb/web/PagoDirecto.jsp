<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 21-07-2014
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    String path = request.getContextPath();
    String basePath = "";
    pageContext.setAttribute("base", basePath);
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <title>MetLife</title>
        <link rel="profile" href="http://gmpg.org/xfn/11" />
        <link rel="shortcut icon" href="favicon.ico" />
        <script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
    </head>

    <body>
        <form id = "pagoForzado" name="pagoForzado" action="InicioPago" method="post">
            <input id="tid" type='hidden' name='tid' value="${tid}" />
            <input id="idSistema" type='hidden' name='idSistema' value="${ConvenioForzado.getBotonInstitucionesPago().getId()}"/>
            <input id="idConvenio" type='hidden' name='idConvenio' value="${ConvenioForzado.getId()}"/>
        </form>

        <script lang="javascript">
            document.forms["pagoForzado"].submit();
        </script>
    </body>

</html>







