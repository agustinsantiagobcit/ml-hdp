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
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <c:if test="${Boletas.size() > 1}"><h3 class="panel-title">Detalle de boletas a pagar</h3></c:if>
                            <c:if test="${Boletas.size() == 1}"><h3 class="panel-title">Detalle de boleta a pagar</h3></c:if>

                        </div>
                        <div class="panel-body">

                            <c:forEach var="boleta" varStatus="status" items="${Boletas}">
                                <!-- Detalle boleta -->
                                <div class="row">
                                    <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                        <dl>
                                            <dt>Número de boleta:</dt>
                                            <dd>${boleta.getNumeroBoleta()}</dd>
                                        </dl>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12">
                                        <dl>
                                            <dt>Detalle de boleta:</dt>
                                            <dd>${boleta.descripcion}</dd>
                                        </dl>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                        <dl>
                                            <dt>Monto a pagar mensual:</dt>


                                            <dd><strong>

                                                <c:if test="${SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' }">
                                                    ${SimboloDivisa} <fmt:formatNumber value="${boleta.monto}" type="NUMBER" currencySymbol="${SimboloDivisa}" maxFractionDigits="2"/>
                                                </c:if>
                                                <c:if test="${!(SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' )}">
                                                    <fmt:formatNumber value="${boleta.monto}" type="currency" currencySymbol="${SimboloDivisa}" maxFractionDigits="0"/>
                                                </c:if>

                                            </strong> <!--/ <span>[Monto UF]</span>--></dd>
                                        </dl>
                                    </div>
                                </div>
                            </c:forEach>



                        </div>
                    </div>

                    <c:if test="${Boletas.size() > 1}">
                    <div class="row total-row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 col-lg-offset-4 col-md-offset-4 col-sm-offset-3">
                                <strong>Total a Pagar:</strong>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                <span class="clp">
                                    <c:if test="${SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' }">
                                        ${SimboloDivisa} <fmt:formatNumber value="${PaymentInfo.pago.montoTotal}" type="NUMBER" currencySymbol="${SimboloDivisa}" maxFractionDigits="2"/>
                                    </c:if>
                                    <c:if test="${!(SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' )}">
                                        <fmt:formatNumber value="${PaymentInfo.pago.montoTotal}" type="currency" currencySymbol="${SimboloDivisa}" maxFractionDigits="0"/>
                                    </c:if>
                                </span><!-- / <span>[Monto UF]</span>-->
                            </div>
                        </div>
                    </div>
                    </c:if>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Datos del pagador</h3>
                        </div>
                        <form action="InicioPagoPatPass" method="post">
                        <div class="panel-body">
                            <div class="row row-noline">
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dd>
                                        Revise y complete la siguiente información:
                                        </dd>
                                    </dl>
                                </div>
                            </div>

                            <div class="row row-noline">
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>RUT:</dt>
                                        <dd>
                                            <input name="rut" type="text" value="${rut}"/>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row row-noline">
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>Nombres:</dt>
                                        <dd><input name="name" type="text" value="${nombre}"/> </dd>
                                    </dl>
                                </div>
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>Apellido Paterno:</dt>
                                        <dd><input name="fSurname" type="text" value="${apellidoP}"/> </dd>
                                    </dl>
                                </div>
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>Apellido Materno:</dt>
                                        <dd><input name="mSurname" type="text" value="${apellidoM}"/> </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row row-noline">
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>Teléfono:</dt>
                                        <dd><input name="phone" type="text" value="${PaymentInfo.pago.clienteBean.telefono}" /> </dd>
                                    </dl>
                                </div>
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dt>Email:</dt>
                                        <dd><input name="email" type="text" value="${PaymentInfo.pago.clienteBean.email}"/> </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="row row-noline">
                                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-12">
                                    <dl>
                                        <dd>
                                            <input type="hidden" name="tid" value="${PaymentInfo.pago.id}"/>
                                            <input type="submit" value="Pagar"/>
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




                <div class="clearfix">


                    <!--<h4>Selecciona forma de pago</h4>-->
                    <div class="form-inline">
                        <div class="form-group">

                            <!--
                            <c:forEach var="link" varStatus="status" items="${LinksPago}">
                                <label class="radio-inline">


                                    <form name="selectPayment#{link.getBotonInstitucionesPago().getId()}" action="InicioPago" method="post">
                                        <input id="tid" type='hidden' name='tid' value="#{tid}" />
                                        <input id="idSistema" type='hidden' name='idSistema' value="#{link.getBotonInstitucionesPago().getId()}"/>
                                        <input id="idConvenio" type='hidden' name='idConvenio' value="#{link.getId()}"/>
                                    </form>

                                    <a href="javascript:selectPayment#{link.getBotonInstitucionesPago().getId()}.submit()">
                                        <img src="${link.getBotonInstitucionesPago().getUrlLogo()}"/>
                                    </a>
                                </label>

                            </c:forEach>
                            -->

                        </div>
                    </div>

                    <!--<div class="row center-block">
                        <button type="button" class="btn btn-lg">Continuar con el pago</button>
                    </div>-->
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







