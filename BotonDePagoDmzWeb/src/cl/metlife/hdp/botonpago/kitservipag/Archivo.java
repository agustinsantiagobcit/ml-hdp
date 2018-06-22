/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Ggonzalez
 */
public class Archivo {
    
    private Properties parametros;
    private String rutaArchivo;
    
    public Archivo(String unaRuta){
        this.setRutaArchivo(unaRuta);
    }
    
    public void setRutaArchivo(String unaRuta){
        this.rutaArchivo = unaRuta;
    }
    
    public String getRutaArchivo(){
        return this.rutaArchivo;
    }
    
    public Properties getProperties(){
        return this.parametros;
    }
    
    public String getParametros(String unParametro){
        return this.getProperties().getProperty(unParametro);
    }
    
    public void cargarArchivo(){
    this.parametros = new Properties();
        try
        {
            this.getProperties().load(new FileInputStream(this.getRutaArchivo()));
        }
        catch(FileNotFoundException _e){
            System.out.println("No se pudo encontrar el Archivo");
            _e.printStackTrace();
        }catch(IOException e){
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
    }
    
    
}
