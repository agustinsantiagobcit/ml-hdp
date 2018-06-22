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

    <script type="text/javascript">
        if (/msie/i.test (navigator.userAgent)) //only override IE
        {
            document.nativeGetElementById = document.getElementById;
            document.getElementById = function(id)
            {
                var elem = document.nativeGetElementById(id);
                if(elem)
                {
                    //make sure that it is a valid match on id
                    if(elem.attributes['id'].value == id)
                    {
                        return elem;
                    }
                    else
                    {
                        //otherwise find the correct element
                        for(var i=1;i<document.all[id].length;i++)
                        {
                            if(document.all[id][i].attributes['id'].value == id)
                            {
                                return document.all[id][i];
                            }
                        }
                    }
                }
                return null;
            };
        }
    </script>

    <script type="text/javascript">

        $( document ).ready(function() {
            $("#boton").click();
        });

        var state = -1;

        function iframeCargado() {
            state++;

            if(state == 0) {
            }
            if(state == 1) {
                $("#ifr").show();
                $("#dForm").hide();
                $("#dDatosCliente").hide();
            }
            if(state > 1) {
                var ifrwContent;
                ifrwContent = $("#ifrWindow").contents();

                if(ifrwContent != null){
                    var iSU = ifrwContent.text().indexOf("End[")+4;
                    var fSU = ifrwContent.text().indexOf("]");
                    var endURL = ifrwContent.text().substring(iSU, fSU);

                    endURL = endURL + "&loads=" + state;
                    while(endURL.indexOf("&amp;")>=0) {
                        endURL = endURL.replace("&amp;","&");
                    }
                    window.location.replace(endURL);
                }
            }
        }

        function postForm() {
            $("#boton").prop( "disabled", true );
            $("#boton").hide();
            $("#loadingImg").show();

            $("#loadingImg").css( "width",  "60px" );

            $("#form1").submit();
        }
    </script>


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

                    <div class="row row-data" id="dDatosCliente">
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

                            <div class="form-group" id="dForm">
                                <!--
                                <p>
                                    Estimado(a) cliente:
                                </p>

                                <p>
                                    A continuación se mostrará el portal de Transbank para realizar la instalación de su mandato
                                    y autorización al cargo de futuras  primas del seguro a su tarjeta de crédito. Para completar
                                    el proceso debe ejecutar los siguientes pasos:
                                </p>

                                <p>
                                    <ol>
                                        <li>Ingreso de nombre completo, datos de la tarjeta de crédito y  datos de contacto</li>
                                        <li>Confirmación en el banco</li>
                                        <li>Verificación de la información y suscripción el mandato</li>
                                        <li>Una vez visualizado el comprobante  debe presionar &quot;<b>Cerrar sesión</b>&quot; para el término del proceso. </li>
                                    </ol>
                                </p>
                                </br>
-->
                                <form name="form1" id="form1" action="${postToUrl}" target="ifr" method="post">

                                    <c:forEach var="key" items="${postMapKeys}">
                                        <input type="hidden" id="${key}" name="${key}" value="${postMapContent[key]}" />
                                    </c:forEach>

                                    <input onclick="postForm()" type="submit" id="boton" value="Continuar"/>

                                </form>

                                <br/><br/>
                                <img id="loadingImg" style="display: none; " src="<%=basePath%>imagenes/spinner.gif"/>

                            </div>



                            <div class="form-group" id="ifr" style="display: none;" >

                                <p>Estimado(a) cliente:</p>
                                <p>
                                    Para completar el proceso de instalación del mandato de pago de las primas de tu
                                    seguro, por favor asegúrate de visualizar el comprobante de aprobación y sólo
                                    una vez que aparezca este comprobante, presiona &quot;cerrar sesión&quot; en la
                                    parte superior del formulario para volver a la página de contratación.
                                </p>

                                <iframe src=""  onload="iframeCargado();" id="ifrWindow" name="ifr" width="1024px" height="1000px" style="border:2px solid grey"></iframe>

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



