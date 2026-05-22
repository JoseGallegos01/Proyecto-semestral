package modelo;

import java.util.ArrayList;

public class Terminal {
    private String nombre;
    private Direccion direccion;
    private ArrayList<Viaje> llegadas;
    private ArrayList<Viaje> salidas;
    public Terminal(String nombre, Direccion direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.llegadas = new ArrayList<>();
        this.salidas = new ArrayList<>();
    }
    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion){
        this.direccion = direccion;
    }
    public void addLlegada(Viaje viaje){
        this.llegadas.add(viaje);
    }
    public void addSalida(Viaje viaje){
        this.salidas.add(viaje);
    }
    public Viaje[] getLlegadas(){
        return this.llegadas.toArray(new Viaje[0]);
    }
    public Viaje[] getSalidas(){
        return this.salidas.toArray(new Viaje[0]);
    }

    public static class Tripulante extends Persona {

        private Direccion direccion;

        public Tripulante(IdPersona id, Nombre nom, Direccion dir) {
            super(id, nom); // Llama al constructor de la clase padre (Persona)
            this.direccion = dir;
        }

        public Direccion getDireccion() {
            return this.direccion;
        }

        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }

        public abstract void addViaje(Viaje viaje);

        public abstract int getNroViajes();
    }
}
