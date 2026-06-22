package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utilidades.*;

public class Auxiliar extends Tripulante implements Serializable {

    private List<Viaje> viajes;

    public Auxiliar(IdPersona id, Nombre nom, Direccion dir){
        super(id, nom, dir);
        this.viajes = new ArrayList<Viaje>();
    }

    public void addViaje(Viaje viaje) {
        this.viajes = new ArrayList<>();
    }

    @Override
    public int getNroViajes() {
        return viajes.size();
    }

    public int getNroViaje() {
        return this.viajes.size();
    }
}