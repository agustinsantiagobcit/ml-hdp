<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 09-09-2014
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creacion de pagos</title>
</head>
<body>

<h1>Pagos de test</h1>
<p>
    Esta página permite crear pagos en el sistema para propósitos de test.
</p>

<p>
    Al ser de prueba, esta página no realiza validaciones de datos.
</p>

<p>
    Se permite crear el pago de hasta 6 boletas simultaneamente. Si no se desea pagar con alguna boleta, se debe setear su monto en 0. Montos en pesos.
</p>
<br/><br/>
<form action="CrearPago" method="post" />
    <input type="submit"/><br/>


    Nombre: <input name="nombre" type="text" value="Juan Perez" /> <br/>
    Rut: <input name="rut" type="text" value="1-9"/> <br/><br/><br/><br/>

    Boleta 1: <br/>
    Monto (clp): <input name="b1Monto" value="1"/> <br/>
    Numero Boleta (producto): <input name="b1idBoleta"  value="1"/> <br/>
    Descripción: <input name="b1Descripcion"  value="Boleta 1"/> <br/>
    <br/>

    Boleta 2: <br/>
    Monto (clp): <input name="b2Monto" value="0"/> <br/>
    Numero Boleta (producto): <input name="b2idBoleta"  value="2"/> <br/>
    Descripción: <input name="b2Descripcion" value="Boleta 2"/> <br/>
    <br/>

    Boleta 3: <br/>
    Monto (clp): <input name="b3Monto" value="0"/> <br/>
    Numero Boleta (producto): <input name="b3idBoleta"  value="3"/> <br/>
    Descripción: <input name="b3Descripcion" value="Boleta 3"/> <br/>
    <br/>

    Boleta 4: <br/>
    Monto (clp): <input name="b4Monto" value="0"/> <br/>
    Numero Boleta (producto): <input name="b4idBoleta"  value="4"/> <br/>
    Descripción: <input name="b4Descripcion" value="Boleta 4"/> <br/>
    <br/>

    Boleta 5: <br/>
    Monto (clp): <input name="b5Monto" value="0"/> <br/>
    Numero Boleta (producto): <input name="b5idBoleta"  value="5"/> <br/>
    Descripción: <input name="b5Descripcion" value="Boleta 5"/> <br/>
    <br/>

    Boleta 2: <br/>
    Monto (clp): <input name="b6Monto" value="0"/> <br/>
    Numero Boleta (producto): <input name="b6idBoleta"  value="6"/> <br/>
    Descripción: <input name="b6Descripcion" value="Boleta 6"/> <br/>
    <br/>

    <input type="submit"/>

</form>

</body>
</html>
