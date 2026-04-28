public class Pasaporte implements IdPersona {
    //Cristobal Jeldres
    private String numero;
    private String nacionalidad;
    //En teoria esta listo

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

    public static Pasaporte of(String numero, String nacionalidad) {
        if (numero == null || numero.equals("")) {
            return null;
        }

        if (nacionalidad == null || nacionalidad.equals("")) {
            return null;
        }

        return new Pasaporte(numero, nacionalidad);
    }
}