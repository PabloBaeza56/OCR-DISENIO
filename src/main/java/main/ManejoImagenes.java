package main;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ManejoImagenes {

    Set<CoordenadasRojas> coordenadasSet;
    
    public ManejoImagenes(){

        this.coordenadasSet = new HashSet<>();
    }

    public void EncontrarContornos(String ArchivoVerificar, String ArchivoSalida) {
        nu.pattern.OpenCV.loadShared();
        Mat src = Imgcodecs.imread(ArchivoVerificar);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);

            int margin = 10;
            int x = Math.max(rect.x - margin, 0);
            int y = Math.max(rect.y - margin, 0);
            int width = Math.min(rect.width + 2 * margin, src.cols() - x);
            int height = Math.min(rect.height + 2 * margin, src.rows() - y);
            
            Rect expandedRect = new Rect(x, y, width, height);

            if (expandedRect.width > 500 && expandedRect.height > 500 && expandedRect.width < 1500 && expandedRect.height < 1500) {
                Imgproc.rectangle(src, expandedRect.tl(), expandedRect.br(), new Scalar(0, 0, 255), 2);

                Punto esquinaSupIzq = new Punto(expandedRect.x, expandedRect.y);
                Punto esquinaSupDer = new Punto((expandedRect.x + expandedRect.width), expandedRect.y );
                Punto esquinaInfIzq = new Punto(expandedRect.x, (expandedRect.y + expandedRect.height));
                Punto esquinaInfDer = new Punto((expandedRect.x + expandedRect.width), (expandedRect.y + expandedRect.height));

                CoordenadasRojas coordenadas = new CoordenadasRojas(esquinaSupIzq, esquinaSupDer, esquinaInfIzq, esquinaInfDer);

                boolean existe = false;
                for (CoordenadasRojas c : coordenadasSet) {
                    if (c.equals(coordenadas)) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    coordenadasSet.add(coordenadas);

                }
            }
        }
        Imgcodecs.imwrite(ArchivoSalida, src);
    }

    public int detectarRostros(String cadenaRuta) throws ExcepcionesPropias {
        nu.pattern.OpenCV.loadShared();
        int ContadorRostrosDetectados = 0;

        Mat image = Imgcodecs.imread(cadenaRuta);

        CascadeClassifier faceDetector = new CascadeClassifier();
        //El archivo se encuentra en el paquete actual, adecue su path segun su computadora
        faceDetector.load("C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main\\haarcascade_frontalface_default.xml");

        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImage, grayImage);

        MatOfRect faceDetections = new MatOfRect();
       
        faceDetector.detectMultiScale(grayImage, faceDetections, 1.1, 3, 0, new Size(75, 75), new Size(image.width(), image.height()));
        // Dibuja un rectángulo alrededor de cada cara detectada
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 255, 0), 2);
            ContadorRostrosDetectados++;
        }

        if (faceDetections.toArray().length < 2){
            throw new ExcepcionesPropias("No se encontraron suficientes caras");
        }

        Imgcodecs.imwrite(cadenaRuta, image);
        return ContadorRostrosDetectados;
    }

    //@LEGACY
    public int PDFtoPNGConverter(String pdfFilePath, String outputDir) throws IOException {
        int ContadorPaginasGeneradas = 0;
        try (PDDocument document = Loader.loadPDF(new File(pdfFilePath))) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                BufferedImage image = renderer.renderImageWithDPI(pageIndex, 300);

                String outputFileName = outputDir + "\\page_" + (pageIndex + 1) + ".png";
                File outputFile = new File(outputFileName);
                ImageIO.write(image, "png", outputFile);
                ContadorPaginasGeneradas++;
            }

            System.out.println("¡Conversión de PDF a PNG completada!");

        } catch (IOException e) {}
        return ContadorPaginasGeneradas;
    }

    public void DividirImagenesPorContorno(String cadenaRuta) {
        nu.pattern.OpenCV.loadShared();
        Mat src = Imgcodecs.imread(cadenaRuta);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        List<Mat> croppedImages = new ArrayList<>();
        int i = 0;
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            
            if (rect.width > 500 && rect.height > 500 && rect.width < 2000 && rect.height < 2000) {
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
                    Imgcodecs.imwrite(cadenaRuta + "XXXX" + i + ".png", cropped);
                    i++;
                }
            }
        }
    }

    public void RecortarImagen(String inputImagePath, CoordenadasRojas coordenadas, String outputImagePath) {

        double topLeftX = coordenadas.getEsquinaSuperiorIzquierda().getX();
        double topLeftY = coordenadas.getEsquinaSuperiorIzquierda().getY();
        double bottomRightX = coordenadas.getEsquinaInferiorDerecha().getX();
        double bottomRightY = coordenadas.getEsquinaInferiorDerecha().getY();

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            int x = (int) topLeftX;
            int y = (int) topLeftY;
            int width = (int) (bottomRightX - topLeftX);
            int height = (int) (bottomRightY - topLeftY);

            ImagePlus imagePlus = new ImagePlus("", inputImage);

            ImageProcessor imageProcessor = imagePlus.getProcessor();

            imageProcessor.setRoi(x, y, width, height);
            ImageProcessor croppedImageProcessor = imageProcessor.crop();
            BufferedImage croppedImage = croppedImageProcessor.getBufferedImage();

            File outputImageFile = new File(outputImagePath);
            ImageIO.write(croppedImage, "png", outputImageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
