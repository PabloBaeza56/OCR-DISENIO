package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ManejoCadenas {
    LinkedList<String> arregloElementosOCR;
    ArrayList<String> datosVitales;
    ClaveDeElector elector;
    String cadenaOriginal;
    PersonaINE ciudadano;
    
    public ManejoCadenas(String cadenaOriginal, PersonaINE ciudadano){
        this.arregloElementosOCR = new LinkedList();
        this.datosVitales = new ArrayList();
        this.cadenaOriginal = cadenaOriginal;
        this.ciudadano = ciudadano; 
    }
    
    public String LimpiarCadenaValoresBasura(){
        String regex = "[^A-Z0-9/\\s]";
        String cadenaLimpia = this.cadenaOriginal.replaceAll(regex, "");
        return cadenaLimpia;
    }
    
    public void VerificarINE() throws ExcepcionesPropias{
        Boolean esIne = false;
        Iterator<String> iterator2 = this.arregloElementosOCR.iterator();
        while (iterator2.hasNext()) {
            String elemento = iterator2.next();
           
            if (elemento.contains("INSTITUTO") || elemento.contains("VOTAR") || elemento.contains("ELECTORAL") || elemento.contains("CREDENCIAL") || elemento.contains("CLAVE") || elemento.contains("NACIONAL")) {
                esIne = true;
            }     
        }
        if (!esIne){
            throw new ExcepcionesPropias("NO es una INE");
        }
    }
    
    public void GuardarSubcadenasConMasDeUnDigito(String nuevaCadena){
        String[] lineas = dividirPorSaltosDeLinea(nuevaCadena);
        for (String linea : lineas) {
            if (linea.length() > 1){
                this.arregloElementosOCR.add(linea);
            }
        }
    }
    
    public void BuscarCURPyClaveElector(){
        for (String elemento : this.arregloElementosOCR){
            if (elemento.length() == 18){
                datosVitales.add(elemento);
            }
        }
    }
    
    public void EncontrarClaveElector() throws ExcepcionesPropias{
        if (datosVitales.size() != 2){
            throw new ExcepcionesPropias("La obtencion de datos Vitales es ambigua");
        }
        
        if (datosVitales.get(0).matches("[a-zA-Z]{6}.*")){
            this.elector = new ClaveDeElector(RepararClaveElector(datosVitales.get(0)));
            ciudadano.setCurp(RepararCURP(datosVitales.get(1)));
            ciudadano.setClaveElector(RepararClaveElector(datosVitales.get(0)));
        } else {
            this.elector = new ClaveDeElector(RepararClaveElector(datosVitales.get(1)));
            ciudadano.setCurp(RepararCURP(datosVitales.get(0)));
            ciudadano.setClaveElector(RepararClaveElector(datosVitales.get(1)));
        }
        this.arregloElementosOCR.remove(datosVitales.get(0));
        this.arregloElementosOCR.remove(datosVitales.get(1));
    }
    
    public void EliminarElementosBasura(){
        Iterator<String> iterator2 = this.arregloElementosOCR.iterator();
        while (iterator2.hasNext()) {
            String elemento = iterator2.next();
            if(verificarCuatroNumeros(elemento)){
                iterator2.remove();
            }
            if ((elemento.contains("ELECTORAL") || elemento.contains("CREDENCIAL") || elemento.contains("CLAVE") ||
                elemento.contains("CURP") || elemento.contains("VIGENCIA")  ||
                elemento.contains("SECC") || elemento.contains("VOTAR") || elemento.contains("NOMBRE") ||
                elemento.contains("INSTITUTO") || elemento.contains("NACIONAL") ||
                elemento.contains("FECHA") || elemento.contains("NACIMIENTO") || elemento.contains("SEXO") ||
                elemento.contains("PARA"))) {
                    iterator2.remove();
            }      
        }
    }
    
    public void EncontrarNombres(){
        Iterator<String> iterator = this.arregloElementosOCR.iterator();
        while (iterator.hasNext()) {
            String elemento = iterator.next();
            
            if (elemento.contains(String.valueOf(elector.getPrimeraLetraPrimerApellido())) && elemento.contains(String.valueOf(elector.getSegundaLetraPrimerApellido()))) {
                ciudadano.setPrimerApellido(elemento);
                iterator.remove();    
            } 

            if (elemento.contains(String.valueOf(elector.getPrimeraLetraSegundoApellido())) && elemento.contains(String.valueOf(elector.getSegundaLetraSegundoApellido()))) {
                ciudadano.setSegundoApellido(elemento);
                iterator.remove();
            }

            if (elemento.contains(String.valueOf(elector.getPrimeraLetraNombre())) && elemento.contains(String.valueOf(elector.getSegundaLetraNombre()))) {
                ciudadano.setNombre(elemento);
                iterator.remove(); 
            }
        }
    }
    
    public void EncontrarSexo(){
        ciudadano.setSexo(elector.getSexo());
    }
    
    public void EncontrarEntidadFederativa(){
        ciudadano.setNumeroEntidadFederativa(String.valueOf(elector.getEntidadFederativaPrimerDigito()) + String.valueOf(elector.getEntidadFederativaSegundoDigito()));    
    }
    
    public void EncontrarFechaNacimiento(){

        String cadenaFecha = String.valueOf(elector.getDiaPrimerDigito()) +
              String.valueOf(elector.getDiaSegundoDigito()) +
              "/" +
              String.valueOf(elector.getMesPrimerDigito()) +
              String.valueOf(elector.getMesSegundoDigito()) +
              "/" +
              String.valueOf(elector.getAnioPrimerDigito()) +
              String.valueOf(elector.getAnioSegundoDigito());
        
 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate fecha = LocalDate.parse(cadenaFecha, formatter);
        String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        ciudadano.setFechaNacimiento(fechaFormateada);   
    }
    
    public void EncontrarDomicilio(){
        int posicionDomicilio = 0;
        int posicionEstado = 0;
        int indiceGeneral = 0;
        for (String elemento : this.arregloElementosOCR){
            if (elemento.contains("DOMI")){
                posicionDomicilio = indiceGeneral;
            }
            if (elemento.matches("[A-Z]{3}")){
                posicionEstado = indiceGeneral;
            }
            indiceGeneral++;
        }
     
        if (posicionDomicilio != -1 && posicionEstado != -1) {
            LinkedList<String> elementosEntre = new LinkedList<>(arregloElementosOCR.subList(posicionDomicilio + 1, posicionEstado + 1));
            if (!elementosEntre.isEmpty() && !elementosEntre.getFirst().equals("C")) {
                elementosEntre.set(0, "C");
            }
            this.arregloElementosOCR.removeAll(elementosEntre);
            this.arregloElementosOCR.remove(posicionDomicilio);
            
            ciudadano.setDomicilio(unirElementos(elementosEntre));
        } else {
            ciudadano.setDomicilio("ERROR");
        }
    }
    
    public LinkedList<String> DevolverElementosNoIdentificados(){
        return arregloElementosOCR;
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
    
    public static String RepararCURP(String cadena){

        char[] caracteres = cadena.toCharArray();
        for (int i = 4; i <= 8 && i < caracteres.length; i++) {
            if (caracteres[i] == 'O') { 
                caracteres[i] = '0'; 
            }
        }
        String nuevaCadena = String.valueOf(caracteres);
        return nuevaCadena; 
    }
    
    public static String RepararClaveElector(String cadena){

        char[] caracteres = cadena.toCharArray();
        for (int i = 6; i <=11 && i < caracteres.length; i++) {
            if (caracteres[i] == 'O') {
                caracteres[i] = '0';
            }
        }
        String nuevaCadena = String.valueOf(caracteres);
        return nuevaCadena;
    }
    
    public static String unirElementos(LinkedList<String> lista) {
        StringBuilder sb = new StringBuilder();
        
        for (String elemento : lista) {
            sb.append(" ").append(elemento);
        }
        
        return sb.toString().trim();
    }

    
    
}
