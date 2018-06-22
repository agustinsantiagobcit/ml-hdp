package cl.qr.cl.qr.pjp;

import java.util.Map;
import java.util.TreeMap;

/**
 * Exception para lanzar desde lógica interna que implica que el proceso de
 * venta en curso ha fallado y se despliegue la página de error generico. En
 * caso de que ocurra esta exception se debe generar una alerta y enviar un
 * correo. Esto de maneja en el exception handler.
 * 
 * @author cristian.junge@nuwit.com
 * 
 */
public class MetlifeException extends RuntimeException {

	private static final long serialVersionUID = -2465955492450739426L;

	Map<String, Object> datosError;

	boolean flagMensajeEsDesplegable;

	public MetlifeException(String message) {
		super(message);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = false;
	}

	public MetlifeException(String message, boolean flagMensajeEsDesplegable) {
		super(message);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = flagMensajeEsDesplegable;
	}

	public MetlifeException(Throwable cause) {
		super(cause);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = false;
	}

	public MetlifeException(Throwable cause, boolean flagMensajeEsDesplegable) {
		super(cause);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = flagMensajeEsDesplegable;
	}

	public MetlifeException(String message, Throwable cause) {
		super(message, cause);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = false;
	}

	public MetlifeException(String message, Throwable cause,
							boolean flagMensajeEsDesplegable) {
		super(message, cause);
		this.datosError = new TreeMap<String, Object>();
		this.flagMensajeEsDesplegable = flagMensajeEsDesplegable;
	}

	public MetlifeException(String message, Throwable cause,
							Map<String, Object> datosError) {
		super(message, cause);
		this.datosError = datosError;
	}

	public Map<String, Object> getDatosError() {
		return datosError;
	}

	public void putDatoError(String key, Object valor) {
		if (valor != null) {
			this.datosError.put(key, valor);
		}

	}

	public boolean isFlagMensajeEsDesplegable() {
		return flagMensajeEsDesplegable;
	}

	public void setFlagMensajeEsDesplegable(boolean flagMensajeEsDesplegable) {
		this.flagMensajeEsDesplegable = flagMensajeEsDesplegable;
	}

}
