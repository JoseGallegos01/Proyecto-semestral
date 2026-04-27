import java.util.Objects;

public class Persona {

    //Jose Gallegos

    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(IdPersona id, Nombre nombre) {
        this.idPersona = id;
        this.nombreCompleto = nombre;
    }

    public IdPersona getIdPersona() {return idPersona;}
    public Nombre getNombreCompleto() {return nombreCompleto;}
    public String getTelefono() {return telefono;}

    public void setNombreCompleto(Nombre nombreCompleto) {this.nombreCompleto = nombreCompleto;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombreCompleto=" + nombreCompleto +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (otro == null || getClass() != otro.getClass()) return false;
        Persona persona = (Persona) otro;
        return Objects.equals(idPersona, persona.idPersona);
    }
}