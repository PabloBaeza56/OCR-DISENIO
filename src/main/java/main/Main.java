package main;

import java.io.IOException;

import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
       
        ManejoImagenes jj = new ManejoImagenes();
       
       
        jj.EncontrarContornos("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivo.png", "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.png");
       // 
       jj.DividirImagenesPorContorno("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.png");
       
        jj.detectarRostros("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.pngXXXX0.png");
        //

        CoordenadasRojas salidax = jj.VerificarContencion();
        System.out.println(salidax);
        
        //System.out.println(jj.coordenadasSet);          
        //jj.RecortarImagen("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivo.png");
        
        
        
        
        
        //OCR conti = new OCR();
        //String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\salida.png"));
        //System.out.println(salida);
        /*
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