package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Venta implements Serializable {
    ArrayList<Pasaje> pasajes;
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    //Se añade un private externo que sin el no se puede verificar si el pago fue con efectivo o targeta
    private Pago pago;
    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.pasajes = new ArrayList<>();
        this.cliente = cliente;

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
    public Cliente getCliente(){
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero){
        Pasaje pasaje = new Pasaje(asiento, this, viaje, pasajero);
        pasajes.add(pasaje);
        viaje.addPasaje(pasaje);
    }

    //desde aca se usa el private Pago pago
    public boolean pagaMonto(){
        if(pago != null){
            return false;
        }
        pago = new PagoEfectivo(getMonto());
        return true;
    }

    public boolean pagaMonto(long nroTarjeta){
        if(pago != null){
            return false;
        }
        pago = new PagoTarjeta(getMonto(), nroTarjeta);
        return true;
    }

    public int getMontoPagado(){
        if(pago == null){
            return 0;
        }
        return pago.getMonto();
    }

    public String getTipoPago(){
        if(pago == null){
            return "Sin pago";
        }
        if(pago instanceof PagoEfectivo){
            return "Efectivo";
        }
        if(pago instanceof PagoTarjeta){
            return "Tarjeta";
        }
        return "";
    }
    //fin del private Pago pago
    public Pasaje[] getPasajes(){
        return pasajes.toArray(new Pasaje[0]);
    }
    public int getMonto(){
        int monto = 0;
        for (Pasaje p : pasajes){
            monto += p.getViaje().getPrecio();
        }
        return monto;
    }

}