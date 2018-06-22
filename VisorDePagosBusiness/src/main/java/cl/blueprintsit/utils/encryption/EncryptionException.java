package cl.blueprintsit.utils.encryption;

/**
 * Created by BluePrints Developer on 23-05-2017.
 */
public class EncryptionException extends Exception {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
