package main;

import java.text.DecimalFormat;

public class Coordenadas {
    private static final DecimalFormat df = new DecimalFormat("#.##");
    private Punto esquinaSuperiorIzquierda;
    private Punto esquinaSuperiorDerecha;
    private Punto esquinaInferiorIzquierda;
    private Punto esquinaInferiorDerecha;

    public Coordenadas(Punto esquinaSuperiorIzquierda, Punto esquinaSuperiorDerecha,
                       Punto esquinaInferiorIzquierda, Punto esquinaInferiorDerecha) {
        this.esquinaSuperiorIzquierda = esquinaSuperiorIzquierda;
        this.esquinaSuperiorDerecha = esquinaSuperiorDerecha;
        this.esquinaInferiorIzquierda = esquinaInferiorIzquierda;
        this.esquinaInferiorDerecha = esquinaInferiorDerecha;
    }

    public Punto getEsquinaSuperiorIzquierda() {
        return esquinaSuperiorIzquierda;
    }

    public void setEsquinaSuperiorIzquierda(Punto esquinaSuperiorIzquierda) {
        this.esquinaSuperiorIzquierda = esquinaSuperiorIzquierda;
    }

    public Punto getEsquinaSuperiorDerecha() {
        return esquinaSuperiorDerecha;
    }

    public void setEsquinaSuperiorDerecha(Punto esquinaSuperiorDerecha) {
        this.esquinaSuperiorDerecha = esquinaSuperiorDerecha;
    }

    public Punto getEsquinaInferiorIzquierda() {
        return esquinaInferiorIzquierda;
    }

    public void setEsquinaInferiorIzquierda(Punto esquinaInferiorIzquierda) {
        this.esquinaInferiorIzquierda = esquinaInferiorIzquierda;
    }

    public Punto getEsquinaInferiorDerecha() {
        return esquinaInferiorDerecha;
    }

    public void setEsquinaInferiorDerecha(Punto esquinaInferiorDerecha) {
        this.esquinaInferiorDerecha = esquinaInferiorDerecha;
    }

    @Override
    public String toString() {
        return "Esquina superior izquierda: " + esquinaSuperiorIzquierda +
               "\nEsquina superior derecha: " + esquinaSuperiorDerecha +
               "\nEsquina inferior izquierda: " + esquinaInferiorIzquierda +
               "\nEsquina inferior derecha: " + esquinaInferiorDerecha;
    }
    
    @Override
   public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Coordenadas other = (Coordenadas) obj;
    double tolerance = 3.0; // Tolerancia de +-3

    return Math.abs(esquinaSuperiorIzquierda.getX() - other.esquinaSuperiorIzquierda.getX()) <= tolerance &&
           Math.abs(esquinaSuperiorIzquierda.getY() - other.esquinaSuperiorIzquierda.getY()) <= tolerance &&
           Math.abs(esquinaSuperiorDerecha.getX() - other.esquinaSuperiorDerecha.getX()) <= tolerance &&
           Math.abs(esquinaSuperiorDerecha.getY() - other.esquinaSuperiorDerecha.getY()) <= tolerance &&
           Math.abs(esquinaInferiorIzquierda.getX() - other.esquinaInferiorIzquierda.getX()) <= tolerance &&
           Math.abs(esquinaInferiorIzquierda.getY() - other.esquinaInferiorIzquierda.getY()) <= tolerance &&
           Math.abs(esquinaInferiorDerecha.getX() - other.esquinaInferiorDerecha.getX()) <= tolerance &&
           Math.abs(esquinaInferiorDerecha.getY() - other.esquinaInferiorDerecha.getY()) <= tolerance;
}

    @Override
    public int hashCode() {
        int result = df.format(esquinaSuperiorIzquierda.getX()).hashCode();
        result = 31 * result + df.format(esquinaSuperiorIzquierda.getY()).hashCode();
        result = 31 * result + df.format(esquinaSuperiorDerecha.getX()).hashCode();
        result = 31 * result + df.format(esquinaSuperiorDerecha.getY()).hashCode();
        result = 31 * result + df.format(esquinaInferiorIzquierda.getX()).hashCode();
        result = 31 * result + df.format(esquinaInferiorIzquierda.getY()).hashCode();
        result = 31 * result + df.format(esquinaInferiorDerecha.getX()).hashCode();
        result = 31 * result + df.format(esquinaInferiorDerecha.getY()).hashCode();
        return result;
    }
    
    
    
}