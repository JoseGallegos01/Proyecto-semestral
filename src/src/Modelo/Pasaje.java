package Modelo;

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

//    @Override
//    public String toString() {
//
//        String empresa = this.getViaje().getNombre().toUpperCase();
//        String numeroPasaje = String.valueOf(this.Numero());
//
//        String nombrePasajero = this.getPasajero().getNombreCompleto().toString().toUpperCase();
//        String rutPasajero = this.getPasajero().getIdPersona().toString();
//
//        String patente = this.getViaje().getBus().getPatente().toUpperCase();
//        String asiento = String.valueOf(this.asiento);
//        String valorPagado = String.valueOf(this.getMonto);
//
//        String origen = this.getViaje().getTerminalSalida().getNombre().toUpperCase();
//        String destino = this.getViaje().getTerminalLlegada().getNombre().toUpperCase();
//        String fecha = this.getViaje().getFecha();
//        String hora = this.getViaje().getHora();
//
//        System.out.println("----------------------- PASAJE ELECTRÓNICO -----------------------");
//        System.out.printf("%-23s %s\n", "Nombre Empresa", "Número de pasaje");
//        System.out.printf("%-23s %s\n", empresa, numeroPasaje);
//
//        System.out.printf("%-48s %s\n", "Nombre Pasajero", "RUT/Pasaporte");
//        System.out.printf("%-48s %s\n", nombrePasajero, rutPasajero);
//
//        System.out.printf("%-17s %-15s %s\n", "Patente bus", "Asiento", "Valor Pagado");
//        System.out.printf("%-17s %-15s %s\n", patente, asiento, valorPagado);
//
//        System.out.printf("%-17s %-20s %-14s %s\n", "Terminal origen", "Terminal destino", "Fecha", "Hora");
//        System.out.printf("%-17s %-20s %-14s %s\n", origen, destino, fecha, hora);
//        System.out.println("------------------------------------------------------------------");
//
//        return ?;
//    }
}
