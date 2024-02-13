package main;

public class CoordenadasRojas extends Coordenadas {
    // Constructor
    public CoordenadasRojas(Punto esquinaSuperiorIzquierda, Punto esquinaSuperiorDerecha, Punto esquinaInferiorIzquierda, Punto esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }
    
    public CoordenadasRojas() {
        super( new Punto(0,0),  new Punto(0,0),  new Punto(0,0),  new Punto(0,0));
    }
    
    public void setCoordenadas(CoordenadasRojas rojo) {
        super.setEsquinaSuperiorIzquierda(rojo.getEsquinaSuperiorIzquierda());
        super.setEsquinaSuperiorDerecha(rojo.getEsquinaSuperiorDerecha());
        super.setEsquinaInferiorIzquierda(rojo.getEsquinaInferiorIzquierda());
        super.setEsquinaInferiorDerecha(rojo.getEsquinaInferiorDerecha());
    }

    // Método para imprimir las coordenadas rojas
    @Override
    public String toString() {
        return "Coordenadas Rojas: " + super.toString()+ "\n";
    }
}