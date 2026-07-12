package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utilidades.*;

public class Conductor extends Tripulante implements Serializable {

    private List<Viaje> viajes;

    public Conductor(IdPersona id, Nombre nom, Direccion dir) {
        super(id, nom, dir);
        this.viajes = new ArrayList<>();
    }

    @Override
    public void addViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }

    @Override
    public int getNroViajes() {
        return this.viajes.size();
    }
}