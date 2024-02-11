package main;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ManejoImagenes {
    public void EncontrarContornos(){
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
            if (rect.width > 400 && rect.height > 300 && rect.width < 800 && rect.height < 600) {
                Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(0, 0, 255), 2);
            }
        }

        // Guarda la imagen con los rectángulos dibujados
        Imgcodecs.imwrite("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDA2.png", src);
    }
    
    public void DividirImagenesPorContorno(){
        // Carga la imagen
        nu.pattern.OpenCV.loadShared();
        Mat src = Imgcodecs.imread("C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDA.png");

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

        // Recorta los rectángulos
        List<Mat> croppedImages = new ArrayList<>();
        int i = 0;
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            // Solo recorta los rectángulos que son más grandes que un cierto tamaño
            if (rect.width > 200 && rect.height > 150 && rect.width < 2000 && rect.height < 1750) {
                Mat cropped = new Mat(src, rect);
                boolean isDuplicate = false;
                for (Mat img : croppedImages) {
                    Mat hist1 = new Mat();
                    Mat hist2 = new Mat();
                    Imgproc.calcHist(java.util.Arrays.asList(cropped), new MatOfInt(0), new Mat(), hist1, new MatOfInt(256), new MatOfFloat(0, 256));
                    Imgproc.calcHist(java.util.Arrays.asList(img), new MatOfInt(0), new Mat(), hist2, new MatOfInt(256), new MatOfFloat(0, 256));
                    double res = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
                    if (res > 0.9) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    croppedImages.add(cropped);
                    Imgcodecs.imwrite("C:\\Users\\pablo\\OneDrive\\Escritorio\\" + i + ".jpg", cropped);
                    i++;
                }
            }
        }
    }
    
    
}
