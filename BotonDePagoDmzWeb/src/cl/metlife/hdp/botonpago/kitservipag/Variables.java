/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;

/**
 *
 * @author Ggonzalez
 */
public class Variables {

    private static final String RUTA_ARCHIVO = "C:\\servipag\\colectivos\\conf.txt";
    public static String rutaArchivoConfNodos;
    public static String nodo_XML1;
    public static String nodo_XML2;
    public static String nodo_XML4;
    public static String nombre_Archivo_Log_XML1;
    public static String nombre_Archivo_Log_XML2;
    public static String nombre_Archivo_Log_XML4;
    public static String rutaLlavePrivada;
    public static String rutaLlavePublica;
    public static String servipag;
    public static String paginaError;
    public static String rutaLog;



    public void CargarVariables(String configPath){
        //Archivo arch = new Archivo(RUTA_ARCHIVO);
        Archivo arch = new Archivo(configPath);
        arch.cargarArchivo();
        rutaArchivoConfNodos = arch.getParametros("rutaArchivoConfNodos");
        nodo_XML1 = arch.getParametros("Nodo_XML1");
        nodo_XML2 = arch.getParametros("Nodo_XML2");
        nodo_XML4 = arch.getParametros("Nodo_XML4");
        nombre_Archivo_Log_XML1 = arch.getParametros("Nombre_Archivo_Log_XML1");
        nombre_Archivo_Log_XML2 = arch.getParametros("Nombre_Archivo_Log_XML2");
        nombre_Archivo_Log_XML4 = arch.getParametros("Nombre_Archivo_Log_XML4");
        rutaLlavePrivada = arch.getParametros("RutaLlavePrivada");
        rutaLlavePublica = arch.getParametros("RutaLlavePublica");
        servipag = arch.getParametros("Servipag");
        paginaError = arch.getParametros("PaginaError");
        rutaLog = arch.getParametros("RutaLog");
        
    }
}
