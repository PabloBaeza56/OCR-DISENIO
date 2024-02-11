package main;

import java.io.IOException;
import java.util.ArrayList;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
        OCR conti = new OCR();
 
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png"));
        String regex = "[^A-Z0-9/\\s]"; //regex para el fente
        String nuevaCadena = salida.replaceAll(regex, "");
        System.out.println(nuevaCadena);
       
        String[] lineas = dividirPorSaltosDeLinea(nuevaCadena);
        ArrayList<String> arr = new ArrayList();
        
        // Imprimir cada línea
        for (String linea : lineas) {
            if (linea.length() > 1){
                 System.out.println(linea + "  " + linea.length());
                 arr.add(linea);
            }
        }
        
        ArrayList<String> datosVitales = new ArrayList();
        for (String elemento : arr){
            if (elemento.length() == 18){
                System.out.println(elemento);
                datosVitales.add(elemento);
            }
        }
        
        String cadenaOficial = "";
        if (datosVitales.get(0).matches("[a-zA-Z]{6}.*")){
            cadenaOficial = datosVitales.get(0);
        } else {
            cadenaOficial = datosVitales.get(1);
        }
        
        //Cuando es clave de elector
        String datos = cadenaOficial;
        char[] caracteres = new char[15];

        for (int i = 0; i < 15; i++) {
            caracteres[i] = datos.charAt(i);
        }
        System.out.println(cadenaOficial);
        char primeraLetraPrimerApellido = caracteres[0];
        char segundaLetraPrimerApellido = caracteres[1];
        char primeraLetraSegundoApellido = caracteres[2];
        char segundaLetraSegundoApellido = caracteres[3];
        char primeraLetraNombre = caracteres[4];
        char segundaLetraNombre = caracteres[5];
        char anioPrimerDigito = caracteres[6];
        char anioSegundoDigito = caracteres[7];
        char mesPrimerDigito = caracteres[8];
        char mesSegundoDigito = caracteres[9];
        char diaPrimerDigito = caracteres[10];
        char diaSegundoDigito = caracteres[11];
        char entidadFederativaPrimerDigito = caracteres[12];
        char entidadFederativaSegundoDigito = caracteres[13];
        char genero = caracteres[14];
        
        //1er y 2º dígito: Dos consonante iniciales del primer apellido
        //3º y 4º: Dos consonantes iniciales del segundo apellido
        //5º y 6º: Dos consonantes iniciales del nombre del elector
        //7º, 8º, 9º, 10º, 11º y 12º: Fecha de nacimiento (dos dígitos para el año, dos dígitos para el mes, dos dígitos para el día)
        //13º y 14º: Número de la entidad federativa de nacimiento
        
        
        
         
    }
    
     public static String[] dividirPorSaltosDeLinea(String cadena) {
         return cadena.split("\\s+");
    }
}