package Modelo;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Pasaje implements Serializable {

    private long numero;
    private int asiento;
    private Venta venta;
    private Viaje viaje;
    private Pasajero pasajero;

    public Pasaje(long numero, Venta venta, Viaje viaje, Pasajero pasajero) {
        this.numero = numero;
        this.asiento = asiento;
        this.venta = venta;
        this.viaje = viaje;
        this.pasajero = pasajero;
    }

    public long getNumero() {
        return numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public Venta getVenta() {
        return venta;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String nombreEmpresa = this.viaje.getBus().getEmpresa().getNombre().toUpperCase();
        String nombrePasajero = this.pasajero.getNombreCompleto().toString().toUpperCase();
        String idPasajero = this.pasajero.getIdPersona().toString();
        String patente = this.viaje.getBus().getPatente().toUpperCase();
        int precio = this.viaje.getPrecio();
        String origen = this.viaje.getTerminalSalida().getNombre().toUpperCase();
        String destino = this.viaje.getTerminalLlegada().getNombre().toUpperCase();
        String fecha = this.viaje.getFecha().format(dateFormatter);
        String hora = this.viaje.getHora().format(timeFormatter);

        StringBuilder sb = new StringBuilder();
        sb.append("------------------------- PASAJE ELECTRONICO -------------------------\n");
        sb.append(String.format("%-30s %s\n", "Nombre Empresa", "Numero de pasaje"));
        sb.append(String.format("%-30s %d\n", nombreEmpresa, this.numero));
        sb.append("\n");
        sb.append(String.format("%-45s %s\n", "Nombre Pasajero", "RUT/Pasaporte"));
        sb.append(String.format("%-45s %s\n", nombrePasajero, idPasajero));
        sb.append("\n");
        sb.append(String.format("%-15s %-20s %s\n", "Patente bus", "Asiento", "Valor Pagado"));
        sb.append(String.format("%-15s %-20d %d\n", patente, this.asiento, precio));
        sb.append("\n");
        sb.append(String.format("%-25s %-25s %-15s %s\n", "Terminal origen", "Terminal destino", "Fecha", "Hora"));
        sb.append(String.format("%-25s %-25s %-15s %s\n", origen, destino, fecha, hora));
        sb.append("----------------------------------------------------------------------");

        return sb.toString();
    }
}