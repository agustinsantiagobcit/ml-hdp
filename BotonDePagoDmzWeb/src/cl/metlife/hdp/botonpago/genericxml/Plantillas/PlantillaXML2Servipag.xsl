<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/">
<ConfirmacionSolicitudPago>
	<IdentificadorComercio><xsl:value-of select="/Servipag/CodigoIdentificador"/></IdentificadorComercio>
	<InfoPago>
		<IdPago><xsl:value-of select="/Servipag/IdTxCliente"/></IdPago>
		<FechaTransaccion>
		         <Dia><xsl:value-of select="substring(/Servipag/FechaPago,7)"/></Dia>
		        <Mes><xsl:value-of select="substring(/Servipag/FechaPago,5,2)"/></Mes>
		         <Agno><xsl:value-of select="substring(/Servipag/FechaPago,1,4)"/></Agno>
               </FechaTransaccion>
		<FechaContable>
		         <Dia><xsl:value-of select="substring(/Servipag/FechaContable,7)"/></Dia>
		        <Mes><xsl:value-of select="substring(/Servipag/FechaContable,5,2)"/></Mes>
		         <Agno><xsl:value-of select="substring(/Servipag/FechaContable,1,4)"/></Agno>
               </FechaContable>
		<Estado></Estado>
		<DescriptorRetorno></DescriptorRetorno>
		<MontoTotal></MontoTotal>
		<NroBoletas></NroBoletas>
		<Comprobante></Comprobante>
		<CodigoAutorizacion></CodigoAutorizacion>
		<Firma><xsl:value-of select="/Servipag/FirmaServipag"/></Firma>
		<IdTransaccionRemoto><xsl:value-of select="/Servipag/IdTrxServipag"/></IdTransaccionRemoto>
		<MedioPago><xsl:value-of select="/Servipag/CodMedioPago"/></MedioPago>
	</InfoPago>
	<Boletas>
		<Monto><xsl:value-of select="/Servipag/Monto"/></Monto>
		<idBoleta><xsl:value-of select="/Servipag/Boleta"/></idBoleta>
	</Boletas>
</ConfirmacionSolicitudPago>
</xsl:template>

</xsl:stylesheet>
