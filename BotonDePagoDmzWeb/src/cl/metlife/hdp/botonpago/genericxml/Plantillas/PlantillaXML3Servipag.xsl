<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" encoding="ISO-8859-1"/>

<xsl:template match="/NotificacionPago">
<Servipag>
	<CodigoRetorno><xsl:value-of select="/NotificacionPago/Codigo"/></CodigoRetorno>
	<xsl:if test="Codigo = 000">
		<MensajeRetorno>Confirmación Exitosa</MensajeRetorno>
	</xsl:if>
	<xsl:if test="Codigo = 001">
		<MensajeRetorno>Confirmación Rechazada</MensajeRetorno>
	</xsl:if>
</Servipag>
</xsl:template>

</xsl:stylesheet>
