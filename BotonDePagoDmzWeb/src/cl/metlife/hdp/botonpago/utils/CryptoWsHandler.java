package cl.metlife.hdp.botonpago.utils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.io.pem.PemReader;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Set;


public class CryptoWsHandler implements SOAPHandler<SOAPMessageContext> {

    private static String signQN = "http://metlife.cl/PagoDAWS/sign";
    private static final String CHARSET = "UTF8";
    private static final String ENCRYPTION_ALGORITHM = "RSA";
    private static final String HASH_ENCRYPTION_ALGORITHM = "MD5WithRSA";


    public String generateSign(String dataString, String privateKeyPath)
    {
        String PATH = privateKeyPath;
        PrivateKey privateKey = null;
        String FirmaFinal = "";
        try
        {
            Security.addProvider(new BouncyCastleProvider());
            PEMReader reader = new PEMReader(new InputStreamReader(new FileInputStream(PATH)));
            KeyPair par = (KeyPair) reader.readObject();
            privateKey = par.getPrivate();

            byte[] data = dataString.getBytes(this.CHARSET);
            Signature sig = Signature.getInstance(this.HASH_ENCRYPTION_ALGORITHM);
            sig.initSign(privateKey);
            sig.update(data);
            byte[] firma = sig.sign();
            FirmaFinal = new BASE64Encoder().encode(firma);
            return FirmaFinal;
        }
        catch(Exception _e)
        {
            return _e.getMessage();
        }
    }


    public boolean checkSign(String dataString, String sign, String publicKeyPath) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            byte[] data = dataString.getBytes(this.CHARSET);

            Signature sig = Signature.getInstance(this.HASH_ENCRYPTION_ALGORITHM);


            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            PemReader reader = new PemReader( new FileReader( publicKeyPath ));
            byte[] pubKey = reader.readPemObject(  ).getContent(  );
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( pubKey );
            PublicKey publicKey = keyFactory.generatePublic( publicKeySpec );


            sig.initVerify(publicKey);
            sig.update(data);
            boolean valid = sig.verify(new BASE64Decoder().decodeBuffer(sign));
            return valid;

        } catch (SignatureException e) {
            System.out.println(e);
        } catch (InvalidKeySpecException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (InvalidKeyException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Hubo una excepcion al validar la firma");
        return false;
    }

    public CryptoWsHandler() {
        System.out.print("Inicializando handler");
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if(isOutbound) {
            this.handleOutboundMessage(context);
        }
        else {
            this.handleInboundMessage(context);
        }

        //continue other handler chain
        return true;
    }


    private void handleInboundMessage(SOAPMessageContext context) {

        String sign = this.getSign(context);
        String content = this.getContent(context);
        boolean validSign = this.signIsValid(sign, content);

        if(!validSign) {
            generateSOAPErrMessage(context.getMessage(), "Sign not valid");
        }
    }

    private boolean signIsValid(String sign, String content) {
        String publicKeyPath = "C:\\dawsServicePub.key";
       return this.checkSign(content, sign, publicKeyPath);
    }

    private String getContent(SOAPMessageContext context) {
        try {
            return context.getMessage().getSOAPBody().toString();
        } catch (SOAPException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getSign(SOAPMessageContext context) {
        try {
            SOAPMessage soapMsg = context.getMessage();
            SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
            SOAPHeader soapHeader = soapEnv.getHeader();

            //if no header, add one
            if (soapHeader == null) {
                soapHeader = soapEnv.addHeader();
                //throw exception
                //generateSOAPErrMessage(soapMsg, "No SOAP header.");
                //return "";
            }

            //Get sign from SOAP header
            Iterator it = soapHeader.extractAllHeaderElements();

            //if no header block for next actor found? throw exception
            if (it == null || !it.hasNext()) {
                generateSOAPErrMessage(soapMsg, "No header block for next actor.");
                return "";
            }

            //if no mac address found? throw exception
            Node signNode = (Node) it.next();
            String signValue = (signNode == null) ? null : signNode.getValue();

            if (signValue == null) {
                generateSOAPErrMessage(soapMsg, "No sign in header block.");
                return "";
            }

            return signValue;

        }
        catch (SOAPException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void handleOutboundMessage(SOAPMessageContext context) {
        String privateKeyPath = "C:\\dawsClientPriv.key";
        String content =  this.getContent(context);
        String sign = this.generateSign(content, privateKeyPath);


        try {
            SOAPMessage soapMsg = context.getMessage();
            SOAPHeader soapHeader = soapMsg.getSOAPPart().getEnvelope().getHeader();

            if(soapHeader==null) {
                soapHeader = soapMsg.getSOAPPart().getEnvelope().addHeader();
            }

            QName qname = new QName(signQN, "sign");
            SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

            //soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
            soapHeaderElement.addTextNode(sign);
            soapMsg.saveChanges();

        } catch (SOAPException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {


        return true;
    }

    @Override
    public void close(MessageContext context) {
        //System.out.println("Server : close()......");
    }



    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();


            soapBody.removeContents();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            msg.saveChanges();
            throw new SOAPFaultException(soapFault);
        }
        catch(SOAPException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}