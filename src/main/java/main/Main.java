package main;

import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        /*
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

        */
       
        ManejoImagenes jj = new ManejoImagenes();
        int ContadorPaginas = jj.PDFtoPNGConverter("C:\\Users\\pablo\\OneDrive\\Escritorio\\original.pdf", "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG");

        for (int i = 1; i <= ContadorPaginas; i++){
            jj.EncontrarContornos("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\page_"+i+".png");
            jj.DividirImagenesPorContorno("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\page_"+i+".png");
            
        }
        
        

        //jj.DetectarRostros("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\page_1.pngXXXX1.jpg");
        /*
        OCR conti = new OCR();
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\ArchivoPrueba.png"));
        PersonaINE ciudadano = new PersonaINE();
        ManejoCadenas manejador = new ManejoCadenas(salida, ciudadano);
        manejador.GuardarSubcadenasConMasDeUnDigito(manejador.LimpiarCadenaValoresBasura());
        manejador.EliminarElementosBasura();
        System.out.println(salida);
        */
        /*
        manejador.BuscarCURPyClaveElector();
        manejador.EncontrarClaveElector();
        manejador.EncontrarNombres();
        manejador.EncontrarEntidadFederativa();
        manejador.EncontrarSexo();
        manejador.EncontrarFechaNacimiento();
        manejador.EncontrarDomicilio();
        System.out.println(ciudadano);
        System.out.println("Cadenas no analizadas " + manejador.DevolverElementosNoIdentificados());
        */
        
        
    }
}