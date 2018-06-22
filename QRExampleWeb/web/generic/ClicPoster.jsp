<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 24-05-2016
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>


<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 21-07-2014
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, width=device-width">
    <title>MetLife</title>
    <meta name="description" content="" />
    <meta name="Keywords" content="" />
    <link rel="profile" href="http://gmpg.org/xfn/11" />
    <link rel="shortcut icon" href="imagenes/favicon.ico" />
    <!-- styles / fonts -->
    <link href='<%=basePath%>css2/cssOpenSans.css' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="<%=basePath%>css2/bootstrap.css" />
    <link href="<%=basePath%>css2/style.css?cName=${PaymentInfo.pago.campaign}" rel="stylesheet" media="screen" />
    <link href="<%=basePath%>css2/jquery-ui.css" rel="stylesheet" media="screen" />
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.js"></script>
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="<%=basePath%>js/html5shiv.js"></script>
    <![endif]-->
    <fmt:setLocale value="es_CL"/>
</head>

<body>
<div id="wrapper">
    <header id="header" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix">
        <div class="container">
            <h1><a href="http://www.metlife.cl" title="Metlife"><img src="imagenes/logo.png" alt="Metlife" class="img-responsive" /></a></h1>
        </div>
    </header>
    <div class="cont-site col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix">
        <div id="content" class="clearfix container">

            <div class="box">
                <div class="int-box box-shadow--6dp">

                    <div class="row row-data">
                        <div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                            <dl>
                                <dt>Nombre:</dt>
                                <dd>${PaymentInfo.pago.clienteBean.nombreCliente}</dd>
                            </dl>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <dl>
                                <dt>RUT:</dt>
                                <dd>${PaymentInfo.pago.clienteBean.rutCliente}</dd>
                            </dl>
                        </div>

                    </div>


                    <div class="clearfix">


                        <div class="form-inline">
                            <div class="form-group">
                                <p>
                                    Estimado(a)
                                </p>

                                <p>
                                    A continuación será redirigido al portal de Transbank para realizar la instalación del mandato
                                    y autorizar el cargo de la prima del seguro a su tarjeta de crédito.
                                </p>

                                <form name="form" id="form" action="${postToUrl}" target="${PaymentTarget}" method="post">
                                    <c:forEach var="key" items="${postMapKeys}">
                                        <input type="hidden" id="${key}" name="${key}" value="${postMapContent[key]}" />
                                    </c:forEach>
                                    <input type="submit" value="Continuar"/>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer class="clearfix">
        <div class="container clearfix">

        </div>
    </footer>
</div>






</body>

</html>



