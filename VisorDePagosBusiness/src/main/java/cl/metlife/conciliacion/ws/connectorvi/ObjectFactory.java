//
// Generated By:JAX-WS RI IBM 2.1.6 in JDK 6 (JAXB RI IBM JAXB 2.1.10 in JDK 6)
//


package cl.metlife.conciliacion.ws.connectorvi;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.metlife.conciliacion.ws.connectorvi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _SalidaResultadoConciliacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "salida_resultadoConciliacion");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _TipoDocumento_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "tipoDocumento");
    private final static QName _ResultadoConciliacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "resultadoConciliacion");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Moneda_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "moneda");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Pago_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "pago");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _ConciliarPagoResponseConciliarPagoResult_QNAME = new QName("http://tempuri.org/", "ConciliarPagoResult");
    private final static QName _PagoAgnoCuota_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "AgnoCuota");
    private final static QName _PagoNumeroDocumento_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "NumeroDocumento");
    private final static QName _PagoNumeroProducto_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "NumeroProducto");
    private final static QName _PagoMesCuota_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "MesCuota");
    private final static QName _PagoNumeroConvenio_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "NumeroConvenio");
    private final static QName _PagoCuentaControl_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "CuentaControl");
    private final static QName _ConciliarPagoInformacionPago_QNAME = new QName("http://tempuri.org/", "InformacionPago");
    private final static QName _ResultadoConciliacionDetalleEstado_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "DetalleEstado");
    private final static QName _ResultadoConciliacionNuevoEstado_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "NuevoEstado");
    private final static QName _ResultadoConciliacionNumeroFactura_QNAME = new QName("http://schemas.datacontract.org/2004/07/", "NumeroFactura");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.metlife.conciliacion.ws.connectorvi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConciliarPagoResponse }
     * 
     */
    public ConciliarPagoResponse createConciliarPagoResponse() {
        return new ConciliarPagoResponse();
    }

    /**
     * Create an instance of {@link Pago }
     * 
     */
    public Pago createPago() {
        return new Pago();
    }

    /**
     * Create an instance of {@link ConciliarPago }
     * 
     */
    public ConciliarPago createConciliarPago() {
        return new ConciliarPago();
    }

    /**
     * Create an instance of {@link ResultadoConciliacion }
     * 
     */
    public ResultadoConciliacion createResultadoConciliacion() {
        return new ResultadoConciliacion();
    }

    /**
     * Create an instance of {@link SalidaResultadoConciliacion }
     * 
     */
    public SalidaResultadoConciliacion createSalidaResultadoConciliacion() {
        return new SalidaResultadoConciliacion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalidaResultadoConciliacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "salida_resultadoConciliacion")
    public JAXBElement<SalidaResultadoConciliacion> createSalidaResultadoConciliacion(SalidaResultadoConciliacion value) {
        return new JAXBElement<SalidaResultadoConciliacion>(_SalidaResultadoConciliacion_QNAME, SalidaResultadoConciliacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "tipoDocumento")
    public JAXBElement<TipoDocumento> createTipoDocumento(TipoDocumento value) {
        return new JAXBElement<TipoDocumento>(_TipoDocumento_QNAME, TipoDocumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoConciliacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "resultadoConciliacion")
    public JAXBElement<ResultadoConciliacion> createResultadoConciliacion(ResultadoConciliacion value) {
        return new JAXBElement<ResultadoConciliacion>(_ResultadoConciliacion_QNAME, ResultadoConciliacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Moneda }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "moneda")
    public JAXBElement<Moneda> createMoneda(Moneda value) {
        return new JAXBElement<Moneda>(_Moneda_QNAME, Moneda.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pago }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "pago")
    public JAXBElement<Pago> createPago(Pago value) {
        return new JAXBElement<Pago>(_Pago_QNAME, Pago.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalidaResultadoConciliacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ConciliarPagoResult", scope = ConciliarPagoResponse.class)
    public JAXBElement<SalidaResultadoConciliacion> createConciliarPagoResponseConciliarPagoResult(SalidaResultadoConciliacion value) {
        return new JAXBElement<SalidaResultadoConciliacion>(_ConciliarPagoResponseConciliarPagoResult_QNAME, SalidaResultadoConciliacion.class, ConciliarPagoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "AgnoCuota", scope = Pago.class)
    public JAXBElement<String> createPagoAgnoCuota(String value) {
        return new JAXBElement<String>(_PagoAgnoCuota_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "NumeroDocumento", scope = Pago.class)
    public JAXBElement<String> createPagoNumeroDocumento(String value) {
        return new JAXBElement<String>(_PagoNumeroDocumento_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "NumeroProducto", scope = Pago.class)
    public JAXBElement<String> createPagoNumeroProducto(String value) {
        return new JAXBElement<String>(_PagoNumeroProducto_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "MesCuota", scope = Pago.class)
    public JAXBElement<String> createPagoMesCuota(String value) {
        return new JAXBElement<String>(_PagoMesCuota_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "NumeroConvenio", scope = Pago.class)
    public JAXBElement<String> createPagoNumeroConvenio(String value) {
        return new JAXBElement<String>(_PagoNumeroConvenio_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "CuentaControl", scope = Pago.class)
    public JAXBElement<String> createPagoCuentaControl(String value) {
        return new JAXBElement<String>(_PagoCuentaControl_QNAME, String.class, Pago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pago }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "InformacionPago", scope = ConciliarPago.class)
    public JAXBElement<Pago> createConciliarPagoInformacionPago(Pago value) {
        return new JAXBElement<Pago>(_ConciliarPagoInformacionPago_QNAME, Pago.class, ConciliarPago.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "DetalleEstado", scope = ResultadoConciliacion.class)
    public JAXBElement<String> createResultadoConciliacionDetalleEstado(String value) {
        return new JAXBElement<String>(_ResultadoConciliacionDetalleEstado_QNAME, String.class, ResultadoConciliacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "NuevoEstado", scope = ResultadoConciliacion.class)
    public JAXBElement<String> createResultadoConciliacionNuevoEstado(String value) {
        return new JAXBElement<String>(_ResultadoConciliacionNuevoEstado_QNAME, String.class, ResultadoConciliacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "CuentaControl", scope = ResultadoConciliacion.class)
    public JAXBElement<String> createResultadoConciliacionCuentaControl(String value) {
        return new JAXBElement<String>(_PagoCuentaControl_QNAME, String.class, ResultadoConciliacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "NumeroFactura", scope = ResultadoConciliacion.class)
    public JAXBElement<String> createResultadoConciliacionNumeroFactura(String value) {
        return new JAXBElement<String>(_ResultadoConciliacionNumeroFactura_QNAME, String.class, ResultadoConciliacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoConciliacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/", name = "resultadoConciliacion", scope = SalidaResultadoConciliacion.class)
    public JAXBElement<ResultadoConciliacion> createSalidaResultadoConciliacionResultadoConciliacion(ResultadoConciliacion value) {
        return new JAXBElement<ResultadoConciliacion>(_ResultadoConciliacion_QNAME, ResultadoConciliacion.class, SalidaResultadoConciliacion.class, value);
    }

}
