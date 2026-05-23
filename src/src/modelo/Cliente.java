package Modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

public class Cliente extends Persona {
    private String email;
<<<<<<< HEAD:src/src/Cliente.java
    private Venta venta;

    //Jose Gallegos
=======
>>>>>>> origin/Cristobal:src/src/modelo/Cliente.java

    public Cliente(IdPersona id, Nombre nom, String email) {
        super(id, nom);
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}