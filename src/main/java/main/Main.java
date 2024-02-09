package main;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        String tessdataPath = "C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main";
        tesseract.setDatapath(tessdataPath);
        System.setProperty("TESSDATA_PREFIX", tessdataPath);

        try {
            tesseract.setLanguage("spa");
            String text = tesseract.doOCR(new File("C:\\Users\\pablo\\OneDrive\\Escritorio\\OIP.jpeg"));
            System.out.println("Texto reconocido:");
            System.out.println(text);
        } catch (TesseractException e) {
            System.err.println("Error al realizar OCR: " + e.getMessage());
        }
    }
}
