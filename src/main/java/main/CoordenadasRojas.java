package main;

public class CoordenadasRojas extends Coordenadas {
    // Constructor
    public CoordenadasRojas(float esquinaSuperiorIzquierda, float esquinaSuperiorDerecha, float esquinaInferiorIzquierda, float esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }

    // Método para imprimir las coordenadas rojas
    @Override
    public String toString() {
        return "Coordenadas Rojas: " + super.toString();
    }
}