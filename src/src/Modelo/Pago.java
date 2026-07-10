package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utilidades.*;

public class Pago implements Serializable {
    private int monto;

    public Pago(int monto) {
        this.monto = monto;
    }

    public int getMonto() {
        return monto;
    }

}
