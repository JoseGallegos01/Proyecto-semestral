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

    public static class Auxiliar extends modelo.Viaje.Tripulante {

        private List<modelo.Viaje> viajes;

        public Auxiliar(IdPersona id, Nombre nom, Direccion dir){
            super(id, nom, dir);
            this.viajes = new ArrayList<modelo.Viaje>();
        }

        public void addViaje(modelo.Viaje viaje) {
            this.viajes = new ArrayList<>();
        }

        public int getNroViaje() {
            return this.viajes.size();
        }
    }
}
