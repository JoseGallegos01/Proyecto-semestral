package Modelo;
import utilidades.*;

import java.io.Serializable;

public abstract class Tripulante extends Persona implements Serializable {

    private Direccion direccion;

    public Tripulante(IdPersona id, Nombre nom, Direccion dir) {
        super(id, nom);
        if (dir == null) {
            throw new IllegalArgumentException("La direccion del tripulante no puede ser nula.");
        }
        this.direccion = dir;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        if (direccion != null) {
            this.direccion = direccion;
        } else {
            System.out.println("Error: Se intento asignar una direccion nula.");
        }
    }

    public abstract void addViaje(Viaje viaje);
    public abstract int getNroViajes();
}
