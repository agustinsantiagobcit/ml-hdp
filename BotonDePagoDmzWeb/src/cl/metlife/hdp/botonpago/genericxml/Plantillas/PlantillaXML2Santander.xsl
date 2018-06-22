<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/">
<ConfirmacionSolicitudPago>
	<IdentificadorComercio><xsl:value-of select="/MPOUT/IDCOM"/></IdentificadorComercio>
	<InfoPago>
		<IdPago><xsl:value-of select="substring(/MPOUT/IDTRX,11)"/></IdPago>
		<FechaTransaccion>
		         <Dia><xsl:value-of select="substring(/MPOUT/FECHATRX,7)"/></Dia>
		        <Mes><xsl:value-of select="substring(/MPOUT/FECHATRX,5,2)"/></Mes>
		         <Agno><xsl:value-of select="substring(/MPOUT/FECHATRX,1,4)"/></Agno>
               </FechaTransaccion>
		<FechaContable>
		         <Dia><xsl:value-of select="substring(/MPOUT/FECHACONT,7)"/></Dia>
		        <Mes><xsl:value-of select="substring(/MPOUT/FECHACONT,5,2)"/></Mes>
		         <Agno><xsl:value-of select="substring(/MPOUT/FECHACONT,1,4)"/></Agno>
               </FechaContable>
		<Estado><xsl:value-of select="/MPOUT/CODRET"/></Estado>
		<DescriptorRetorno><xsl:value-of select="/MPOUT/DESCRET"/></DescriptorRetorno>
		<MontoTotal><xsl:value-of select="/MPOUT/TOTAL"/></MontoTotal>
		<NroBoletas><xsl:value-of select="/MPOUT/NROPAGOS"/></NroBoletas>
		<Comprobante><xsl:value-of select="/MPOUT/NUMCOMP"/></Comprobante>
		<CodigoAutorizacion><xsl:value-of select="/MPOUT/IDREG"/></CodigoAutorizacion>
		<Firma></Firma>
		<IdTransaccionRemoto></IdTransaccionRemoto>
		<MedioPago></MedioPago>
	</InfoPago>
	<Boletas>
		<Monto></Monto>
		<idBoleta></idBoleta>
	</Boletas>
</ConfirmacionSolicitudPago>
</xsl:template>

</xsl:stylesheet>
