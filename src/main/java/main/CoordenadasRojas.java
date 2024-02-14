package main;

public class CoordenadasRojas extends Coordenadas {
    
    public CoordenadasRojas() {
        super( new Punto(0,0),  new Punto(0,0),  new Punto(0,0),  new Punto(0,0));
    }

    public CoordenadasRojas(Punto esquinaSuperiorIzquierda, Punto esquinaSuperiorDerecha, Punto esquinaInferiorIzquierda, Punto esquinaInferiorDerecha) {
        super( esquinaSuperiorIzquierda,  esquinaSuperiorDerecha,  esquinaInferiorIzquierda,  esquinaInferiorDerecha);
    }
    
    public void setCoordenadas(CoordenadasRojas rojo) {
        super.setEsquinaSuperiorIzquierda(rojo.getEsquinaSuperiorIzquierda());
        super.setEsquinaSuperiorDerecha(rojo.getEsquinaSuperiorDerecha());
        super.setEsquinaInferiorIzquierda(rojo.getEsquinaInferiorIzquierda());
        super.setEsquinaInferiorDerecha(rojo.getEsquinaInferiorDerecha());
    }

    @Override
    public String toString() {
        return "Coordenadas Rojas: " + super.toString()+ "\n";
    }
}