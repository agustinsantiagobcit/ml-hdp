<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 21-07-2014
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = "";//request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("base", basePath);
%>
<%@ page import="cl.metlife.hdp.botonpago.dawsclient.PaymentInfo" %>



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
    <link href="<%=basePath%>css2/style.css" rel="stylesheet" media="screen" />
    <link href="<%=basePath%>css2/jquery-ui.css" rel="stylesheet" media="screen" />
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.js"></script>
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="<%=basePath%>js/html5shiv.js"></script>
    <![endif]-->
    <!-- new imports -->
    <link href='<%=basePath%>css/metlife-v2.0.css' rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="<%=basePath%>css/metlife-v2.0.js"></script>
</head>

<body>
<div id="wrapper">
    <div class="row global-header">
        <div class="col-xs-4 col-sm-5 global-header__left">

        </div>
        <div class="col-xs-4 col-sm-2 global-header__middle">
            <h1> <a href="index.html" id="homepage_header_global-logoLink"> <img src="ar_images/metlife_logo.png" alt="MetLife" class="global-header__logo img-responsive"> </a> </h1>
        </div>
        <div id=""></div>
        <div class="col-xs-4 col-sm-5 global-header__right">
            <div class="col contact-trigger pull-right" title="CONTACT" data-target="contact-container">
                <a href="#" id="homepage_header_global-contactLabel" class="contact-trigger__label"> Salir</a>
            </div>
        </div>
    </div>
    <div class="cont-site col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix">
        <div id="content" class="clearfix container">
            <h3>Pago en línea</h3>
            <div class="box">
                <div class="int-box box-shadow--6dp">
                    <div class="message unsuccess">
                        <p><strong>La transacción no pudo completarse correctamente</strong></p>
                    </div>

                    <h4>Pago desconocido</h4>

                    <p>
                        Estimado Cliente, El pago que desea realizar es inválido.
                    </p>
                    <p>
                        Por favor, vuelva e intente pagar de nuevo.
                    </p>
                    <p>
                        <img  src="imagenes/unhappy1.jpg"/>
                    </p>
                    <!-- class="center-block"-->
                    <div class="clearfix">
                        <div class="row right-block">
                        </div>
                    </div>

                </div>


                <footer>
                    <div class="footer-text-box">
                        <p>Los datos e informaci&oacute;n contenida en el Portal de Clientes, corresponde a aquella proporcionada por el cliente contratante al momento de la contrataci&oacute;n
                            del seguro y otorgamiento del cr&eacute;dito, seg&uacute;n corresponda. El cliente contratante es responsable de comunicar a MetLife cualquier modificaci&oacute;n de sus datos
                            personales, informaci&oacute;n de contacto u otro.</p>
                    </div>

                    <div class="main-footer">
                        <div class="img_left">
                            <a href="http://www.metlife.cl" title="Metlife"><img src="imagenes/logo.png" alt="Metlife" class="logo-footer" width="120" /></a>
                            <span class="phone">600 390 3000</span>
                        </div>
                        <div class="lists_center">
                            <ul>
                                <li><a href="http://w3.metlife.cl/personas/conocenos/metlife-en-chile/informacion-corporativa.html">Informaci&oacute;n Corporativa</a> </li>
                                <li><span class="bullet">&bull;</span><a href="http://w3.metlife.cl/personas/conocenos/metlife-en-chile/politica-de-privacidad.html">Pol&iacute;tica de Privacidad</a></li>
                                <li><span class="bullet">&bull;</span><a href="http://w3.metlife.cl/servicio-al-cliente/consultas-y-reclamos.html">Circular N&deg; 2131</a></li>
                                <li><span class="bullet">&bull;</span><a href="http://www.ddachile.cl/v5/Inicio.aspx">Defensor del Asegurado</a></li>
                                <li><span class="bullet"></span><a href="https://w3.metlife.cl/assets/documentassets/codigo_atorregulacion_y_compendio_buenas_practicas.pdf">Consejo de Autorregulaci&oacute;n</a></li>
                            </ul>

                            <p class="footer_copyright">&copy; 2017 Metlife Chile, Agustinas 640, Santiago, Chile. Desde teléfonos celulares o desde el extranjero al <span class="phone_footer">+56 2 2826 47 90</span></p>

                        </div>

                        <div class="socials_right">
                            <p>S&iacute;guenos</p>
                            <a href="https://es-la.facebook.com/MetLifeCl/"><img src="logos/socials1.png" alt="Facebook" /></a>
                            <a href="https://twitter.com/metlifechile"><img src="logos/socials2.png" alt="Twitter" /></a>
                        </div>
                    </div>
                </footer>




            </div>
            <div class="notes">
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











