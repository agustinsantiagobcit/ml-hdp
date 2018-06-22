<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 27-08-2014
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<html>
    <head>
        <script language="javascript" type="text/javascript">
            function sleepFor( sleepDuration ){
                var now = new Date().getTime();
                while(new Date().getTime() < now + sleepDuration){ /* do nothing */ }
            }

            function callPost()
            {
                sleepFor(8000);
                document.form.submit();
            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Iniciando el pago...</title>
    </head>
    <body onload="javascript:callPost();" >
        Iniciando el pago...

        <form name="form" id="form" action="${postToUrl}" target="${PaymentTarget}" method="post">
            <input type="hidden" id="${postParamName}" name="${postParamName}" value="${postContent}" />
        </form>
    </body>
</html>
