<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://ws.conciliacion.hdp.metlife.cl/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://ws.conciliacion.hdp.metlife.cl/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://ws.conciliacion.hdp.metlife.cl/">
      <s:element name="ConciliarPago">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="InformacionPago" type="tns:pago" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="pago">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="NumeroConvenio" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="CuentaControl" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" form="unqualified" name="TipoDocumentoPago" type="tns:tipoDocumento" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="NumeroDocumento" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="NumeroProducto" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="MesCuota" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="AgnoCuota" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" form="unqualified" name="MontoPagado" type="s:double" />
          <s:element minOccurs="1" maxOccurs="1" form="unqualified" name="CodigoMoneda" type="tns:moneda" />
          <s:element minOccurs="1" maxOccurs="1" form="unqualified" name="FechaPago" type="s:dateTime" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="tipoDocumento">
        <s:restriction base="s:string">
          <s:enumeration value="Cupon" />
          <s:enumeration value="OnLine" />
          <s:enumeration value="PAT" />
          <s:enumeration value="PAC" />
        </s:restriction>
      </s:simpleType>
      <s:simpleType name="moneda">
        <s:restriction base="s:string">
          <s:enumeration value="CLP" />
          <s:enumeration value="USD" />
          <s:enumeration value="UF" />
        </s:restriction>
      </s:simpleType>
      <s:element name="ConciliarPagoResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="Resultado" type="tns:resultadoConciliacion" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="resultadoConciliacion">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="NuevoEstado" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="DetalleEstado" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="NumeroFactura" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" form="unqualified" name="CuentaControl" type="s:string" />
        </s:sequence>
      </s:complexType>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="ConciliarPagoSoapIn">
    <wsdl:part name="parameters" element="tns:ConciliarPago" />
  </wsdl:message>
  <wsdl:message name="ConciliarPagoSoapOut">
    <wsdl:part name="parameters" element="tns:ConciliarPagoResponse" />
  </wsdl:message>
  <wsdl:portType name="ConectorConciliadorWSSoap">
    <wsdl:operation name="ConciliarPago">
      <wsdl:input message="tns:ConciliarPagoSoapIn" />
      <wsdl:output message="tns:ConciliarPagoSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="ConectorConciliadorWSSoap" type="tns:ConectorConciliadorWSSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="ConciliarPago">
      <soap:operation soapAction="ConciliarPago" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="ConectorConciliadorWSSoap12" type="tns:ConectorConciliadorWSSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="ConciliarPago">
      <soap12:operation soapAction="ConciliarPago" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="ConectorConciliadorWS">
    <wsdl:port name="ConectorConciliadorWSSoap" binding="tns:ConectorConciliadorWSSoap">
      <soap:address location="http://mlclwsdevqa01/Cobranza/CuponDePago/ConectorConciliadorWS.asmx" />
    </wsdl:port>
    <wsdl:port name="ConectorConciliadorWSSoap12" binding="tns:ConectorConciliadorWSSoap12">
      <soap12:address location="http://mlclwsdevqa01/Cobranza/CuponDePago/ConectorConciliadorWS.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>