public class Pasajero extends Persona {
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(IdPersona id, Nombre nombre, Nombre nomContacto, String fonoContacto) {
        super(id, nombre);
        this.nomContacto = nomContacto;
        this.fonoContacto = fonoContacto;
    }

    public Nombre getNomContacto() {return nomContacto;}
    public String getFonoContacto() {return fonoContacto;}

    public void setNomContacto(Nombre nom) {this.nomContacto = nom;}
    public void setFonoContacto(String fono) {this.fonoContacto = fono;}
}