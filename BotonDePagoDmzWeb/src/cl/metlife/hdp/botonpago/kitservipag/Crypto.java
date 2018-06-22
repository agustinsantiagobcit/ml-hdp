

package cl.metlife.hdp.botonpago.kitservipag;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.io.pem.PemReader;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


/**
 *
 * @author Ggonzalez
 */
public class Crypto {

    /** The Constant CHARSET. */
    private static final String CHARSET = "UTF8";

    /** The Constant ENCRYPTION_ALGORITHM. */
    private static final String ENCRYPTION_ALGORITHM = "RSA";

    /** The Constant HASH_ENCRIPTION_ALGORITHM. */
    private static final String HASH_ENCRYPTION_ALGORITHM = "MD5WithRSA";


  //Genera Firma con llave privada formato PEM
    public static String GeneraFirma(String datos, String llavePrivada)
    {
        String PATH = llavePrivada;
        PrivateKey privateKey = null;
        String FirmaFinal = "";
        try
        {
            Security.addProvider(new BouncyCastleProvider());
            System.out.println("Ruta llave privada: " + llavePrivada);
            PEMReader reader = new PEMReader(new InputStreamReader(new FileInputStream(PATH)));
            KeyPair par = (KeyPair) reader.readObject();
            privateKey = par.getPrivate();
            
	        byte[] data = datos.getBytes(Crypto.CHARSET);
            Signature sig = Signature.getInstance(Crypto.HASH_ENCRYPTION_ALGORITHM);
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


    public static boolean checkSign(String dataString, String sign, String publicKeyPath) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            byte[] data = dataString.getBytes(Crypto.CHARSET);

            Signature sig = Signature.getInstance(Crypto.HASH_ENCRYPTION_ALGORITHM);


            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            System.out.println("Ruta llave publica: " + publicKeyPath);
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



}
