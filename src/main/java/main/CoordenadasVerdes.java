
package main;

public class CoordenadasVerdes extends Coordenadas {
    // Constructor
    public CoordenadasVerdes(Punto esquinaSuperiorIzquierda, Punto esquinaSuperiorDerecha, Punto esquinaInferiorIzquierda, Punto esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }

    // Método para imprimir las coordenadas verdes
    @Override
    public String toString() {
        return "Coordenadas Verdes: " + super.toString() + "\n";
    }
}