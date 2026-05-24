package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Pago {
    private int monto;

    public Pago(int monto) {
        this.monto = monto;
    }

    public int getMonto() {
        return monto;
    }

    public static class Auxiliar extends Modelo.Viaje.Tripulante {

        private List<Modelo.Viaje> viajes;

        public Auxiliar(IdPersona id, Nombre nom, Direccion dir){
            super(id, nom, dir);
            this.viajes = new ArrayList<Modelo.Viaje>();
        }

        public void addViaje(Modelo.Viaje viaje) {
            this.viajes = new ArrayList<>();
        }

        public int getNroViaje() {
            return this.viajes.size();
        }
    }
}
