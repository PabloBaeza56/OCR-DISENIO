package main;

public class CoordenadasRojas extends Coordenadas {
    // Constructor
    public CoordenadasRojas(Punto esquinaSuperiorIzquierda, Punto esquinaSuperiorDerecha, Punto esquinaInferiorIzquierda, Punto esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }

    // Método para imprimir las coordenadas rojas
    @Override
    public String toString() {
        return "Coordenadas Rojas: " + super.toString()+ "\n";
    }
}