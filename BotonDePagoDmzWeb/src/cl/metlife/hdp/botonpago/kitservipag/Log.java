/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ggonzalez
 */
public class Log {

    public void genera(String data, String path, String nombreLog) {
        try {
            RandomAccessFile miRAFile;
            Date fecha=new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaAc = formato.format(fecha);
            
            SimpleDateFormat formatoNomArchivo = new SimpleDateFormat("yyyyMMdd");
            String fechaNomArchivo = formatoNomArchivo.format(fecha);
            
            String nomArchivo = path+nombreLog+fechaNomArchivo+".Log";
            // Abrimos el fichero de acceso aleatorio
            miRAFile = new RandomAccessFile(nomArchivo, "rw");
            // Nos vamos al final del fichero
            miRAFile.seek(miRAFile.length());
            // Incorporamos la cadena al fichero     
            miRAFile.writeBytes(fechaAc+" : "+data);
            // Cerramos el fichero
            miRAFile.close();
        } catch (Exception _e) {
        }
    }
}