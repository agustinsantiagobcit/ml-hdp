/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.metlife.hdp.botonpago.kitservipag;

import java.io.FileReader;
import org.ini4j.Ini;

/**
 *
 * @author Ggonzalez
 */
public class BuscaNodos {

    public String[][] leer(String seccion) {
        String filename = Variables.rutaArchivoConfNodos;
        try {
            Ini ini = new Ini(new FileReader(filename));
            int largoArreglo = 0;
            
            //determina largo del arreglo
            for (String key : ini.get(seccion).keySet()) {
                if(!ini.get(seccion).fetch(key).equals("")){
                    largoArreglo++;
                }
            }

            //arreglo con largo de las entradas válidas
            String arregloSeccion[][] = new String[largoArreglo][2];
            
            //Recuperar las entradas de las seccion entradaX
            int aux1 = 0;
            
            for (String key : ini.get(seccion).keySet()) {
                if(!ini.get(seccion).fetch(key).equals("")){
                    arregloSeccion[aux1][0] = ini.get(seccion).fetch(key);
                    arregloSeccion[aux1][1] = key;
                    aux1++;
                }
            }
            
           //Ordenar Nodos descendente            
            String tmp[][] = new String[1][2];
            for (int i=0; i<(arregloSeccion.length); i++)
            {
                    for (int j=0; j<(arregloSeccion.length-1); j++)
                    {
                            if(Integer.parseInt(arregloSeccion[j][0]) > Integer.parseInt(arregloSeccion[j+1][0]))
                            {
                                    tmp[0][0]=arregloSeccion[j][0];
                                    tmp[0][1]=arregloSeccion[j][1];
                                    arregloSeccion[j][0]=arregloSeccion[j+1][0];
                                    arregloSeccion[j][1]=arregloSeccion[j+1][1];
                                    arregloSeccion[j+1][0]=tmp[0][0];
                                    arregloSeccion[j+1][1]=tmp[0][1];
                            }
                    }
            }
            return arregloSeccion;
            
        } catch (Exception e) {
           String arregloError[][] = new String[1][2];
           arregloError[0][0] = e.toString();
           arregloError[0][1] = e.getMessage();
           return arregloError;
        }
    }
}
