package Modelo;

import modelo.Terminal;

import java.util.ArrayList;
import java.util.List;

public class PagoEfectivo extends Pago {

    public PagoEfectivo(int monto) {
        super(monto);
    }

    public static class Bus {
        //Vicente Salinas

        private String patente;
        private String marca;
        private String modelo;
        private int nroAsientos;
        private List<Terminal.Viaje> viajes;

        public Bus(String patente, int nroAsientos) {
            this.patente = patente;
            this.nroAsientos = nroAsientos;
            this.viajes = new ArrayList<>();
        }
        public String getPatente() {
            return patente;
        }
        public String getMarca() {
            return marca;
        }
        public void setMarca(String marca) {
            this.marca = marca;
        }
        public String getModelo() {
            return modelo;
        }
        public void setModelo(String modelo) {
            this.modelo = modelo;
        }
        public int getNroAsientos() {
            return nroAsientos;
        }
        public void addViaje(Terminal.Viaje viaje){
            viajes.add(viaje);
        }
    }
}
