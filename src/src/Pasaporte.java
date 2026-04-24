public class Pasaporte implements IdPersona {
    private String numero;
    private String nacionalidad;
    //hay que terminar el equals y hacer el metodo of

    public Pasaporte(String numero, String nacionalidad) {
        this.numero = numero;
        this.nacionalidad = nacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return numero + " " + nacionalidad;
    }

    @Override
    public boolean equals(Object otro) {

        return false;
    }
}
