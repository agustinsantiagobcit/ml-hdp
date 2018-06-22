<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/InicioPago">
  <MPINI> 
	<IDCOM><xsl:value-of select="/InicioPago/InfoPago/IdentificadorComercio"/></IDCOM> 
	<IDTRX><xsl:value-of select="/InicioPago/InfoPago/IdPago"/></IDTRX> 
	<TOTAL><xsl:value-of select="/InicioPago/InfoPago/MontoTotal"/></TOTAL> 
	<NROPAGOS><xsl:value-of select="/InicioPago/InfoPago/NroBoletas"/></NROPAGOS> 
	<xsl:for-each select="/InicioPago/InfoPago/Boleta">
		<DETALLE> 
		<SRVREC><xsl:value-of select="IdentificadorTienda"/></SRVREC> 
		<MONTO><xsl:value-of select="Monto"/></MONTO> 
		<GLOSA><xsl:value-of select="Glosa"/></GLOSA> 
		<CANTIDAD>1</CANTIDAD> 
		<PRECIO><xsl:value-of select="Monto"/></PRECIO> 
		<DATOADIC></DATOADIC> 
	</DETALLE> 
	</xsl:for-each>
</MPINI>

</xsl:template>


</xsl:stylesheet>
