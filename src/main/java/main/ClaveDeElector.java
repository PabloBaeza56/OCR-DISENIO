package main;

public class ClaveDeElector {
    private char primeraLetraPrimerApellido;
    private char segundaLetraPrimerApellido;
    private char primeraLetraSegundoApellido;
    private char segundaLetraSegundoApellido;
    private char primeraLetraNombre;
    private char segundaLetraNombre;
    private char anioPrimerDigito;
    private char anioSegundoDigito;
    private char mesPrimerDigito;
    private char mesSegundoDigito;
    private char diaPrimerDigito;
    private char diaSegundoDigito;
    private char entidadFederativaPrimerDigito;
    private char entidadFederativaSegundoDigito;
    private char sexo;

    // Constructor
    public ClaveDeElector(String cadenaOficial) {
        
        String datos = cadenaOficial;
        char[] caracteres = new char[15];

        for (int i = 0; i < 15; i++) {
            caracteres[i] = datos.charAt(i);
        }
        
        primeraLetraPrimerApellido = caracteres[0];
        segundaLetraPrimerApellido = caracteres[1];
        primeraLetraSegundoApellido = caracteres[2];
        segundaLetraSegundoApellido = caracteres[3];
        primeraLetraNombre = caracteres[4];
        segundaLetraNombre = caracteres[5];
        anioPrimerDigito = caracteres[6];
        anioSegundoDigito = caracteres[7];
        mesPrimerDigito = caracteres[8];
        mesSegundoDigito = caracteres[9];
        diaPrimerDigito = caracteres[10];
        diaSegundoDigito = caracteres[11];
        entidadFederativaPrimerDigito = caracteres[12];
        entidadFederativaSegundoDigito = caracteres[13];
        sexo = caracteres[14];
    }

    public char getPrimeraLetraPrimerApellido() {
        return primeraLetraPrimerApellido;
    }

    public char getSegundaLetraPrimerApellido() {
        return segundaLetraPrimerApellido;
    }

    public char getPrimeraLetraSegundoApellido() {
        return primeraLetraSegundoApellido;
    }

    public char getSegundaLetraSegundoApellido() {
        return segundaLetraSegundoApellido;
    }

    public char getPrimeraLetraNombre() {
        return primeraLetraNombre;
    }

    public char getSegundaLetraNombre() {
        return segundaLetraNombre;
    }

    public char getAnioPrimerDigito() {
        return anioPrimerDigito;
    }

    public char getAnioSegundoDigito() {
        return anioSegundoDigito;
    }

    public char getMesPrimerDigito() {
        return mesPrimerDigito;
    }

    public char getMesSegundoDigito() {
        return mesSegundoDigito;
    }

    public char getDiaPrimerDigito() {
        return diaPrimerDigito;
    }

    public char getDiaSegundoDigito() {
        return diaSegundoDigito;
    }
 
    public char getEntidadFederativaPrimerDigito() {
        return entidadFederativaPrimerDigito;
    }

    public char getEntidadFederativaSegundoDigito() {
        return entidadFederativaSegundoDigito;
    }

    public char getSexo() {
        return sexo;
    }
    
    

}
