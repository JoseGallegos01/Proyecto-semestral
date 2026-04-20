public class Pasaje {
    private long numero;
    private int asiento;

    private venta venta;
    private Viaje viaje;

    public Pasaje(long numero, int asiento, venta venta, Viaje viaje) {
        this.numero = numero;
        this.asiento = asiento;
        this.venta = venta;
        this.viaje = viaje;
    }

    public long getNumero() {
        return (int) numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public venta getVenta() {
        return venta;
    }

    public Viaje getViaje() {
        return viaje;
    }
}
