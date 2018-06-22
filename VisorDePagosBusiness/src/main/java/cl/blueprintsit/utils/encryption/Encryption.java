package cl.blueprintsit.utils.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by BluePrints Developer on 23-05-2017.
 */
public class Encryption {


    public static final String ALGORYTM = "Blowfish";

    public static byte[] encrypt(String strClearText, String strKey) throws EncryptionException {
        try {
            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), ALGORYTM);
            Cipher cipher = Cipher.getInstance(ALGORYTM);
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            return cipher.doFinal(strClearText.getBytes());
        }catch (Exception e){
            throw  new EncryptionException("error al encriptar",e);
        }
    }

    public static String decrypt(byte[] strEncrypted,String strKey) throws EncryptionException {

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(), ALGORYTM);
            Cipher cipher=Cipher.getInstance(ALGORYTM);
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted=cipher.doFinal(strEncrypted);
            return new String(decrypted);

        }catch (BadPaddingException e){
            throw new EncryptionException("Error al  decitptar");
        }
        catch (Exception e) {
            throw new EncryptionException("Error al  decitptar",e);
        }
    }

    private Encryption(){}


}
