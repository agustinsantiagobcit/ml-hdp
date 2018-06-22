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
    <link rel="stylesheet" href="<%=basePath%>css2/iefix.css" />
    <script src="<%=basePath%>js/html5shiv.js"></script>
    <![endif]-->
    <fmt:setLocale value="es_CL"/>

    <!-- new imports -->
    <link href='<%=basePath%>css/metlife-v2.0.css' rel='stylesheet' type='text/css' />
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

    <div class="cont-site col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix" style="background-color:#f2f2f2">
        <div id="content" class="clearfix container">
            <h3>Centro de Pagos</h3>
            <div class="box">
                <div class="int-box box-shadow--6dp">
                    <h4>Información Personal</h4>
                    <div class="row row-data">
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <dl>
                                <dt>RUT:</dt>
                                <dd>${PaymentInfo.pago.clienteBean.rutCliente}</dd>
                            </dl>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <dl>
                                <dt>Nombres:</dt>
                                <dd>${PaymentInfo.pago.clienteBean.nombreCliente}</dd>
                            </dl>
                        </div>
                        <c:if test="${PaymentInfo.pago.clienteBean.email !=  null}">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <dl>
                                    <dt>Correo electrónico:</dt>
                                    <dd>${PaymentInfo.pago.clienteBean.email}</dd>
                                </dl>
                            </div>
                        </c:if>
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <dl>
                                <dt>Teléfono Celular:</dt>
                                <dd>${PaymentInfo.pago.clienteBean.telefono}</dd>
                            </dl>
                        </div>


                    </div>

                    <h4>Detalle del pago</h4>
                    <c:forEach var="boleta" varStatus="status" items="${Boletas}">
                        <!-- Detalle boleta -->
                        <div class="row">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <dl>
                                    <dt>Nº:</dt>
                                    <dd>${boleta.getNumeroBoleta()}</dd>
                                </dl>
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <dl>
                                    <dt>Descripción del Pago</dt>
                                    <dd>${boleta.descripcion}</dd>
                                </dl>
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <dl>
                                    <dt>Fecha de Vencimiento</dt>
                                    <dd>${boleta.vencimientoFormatted}</dd>
                                </dl>
                            </div>
                            <c:if test="${MostrarMontos == 'True'}">
                                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                    <dl>
                                        <dt>Total a Pagar</dt>

                                        <dd>
                                            <strong>

                                                <c:if test="${SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' }">
                                                    ${SimboloDivisa} <fmt:formatNumber value="${boleta.monto}" type="NUMBER" currencySymbol="${SimboloDivisa}" maxFractionDigits="2"/>
                                                </c:if>
                                                <c:if test="${!(SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' )}">
                                                    <fmt:formatNumber value="${boleta.monto}" type="currency" currencySymbol="${SimboloDivisa}" maxFractionDigits="0"/>
                                                </c:if>
                                            </strong>
                                            <!--/ <span>[Monto UF]</span>-->
                                        </dd>
                                    </dl>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>


                    <div class="row total-row">
                        <c:if test="${MostrarMontos == 'True'}">

                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="col-lg-6 col-md-4 col-sm-5 col-xs-12 col-lg-offset-4 col-md-offset-4 col-sm-offset-3">
                                <strong>Total a Pagar:</strong>
                            </div>
                            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">

                                <c:if test="${SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' }">
                                    ${SimboloDivisa} <fmt:formatNumber value="${PaymentInfo.pago.montoTotal}" type="NUMBER" currencySymbol="${SimboloDivisa}" maxFractionDigits="2"/>
                                </c:if>
                                <c:if test="${!(SimboloDivisa == 'UF' || SimboloDivisa == 'Uf' || SimboloDivisa == 'uf' || SimboloDivisa == 'UF ' )}">
                                    <fmt:formatNumber value="${PaymentInfo.pago.montoTotal}" type="currency" currencySymbol="${SimboloDivisa}" maxFractionDigits="0"/>
                                </c:if>
                            </div>
                        </div>
                        </c:if>
                    </div>
                    <div class="clearfix">


                        <h4>Selecciona forma de pago</h4>
                        <div class="form-inline">
                            <div class="form-group">
                                <c:forEach var="link" varStatus="status" items="${LinksPago}">
                                    <label class="radio-inline"><!-- ${link.getBotonInstitucionesPago().getNombre()} -->


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

                            </div>
                        </div>
                        <!--<div class="row center-block">
                            <button type="button" class="btn btn-lg">Continuar con el pago</button>
                        </div>-->
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
            <!--  %@include file="Footer.jsp" %>-->

        </div>
    </div>
    <footer class="clearfix">
        <div class="container clearfix">

        </div>
    </footer>
</div>
</body>

<script>
    var UtilityModule = (function () {
        var ua = navigator.userAgent.toLowerCase();
        var isDesktop = !(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows|nexus (ce|phone)|xda|xiino/i.test(ua) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(ua.substr(0, 4)));
        var resizeTimeout;
        var currentViewPort = getViewport();

        // Insert responsive media queries
        function responsiveMediaQueries() {
            var viewports = [
                '<div class="visible-xs-block"></div>',
                '<div class="visible-sm-block"></div>',
                '<div class="visible-md-block"></div>'
            ];

            $('body').append('<div class="responsive-media-queries"></div>');
            viewports.forEach(function (element) {
                $('.responsive-media-queries').append(element);
            });
        }

        // Determine viewport's width
        function getViewport() {
            var screenMode = "";

            if ($('.visible-xs-block').is(':visible')) {
                screenMode = "mobile";
            } else if ($('.visible-sm-block').is(':visible')) {
                screenMode = "tablet";
            } else {
                screenMode = "desktop";
            }

            return screenMode;
        }

        function viewportChange() {
            if (currentViewPort != getViewport()) {
                currentViewPort = getViewport();
                return true;
            } else {
                return false;
            }
        }

        function unique(list) {
            var result = [];
            $.each(list, function (i, e) {
                if ($.inArray(e, result) == -1) result.push(e);
            });
            return result;
        }

        function isWhole(s) {
            var isWhole_re = /^\s*\d+\s*$/;
            return String(s).search(isWhole_re) != -1;
        }

        function isNonblank(s) {
            var isNonblank_re = /\S/;
            return String(s).search(isNonblank_re) != -1;
        }

        function isNumber(s) {
            return /^\d+$/.test(s);
        }

        function updatePageFrom(name) {
            var pageFrom = UtilityModule.getQueryStringNoHash()["pageFrom"];
            if (pageFrom != undefined) {
                name.val(pageFrom);
            }
        }

        function replaceAll(txt, replace, with_this) {
            return txt.replace(new RegExp('\\b' + replace + '\\b', 'gi'), with_this);
        }

        function isLeapYear(a) {
            a = parseInt(a);
            if (a % 4 == 0) {
                if (a % 100 != 0) {
                    return true;
                } else {
                    if (a % 400 == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        function populateDaysDropDown(id) {
            var numDayDropDown = $(".dobDay").length;
            var numMonthDropDown = $(".dobMonth").length;
            var numYearDropDown = $(".dobYear").length;
            if (($(".dobMonth").val() == "09") || ($(".dobMonth").val() == "04") ||
                    ($(".dobMonth").val() == "06") || ($(".dobMonth").val() == "11")) {
                $(".dobDay option:eq(31)").remove();

            } else if ($(".dobMonth").val() == "02") {

                if ((UtilityModule.isLeapYear($(".dobYear").val()) == false) || $(".dobYear").val() == "") {
                    $(".dobDay option:eq(31)").remove();
                    $(".dobDay option:eq(30)").remove();
                    $(".dobDay option:eq(29)").remove();
                } else {
                    if (($(".dobDay option[value='29']").length > (numDayDropDown - numDayDropDown)) == false) {
                        $(".dobDay").append('<option value="29">29</option>');
                    }
                    $(".dobDay option:eq(31)").remove();
                    $(".dobDay option:eq(30)").remove();
                }

            } else {
                if ((($(".dobDay option[value='29']").length - numDayDropDown) > 0) == false) {

                    $(".dobDay").append('<option value="29">29</option>');
                }
                if ((($(".dobDay option[value='30']").length - numDayDropDown) > 0) == false) {

                    $(".dobDay").append('<option value="30">30</option>');
                }
                if ((($(".dobDay option[value='31']").length - numDayDropDown) > 0) == false) {

                    $(".dobDay").append('<option value="31">31</option>');
                }
            }
        }

        function toTitleCase(str) {
            return str.replace(/\w\S*/g, function (txt) {
                return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
            });
        }

        function encode(d) {
            if (d == '<')
                return '&lt;';
            if (d == '>')
                return '&gt;';
            if (d == '&')
                return '&amp;';

            if (d.charCodeAt(0) > 127) {
                return '&#' + d.charCodeAt(0) + ';';
            }
            return d;
        }

        function escapeChar(value) {
            var bb = "";
            for (i = 0; i < value.length; i++) {
                bb += encode(value.charAt(i));
            }
            return bb;
        }

        function strTrim(a) {
            a = a.replace(/^\s+/g, "");
            a = a.replace(/\s+$/g, "");
            return a;
        }

        function getQueryStringNew() {
            var vars = [], hash;
            var hashes = window.location.href.slice(window.location.href.indexOf('#') + 1).split('&');
            for (var i = 0; i < hashes.length; i++) {
                hash = hashes[i].split('=');
                vars.push(hash[0]);
                vars[hash[0]] = hash[1];
            }
            return vars;
        }

        function getQueryStringNoHash() {
            var vars = [], hash;
            var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
            for (var i = 0; i < hashes.length; i++) {
                hash = hashes[i].split('=');
                vars.push(hash[0]);
                vars[hash[0]] = hash[1];
            }
            return vars;
        }

        function gmapsAutoCompleteInit(className, countryCode, map) {
            var googleautocomplete;
            if (typeof countryCode !== 'undefined') {
                var options = {
                    componentRestrictions: {country: countryCode}
                };
                googleautocomplete = new google.maps.places.Autocomplete(document.getElementsByClassName(className)[0], options);
            } else {
                googleautocomplete = new google.maps.places.Autocomplete(document.getElementsByClassName(className)[0]);
            }
            if (className == "cta_search" || className == "from-address") {
                googleautocomplete.bindTo('bounds', map);
            }
            google.maps.event.addListener(googleautocomplete, 'place_changed', function () {
                var place = googleautocomplete.getPlace();
                if (!place || !place.geometry) {
                }
            });
        }

        function getBrowserName() {
            var browsername = navigator.appName;
            if (browsername.indexOf("Netscape") != -1) {
                browsername = "NS";
            } else if (browsername.indexOf("Microsoft") != -1) {
                browsername = "MSIE";
            } else {
                browsername = "N/A";
            }
            return browsername;
        }

        function getCookie(c_name) {
            if (document.cookie.length > 0) {
                c_start = document.cookie.indexOf(c_name + "=");
                if (c_start != -1) {
                    c_start = c_start + c_name.length + 1;
                    c_end = document.cookie.indexOf(";", c_start);
                    if (c_end == -1) c_end = document.cookie.length;
                    return unescape(document.cookie.substring(c_start, c_end));
                }
            }
            return "";
        }

        function getQueryString(a) {
            a = a.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var b = "[\\?&]" + a + "=([^&#]*)";
            var c = new RegExp(b);
            var d = c.exec(window.location.href);
            if (null == d) return "";
            else return d[1];
        }

        function internationalizeDate() {
            var months = {
                January: "01",
                February: "02",
                March: "03",
                April: "04",
                May: "05",
                June: "06",
                July: "07",
                August: "08",
                September: "09",
                October: "10",
                November: "11",
                December: "12"
            };

            $(".internationalizeDate").each(function () {
                var date = $(this).find("span").text().trim();
                var splitDate = date.split(' ');

                var month = splitDate[0];
                var day = splitDate[1].replace(/\,/g, "");
                var year = splitDate[2];

                $(this).find("span").text(day + "-" + months[month] + "-" + year);
            });
        }

        function heightMatcher(elements) {
            var height = 0;
            elements.css('min-height', '0px');
            elements.each(function () {
                height = $(this).outerHeight() > height ? $(this).outerHeight() : height;
            });
            elements.css('min-height', height + 'px');
        }

        return {
            init: function () {
                responsiveMediaQueries();
                updatePageFrom($('[name="pageFrom"]'));

                //If user on desktop, disable phone dialing
                if (isDesktop) {
                    $("a[href*='tel:']").each(function () {
                        $(this).removeAttr('href').addClass('disabled-anchor');
                    });
                }

                resizeTimeout = setTimeout(function () {
                    $(window).lazyLoadXT({
                        checkDuplicates: false
                    });
                    clearTimeout(resizeTimeout);
                });

                $(window).on("resize", function () {
                    resizeTimeout = setTimeout(function () {
                        $(window).lazyLoadXT({
                            checkDuplicates: false
                        });
                        clearTimeout(resizeTimeout);
                    });
                });

                if (/Android/i.test(navigator.userAgent)) {
                    $(window).resize(function () {
                        $('input').click(function () {
                            $(this).focus();
                        });
                    });
                }

                if (navigator.appName == 'Microsoft Internet Explorer' || !!(navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/rv:11/)) || (typeof $.browser !== "undefined" && $.browser.msie == 1)) {
                    $(document).ready(function () {
                        $("select").each(function () {
                            $(this).val($(this).find('option:first').val());
                        });
                        $("input[type='text']").each(function () {
                            $(this).val("");
                        });

                    });
                }

                // Make background images clickable
                $('.bg.clickable').on("click", function () {
                    if ($(this).attr('data-target') == "_blank") {
                        window.open($(this).attr('data-redirect'));
                    } else {
                        window.location.href = $(this).attr('data-redirect');
                    }
                });

                /***** Button Group *********/
                // Init button group
                $('.btn-group .btn:first-child').find('.btn-group__icon').show();
                $('.btn-group .btn:first-child').addClass('active');
                $('.btn-group-selected').val($('.btn-group .btn:first-child').attr('data-btn-group-option'));

                // Manage button group
                $('.btn-group .btn').on("click", function (event) {
                    event.preventDefault();
                    if ($(this).hasClass('active')) {
                        //un-comment to allow "no button selected" state
                        //$('.btn-group .btn').removeClass('active');
                        //$('.btn-group .icon').hide();
                        //$('.btn-group-selected').val();
                    } else {
                        $('.btn-group .btn').removeClass('active');
                        $('.btn-group__icon').hide();
                        $(this).find('.btn-group__icon').show();
                        $(this).addClass('active');
                    }
                });
            },
            getViewport: getViewport,
            viewportChange: viewportChange,
            unique: unique,
            isWhole: isWhole,
            isNonblank: isNonblank,
            isNumber: isNumber,
            updatePageFrom: updatePageFrom,
            replaceAll: replaceAll,
            isLeapYear: isLeapYear,
            populateDaysDropDown: populateDaysDropDown,
            toTitleCase: toTitleCase,
            encode: encode,
            escapeChar: escapeChar,
            strTrim: strTrim,
            getQueryStringNew: getQueryStringNew,
            getQueryStringNoHash: getQueryStringNoHash,
            gmapsAutoCompleteInit: gmapsAutoCompleteInit,
            getBrowserName: getBrowserName,
            getCookie: getCookie,
            getQueryString: getQueryString,
            internationalizeDate: internationalizeDate,
            heightMatcher: heightMatcher
        };
    })();
    UtilityModule.init();

    if ($(".global-header").length > 0) {
        var stickyOffset = $('.global-header').offset().top + 20;

        $(window).scroll(function () {
            var scroll_pos = $(window).scrollTop();

            if (scroll_pos >= stickyOffset) {
                $('.ctcTall').addClass('untopped');
                $('.ctcTall').removeClass('topped');
                $('.ctcOffline').addClass('untopped');
                $('.ctcOffline').removeClass('topped');
                $('#tcpa_c2c').css("display", "none");
            } else {
                $('.ctcTall').removeClass('untopped');
                $('.ctcTall').addClass('topped');
                $('.ctcOffline').removeClass('untopped');
                $('.ctcOffline').addClass('topped');
            }
        });
    }

    if ($(".global-header").length > 0) {
        var GlobalHeaderModule = (function () {
            var currentView = UtilityModule.getViewport();
            var currentSpot = 0;
            var resizeMenu = false;

            function adjustMegaMenu() {
                var scroll = $(window).scrollTop();
                if (scroll > 5) {
                    if ($(".cookieShell").length > 0) {
                        $('.megamenu').addClass('cookie-megamenu--minimized');
                    }
                    if ($(".cookieShell").css("display") === "none") {
                        $('.megamenu').removeClass('cookie-megamenu--minimized');
                    }
                    $('.global-header').addClass('global-header--minimized');
                    $('.global-header__left').addClass('global-header__left--minimized');
                    $('.global-header__logo').addClass('global-header__logo--minimized');
                    $('.login-trigger').addClass('login-trigger--minimized');
                    //$('.login-container').css('top','50px');
                    $('.contact-trigger').addClass('contact-trigger--minimized');
                    $('.megamenu').addClass('megamenu--minimized');
                    $('.suggestionsbox').addClass('suggestionsbox--minimized');
                    if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                        var cookieHeight = $(".cookieShell").outerHeight();
                        $('body').css('padding-top', cookieHeight + 50 + 'px');
                    } else {
                        $('body').css('padding-top', '50px');
                    }
                    //$('.login-container').addClass('login-container--minimized');
                    if ($('.microsite-header').length > 0) {
                        $('body').css('padding-top', '0px');
                    }
                } else {
                    if ($(".cookieShell").length > 0) {
                        $('.megamenu').removeClass('cookie-megamenu--minimized');
                    }
                    $('.global-header').removeClass('global-header--minimized');
                    $('.global-header__left').removeClass('global-header__left--minimized');
                    $('.global-header__logo').removeClass('global-header__logo--minimized');
                    $('.login-trigger').removeClass('login-trigger--minimized');
                    //$('.login-container').css('top','70px');
                    $('.contact-trigger').removeClass('contact-trigger--minimized');
                    $('.megamenu').removeClass('megamenu--minimized');
                    $('.suggestionsbox').removeClass('suggestionsbox--minimized');
                    //$('.login-container').removeClass('login-container--minimized');
                    headerPosition();
                }
            }

            //Adjust the width of second row of MegaMenu
            function resizeMegaMenu() {
                if (UtilityModule.getViewport() == "mobile") {
                    if ($(".megamenu").hasClass("megamenu--open")) {
                        if (!$(".megamenu--open").hasClass("megamenu--open--mobile")) {
                            $(".megamenu--open").addClass("megamenu--open--mobile");
                        }
                    }
                }
                if (UtilityModule.getViewport() == "tablet" || UtilityModule.getViewport() == "desktop") {
                    if ($(".megamenu--open").hasClass("megamenu--open--mobile")) {
                        $(".megamenu--open").removeClass("megamenu--open--mobile");
                    }
                    $(".megamenu__sub-items").show();
                    if ($('.megamenu').hasClass('megamenu--open')) {
                        if ($(".contact-trigger").css("display") != "none") {
                            $(".contact-trigger").hide();
                        }
                        if ($(".login-trigger").css("display") != "none") {
                            $(".login-trigger").hide();
                        }
                    } else {
                        if ($(".contact-trigger").css("display") == "none") {
                            $(".contact-trigger").show();
                        }
                        if ($(".login-trigger").css("display") == "none") {
                            $(".login-trigger").show();
                        }
                    }
                    resizeMenu = true;
                } else {
                    if (resizeMenu == true) {
                        if ($(".megamenu__sub-items").css("display") != "none") {
                            $(".megamenu__sub-items").hide();
                        }

                        $('.megamenu__main-icons').removeClass("menu-open");
                    }
                    resizeMenu = false;

                    if ($(".contact-trigger").css("display") != "none") {
                        $(".contact-trigger").hide();
                    }

                    if ($(".login-trigger").css("display") == "none") {
                        $(".login-trigger").show();
                    }
                }
            }

            function headerPosition() {
                var cookieHeightMod;
                if (UtilityModule.getViewport() != "mobile") {
                    cookieHeightMod = 70;
                } else {
                    cookieHeightMod = 50;
                }

                if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                    var cookieHeight = $(".cookieShell").outerHeight();
                    $('body').css('padding-top', (cookieHeight + cookieHeightMod) + 'px');
                } else {
                    $('body').css('padding-top', cookieHeightMod + 'px');
                }
            }

            function closeContactForm() {
                $('.contact-container--global').stop().animate({right: '-640'}, 400);
                $('.contactSideForm').find('.error-mandatory').removeClass('error-mandatory');
                $('.contactSideForm').find('.errorSpanOpen').removeClass('errorSpanOpen');
                $('.contactSideForm').find('.form-user-ctrl').removeClass('error');
                $('.contactSideForm').find('svg').css('fill', '#666');
                $('.productUserType').hide();
                $('.productPolicyTypes').find('select').prop('selectedIndex', 0);
                $('#state_Acc').prop('selectedIndex', 0);
            }

            function openSearchBox() {
                $('.search-trigger').toggleClass('search-trigger--open');
                //actions for mobile viewport
                if (UtilityModule.getViewport() == "mobile") {
                    //check and close megamenu if it is open
                    if ($('.megamenu').is(':visible')) {
                        $('.megamenu').removeClass('megamenu--open');
                        $('.icon-menu').toggle();
                        $('.icon-close').toggle();
                        $(".js-megaMenuToggle").toggleClass("hidden");
                        $('.megamenu-trigger__link').removeClass('megamenu-trigger__icon--open');
                    }
                    //Open searchbox in mobile
                    if ($('.search-trigger__container').css("display") == "none") {
                        $("body > :not('.megamenu, .global-header')").removeClass("megamenu--open--hide");
                        $(".cookieShell").closest(".container").removeClass("megamenu--open--hide");
                        $("html, body, .global-header").removeClass('megamenu--open--style');

                        $('.search-trigger__icon').addClass('search-trigger__icon--open');
                        $('.search-trigger__container').css('display', 'block');
                        if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                            var cookieHeight = $(".cookieShell").outerHeight();
                            $(".search-trigger__container").animate({
                                top: cookieHeight + 50 + "px"
                            }, 50, function () {
                                $('.search-trigger__container').addClass('search-trigger__container--open');
                                $('.search-trigger__container').css('top', '');
                                $('.search-trigger__container').css('display', '');
                                $(".search-trigger__container--open").css("top", cookieHeight + 50 + "px");
                            });
                        } else {
                            $(".search-trigger__container").animate({
                                top: "50"
                            }, 50, function () {
                                $('.search-trigger__container').addClass('search-trigger__container--open');
                                $('.search-trigger__container').css('top', '');
                                $('.search-trigger__container').css('display', '');
                            });
                        }

                    } else {
                        //close searchbox in mobile
                        $('.search-trigger__icon').removeClass('search-trigger__icon--open');
                        $('.search-trigger__container').css('display', 'none');
                        $(".search-trigger__container").animate({
                            top: "0"
                        }, 50, function () {
                            $('.search-trigger__container').removeClass('search-trigger__container--open');
                            $('.search-trigger__container').css('top', '');
                        });
                        setTimeout(function () {
                            $(".search-trigger__container").removeClass('search-trigger__container--open');
                        }, 250);
                    }
                    currentView = UtilityModule.getViewport();
                } else {
                    //actions for tablet/desktop viewport
                    if ($('.search-trigger__container').css("display") == "none") {
                        //open searchbox in tablet/desktop
                        $(".search-trigger__icon").animate({
                            left: "145"
                        }, 150, function () {
                            $('.search-trigger__icon').addClass('search-trigger__icon--open');
                            $('.search-trigger__icon').css('left', '');
                        });


                        $('.search-trigger__container').css('display', 'block');
                        $(".search-trigger__container").animate({
                            top: "50"
                        }, 50, function () {
                            $('.search-trigger__container').addClass('search-trigger__container--open');
                            $('.search-trigger__container').css('top', '');
                            $('.search-trigger__container').css('display', '');
                        });
                        $('.search-trigger__icon').toggleClass('search-trigger__icon--open');
                        currentView = UtilityModule.getViewport();
                    }
                }
            }

            function adjustSearchBox() {
                if ($('.search-trigger__container--open').is(':visible')) {
                    var cookieHeight;
                    if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                        cookieHeight = $(".cookieShell").outerHeight();
                        $(".search-trigger__container").css("top", cookieHeight + 50 + "px");
                    }
                    if (UtilityModule.getViewport() == "mobile") {
                        if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                            cookieHeight = $(".cookieShell").outerHeight();
                            $(".search-trigger__container").css("top", cookieHeight + 50 + "px");
                        }
                        if ($('.megamenu').is(':visible')) {
                            $(".search-trigger__icon").animate({
                                left: "12"
                            }, 50, function () {
                                $('.search-trigger').removeClass('search-trigger--open');
                                $('.search-trigger__icon').css('left', '');
                            });

                            $('.search-trigger__container').css('display', 'none');
                            $(".search-trigger__container").animate({
                                top: "0"
                            }, 50, function () {
                                $('.search-trigger__container').removeClass('search-trigger__container--open');
                                $('.search-trigger__container').css('top', '');
                            });
                            setTimeout(function () {
                                $(".search-trigger__container").removeClass('search-trigger__container--open');
                            }, 250);
                            currentView = UtilityModule.getViewport();
                        }
                    }
                }
                if (UtilityModule.getViewport() != "mobile") {
                    //This assumes that the cookieShell (i.e the cookie banner) exists on the page
                    //This was affecting the searchbox on resize.  Adding an additional check that if the cookie
                    //banner doesn't exist, we do not remove the style attribute on the search container div
                    //on resize.
                    if ($(".cookieShell.hidden").length == 0 && $(".cookieShell").css('display') != "none" && $(".cookieShell").css('display') != null) {
                        if ($('.cookieShell').length == 0) {
                            return false;
                        } else {
                            $(".search-trigger__container--open").removeAttr('style');
                        }
                    }
                    if ($('.megamenu').is(':visible')) {
                        $('.search-trigger__container').css('display', 'block');
                        $(".search-trigger__container").animate({
                            top: "50"
                        }, 50, function () {
                            $('.search-trigger__container').addClass('search-trigger__container--open');
                            $('.search-trigger__container').css('top', '');
                            $('.search-trigger__container').css('display', '');
                        });
                        $(".search-trigger__icon").animate({
                            left: "145"
                        }, 150, function () {
                            $('.search-trigger__icon').addClass('search-trigger__icon--open');
                            $('.search-trigger__icon').css('left', '');
                        });
                        currentView = UtilityModule.getViewport();
                    }
                }
            }

            function closeSearchBox() {
                $('.search-trigger').removeClass('search-trigger--open');
                $('.search-trigger__icon').removeClass('search-trigger__icon--open');
                setTimeout(function () {
                    $('.search-trigger__container').hide();
                }, 100);
            }

            return {
                init: function () {
                    adjustMegaMenu();

                    $(window).on({
                        scroll: function () {
                            adjustMegaMenu();
                        },
                        resize: function () {
                            var thisView = UtilityModule.getViewport();
                            headerPosition();
                            resizeMegaMenu();
                            if (thisView != currentView) {
                                adjustSearchBox();
                                closeContactForm();
                                currentView = UtilityModule.getViewport();
                            }
                        }
                    });

                    $(window).bind('pageshow', function () {
                        $('.search-trigger__search-box').val("");
                    });

                    $('body').on('click touchstart tap', function (e) {
                        var megaMenuTrigger = $(".megamenu-trigger");
                        var container = $(".search-trigger");
                        var suggestions = $(".suggestionsbox");
                        var suggestionsTable = $(".ss-gac-table");
                        var suggestionsTableBody = $(".ss-gac-m");
                        var suggestionsTableBodyRowA = $(".ss-gac-a");
                        var suggestionsTableBodyRowB = $(".ss-gac-b");
                        var suggestionsTableBodyRowC = $(".ss-gac-c");
                        if (!$('.megamenu-trigger__link').hasClass('megamenu-trigger__icon--open')) {
                            if (!suggestionsTable.is(e.target) && suggestionsTable.has(e.target).length === 0 && !suggestions.is(e.target) && suggestions.has(e.target).length === 0 && !container.is(e.target) && container.has(e.target).length === 0 && !megaMenuTrigger.is(e.target) && megaMenuTrigger.has(e.target).length == 0 && !suggestionsTableBody.is(e.target) && suggestionsTableBody.has(e.target).length == 0 && !suggestionsTableBodyRowA.is(e.target) && suggestionsTableBodyRowA.has(e.target).length == 0 && !suggestionsTableBodyRowB.is(e.target) && suggestionsTableBodyRowB.has(e.target).length == 0 && !suggestionsTableBodyRowC.is(e.target) && suggestionsTableBodyRowC.has(e.target).length == 0) {
                                closeSearchBox();
                            }
                        }
                    });

                    $('.megamenu-trigger').on('click', function () {
                        if ($(".icon-close.megamenu-trigger__icon").css("display") == "none") {
                            currentSpot = $('body').scrollTop();
                            $(".icon-close.megamenu-trigger__icon").css("display", "inline-block");
                            $(".icon-menu.megamenu-trigger__icon").css("display", "none");
                            $("html, body").animate({scrollTop: 0}, 1);
                        } else {
                            $(".icon-close.megamenu-trigger__icon").css("display", "none");
                            $(".icon-menu.megamenu-trigger__icon").css("display", "inline-block");
                            $("html, body").animate({scrollTop: currentSpot}, 1);
                        }

                        $('.' + $(this).attr('data-target')).toggleClass('megamenu--open');

                        if ($('.microsite-header').length == 0) {
                            $("body > :not('.megamenu, .global-header')").toggleClass("megamenu--open--hide");
                            $(".cookieShell").closest(".container").removeClass("megamenu--open--hide");
                        }

                        $("html, body, .global-header").toggleClass('megamenu--open--style');
                        $(".js-megaMenuToggle").toggleClass("hidden");
                        $('.login-container').hide();

                        closeContactForm();
                        $('.megamenu-trigger__link').toggleClass('megamenu-trigger__icon--open');
                        if (UtilityModule.getViewport() == "desktop") {
                            if ($('.megamenu').hasClass('megamenu--open')) {
                                if ($('.megamenu-trigger__link').hasClass('megamenu-trigger__icon--open')) {
                                    if (!$('.search-trigger__container').is(':visible')) {
                                        openSearchBox();
                                    }
                                } else {
                                    if ($('.search-trigger__container').is(':visible')) {
                                        closeSearchBox();
                                    }
                                }

                                if ($('.login-trigger').length != 0) {
                                    $('.login-trigger').hide();
                                }
                                if ($('.contact-trigger').length != 0) {
                                    $('.contact-trigger').hide();
                                }
                                if ($('.user-trigger').length != 0) {
                                    $('.user-trigger').hide();
                                }
                            } else {
                                if ($('.megamenu-trigger__link').hasClass('megamenu-trigger__icon--open')) {
                                    if (!$('.search-trigger__container').is(':visible')) {
                                        openSearchBox();
                                    }
                                } else {
                                    if ($('.search-trigger__container').is(':visible')) {
                                        closeSearchBox();
                                    }
                                }
                                if ($('.login-trigger').length != 0) {
                                    $('.login-trigger').show();
                                }
                                if ($('.contact-trigger').length != 0) {
                                    $('.contact-trigger').show();
                                }
                                if ($('.user-trigger').length != 0) {
                                    $('.user-trigger').show();
                                }
                            }
                        } else if (UtilityModule.getViewport() == "tablet") {
                            if ($('.megamenu').hasClass('megamenu--open')) {
                                if ($('.megamenu-trigger__link').hasClass('megamenu-trigger__icon--open')) {
                                    if (!$('.search-trigger__container').is(':visible')) {
                                        openSearchBox();
                                    }
                                } else {
                                    if ($('.search-trigger__container').is(':visible')) {
                                        closeSearchBox();
                                    }
                                }
                                if ($('.login-trigger').length != 0) {
                                    $('.login-trigger').hide();
                                }
                                if ($('.contact-trigger').length != 0) {
                                    $('.contact-trigger').hide();
                                }
                                if ($('.user-trigger').length != 0) {
                                    $('.user-trigger').hide();
                                }
                            } else {
                                if ($('.megamenu-trigger__link').hasClass('megamenu-trigger__icon--open')) {
                                    if (!$('.search-trigger__container').is(':visible')) {
                                        openSearchBox();
                                    }
                                } else {
                                    if ($('.search-trigger__container').is(':visible')) {
                                        closeSearchBox();
                                    }
                                }
                                if ($('.login-trigger').length != 0) {
                                    $('.login-trigger').show();
                                }
                                if ($('.contact-trigger').length != 0) {
                                    $('.contact-trigger').show();
                                }
                                if ($('.user-trigger').length != 0) {
                                    $('.user-trigger').show();
                                }
                            }
                        } else {
                            closeSearchBox();
                            $(".megamenu").toggleClass('megamenu--open--mobile');
                        }
                    });

                    $('.search-trigger__icon, .search-trigger__label').on("click", function () {
                        openSearchBox();
                    });

                    $('.login-trigger').on("click", function (e) {
                        if (!$(".login-trigger").hasClass("linkOnly")) {
                            e.preventDefault();
                            $('body').addClass('overlay-scroll__parent');
                            $('.login-container').addClass('overlay-scroll__child');
                            $('.login-types').addClass('overlay-scroll__child');
                            $(".global-header__middle").addClass("menu--left");
                            $('.' + $(this).attr('data-target')).slideToggle();
                            if ($('.megamenu').is(':visible')) {
                                $("body > :not('.megamenu, .global-header')").removeClass("megamenu--open--hide");
                                $(".cookieShell").closest(".container").removeClass("megamenu--open--hide");
                                $("html, body, .global-header").removeClass('megamenu--open--style');
                                $('.megamenu').removeClass("overlay-scroll__child");
                                $('.megamenu').toggleClass('megamenu--open');
                                $('.icon-close').toggle();
                                $(".js-megaMenuToggle").toggleClass("hidden");
                                $('.icon-menu').toggle();
                            }
                        }
                    });

                    $('.contact-trigger').on("click", function (e) {
                        e.preventDefault();
                        currentView = UtilityModule.getViewport();
                        $("#contactSidebar").find(".form-user-grp").each(function () {
                            $(this).find("input, select, textarea").removeClass('error');
                            $(this).find("input, select, textarea").val('');
                        });
                        $(".contactSidebar .contactOtherDetails").show();
                        $('.contact-container--global').stop().animate({right: '0'}, 400);
                    });

                    $('.contact-close').on("click", function (e) {
                        e.preventDefault();
                        closeContactForm();
                    });

                    $('.productPolicyTypes').on('change', function () {
                        currentView = UtilityModule.getViewport();
                    });
                },
                headerPosition: headerPosition,
                openSearchBox: openSearchBox,
                closeSearchBox: closeSearchBox
            };
        })();
        GlobalHeaderModule.init();
    }
</script>


</html>







