
package main;

public class CoordenadasVerdes extends Coordenadas {
    // Constructor
    public CoordenadasVerdes(float esquinaSuperiorIzquierda, float esquinaSuperiorDerecha, float esquinaInferiorIzquierda, float esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }

    // Método para imprimir las coordenadas verdes
    @Override
    public String toString() {
        return "Coordenadas Verdes: " + super.toString();
    }
}