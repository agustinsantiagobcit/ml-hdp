//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.hdp.botonpago.dawsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pago complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pago">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="archivosConciliacion" type="{http://daws.botonpago.hdp.metlife.cl/}archivosConciliacion" minOccurs="0"/>
 *         &lt;element name="cliente" type="{http://daws.botonpago.hdp.metlife.cl/}cliente" minOccurs="0"/>
 *         &lt;element name="codigosMoneda" type="{http://daws.botonpago.hdp.metlife.cl/}codigosMoneda" minOccurs="0"/>
 *         &lt;element name="cuentaControl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://daws.botonpago.hdp.metlife.cl/}estado" minOccurs="0"/>
 *         &lt;element name="fechaPago" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idPago" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="institucionesDePago" type="{http://daws.botonpago.hdp.metlife.cl/}institucionesDePago" minOccurs="0"/>
 *         &lt;element name="lineasDeNegocio" type="{http://daws.botonpago.hdp.metlife.cl/}lineasDeNegocio" minOccurs="0"/>
 *         &lt;element name="logs" type="{http://daws.botonpago.hdp.metlife.cl/}log" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="montoAPagar" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="montoPagado" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroProducto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="periodoAgno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="periodoMes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pago", namespace = "http://daws.botonpago.hdp.metlife.cl/", propOrder = {
    "archivosConciliacion",
    "cliente",
    "codigosMoneda",
    "cuentaControl",
    "estado",
    "fechaPago",
    "idPago",
    "institucionesDePago",
    "lineasDeNegocio",
    "logs",
    "montoAPagar",
    "montoPagado",
    "numeroDocumento",
    "numeroFactura",
    "numeroProducto",
    "periodoAgno",
    "periodoMes"
})
public class Pago {

    protected ArchivosConciliacion archivosConciliacion;
    protected Cliente cliente;
    protected CodigosMoneda codigosMoneda;
    protected String cuentaControl;
    protected Estado estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaPago;
    protected long idPago;
    protected InstitucionesDePago institucionesDePago;
    protected LineasDeNegocio lineasDeNegocio;
    @XmlElement(nillable = true)
    protected List<Log> logs;
    protected Double montoAPagar;
    protected Double montoPagado;
    protected Integer numeroDocumento;
    protected String numeroFactura;
    protected Integer numeroProducto;
    protected String periodoAgno;
    protected String periodoMes;

    /**
     * Gets the value of the archivosConciliacion property.
     * 
     * @return
     *     possible object is
     *     {@link ArchivosConciliacion }
     *     
     */
    public ArchivosConciliacion getArchivosConciliacion() {
        return archivosConciliacion;
    }

    /**
     * Sets the value of the archivosConciliacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchivosConciliacion }
     *     
     */
    public void setArchivosConciliacion(ArchivosConciliacion value) {
        this.archivosConciliacion = value;
    }

    /**
     * Gets the value of the cliente property.
     * 
     * @return
     *     possible object is
     *     {@link Cliente }
     *     
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Sets the value of the cliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cliente }
     *     
     */
    public void setCliente(Cliente value) {
        this.cliente = value;
    }

    /**
     * Gets the value of the codigosMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link CodigosMoneda }
     *     
     */
    public CodigosMoneda getCodigosMoneda() {
        return codigosMoneda;
    }

    /**
     * Sets the value of the codigosMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigosMoneda }
     *     
     */
    public void setCodigosMoneda(CodigosMoneda value) {
        this.codigosMoneda = value;
    }

    /**
     * Gets the value of the cuentaControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaControl() {
        return cuentaControl;
    }

    /**
     * Sets the value of the cuentaControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaControl(String value) {
        this.cuentaControl = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link Estado }
     *     
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Estado }
     *     
     */
    public void setEstado(Estado value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fechaPago property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPago() {
        return fechaPago;
    }

    /**
     * Sets the value of the fechaPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPago(XMLGregorianCalendar value) {
        this.fechaPago = value;
    }

    /**
     * Gets the value of the idPago property.
     * 
     */
    public long getIdPago() {
        return idPago;
    }

    /**
     * Sets the value of the idPago property.
     * 
     */
    public void setIdPago(long value) {
        this.idPago = value;
    }

    /**
     * Gets the value of the institucionesDePago property.
     * 
     * @return
     *     possible object is
     *     {@link InstitucionesDePago }
     *     
     */
    public InstitucionesDePago getInstitucionesDePago() {
        return institucionesDePago;
    }

    /**
     * Sets the value of the institucionesDePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitucionesDePago }
     *     
     */
    public void setInstitucionesDePago(InstitucionesDePago value) {
        this.institucionesDePago = value;
    }

    /**
     * Gets the value of the lineasDeNegocio property.
     * 
     * @return
     *     possible object is
     *     {@link LineasDeNegocio }
     *     
     */
    public LineasDeNegocio getLineasDeNegocio() {
        return lineasDeNegocio;
    }

    /**
     * Sets the value of the lineasDeNegocio property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineasDeNegocio }
     *     
     */
    public void setLineasDeNegocio(LineasDeNegocio value) {
        this.lineasDeNegocio = value;
    }

    /**
     * Gets the value of the logs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Log }
     * 
     * 
     */
    public List<Log> getLogs() {
        if (logs == null) {
            logs = new ArrayList<Log>();
        }
        return this.logs;
    }

    /**
     * Gets the value of the montoAPagar property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMontoAPagar() {
        return montoAPagar;
    }

    /**
     * Sets the value of the montoAPagar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMontoAPagar(Double value) {
        this.montoAPagar = value;
    }

    /**
     * Gets the value of the montoPagado property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMontoPagado() {
        return montoPagado;
    }

    /**
     * Sets the value of the montoPagado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMontoPagado(Double value) {
        this.montoPagado = value;
    }

    /**
     * Gets the value of the numeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Sets the value of the numeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroDocumento(Integer value) {
        this.numeroDocumento = value;
    }

    /**
     * Gets the value of the numeroFactura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Sets the value of the numeroFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFactura(String value) {
        this.numeroFactura = value;
    }

    /**
     * Gets the value of the numeroProducto property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroProducto() {
        return numeroProducto;
    }

    /**
     * Sets the value of the numeroProducto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroProducto(Integer value) {
        this.numeroProducto = value;
    }

    /**
     * Gets the value of the periodoAgno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriodoAgno() {
        return periodoAgno;
    }

    /**
     * Sets the value of the periodoAgno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriodoAgno(String value) {
        this.periodoAgno = value;
    }

    /**
     * Gets the value of the periodoMes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriodoMes() {
        return periodoMes;
    }

    /**
     * Sets the value of the periodoMes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriodoMes(String value) {
        this.periodoMes = value;
    }

}
