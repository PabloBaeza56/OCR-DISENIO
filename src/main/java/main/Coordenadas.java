package main;

import java.text.DecimalFormat;

public class Coordenadas {
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
    
    // Formateador para redondear las coordenadas a dos decimales
    private static final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordenadas other = (Coordenadas) obj;
        return df.format(esquinaSuperiorIzquierda.getX()).equals(df.format(other.esquinaSuperiorIzquierda.getX())) &&
               df.format(esquinaSuperiorIzquierda.getY()).equals(df.format(other.esquinaSuperiorIzquierda.getY())) &&
               df.format(esquinaSuperiorDerecha.getX()).equals(df.format(other.esquinaSuperiorDerecha.getX())) &&
               df.format(esquinaSuperiorDerecha.getY()).equals(df.format(other.esquinaSuperiorDerecha.getY())) &&
               df.format(esquinaInferiorIzquierda.getX()).equals(df.format(other.esquinaInferiorIzquierda.getX())) &&
               df.format(esquinaInferiorIzquierda.getY()).equals(df.format(other.esquinaInferiorIzquierda.getY())) &&
               df.format(esquinaInferiorDerecha.getX()).equals(df.format(other.esquinaInferiorDerecha.getX())) &&
               df.format(esquinaInferiorDerecha.getY()).equals(df.format(other.esquinaInferiorDerecha.getY()));
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