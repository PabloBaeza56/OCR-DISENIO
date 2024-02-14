package main;

public class PersonaINE {

    private String primerApellido;
    private String segundoApellido;
    private String nombre;
    private String domicilio;
    private String claveElector;
    private String curp;
    private String fechaNacimiento;
    private String numeroEntidadFederativa;
    private char sexo;

    public String getNumeroEntidadFederativa() {
        return numeroEntidadFederativa;
    }

    public void setNumeroEntidadFederativa(String numeroEntidadFederativa) {
        this.numeroEntidadFederativa = numeroEntidadFederativa;
    }
    
    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getClaveElector() {
        return claveElector;
    }

    public void setClaveElector(String claveElector) {
        this.claveElector = claveElector;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    // Método toString para imprimir información de la persona
    @Override
    public String toString() {
    return """
           Persona{
           primerApellido='""" + primerApellido + "'\n"
            + " segundoApellido='" + segundoApellido + "'\n"
            + " nombre='" + nombre + "'\n"
            + " domicilio='" + domicilio + "'\n"
            + " claveElector='" + claveElector + "'\n"
            + " curp='" + curp + "'\n"
            + " fechaNacimiento=" + fechaNacimiento + "\n"
            + " sexo=" + sexo + "\n"
            + " entidadFederativa=" + numeroEntidadFederativa + "\n"
            + '}';
}
}
