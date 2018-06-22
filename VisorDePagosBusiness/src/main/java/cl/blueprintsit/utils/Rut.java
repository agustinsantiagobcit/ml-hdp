package cl.blueprintsit.utils;

import java.text.DecimalFormat;

/**
 * Created by BluePrints Developer on 18-05-2016.
 */
public class Rut {

    private int numero;
    private char dv;

    public Rut(int numero, char digitoVerificador) {
        this.numero = numero;
        this.dv = digitoVerificador;
    }

    public String toString(){
        return (numero+"-"+dv).toUpperCase();
    }

    public static Rut parseRut(String serializedRut) throws WrongRutFormat {
        try {
            String[] rutParts = serializedRut.split("-");
            Integer numRut;
            char dv;
            if(rutParts.length<2){
                numRut = Integer.parseInt(rutParts[0].replace(".","").substring(0,rutParts[0].length()-1));
                dv = rutParts[0].charAt(rutParts[0].charAt(rutParts[0].length()-1));
            }else{
                numRut = Integer.parseInt(rutParts[0].replace(".", ""));
                dv = rutParts[1].charAt(0);
            }


            Rut rut = new Rut(numRut,dv);
            if(rut.isValid())
                return rut;
            else
                throw new WrongRutFormat("Rut " + serializedRut+ " invalido");

        }catch (Exception e) {
            throw new WrongRutFormat("No se puede obtener el rut de " + serializedRut);
        }
    }


    public boolean isValid() {

        int dvT;
        int sum = 0;
        int rut = numero;
        int[] factors = {2, 3, 4, 5, 6, 7, 2, 3};
        for (int factor : factors) {
            int dig = rut % 10;
            rut /= 10;
            sum += dig * factor;
        }

        dvT = (11 - (sum % 11)) % 11;
        if (dv == 'k' || dv == 'K') {
            return dvT == 10;
        } else {
            return dvT == Integer.parseInt(Character.toString(dv));
        }
    }

    private static class WrongRutFormat extends Exception {
        public WrongRutFormat(String s) {
            super(s);
        }
    }


    public static String formatRut(String inputRut) {

        if(inputRut == null || inputRut.trim().isEmpty() || inputRut.trim().length() < 2) {
            return inputRut;
        }

        String rut = inputRut.trim();
        rut = rut.replace("-","");
        rut = rut.replace(".","");

        long num = 0L;

        try {
            num = Long.parseLong(rut.substring(0,rut.length()-1));
        }
        catch (NumberFormatException e) {
            return rut;
        }

        DecimalFormat df = new DecimalFormat("###,###,###,###");

        String fRut = df.format(num);

        fRut = fRut.concat("-");

        fRut = fRut.concat(rut.substring(rut.length()-1));

        return fRut;
    }
}
