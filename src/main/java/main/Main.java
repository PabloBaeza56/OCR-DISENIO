package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
        OCR conti = new OCR();
 
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png"));
        String regex = "[^A-Z0-9/\\s]"; //regex para el fente
        String nuevaCadena = salida.replaceAll(regex, "");
        //System.out.println(nuevaCadena);
        //Verficar que diga instituto nacional electoral
        //credencial para votar
       
        String[] lineas = dividirPorSaltosDeLinea(nuevaCadena);
        LinkedList<String> arr = new LinkedList();
        
        // Imprimir cada línea de la salida
        for (String linea : lineas) {
            if (linea.length() > 1){
                 //System.out.println(linea + "  " + linea.length());
                 arr.add(linea);
            }
        }
        
        
        //Encontar CURP y CLAVE
        ArrayList<String> datosVitales = new ArrayList();
        for (String elemento : arr){
            if (elemento.length() == 18){
                //System.out.println(elemento);
                datosVitales.add(elemento);
            }
        }
        
        //Verificar cual es la clave de elector
        String cadenaOficial = "";
        if (datosVitales.get(0).matches("[a-zA-Z]{6}.*")){
            cadenaOficial = datosVitales.get(0);
        } else {
            cadenaOficial = datosVitales.get(1);
        }
        arr.remove(datosVitales.get(0));
        arr.remove(datosVitales.get(1));
        
        
        
        //Cuando es clave de elector
        String datos = cadenaOficial;
        char[] caracteres = new char[15];

        for (int i = 0; i < 15; i++) {
            caracteres[i] = datos.charAt(i);
        }
        //System.out.println(cadenaOficial);
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
        
        
            Iterator<String> iterator2 = arr.iterator();
            while (iterator2.hasNext()) {
                String elemento = iterator2.next();
                if(verificarCuatroNumeros(elemento)){
                    iterator2.remove();
                }
                if ((elemento.contains("ELECTORAL") || elemento.contains("CREDENCIAL") || elemento.contains("CLAVE") ||
                elemento.contains("CURP") || elemento.contains("VIGENCIA") || elemento.contains("DOMICILO") ||
                elemento.contains("SECC") || elemento.contains("VOTAR") || elemento.contains("NOMBRE") ||
                elemento.contains("INSTITUTO") || elemento.contains("NACIONAL") || elemento.contains("DOMICILO") ||
                elemento.contains("FECHA") || elemento.contains("NACIMIENTO") || elemento.contains("SEXO") ||
                elemento.contains("PARA"))) {
                    iterator2.remove();
                }

                
            }
            
        
        
        
       Iterator<String> iterator = arr.iterator();
        while (iterator.hasNext()) {
            String elemento = iterator.next();

            

            if (elemento.contains(String.valueOf(primeraLetraPrimerApellido)) && elemento.contains(String.valueOf(segundaLetraPrimerApellido))) {
                System.out.println("Primer Apellido " + elemento);
                iterator.remove();    
            } 

            if (elemento.contains(String.valueOf(primeraLetraSegundoApellido)) && elemento.contains(String.valueOf(segundaLetraSegundoApellido))) {
                System.out.println("Segundo Apellido " + elemento);
                iterator.remove();
            }

            if (elemento.contains(String.valueOf(primeraLetraNombre)) && elemento.contains(String.valueOf(segundaLetraNombre))) {
                System.out.println("Nombre " + elemento);
                iterator.remove(); 
            }
        }
        System.out.println(arr);
        
        //1er y 2º dígito: Dos consonante iniciales del primer apellido
        //3º y 4º: Dos consonantes iniciales del segundo apellido
        //5º y 6º: Dos consonantes iniciales del nombre del elector
        //7º, 8º, 9º, 10º, 11º y 12º: Fecha de nacimiento (dos dígitos para el año, dos dígitos para el mes, dos dígitos para el día)
        //13º y 14º: Número de la entidad federativa de nacimiento
        
        
        
         
    }
    
    public static String[] dividirPorSaltosDeLinea(String cadena) {
         return cadena.split("\\s+");
    }
    public static boolean verificarCuatroNumeros(String subcadena) {
        // Define una expresión regular para buscar exactamente cuatro números en la subcadena
        String regex = ".*\\b\\d{4}\\b.*";

        // Crea un objeto Pattern con la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Crea un objeto Matcher para la subcadena
        Matcher matcher = pattern.matcher(subcadena);

        // Devuelve true si se encuentra una coincidencia, false en caso contrario
        return matcher.matches();
    }
}