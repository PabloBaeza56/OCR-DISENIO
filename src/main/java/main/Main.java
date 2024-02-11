package main;

import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;

public class Main {
    public static void main(String[] args) throws TesseractException, IOException {
        
        OCR conti = new OCR();
 
        System.out.println(conti.MetodoAlternativo("C:\\Users\\pablo\\OneDrive\\Escritorio\\2.jpg"));
        
        
    }
}