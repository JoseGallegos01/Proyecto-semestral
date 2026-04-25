public class Pasaporte implements IdPersona {
    //Cristobal Jeldres
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
        Pasaporte p = (Pasaporte) otro;

        return numero.equals(p.numero) &&
                nacionalidad.equals(p.nacionalidad);
    }
}
