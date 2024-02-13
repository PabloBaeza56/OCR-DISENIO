package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
       
        ManejoImagenes jj = new ManejoImagenes();
       
       
        jj.EncontrarContornos("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivo.png", "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.png");
        jj.DividirImagenesPorContorno("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.png");
        jj.detectarRostros("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.pngXXXX0.png");
        List<CoordenadasRojas> listaCoordenadas = new ArrayList<>(jj.coordenadasSet);
        jj.RecortarImagen("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivo.png",listaCoordenadas.get(0));
        
        
        OCR conti = new OCR();
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png"));
        System.out.println(salida);
        
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