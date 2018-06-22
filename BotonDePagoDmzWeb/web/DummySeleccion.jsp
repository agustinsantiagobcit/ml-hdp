<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 04-12-2014
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = "";//request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("base", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Simulador del pago.</title>
    <base href="<%=basePath%>">
    <link rel="shortcut icon" href="<%=basePath%>imagenes/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/normalize.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/botonpago.css" media="screen"/>
    <script src="js/jquery-1.11.1.js"></script>

</head>

<body>
<div id="wrapper">

    <div id="header">
        <div id="header2">
            <div id="headerTop">
                <div id="headerLogo">

                </div>
                <div id="header-title">
                    <h1>Sistema de pago en línea</h1>
                </div>
            </div>
        </div>
    </div>

    <div id="content">
        <div style="text-align: center; width: 880px; padding-left: 35px; padding-top: 10px">


                <table border=0 cellpadding=0 cellspacing=0 style="margin: 10px 10px 10px 35px; width: 800;">
                    <tr>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_izq_sup.gif"></td>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_centro_sup.gif"></td>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_der_sup.gif"></td>
                    </tr>
                    <tr>
                        <td style="width:6; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_centro_izq.gif"></td>
                        <td align="center">


                            <h2>Seleccione el resultado del pago simulado</h2>

                            <form action="DummyNotify" method="post" id="f1">
                                <input type="hidden" id="status1" name="status" value="OK" />
                                <input type="hidden" id="info1" name="info" value="${param["info"]}" />
                                <input type="submit" value="ACEPTAR">

                            </form>

                            <form action="DummyNotify" method="post" id="f2">
                                <input type="hidden" id="status2" name="status"  value="NOK" />
                                <input type="hidden" id="info2" name="info" value="${param["info"]}" />
                                <input type="submit" value="RECHAZAR">

                            </form>


                            <!-- Fin interior recuadro -->
                        </td>
                        <td style="width:6; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_centro_der.gif"></td>
                    </tr>
                    <tr>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_izq_inf.gif"></td>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_centro_inf.gif"></td>
                        <td style="width:4; height:6" align="left" background="<%=basePath%>imagenes/tabla/rec_lat_der_inf.gif"></td>
                    </tr>
                </table>


        </div>
    </div>

    <div id="footer"></div>

</div>

</body>
</html>












</div>
</body>
</html>


