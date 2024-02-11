package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OCR {
    private Tesseract tesseract;
    private String tessdataPath = "C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main";
    
    protected OCR(){
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath(tessdataPath);
        System.setProperty("TESSDATA_PREFIX", tessdataPath);
        this.tesseract.setLanguage("spa");
    }
    
    public String AnalizarImagen(String URLimagen) throws TesseractException{
        String text = "";
        try {
            text = tesseract.doOCR(new File(URLimagen));
        } catch (TesseractException e) {
            System.err.println("Error al realizar OCR: " + e.getMessage());
        }
        return text;   
    }
    
    //MUCHO MEJOR
    public String MetodoAlternativo(String URLimagen) throws TesseractException{
        String tessdataPath = "C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main";
        System.setProperty("TESSDATA_PREFIX", tessdataPath);
        
        nu.pattern.OpenCV.loadShared();

        // Cargar la imagen
        Mat image = Imgcodecs.imread("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png");

        // Convertir la imagen a escala de grises
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Crear un objeto Tesseract
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessdataPath);
        tesseract.setLanguage("spa");

        // Realizar OCR en la imagen
        String result = doOCRWithBufferedImage("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png", tesseract);
        //System.out.println("Texto detectado: " + result);
        
        String regex = "[^A-Z0-9\\s]"; //regex para el fente
            //String regex = "[^A-Z<]+"; //regex para el reverso
        String nuevaCadena = result.replaceAll(regex, "");
        String textoSinSaltosDeLinea = nuevaCadena.replaceAll("\\n", "");
        String cadenaf = textoSinSaltosDeLinea.trim();
        String cadena = cadenaf.replaceAll("  ", " ");
        String cadenag = cadena.replaceAll(" ", "-");
        String cadenax = cadenag.replaceAll("--", "-");
        
        

        // Imprimir el resultado
        System.out.println("Cadena original: " + cadenax);
        return cadenax;
    }
    private static String doOCRWithBufferedImage(String imagePath, ITesseract tesseract) throws TesseractException {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            return tesseract.doOCR(bufferedImage);
        } catch (IOException e) {
        }
        return "";
    }
    
}
      /*
            OCR osi = new OCR();
            String text = osi.AnalizarImagen("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png");
            
            String regex = "[^A-Z0-9/\\s]"; //regex para el fente
            //String regex = "[^A-Z<]+"; //regex para el reverso
            String nuevaCadena = text.replaceAll(regex, "");
            

            System.out.println(nuevaCadena);
*/