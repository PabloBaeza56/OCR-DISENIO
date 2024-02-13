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
            // Agrega un margen alrededor del rectángulo encontrado
            int margin = 10;
            int x = Math.max(rect.x - margin, 0);
            int y = Math.max(rect.y - margin, 0);
            int width = Math.min(rect.width + 2 * margin, src.cols() - x);
            int height = Math.min(rect.height + 2 * margin, src.rows() - y);
            Rect expandedRect = new Rect(x, y, width, height);
            // Solo dibuja los rectángulos que son más grandes que un cierto tamaño
            if (expandedRect.width > 500 && expandedRect.height > 500 && expandedRect.width < 1500 && expandedRect.height < 1500) {
                Imgproc.rectangle(src, expandedRect.tl(), expandedRect.br(), new Scalar(0, 0, 255), 2);
                // Imprime las coordenadas de las esquinas
                //System.out.println("Esquina superior izquierda: (" + expandedRect.x + ", " + expandedRect.y + ")");
                //System.out.println("Esquina superior derecha: (" + (expandedRect.x + expandedRect.width) + ", " + expandedRect.y + ")");
                //System.out.println("Esquina inferior izquierda: (" + expandedRect.x + ", " + (expandedRect.y + expandedRect.height) + ")");
                //System.out.println("Esquina inferior derecha: (" + (expandedRect.x + expandedRect.width) + ", " + (expandedRect.y + expandedRect.height) + ")");

                Punto esquinaSupIzq = new Punto(expandedRect.x, expandedRect.y);
                Punto esquinaSupDer = new Punto((expandedRect.x + expandedRect.width), expandedRect.y );
                Punto esquinaInfIzq = new Punto(expandedRect.x, (expandedRect.y + expandedRect.height));
                Punto esquinaInfDer = new Punto((expandedRect.x + expandedRect.width), (expandedRect.y + expandedRect.height));

                CoordenadasRojas coordenadas = new CoordenadasRojas(esquinaSupIzq, esquinaSupDer, esquinaInfIzq, esquinaInfDer);

                // Verifica si ya existe una coordenada similar en el conjunto
                boolean existe = false;
                for (CoordenadasRojas c : coordenadasSet) {
                    if (c.equals(coordenadas)) {
                        existe = true;
                        break;
                    }
                }

            // Si no existe una coordenada similar, agrégala al conjunto
            if (!existe) {
                coordenadasSet.add(coordenadas);
            
            }
            


            }
        }

    // Guarda la imagen con los rectángulos dibujados en un archivo de salida
    Imgcodecs.imwrite(ArchivoSalida, src);
}

    public int detectarRostros(String cadenaRuta) {
    nu.pattern.OpenCV.loadShared();
    int ContadorRostrosDetectados = 0;

    // Lee la imagen
    Mat image = Imgcodecs.imread(cadenaRuta);

    // Carga el clasificador de detección de caras
    CascadeClassifier faceDetector = new CascadeClassifier();
    faceDetector.load("C:\\Users\\pablo\\OneDrive\\Documentos\\NetBeansProjects\\OCR-DISENIO\\src\\main\\java\\main\\haarcascade_frontalface_default.xml");

    // Convierte la imagen a escala de grises
    Mat grayImage = new Mat();
    Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
    Imgproc.equalizeHist(grayImage, grayImage);

    // Detecta caras en la imagen
   MatOfRect faceDetections = new MatOfRect();
   //faceDetector.detectMultiScale(grayImage, faceDetections, 1.1, 3, 0, new Size(50, 50), new Size(image.width(), image.height()));
    faceDetector.detectMultiScale(grayImage, faceDetections, 1.1, 3, 0, new Size(75, 75), new Size(image.width(), image.height()));
    // Dibuja un rectángulo alrededor de cada cara detectada
    for (Rect rect : faceDetections.toArray()) {
        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 255, 0), 2);

        // Imprime las coordenadas de las esquinas del rectángulo
        //System.out.println("Esquina superior izquierda: (" + rect.x + ", " + rect.y + ")");
        //System.out.println("Esquina superior derecha: (" + (rect.x + rect.width) + ", " + rect.y + ")");
        //System.out.println("Esquina inferior izquierda: (" + rect.x + ", " + (rect.y + rect.height) + ")");
        //System.out.println("Esquina inferior derecha: (" + (rect.x + rect.width) + ", " + (rect.y + rect.height) + ")");

        //Punto esquinaSupIzq = new Punto(rect.x, rect.y);
        //Punto esquinaSupDer = new Punto((rect.x + rect.width), rect.y );
        //Punto esquinaInfIzq = new Punto(rect.x, (rect.y + rect.height));
        //Punto esquinaInfDer = new Punto((rect.x + rect.width), (rect.y + rect.height));

        //CoordenadasVerdes coordenadas = new CoordenadasVerdes(esquinaSupIzq, esquinaSupDer, esquinaInfIzq, esquinaInfDer);
        //coordenadasSet.add(coordenadas); 
        ContadorRostrosDetectados++;
    }

    // Muestra el número de caras detectadas
    System.out.println("Número de caras detectadas: " + faceDetections.toArray().length);

    // Guarda la imagen con los rectángulos dibujados
    Imgcodecs.imwrite(cadenaRuta, image);
    return ContadorRostrosDetectados;
}

    
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
        // Carga la imagen
        
        nu.pattern.OpenCV.loadShared();
        Mat src = Imgcodecs.imread(cadenaRuta);

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

    
    
    
    public CoordenadasRojas VerificarContencion() {
    List<CoordenadasRojas> rectangulosRojosContenedores = new ArrayList<>();

    // Recorrer todos los rectángulos rojos
    for (Coordenadas c : this.coordenadasSet) {
        if (c instanceof CoordenadasRojas) {
            CoordenadasRojas rojo = (CoordenadasRojas) c;
            boolean contenido = false;

            // Verificar si el rectángulo rojo contiene algún rectángulo verde
            for (Coordenadas c2 : this.coordenadasSet) {
                if (c2 instanceof CoordenadasVerdes) {
                    CoordenadasVerdes verde = (CoordenadasVerdes) c2;
                    if (estaContenido(rojo, verde)) {
                        contenido = true;
                        break;
                    }
                }
            }

            // Si el rectángulo rojo contiene algún rectángulo verde, se agrega a la lista de rectángulos contenedores
            if (contenido) {
                rectangulosRojosContenedores.add(rojo);
            }
        }
    }

    CoordenadasRojas salida = new CoordenadasRojas();
    if (!rectangulosRojosContenedores.isEmpty()) {
        System.out.println("Los siguientes rectángulos rojos contienen rectángulos verdes:");
        for (CoordenadasRojas rojo : rectangulosRojosContenedores) {
            salida.setCoordenadas(rojo);
        }
    } else {
        System.out.println("Ningún rectángulo rojo contiene rectángulos verdes.");
    }
    return salida;
}
    
    private static boolean estaContenido(CoordenadasRojas rojo, CoordenadasVerdes verde) {
        if (verde == null) {
            return false;
        }
        double x1 = rojo.getEsquinaSuperiorIzquierda().getX();
        double y1 = rojo.getEsquinaSuperiorIzquierda().getY();
        double x2 = rojo.getEsquinaInferiorDerecha().getX();
        double y2 = rojo.getEsquinaInferiorDerecha().getY();
        double vx1 = verde.getEsquinaSuperiorIzquierda().getX();
        double vy1 = verde.getEsquinaSuperiorIzquierda().getY();
        double vx2 = verde.getEsquinaInferiorDerecha().getX();
        double vy2 = verde.getEsquinaInferiorDerecha().getY();

        return vx1 >= x1 && vy1 >= y1 && vx2 <= x2 && vy2 <= y2;
    }
    
    public void RecortarImagen(String inputImagePath, CoordenadasRojas coordenadas) {
    // Ruta de salida para la imagen recortada
    String outputImagePath = "C:\\Users\\pablo\\OneDrive\\Escritorio\\SALIDAPNG\\salida.png";

    // Coordenadas de la esquina superior izquierda y esquina inferior derecha
    double topLeftX = coordenadas.getEsquinaSuperiorIzquierda().getX();
    double topLeftY = coordenadas.getEsquinaSuperiorIzquierda().getY();
    double bottomRightX = coordenadas.getEsquinaInferiorDerecha().getX();
    double bottomRightY = coordenadas.getEsquinaInferiorDerecha().getY();

    try {
        // Lee la imagen de entrada
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        // Calcula las coordenadas y dimensiones del rectángulo de recorte
        int x = (int) topLeftX;
        int y = (int) topLeftY;
        int width = (int) (bottomRightX - topLeftX);
        int height = (int) (bottomRightY - topLeftY);

        // Crea una instancia de ImagePlus desde la imagen de entrada
        ImagePlus imagePlus = new ImagePlus("", inputImage);

        // Obtiene el procesador de la imagen
        ImageProcessor imageProcessor = imagePlus.getProcessor();

        // Realiza el recorte
        imageProcessor.setRoi(x, y, width, height);
        ImageProcessor croppedImageProcessor = imageProcessor.crop();
        BufferedImage croppedImage = croppedImageProcessor.getBufferedImage();

        // Guarda la imagen recortada
        File outputImageFile = new File(outputImagePath);
        ImageIO.write(croppedImage, "png", outputImageFile);

        System.out.println("Imagen recortada guardada en: " + outputImagePath);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
