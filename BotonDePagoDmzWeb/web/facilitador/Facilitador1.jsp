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
    <link rel="stylesheet" href="<%=basePath%>css2/iefix.css" />
    <![endif]-->
    <fmt:setLocale value="es_CL"/>
    <style>
        .panel-body {
            padding: 15px;
            background-color: #F0FaFF;
        }


    </style>
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
            <h3>Pago en línea</h3>
            <div class="box">
                <div class="int-box box-shadow--6dp">
                    <h4>Datos del cliente</h4>
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

                        <c:if test="${PaymentInfo.pago.clienteBean.email !=  null}">
                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <dl>
                                    <dt>Correo electrónico:</dt>
                                    <dd>${PaymentInfo.pago.clienteBean.email}</dd>
                                </dl>
                            </div>
                        </c:if>


                    </div>

                    <div  class="int-box ">
                        <p>
                            Estimado(a) cliente:
                        </p>

                        <p>
                            En la siguiente pantalla se mostrará el portal de Transbank para realizar la instalación de su mandato
                            y autorización al cargo de futuras  primas del seguro a su tarjeta de crédito. Para completar
                            el proceso debe ejecutar los siguientes pasos:
                        </p>

                        <p>
                        <ol>
                            <li>Ingreso de nombre completo, datos de la tarjeta de crédito y  datos de contacto</li>
                            <li>Confirmación en el banco</li>
                            <li>Verificación de la información y suscripción el mandato</li>
                            <li>Una vez visualizado el comprobante  debe presionar <b style="background-color: yellow">&quot;Cerrar sesión&quot;</b> para el término del proceso. </li>
                        </ol>
                        </p>
                    </div>


                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title" style="margin-top: 0px !important;">Datos del <b>tarjetahabiente</b></h3>
                        </div>
                        <form action="ContinuarPagoFacilitador" method="post">
                            <div class="panel-body">
                                <div class="row row-noline">
                                    <div>
                                    <!--<div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">-->
                                        <dl>
                                            <dd>
                                                Revise y complete la siguiente información del <b>tarjetahabiente</b>:
                                            </dd>
                                        </dl>
                                    </div>
                                </div>

                                <div class="row row-noline">
                                    <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                        <dl>
                                            <dt>RUT:</dt>
                                            <dd>
                                                <input name="rutTh" type="text" value="${rutTarjetahabiente}"/>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>

                                <div class="row row-noline">
                                    <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                        <dl>
                                            <dd>
                                                <input type="hidden" name="tid" value="${PaymentInfo.pago.id}"/>
                                                <input type="submit" value="Suscribir Mandato"/>
                                            </dd>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="row">
                        </div>
                    </div>
                </div>


            </div>

            <footer>
                <div class="footer-text-box">
                    <p>Los datos e informaci&oacute;n contenido corresponde a aquella proporcionada por el cliente contratante al
                        momento de la contrataci&oacute;n del seguro y otorgamiento del cr&eacute;dito seg&uacute;n corresponda. El cliente ser&aacute; responsable de comunicar
                        a la Compañ&iacute;a Aseguradora cualquier modificaci&oacute;n de sus datos personales, informaci&oacute;n de contacto u otro.</p>
                </div>

                <div class="main-footer">
                    <div class="img_left">
                        <a href="http://www.metlife.cl" title="Metlife"><img src="imagenes/logo.png" alt="Metlife" class="logo-footer" width="120" /></a>
                    </div>
                    <div class="lists_center">
                        <ul>
                            <li><a href="#">Informaci&oacute;n Corporativa</a> </li>
                            <li><span class="bullet">&bull;</span><a href="#">Pol&iacute;tica de Privacidad</a></li>
                            <li><span class="bullet">&bull;</span><a href="#">Circular N&deg; 2131</a></li>
                            <li class="footer_copyright">&copy; 2017 Metlife Chile, Agustinas 640, Santiago, Chile</li>
                        </ul>

                    </div>

                    <div class="socials_right">
                        <p>S&iacute;guenos</p>
                        <img src="logos/socials1.png" alt="Facebook" />
                        <img src="logos/socials2.png" alt="Facebook" />
                    </div>
                </div>
            </footer>
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

