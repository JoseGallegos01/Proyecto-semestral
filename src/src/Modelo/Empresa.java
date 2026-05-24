package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Direccion;
import utilidades.Rut;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private java.util.ArrayList<modelo.Bus> buses;
    private java.util.ArrayList<Tripulante> tripulantes;

    public Empresa(Rut rut, String nombre, String url) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses = new java.util.ArrayList<>();
        this.tripulantes = new java.util.ArrayList<>();
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(modelo.Bus bus) {
        if (bus != null) {
            this.buses.add(bus);
        }
    }

    public modelo.Bus[] getBuses() {
        return this.buses.toArray(new modelo.Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir) {
        if (id == null || nom == null || dir == null) {
            return false;
        }

        for (Tripulante tripulante : tripulantes) {
            if(){
                return false;
            }
        }

        Conductor nuevoConductor = new Conductor(id,nom,dir);
        return this.tripulantes.add(nuevoConductor);
    }

    public boolean addAuxiliar (IdPersona id, Nombre nom, Direccion dir){
        if (id == null || nom == null || dir == null) {
            return false;
        }
        for (Tripulante tripulante : tripulantes) {
            if (){
                return false;
            }
        }

        Auxiliar nuevoAuxiliar = new Auxiliar(id,nom,dir);
        return this.tripulantes.add(nuevoAuxiliar);


    }
    public Tripulante[] getTripulantes() {
        return this.tripulantes.toArray(new Tripulante[0]);
    }

    public modelo.Venta[] getVentas(){
        java.util.ArrayList<modelo.Venta> ventas = new java.util.ArrayList<>();


        return ;
    }

}
