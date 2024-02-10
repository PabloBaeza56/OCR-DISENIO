package main;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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
    
}
      /*
            OCR osi = new OCR();
            String text = osi.AnalizarImagen("C:\\Users\\pablo\\OneDrive\\Escritorio\\img20240209_18444422.png");
            
            String regex = "[^A-Z0-9/\\s]"; //regex para el fente
            //String regex = "[^A-Z<]+"; //regex para el reverso
            String nuevaCadena = text.replaceAll(regex, "");
            

            System.out.println(nuevaCadena);
*/