import java.util.ArrayList;

public class Pasajero extends Persona {
    private Nombre nomContacto;
    private String fonoContacto;
    private ArrayList<Pasaje> pasajes;

    public Pasajero(IdPersona id, Nombre nombre, Nombre nomContacto, String fonoContacto) {
        super(id, nombre);
        this.nomContacto = nomContacto;
        this.fonoContacto = fonoContacto;
        this.pasajes = new ArrayList<>();
    }

    public Nombre getNomContacto() {return nomContacto;}
    public String getFonoContacto() {return fonoContacto;}

    public void setNomContacto(Nombre nom) {this.nomContacto = nom;}
    public void setFonoContacto(String fono) {this.fonoContacto = fono;}
}