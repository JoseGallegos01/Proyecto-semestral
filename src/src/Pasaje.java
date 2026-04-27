public class Pasaje {
    //Cristobal Jeldres
    //En teoria no pide nada mas
    private long numero;
    private int asiento;

    private Venta venta;
    private Viaje viaje;
    private Pasajero pasajero;

    public Pasaje(int asiento, Venta venta, Viaje viaje, Pasajero pasajero) {
        this.numero = numero;
        this.asiento = asiento;
        this.venta = venta;
        this.viaje = viaje;
        this.pasajero = pasajero;
    }

    public long getNumero() { return (int) numero; }

    public int getAsiento() { return asiento; }

    public Venta getVenta() { return venta; }

    public Viaje getViaje() { return viaje; }

    public Pasajero getPasajero() { return pasajero; }
}
