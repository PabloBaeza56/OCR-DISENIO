package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ManejoCadenas {
    LinkedList<String> arr;
    ArrayList<String> datosVitales;
    String cadenaCURP;
    String cadenaClaveElector;
    ClaveDeElector elector;
    
    public ManejoCadenas(){
        this.arr = new LinkedList();
        this.datosVitales = new ArrayList();
        this.cadenaCURP = "";
        this.cadenaClaveElector = "";
        
    }
    
    public String LimpiarCadenaValoresBasura(String cadenaALimpiar){
        String regex = "[^A-Z0-9/\\s]";
        String cadenaLimpia = cadenaALimpiar.replaceAll(regex, "");
        return cadenaLimpia;
    }
    
    public void GuardarSubcadenasConMasDeUnDigito(String nuevaCadena){
        String[] lineas = dividirPorSaltosDeLinea(nuevaCadena);
        for (String linea : lineas) {
            if (linea.length() > 1){
                arr.add(linea);
            }
        }
    }
    
    public void GuardarCadenasVitales(){
        for (String elemento : this.arr){
            if (elemento.length() == 18){
                datosVitales.add(elemento);
            }
        }
    }
    
    public void BuscarClaveElector(){
        if (datosVitales.get(0).matches("[a-zA-Z]{6}.*")){
            this.cadenaClaveElector = datosVitales.get(0);
            this.elector = new ClaveDeElector(this.cadenaClaveElector);
            this.cadenaCURP = datosVitales.get(1);
        } else {
            this.cadenaClaveElector = datosVitales.get(1);
            this.elector = new ClaveDeElector(this.cadenaClaveElector);
            this.cadenaCURP = datosVitales.get(0);
        }
        arr.remove(datosVitales.get(0));
        arr.remove(datosVitales.get(1));
    }
    
    public void EliminarElementosBasura(){
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
    }
    
    public void EncontrarElementosValiosos(){
        Iterator<String> iterator = arr.iterator();
        while (iterator.hasNext()) {
            String elemento = iterator.next();

            

            if (elemento.contains(String.valueOf(elector.getPrimeraLetraPrimerApellido())) && elemento.contains(String.valueOf(elector.getSegundaLetraPrimerApellido()))) {
                System.out.println("Primer Apellido " + elemento);
                iterator.remove();    
            } 

            if (elemento.contains(String.valueOf(elector.getPrimeraLetraSegundoApellido())) && elemento.contains(String.valueOf(elector.getSegundaLetraSegundoApellido()))) {
                System.out.println("Segundo Apellido " + elemento);
                iterator.remove();
            }

            if (elemento.contains(String.valueOf(elector.getPrimeraLetraNombre())) && elemento.contains(String.valueOf(elector.getSegundaLetraNombre()))) {
                System.out.println("Nombre " + elemento);
                iterator.remove(); 
            }
        }
    }
    
    public String[] dividirPorSaltosDeLinea(String cadena) {
        return cadena.split("\\s+");
    }
    
    public static boolean verificarCuatroNumeros(String subcadena) {

        String regex = ".*\\b\\d{4}\\b.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(subcadena);
        return matcher.matches();
    }

    
    
}
