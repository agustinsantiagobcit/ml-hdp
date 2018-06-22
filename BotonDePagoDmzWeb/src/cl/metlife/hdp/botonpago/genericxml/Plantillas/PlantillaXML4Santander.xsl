<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/">

<Fin>
	<IdentificadorComercio><xsl:value-of select="substring(/MPFIN/IDTRX,1,10)"/></IdentificadorComercio>
	<CodigoRetorno><xsl:value-of select="/MPFIN/CODRET"/></CodigoRetorno>
	<NroBoletas><xsl:value-of select="/MPFIN/NROPAGOS"/></NroBoletas>
	<Monto><xsl:value-of select="/MPFIN/TOTAL"/></Monto>
	<Firma></Firma>
	<IdTransaccionRemoto></IdTransaccionRemoto>
</Fin>

</xsl:template>

</xsl:stylesheet>
