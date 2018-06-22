<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/">

<Fin>
	<IdentificadorComercio><xsl:value-of select="/Servipag/IdTxCliente"/></IdentificadorComercio>
	<CodigoRetorno><xsl:value-of select="/Servipag/EstadoPago"/></CodigoRetorno>
	<NroBoletas></NroBoletas>
	<Monto></Monto>
	<Firma><xsl:value-of select="/Servipag/FirmaServipag"/></Firma>
	<IdTransaccionRemoto><xsl:value-of select="/Servipag/IdTrxServipag"/></IdTransaccionRemoto>
</Fin>

</xsl:template>
</xsl:stylesheet>
