package main;

import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
        OCR conti = new OCR();
 
        
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png"));
        PersonaINE ciudadano = new PersonaINE();
        ManejoCadenas manejador = new ManejoCadenas(salida, ciudadano);
        manejador.GuardarSubcadenasConMasDeUnDigito(manejador.LimpiarCadenaValoresBasura());
        manejador.EliminarElementosBasura();
        
        manejador.BuscarCURPyClaveElector();
        manejador.EncontrarClaveElector();
        manejador.EncontrarNombres();
        manejador.EncontrarEntidadFederativa();
        manejador.EncontrarSexo();
        manejador.EncontrarFechaNacimiento();
        manejador.EncontrarDomicilio();
        System.out.println(ciudadano);
        System.out.println("Cadenas no analizadas " + manejador.DevolverElementosNoIdentificados());
        
        
        
        
        
    }
}