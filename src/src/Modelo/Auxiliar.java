package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Auxiliar extends Tripulante {

    private List<Viaje> viajes;

    public Auxiliar(IdPersona id, Nombre nom, Direccion dir){
        super(id, nom, dir);
        this.viajes = new ArrayList<Viaje>();
    }

    public void addViaje(Viaje viaje) {
        this.viajes = new ArrayList<>();
    }

    public int getNroViaje() {
        return this.viajes.size();
    }
}
