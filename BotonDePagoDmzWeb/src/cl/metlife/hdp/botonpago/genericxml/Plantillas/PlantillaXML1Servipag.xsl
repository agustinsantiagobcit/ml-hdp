<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/InicioPago">
<xsl:param name="guion">-</xsl:param> 
<Servipag>
	<Header>
		<FirmaEPS ><xsl:value-of select="/InicioPago/InfoPago/Firma"/></FirmaEPS>
		<CodigoCanalPago><xsl:value-of select="/InicioPago/InfoPago/IdentificadorComercio"/></CodigoCanalPago>
		<IdTxPago><xsl:value-of select="/InicioPago/InfoPago/IdPago"/></IdTxPago>
		<FechaPago><xsl:value-of select="/InicioPago/InfoPago/FechaPago/Agno"/><xsl:value-of select="/InicioPago/InfoPago/FechaPago/Mes"/><xsl:value-of select="/InicioPago/InfoPago/FechaPago/Dia"/></FechaPago>
		<MontoTotalDeuda><xsl:value-of select="/InicioPago/InfoPago/MontoTotal"/></MontoTotalDeuda>
		<NumeroBoletas><xsl:value-of select="/InicioPago/InfoPago/NroBoletas"/></NumeroBoletas>
		<NombreCliente><xsl:value-of select="/InicioPago/Cliente/Nombre"/></NombreCliente>
		<RutCliente><xsl:value-of select="translate(normalize-space(/InicioPago/Cliente/Rut),$guion,'')" /></RutCliente>
		<EmailCliente><xsl:value-of select="/InicioPago/Cliente/Email"/></EmailCliente>
		<Version>2</Version>
	</Header>
	<xsl:for-each select="/InicioPago/InfoPago/Boleta">
	<Documentos>
		<IdSubTx><xsl:number format="1" value="position()" /></IdSubTx>
		<Identificador><xsl:value-of select="IdentificadorTienda"/></Identificador>
		<Boleta><xsl:value-of select="IdBoleta"/></Boleta>
		<Monto><xsl:value-of select="Monto"/></Monto>
		<FechaVencimiento><xsl:value-of select="FechaVenc/Agno"/><xsl:value-of select="FechaVenc/Mes"/><xsl:value-of select="FechaVenc/Dia"/></FechaVencimiento>
	</Documentos>
	</xsl:for-each>
</Servipag>

</xsl:template>

</xsl:stylesheet>
