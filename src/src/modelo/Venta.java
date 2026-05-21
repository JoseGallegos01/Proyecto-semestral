package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    ArrayList<Pasaje> pasajes;
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private boolean pagada;
    private String tipoPago;
    private int montoPagado;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.pasajes = new ArrayList<>();
        this.cliente = cliente;
        this.pagada = false;
        this.tipoPago = null;
        this.montoPagado = 0;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje pasaje = new Pasaje(asiento, this, viaje, pasajero);
        pasajes.add(pasaje);
        viaje.addPasaje(pasaje);
    }

    public Pasaje[] getPasajes() {
        return pasajes.toArray(new Pasaje[0]);
    }

    public int getMonto() {
        int monto = 0;
        for (Pasaje p : pasajes) {
            monto += p.getViaje().getPrecio();
        }
        return monto;
    }

    public boolean pagaMonto() {
        if (pagada) {
            return false;
        }
        pagada = true;
        tipoPago = "Efectivo";
        montoPagado = getMonto();
        return true;
    }

    public boolean pagaMonto(String nroTarjeta) {
        if (pagada) {
            return false;
        }
        pagada = true;
        tipoPago = "Tarjeta";
        montoPagado = getMonto();
        return true;
    }

    public int getMontoPagado() {
        if (pagada) {
            return montoPagado;
        } else {
            return 0;
        }
    }

    public String getTipoPago() {
        if (pagada) {
            return tipoPago;
        } else {
            return null;
        }
    }
}



