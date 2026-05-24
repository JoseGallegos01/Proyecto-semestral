pcion;
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

        public Direccionackage modelo;

import java.time.LocalDate;
import java.time.LocalTime;
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
                this.direccion = direc getDireccion() {
            return this.direccion;
        }

        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }

        public abstract void addViaje(Viaje viaje);

        public abstract int getNroViajes();
    }

    public static class Viaje {
        //Vicente Salinas

        private LocalDate fecha;
        private LocalTime hora;
        private int precio;
        private Bus bus;
        private ArrayList<Pasaje>pasajes;

        public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus){
            this.fecha = fecha;
            this.hora = hora;
            this.precio = precio;
            this.bus = bus;
            this.pasajes=new ArrayList<>();
            bus.addViaje(this);
        }
        public LocalDate getFecha() {
            return fecha;
        }
        public LocalTime getHora() {
            return hora;
        }
        public int getPrecio() {
            return precio;
        }
        public void setPrecio(){
            this.precio = precio;
        }
        public Bus getBus(){
            return bus;
        }
        public void setBus(Bus bus){
            this.bus = bus;
        }
        public void addPasaje(Pasaje pasaje){
            pasajes.add(pasaje);
        }
        public String [][]getAsientos(){
        int total =bus.getNroAsientos();
        String [][] asientos = new String [total][2];

        for (int i =0; i<total; i++){
            asientos[i][0]=(i+1)+"";
            asientos[i][1]="Libre";
        }
        for (Pasaje p:pasajes){
            int asiento =p.getAsiento();
            asientos[asiento-1][1]="Ocupado";
        }
        return asientos;

        }
        public String [][]getListaPasajeros() {
            String[][] lista = new String[pasajes.size()][4];

            for (int i = 0; i < pasajes.size(); i++) {
                Pasajero p = pasajes.get(i).getPasajero();
                lista[i][0] = p.getIdPersona().toString();
                lista[i][1] = p.getNombreCompleto().toString();
                lista[i][2] = String.valueOf(p.getNomContacto());
                lista[i][3] = p.getFonoContacto();
            }
            return lista;
        }
        public boolean existeDisponibilidad(){
        return getnroAsientosDisponibles()>0;
        }
        public int getnroAsientosDisponibles(){
            return bus.getNroAsientos()-pasajes.size();
        }
    }
}
