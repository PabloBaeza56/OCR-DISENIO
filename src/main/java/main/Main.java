package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException, ExcepcionesPropias {
        //El paquete se debe llamar analizarINE
        //Las imagenes de ejemplo se encuentran en la carpeta actual
        //Modifique las paths segun la ubicacion de la imagen en su equipo de computo
        //Modifique las paths del detector de caras y ocr (El archi se encuentra en la carpeta)
        
       //Primer Proceso
       //Usar imagen primer proceso
        ManejoImagenes jj = new ManejoImagenes();
        String pathImagenOriginal = "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivo.png";
        String pathSalidaDivisionContornos = "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\definitivoSALIDA.png";
       
       
        jj.EncontrarContornos(pathImagenOriginal, pathSalidaDivisionContornos);
        jj.DividirImagenesPorContorno(pathSalidaDivisionContornos);
        
        List<CoordenadasRojas> listaCoordenadas = new ArrayList<>(jj.coordenadasSet);
        
        for (int i = 0; i < listaCoordenadas.size(); i++){
            try{
            jj.detectarRostros(pathSalidaDivisionContornos.substring(0, pathSalidaDivisionContornos.length() - 4)+ "_recortada_" + i + ".png");
            jj.RecortarImagen(pathImagenOriginal,listaCoordenadas.get(i), "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\salida.png");
            } catch (ExcepcionesPropias e){}
        }
        
        /*
        Debido a cuestiones de calidad de imagen, la imagen recortada en el proceso
        de arriba, no la detecta adecuadamnete el proceso de abajo, por lo tanto se utilizara otra imagen
        que funciona adecuadamente, la reduccion de la calidad se debe al proceso de poner las 2 caras de la INE
        en el documento.
        */
        
        //Segundo Proceso
        //Usar imagen segundo proceso
        OCR conti = new OCR();
        String salida = (conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png")); 
        PersonaINE ciudadano = new PersonaINE();
        ManejoCadenas manejador = new ManejoCadenas(salida, ciudadano);
        manejador.GuardarSubcadenasConMasDeUnDigito(manejador.LimpiarCadenaValoresBasura());
        manejador.VerificarINE();
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