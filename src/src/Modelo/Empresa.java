package modelo;

import utilidades.Rut;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private java.util.ArrayList<modelo.Bus> buses;
    private java.util.ArrayList<Tripulante> tripulantes;

    public Empresa(Rut rut, String nombre) {
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
        this.buses.toArray(new modelo.Bus[0]);
    }


}
