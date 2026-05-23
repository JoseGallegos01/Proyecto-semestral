package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Conductor extends  Tripulante {

    private List<Viaje> viajes;

    public Conductor(IdPersona id, Nombre nom, Direccion dir) {
        super(id, nom, dir);
        this.viajes = new ArrayList<>();
    }

    public void addViaje(Viaje viaje) {
        this.viajes.add(viaje);
    }

    public int getNroVIaje() {
        return this.viajes.size();
    }
}
