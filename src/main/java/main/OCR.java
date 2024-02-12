package main;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OCR {
    private Tesseract tesseract;
    private String tessdataPath = "C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main";
    
    protected OCR(){
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath(tessdataPath);
        System.setProperty("TESSDATA_PREFIX", tessdataPath);
        this.tesseract.setLanguage("spa");
    }
    
    public String MetodoAlternativo(String URLimagen) throws TesseractException{
        
        nu.pattern.OpenCV.loadShared();
        String result = doOCRWithBufferedImage(URLimagen);
        return result;
    }
    
    private String doOCRWithBufferedImage(String imagePath) throws TesseractException {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            return tesseract.doOCR(bufferedImage);
        } catch (IOException e) {
        }
        return "";
    }

}

