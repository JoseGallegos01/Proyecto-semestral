package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Direccion;
import utilidades.Rut;
import java.util.ArrayList;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private java.util.ArrayList<Modelo.Bus> buses;
    private java.util.ArrayList<Tripulante> tripulantes;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.buses = new ArrayList<>();
        this.tripulantes = new ArrayList<>();
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

    public void addBus(Bus bus) {
        if (bus != null) {
            this.buses.add(bus);
        }
    }

    public Bus[] getBuses() {
        return this.buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, Direccion dir) {
        if (id == null || nom == null || dir == null) {
            return false;
        }

        for (Tripulante tripulante : tripulantes) {
            if(tripulante.getIdPersona().equals(id) && tripulante.getDireccion().equals(dir)) {
                return false;
            }
        }

        Conductor nuevoConductor = new Conductor(id,nom,dir);
        return this.tripulantes.add(nuevoConductor);
    }

    public boolean addAuxiliar (IdPersona id, Nombre nom, Direccion dir) {
        if (id == null || nom == null || dir == null) {
            return false;
        }
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.getIdPersona().equals(id) && tripulante.getDireccion().equals(dir)) {
                return false;
            }
        }

        Auxiliar nuevoAuxiliar = new Auxiliar(id, nom, dir);
        return this.tripulantes.add(nuevoAuxiliar);
    }
    public Tripulante[] getTripulantes() {
            return this.tripulantes.toArray(new Tripulante[0]);
        }

        public Venta[] getVentas(){
            ArrayList<Venta> ventas = new java.util.ArrayList<>();
            for (Bus bus : buses) {
                for (Viaje viaje : bus.getViajes()) {
                    for (Venta venta : viaje.getVentas()) {
                        if (!ventas.contains(venta)) {
                            ventas.add(venta);
                        }
                    }
                }
            }

            return ventas.toArray(new Venta[0]);
        }
    }

