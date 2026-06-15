package utilidades;

import java.io.Serializable;

public class Direccion implements Serializable {
    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getComuna() {
        return comuna;
    }

    public String toString(){
        return calle + " " + numero + ", " + comuna;
    }

    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }

        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }

        Direccion direccion = (Direccion) otro;

        return this.numero == direccion.numero && this.calle.equals(direccion.calle) && this.comuna.equals(direccion.comuna);
    }
}
