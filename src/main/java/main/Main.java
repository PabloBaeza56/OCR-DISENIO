package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class Main {
  

    public static void main(String[] args) throws TesseractException, IOException {
        // Carga la imagen
        nu.pattern.OpenCV.loadShared();
        Mat src = Imgcodecs.imread("C:\\Users\\pablo\\OneDrive\\Escritorio\\ArchivoPrueba.png");

    
        // Convierte la imagen a escala de grises
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // Aplica un filtro Gaussiano para suavizar la imagen
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Detecta los bordes usando el algoritmo de Canny
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        // Encuentra los contornos
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        // Dibuja los rectángulos
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            // Solo dibuja los rectángulos que son más grandes que un cierto tamaño
            if (rect.width > 200 && rect.height > 150 && rect.width < 2000 && rect.height < 1750) {
                Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(0, 0, 255), 2);
            }
        }

        // Guarda la imagen con los rectángulos dibujados
        Imgcodecs.imwrite("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDA.png", src);
    }
}