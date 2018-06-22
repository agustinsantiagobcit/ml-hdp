package cl.metlife.hdp.botonpago.genericxml;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Created by Ivan on 28-08-2014.
 */
public class TransformacionXML {

    public String Transformar(String xmlEntrada, String plantilla) throws TransformerException, FileNotFoundException {
        String retorno = "";

        Source xmlEntradaSource = new StreamSource(new StringReader(xmlEntrada));
        Writer outputWriter = new StringWriter();
        Result outputResult = new StreamResult(outputWriter);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer tr = tf.newTransformer(new StreamSource(new StringReader(plantilla)));

        tr.transform(xmlEntradaSource,outputResult);

        retorno = outputResult.toString();

        return retorno;
    }
}
