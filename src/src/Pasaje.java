public class Pasaje {
    private long numero;
    private int asiento;

    private venta venta;
    private Viaje viaje;
    private Pasajero pasajero;

    public Pasaje(long numero, int asiento, venta venta, Viaje viaje, Pasajero pasajero) {
        this.numero = numero;
        this.asiento = asiento;
        this.venta = venta;
        this.viaje = viaje;
        this.pasajero = pasajero;
    }

    public int getNumero() {
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

    public Pasajero getPasajero() {
        return pasajero;
    }
}
